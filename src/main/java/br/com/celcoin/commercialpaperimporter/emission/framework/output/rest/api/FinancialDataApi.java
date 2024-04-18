package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.FinancialDataResponseDTO;
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

public class FinancialDataApi {

    private static final Logger LOG = LogManager.getLogger(FinancialDataApi.class);
    private final static String API_POST = "/api/v1/solicitacoes/%s/dados-financeiros";

    private final static String MESSAGE_ERROR = "Erro ao adicionar dados financeiros, verifique o erro %s";

    public FinancialDataResponseDTO postFinancialData(String token, String id, FinancialData financialData) {
        var uri = String.format(Constants.ApiEmissao.URI, SecretManager.getInstance().getEnvironment());
        var uriPost = String.format("%s%s", uri, String.format(API_POST, id));
        LOG.info("post: {}, objeto: {}", uriPost, financialData);

        try {
            var bodyObject = this.financialDataToJson(financialData);
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
                return FinancialDataResponseDTO.jsonToInput(response);
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

    public String financialDataToJson(FinancialData financialData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(financialData);
        } catch (JsonProcessingException e) {
            throw new ErroTecnicoException(String.format(MESSAGE_ERROR, e));
        }
    }

}
