# 🤖 JokeGPT

JokeGPT is a playful, AI-powered web application built with Spring Boot that generates jokes based on user prompts. It uses the OpenRouter API to deliver witty, emoji-filled humor through a clean Thymeleaf interface.

---

## 🚀 Features

- Generate jokes using AI based on any topic
- Responsive UI built with Thymeleaf
- External CSS for styling
- Robust error handling with custom exceptions
- Modular architecture with clear separation of concerns
- DevTools support for hot reload during development

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 3.5.5
- Thymeleaf
- Maven
- OpenRouter API
- Gson (for JSON parsing)


---

## ⚙️ Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/jokes-gpt.git
   cd jokes-gpt

2. **Configure your API keys Add the following to your application.properties**
openrouter.url=https://openrouter.ai/api/v1/chat/completions
openrouter.key=your_api_key_here
openrouter.model.name=openai/gpt-oss-20b:free 

3. **Run the application**

bash
mvn spring-boot:run
Access the app Open your browser and go to: http://localhost:8080
