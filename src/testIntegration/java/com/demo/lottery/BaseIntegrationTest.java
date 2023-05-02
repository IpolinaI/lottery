package com.demo.lottery;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class BaseIntegrationTest {
    public static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("library/postgres:12-alpine")
                    .withUsername("test_user")
                    .withPassword("test_password")
                    .withDatabaseName("lottery_db")
                    .withExposedPorts(5432)
                    .withReuse(true);

    @BeforeAll
    public static void beforeAll() {
        POSTGRES_CONTAINER.start();
    }

    @DynamicPropertySource
    private static void dbProperties(DynamicPropertyRegistry registry) {
        postgresqlProperties().entrySet().forEach(
                entry -> registry.add(entry.getKey(), entry::getValue)
        );
    }

    private static Map<String, String> postgresqlProperties() {
        return Map.of(
                "spring.datasource.url", POSTGRES_CONTAINER.getJdbcUrl(),
                "spring.datasource.username", POSTGRES_CONTAINER.getUsername(),
                "spring.datasource.password", POSTGRES_CONTAINER.getPassword()
        );
    }
}
