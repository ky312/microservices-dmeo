# Account Management Service

This project is a Spring Boot application for managing bank accounts, including creating accounts, retrieving account information, depositing money, withdrawing money, and deleting accounts. It integrates with a Customer Service to fetch customer details.

## Features

- Fetch all accounts
- Get Account details along with customer
- Deposit money into an account
- Withdraw money from an account
- Delete an account by account number

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven


### API Endpoints

#### Get All Accounts
**URL:** `/api/accounts`  
**Method:** `GET`  
**Response:**
- 200 OK: Returns a list of all accounts.

**cURL Request:**

```sh
curl -X GET "http://localhost:8080/api/accounts" -H "accept: application/json"
```
```
Response:
    {
        "status": 201,
        "message": "success",
        "data": [
            {
                "id": 1,
                "customerId": "CUST1",
                "accountNumber": "ACCKY889",
                "accountType": "SAVINGS",
                "balance": 1000.00
            },
            {
                "id": 2,
                "customerId": "CUST2",
                "accountNumber": "ACCRG432",
                "accountType": "CURRENT",
                "balance": 1500.00
            },
            {
                "id": 3,
                "customerId": "CUST3",
                "accountNumber": "ACCAK865",
                "accountType": "SAVINGS",
                "balance": 2000.00
            }
        ]
    }
```


#### Get Account details

**URL:** `/api/accounts/info/{accountNumber}`  
**Method:** `GET`   
**Response:**  
- 200 OK: Returns a account information along with customer it's customer information associated with it.

**cURL Request:**
```sh
curl -X GET "http://localhost:8080/api/accounts/info/ACCKY889" -H "accept: application/json"
```
```
Response:
     {
        "status": 200,
        "message": "success",
        "data": {
            "account": {
                "id": 1,
                "customerId": "CUST1",
                "accountNumber": "ACCKY889",
                "accountType": "SAVINGS",
                "balance": 1000.00
            },
            "customer": {
                "id": 1,
                "firstName": "Kaushal",
                "lastName": "Yadav",
                "email": "kaushal.yadav@mail.com",
                "phoneNumber": "9892041881"
            }
        }
    }
```


#### Add Money to account

**URL:** `/api/accounts/deposit`  
**Method:** `POST`  
**Request Body:** `AccountInfo`  
**Response:**
- 200 OK: Returns the updated account balance after deposit.

**cURL Request:**
```sh
curl -X POST "http://localhost:8080/api/accounts/deposit" -H "Content-Type: application/json" -d '{
    "accountNumber": "ACCKY889",
    "customerId": "CUST1",
    "amount": 5000
}'
```
```
Response:
    {
        "status": 200,
        "message": "success",
        "data": {
            "id": 1,
            "customerId": "CUST1",
            "accountNumber": "ACCKY889",
            "accountType": "SAVINGS",
            "balance": 6000.00
        }
    }
```


#### Withdraw money from account

**URL:** `/api/accounts/withdraw`  
**Method:** `POST`  
**Request Body:** `AccountInfo`  
**Response:**
- 200 OK: Returns the updated account balance after withdraw.

**cURL Request:**
```sh
curl -X POST "http://localhost:8080/api/accounts/withdraw" -H "Content-Type: application/json" -d '{
    "accountNumber": "ACCKY889",
    "customerId": "CUST1",
    "amount": 5000
}'
```
```
Response:
    {
        "status": 200,
        "message": "success",
        "data": {
            "id": 1,
            "customerId": "CUST1",
            "accountNumber": "ACCKY889",
            "accountType": "SAVINGS",
            "balance": 1000.00
        }
    }
```

#### Delete Account by Account Number

**URL:** `/api/accounts/delete/{accountNumber}`  
**Method:** `DELETE`  
**Response:**
- 204 NO CONTENT: Confirms account deletion.

**cURL Request:**
```sh
curl -X DELETE "http://localhost:8080/api/accounts/delete/ACCKY889" -H "accept: application/json"
```