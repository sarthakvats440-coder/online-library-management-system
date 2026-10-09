package library;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private String bookId;
    private String bookTitle;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status; // BORROWED, RETURNED, OVERDUE

    public Transaction(String transactionId, String bookId, String bookTitle, String memberId,
                        LocalDate borrowDate, LocalDate dueDate) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = "BORROWED";
    }

    public String getTransactionId() { return transactionId; }
    public String getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getMemberId() { return memberId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isOverdue() {
        return status.equals("BORROWED") && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return String.format("TxnID:%s | Book:%s | Member:%s | Borrowed:%s | Due:%s | Returned:%s | Status:%s",
                transactionId, bookTitle, memberId, borrowDate,
                dueDate, (returnDate == null ? "-" : returnDate), status);
    }
}
