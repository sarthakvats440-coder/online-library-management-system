package library;

import java.time.LocalDate;
import java.util.*;

public class Library {
    private Map<String, Book> books = new LinkedHashMap<>();
    private Map<String, Member> members = new LinkedHashMap<>();
    private List<Transaction> transactions = new ArrayList<>();
    private int bookCounter = 1;
    private int memberCounter = 1;
    private int txnCounter = 1;
    private static final int BORROW_DAYS = 14;

    // ---------- Librarian: Book Management ----------
    public Book addBook(String title, String author, String isbn, String genre, int copies) {
        String id = "B" + String.format("%03d", bookCounter++);
        Book book = new Book(id, title, author, isbn, genre, copies);
        books.put(id, book);
        return book;
    }

    public boolean editBook(String bookId, String title, String author, String isbn, String genre) {
        Book b = books.get(bookId);
        if (b == null) return false;
        if (title != null) b.setTitle(title);
        if (author != null) b.setAuthor(author);
        if (isbn != null) b.setIsbn(isbn);
        if (genre != null) b.setGenre(genre);
        return true;
    }

    public boolean deleteBook(String bookId) {
        return books.remove(bookId) != null;
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    // ---------- Librarian: Member Management ----------
    public Member addMember(String name, String email, String password) {
        String id = "M" + String.format("%03d", memberCounter++);
        Member m = new Member(id, name, email, password);
        members.put(id, m);
        return m;
    }

    public boolean editMember(String memberId, String name, String email) {
        Member m = members.get(memberId);
        if (m == null) return false;
        if (name != null) m.setName(name);
        if (email != null) m.setEmail(email);
        return true;
    }

    public boolean deleteMember(String memberId) {
        return members.remove(memberId) != null;
    }

    public Collection<Member> getAllMembers() {
        return members.values();
    }

    public Member findMember(String memberId) {
        return members.get(memberId);
    }

    public Member authenticateMember(String email, String password) {
        for (Member m : members.values()) {
            if (m.getEmail().equalsIgnoreCase(email) && m.getPassword().equals(password)) {
                return m;
            }
        }
        return null;
    }

    // ---------- Book Search (Member) ----------
    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(k)
                    || b.getAuthor().toLowerCase().contains(k)
                    || b.getGenre().toLowerCase().contains(k)) {
                results.add(b);
            }
        }
        return results;
    }

    // ---------- Transaction Management: Borrow / Return ----------
    public String borrowBook(String bookId, String memberId) {
        Book book = books.get(bookId);
        Member member = members.get(memberId);
        if (book == null) return "Book not found.";
        if (member == null) return "Member not found.";
        if (book.getAvailableCopies() <= 0) return "No copies available for this book.";

        String txnId = "T" + String.format("%04d", txnCounter++);
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(BORROW_DAYS);
        Transaction txn = new Transaction(txnId, bookId, book.getTitle(), memberId, borrowDate, dueDate);
        transactions.add(txn);
        member.addTransaction(txn);
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        notifyMember(member, "Book borrowed: " + book.getTitle() + ". Due on " + dueDate);
        return "Book borrowed successfully. Transaction ID: " + txnId + " | Due date: " + dueDate;
    }

    public String returnBook(String transactionId) {
        Transaction txn = findTransaction(transactionId);
        if (txn == null) return "Transaction not found.";
        if (txn.getStatus().equals("RETURNED")) return "Book already returned.";

        txn.setReturnDate(LocalDate.now());
        txn.setStatus("RETURNED");
        Book book = books.get(txn.getBookId());
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
        }
        Member member = members.get(txn.getMemberId());
        if (member != null) {
            notifyMember(member, "Book returned: " + txn.getBookTitle());
        }
        return "Book returned successfully.";
    }

    private Transaction findTransaction(String transactionId) {
        for (Transaction t : transactions) {
            if (t.getTransactionId().equals(transactionId)) return t;
        }
        return null;
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public List<Transaction> getBorrowingHistory(String memberId) {
        Member m = members.get(memberId);
        return m == null ? Collections.emptyList() : m.getBorrowingHistory();
    }

    // ---------- Notification Management ----------
    private void notifyMember(Member member, String message) {
        if (member.isNotificationsEnabled()) {
            System.out.println("[NOTIFICATION to " + member.getEmail() + "]: " + message);
        }
    }

    public void sendDueDateReminders() {
        for (Transaction t : transactions) {
            if (t.isOverdue()) {
                Member m = members.get(t.getMemberId());
                if (m != null) {
                    notifyMember(m, "Reminder: '" + t.getBookTitle() + "' was due on " + t.getDueDate() + ". Please return it.");
                }
            }
        }
    }

    // ---------- Inventory / Reports ----------
    public String generateInventoryReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== INVENTORY REPORT =====\n");
        int totalBooks = 0, totalAvailable = 0;
        for (Book b : books.values()) {
            sb.append(b).append("\n");
            totalBooks += b.getTotalCopies();
            totalAvailable += b.getAvailableCopies();
        }
        sb.append("-----------------------------\n");
        sb.append("Total copies: ").append(totalBooks).append("\n");
        sb.append("Available copies: ").append(totalAvailable).append("\n");
        sb.append("Currently borrowed: ").append(totalBooks - totalAvailable).append("\n");

        long overdueCount = transactions.stream().filter(Transaction::isOverdue).count();
        sb.append("Overdue transactions: ").append(overdueCount).append("\n");
        return sb.toString();
    }
}
