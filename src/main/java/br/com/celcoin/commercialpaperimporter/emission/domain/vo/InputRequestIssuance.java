package br.com.celcoin.commercialpaperimporter.emission.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
@JsonIgnoreProperties
public class InputRequestIssuance {

    private String requestId;

    private String type;

    private String seriesNumber;

    private String emissionDate;

    private String dueDate;

    private String emissaoLocation;

    private String ufOfTheEmission;

    private String cnpjOfTheBookkeeper;

    private List<InputInvestor> investors;

    private InputFinancialData financialData;

    private InputConstitutiveTerm constitutiveTerm;

    private InputIssuer issuer;

    private InputLegalRepresentative legalRepresentative;

    public static InputRequestIssuance jsonToInput(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, InputRequestIssuance.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
