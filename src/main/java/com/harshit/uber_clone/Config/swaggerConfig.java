package com.harshit.uber_clone.Config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.annotations.Operation;

@Configuration
public class swaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        Info info = new Info();
        info.setTitle("Uber Clone Backend API");
        info.setVersion("1.0");
        info.setDescription(
                "Ride Booking Backend built using Spring Boot, PostgreSQL, Hibernate and JPA"
        );
        Contact contact = new Contact();
        contact.setName("Harshit Kumar");
        contact.setEmail("your-email@example.com");
        info.setContact(contact);

        new OpenAPI().info(info);
        return new OpenAPI()
                .info(info);

    }

}
