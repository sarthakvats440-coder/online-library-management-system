# Java Web Based Projects – Marking Rubric Mapping

| Rubric item (marks) | Where it is covered |
|---|---|
| **Problem understanding & solution design (8)** | Problem statement and architecture slides; roles (librarian / member); layered design JSP → Servlet → Service → DAO → MySQL; ER diagram in `docs/img/er_diagram.png` |
| **Core Java concepts (10)** | *Inheritance / abstraction / polymorphism:* `User` (abstract) → `Member`, `Librarian`, each overriding `getRole()` and `getHomePath()`. *Interfaces + generics:* `CrudDAO<T,ID>`, `Searchable<T>`. *Exception handling:* custom `LibraryException`, `BookNotAvailableException`, try-with-resources, rollback. *Collections:* `List`, `LinkedHashMap`, `TreeMap`, streams in `LibraryService.inventoryReport()`. *Multithreading:* `OverdueScheduler` (ScheduledExecutorService, `AtomicInteger`). |
| **Database integration – JDBC (8)** | `DBConnection` (driver + properties), `BookDAO` / `UserDAO` / `TransactionDAO` with `PreparedStatement`, joins, `SELECT … FOR UPDATE`, manual commit / rollback in borrow and return |
| **Servlets & web integration (7)** | 8 `@WebServlet` classes, `AuthFilter` (`@WebFilter`), `@WebListener`, HttpSession login, flash messages, JSP + JSTL views, POST-redirect-GET |

## Gaps to fix yourself before the review
1. The web app was compile-checked only; it has **not been run against a real MySQL and Tomcat**. Run it once end to end and fix anything that differs on your machine.
2. Replace the mock UI images in the slides with real screenshots from your running app.
3. The original console files in `src/library/` have almost no comments; the rubric asks for well-commented code.
4. Be ready to explain every class. The web layer was generated for you; read it and add your own comments.
5. Add your team members' names and project title details to the title slide.
