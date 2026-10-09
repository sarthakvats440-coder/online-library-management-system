package library;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        System.out.println("========================================");
        System.out.println(" ONLINE LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Login as Librarian");
            System.out.println("2. Login as Member");
            System.out.println("3. Register as new Member");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": librarianMenu(); break;
                case "2": memberLogin(); break;
                case "3": registerMember(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }

    // ---------------- Librarian ----------------
    private static void librarianMenu() {
        System.out.println("\n--- Librarian Dashboard ---");
        boolean back = false;
        while (!back) {
            System.out.println("\n1. Add Book");
            System.out.println("2. Edit Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View All Books");
            System.out.println("5. Add Member");
            System.out.println("6. Edit Member");
            System.out.println("7. Delete Member");
            System.out.println("8. View All Members");
            System.out.println("9. View All Transactions");
            System.out.println("10. Generate Inventory Report");
            System.out.println("11. Send Due-Date Reminders");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            String c = sc.nextLine().trim();

            switch (c) {
                case "1": addBookFlow(); break;
                case "2": editBookFlow(); break;
                case "3": deleteBookFlow(); break;
                case "4": viewAllBooks(); break;
                case "5": addMemberFlow(); break;
                case "6": editMemberFlow(); break;
                case "7": deleteMemberFlow(); break;
                case "8": viewAllMembers(); break;
                case "9": viewAllTransactions(); break;
                case "10": System.out.println(library.generateInventoryReport()); break;
                case "11": library.sendDueDateReminders(); break;
                case "0": back = true; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void addBookFlow() {
        System.out.print("Title: "); String title = sc.nextLine();
        System.out.print("Author: "); String author = sc.nextLine();
        System.out.print("ISBN: "); String isbn = sc.nextLine();
        System.out.print("Genre: "); String genre = sc.nextLine();
        System.out.print("Number of copies: "); int copies = readInt();
        Book b = library.addBook(title, author, isbn, genre, copies);
        System.out.println("Book added: " + b);
    }

    private static void editBookFlow() {
        System.out.print("Book ID to edit: "); String id = sc.nextLine();
        System.out.print("New title (blank to skip): "); String title = emptyToNull(sc.nextLine());
        System.out.print("New author (blank to skip): "); String author = emptyToNull(sc.nextLine());
        System.out.print("New ISBN (blank to skip): "); String isbn = emptyToNull(sc.nextLine());
        System.out.print("New genre (blank to skip): "); String genre = emptyToNull(sc.nextLine());
        boolean ok = library.editBook(id, title, author, isbn, genre);
        System.out.println(ok ? "Book updated." : "Book not found.");
    }

    private static void deleteBookFlow() {
        System.out.print("Book ID to delete: "); String id = sc.nextLine();
        System.out.println(library.deleteBook(id) ? "Book deleted." : "Book not found.");
    }

    private static void viewAllBooks() {
        Collection<Book> all = library.getAllBooks();
        if (all.isEmpty()) { System.out.println("No books in catalog."); return; }
        for (Book b : all) System.out.println(b);
    }

    private static void addMemberFlow() {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Password: "); String pw = sc.nextLine();
        Member m = library.addMember(name, email, pw);
        System.out.println("Member added: " + m);
    }

    private static void editMemberFlow() {
        System.out.print("Member ID to edit: "); String id = sc.nextLine();
        System.out.print("New name (blank to skip): "); String name = emptyToNull(sc.nextLine());
        System.out.print("New email (blank to skip): "); String email = emptyToNull(sc.nextLine());
        boolean ok = library.editMember(id, name, email);
        System.out.println(ok ? "Member updated." : "Member not found.");
    }

    private static void deleteMemberFlow() {
        System.out.print("Member ID to delete: "); String id = sc.nextLine();
        System.out.println(library.deleteMember(id) ? "Member deleted." : "Member not found.");
    }

    private static void viewAllMembers() {
        Collection<Member> all = library.getAllMembers();
        if (all.isEmpty()) { System.out.println("No members registered."); return; }
        for (Member m : all) System.out.println(m);
    }

    private static void viewAllTransactions() {
        List<Transaction> all = library.getAllTransactions();
        if (all.isEmpty()) { System.out.println("No transactions yet."); return; }
        for (Transaction t : all) System.out.println(t);
    }

    // ---------------- Member ----------------
    private static void registerMember() {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Password: "); String pw = sc.nextLine();
        Member m = library.addMember(name, email, pw);
        System.out.println("Registration successful. Your Member ID is " + m.getMemberId());
    }

    private static void memberLogin() {
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Password: "); String pw = sc.nextLine();
        Member member = library.authenticateMember(email, pw);
        if (member == null) {
            System.out.println("Invalid credentials.");
            return;
        }
        System.out.println("Welcome, " + member.getName() + "!");
        memberMenu(member);
    }

    private static void memberMenu(Member member) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Member Dashboard (" + member.getName() + ") ---");
            System.out.println("1. Search Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Borrowing History");
            System.out.println("5. Update Profile");
            System.out.println("6. Toggle Notifications (currently " + (member.isNotificationsEnabled() ? "ON" : "OFF") + ")");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            String c = sc.nextLine().trim();

            switch (c) {
                case "1": searchBooksFlow(); break;
                case "2": borrowBookFlow(member); break;
                case "3": returnBookFlow(); break;
                case "4": viewHistoryFlow(member); break;
                case "5": updateProfileFlow(member); break;
                case "6": member.setNotificationsEnabled(!member.isNotificationsEnabled()); break;
                case "0": back = true; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void searchBooksFlow() {
        System.out.print("Search keyword (title/author/genre): "); String kw = sc.nextLine();
        List<Book> results = library.searchBooks(kw);
        if (results.isEmpty()) { System.out.println("No matching books found."); return; }
        for (Book b : results) System.out.println(b);
    }

    private static void borrowBookFlow(Member member) {
        System.out.print("Book ID to borrow: "); String bookId = sc.nextLine();
        System.out.println(library.borrowBook(bookId, member.getMemberId()));
    }

    private static void returnBookFlow() {
        System.out.print("Transaction ID: "); String txnId = sc.nextLine();
        System.out.println(library.returnBook(txnId));
    }

    private static void viewHistoryFlow(Member member) {
        List<Transaction> history = library.getBorrowingHistory(member.getMemberId());
        if (history.isEmpty()) { System.out.println("No borrowing history yet."); return; }
        for (Transaction t : history) System.out.println(t);
    }

    private static void updateProfileFlow(Member member) {
        System.out.print("New name (blank to skip): "); String name = sc.nextLine();
        System.out.print("New email (blank to skip): "); String email = sc.nextLine();
        if (!name.isBlank()) member.setName(name);
        if (!email.isBlank()) member.setEmail(email);
        System.out.println("Profile updated.");
    }

    // ---------------- Helpers ----------------
    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }

    private static void seedData() {
        library.addBook("Clean Code", "Robert C. Martin", "9780132350884", "Programming", 3);
        library.addBook("The Pragmatic Programmer", "Andrew Hunt", "9780201616224", "Programming", 2);
        library.addBook("Atomic Habits", "James Clear", "9780735211292", "Self-Help", 4);
        library.addMember("Demo Member", "demo@example.com", "demo123");
    }
}
