# 🏠 Smart Home E-commerce Web Application

A full-stack Java-based e-commerce platform for smart home products, built using **Java Servlets, JSP, MySQL, and MongoDB**, with advanced **AI-powered features** such as product recommendation and automated customer support.

---

## 🚀 Features

### 👤 User Management

-   User registration and login system
-   Session-based authentication
-   Role-based access (Customer / Store Manager)

### 🛍️ Product Catalog

-   Browse products by category
-   View detailed product information
-   Search with auto-suggestions (AJAX-based)

### 🛒 Cart & Checkout

-   Add/remove products from cart
-   Session-based cart management
-   Secure checkout flow with order generation

### 📦 Order Management

-   View order history
-   Cancel orders
-   Delivery date estimation

### ⭐ Reviews System (MongoDB)

-   Submit product reviews
-   Store reviews in MongoDB
-   Semantic search on reviews using embeddings

### 🤖 AI-Powered Features

-   **Product Recommendation System**

    -   Uses embeddings to suggest similar products

-   **Smart Review Search**

    -   Finds semantically similar reviews

-   **Automated Customer Support**

    -   Upload product image + issue description
    -   AI decides: Refund / Replace / Escalate

### 📊 Admin Dashboard

-   Inventory management
-   Sales reports
-   Trending products (MongoDB aggregation)
-   Top zip code analytics

---

## 🛠️ Tech Stack

-   **Backend:** Java Servlets, JSP
-   **Frontend:** HTML, CSS, JavaScript, AJAX
-   **Database:** MySQL (transactions), MongoDB (reviews)
-   **Server:** Apache Tomcat
-   **AI Integration:** Embeddings-based similarity (OpenAI APIs)

---

## 📂 Project Structure

```
SmartHomeWebsite/
 ├── src/main/java/com/smarthome/   # Servlets, Services, Models
 ├── src/main/webapp/               # JSP, WEB-INF, static files
 ├── .gitignore
 ├── README.md
```

---

## ⚙️ How to Run (VS Code + Tomcat)

### 1. Compile the Project

```bash
cd main
javac -d webapp/WEB-INF/classes -classpath "$(find webapp/WEB-INF/lib -name '*.jar' | tr '\n' ':')" java/com/smarthome/**/*.java
```

---

### 2. Deploy to Tomcat

-   Rename `webapp` → `SmartHomeWebsite`
-   Copy into:

```
apache-tomcat/webapps/
```

---

### 3. Start Tomcat

```bash
cd apache-tomcat/bin
./startup.sh   # Mac/Linux
startup.bat    # Windows
```

---

### 4. Access Application

```
http://localhost:8080/SmartHomeWebsite/
```

---

## ⚠️ Configuration

Update database credentials in your connection file:

```java
String url = "jdbc:mysql://localhost:3306/your_db";
String username = "your_username";
String password = "your_password";
```

---

## 🧠 Key Highlights

-   Built a **complete e-commerce lifecycle** (Auth → Cart → Orders → Reports)
-   Integrated **AI-based recommendation and decision systems**
-   Used **hybrid database architecture (MySQL + MongoDB)**
-   Implemented **semantic search using embeddings**

---

## 📌 Future Improvements

-   Convert to **Spring Boot microservices architecture**
-   Add REST APIs
-   Deploy on AWS / Docker
-   Improve UI with modern frameworks (React)

---

## 👨‍💻 Author

**Koustav Majumder**

---

## ⭐ If you like this project

Give it a ⭐ on GitHub — it helps!
