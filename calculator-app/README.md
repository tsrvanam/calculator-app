# Java Calculator Web Application

A full-stack calculator app built with **Java Servlets + JSP**, packaged as a **WAR** file using **Maven**, and deployed on **Apache Tomcat**.

---

## Project Structure

```
calculator-app/
├── pom.xml                                         ← Maven build config
└── src/
    ├── main/
    │   ├── java/com/calculator/
    │   │   ├── model/
    │   │   │   └── CalculationResult.java          ← Result model
    │   │   ├── servlet/
    │   │   │   ├── CalculatorServlet.java          ← Main web servlet
    │   │   │   └── CalculatorApiServlet.java       ← REST JSON API
    │   │   └── util/
    │   │       └── CalculatorEngine.java           ← Core logic
    │   └── webapp/
    │       ├── WEB-INF/
    │       │   ├── web.xml                         ← Deployment descriptor
    │       │   └── views/
    │       │       ├── calculator.jsp              ← Main UI
    │       │       └── error.jsp                   ← Error page
    │       ├── css/style.css
    │       ├── js/calculator.js
    │       └── index.jsp                           ← Welcome redirect
    └── test/
        └── java/com/calculator/
            └── CalculatorEngineTest.java           ← JUnit 5 tests
```

---

## Prerequisites

| Tool         | Version     |
|--------------|-------------|
| Java JDK     | 11 or later |
| Apache Maven | 3.6+        |
| Apache Tomcat | 9.x or 10.x |

---

## Build the WAR File

```bash
# Step 1: Navigate to the project root
cd calculator-app

# Step 2: Compile and run tests
mvn clean test

# Step 3: Package into a WAR file
mvn clean package

# The WAR is generated at:
# target/calculator-app.war
```

---

## Deploy to Apache Tomcat

### Option A — Copy WAR to Tomcat (Manual)

```bash
# 1. Copy the WAR to Tomcat's webapps directory
cp target/calculator-app.war /opt/tomcat/webapps/

# 2. Start Tomcat (if not already running)
/opt/tomcat/bin/startup.sh          # Linux/Mac
C:\tomcat\bin\startup.bat           # Windows

# 3. Access the app
open http://localhost:8080/calculator-app
```

### Option B — Tomcat Manager (GUI)

1. Open `http://localhost:8080/manager/html` in your browser.
2. Log in (configure credentials in `conf/tomcat-users.xml`):
   ```xml
   <role rolename="manager-gui"/>
   <user username="admin" password="admin" roles="manager-gui"/>
   ```
3. Under **WAR file to deploy**, select `target/calculator-app.war`.
4. Click **Deploy**.

### Option C — Embedded Tomcat via Maven Plugin

```bash
# Run directly without Tomcat installation
mvn tomcat7:run

# Access at: http://localhost:8080/calculator
```

---

## Application URLs

| URL | Description |
|-----|-------------|
| `http://localhost:8080/calculator-app/` | Calculator UI (redirects to /calculate) |
| `http://localhost:8080/calculator-app/calculate` | Main calculator page |
| `http://localhost:8080/calculator-app/api/calculate` | REST JSON API |

---

## REST API Examples

### GET Request (Query Parameters)
```bash
# Addition
curl "http://localhost:8080/calculator-app/api/calculate?operand1=10&operator=%2B&operand2=5"

# Division
curl "http://localhost:8080/calculator-app/api/calculate?operand1=20&operator=%2F&operand2=4"

# Square Root
curl "http://localhost:8080/calculator-app/api/calculate?action=sqrt&operand1=25"
```

### POST Request (JSON Body)
```bash
curl -X POST http://localhost:8080/calculator-app/api/calculate \
  -H "Content-Type: application/json" \
  -d '{"operand1": 15, "operator": "*", "operand2": 4}'
```

### JSON Response
```json
{
  "success": true,
  "result": 60.0,
  "expression": "15 * 4 = 60"
}
```

---

## Supported Operations

| Operator | Operation     | Example          |
|----------|--------------|------------------|
| `+`      | Addition      | 10 + 5 = 15      |
| `-`      | Subtraction   | 10 - 3 = 7       |
| `*`      | Multiplication| 4 * 5 = 20       |
| `/`      | Division      | 10 / 4 = 2.5     |
| `%`      | Modulo        | 10 % 3 = 1       |
| `^`      | Power         | 2 ^ 8 = 256      |
| `√`      | Square Root   | √16 = 4          |
| `%`(API) | Percentage    | 50% = 0.5        |

---

## Run Unit Tests

```bash
mvn test
```

Tests cover: addition, subtraction, multiplication, division, division-by-zero,
modulo, power, square root, percentage, negative numbers, and decimals.

---

## Common Issues & Fixes

| Problem | Fix |
|---------|-----|
| Port 8080 in use | Edit `conf/server.xml` → change `<Connector port="8080"` to `8081` |
| 404 on deploy | Verify the WAR name matches the context path |
| ClassNotFound in Tomcat | Ensure `javax.servlet-api` scope is `provided` in pom.xml |
| Tomcat 10 compatibility | Change `javax.servlet` to `jakarta.servlet` in imports |

---

## Tomcat 10 Migration Note

If you use **Tomcat 10+**, replace the `javax.*` imports with `jakarta.*`:

```xml
<!-- pom.xml change for Tomcat 10 -->
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>5.0.0</version>
    <scope>provided</scope>
</dependency>
```

And update all servlet imports in Java files:
```java
// Tomcat 9:  import javax.servlet.*;
// Tomcat 10: import jakarta.servlet.*;
```
