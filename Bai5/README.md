# Bai5 – Môi trường kiểm tra / Sản phẩm

Spring Boot 3.3.5, Java 17, Security + JPA + Thymeleaf + MySQL.

## Chạy ứng dụng

1. **Tạo database MySQL:**  
   `CREATE DATABASE bai5_db;`

2. **Sửa mật khẩu** (nếu cần) trong `src/main/resources/application.properties`:  
   `spring.datasource.password=...`

3. **Chạy:**  
   `mvnw.cmd spring-boot:run` (Windows) hoặc `./mvnw spring-boot:run` (Linux/Mac)

4. Mở trình duyệt: **http://localhost:8082**  
   - Đăng nhập: **admin** / **user** (mật khẩu giống nhau, mặc định: `password`)

## Cấu trúc

- **Security:** ROLE_ADMIN (thêm/sửa/xóa sản phẩm), ROLE_USER (xem sản phẩm, trang Order).
- **JPA:** Account, Role, Product. Dữ liệu mẫu trong `data.sql`.
- **Upload ảnh:** thư mục `uploads/` (có trong .gitignore).
