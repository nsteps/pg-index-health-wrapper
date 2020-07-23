package io.github.stepanovnickolay.pgindexhealthwrapper

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])
abstract class AbstractIntegrationTest {
    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(context: ConfigurableApplicationContext) {
            context.environment.propertySources.addFirst(MapPropertySource(
                    "testcontainers", properties
            ))
        }

        companion object {
            private var postgres: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>()
            val properties: Map<String, String>
                get() {
                    Startables.deepStart(listOf(postgres)).join()
                    return java.util.Map.of(
                            "spring.datasource.url", postgres.getJdbcUrl(),
                            "spring.datasource.username", postgres.getUsername(),
                            "spring.datasource.password", postgres.getPassword()
                    )
                }
        }
    }
}