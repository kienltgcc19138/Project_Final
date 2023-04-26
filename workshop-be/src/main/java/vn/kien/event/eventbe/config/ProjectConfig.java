package vn.kien.event.eventbe.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 12:01
 */
@Configuration
@EnableWebMvc
@EnableConfigurationProperties
public class ProjectConfig {

    @Value("${URL_SWAGGER}")
    private String urlSwagger;

    @Bean
    public WebMvcConfigurer customConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer.defaultContentType(MediaType.APPLICATION_JSON);
            }
        };
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        List<Server> servers = new ArrayList<>();

        Server server = new Server();
        //local
        server.setUrl(urlSwagger);
//        server.setUrl("https://khoa-luan-gl.herokuapp.com/");
        server.setDescription("Swagger");
        servers.add(server);
        return new OpenAPI()
                .info(new Info().title("Swagger")
                        .description("Swagger UI")
                        .version("1.0"))
                .servers(servers);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
