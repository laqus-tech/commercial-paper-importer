# README #

Steps to run
### What is this repository for? ###

* receives events from SQS and sends emissions to Laqus

### How do I get set up? ###

* config serverless framework
* configure a bucket for the account in serverless.yml
* create a topic and add permissions on the file serverless.yml
* create three environment variables: (API_KEY, SECRET_KEY and ENVIRONMENT) and place the secret path in the serverless.yml files and in the Constants.java object on line 6
* configure the name of the dynamo table in the serverless.yml file on lines (12, 34 and 70). And configure the table name in the IssuanceData.java object line 22
* to build the project, 1 - mvn clean install 2 - serverless deploy