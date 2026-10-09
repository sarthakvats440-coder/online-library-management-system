package library;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private String memberId;
    private String name;
    private String email;
    private String password;
    private boolean notificationsEnabled;
    private List<Transaction> borrowingHistory;

    public Member(String memberId, String name, String email, String password) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.notificationsEnabled = true;
        this.borrowingHistory = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isNotificationsEnabled() { return notificationsEnabled; }
    public void setNotificationsEnabled(boolean notificationsEnabled) { this.notificationsEnabled = notificationsEnabled; }
    public List<Transaction> getBorrowingHistory() { return borrowingHistory; }
    public void addTransaction(Transaction t) { borrowingHistory.add(t); }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | Notifications:%s", memberId, name, email,
                notificationsEnabled ? "ON" : "OFF");
    }
}
