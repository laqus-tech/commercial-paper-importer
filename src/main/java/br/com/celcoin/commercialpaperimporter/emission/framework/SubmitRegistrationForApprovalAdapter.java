package br.com.celcoin.commercialpaperimporter.emission.framework;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.SubmitRegistrationForApprovalOutput;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.SubmitRegistrationForApprovalApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;

public class SubmitRegistrationForApprovalAdapter implements SubmitRegistrationForApprovalOutput {
    @Override
    public String approve(String id) {
        var token = new TokenApi().getAccessToken();
        return new SubmitRegistrationForApprovalApi().approve(token, id);
    }
}
