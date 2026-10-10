# Online Library Management System

A Java web application for managing a library: librarians manage books and members, members search and borrow books.
Built with **Java Servlets, JSP/JSTL, JDBC and MySQL** (Review 1: project structure, database design and connectivity, UI).

> The repository also keeps the original console prototype in `src/library/` (unchanged).

## Features
| Role | What they can do |
|------|------------------|
| Librarian | Dashboard with inventory report · add / edit / delete books · view and remove members · view all transactions |
| Member | Register / login · search books by title, author or genre · borrow and return books · view own borrowing history |
| System | Passwords stored as SHA-256 hashes · borrow/return run as JDBC transactions (commit/rollback) · background thread flags overdue loans hourly |

## Tech stack
Java 17 · Servlet 4.0 / JSP 2.3 / JSTL 1.2 (Tomcat 9) · MySQL 8 · MySQL Connector/J 8.3 · Maven

## Requirements
- JDK 17 or newer
- Apache Maven 3.8+
- MySQL 8.x
- Apache Tomcat **9.x** (uses the `javax.servlet` namespace; Tomcat 10+ would need `jakarta.*` imports)

## Setup
1. **Create the database**
   ```bash
   mysql -u root -p < web-app/sql/schema.sql
   mysql -u root -p < web-app/sql/seed.sql
   ```
2. **Configure the connection** – edit `web-app/src/main/resources/db.properties` (URL, user, password).
3. **Build**
   ```bash
   cd web-app
   mvn clean package
   ```
4. **Deploy** – copy `web-app/target/library.war` into Tomcat's `webapps/` folder and start Tomcat.
5. **Open** <http://localhost:8080/library/>

### Demo accounts (from `seed.sql`)
| Role | Email | Password |
|------|-------|----------|
| Librarian | admin@library.com | admin123 |
| Member | demo@example.com | demo123 |

## Project structure
```
online-library-management-system/
├── README.md
├── src/library/                  # original console prototype (unchanged)
├── docs/img/                     # ER diagram and UI design images
└── web-app/
    ├── pom.xml
    ├── sql/            schema.sql, seed.sql
    └── src/main/
        ├── resources/  db.properties
        ├── java/com/library/
        │   ├── model/      User (abstract) · Member · Librarian · Book · Transaction
        │   ├── dao/        CrudDAO<T,ID> · Searchable<T> · BookDAO · UserDAO · TransactionDAO
        │   ├── service/    LibraryService (validation, reports with Collections/streams)
        │   ├── servlet/    Login · Register · Logout · Book · Borrow(/borrow,/return) · Transaction · Member · Dashboard
        │   ├── filter/     AuthFilter (login + role protection)
        │   ├── listener/   OverdueScheduler (background thread)
        │   ├── exception/  LibraryException · BookNotAvailableException
        │   └── util/       DBConnection (JDBC) · PasswordUtil (SHA-256)
        └── webapp/     *.jsp, header.jspf, footer.jspf, css/style.css, WEB-INF/web.xml
```

## Architecture
`Browser → JSP (view) → Servlet (controller) → LibraryService → DAO (JDBC) → MySQL`

## Database
Three tables: `users` (librarian or member), `books`, `transactions` (links a user to a book, with `borrow_date`, `due_date`, `return_date`, `status`).
`transactions.user_id → users.user_id` and `transactions.book_id → books.book_id` (both foreign keys, `ON DELETE CASCADE`).
See `docs/img/er_diagram.png`.

## Console prototype (original)
```bash
mkdir -p bin && javac src/library/*.java -d bin && java -cp bin library.Main
```
Demo data is in memory only; nothing is saved when it exits.

## Known limitations
- Fines, email notifications and book reservations are not implemented yet.
- No automated tests yet.
