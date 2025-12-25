# Client Panel - Spring Boot Application

A modern web application for managing clients using Spring Boot 3.5.4, Thymeleaf, AdminLTE, and Spring Security.

## Technology Stack

- **Java Version**: 21
- **Build Tool**: Maven
- **Framework**: Spring Boot 3.5.4
- **Template Engine**: Thymeleaf
- **Security**: Spring Security
- **ORM**: Spring Data JPA / Hibernate
- **Database**: PostgreSQL
- **UI**: Bootstrap 5.3.0 + AdminLTE 3.2.0
- **Libraries**: Lombok

## Project Structure

```
client-panel/
├── src/
│   ├── main/
│   │   ├── java/com/clientpanel/
│   │   │   ├── ClientPanelApplication.java (Main entry point)
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── DashboardController.java
│   │   │   │   └── ClientController.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java
│   │   │   ├── model/
│   │   │   │   └── User.java
│   │   │   └── config/
│   │   │       └── SecurityConfig.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── templates/
│   │   │   │   ├── index.html
│   │   │   │   ├── login.html
│   │   │   │   ├── layout/base.html
│   │   │   │   ├── dashboard/dashboard.html
│   │   │   │   └── client/
│   │   │   │       ├── list.html
│   │   │   │       ├── add.html
│   │   │   │       └── edit.html
│   │   │   └── static/
│   │   │       ├── css/custom.css
│   │   │       └── js/custom.js
│   └── test/
├── pom.xml
└── README.md
```

## Database Configuration

The application uses an existing PostgreSQL database. Configure the connection in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost/wa_client
spring.datasource.username=postgres
spring.datasource.password=admin
```

**Environment Variables** (Recommended for production):
- `DB_URL`: Database connection URL
- `DB_USER`: Database username
- `DB_PASSWORD`: Database password

Example:
```bash
export DB_URL=jdbc:postgresql://localhost/wa_client
export DB_USER=postgres
export DB_PASSWORD=admin
```

## Getting Started

### Prerequisites

- Java 21 JDK installed
- Maven 3.8.9 or higher
- PostgreSQL database running with `wa_client` database created
- Git (for version control)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Arsalan-Ahmed-Qureshi/client-panel.git
   cd client-panel
   ```

2. **Build the application**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Open your browser and navigate to `http://localhost:8080`
   - Default login page: `http://localhost:8080/login`

## Features

### Public Pages
- **Home Page**: Welcome page with login option
- **Login Page**: Email/password authentication

### Authenticated Pages

#### Dashboard
- Client summary by status (Active/Inactive)
- Activity statistics
- Quick action buttons
- User management links

#### Client Management
- **List Clients**: Paginated list (10 per page)
  - Display: client_id, email, name, mobile, phone_number_id, status
  - Edit and delete actions
  
- **Add Client**:
  - Client ID (unique, 3-10 characters, alphanumeric with hyphens/underscores)
  - Email (unique, valid email format)
  - Mobile (numeric, unique, 10-15 digits)
  - Phone Number ID (numeric, unique, exactly 15 digits)
  - Chat Prefix (optional, max 2000 characters)
  - Default role: ROLE_CLIENT
  - Default status: Active
  
- **Edit Client**:
  - Same fields as Add Client
  - Client ID is read-only
  - Status can be toggled (Active/Inactive)

## Security

### Authentication
- Email-based login
- Password stored securely (BCrypt encryption)
- Session management

### Authorization
- Role-based access control
- Roles: `ROLE_ADMIN`, `ROLE_CLIENT`
- Dashboard and Client management: Authenticated users only

## API Endpoints

### Home & Auth
- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - Login submission
- `GET /logout` - Logout

### Dashboard
- `GET /dashboard` - Dashboard view

### Clients
- `GET /clients` - List clients (with pagination)
- `GET /clients/add` - Add client form
- `POST /clients/add` - Create new client
- `GET /clients/edit/{clientId}` - Edit client form
- `POST /clients/edit/{clientId}` - Update client
- `POST /clients/delete/{clientId}` - Delete client

## Form Validation

### Client ID
- Required, 3-10 characters
- Pattern: `[a-zA-Z0-9_-]+`
- Must be unique

### Email
- Required, valid email format
- Must be unique

### Mobile
- Required, 10-15 numeric digits
- Must be unique

### Phone Number ID
- Required, exactly 15 numeric digits
- Must be unique

### Chat Prefix
- Optional, maximum 2000 characters

## Development

### Build
```bash
mvn clean install
```

### Run Tests
```bash
mvn test
```

### Create JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/client-panel-1.0.0.jar
```

## Environment Setup

### Set Database Environment Variables
```bash
# Linux/Mac
export DB_URL=jdbc:postgresql://localhost/wa_client
export DB_USER=postgres
export DB_PASSWORD=admin

# Windows (Command Prompt)
set DB_URL=jdbc:postgresql://localhost/wa_client
set DB_USER=postgres
set DB_PASSWORD=admin

# Windows (PowerShell)
$env:DB_URL="jdbc:postgresql://localhost/wa_client"
$env:DB_USER="postgres"
$env:DB_PASSWORD="admin"
```

## Database Schema

The application uses existing tables. No schema creation is performed. The main table used is:

**users** table with columns:
- `client_id` (Primary Key)
- `email` (Unique)
- `name`
- `mobile` (Unique)
- `phone_number_id` (Unique)
- `pass` (Password)
- `chat_prefix`
- `role`
- `status`
- `created_at`
- `updated_at`

## Troubleshooting

### Database Connection Issues
- Verify PostgreSQL is running
- Check database credentials in `application.properties`
- Ensure `wa_client` database exists

### Login Issues
- Verify user exists in the database
- Check that the `pass` column contains a valid password
- Ensure the user's `status` is set to "Active"

### Thymeleaf Template Issues
- Clear browser cache
- Check template file paths
- Verify `spring.thymeleaf.cache=false` in development

## Deployment

### JAR Deployment
```bash
mvn clean package
java -Dserver.port=8080 \
     -DDB_URL=jdbc:postgresql://host:port/wa_client \
     -DDB_USER=username \
     -DDB_PASSWORD=password \
     -jar target/client-panel-1.0.0.jar
```

### Docker (Optional)
A Dockerfile can be created for containerization.

## Contributing

1. Create a feature branch
2. Make your changes
3. Commit with clear messages
4. Push to the repository
5. Create a Pull Request

## License

This project is proprietary and confidential.

## Contact

For support and inquiries, contact the development team.

## Version

**Current Version**: 1.0.0  
**Last Updated**: December 2025

## Notes

- No schema changes are made (DDL auto = validate)
- The application reuses existing database tables
- All code follows clean architecture principles
- Production-ready code quality standards applied
