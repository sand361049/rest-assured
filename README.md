# REST Assured API Testing Framework

A comprehensive REST Assured API testing framework demonstrating various HTTP methods and testing scenarios using [httpbin.org](https://httpbin.org/).

---

## 📋 Table of Contents
1. [REST Assured Setup](#-rest-assured-setup)
2. [About REST Assured Framework](#-about-rest-assured-framework)
3. [Test Functions & Examples](#-test-functions--examples)
4. [How to Run Tests](#-how-to-run-tests)

---

## 🚀 REST Assured Setup

### Prerequisites
- **Java 21** or higher
- **Maven 3.6+** or higher
- **Internet connection** (to access httpbin.org APIs)

### Step 1: Clone the Repository
```bash
git clone git@github.com:sand361049/rest-assured.git
cd rest-assured
```

### Step 2: Maven Dependencies
The project uses the following key dependencies in `pom.xml`:

```xml
<!-- REST Assured for API Testing -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
</dependency>

<!-- TestNG for Test Framework -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.9.0</version>
</dependency>

<!-- Jackson for JSON Processing -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.16.1</version>
</dependency>

<!-- Hamcrest for Assertions -->
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest</artifactId>
    <version>2.2</version>
</dependency>
```

### Step 3: Install Dependencies
```bash
mvn clean install
```

### Step 4: Verify Setup
```bash
mvn --version
java --version
```

---

## 📚 About REST Assured Framework

**REST Assured** is a Java library that simplifies testing and validation of REST APIs. It provides a domain-specific language (DSL) for writing powerful, maintainable tests for RESTful APIs.

### Key Features:
- **BDD-Style Syntax**: Uses Given-When-Then pattern for readable tests
- **Easy Request Building**: Simple methods to add headers, parameters, body, authentication
- **Powerful Response Validation**: Built-in matchers for status codes, headers, body content
- **JSON/XML Support**: Automatic serialization/deserialization
- **Authentication Support**: Basic, Digest, OAuth, Form authentication
- **Specification Reuse**: Request and Response specifications for DRY code

### Basic Syntax:
```java
given()
    .header("Content-Type", "application/json")
    .queryParam("key", "value")
    .body(requestBody)
.when()
    .post("/endpoint")
.then()
    .statusCode(200)
    .body("field", equalTo("expectedValue"));
```

### Understanding Given-When-Then Pattern:

REST Assured follows the **BDD (Behavior-Driven Development)** style using the **Given-When-Then** pattern, which makes tests highly readable and self-documenting.

#### 🔹 **GIVEN** - Test Setup & Request Preparation
**Purpose:** Set up the preconditions and prepare the request

**What you do here:**
- Configure request headers
- Add query parameters or path parameters
- Set request body (JSON, XML, form data)
- Add authentication credentials
- Set cookies
- Configure content type

**Example:**
```java
given()
    .header("Authorization", "Bearer token123")
    .header("Content-Type", "application/json")
    .queryParam("userId", "12345")
    .body("{\"name\":\"John\", \"age\":30}")
```

**Think of it as:** *"GIVEN I have these request configurations..."*

---

#### 🔹 **WHEN** - Execute the Action
**Purpose:** Perform the actual HTTP operation (the action being tested)

**What you do here:**
- Specify the HTTP method (GET, POST, PUT, DELETE, PATCH, etc.)
- Specify the endpoint/URL path
- Trigger the API call

**Example:**
```java
.when()
    .post("/api/users")
```

**Think of it as:** *"WHEN I send a POST request to /api/users..."*

---

#### 🔹 **THEN** - Validate the Response
**Purpose:** Assert and verify the response meets expectations

**What you do here:**
- Validate HTTP status code
- Verify response headers
- Assert response body content
- Check response time
- Extract data from response
- Validate JSON/XML structure

**Example:**
```java
.then()
    .statusCode(201)
    .header("Content-Type", "application/json")
    .body("name", equalTo("John"))
    .body("age", equalTo(30))
    .body("id", notNullValue())
```

**Think of it as:** *"THEN I expect the response to have status 201 and contain these values..."*

---

### 📖 **Complete Example with Explanation:**

```java
@Test
public void testCreateUser() {
    // GIVEN: Prepare the request with user data
    given()
        .contentType(ContentType.JSON)           // Set content type
        .header("Authorization", "Bearer xyz")   // Add auth header
        .body("{\"name\":\"Alice\",\"age\":25}") // Set request body

    // WHEN: Send POST request to create user
    .when()
        .post("/api/users")

    // THEN: Verify the response
    .then()
        .statusCode(201)                         // Expect 201 Created
        .body("name", equalTo("Alice"))          // Verify name
        .body("age", equalTo(25))                // Verify age
        .body("id", notNullValue());             // Ensure ID is generated
}
```

**This reads like a sentence:**
> "**Given** I have a JSON request with user data and authorization,
> **When** I send a POST request to /api/users,
> **Then** I expect a 201 status code and the response to contain the user details."

---

### ✅ **Benefits of Given-When-Then:**

| Benefit | Description |
|---------|-------------|
| **Readability** | Tests read like plain English, easy to understand |
| **Structure** | Clear separation of setup, action, and validation |
| **Maintainability** | Easy to modify and extend tests |
| **Documentation** | Tests serve as living documentation of API behavior |
| **Collaboration** | Non-technical stakeholders can understand test scenarios |

---

### Project Structure:
```
src/
├── main/java/com/restassured/
│   ├── config/
│   │   └── BaseConfig.java          # Base URL and common configurations
│   └── models/
│       └── User.java                 # POJO for request/response mapping
└── test/java/com/restassured/tests/
    ├── BaseTest.java                 # Base test setup
    ├── GetRequestTests.java          # GET request tests
    ├── PostRequestTests.java         # POST request tests
    ├── PutRequestTests.java          # PUT request tests
    ├── DeleteRequestTests.java       # DELETE request tests
    └── AdvancedTests.java            # Advanced testing scenarios
```

### Key Files Explained:

#### 📁 **Configuration Files**

**`pom.xml`**
- Maven project configuration file
- Defines project dependencies (REST Assured, TestNG, Jackson, Hamcrest, Lombok)
- Configures Maven plugins (Surefire for test execution, Compiler plugin)
- Sets Java version to 21
- Manages dependency versions centrally using properties

**`testng.xml`**
- TestNG suite configuration file
- Defines which test classes to execute
- Organizes tests into logical groups
- Allows parallel execution configuration
- Used by Maven Surefire plugin to run tests

**`.gitignore`**
- Specifies files and folders to exclude from Git version control
- Ignores compiled files (*.class), build artifacts (target/), IDE files (.idea/, *.iml)
- Prevents committing logs, test reports, and temporary files

---

#### 📁 **Source Files (src/main/java)**

**`BaseConfig.java`**
```java
Location: src/main/java/com/restassured/config/BaseConfig.java
```
**Purpose:** Centralized configuration for REST Assured framework

**What it does:**
- Defines the base URL for all API requests (`https://httpbin.org`)
- Creates reusable `RequestSpecification` with default headers and content type
- Creates reusable `ResponseSpecification` for common response validations
- Sets up REST Assured with logging for failed validations
- Promotes DRY (Don't Repeat Yourself) principle by centralizing common configurations

**Key Methods:**
- `getRequestSpecification()` - Returns pre-configured request specification
- `getResponseSpecification()` - Returns pre-configured response specification
- `setupRestAssured()` - Initializes REST Assured with base URI and logging

---

**`User.java`**
```java
Location: src/main/java/com/restassured/models/User.java
```
**Purpose:** POJO (Plain Old Java Object) model for user data

**What it does:**
- Represents a User entity with properties: name, email, age, city
- Uses Lombok annotations to auto-generate getters, setters, constructors
- Uses Jackson annotations for JSON serialization/deserialization
- Enables object-to-JSON conversion for request bodies
- Enables JSON-to-object conversion for response bodies
- Provides Builder pattern for easy object creation

**Annotations Used:**
- `@Data` - Generates getters, setters, toString, equals, hashCode
- `@Builder` - Enables builder pattern for object creation
- `@NoArgsConstructor` - Generates no-argument constructor
- `@AllArgsConstructor` - Generates constructor with all arguments
- `@JsonProperty` - Maps Java field to JSON property

---

#### 📁 **Test Files (src/test/java)**

**`BaseTest.java`**
```java
Location: src/test/java/com/restassured/tests/BaseTest.java
```
**Purpose:** Base class for all test classes

**What it does:**
- Provides common setup for all test classes
- Executes `@BeforeClass` method to initialize REST Assured configuration
- Calls `BaseConfig.setupRestAssured()` before any tests run
- All test classes extend this class to inherit common setup
- Ensures consistent configuration across all tests

---

**`GetRequestTests.java`**
```java
Location: src/test/java/com/restassured/tests/GetRequestTests.java
```
**Purpose:** Contains all GET request test scenarios

**What it does:**
- Tests basic GET requests to verify API connectivity
- Tests GET requests with query parameters
- Tests GET requests with custom headers
- Validates response time and performance
- Demonstrates response data extraction using JsonPath
- Contains 5 test methods covering different GET scenarios

---

**`PostRequestTests.java`**
```java
Location: src/test/java/com/restassured/tests/PostRequestTests.java
```
**Purpose:** Contains all POST request test scenarios

**What it does:**
- Tests POST requests with HashMap/Map as request body
- Tests POST requests with POJO (User object) serialization
- Tests POST requests with raw JSON string
- Tests POST requests with form-encoded data
- Demonstrates different ways to send data in POST requests
- Contains 4 test methods covering different POST scenarios

---

**`PutRequestTests.java`**
```java
Location: src/test/java/com/restassured/tests/PutRequestTests.java
```
**Purpose:** Contains all PUT request test scenarios

**What it does:**
- Tests PUT requests for updating resources with JSON body
- Tests PUT requests with POJO serialization
- Tests partial update scenarios
- Validates that update operations work correctly
- Contains 3 test methods covering different PUT scenarios

---

**`DeleteRequestTests.java`**
```java
Location: src/test/java/com/restassured/tests/DeleteRequestTests.java
```
**Purpose:** Contains all DELETE request test scenarios

**What it does:**
- Tests basic DELETE requests
- Tests DELETE requests with query parameters
- Tests DELETE requests with request body (non-standard but supported)
- Tests DELETE requests with custom headers (e.g., Authorization)
- Contains 4 test methods covering different DELETE scenarios

---

**`AdvancedTests.java`**
```java
Location: src/test/java/com/restassured/tests/AdvancedTests.java
```
**Purpose:** Contains advanced API testing scenarios

**What it does:**
- Tests Basic Authentication mechanism
- Tests specific HTTP status codes (201, 404, etc.)
- Tests API delay and timeout scenarios
- Tests response header validation
- Tests cookie handling (sending and receiving)
- Tests UUID format validation using regex
- Tests complex JSON response structures
- Contains 8 test methods covering advanced scenarios

---

### 🔑 Key Concepts Used:

| Concept | File(s) | Description |
|---------|---------|-------------|
| **BDD Syntax** | All test files | Given-When-Then pattern for readable tests |
| **Request Specification** | BaseConfig.java | Reusable request configuration |
| **Response Specification** | BaseConfig.java | Reusable response validation |
| **POJO Serialization** | User.java, PostRequestTests.java | Object to JSON conversion |
| **Hamcrest Matchers** | All test files | Powerful assertions (equalTo, greaterThan, etc.) |
| **TestNG Annotations** | All test files | @Test, @BeforeClass for test organization |
| **Lombok** | User.java | Reduces boilerplate code |
| **Jackson** | User.java | JSON processing library |

---

## 🧪 Test Functions & Examples

### 1. GET Request Tests (`GetRequestTests.java`)

#### 1.1 Simple GET Request
**Function:** `testSimpleGetRequest()`

**Input:**
```java
GET https://httpbin.org/get
```

**Sample API Response:**
```json
{
  "args": {},
  "headers": {
    "Accept": "*/*",
    "Host": "httpbin.org"
  },
  "origin": "122.171.16.202",
  "url": "https://httpbin.org/get"
}
```

**What is Tested:**
- ✅ Status code is 200
- ✅ Response URL matches expected value
- ✅ Response is in JSON format

---

#### 1.2 GET Request with Query Parameters
**Function:** `testGetRequestWithQueryParams()`

**Input:**
```java
GET https://httpbin.org/get?name=John&age=30
```

**Sample API Response:**
```json
{
  "args": {
    "name": "John",
    "age": "30"
  },
  "headers": {...},
  "url": "https://httpbin.org/get?name=John&age=30"
}
```

**What is Tested:**
- ✅ Query parameters are correctly sent
- ✅ Response contains the query parameters in `args` field
- ✅ Values match the sent parameters

---

#### 1.3 GET Request with Custom Headers
**Function:** `testGetRequestWithHeaders()`

**Input:**
```java
GET https://httpbin.org/headers
Headers:
  Custom-Header: CustomValue
  User-Agent: RestAssured-Test
```

**Sample API Response:**
```json
{
  "headers": {
    "Custom-Header": "CustomValue",
    "User-Agent": "RestAssured-Test",
    "Host": "httpbin.org"
  }
}
```

**What is Tested:**
- ✅ Custom headers are sent correctly
- ✅ Response echoes back the headers
- ✅ Header values match expected values

---

#### 1.4 Response Time Validation
**Function:** `testResponseTime()`

**Input:**
```java
GET https://httpbin.org/get
```

**What is Tested:**
- ✅ Response time is less than 5 seconds
- ✅ API performance is acceptable
- ✅ Status code is 200

---

#### 1.5 Extract Response Data
**Function:** `testExtractResponseData()`

**Input:**
```java
GET https://httpbin.org/get?test=value
```

**What is Tested:**
- ✅ Response data can be extracted
- ✅ Specific fields can be accessed using JsonPath
- ✅ Extracted values can be used for further validation

---

### 2. POST Request Tests (`PostRequestTests.java`)

#### 2.1 POST with Map/HashMap
**Function:** `testPostRequestWithMap()`

**Input:**
```java
POST https://httpbin.org/post
Content-Type: application/json
Body:
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30
}
```

**Sample API Response:**
```json
{
  "json": {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "age": 30
  },
  "headers": {
    "Content-Type": "application/json"
  },
  "url": "https://httpbin.org/post"
}
```

**What is Tested:**
- ✅ POST request with JSON body using HashMap
- ✅ Request body is correctly serialized
- ✅ Response echoes back the sent data
- ✅ All fields match expected values

---

#### 2.2 POST with POJO (Plain Old Java Object)
**Function:** `testPostRequestWithPOJO()`

**Input:**
```java
POST https://httpbin.org/post
Content-Type: application/json
Body: User object
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "age": 25,
  "city": "New York"
}
```

**Sample API Response:**
```json
{
  "json": {
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "age": 25,
    "city": "New York"
  }
}
```

**What is Tested:**
- ✅ POJO to JSON serialization
- ✅ Object mapping works correctly
- ✅ All object properties are sent
- ✅ Response contains all fields

---

#### 2.3 POST with JSON String
**Function:** `testPostRequestWithJsonString()`

**Input:**
```java
POST https://httpbin.org/post
Content-Type: application/json
Body:
{
  "username": "testuser",
  "password": "testpass123",
  "active": true
}
```

**Sample API Response:**
```json
{
  "json": {
    "username": "testuser",
    "password": "testpass123",
    "active": true
  }
}
```

**What is Tested:**
- ✅ Raw JSON string as request body
- ✅ Boolean values are handled correctly
- ✅ String values are preserved
- ✅ JSON structure is maintained

---

#### 2.4 POST with Form Data
**Function:** `testPostRequestWithFormData()`

**Input:**
```java
POST https://httpbin.org/post
Content-Type: application/x-www-form-urlencoded
Body:
  firstName=John
  lastName=Doe
  email=john@example.com
```

**Sample API Response:**
```json
{
  "form": {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com"
  },
  "headers": {
    "Content-Type": "application/x-www-form-urlencoded"
  }
}
```

**What is Tested:**
- ✅ Form-encoded data submission
- ✅ Content-Type header is correct
- ✅ Form parameters are sent correctly
- ✅ Response contains form data

---

### 3. PUT Request Tests (`PutRequestTests.java`)

#### 3.1 PUT with JSON Body
**Function:** `testPutRequestWithJson()`

**Input:**
```java
PUT https://httpbin.org/put
Content-Type: application/json
Body:
{
  "name": "Updated Name",
  "email": "updated@example.com",
  "status": "active"
}
```

**Sample API Response:**
```json
{
  "json": {
    "name": "Updated Name",
    "email": "updated@example.com",
    "status": "active"
  },
  "url": "https://httpbin.org/put"
}
```

**What is Tested:**
- ✅ PUT request with JSON payload
- ✅ Update operation simulation
- ✅ All fields are updated correctly
- ✅ Status code is 200

---

#### 3.2 PUT with POJO
**Function:** `testPutRequestWithPOJO()`

**Input:**
```java
PUT https://httpbin.org/put
Content-Type: application/json
Body: User object
{
  "name": "Updated User",
  "email": "updated.user@example.com",
  "age": 35,
  "city": "San Francisco"
}
```

**Sample API Response:**
```json
{
  "json": {
    "name": "Updated User",
    "email": "updated.user@example.com",
    "age": 35,
    "city": "San Francisco"
  }
}
```

**What is Tested:**
- ✅ Object serialization for PUT requests
- ✅ Complete object update
- ✅ All properties are sent
- ✅ Response matches sent data

---

#### 3.3 PUT for Partial Update
**Function:** `testPutRequestPartialUpdate()`

**Input:**
```java
PUT https://httpbin.org/put
Content-Type: application/json
Body:
{
  "id": 123,
  "name": "Partial Update",
  "description": "Only updating specific fields"
}
```

**Sample API Response:**
```json
{
  "json": {
    "id": 123,
    "name": "Partial Update",
    "description": "Only updating specific fields"
  }
}
```

**What is Tested:**
- ✅ Partial field updates
- ✅ Specific fields can be updated
- ✅ Integer and string values
- ✅ Response contains updated fields

---

### 4. DELETE Request Tests (`DeleteRequestTests.java`)

#### 4.1 Simple DELETE Request
**Function:** `testSimpleDeleteRequest()`

**Input:**
```java
DELETE https://httpbin.org/delete
```

**Sample API Response:**
```json
{
  "args": {},
  "headers": {...},
  "url": "https://httpbin.org/delete"
}
```

**What is Tested:**
- ✅ Basic DELETE request
- ✅ Status code is 200
- ✅ URL is correct
- ✅ Request completes successfully

---

#### 4.2 DELETE with Query Parameters
**Function:** `testDeleteRequestWithQueryParams()`

**Input:**
```java
DELETE https://httpbin.org/delete?id=123&reason=obsolete
```

**Sample API Response:**
```json
{
  "args": {
    "id": "123",
    "reason": "obsolete"
  },
  "url": "https://httpbin.org/delete?id=123&reason=obsolete"
}
```

**What is Tested:**
- ✅ DELETE with query parameters
- ✅ Parameters are sent correctly
- ✅ Response contains the parameters
- ✅ Values match expected data

---

#### 4.3 DELETE with Request Body
**Function:** `testDeleteRequestWithBody()`

**Input:**
```java
DELETE https://httpbin.org/delete
Content-Type: application/json
Body:
{
  "id": 456,
  "confirm": true
}
```

**Sample API Response:**
```json
{
  "json": {
    "id": 456,
    "confirm": true
  }
}
```

**What is Tested:**
- ✅ DELETE request with body (non-standard but supported)
- ✅ JSON body is sent correctly
- ✅ Boolean and integer values
- ✅ Response echoes the body

---

#### 4.4 DELETE with Headers
**Function:** `testDeleteRequestWithHeaders()`

**Input:**
```java
DELETE https://httpbin.org/delete
Headers:
  Authorization: Bearer token123
  X-Custom-Header: delete-001
```

**Sample API Response:**
```json
{
  "headers": {
    "Authorization": "Bearer token123",
    "X-Custom-Header": "delete-001"
  }
}
```

**What is Tested:**
- ✅ Custom headers in DELETE request
- ✅ Authorization header
- ✅ Custom request ID header
- ✅ Headers are sent correctly

---

### 5. Advanced Tests (`AdvancedTests.java`)

#### 5.1 Basic Authentication
**Function:** `testBasicAuthentication()`

**Input:**
```java
GET https://httpbin.org/basic-auth/user/passwd
Authorization: Basic dXNlcjpwYXNzd2Q=
```

**Sample API Response:**
```json
{
  "authenticated": true,
  "user": "user"
}
```

**What is Tested:**
- ✅ Basic authentication mechanism
- ✅ Credentials are sent correctly
- ✅ Authentication is successful
- ✅ User information is returned

---

#### 5.2 Status Code Testing
**Function:** `testStatusCodeEndpoint()`

**Input:**
```java
GET https://httpbin.org/status/201
```

**Sample API Response:**
```
HTTP/1.1 201 Created
(Empty body)
```

**What is Tested:**
- ✅ Specific status code validation
- ✅ API returns expected status code
- ✅ Status code 201 (Created)

---

#### 5.3 Delay/Timeout Testing
**Function:** `testDelayEndpoint()`

**Input:**
```java
GET https://httpbin.org/delay/2
```

**Sample API Response:**
```json
{
  "args": {},
  "headers": {...},
  "url": "https://httpbin.org/delay/2"
}
```

**What is Tested:**
- ✅ Response time is greater than 2 seconds
- ✅ API handles delays correctly
- ✅ Timeout scenarios
- ✅ Performance measurement

---

#### 5.4 Response Headers Validation
**Function:** `testResponseHeaders()`

**Input:**
```java
GET https://httpbin.org/response-headers?Custom-Header=TestValue&Another-Header=AnotherValue
```

**Sample API Response:**
```
HTTP/1.1 200 OK
Custom-Header: TestValue
Another-Header: AnotherValue
Content-Type: application/json
```

**What is Tested:**
- ✅ Custom response headers
- ✅ Header values are correct
- ✅ Multiple headers validation
- ✅ Response header inspection

---

#### 5.5 Cookie Testing
**Function:** `testCookies()`

**Input:**
```java
GET https://httpbin.org/cookies
Cookies:
  session_id=abc123
  user_token=xyz789
```

**Sample API Response:**
```json
{
  "cookies": {
    "session_id": "abc123",
    "user_token": "xyz789"
  }
}
```

**What is Tested:**
- ✅ Sending cookies with request
- ✅ Multiple cookies handling
- ✅ Cookie values are sent correctly
- ✅ Response contains cookies

---

#### 5.6 Set Cookies
**Function:** `testSetCookies()`

**Input:**
```java
GET https://httpbin.org/cookies/set?name=value&another=test
```

**Sample API Response:**
```
HTTP/1.1 200 OK
Set-Cookie: name=value
Set-Cookie: another=test
```

**What is Tested:**
- ✅ Server sets cookies
- ✅ Cookie extraction from response
- ✅ Multiple cookies from server
- ✅ Cookie values are correct

---

#### 5.7 UUID Validation
**Function:** `testUuidEndpoint()`

**Input:**
```java
GET https://httpbin.org/uuid
```

**Sample API Response:**
```json
{
  "uuid": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
}
```

**What is Tested:**
- ✅ UUID format validation
- ✅ Regex pattern matching
- ✅ UUID structure (8-4-4-4-12 format)
- ✅ Response contains valid UUID

---

#### 5.8 JSON Response Validation
**Function:** `testJsonEndpoint()`

**Input:**
```java
GET https://httpbin.org/json
```

**Sample API Response:**
```json
{
  "slideshow": {
    "title": "Sample Slide Show",
    "author": "Yours Truly",
    "date": "date of publication",
    "slides": [...]
  }
}
```

**What is Tested:**
- ✅ JSON content type
- ✅ Complex JSON structure
- ✅ Nested JSON validation
- ✅ Field existence check

---

## 🏃 How to Run Tests

### Option 1: Run All Tests
```bash
mvn clean test
```
This will execute all test classes and generate a test report.

---

### Option 2: Run Specific Test Class
```bash
# Run only GET request tests
mvn test -Dtest=GetRequestTests

# Run only POST request tests
mvn test -Dtest=PostRequestTests

# Run only PUT request tests
mvn test -Dtest=PutRequestTests

# Run only DELETE request tests
mvn test -Dtest=DeleteRequestTests

# Run only Advanced tests
mvn test -Dtest=AdvancedTests
```

---

### Option 3: Run Specific Test Method
```bash
# Run a single test method
mvn test -Dtest=GetRequestTests#testSimpleGetRequest

# Run multiple specific methods
mvn test -Dtest=GetRequestTests#testSimpleGetRequest+testGetRequestWithQueryParams
```

---

### Option 4: Run with TestNG XML Suite
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

### Option 5: Run Tests in IDE
1. **IntelliJ IDEA / Eclipse:**
   - Right-click on test class → Run
   - Right-click on test method → Run
   - Right-click on `testng.xml` → Run

---

### Test Execution Output
When you run tests, you'll see detailed logs:

```
Request method:	GET
Request URI:	https://httpbin.org/get
Headers:		Accept=*/*
Body:			<none>

HTTP/1.1 200 OK
Content-Type: application/json
{
    "args": {},
    "headers": {...},
    "url": "https://httpbin.org/get"
}

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
```

---

### Test Reports
After execution, find reports at:
- **Surefire Reports:** `target/surefire-reports/`
- **TestNG Reports:** `test-output/index.html`

---

## 📊 Summary

| Test Class | Number of Tests | Coverage |
|------------|----------------|----------|
| GetRequestTests | 5 | GET requests, query params, headers, response time |
| PostRequestTests | 4 | POST with Map, POJO, JSON, form data |
| PutRequestTests | 3 | PUT with JSON, POJO, partial updates |
| DeleteRequestTests | 4 | DELETE with params, body, headers |
| AdvancedTests | 8 | Auth, status codes, delays, cookies, UUID |
| **Total** | **24** | **Complete API testing coverage** |

---

## 🎯 Key Learnings from This Project

1. **REST Assured Syntax** - Given-When-Then pattern
2. **HTTP Methods** - GET, POST, PUT, DELETE
3. **Request Building** - Headers, params, body, auth
4. **Response Validation** - Status codes, body, headers, cookies
5. **Data Formats** - JSON, Form data, POJO serialization
6. **Advanced Scenarios** - Authentication, timeouts, patterns

---

## � Support

For questions or issues, please open an issue in the repository.

**Repository:** [git@github.com:sand361049/rest-assured.git](git@github.com:sand361049/rest-assured.git)

