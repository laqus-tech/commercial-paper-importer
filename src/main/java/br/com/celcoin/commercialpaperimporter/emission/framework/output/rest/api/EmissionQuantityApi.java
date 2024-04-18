package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.EmissionQuantityResponseDTO;
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

public class EmissionQuantityApi {

    private static final Logger LOG = LogManager.getLogger(EmissionQuantityApi.class);
    private final static String API_GET = "/api/v1/emissores/%s/quantidade-emissoes";

    private final static String MESSAGE_ERROR = "Erro ao solicitar o próximo numero de emissão, verifique o erro %s";

    public EmissionQuantityResponseDTO postRequestIssuance(String token, String cnpjEmissor) {
        var uri = String.format(Constants.ApiEmissao.URI, SecretManager.getInstance().getEnvironment());
        var apiGet = String.format(API_GET, cnpjEmissor);

        LOG.info("get: {}", apiGet);

        try {
            final HttpClient client = HttpClient.newHttpClient();

            var uriPost = String.format("%s%s", uri, apiGet);
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uriPost))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", String.format("Bearer %s", token))
                    .build();

            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

            var statusCode = send.statusCode();
            var response = send.body();

            LOG.info("status code quantidade: {}", statusCode);

            if (statusCode >= 200 && statusCode < 300) {
                LOG.info("sucesso no get: {} response: {}", uriPost, response);
                return EmissionQuantityResponseDTO.jsonToInput(response);
            } else if (statusCode == 400) {
                LOG.info("emissor ainda não foi aprovado: {} response: {}", uriPost, response);
                return EmissionQuantityResponseDTO.builder().build();
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

}
