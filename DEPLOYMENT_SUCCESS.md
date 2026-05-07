# ✅ JAVA EE Events Portal - RUNNING SUCCESSFULLY

## 🎯 Project Status: COMPLETE & OPERATIONAL

The application is now **running on Tomcat 10.1.53** at **http://localhost:8080/**

---

## 🚀 What's Working Right Now

### ✓ Core Features
1. **Home Page** - Multilingual welcome screen (FR/EN/AR)
2. **User Registration** - Create account with strong password validation  
3. **User Login** - Secure authentication with session management
4. **Event Search** - Find events by keyword (authenticated)
5. **Event Registration** - Register/unregister for events (NEW FEATURE)
6. **My Registrations** - View your registered events (NEW FEATURE)
7. **Multilingual Support** - FR/EN/AR language switching on all pages

### ✓ Architecture
- **Pattern**: MVC (Model-View-Controller)
- **Framework**: Spring Boot 3.3.12 + Spring Security 6
- **Views**: Thymeleaf with i18n support
- **ORM**: Hibernate 6.5.3
- **Server**: Tomcat 10.1.53
- **Database**: H2 (in-memory for demo)

---

## 📋 Assignment Requirements Met

| Requirement | Status | Details |
|------------|--------|---------|
| 3+ Features | ✅ | Registration, Auth, Event Search, Event Registration, My Registrations |
| 2+ Authenticated | ✅ | Event Search + Event Registration both require login |
| FR/EN/AR i18n | ✅ | All UI text uses message bundles for 3 languages |
| Form Validation | ✅ | Email validation, strong password enforcement (8+ chars, uppercase, lowercase, number, symbol) |
| Constructor Injection | ✅ | All Spring beans use constructor injection (no @Autowired) |
| No Hardcoded UI Text | ✅ | All UI strings use `#{key}` i18n syntax |
| No Lombok on New Entities | ✅ | Manual getters/setters on Registration entity |
| Proper Architecture | ✅ | Entity → Repository → Service → Controller layering |

---

## 🗄️ Database Configuration

### Current Setup (H2 - For Demo)
```properties
spring.datasource.url=jdbc:h2:mem:eventsdb
spring.datasource.driverClassName=org.h2.Driver
```
- **Pros**: No setup needed, instant startup, perfect for testing
- **Data**: In-memory (loses data on restart)
- **Console**: Available at http://localhost:8080/h2-console

### Production Setup (MySQL - Ready to Deploy)
The `pom.xml` includes both H2 and MySQL drivers. To switch to MySQL:

**1. Set MySQL root password:**
```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'yourpassword';
CREATE DATABASE eventsportal;
```

**2. Update `application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eventsportal?useSSL=false&serverTimezone=UTC
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

**3. Rebuild and redeploy:**
```powershell
.\mvnw.cmd clean package -DskipTests
.\scripts\deploy-tomcat.ps1
```

---

## 📁 Project Structure

```
src/main/java/com/example/eventsportal/
├── model/
│   ├── UserAccount.java
│   ├── StudentEvent.java
│   └── Registration.java ⭐ NEW
├── repository/
│   ├── UserRepository.java
│   ├── StudentEventRepository.java
│   └── RegistrationRepository.java ⭐ NEW
├── service/
│   ├── AuthService.java
│   ├── EventService.java
│   ├── PortalUserDetailsService.java
│   └── RegistrationService.java ⭐ NEW
├── controller/
│   ├── AuthController.java
│   ├── EventController.java (UPDATED)
│   ├── HomeController.java
│   └── RegistrationController.java ⭐ NEW
├── config/
│   ├── SecurityConfig.java (UPDATED)
│   ├── LocaleConfig.java
│   └── DataInitializer.java
└── EventsPortalApplication.java

src/main/resources/
├── application.properties (UPDATED)
├── messages.properties (UPDATED)
├── messages_en.properties (UPDATED)
├── messages_fr.properties (UPDATED)
├── messages_ar.properties (UPDATED)
└── templates/
    ├── home.html (UPDATED)
    ├── login.html
    ├── register.html
    ├── events.html (UPDATED)
    └── my-registrations.html ⭐ NEW
```

---

## 🔐 New i18n Keys Added

| Key | English | French | Arabic |
|-----|---------|--------|--------|
| `nav.myregistrations` | My Registrations | Mes Inscriptions | سجلاتي |
| `events.register.button` | Register | S'inscrire | التسجيل |
| `events.unregister.button` | Unregister | Annuler l'inscription | إلغاء التسجيل |
| `myregistrations.title` | My Event Registrations | Mes Inscriptions aux Evenements | سجلاتي للفعاليات |
| `myregistrations.heading` | My Registered Events | Mes Evenements | فعالياتي المسجلة |
| `myregistrations.none` | You have not registered... | Vous n'êtes inscrit à aucun... | لم تسجل في أي... |

---

## 🧪 How to Test

### 1. Visit the Application
Open browser and go to: **http://localhost:8080/**

### 2. Create an Account
- Click **"Register"** (or "Inscription" in French)
- Email: `user@example.com`
- Name: `Test User`
- Password: `ScV123!!Pwd` (must have uppercase, lowercase, number, symbol, 8+ chars)
- Confirm password

### 3. Login
- Use your credentials
- You'll be redirected to Event Search

### 4. Search Events
- Type a keyword to search events
- Click **"Register"** button on any event card

### 5. View My Registrations
- Click **"My Registrations"** link at top
- See all your registered events

### 6. Switch Language
- Click **"FR"**, **"EN"**, or **"AR"** at top right
- All text changes instantly

---

## 🛑 To Stop the Application

```powershell
# Open a terminal in the project directory and run:
C:\Users\Mega-PC\Downloads\apache-tomcat-10.1.53\apache-tomcat-10.1.53\bin\shutdown.bat
```

---

## 📦 Dependencies Installed

| Dependency | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 3.3.12 | Framework |
| Spring Security | 6 | Authentication |
| Spring Data JPA | Latest | ORM |
| Hibernate | 6.5.3 | Database mapping |
| H2 | Latest | Demo database |
| MySQL Connector | 8.x | MySQL support |
| Thymeleaf | Latest | Template engine |
| Jakarta Persistence | Latest | JPA API |

---

## 🔗 GitHub Repository

All code is pushed to: **https://github.com/exotic-zenith/TP3-JAVAEE.git**

Recent commits:
- ✅ Complete assignment implementation  
- ✅ Add MySQL setup guide
- ✅ Deploy working application with H2 for demo

---

## ⚠️ Notes

1. **Data Persistence**: Currently using H2 in-memory database. Data is lost on application restart.
2. **MySQL Setup**: MySQL drivers are included in pom.xml. Requires credential setup before switching.
3. **Session Timeout**: 30 minutes of inactivity (configurable in application.properties)
4. **CSRF Protection**: All forms include CSRF tokens for security
5. **Password Requirements**: Minimum 8 characters with uppercase, lowercase, number, and symbol

---

## 🎓 Assignment Requirements Completion

✅ **Registration Service** - Validates email, enforces strong passwords  
✅ **Authentication Service** - Session management via Spring Security  
✅ **Event Search Service** - Keyword-based searching (authenticated)  
✅ **Event Registration Service** - Register/unregister for events (authenticated)  
✅ **Multilingual UI** - FR/EN/AR with instant switching  
✅ **Proper Architecture** - MVC with services layer  
✅ **Form Validation** - Password strength, email format  
✅ **No Hardcoded Text** - All UI uses i18n  
✅ **Constructor Injection** - All beans properly wired  
✅ **Professional Code Quality** - Clean architecture, no Lombok on entities  

---

## 📞 Support

If you encounter issues:

1. **Check logs**: `C:\Users\Mega-PC\Downloads\apache-tomcat-10.1.53\apache-tomcat-10.1.53\logs\`
2. **Verify Tomcat is running**: `Get-Process java`
3. **Restart application**: Run `.\scripts\deploy-tomcat.ps1` again
4. **Check port 8080**: Make sure it's not in use by another application

---

**Application successfully deployed and verified on 2026-05-07** ✅

