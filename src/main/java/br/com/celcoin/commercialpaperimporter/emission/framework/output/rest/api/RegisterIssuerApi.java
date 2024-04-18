package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.IssuerRequestDTO;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.IssuerRespondeDTO;
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

public class RegisterIssuerApi {

    private static final Logger LOG = LogManager.getLogger(FinancialDataApi.class);
    private final static String API_POST = "/api/v1/cadastros";

    private final static String MESSAGE_ERROR = "Erro ao adicionar o emissor, verifique o erro %s";

    public IssuerRespondeDTO postIssuer(String token, IssuerRequestDTO issuer) {
        var uri = String.format(Constants.ApiCadastro.URI, SecretManager.getInstance().getEnvironment());
        var uriPost = String.format("%s%s", uri, API_POST);
        LOG.info("post: {}, objeto: {}", uriPost, issuer);

        try {
            var bodyObject = this.issuerToJson(issuer);
            final HttpClient client = HttpClient.newHttpClient();

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
                return IssuerRespondeDTO.jsonToInput(response);
            } else {
                throw new ErroNegocioException(String.format("request não processado correatamente, analise o retorno de dados financeiros: %s", response));
            }
        } catch (URISyntaxException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (IOException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        } catch (InterruptedException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }

    }

    public String issuerToJson(IssuerRequestDTO issuer) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(issuer);
        } catch (JsonProcessingException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }
    }

}
