package br.com.celcoin.commercialpaperimporter.emission.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class InputIssuer {

    private String typeOfCompany;

    private String function;

    private String cnpj;

    private String corporateName;

    private String mainActivity;

    private Integer revenueAverageMonthly12Months;

    private InputAddress address;

    private InputBankData bankData;

    private List<InputPhone> phones;

    public static InputIssuer jsonToInput(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, InputIssuer.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
