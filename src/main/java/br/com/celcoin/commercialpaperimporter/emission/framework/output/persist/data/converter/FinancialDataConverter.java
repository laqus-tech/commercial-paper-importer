//package br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.data.converter;
//
//import br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.data.FinancialData;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//
//public class FinancialDataConverter implements DynamoDBTypeConverter<AttributeValue, FinancialData> {
//
//    @Override
//    public AttributeValue convert(FinancialData data) {
//        return new AttributeValue()
//                .addMEntry("custoDaEmissao", new AttributeValue().withN(data.getCustoDaEmissao()));
//    }
//
//    @Override
//    public FinancialData unconvert(AttributeValue attributeValue) {
//        return null;
//    }
//}
