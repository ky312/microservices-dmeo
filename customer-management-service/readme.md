# Customer Management Service

This project is a Spring Boot application for managing customers, including creating customers, retrieving customer information, updating customer details, and deleting customers. It integrates with an Account Service to create customer accounts.

## Features

- Create a new customer
- Retrieve customer information by ID
- Update customer details
- Delete a customer by ID along with its account
- Fetch all customers

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### API Endpoints

##### Get All Customers

**URL:** `/api/customers`  
**Method:** `GET`  
**Response:**
- 200 OK: Returns a list of all customers.

**cURL Request Syntax:**
```sh
curl -X GET "http://localhost:8080/api/customers" -H "accept: application/json"
```
```
Response:
    {
        "status": 200,
        "message": "success",
        "data": [
            {
                "id": 1,
                "firstName": "Kaushal",
                "lastName": "Yadav",
                "email": "kaushal.yadav@mail.com",
                "phoneNumber": "9892041881"
            },
            {
                "id": 2,
                "firstName": "Raj",
                "lastName": "Gupta",
                "email": "raj.gupta@mail.com",
                "phoneNumber": "7756867432"
            },
            {
                "id": 3,
                "firstName": "Amit",
                "lastName": "Kumar",
                "email": "amit.kumar@mail.com",
                "phoneNumber": "9920987865"
            }
        ]
    }
```

#### Get Single Customer Details

**URL:** `/api/customers/{id}`  
**Method:** `GET`   
**Response:**
- 200 OK: Returns a customer information.

**cURL Request:**
```sh
curl -X GET "http://localhost:8080/api/customers/1" -H "accept: application/json"
```
```
Response:
    {
        "status": 200,
        "message": "success",
        "data": {
            "id": 1,
            "firstName": "Kaushal",
            "lastName": "Yadav",
            "email": "kaushal.yadav@mail.com",
            "phoneNumber": "9892041881"
        }
    }
```

#### Create Customer

**URL:** `/api/customers`  
**Method:** `POST`  
**Request Body:** `Customer`  
**Response:**
- 201 CREATED: Returns the created customer.

**cURL Request:**
```sh
curl -X POST "http://localhost:8080/api/customers" -H "Content-Type: application/json" -d '{
    "firstName": "Kamal",
    "lastName": "Kumar",
    "email": "kamal.kumar@mail.com",
    "phoneNumber": "843911245"
}'
```
```
Response:
    {
        "status": 201,
        "message": "success",
        "data": {
            "customer": {
                "id": 4,
                "firstName": "Kamal",
                "lastName": "Kumar",
                "email": "kamal.kumar@mail.com",
                "phoneNumber": "843911245"
            },
            "account": {
                "id": 4,
                "customerId": "CUST4",
                "accountNumber": "ACC161234567012800",
                "accountType": "SAVING",
                "balance": 0
            }
        }
    }
```

#### Update Customer

**URL:** `/api/customers/{id}`  
**Method:** `PUT`  
**Request Body:** `Customer`  
**Response:**
- 200 OK: Returns the updated customer.

**cURL Request:**
```sh
curl -X PUT "http://localhost:8080/api/customers/1" -H "Content-Type: application/json" -d '{
    "id": 1,
    "firstName": "Kaushal",
    "lastName": "Yadav",
    "email": "kaushal.yadav@mail.com",
    "phoneNumber": "9892008000"
}'
```
```
Response:
    {
        "status": 200,
        "message": "success",
        "data": {
            "id": 1,
            "firstName": "Kaushal",
            "lastName": "Yadav",
            "email": "kaushal.yadav@mail.com",
            "phoneNumber": "9892008000"
        }
    }
```


#### Delete Customer

**URL:** `/api/customers/{id}`  
**Method:** `DELETE`   
**Response:**
- 204 NO CONTENT: Confirms customer deletion.

**cURL Request:**
```sh
curl -X DELETE "http://localhost:8080/api/customers/1" -H "accept: application/json"
```
