package BaiTap1;

import java.util.Scanner;

public class Book {

    private int id;
    private String title;
    private String author;
    private double price;

    public Book() {
    }

    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma sach: ");
        id = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap ten sach: ");
        title = sc.nextLine();
        System.out.print("Nhap tac gia: ");
        author = sc.nextLine();
        System.out.print("Nhap don gia: ");
        price = Double.parseDouble(sc.nextLine());
    }

    public void outPut() {
        System.out.println(
                "Book{id=" + id
                + ", title='" + title
                + "', author='" + author
                + "', price=" + price + "}"
        );
    }
}
