package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.ConsultRequestResponseDTO;
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

public class ConsultRequestApi {

    private static final Logger LOG = LogManager.getLogger(SubmissionApi.class);
    private final static String API_GET = "/api/v1/solicitacoes/%s";

    private final static String MESSAGE_ERROR = "Erro ao consultar solicitação";

    public ConsultRequestResponseDTO consult(String token, String id) {
        var uri = String.format(Constants.ApiEmissao.URI, SecretManager.getInstance().getEnvironment());
        var apiGet = String.format(API_GET, id);
        LOG.info("get: {}", apiGet);

        try {
            final HttpClient client = HttpClient.newHttpClient();

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("%s%s", uri, apiGet)))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", String.format("Bearer %s", token))
                    .build();

            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

            var statusCode = send.statusCode();
            var response = send.body();

            if (statusCode >= 200 && statusCode < 300) {
                LOG.info("sucesso no get de submissao: {}", response);
                return ConsultRequestResponseDTO.jsonToInput(response);
            } else {
                throw new ErroNegocioException(String.format("request não processado corretamente, analise o retorno de soliciraçoes: %s", response));
            }
        } catch (URISyntaxException e) {
            throw new ErroTecnicoException(MESSAGE_ERROR);
        } catch (IOException e) {
            throw new ErroTecnicoException(MESSAGE_ERROR);
        } catch (InterruptedException e) {
            throw new ErroTecnicoException(MESSAGE_ERROR);
        }
    }

}
