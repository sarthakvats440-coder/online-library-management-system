# Online Library Management System (Java Console App)

A simple console-based Java implementation of the Online Library Management System project.

## Features

**Librarian**
- Add / Edit / Delete books
- Add / Edit / Delete members
- View all transactions
- Generate inventory report
- Send due-date reminders (console notifications)

**Member**
- Register / Login
- Search books (title, author, genre)
- Borrow / Return books
- View borrowing history
- Update profile
- Toggle notification preference

## How to Compile & Run

```
cd src
javac library/*.java -d ../bin
cd ../bin
java library.Main
```

## Demo data (pre-loaded)

- 3 sample books
- 1 demo member: email `demo@example.com`, password `demo123`

## Structure

```
src/library/
  Book.java          - Book model
  Member.java        - Member model
  Transaction.java   - Borrow/return transaction model
  Library.java       - Core business logic
  Main.java          - Console UI (entry point)
```
