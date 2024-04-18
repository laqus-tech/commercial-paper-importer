package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.TokenApiResponseDTO;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.Constants;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.SecretManager;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroTecnicoException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TokenApi {

    private static final Logger LOG = LogManager.getLogger(TokenApi.class);
    private final static String API_POST = "/api/v1/auth";

    private final static String MESSAGE_ERROR = "Erro ao solicitar o token, verifique o erro %s";

    public String getAccessToken() {
        try {
            var uri = String.format(Constants.ApiFrontier.URI, SecretManager.getInstance().getEnvironment());
            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("%s%s", uri, API_POST)))
                    .version(HttpClient.Version.HTTP_2)
                    .POST(HttpRequest.BodyPublishers.ofString( String.format("{\"apiKey\":\"%s\",\"secretKey\":\"%s\"}", SecretManager.getInstance().getApiKey(), SecretManager.getInstance().getSecretKey()) ))
                    .header("Content-Type", "application/json")
                    .build();

            return TokenApiResponseDTO.jsonToInput(client.send(request, HttpResponse.BodyHandlers.ofString()).body()).getAccessToken();
        } catch (URISyntaxException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (IOException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (InterruptedException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }
    }

}
