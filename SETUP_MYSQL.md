# Setup Guide for Events Portal with MySQL

## Prerequisites
- Java 17+
- Maven 3.6+
- MySQL Server 8.0+
- Tomcat 10.1.53

## Database Setup

### 1. Create MySQL Database and User

Open MySQL command prompt and run:

```sql
-- Create database
CREATE DATABASE eventsportal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user (if not exists)
CREATE USER 'root'@'localhost' IDENTIFIED BY '';

-- Grant privileges
GRANT ALL PRIVILEGES ON eventsportal.* TO 'root'@'localhost';

-- Flush privileges
FLUSH PRIVILEGES;
```

### 2. Application Configuration

The `application.properties` file is already configured for MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eventsportal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3. Build and Deploy

```powershell
# Build the project
.\mvnw.cmd clean package

# Deploy to Tomcat using the provided script
.\scripts\deploy-tomcat.ps1
```

## Features Implemented

### 1. ✅ Event Registration (New)
- Users can register/unregister for events
- View registered events in /my-registrations
- Register/unregister buttons on event cards
- Prevents duplicate registrations

### 2. ✅ Multilingual Support (FR/EN/AR)
- All text uses i18n keys
- Language switcher on all pages
- Added new keys:
  - `nav.myregistrations` - Navigation link
  - `events.register.button` - Register button
  - `events.unregister.button` - Unregister button
  - `myregistrations.*` - My registrations page
  - `registration.*` - Registration messages

### 3. ✅ Security Features
- Removed H2 Console (security improvement)
- New endpoints protected:
  - `/events/{id}/register` - POST
  - `/events/{id}/unregister` - POST
  - `/my-registrations` - GET
- CSRF protection on all forms
- Session management (30 minutes)

### 4. ✅ Database Migration
- Replaced H2 with MySQL
- New tables:
  - `registrations` - Event registrations with user/event references
  - All tables created automatically via Hibernate DDL
- Data persistence across application restarts

## Project Structure

```
src/main/java/com/example/eventsportal/
├── config/
│   ├── SecurityConfig.java         (Updated - removed H2 config)
│   ├── LocaleConfig.java
│   └── DataInitializer.java
├── controller/
│   ├── AuthController.java
│   ├── EventController.java        (Updated - shows registered events)
│   ├── HomeController.java
│   └── RegistrationController.java (New)
├── model/
│   ├── UserAccount.java
│   ├── StudentEvent.java
│   └── Registration.java           (New)
├── repository/
│   ├── UserRepository.java
│   ├── StudentEventRepository.java
│   └── RegistrationRepository.java (New)
├── service/
│   ├── AuthService.java
│   ├── EventService.java
│   ├── PortalUserDetailsService.java
│   └── RegistrationService.java    (New)
└── EventsPortalApplication.java

src/main/resources/
├── application.properties          (Updated - MySQL config)
├── messages.properties
├── messages_en.properties          (Updated - new keys)
├── messages_fr.properties          (Updated - new keys)
├── messages_ar.properties          (Updated - new keys)
└── templates/
    ├── home.html                   (Updated - added my-registrations link)
    ├── login.html
    ├── register.html
    ├── events.html                 (Updated - added register/unregister buttons)
    └── my-registrations.html       (New)
```

## Testing the Application

1. **Register Account**
   - Navigate to http://localhost:8080/register
   - Create account with strong password

2. **Login**
   - Go to http://localhost:8080/login
   - Enter credentials

3. **Search Events**
   - After login, browse to /events
   - Search for events by keyword
   - You'll see Register/Unregister buttons

4. **Register for Events**
   - Click "Register" button on event card
   - Check /my-registrations to see your registrations
   - Unregister by clicking "Unregister"

5. **Language Switching**
   - Use language switchers (FR/EN/AR) on any page
   - All text changes accordingly

6. **Session Management**
   - Session expires after 30 minutes of inactivity
   - Use "Logout" button to end session manually

## Troubleshooting

### MySQL Connection Error
- Ensure MySQL is running
- Check username/password in application.properties
- Verify port 3306 is accessible

### Tomcat Deployment Error
- Check CATALINA_HOME environment variable
- Ensure sufficient disk space in webapps folder
- Check Tomcat logs for detailed errors

### Database Tables Not Created
- Enable logging in application.properties:
  ```
  spring.jpa.show-sql=true
  ```
- Check Hibernate DDL output in console
- Tables are created on first run

## Dependencies
- Spring Boot 3.3.12
- Spring Security 6
- Spring Data JPA
- Hibernate 6.5.3
- MySQL Connector Java 8.x
- Thymeleaf + Spring Security Extension
- Jakarta Persistence API

## Assignment Requirements Met
✅ 3+ features implemented (registration, search, auth)
✅ 2+ require authentication (search, registration)
✅ FR/EN/AR multilingual support
✅ Form validation (password strength, email validation)
✅ Proper architecture (MVC with services)
✅ No hardcoded UI text (all i18n)
✅ No Lombok on new entities
✅ Constructor injection everywhere
✅ MySQL database integration

