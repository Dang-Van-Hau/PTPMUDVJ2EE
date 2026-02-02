package com.phattrienungdungvoiJ2EE.bai4_crud.Service;

import com.phattrienungdungvoiJ2EE.bai4_crud.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    List<Product> listProduct = new ArrayList<>();
    
    public List<Product> getAll() {
        return listProduct;
    }
    
    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(p -> p.getId())
                .max()
                .orElse(0);
        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }
    
    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setPrice(editProduct.getPrice());
            find.setName(editProduct.getName());
            if (editProduct.getCategory() != null) {
                find.setCategory(editProduct.getCategory());
            }
            if (editProduct.getImage() != null && !editProduct.getImage().isEmpty()) {
                find.setImage(editProduct.getImage());
            }
        }
    }
    
    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        String contentType = imageProduct.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }
        
        if (!imageProduct.isEmpty()) {
            try {
                String newFileName = UUID.randomUUID().toString() + "_" + imageProduct.getOriginalFilename();
                
                // Đọc toàn bộ file vào byte array
                byte[] imageBytes = imageProduct.getBytes();
                
                // Lưu vào src/main/resources/static/images (để giữ lại khi restart)
                Path dirImagesSrc = Paths.get("src/main/resources/static/images");
                if (!Files.exists(dirImagesSrc)) {
                    Files.createDirectories(dirImagesSrc);
                }
                Path pathFileUploadSrc = dirImagesSrc.resolve(newFileName);
                Files.write(pathFileUploadSrc, imageBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                
                // Lưu vào target/classes/static/images (để hiển thị ngay khi chạy)
                Path dirImagesTarget = Paths.get("target/classes/static/images");
                if (!Files.exists(dirImagesTarget)) {
                    Files.createDirectories(dirImagesTarget);
                }
                Path pathFileUploadTarget = dirImagesTarget.resolve(newFileName);
                Files.write(pathFileUploadTarget, imageBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                
                newProduct.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi lưu hình ảnh: " + e.getMessage());
            }
        }
    }
    
    public void delete(int id) {
        listProduct.removeIf(p -> p.getId() == id);
    }
}
