package econo.buddybridge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {

        String contextPath = servletContext.getContextPath();
        Server server = new Server().url(contextPath);

        return new OpenAPI()
                .servers(List.of(server))
                .info(swaggerInfo());
    }

    private Info swaggerInfo() {
        License license = new License();
        license.setUrl("https://github.com/JNU-econovation/BuddyBridge_BE");
        license.setName("Buddy Bridge");

        return new Info()
                .version("v0.0.1")
                .title("Buddy Bridge API문서")
                .description("Buddy Bridge의 API 문서 입니다.")
                .license(license);
    }
}
