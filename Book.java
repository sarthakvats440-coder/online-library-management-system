package library;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private int totalCopies;
    private int availableCopies;

    public Book(String bookId, String title, String author, String isbn, String genre, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    @Override
    public String toString() {
        return String.format("[%s] %s by %s | ISBN:%s | Genre:%s | Available:%d/%d",
                bookId, title, author, isbn, genre, availableCopies, totalCopies);
    }
}
