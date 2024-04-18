package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.RequestIssuanceApiResponseDTO;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.Constants;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.SecretManager;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroNegocioException;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroTecnicoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestIssuanceApi {

    private static final Logger LOG = LogManager.getLogger(RequestIssuanceApi.class);
    private final static String API_POST = "/api/v1/solicitacoes";

    private final static String MESSAGE_ERROR = "Erro ao enviar solicitação, verifique o erro %s";

    public RequestIssuanceApiResponseDTO postRequestIssuance(String token, Issuance createCommercialPaper) {
        var uri = String.format(Constants.ApiEmissao.URI, SecretManager.getInstance().getEnvironment());
        LOG.info("post: {}, objeto: {}", API_POST, createCommercialPaper);

        try {
            var bodyObject = this.commercialPaperToJson(createCommercialPaper);
            final HttpClient client = HttpClient.newHttpClient();

            var uriPost = String.format("%s%s", uri, API_POST);
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uriPost))
                    .version(HttpClient.Version.HTTP_2)
                    .POST(HttpRequest.BodyPublishers.ofString(bodyObject))
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", String.format("Bearer %s", token))
                    .build();

            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

            var statusCode = send.statusCode();
            var response = send.body();

            if (statusCode >= 200 && statusCode < 300) {
                LOG.info("sucesso no post: {} response: {}", uriPost, response);
                return RequestIssuanceApiResponseDTO.jsonToInput(response);
            } else {
                throw new ErroNegocioException(String.format("request não processado corretamente, analise o retorno de solicitacoes: %s", response));
            }
        } catch (URISyntaxException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (IOException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (InterruptedException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }
    }

    public String commercialPaperToJson(Issuance createCommercialPaper) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(createCommercialPaper);
        } catch (JsonProcessingException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }
    }

}
