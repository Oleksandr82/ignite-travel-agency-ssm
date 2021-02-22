# Travel Agency Demo application

Source code in this repository is to support a Spring Sate Machine presentation.
The idea behind the project is to show how SSM can be used to implement a [Distributed Saga](https://github.com/aphyr/dist-sagas/blob/master/sagas.pdf) 
and orchestrate a business transaction spanning multiple microservices.

# Implementation notes
1. The project is implemented as a multi-module maven project to simplify development (in real life I would expect 
   that each service is implemented by a separate team as a standalone project, or perhaps it is 3rd party services, 
   and BOM to be defined on the organization/tribe level that reflects common versions of the most important
   dependencies. It also can be the case that BOM is not used at all). 
2. DTOs are implemented separately in each service. In real life it may be implemented as a schema registry 
   (if there is a centralized API management), or by each team independently. 
3. DTOs are oversimplified. They do not represent real business objects and are needed just to represent the data flows.

# API

The workflow is set up in the following way:
- book a flight, if it is successful:
- book a hotel, if it is successful:
- book a car, if it is successful:
- if it is successful, return the overall result,
- if there is an error on any of the stage, try cancelling previous bookings and return 'not found'.

Value of parameters and overall result:

| flightCode | hotelName | carName | Result |
|---|---|---|---|
| KL1009 | TULIP INN | Fiat | OK |
| KL0000 | TULIP INN | Fiat | NOK, cancel on a flight |
| KL1009 | Some Hotel | Fiat | NOK, cancel on a hotel after the flight is booked |
| KL1009 | TULIP INN | NOT Fiat | NOK, cancel on a car after the flight and the hotel are booked |

# How to run
## Option 1
- Navigate to the ./docker folder and run > docker-compose up there. It will spin up ActiveMQ, Zipkin and MySQL
- Run travel-agency-eureka service
- Wait a minute before the services will spin up
- Run travel-agency-config-server
- Run travel-agency-api-gateway
- Run hotel-reservation-service
- Run flight-reservation-service
- Finally, run travel-agent-service

## Option 2
WIP. Please note, the following steps are in WIP phase and not available yet
- TODO: build docker images for all services in the project
- TODO: Navigate to the ./docker folder and run > docker-compose with docker-compose-all.yml up there.
- Wait a couple of minutes until the system will spin up all services 

# Services

## Infrastructural services
| Service name | Port | Comments |
|---|---|---|
| travel-agency-eureka | 8761 | Service Discovery |
| travel-agency-api-gateway | 9090 | API Gateway |
| travel-agency-config-server | 8888 | Externalized configuration |
| Zipkin | 9411 | Distributed tracing |
| ActiveMQ | 8161, 61616 | JMS |
| Mongo | 27017 | | 
| Mongo Express | 8091 | Mongo Admin UI |
| MySQL | 3306 | RDBMS |

## Business Services
| Service name | Port | Comments |
|---|---|---|
| hotel-reservation-service | 8081 | Hotel Reservation Service |
| flight-reservation-service | 8082 | Flight Reservation Service |
| car-rental-service | 8083 | Car Rental Service |

## Orchestration
| Service name | Port | Comments |
|---|---|---|
| travel-agent-service | 8080 | Orchestrator |