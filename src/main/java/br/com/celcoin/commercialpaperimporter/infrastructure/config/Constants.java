package br.com.celcoin.commercialpaperimporter.infrastructure.config;

public class Constants {

    public class ApiToken {
        public static final String SECRET = "arn:aws:secretsmanager:us-east-1:061774114609:secret:sandbox/laqus/api-1MQnAQ";

    }

    public class ApiEmissao {
        public static final String URI = "https://emissao-frontier.%s.cloud.laqus.com.br";

    }

    public class ApiFrontier {
        public static final String URI = "https://auth-frontier.%s.cloud.laqus.io";

    }

    public class ApiCadastro {
        public static final String URI = "https://onboarding-frontier.%s.cloud.laqus.io";

    }

    public class IssuanceData {
        public static final String ID = "ID";

        public static final String ISSUANCE_NUMBER = "ISSUANCE_NUMBER";

        public static final String ID_INTEGRATION = "ID_INTEGRATION";
        public static final String REQUEST_ID = "REQUEST_ID";

        public static final String SERIES_NUMBER = "SERIES_NUMBER";

        public static final String SERIES_NUMBER_LSI = "SERIES_NUMBER_LSI";

        public static final String TYPE = "TYPE";
        public static final String STATUS = "ISSUANCE_STATUS";

        public static final String ISSUANCE_DATE = "ISSUANCE_DATE";

        public static final String ISSUANCE_EXPIRE_DATE = "ISSUANCE_EXPIRE_DATE";

        public static final String LOCAL_OF_ISSUE = "LOCAL_OF_ISSUE";

        public static final String UF_OF_THE_EMISSION = "UF_OF_THE_EMISSION";

        public static final String COST_OF_ISSUE = "COST_OF_ISSUE";

        public static final String NOMINAL_UNITAR_VALUE = "NOMINAL_UNITAR_VALUE";

        public static final String AMOUNT = "AMOUNT";

        public static final String PERCENTUAL_FOR_INTEREST_REMUNERATORIES = "PERCENTUAL_FOR_INTEREST_REMUNERATORIES";

        public static final String INDEXER_PERCENTAGE = "INDEXER_PERCENTAGE";

        public static final String INDEXER = "INDEXER";

        public static final String CONVENTION = "CONVENTION";

        public static final String PAYMENT_PLACE = "PAYMENT_PLACE";

        public static final String UF_OF_PAGMENT = "UF_OF_PAGMENT";

        public static final String TYPE_OF_PAYMENT = "TYPE_OF_PAYMENT";

        public static final String PERCENTAGE_OF = "PERCENTAGE_OF";

        public static final String DUE_FIRST_INSTALLMENT = "DUE_FIRST_INSTALLMENT";

        public static final String FREQUENCY = "FREQUENCY";

        public static final String PERIOD = "PERIOD";

        public static final String ISSUER_CNPJ = "ISSUER_CNPJ";

        public static final String ESCRITURATOR_CNPJ = "ESCRITURATOR_CNPJ";

        public static final String INVESTORS = "INVESTORS";

        public static final String CREATE_DATE = "CREATE_DATE";

        public static final String UPDATE_DATE = "UPDATE_DATE";

        public static final String ID_ATTACH_SIGNED_TERM = "ID_ATTACH_SIGNED_TERM";

        public static final String LINK = "LINK";

        public static final String BUCKET_NAME = "BUCKET_NAME";

        public static final String KEY_NAME = "KEY_NAME";

    }

}
