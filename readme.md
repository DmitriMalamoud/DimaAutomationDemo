# QA Automation Demo Project

> _This README was generated with assistance from Claude (Sonnet 4.5) and reflects the current state of the project as of December 2025._

> **A test automation framework demonstrating CI/CD integration, containerized infrastructure, AI-assisted failure analysis, and comprehensive reporting practices.**

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![JUnit](https://img.shields.io/badge/JUnit-5-blue.svg)](https://junit.org/junit5/)
[![Maven](https://img.shields.io/badge/Maven-3.9.11-red.svg)](https://maven.apache.org/)
[![Jenkins](https://img.shields.io/badge/Jenkins-LTS--JDK21-yellow.svg)](https://www.jenkins.io/)
[![Allure](https://img.shields.io/badge/Allure-2.35.1-purple.svg)](https://docs.qameta.io/allure/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)](https://www.docker.com/)

---

## 📋 Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Running Tests](#running-tests)
- [CI/CD Pipeline](#cicd-pipeline)
- [Environment Configuration](#environment-configuration)
- [Reporting](#reporting)
- [LLM-Assisted Failure Analysis](#llm-assisted-failure-analysis)
- [Docker Infrastructure](#docker-infrastructure)
- [Jenkins Configuration](#jenkins-configuration)
- [Troubleshooting](#troubleshooting)
- [Future Improvements](#future-improvements)

---

## 🎯 Overview

This project is a QA automation framework built to demonstrate practical skills in:

- ✅ Spring Boot API integration testing
- ✅ Multi-environment test execution using YAML profiles
- ✅ Dockerized Jenkins CI/CD with Configuration as Code (JCasC)
- ✅ Allure reporting with step-level detail
- ✅ AI-powered test failure analysis using local LLM (Ollama + phi3)
- ✅ Tag-based test selection and parameterized builds
- ✅ Containerization and orchestration

The project demonstrates patterns commonly used in microservice testing environments.

---

## 🌟 Key Features

### 1. **Full-Stack Integration Testing**
- JUnit 5 tests against a live Spring Boot API
- Real HTTP requests/responses with validation
- Component scanning and Spring context management

### 2. **Environment-Driven Configuration**
- YAML-based profiles: `LOCAL`, `JENKINS`, `FAIL`
- Spring `@ConfigurationProperties` for configuration management
- Runtime profile selection via `spring.profiles.active`
- Integrated LLM configuration per environment

### 3. **AI-Enhanced Failure Diagnostics**
- Automatic LLM analysis of failed tests
- Root cause hypothesis, investigation steps, and fix suggestions
- Structured prompts with token limits for consistent output
- Analysis attached directly to Allure reports

### 4. **Containerized CI Infrastructure**
- Jenkins runs in Docker with JDK 21, Maven, and Allure pre-installed
- Ollama LLM container for local inference (phi3 model)
- Docker Compose orchestration with shared networking
- Persistent volumes for Jenkins home and Ollama models

### 5. **Comprehensive Reporting**
- Allure reports with test steps, attachments, and categorization
- JUnit XML reports for standard CI integration
- Clean workspace management between builds
- Build retention policies to prevent disk bloat

### 6. **Jenkins Configuration as Code (JCasC)**
- Automated Jenkins setup on container startup
- Pre-configured admin user, tools, and pipeline job
- Minimal manual configuration required
- Git repository auto-linked to pipeline

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Docker Compose                          │
│  ┌────────────────────┐         ┌─────────────────────┐    │
│  │   Jenkins          │◄───────►│   Ollama LLM        │    │
│  │   (JDK21+Maven)    │  CI-NET │   (phi3 model)      │    │
│  │   Port: 8080       │         │   Port: 11434       │    │
│  └────────┬───────────┘         └─────────────────────┘    │
│           │                                                  │
│           │ Triggers                                         │
│           ▼                                                  │
│  ┌─────────────────────────────────────────────────────┐   │
│  │           Maven Test Execution                       │   │
│  │  ┌────────────┐  ┌──────────────┐  ┌─────────────┐ │   │
│  │  │ JUnit 5    │─►│ Spring Boot  │─►│ Allure      │ │   │
│  │  │ Tests      │  │ API (Local)  │  │ Results     │ │   │
│  │  └────────────┘  └──────────────┘  └─────────────┘ │   │
│  │         │                                    │       │   │
│  │         │ On Failure                         │       │   │
│  │         ▼                                    │       │   │
│  │  ┌────────────────────┐                     │       │   │
│  │  │ TestListener       │                     │       │   │
│  │  │ (Failure Capture)  │                     │       │   │
│  │  └──────────┬─────────┘                     │       │   │
│  │             │                                │       │   │
│  │             │ Sends Prompt                   │       │   │
│  │             ▼                                │       │   │
│  │  ┌────────────────────┐                     │       │   │
│  │  │ LlmGateway         │                     │       │   │
│  │  │ POST /api/generate │                     │       │   │
│  │  └──────────┬─────────┘                     │       │   │
│  │             │                                │       │   │
│  │             │ Returns Analysis               │       │   │
│  │             ▼                                │       │   │
│  │  ┌────────────────────┐                     │       │   │
│  │  │ Allure Attachment  │◄────────────────────┘       │   │
│  │  │ (llm-analysis.txt) │                             │   │
│  │  └────────────────────┘                             │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Tech Stack

| Category | Technology | Version | Purpose |
|----------|-----------|---------|---------|
| **Language** | Java | 21 | Core development |
| **Build Tool** | Maven | 3.9.11 | Dependency management, build automation |
| **Framework** | Spring Boot | 3.x | API under test, configuration management |
| **Testing** | JUnit 5 | - | Test framework with tags and nested tests |
| **Reporting** | Allure | 2.35.1 | HTML reports with step-level detail |
| **CI/CD** | Jenkins | LTS (JDK21) | Automated pipeline execution |
| **Containerization** | Docker & Docker Compose | - | Infrastructure as code |
| **Configuration** | Jenkins JCasC | - | Automated Jenkins setup |
| **AI/ML** | Ollama (phi3) | - | Local LLM for failure analysis |
| **Version Control** | Git/GitHub | - | Source control |

---

## 📁 Project Structure

```
automation-demo/
├── docker/
│   ├── Dockerfile                         # Custom Jenkins image
│   ├── jenkins.yml                        # JCasC configuration
│   ├── docker-compose.yml                 # Multi-container orchestration
│   ├── plugins.txt                        # Jenkins plugins list
│   └── ollama-entrypoint.sh              # LLM container initialization
├── src/
│   ├── main/
│   │   ├── java/org/demo/
│   │   │   ├── SpringApplication.java     # Spring Boot main class
│   │   │   ├── apiserver/
│   │   │   │   ├── DemoApiController.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── StringApiRequestBody.java
│   │   │   └── testinfra/
│   │   │       ├── config/
│   │   │       │   ├── TestEnvConfig.java           # Environment config
│   │   │       │   ├── LlmConfig.java               # LLM config
│   │   │       │   └── ApplicationContextProvider.java
│   │   │       ├── apiutils/
│   │   │       │   ├── clients/
│   │   │       │   │   ├── TestApiClient.java
│   │   │       │   │   └── TestApiStringClient.java
│   │   │       │   ├── HttpClientProvider.java
│   │   │       │   └── ResponseBodyHandler.java
│   │   │       ├── assertionutils/
│   │   │       │   ├── AssertionUtil.java
│   │   │       │   ├── AssertType.java
│   │   │       │   └── TestFailureStateHandler.java
│   │   │       ├── llm/
│   │   │       │   ├── LlmGateway.java
│   │   │       │   ├── LlmTestFailPromptBuilder.java
│   │   │       │   ├── OllamaApiRequestPojo.java
│   │   │       │   ├── OllamaApiResponsePojo.java
│   │   │       │   └── test_fail_llm_prompt_template.txt
│   │   │       ├── Logger.java
│   │   │       ├── DateTimeUtils.java
│   │   │       └── GsonProvider.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── environments/
│   │           ├── application-local.yml
│   │           ├── application-jenkins.yml
│   │           └── application-fail.yml
│   └── test/
│       └── java/org/tests/
│           ├── BaseTest.java
│           ├── TestListener.java
│           ├── LlmTestHandler.java
│           └── textApi/
│               └── TextApiTests.java
├── Jenkinsfile                            # Declarative CI/CD pipeline
├── pom.xml                                # Maven build configuration
└── README.md                              # This file
```

---

## 📦 Prerequisites

- **Docker** (20.x or later) & **Docker Compose** (2.x or later)
- **Git** (for cloning the repository)
- (Optional) **Java 21** + **Maven 3.9.11** if running tests locally outside Docker

---

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/YourUsername/automation-demo.git
cd automation-demo
```

### 2. Start the Infrastructure

```bash
cd docker
docker-compose up -d
```

This will:
- Build the custom Jenkins image (with JDK, Maven, Allure pre-installed)
- Start Jenkins on `http://localhost:8080`
- Start Ollama LLM on `http://localhost:11434`
- Pull the `phi3` model for LLM inference
- Configure Jenkins via JCasC

### 3. Access Jenkins

1. Open your browser to **`http://localhost:8080`**
2. Login with:
    - **Username:** `admin`
    - **Password:** `admin`
3. The pipeline job **`test-automation-demo`** is already created

### 4. Run Your First Build

**Important:** On the first run, the job will not yet have parameters configured. The initial build will run with default settings and pull the Jenkinsfile from the repository. After this first build completes, the job will become fully parameterized.

**First Build:**
1. Click on **`test-automation-demo`** job
2. Click **"Build Now"** (parameters not yet available)
3. Wait for the build to complete

**Subsequent Builds:**
1. Click **"Build with Parameters"**
2. Select:
    - **Environment:** `JENKINS` (recommended for CI runs)
    - **TestGroup:** `All` or `Sanity`
    - **LLM_Analysis:** ✅ Enabled
3. Click **"Build"**

### 5. View Results

- **Console Output:** Build → Console Output
- **JUnit Results:** Build → Test Result
- **Allure Report:** Build → Allure Report
    - 💡 **Important:** Open Allure reports in **Incognito/Private mode** to avoid browser cache issues that can display stale data

---

## 🧪 Running Tests

### Locally (Without Docker)

```bash
# Run all tests with LOCAL profile
mvn clean test -Dspring.profiles.active=local

# Run specific test group
mvn clean test -Dspring.profiles.active=local -Dgroups=sanity

# Run with LLM analysis disabled
mvn clean test -Dspring.profiles.active=local -Dllm=false

# Generate and view Allure report
mvn allure:serve
```

### Via Jenkins

Use the parameterized pipeline:

```groovy
// Parameters available after first build:
// - Environment: [JENKINS, LOCAL, FAIL]
// - TestGroup: [All, Sanity, Fail Demo]
// - LLM_Analysis: [true, false]
```

The pipeline handles:
- Profile activation
- Tag-based test filtering
- LLM endpoint configuration
- Report generation
- Workspace cleanup

---

## 🔄 CI/CD Pipeline

### Jenkinsfile Stages

```groovy
pipeline {
    agent any
    
    tools {
        jdk 'jdk21'           // Pre-installed in Docker image
        maven 'maven-3.9.11'  // Pre-installed in Docker image
    }
    
    parameters {
        choice(name: 'Environment', ...)
        choice(name: 'TestGroup', ...)
        booleanParam(name: 'LLM_Analysis', ...)
    }
    
    stages {
        stage('Checkout') { /* SCM checkout */ }
        stage('Build + Test') { /* Maven execution */ }
    }
    
    post {
        always {
            allure results: [[path: 'target/allure-results']]
            junit 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}
```

### Pipeline Features

- ✅ **Parameterized:** Environment and test group selection
- ✅ **Fail-safe:** Uses `returnStatus: true` to ensure post-actions run
- ✅ **Build retention:** Keeps last 8 builds, auto-deletes older ones
- ✅ **Dynamic naming:** Builds labeled with environment (e.g., `#5 env_JENKINS`)

---

## 🌍 Environment Configuration

### YAML Profiles

Located in `src/main/resources/environments/`:

```yaml
# application-jenkins.yml
env:
  name: jenkins
  scheme: http
  host: localhost
  port: 8085

llm:
  api:
    scheme: http
    host: localhost
    port: 11434
    model: phi3
    endpoint: api/generate
    timeout_sec: 300
    num_predict: 350
  prompt:
    default_path: src/main/java/org/demo/testinfra/llm/test_fail_llm_prompt_template.txt
```

### Profile Selection

**In Maven:**
```bash
-Dspring.profiles.active=jenkins
```

**In Jenkins:**
Pipeline parameter `Environment` → automatically mapped to profile

### Configuration Classes

```java
@ConfigurationProperties(prefix = "env")
public class TestEnvConfig {
    private String name;
    private String scheme;
    private String host;
    private int port;
    // getters/setters
}

@ConfigurationProperties(prefix = "llm")
public class LlmConfig {
    private ApiConfig api;
    private PromptConfig prompt;
    // nested configuration classes
}
```

Tests inject these configs and build runtime URLs dynamically.

---

## 📊 Reporting

### Allure Reports

Allure provides:
- **Test suites and cases** with pass/fail/skip status
- **Step-by-step execution** with nested steps
- **Attachments** (logs, screenshots, LLM analysis)
- **Categories** (product defects, test defects, etc.)
- **Trends** across builds
- **Timeline view** for execution order

**Access:** Jenkins Build → **Allure Report** tab

**Note:** Due to aggressive browser caching of Allure assets, it's recommended to view reports in Incognito/Private browsing mode to ensure you see current data.

### JUnit XML Reports

Standard Surefire reports for CI integration:
- Located: `target/surefire-reports/*.xml`
- Published via `junit` pipeline step
- Visible in Jenkins → Test Result

### Report Artifacts

- **Allure JSON/XML:** `target/allure-results/`
- **JUnit XML:** `target/surefire-reports/`
- **LLM Analysis:** Attached to failed tests in Allure

---

## 🤖 LLM-Assisted Failure Analysis

### How It Works

1. **Test Failure Detection**
    - `TestListener` (JUnit extension) hooks into test lifecycle
    - Captures: test name, exception type, message, stack trace

2. **Prompt Construction**
    - Template loaded from `test_fail_llm_prompt_template.txt`
    - Variables injected: test name, error details, stack trace
    - Token limit enforced via `num_predict` parameter

3. **LLM Inference**
    - `LlmGateway` sends POST to `http://ollama-llm:11434/api/generate`
    - Model: `phi3` (lightweight, 8GB RAM compatible)
    - Parameters: `stream: false`, `num_predict: 350`

4. **Result Attachment**
    - LLM response saved as `llm-analysis.txt`
    - Attached to Allure test case
    - Visible in Allure → Test Details → Attachments

### Configuration

**Enable/Disable:**
```bash
# Maven
mvn test -Dllm=true

# Jenkins
Parameter: LLM_Analysis → true/false
```

**Custom LLM Endpoint:**
Configuration is read from the active YAML profile under `llm.api.*`

### Example Output

```
ROOT CAUSE: Authentication token expired or invalid.

INVESTIGATION:
1. Check if auth token is being refreshed properly
2. Verify token expiration time in test data
3. Review auth service logs for rejection reason

SUGGESTED FIX:
- Ensure test setup includes valid token generation
- Add token validation step before login test
- Consider using mock auth for unit tests
```

---

## 🐳 Docker Infrastructure

### docker-compose.yml

```yaml
version: '3.8'

services:
  jenkins:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: jenkins-with-allure
    ports:
      - "8080:8080"
      - "50000:50000"
    volumes:
      - jenkins_home:/var/jenkins_home
    networks:
      - ci-net

  ollama-llm:
    image: ollama/ollama:latest
    container_name: ollama-llm
    ports:
      - "11434:11434"
    volumes:
      - ollama:/root/.ollama
    networks:
      - ci-net

networks:
  ci-net:
    driver: bridge

volumes:
  jenkins_home:
  ollama:
```

### Custom Jenkins Image

**docker/Dockerfile:**
```dockerfile
FROM jenkins/jenkins:lts-jdk21

USER root

# Install Maven
RUN apt-get update && \
    apt-get install -y maven wget unzip && \
    rm -rf /var/lib/apt/lists/*

# Install Allure Commandline
ENV ALLURE_VERSION=2.35.1
RUN wget -qO- https://github.com/allure-framework/allure2/releases/download/${ALLURE_VERSION}/allure-${ALLURE_VERSION}.zip \
    | busybox unzip -d /opt - && \
    ln -s /opt/allure-${ALLURE_VERSION}/bin/allure /usr/local/bin/allure

# Disable setup wizard
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Copy JCasC configuration
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs/jenkins.yaml
COPY --chown=jenkins:jenkins jenkins.yml /var/jenkins_home/casc_configs/jenkins.yaml

# Install plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

USER jenkins
```

### Container Communication

- **Jenkins → Ollama:** `http://ollama-llm:11434` (via Docker network)
- **Local → Ollama:** `http://localhost:11434` (when running tests locally)

---

## ⚙️ Jenkins Configuration

### Configuration as Code (JCasC)

**docker/jenkins.yml:**

```yaml
jenkins:
  securityRealm:
    local:
      allowsSignup: false
      users:
        - id: "admin"
          password: "admin"
  
  authorizationStrategy:
    loggedInUsersCanDoAnything:
      allowAnonymousRead: false

tool:
  jdk:
    installations:
      - name: "jdk21"
        home: "/opt/java/openjdk"
  
  maven:
    installations:
      - name: "maven-3.9.11"
        home: "/opt/maven"
  
  allure:
    installations:
      - name: "allure"
        home: "/opt/allure"

jobs:
  - script: >
      pipelineJob('test-automation-demo') {
        definition {
          cpsScm {
            scm {
              git {
                remote {
                  url('https://github.com/YourUsername/automation-demo.git')
                }
                branch('*/master')
              }
            }
            scriptPath('Jenkinsfile')
          }
        }
      }
```

### Required Plugins

Installed automatically via `plugins.txt`:
- `workflow-aggregator` (Pipeline)
- `git`
- `junit`
- `allure-jenkins-plugin`
- `matrix-project` (Allure dependency)
- `configuration-as-code`

---

## 🐛 Troubleshooting

### Issue: Allure Report Shows Stale Data

**Cause:** Browser caching Allure assets aggressively.

**Solution:**
```
Open Jenkins in Incognito/Private mode
OR
Press Ctrl+Shift+R (hard refresh) in browser
OR
Disable cache in browser DevTools (Network tab)
```

### Issue: LLM Analysis Not Appearing

**Checks:**
1. Verify Ollama container is running: `docker ps | grep ollama`
2. Test LLM endpoint manually:
   ```bash
   curl -X POST http://localhost:11434/api/generate \
     -d '{"model": "phi3", "prompt": "Hello", "stream": false}'
   ```
3. Check Jenkins console output for LLM-related errors
4. Check the Allure attachment itself—it may contain exception information if LLM call failed
5. Verify `LLM_Analysis` parameter is set to `true`

### Issue: Jenkins Can't Find Maven/JDK

**Solution:**
Tools are pre-installed in the Docker image, not via Jenkins installers.

Verify in **Manage Jenkins → Global Tool Configuration**:
- **JDK:** `jdk21` → `/opt/java/openjdk`
- **Maven:** `maven-3.9.11` → `/opt/maven`
- **Allure:** `allure` → `/opt/allure`

### Issue: Tests Fail to Start Spring Boot App

**Check:**
1. Port 8085 is available (configured in YAML profiles)
2. Spring profile is correctly specified: `-Dspring.profiles.active=...`
3. YAML file exists for the specified profile in `src/main/resources/environments/`

---

## 🚀 Future Improvements

This project demonstrates core automation capabilities. In a real-world scenario, the following enhancements would be valuable:

### Infrastructure & Scalability
- **Dedicated Allure server:** Centralized report storage with dashboards and historical data
- **Thread-safe execution:** Refactor for parallel test execution (currently not thread-safe)
- **Modern CI/CD platform:** Consider migrating to GitHub Actions or similar cloud-native solutions
- **Scheduled execution:** Add nightly/on-demand pipeline triggers

### Security & Configuration
- **Secure credential management:** Store API keys in vault systems (currently needed for realistic API calls)
- **Full Spring dependency injection:** Eliminate manual instantiation with `new` in favor of complete Spring-managed beans

### Test Suite Expansion
- **Test organization:** As volume grows, organize tests into separate classes and suites by feature/module
- **Data management:** Implement test data factories and proper cleanup strategies
- **Mock services:** Add WireMock or similar for external dependency simulation

### Reporting & Analysis
- **Enhanced LLM model:** Upgrade from phi3 to Llama 3 or better for improved analysis quality
- **Historical analysis:** Collect and analyze test run history over time to identify patterns
- **Test analytics:** Track flakiness, duration trends, and failure patterns
- **Custom Allure categories:** Map failures to product areas for better triage

### Robustness
- **Retry logic:** Implement retry mechanisms for flaky tests
- **Health checks:** Verify API availability before test execution
- **Rate limiting:** Throttle API calls to avoid overwhelming services
- **Alert integration:** Add Slack/email notifications for critical failures

---

## 📝 License

This project is open-source and available under the MIT License.

---

## 👤 Author

**Dmitri Malamoud**
- GitHub: https://github.com/DmitriMalamoud
- LinkedIn: https://linkedin.com/in/dima-melamud-587a0799

---