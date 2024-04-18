package br.com.celcoin.commercialpaperimporter.emission.framework.input.shcedule;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.input.SubmissionInput;
import br.com.celcoin.commercialpaperimporter.emission.application.usecases.SubmissionUseCase;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;

public class SubmissionHandler implements RequestHandler<ScheduledEvent, Void> {
    private SubmissionUseCase submissionUseCase;

    public SubmissionHandler() {
        this.submissionUseCase = new SubmissionInput();
    }

    @Override
    public Void handleRequest(ScheduledEvent scheduledEvent, Context context) {
        submissionUseCase.processSubmission();
        return null;
    }

}
