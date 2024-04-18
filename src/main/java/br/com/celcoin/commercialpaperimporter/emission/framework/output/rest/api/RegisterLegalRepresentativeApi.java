package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.LegalRepresentativeRequestDTO;
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

public class RegisterLegalRepresentativeApi {

    private static final Logger LOG = LogManager.getLogger(RegisterLegalRepresentativeApi.class);
    private final static String API_POST = "/api/v1/cadastros/%s/representantes-legais";

    private final static String MESSAGE_ERROR = "Erro ao adicionar o representante legal, verifique o erro %s";

    public LegalRepresentativeRequestDTO postLegalRepresentative(String token, String id, LegalRepresentativeRequestDTO legalRepresentativeRequestDTO) {
        var uri = String.format(Constants.ApiCadastro.URI, SecretManager.getInstance().getEnvironment());
        var apiPost = String.format(API_POST, id);

        LOG.info("post: {}", apiPost);

        try {
            var bodyObject = this.legalRepresentativeToJson(legalRepresentativeRequestDTO);
            final HttpClient client = HttpClient.newHttpClient();

            var uriPost = String.format("%s%s", uri, apiPost);
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

            LOG.info("status code quantidade: {}", statusCode);

            if (statusCode >= 200 && statusCode < 300) {
                LOG.info("sucesso no post: {} response: {}", uriPost, response);
                return LegalRepresentativeRequestDTO.jsonToInput(response);
            } else {
                throw new ErroNegocioException(String.format("request não processado corretamente, analise o retorno da quantidade de emissao: %s", response));
            }
        } catch (URISyntaxException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (IOException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (InterruptedException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }

    }

    public String legalRepresentativeToJson(LegalRepresentativeRequestDTO issuer) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(issuer);
        } catch (JsonProcessingException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }
    }

}
