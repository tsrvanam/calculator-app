# 🧮 Java Calculator Web Application

A full-stack **Java Web Application** built with **Servlets + JSP**, designed as a complete **DevOps pipeline practice project** covering Maven build, SonarQube code quality analysis, Nexus artifact repository, and Apache Tomcat deployment.

---

## 🎯 Project Purpose

This project is specifically designed to practice and demonstrate the following DevOps tools end-to-end:

| Tool | Purpose |
|------|---------|
| **Maven** | Build automation, dependency management, WAR packaging |
| **SonarQube** | Static code analysis, code quality & coverage reports |
| **Nexus Repository** | Artifact storage, WAR file versioning & distribution |
| **Apache Tomcat** | WAR deployment, servlet container, production server |
| **Git + GitHub** | Source code version control |

---

## 🏗️ Application Architecture

```
┌─────────────────────────────────────────────────────┐
│                   Browser (Client)                   │
└────────────────────┬────────────────────────────────┘
                     │ HTTP Request
┌────────────────────▼────────────────────────────────┐
│              Apache Tomcat Server                    │
│  ┌─────────────────────────────────────────────┐    │
│  │         calculator-app.war                   │    │
│  │                                             │    │
│  │  ┌──────────────┐   ┌───────────────────┐  │    │
│  │  │ Calculator   │   │ CalculatorApi     │  │    │
│  │  │ Servlet      │   │ Servlet (REST)    │  │    │
│  │  └──────┬───────┘   └────────┬──────────┘  │    │
│  │         │                    │              │    │
│  │  ┌──────▼────────────────────▼──────────┐  │    │
│  │  │        CalculatorEngine (Core Logic)  │  │    │
│  │  └──────────────────────────────────────┘  │    │
│  │                                             │    │
│  │  ┌──────────────┐   ┌───────────────────┐  │    │
│  │  │  JSP Views   │   │ CalculationResult │  │    │
│  │  │  (Frontend)  │   │ (Model)           │  │    │
│  │  └──────────────┘   └───────────────────┘  │    │
│  └─────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
calculator-app/
├── pom.xml                                        ← Maven build configuration
├── README.md
└── src/
    ├── main/
    │   ├── java/com/calculator/
    │   │   ├── model/
    │   │   │   └── CalculationResult.java         ← Result POJO model
    │   │   ├── servlet/
    │   │   │   ├── CalculatorServlet.java          ← Main UI servlet (/calculate)
    │   │   │   └── CalculatorApiServlet.java       ← REST JSON API (/api/calculate)
    │   │   └── util/
    │   │       └── CalculatorEngine.java           ← Core arithmetic logic
    │   └── webapp/
    │       ├── WEB-INF/
    │       │   ├── web.xml                         ← Deployment descriptor
    │       │   └── views/
    │       │       ├── calculator.jsp              ← Main calculator UI
    │       │       └── error.jsp                   ← Error page
    │       ├── css/style.css                       ← Dark theme stylesheet
    │       ├── js/calculator.js                    ← Client-side JS
    │       └── index.jsp                           ← Welcome redirect
    └── test/
        └── java/com/calculator/
            └── CalculatorEngineTest.java           ← 13 JUnit 5 unit tests
```

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 11 |
| Web Framework | Java Servlets 4.0 + JSP |
| Frontend | HTML5, CSS3, JavaScript |
| Template Engine | JSTL 1.2 |
| REST API | Jackson 2.15 (JSON) |
| Testing | JUnit 5 |
| Build Tool | Apache Maven 3.6+ |
| Server | Apache Tomcat 9.x |
| Code Quality | SonarQube |
| Artifact Repo | Nexus Repository Manager |

---

## 🚀 DevOps Pipeline Flow

```
Developer Push
      │
      ▼
┌─────────────┐
│   GitHub    │  ← Source Code Repository
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    Maven    │  ← mvn clean package
│    Build    │  ← Compiles, runs tests, creates WAR
└──────┬──────┘
       │
       ▼
┌─────────────┐
│  SonarQube  │  ← mvn sonar:sonar
│  Analysis   │  ← Code quality, bugs, vulnerabilities, coverage
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    Nexus    │  ← mvn deploy
│  Repository │  ← Stores calculator-app-1.0.0.war
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Tomcat    │  ← Deploy WAR
│   Server    │  ← Serves the application
└─────────────┘
```

---

## 🔧 Prerequisites

| Tool | Version | Installation |
|------|---------|-------------|
| Java JDK | 11+ | `sudo apt install openjdk-11-jdk` |
| Apache Maven | 3.6+ | `sudo apt install maven` |
| Apache Tomcat | 9.x | https://tomcat.apache.org |
| SonarQube | 9.x+ | https://www.sonarqube.org |
| Nexus Repository | 3.x | https://www.sonatype.com/products/nexus-repository |

---

## 🏃 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/calculator-app.git
cd calculator-app
```

### 2. Build the Project
```bash
mvn clean package
```

### 3. Run Tests
```bash
mvn test
```

### 4. Deploy to Tomcat
```bash
cp target/calculator-app.war /opt/tomcat/webapps/
/opt/tomcat/bin/startup.sh
```

### 5. Access the Application
```
http://localhost:8080/calculator-app/
```

---

## 🧪 Maven Commands

```bash
# Clean and compile
mvn clean compile

# Run unit tests only
mvn test

# Run a specific test
mvn test -Dtest=CalculatorEngineTest

# Package into WAR (skipping tests)
mvn clean package -DskipTests

# Full build with tests
mvn clean package

# Install to local Maven repo
mvn clean install

# Deploy to Nexus
mvn clean deploy

# SonarQube analysis
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=calculator-app \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=your_sonar_token
```

---

## 📊 SonarQube Integration

### Step 1 — Add SonarQube properties to `pom.xml`
```xml
<properties>
    <sonar.projectKey>calculator-app</sonar.projectKey>
    <sonar.projectName>Java Calculator App</sonar.projectName>
    <sonar.host.url>http://localhost:9000</sonar.host.url>
    <sonar.login>your_sonar_token_here</sonar.login>
    <sonar.java.source>11</sonar.java.source>
    <sonar.coverage.jacoco.xmlReportPaths>
        target/site/jacoco/jacoco.xml
    </sonar.coverage.jacoco.xmlReportPaths>
</properties>
```

### Step 2 — Add JaCoCo plugin for code coverage
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <goals><goal>prepare-agent</goal></goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>verify</phase>
            <goals><goal>report</goal></goals>
        </execution>
    </executions>
</plugin>
```

### Step 3 — Run SonarQube Analysis
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=calculator-app \
  -Dsonar.host.url=http://YOUR_SONAR_IP:9000 \
  -Dsonar.login=YOUR_SONAR_TOKEN
```

### Step 4 — View Results
```
http://YOUR_SONAR_IP:9000/dashboard?id=calculator-app
```

---

## 📦 Nexus Repository Integration

### Step 1 — Add distribution management to `pom.xml`
```xml
<distributionManagement>
    <repository>
        <id>nexus-releases</id>
        <url>http://YOUR_NEXUS_IP:8081/repository/maven-releases/</url>
    </repository>
    <snapshotRepository>
        <id>nexus-snapshots</id>
        <url>http://YOUR_NEXUS_IP:8081/repository/maven-snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

### Step 2 — Add Nexus credentials to `~/.m2/settings.xml`
```xml
<settings>
    <servers>
        <server>
            <id>nexus-releases</id>
            <username>admin</username>
            <password>your_nexus_password</password>
        </server>
        <server>
            <id>nexus-snapshots</id>
            <username>admin</username>
            <password>your_nexus_password</password>
        </server>
    </servers>
</settings>
```

### Step 3 — Deploy to Nexus
```bash
mvn clean deploy
```

### Step 4 — View Artifact in Nexus
```
http://YOUR_NEXUS_IP:8081/#browse/browse:maven-releases
```

---

## 🌐 Tomcat Deployment

### Manual Deployment
```bash
# Copy WAR to Tomcat webapps
sudo cp target/calculator-app.war /opt/tomcat/webapps/

# Restart Tomcat
sudo /opt/tomcat/bin/shutdown.sh
sudo /opt/tomcat/bin/startup.sh
```

### Using Tomcat Manager
```
URL:      http://YOUR_SERVER_IP:8080/manager/html
Username: admin
Password: admin
```

### Application URLs after Deployment
```
UI:   http://YOUR_SERVER_IP:8080/calculator-app/
API:  http://YOUR_SERVER_IP:8080/calculator-app/api/calculate
```

---

## 🔌 REST API Reference

### GET — Query Parameters
```bash
curl "http://localhost:8080/calculator-app/api/calculate?operand1=10&operator=%2B&operand2=5"
```

### POST — JSON Body
```bash
curl -X POST http://localhost:8080/calculator-app/api/calculate \
  -H "Content-Type: application/json" \
  -d '{"operand1": 10, "operator": "+", "operand2": 5}'
```

### Response
```json
{
  "success": true,
  "result": 15.0,
  "expression": "10 + 5 = 15"
}
```

### Supported Operations
| Operator | Operation | Example |
|----------|-----------|---------|
| `+` | Addition | 10 + 5 = 15 |
| `-` | Subtraction | 10 - 3 = 7 |
| `*` | Multiplication | 4 * 5 = 20 |
| `/` | Division | 10 / 4 = 2.5 |
| `%` | Modulo | 10 % 3 = 1 |
| `^` | Power | 2 ^ 8 = 256 |
| `sqrt` | Square Root | √16 = 4 |

---

## ✅ Unit Tests

**13 JUnit 5 tests** covering all operations:

```bash
mvn test
```

```
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

| Test | Scenario |
|------|----------|
| `testAddition` | 10 + 5 = 15 |
| `testSubtraction` | 10 - 3 = 7 |
| `testMultiplication` | 4 * 5 = 20 |
| `testDivision` | 10 / 4 = 2.5 |
| `testDivisionByZero` | Returns error |
| `testModulo` | 10 % 3 = 1 |
| `testPower` | 2 ^ 8 = 256 |
| `testSqrt` | √16 = 4 |
| `testSqrtNegative` | Returns error |
| `testPercentage` | 50% = 0.5 |
| `testUnknownOperator` | Returns error |
| `testDecimalAddition` | 1.5 + 2.5 = 4.0 |
| `testNegativeNumbers` | -5 + 3 = -2 |

---

## 🔍 Troubleshooting

| Problem | Fix |
|---------|-----|
| `BUILD FAILURE` in Maven | Run `mvn clean install -U` to refresh dependencies |
| WAR not deploying in Tomcat | Check logs: `tail -f /opt/tomcat/logs/catalina.out` |
| SonarQube connection refused | Ensure SonarQube is running on port 9000 |
| Nexus 401 Unauthorized | Check credentials in `~/.m2/settings.xml` |
| Port 8080 already in use | `sudo lsof -i :8080` then kill the process |
| Tomcat 10 ClassNotFound | Replace `javax.servlet` with `jakarta.servlet` in imports |

---

## 👤 Author

**Your Name**
- GitHub: [@your-username](https://github.com/your-username)
- LinkedIn: [your-linkedin](https://linkedin.com/in/your-profile)

---

## 📄 License

This project is licensed under the **MIT License**.

---

> ⭐ If this project helped you learn DevOps, please give it a star on GitHub!
