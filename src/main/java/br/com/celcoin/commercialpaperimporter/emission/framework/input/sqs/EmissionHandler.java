package br.com.celcoin.commercialpaperimporter.emission.framework.input.sqs;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.input.IssuanceInput;
import br.com.celcoin.commercialpaperimporter.emission.application.usecases.IssuanceUseCase;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputRequestIssuance;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class EmissionHandler implements RequestHandler<SQSEvent, Void> {

    private IssuanceUseCase emissionUseCase;

    private static final Logger LOG = LogManager.getLogger(EmissionHandler.class);

    private static final String QUEUE_NAME = "https://sqs.us-east-1.amazonaws.com/061774114609/testefila2";

    public EmissionHandler() {
        this.emissionUseCase = new IssuanceInput();
    }

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        for(SQSEvent.SQSMessage msg : event.getRecords()){
            this.processMessage(msg, context, sqs);
            this.deleteMessage(msg, sqs);
        }
        return null;
    }

    private void processMessage(SQSEvent.SQSMessage msg, Context context, AmazonSQS sqs) {
        try {
            context.getLogger().log("Processed message " + msg.getBody());
            var issuance = emissionUseCase.requestIssuance(InputRequestIssuance.jsonToInput(msg.getBody()));
        } catch (Exception e) {
            context.getLogger().log("An error occurred");
            LOG.error("error: {}", e.getMessage());
            LOG.error("{}", e);
            throw e;
        }

    }

    private void deleteMessage(SQSEvent.SQSMessage msg, AmazonSQS sqs) {
        String receiptHandle = msg.getReceiptHandle();

        sqs.deleteMessage(new DeleteMessageRequest(QUEUE_NAME, receiptHandle));
        LOG.info("mensagem excluida: {}", receiptHandle);

    }
}
