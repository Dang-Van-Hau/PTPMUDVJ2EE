package phattrienungdungvoij2ee.bai5_qlsp.config;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String projectRoot = System.getProperty("user.dir");
		File imagesDir = Paths.get(projectRoot, "src/main/resources/static/images").toFile();
		String imagesPath = imagesDir.getAbsolutePath();

		if (!imagesPath.endsWith(File.separator) && !imagesPath.endsWith("/")) {
			imagesPath += "/";
		}
		imagesPath = imagesPath.replace("\\", "/");

		registry.addResourceHandler("/images/**")
				.addResourceLocations("file:" + imagesPath, "classpath:/static/images/")
				.setCachePeriod(3600);
	}
}
