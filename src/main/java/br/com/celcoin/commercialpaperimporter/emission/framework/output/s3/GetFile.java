package br.com.celcoin.commercialpaperimporter.emission.framework.output.s3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class GetFile {

    private static final Logger LOG = LogManager.getLogger(GetFile.class);

    public byte[] getFile(String bucketName, String keyName) {
        return getObjectBytes(S3Client.builder().region(Region.US_EAST_1).build(), bucketName, keyName);
    }

    public byte[] getObjectBytes(S3Client s3, String bucketName, String keyName) {
        try {
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
            return objectBytes.asByteArray();
        } catch (S3Exception e) {
            LOG.error("erro ao pegar o arquivo: {}", e);
        }
        return null;
    }

}
