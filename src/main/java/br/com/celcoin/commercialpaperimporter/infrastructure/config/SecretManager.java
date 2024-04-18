package br.com.celcoin.commercialpaperimporter.infrastructure.config;

import br.com.celcoin.commercialpaperimporter.infrastructure.config.vo.SecretManagerVO;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

public class SecretManager {
    private static SecretManager instance;
    private String apiKey;

    private String secretKey;

    private String environment;

    private SecretManager() {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();

        new com.amazonaws.services.secretsmanager.model.GetSecretValueRequest().withSecretId("");

        final GetSecretValueRequest request = new GetSecretValueRequest().withSecretId(Constants.ApiToken.SECRET);
        final GetSecretValueResult result = client.getSecretValue(request);
        final SecretManagerVO secretManagerVO = SecretManagerVO.jsonToInput(result.getSecretString());

        this.apiKey = secretManagerVO.getApiKey();
        this.secretKey = secretManagerVO.getSecretKey();
        this.environment = secretManagerVO.getEnvironment();
    }

    public static synchronized SecretManager getInstance() {
        if (instance == null) {
            instance = new SecretManager();
        }
        return instance;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public String getEnvironment() {
        return this.environment;
    }

}
