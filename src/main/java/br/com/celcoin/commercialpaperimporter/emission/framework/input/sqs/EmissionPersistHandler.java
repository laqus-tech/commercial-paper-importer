//package br.com.celcoin.commercialpaperimporter.emission.framework.input.sqs;
//
//import br.com.celcoin.commercialpaperimporter.emission.application.ports.input.IssuanceCreateInput;
//import br.com.celcoin.commercialpaperimporter.emission.application.usecases.IssuanceCreateUseCase;
//import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
//import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputRequestIssuance;
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestHandler;
//import com.amazonaws.services.lambda.runtime.events.SQSEvent;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class EmissionPersistHandler implements RequestHandler<SQSEvent, Void> {
//
//    private static final Logger LOG = LogManager.getLogger(EmissionPersistHandler.class);
//    private IssuanceCreateUseCase issuanceCreateUseCase;
//
//
//    public EmissionPersistHandler() {
//        this.issuanceCreateUseCase = new IssuanceCreateInput();
//    }
//
//    @Override
//    public Void handleRequest(SQSEvent event, Context context) {
//        for(SQSEvent.SQSMessage msg : event.getRecords()){
//            this.processMessage(msg, context);
//        }
//        return null;
//    }
//
//    private void processMessage(SQSEvent.SQSMessage msg, Context context) {
//        try {
//            context.getLogger().log("Processed message " + msg.getBody());
//            Issuance issuance = issuanceCreateUseCase.createIssuance(InputRequestIssuance.jsonToInput(msg.getBody()));
//        } catch (Exception e) {
//            context.getLogger().log("An error occurred");
//            LOG.error("error: {}", e.getMessage());
//            LOG.error("{}", e);
//            throw e;
//        }
//
//    }
//}
