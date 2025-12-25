# Client Panel - Setup Instructions

## Quick Start

### 1. Prerequisites Check
- Java 21 installed: `java -version`
- Maven installed: `mvn -version`
- PostgreSQL running with `wa_client` database

### 2. Configure Database
Update `src/main/resources/application.properties` or set environment variables:

```bash
# Linux/Mac
export DB_URL=jdbc:postgresql://localhost/wa_client
export DB_USER=postgres
export DB_PASSWORD=admin

# Windows PowerShell
$env:DB_URL="jdbc:postgresql://localhost/wa_client"
$env:DB_USER="postgres"
$env:DB_PASSWORD="admin"
```

### 3. Build Project
```bash
mvn clean install
```

### 4. Run Application
```bash
mvn spring-boot:run
```

### 5. Access Application
- Home: http://localhost:8080
- Login: http://localhost:8080/login
- Dashboard: http://localhost:8080/dashboard (after login)
- Clients: http://localhost:8080/clients (after login)

## Project Highlights

✅ **Clean Architecture**: MVC pattern with separation of concerns
✅ **Security**: Spring Security with BCrypt password encryption
✅ **Modern UI**: AdminLTE + Bootstrap 5.3.0
✅ **Database**: No schema changes, validates existing structure
✅ **Validation**: Comprehensive bean validation
✅ **Pagination**: 10 items per page
✅ **Responsive Design**: Mobile-friendly interface

## Key Features

1. **Authentication**: Email/password login with session management
2. **Dashboard**: Client statistics and quick actions
3. **Client Management**: 
   - List clients with pagination
   - Add new clients with validation
   - Edit existing clients
   - Delete clients
4. **Status Management**: Toggle between Active/Inactive
5. **Unique Constraints**: Email, Mobile, Phone Number ID

## Database Tables

**users** table expected columns:
```sql
client_id VARCHAR(10) PRIMARY KEY
email VARCHAR(255) UNIQUE NOT NULL
name VARCHAR(255)
mobile VARCHAR(20) UNIQUE
phone_number_id VARCHAR(15) UNIQUE
pass VARCHAR(255) NOT NULL
chat_prefix TEXT
role VARCHAR(50)
status VARCHAR(20)
created_at TIMESTAMP
updated_at TIMESTAMP
```

## File Structure Created

```
src/main/java/com/clientpanel/
├── ClientPanelApplication.java
├── controller/
│   ├── HomeController.java
│   ├── DashboardController.java
│   └── ClientController.java
├── service/
│   ├── UserService.java
│   └── CustomUserDetailsService.java
├── repository/
│   └── UserRepository.java
├── model/
│   └── User.java
└── config/
    └── SecurityConfig.java

src/main/resources/
├── application.properties
├── templates/
│   ├── index.html
│   ├── login.html
│   ├── layout/base.html
│   ├── dashboard/dashboard.html
│   └── client/
│       ├── list.html
│       ├── add.html
│       └── edit.html
└── static/
    ├── css/custom.css
    └── js/custom.js
```

## Troubleshooting

### Port Already in Use
```bash
# Change port in application.properties
server.port=8081

# Or run with parameter
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Database Connection Failed
1. Verify PostgreSQL is running
2. Check credentials in application.properties
3. Verify `wa_client` database exists
4. Test connection: `psql -U postgres -d wa_client`

### Login Fails
1. User must exist in `users` table
2. Password must be already hashed in `pass` column
3. Status must be "Active"

## Next Steps

1. **Development**: Make code changes, rebuild, and test
2. **Testing**: Add JUnit tests in `src/test/java`
3. **Deployment**: 
   - Build JAR: `mvn clean package`
   - Run JAR: `java -jar target/client-panel-1.0.0.jar`
4. **Containerization**: Create Dockerfile if needed

## Support

For issues or questions, refer to README.md or contact the team.
