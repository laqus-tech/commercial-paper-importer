package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.infrastructure.config.Constants;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.SecretManager;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroNegocioException;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroTecnicoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SubmitRegistrationForApprovalApi {

    private static final Logger LOG = LogManager.getLogger(SubmitRegistrationForApprovalApi.class);
    private final static String API_POST = "/api/v1/cadastros/%s";

    private final static String MESSAGE_ERROR = "Erro ao submeter para aprovaçãp, verifique o erro %s";

    public String approve(String token , String id) {
        var uri = String.format(Constants.ApiCadastro.URI, SecretManager.getInstance().getEnvironment());
        var apiPost = String.format(API_POST, id);

        LOG.info("post: {}", apiPost);

        try {
            final HttpClient client = HttpClient.newHttpClient();

            var uriPost = String.format("%s%s", uri, apiPost);
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uriPost))
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", String.format("Bearer %s", token))
                    .build();

            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

            var statusCode = send.statusCode();
            var response = send.body();

            if (statusCode >= 200 && statusCode < 300) {
                LOG.info("sucesso no post de submeter para aprovação: {} response: {}", uriPost, response);
                return response;
            } else {
                throw new ErroNegocioException(String.format("request não processado corretamente, analise o retorno da requisição, submeter aprovação: %s", response));
            }
        } catch (URISyntaxException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (IOException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (InterruptedException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }
    }

}
