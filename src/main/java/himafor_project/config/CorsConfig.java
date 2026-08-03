package himafor_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000", // Izin untuk testing di laptop
                        "https://himafor-frontend.vercel.app", // Izin khusus Vercel kamu
                        "https://himafor-frontend-r2rpuoznc-kelompok-19.vercel.app" // Tambahkan link Vercel utama kamu di sini jika nanti linknya berubah
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}