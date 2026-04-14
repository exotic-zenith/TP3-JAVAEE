# Events Portal (TP)

Portail web Spring Boot pour evenements etudiants avec:
- inscription utilisateur (validation email + mot de passe fort)
- connexion/session avec Spring Security
- recherche d'evenements protegee apres authentification
- interface multilingue FR/EN/AR via Thymeleaf + i18n

## Stack
- Java 17
- Spring Boot
- Spring MVC + Thymeleaf
- Spring Security
- Spring Data JPA
- H2 database

## Lancer le projet
```powershell
./mvnw.cmd spring-boot:run
```

## Deploiement sur Tomcat 10.1.53
Le projet genere maintenant un fichier `ROOT.war`.

Commande unique (build + copie + restart Tomcat) :

```powershell
.\scripts\deploy-tomcat.ps1
```

Ou depuis `cmd.exe` :

```cmd
scripts\deploy-tomcat.cmd
```

```powershell
./mvnw.cmd clean package
copy target\ROOT.war "C:\Users\Mega-PC\Downloads\apache-tomcat-10.1.53\apache-tomcat-10.1.53\webapps\"
```

Puis lancer Tomcat avec le script fourni :

```powershell
.\scripts\start-tomcat.ps1
```

Ou en double-cliquant / depuis `cmd.exe` :

```cmd
scripts\start-tomcat.cmd
```

Ces scripts definissent automatiquement `CATALINA_HOME` et `CATALINA_BASE`.

## Tester
```powershell
./mvnw.cmd test
```

## URLs utiles
- Accueil: `http://localhost:8080/`
- Inscription: `http://localhost:8080/register`
- Connexion: `http://localhost:8080/login`
- Recherche (auth requise): `http://localhost:8080/events`
- H2 console: `http://localhost:8080/h2-console`

## Changer la langue
Utiliser les liens FR/EN/AR dans l'interface.
Le parametre `?lang=fr|en|ar` met la langue en session.

