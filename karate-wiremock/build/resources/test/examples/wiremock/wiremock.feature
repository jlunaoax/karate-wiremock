@wiremock
Feature: Sample Karate - Wiremock

Background:
* url 'http://localhost:8080'

Scenario: Testing valid GET endpoint
Given path 'user/get'
When method GET
Then status 200

Scenario: Testing the exact response of a GET endpoint
Given path 'user/get'
When method GET
Then status 200
And match $ == {id:"1234",name:"John Smith"}

Scenario: Testing that GET response contains specific field
Given url 'http://localhost:8080/user/get'
When method GET
Then status 200
And match $ contains {id:"1234"}

Scenario: Testing a POST endpoint with request body
Given url 'http://localhost:8080/user/create'
And request { id: '1234' , name: 'John Smith'}
When method POST
Then status 200
And match $ contains {id:"#notnull"}

