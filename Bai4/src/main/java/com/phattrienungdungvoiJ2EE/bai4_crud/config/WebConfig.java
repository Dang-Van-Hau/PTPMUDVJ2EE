package com.phattrienungdungvoiJ2EE.bai4_crud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve images từ src/main/resources/static/images
        Path uploadPath = Paths.get("src/main/resources/static/images");
        String uploadPathString = uploadPath.toFile().getAbsolutePath();
        
        // Đảm bảo có dấu / ở cuối
        String filePath = uploadPathString.replace("\\", "/");
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + filePath, "classpath:/static/images/");
    }
}
