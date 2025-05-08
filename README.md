# Library Management System

## 📋 Description
Library Management System is a modern Java application that provides a complete solution for library management. The application enables book management, user management, loan processing, and offers advanced features for different types of users (administrators, employees, and customers).

## 🚀 Features

### For Administrators
- Complete user management (add, delete, modify)
- Employee management
- Reports and statistics viewing
- Book stock management
- System activity monitoring

### For Employees
- Loan management
- Return processing
- Book search and reservation
- Customer assistance

### For Customers
- Book search
- Book reservation
- Personal history viewing
- Account management

## 🛠 Technologies Used

### Languages and Frameworks
- Java 21
- JavaFX 21.0.1 (for GUI)
- Gradle (build system)

### Database
- MySQL 8.0
- JDBC for connection

### Libraries and Dependencies
- iText7 (for PDF report generation)
- Logback (for logging)
- JUnit 5 (for testing)
- Testcontainers (for integration testing)

## 🔒 Security

### Authentication and Authorization
- Secure login/register system
- SHA-256 password hashing
- Robust credential validation
- Role-based system (ADMINISTRATOR, EMPLOYEE, CUSTOMER)

### Validations
- Email validation for username
- Complex passwords (minimum 8 characters, special characters, digits)
- SQL injection prevention through PreparedStatements
- Input validation at all levels

### Data Protection
- Hashed passwords in database
- Secure database connections
- User session management
- Logging for audit and debugging

## 🏗 Architecture

### Design Patterns
- Factory Pattern (for component creation)
- Builder Pattern (for object construction)
- Repository Pattern (for data access)
- Service Pattern (for business logic)
- MVC (Model-View-Controller)

### Project Structure
```
src/main/java/org/example/
├── controller/     # UI Controllers
├── model/         # Entities and validators
├── repository/    # Data access
├── service/       # Business logic
├── view/          # UI interfaces
└── launcher/      # Entry points
```

## 📦 Installation and Configuration

### Prerequisites
- Java 21 or newer
- MySQL 8.0 or newer
- Gradle 8.1 or newer

### Installation Steps
1. Clone the repository
```bash
git clone [repository-url]
```

2. Configure the database
- Create a MySQL database
- Update credentials in the configuration file

3. Build and Run
```bash
./gradlew build
./gradlew run
```

## 🧪 Testing

### Test Types
- Unit tests (JUnit 5)
- Integration tests (Testcontainers)
- Performance tests

### Running Tests
```bash
./gradlew test
```

## 📊 Performance

### Optimizations
- Implemented caching for books
- Optimized queries
- Efficient database connection management

### Monitoring
- Logging for debugging
- Performance metrics
- Usage reports