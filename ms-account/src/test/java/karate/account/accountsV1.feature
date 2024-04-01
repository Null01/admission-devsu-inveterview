# Created by andres at 23/03/24

Feature: Create accounts and transactions

  Background:
    * def utility = Java.type('karate.utilities.KarateUtility')
    * url baseUrl
    * def accountNumber = utility.generateRandomNumber(8)

  Scenario: Create account of type CORRIENTE
    * def accountRequest =
    """
    {
      "accountNumber": 0,
      "accountType": "CORRIENTE",
      "balance": 12345,
      "clientId": "4a0c75e2-424a-46e3-9856-67e00cbdfb47",
      "status": true
    }
    """
    * set accountRequest.accountNumber = accountNumber

    Given path '/api/cuentas/v1'
    And request accountRequest
    When method POST
    Then status 200
    And match response.status == "OK"
    And match response.data contains {id:"#notnull",createdAt:"#notnull"}

  Scenario: Create transaction for before account created.
    * def transactionRequest = { accountNumber: #(accountNumber), amount: 12345, transactionType: "DEPOSITO" }

    Given path '/api/movimientos/v1'
    And request transactionRequest
    When method POST
    Then status 200
    And match $.status == "OK"
    And match $.data contains {id:"#notnull"}