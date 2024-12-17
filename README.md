'My Bank' V3
- Added Postman collection and Swagger (under /resources folder)
- Access the Swagger Specs at: URL /v3/api-docs
- Access the Swagger UI / API descriptions at URL /swagger-ui/index.html

'MyBank' V2
- This project has been updated to JDK 17 and Spring Boot 3.4

'MyBank' V1

- This project 'mybank' demonstrates the use of Spring Boot, H2/PostgreSQL to build a simple REST app (GET/POST)
- The project uses the standard Spring Boot concepts like REST, JPA, Profiles, Testcontainers, JUnit, Wiremock etc
- The project also has the necessary test code to test the main java code
- This is the V1 (version 1) of the project & next version will add more REST support & other features

A word on the Test Code: 
- There are two independent entities being tested - Referral (T_REFERRAL table) and Transaction (T_TRANSACTION)
- Any test class ending with "TCTest" or "TCIT" makes use of test container(s) - Docker and PostgreSQL & is a real integration test case
- Integration tests (i.e. do not use mocking) are in TransactionControllerIntegrationIT, ReferralRepositoryTCTest and TransactionControllerIntegrationTCIT
- Running any test code using Testcontainers requires Docker service & Docker Desktop to be running

A word on the Test Configuration (application-test.properties):
- If any test code/class is executed individually, it should be successfully executed
- However, if the whole test lot is run together (e.g. mvn clean install), set the test.mode=1 (to indicate 'integrated')
- The property value test.mode is referred in TransactionControllerIntegrationTCIT to toggle the Assert condition
- The default value of test.mode is 0 (indicates individual run) in the application-test.properties file

A word on the Application URLs:
- Use the URL http://localhost:8081/transactions (replace 8081 by any over-ridden port) to get or create transactions
- Sample payload to create a new transaction is:
  {
  "userId": "someUserId",
  "reference": "user comments",
  "amount": 9.90
  }
- and the expected response should be like below
  {
  "id": "c21957a8-b772-48a8-b51c-eadc1ba56adc",
  "userId": "someUserId",
  "timestamp": "2022-12-27T14:06+0530",
  "reference": "user comments",
  "amount": 9.90
  }

Credits/Acknowledgements:

- The project  would not have been possible without the guidance of many IT experts and their blogs
- Some key public resources referred during the project are:

1) Spring & Spring Boot articles on https://www.marcobehler.com 

2) Service mocking (Wiremock, JUnit): https://www.youtube.com/watch?v=xJs-KuEL-co (Green Learner - Youtube)

3) Testcontainers (Testcontainers, JUnit)
https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/
https://www.youtube.com/watch?v=VfwP3GOridU (Think about IT - Youtube)