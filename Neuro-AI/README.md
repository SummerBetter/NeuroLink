# Neuro-AI

## 1. Introduction

**Neuro-AI** is a Spring Boot application that serves as the AI module for the NeuroLink project. It provides a simple RESTful API to interact with a backend AI model, supporting both standard and streaming chat responses. This service is built using Spring AI and is pre-configured to connect with an [Ollama](https://ollama.ai/) model.

## 2. Features

-   **Simple REST API**: Easy-to-use endpoints for AI chat interactions.
-   **Blocking and Streaming**: Offers both a simple request-response endpoint and a streaming endpoint for real-time chat.
-   **Ollama Integration**: Seamlessly connects to a local Ollama instance for AI model inference.
-   **Easy Configuration**: Key settings like server port and Ollama model details are managed in the `application.yaml` file.

## 3. Prerequisites

Before running this application, ensure you have the following installed:

-   **Java 17** or later
-   **Apache Maven**
-   **Ollama**: The application requires a running Ollama instance. You can download it from [ollama.ai](https://ollama.ai/).

## 4. Getting Started

### Configuration

1.  **Ollama Setup**:
    -   After installing Ollama, pull the required model by running:
        ```sh
        ollama pull deepseek-r1:7b
        ```
    -   Ensure the Ollama server is running. The application expects it to be available at `http://localhost:11434`.

2.  **Application Configuration**:
    -   The main configuration is located in `src/main/resources/application.yaml`.
    -   You can modify the `server.port`, Ollama `base-url`, and the chat `model` as needed.

    ```yaml
    server:
      port: 8081
    spring:
      application:
        name: neuro-ai
      ai:
        ollama:
          base-url: http://localhost:11434
          chat:
            model: deepseek-r1:7b
    ```

### Running the Application

1.  **Clone the repository**:
    ```sh
    git clone <repository-url>
    cd NeuroLink/Neuro-AI
    ```

2.  **Build and Run with Maven**:
    -   You can run the application using the Spring Boot Maven plugin:
        ```sh
        mvn spring-boot:run
        ```
    -   The application will start on port `8081`.

## 5. API Endpoints

The API is exposed under the base path `/ai`.

### 5.1. Standard Chat

-   **Endpoint**: `GET /ai/chat`
-   **Description**: Sends a prompt to the AI model and waits for the full response.
-   **Query Parameters**:
    -   `prompt` (string, required): The user's message or question.
-   **Example Request**:
    ```sh
    curl "http://localhost:8081/ai/chat?prompt=Hello, who are you?"
    ```
-   **Example Response**:
    ```json
    "I am a large language model."
    ```

### 5.2. Streaming Chat

-   **Endpoint**: `GET /ai/chatStream`
-   **Description**: Sends a prompt and receives the AI's response as a continuous stream of text chunks.
-   **Query Parameters**:
    -   `prompt` (string, required): The user's message or question.
-   **Example Request**:
    ```sh
    curl "http://localhost:8081/ai/chatStream?prompt=Tell me a short story."
    ```
-   **Example Response**:
    -   The server will return a stream of text events (`text/event-stream`).
    ```
    data:Once
    data: upon
    data: a
    data: time...
    ```
