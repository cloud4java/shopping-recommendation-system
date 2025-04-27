A Spring Boot application that uses Spring AI with OpenAI to generate personalized shopping recommendations based on user preferences and purchase history.

## Features

- Personalized product recommendations based on user interests, budget, and preferred categories
- Consideration of purchase history to avoid recommending similar products
- Detailed explanations for why products are recommended
- REST API for integration with other systems
- Command-line demo showcasing recommendations for different user personas

## Technologies Used

- Spring Boot 3.4.4
- Spring AI 1.0.0-M7
- OpenAI API
- Java 17+

## Prerequisites

- Java 17 or higher
- Maven
- OpenAI API key

## Setup

1. Clone the repository
2. Set your OpenAI API key as an environment variable:
   ```
   export OPENAI_API_KEY=your_api_key_here
   ```
   (On Windows, use `set OPENAI_API_KEY=your_api_key_here`)

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

## API Endpoints

### Generate Recommendations

```
POST /api/recommendations
```

Request body example:
```json
{
  "userId": "user123",
  "interests": ["technology", "gadgets", "programming"],
  "purchaseHistory": [
    {
      "productId": "p001",
      "productName": "Wireless Headphones",
      "category": "electronics",
      "price": 129.99,
      "purchaseDate": "2023-10-15"
    }
  ],
  "budget": 500.0,
  "preferredCategories": ["electronics", "computers"]
}
```

Response example:
```json
{
  "userId": "user123",
  "recommendedProducts": [
    {
      "productId": "rec001",
      "productName": "Mechanical Keyboard",
      "category": "computers",
      "price": 89.99,
      "reasonForRecommendation": "Based on your interest in programming and technology, a mechanical keyboard would enhance your coding experience."
    }
  ],
  "reasonForRecommendation": "These products align with your interests in technology and programming, and are within your budget of $500."
}
```

### Health Check

```
GET /api/recommendations/health
```

Response:
```
Shopping Recommendation Service is running!
```

## Demo

The application includes a command-line demo that showcases recommendations for three different user personas:
1. Tech enthusiast
2. Fitness enthusiast
3. Home cook

The demo runs automatically when the application starts.

## Project Structure

- `model`: Contains the data models for products, recommendations, and requests
- `service`: Contains the recommendation service that interacts with OpenAI
- `controller`: Contains the REST API endpoints
- `util`: Contains utility classes like the sample data provider

## License

This project is licensed under the MIT License - see the LICENSE file for details.
