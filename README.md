# Microservices Architecture with Eureka and API Gateway

This project demonstrates a microservices architecture using Spring Boot, Eureka for service discovery, and Spring Cloud Gateway for API routing and load balancing.

## Project Structure

The project consists of the following modules:

-   **eureka-server:** The Eureka Server for service registration and discovery.
-   **product:** The Product Service, managing product-related operations.
-   **order:** The Order Service, handling order-related operations.
-   **api-gateway:** The API Gateway, routing traffic to the Product and Order Services.

<pre>
springCloud/
├── eureka-server/
│   ├── src/main/java/...
│   ├── src/main/resources/application.properties
│   └── pom.xml
├── order/
│   ├── src/main/java/...
│   ├── src/main/resources/application.properties
│   └── pom.xml
├── product/
│   ├── src/main/java/...
│   ├── src/main/resources/application.properties
│   └── pom.xml
├── api-gateway/
│   ├── src/main/java/...
│   ├── src/main/resources/application.properties
│   └── pom.xml
└── pom.xml (Parent POM)
</pre>


## Technologies Used

-   **Spring Boot:** For building standalone microservices.
-   **Spring Cloud Netflix Eureka:** For service discovery.
-   **Spring Cloud Gateway:** For API routing and load balancing.
-   **Java 17:** Programming language.
-   **Maven:** Build tool.

## Prerequisites

-   Java 17 or higher
-   Maven

## Running the Applications

1.  **Clone the Repository:**

    ```bash
    git clone <repository_url>
    cd springCloud
    ```

2.  **Start the Eureka Server:**

    ```bash
    cd eureka-server
    mvn spring-boot:run
    ```

    The Eureka Server will run on `http://localhost:8761`.

3.  **Start the Product Service:**

    ```bash
    cd ../product
    mvn spring-boot:run
    ```

    The Product Service will run on `http://localhost:8080`.

4.  **Start the Order Service:**

    ```bash
    cd ../order
    mvn spring-boot:run
    ```

    The Order Service will run on `http://localhost:9090`.

5.  **Start the API Gateway:**

    ```bash
    cd ../api-gateway
    mvn spring-boot:run
    ```

    The API Gateway will run on `http://localhost:8081`.

6.  **Verify Eureka Registration:**

    Open your browser and navigate to `http://localhost:8761`. You should see the registered services: `product`, `order`, and `api-gateway`.

## API Gateway Routes

The API Gateway routes traffic as follows:

-   Requests to `/products/**` are routed to the Product Service.
-   Requests to `/orders/**` are routed to the Order Service.

## Testing the API

Use `curl` or Postman to test the API Gateway:

### Product Service

-   **Get all products:**

    ```bash
    curl http://localhost:8081/products
    ```

-   **Get a specific product:**

    ```bash
    curl http://localhost:8081/products/1
    ```

-   **Create a new product:**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"productId": 2, "productName": "New Product"}' http://localhost:8081/products
    ```

### Order Service

-   **Get all orders:**

    ```bash
    curl http://localhost:8081/orders
    ```

-   **Create a new order:**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"orderId": 1, "quantity": 10, "productId": 1}' http://localhost:8081/orders
    ```

## Application Details

### Eureka Server

-   **Port:** 8761
-   **Purpose:** Service registration and discovery.
-   **Configuration:** `eureka-server/src/main/resources/application.properties`

### Product Service

-   **Port:** 8080
-   **Purpose:** Manages product-related operations.
-   **Endpoints:**
    -   `/products` (GET, POST, PUT, DELETE)
    -   `/products/{productId}` (GET, PUT, DELETE)
-   **Configuration:** `product/src/main/resources/application.properties`

### Order Service

-   **Port:** 9090
-   **Purpose:** Handles order-related operations.
-   **Endpoints:**
    -   `/orders` (GET, POST)
-   **Configuration:** `order/src/main/resources/application.properties`

### API Gateway

-   **Port:** 8081
-   **Purpose:** Routes traffic to the Product and Order Services, load balancing.
-   **Routes:**
    -   `/products/**` -> Product Service
    -   `/orders/**` -> Order Service
-   **Configuration:** `api-gateway/src/main/resources/application.properties`

## Author

[Alozie Chijindu]

## License

[Your License]