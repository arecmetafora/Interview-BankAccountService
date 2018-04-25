# Interview-BankAccountService

Interview Assignment - REST services for bank account creation and money transfers

The scope of this project consists of:

 - Building a Java web service for basic bank account operations, like creating a new bank account with some balance and transfering money between two bank accounts.

Use the following command to build and start the service:

    ./gradlew bootRun

The service is deployed at:

> https://ingenico-assignment.herokuapp.com/

## Creating new accounts

The method **account/new** (PUT) is used to create new bank accounts.
It receives an *Account* object serialized as JSON in the request body and returns the account number (plain text).

Here is an example of an *Account* information:
    
    {
    	"owner": {
    		"documentNumber": "235.574.577-08",
    		"firstName": "Alexander",
    		"lastName": "Rocha",
    		"phoneNumber": "+55 (11) 98578-1257",
    		"email": "arec.metafora@gmail.com",
    		"address": "Rua Gomes de Carvalho, 2457, Sao Paulo, SP, Brazil"
    	},
    	"type": "CHECKING",
    	"balance": 5000
    }

- **owner**: Information of the person who the bank account is being created for.
    - **documentNumber**: National identification number
    - **firstName**: Person's first name
    - **lastName**: Person's last name
    - **phoneNumber**: Person's phone number
    - **email**: Person's e-mail
    - **address**: Person's address (street, city, country)
- **type**: Bank account type. Either CHECKING or SAVING.
- **balance**: Initial account balance. Can not be negative.

All the parameters, except owner's phone, email and address are required. If they are missing, the service will return an HTTP 400 response with the error message.

If the balance is negative, another HTTP 400 response will be sent with a proper message.

### Example

    curl -X PUT \
      https://ingenico-assignment.herokuapp.com/account/new \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -d '{
    	"owner": {
    		"documentNumber": "235.574.577-08",
    		"firstName": "Alexander",
    		"lastName": "Rocha",
    		"phoneNumber": "+55 (11) 98578-1257",
    		"email": "arec.metafora@gmail.com",
    		"address": "Rua Gomes de Carvalho, 2457, Sao Paulo, SP, Brazil"
    	},
    	"type": "CHECKING",
    	"balance": 5000
    }'

## Transfer money between accounts

The method **account/transfer** (PUT) is used to create accounts transfers.
It receives a *Trasfer* object serialized as JSON in the request body and returns the transfer receipt number (plain text).

Here is an example of an *Trasfer* information:

    {
    	"origin": "1000-7",
    	"destiny": "1001-5",
    	"amount": 4000 
    }
- **origin**: The account which the money is being debited
- **destiny**: The account which the money is being credited
- **amount**: The transfer amount

All the parameters are required. If they are missing, the service will return an HTTP 400 response with the error message.

If some of the account numbers does not match an existent account in the system, an HTTP 400 is returned with a message.

The origin account can not be overdrawn. If the balance after the transfer is negative, another HTTP 400 response will be sent with a proper message.

### Example

    curl -X PUT \
      https://ingenico-assignment.herokuapp.com/account/transfer \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -d '{
    	"origin": "1000-7",
    	"destiny": "1001-5",
    	"amount": 4000 
    }'

## Accounts and transactions list (debug)

The method **account/list** (GET) was created just to easy the development and tests. It returns a collection of all registered accounts and the transactions made. You can use it to test the system.


## Technical overview

To develop the service I chose the [Spring + Spring Boot](https://spring.io/) framework, at the top of the [Gradle](https://gradle.org/)  build system, using the IDE [IntelliJ](https://www.jetbrains.com/idea/) and this GitHub as repository.

I also configured the [Heroku](https://www.heroku.com/) could platform to host the service. It took me just a few clicks to bind a project to this GitHub repo. So, every pull request this repository receives, the project is build and deployed in the cloud.

The project is divided in three simple parts, using the MVC paradigm:

- **Model**: Serializable entities used to send and receive messages from clients
- **Controller**: the *AccountService* contains all the service requests. It could delegate work to proper bll classes, but I decided to keep it simple for this assignment. Also, there is a *Database* controller entity to stablish connection with a fake and very simple in-memory repository.
- **DAOs**: Classes to hold the in-memory database structure. Could be parte of the controller BLLs if the service was linked to a real database.

In a nutshell, requests are sent to the *AccountService* class, which validates the parameters (sending HTTP 400 in case of business rule violations), retrieving data from or storing data to a "in-memory database" using the *Database* controller class, which requests the information from the *DAO* responsible for storing the requested entity data.

## Missing features

There are some features which were excluded from this project because of his scope, but I think it worth it to give an overview of them, since they would take part in a major role in a real project.

- All methods miss authentication control. A reliable authentication service should've been created to stablish a secure connection with the client, control the session and implement security measures to avoid undesired requests.
- This project miss a database to hold the application data. Choosing a database (SQL or NoSQL) is very important and when should I use one or another depends on which information is being stored and to which reason it will be used
- A proper error handling was not implemented for this project. It would've returned a very consistent and meaningful information, containing clear error codes to ease integration with other systems.
