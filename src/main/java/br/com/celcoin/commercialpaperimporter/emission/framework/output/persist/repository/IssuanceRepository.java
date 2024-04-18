package br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.repository;

import br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.data.IssuanceData;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.Constants;
import br.com.celcoin.commercialpaperimporter.infrastructure.config.DynamoDBAdapter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.data.IssuanceData.ISSUANCE_TABLE_NAME;

public class IssuanceRepository {

    private static final int NUMBER_WORKER_SCAN = 3;
    private static final Logger LOG = LogManager.getLogger(IssuanceRepository.class);
    private final DynamoDBMapper mapper;
    private static DynamoDBAdapter db_adapter;

    public IssuanceRepository() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(ISSUANCE_TABLE_NAME))
                .build();
        this.db_adapter = DynamoDBAdapter.getInstance();
        this.mapper = this.db_adapter.createDbMapper(mapperConfig);
    }

    @Transactional
    public IssuanceData create(IssuanceData data) {
        LOG.info("persiste: {}", data);
        this.mapper.save(data);
        return data;
    }

    public Optional<List<IssuanceData>> listByStatus(String status) {
        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(status));

        final DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withFilterExpression(String.format("%s = :val1", Constants.IssuanceData.STATUS))
                .withExpressionAttributeValues(eav);

        final List<IssuanceData> query = mapper.parallelScan(IssuanceData.class, dynamoDBScanExpression, NUMBER_WORKER_SCAN);

        return Optional.of(query.stream()
                .filter(issuance -> issuance != null)
                .collect(Collectors.toList()));
    }

    public IssuanceData getByRequestId(String requestId) {
        LOG.info("get de id: {}", requestId);
        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(requestId));

        final DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withFilterExpression(String.format("%s = :val1", Constants.IssuanceData.REQUEST_ID))
                .withExpressionAttributeValues(eav);

        final List<IssuanceData> query = mapper.parallelScan(IssuanceData.class, dynamoDBScanExpression, NUMBER_WORKER_SCAN);

        return query.stream()
                .filter(issuance -> issuance.getId() != null)
                .findFirst()
                .orElse(null);
    }

    public IssuanceData merge(IssuanceData data) {
        this.mapper.save(data);
        return data;
    }

    public IssuanceData get(String id, String cnpjDoEscriturador) {
        return this.mapper.load(IssuanceData.class, id, cnpjDoEscriturador);
    }

}
