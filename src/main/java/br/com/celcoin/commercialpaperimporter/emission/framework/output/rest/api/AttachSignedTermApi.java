package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.AttachSignedTermResponseDTO;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.Constants;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.SecretManager;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroNegocioException;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroTecnicoException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AttachSignedTermApi {

    private static final Logger LOG = LogManager.getLogger(AttachSignedTermApi.class);
    private final static String API_POST = "/api/v1/solicitacoes/%s/documentos";

    private final static String MESSAGE_ERROR = "Erro ao anexar termo assinado, verifique o erro %s";

    public AttachSignedTermResponseDTO postAttachSignedTerm(String token, String id, byte[] constitutiveTerm) {
        var uri = String.format(Constants.ApiEmissao.URI, SecretManager.getInstance().getEnvironment());
        var apiPost = String.format(API_POST, id);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(String.format("%s%s", uri, apiPost));

        final String boundary = "----CustomBoundary123";

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setBoundary(boundary);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("arquivo", new ByteArrayInputStream(constitutiveTerm), org.apache.http.entity.ContentType.DEFAULT_BINARY, "arquivo.pdf");
        builder.addTextBody("tipo", "TermoConstitutivoAssinado");

        HttpEntity multipart = builder.build();

        httpPost.setHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
        httpPost.setHeader("accept", "application/json");
        httpPost.setHeader("Authorization", String.format("Bearer %s", token));
        httpPost.setEntity(multipart);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseJson = EntityUtils.toString(response.getEntity());
            if (statusCode >= 200 && statusCode < 300) {
                LOG.info("sucesso no post do termo assinado: {}", responseJson);
                return AttachSignedTermResponseDTO.jsonToInput(responseJson);
            } else {
                throw new ErroNegocioException(String.format("request não processado corretamente, analise o retorno de anexar o arquivo: %s", response));
            }
        } catch (IOException e) {
            LOG.error("erro no request do documento: {}", e);
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }
    }

}
