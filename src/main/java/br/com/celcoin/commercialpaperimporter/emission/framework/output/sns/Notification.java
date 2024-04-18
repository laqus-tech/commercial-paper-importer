package br.com.celcoin.commercialpaperimporter.emission.framework.output.sns;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Notification {

    private static final Logger LOG = LogManager.getLogger(Notification.class);

    private static final String REGION = "us-east-1";

    private static final String TOPIC_ARN = "arn:aws:sns:us-east-1:061774114609:topico-teste";

    public void send(String message, Issuance issuance) {
        LOG.info("vai notificar ao topico SNS");

        AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
                .withRegion(REGION)
                .build();

        var issuanceJsonStr = toJson(issuance);
        LOG.info("objeto para json: {}", issuanceJsonStr);
        if (message!=null) {
            issuanceJsonStr = String.format("error: %s, para o issuance: %s", message, issuanceJsonStr);
        }

        PublishRequest request = new PublishRequest()
                .withTopicArn(TOPIC_ARN)
                .withMessage(issuanceJsonStr);

        PublishResult result = snsClient.publish(request);
        LOG.info("notificacao : {}", result);
        snsClient.shutdown();
    }

    private String toJson(Issuance issuance) {
        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(issuance);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
