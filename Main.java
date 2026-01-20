package BaiTap1;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("----------CHUONG TRINH QUAN LY SACH----------");
            System.out.println("1. Them 1 cuon sach");
            System.out.println("2. Xoa 1 cuon sach");
            System.out.println("3. Thay doi sach");
            System.out.println("4. Xuat thong tin");
            System.out.println("5. Tim sach lap trinh");
            System.out.println("6. Lay toi da K sach theo gia");
            System.out.println("7. Tim sach theo tac gia");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            chon = sc.nextInt();
            sc.nextLine();
            switch (chon) {
                case 1: {
                    Book b = new Book();
                    b.input();
                    listBook.add(b);
                    break;
                }
                case 2: {
                    System.out.print("Nhap ma sach can xoa: ");
                    int id = sc.nextInt();
                    listBook.removeIf(b -> b.getId() == id);
                    break;
                }
                case 3: {
                    System.out.print("nhap ma sach can sua: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    Book find = listBook.stream()
                            .filter(b -> b.getId() == id)
                            .findFirst()
                            .orElse(null);
                    if (find != null) {
                        find.input();
                    }
                    break;
                }
                case 4: {
                    listBook.forEach(Book::outPut);
                    break;
                }
                case 5: {
                    List<Book> list5 = listBook.stream()
                            .filter(b -> b.getTitle().toLowerCase().contains("lap trinh"))
                            .collect(Collectors.toList());

                    list5.forEach(Book::outPut);
                    break;
                }
                case 6: {
                    System.out.print("Nhap K: ");
                    int k = sc.nextInt();
                    System.out.print("Nhap gia P: ");
                    double p = sc.nextDouble();
                    listBook.stream()
                            .filter(b -> b.getPrice() <= p)
                            .limit(k)
                            .forEach(Book::outPut);
                    break;
                }
                case 7: {
                    System.out.print("Nhap so luong tac gia: ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    Set<String> authorSet = new HashSet<>();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Nhap ten tac gia: ");
                        authorSet.add(sc.nextLine());
                    }
                    listBook.stream()
                            .filter(b -> authorSet.contains(b.getAuthor()))
                            .forEach(Book::outPut);
                    break;
                }
            }
        } while (chon != 0);
    }
}
