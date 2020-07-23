package io.github.stepanovnickolay.pgindexhealthwrapper

import io.github.mfvanek.pg.common.health.DatabaseHealthFactory
import io.github.mfvanek.pg.common.health.DatabaseHealthFactoryImpl
import io.github.mfvanek.pg.common.health.logger.Exclusions
import io.github.mfvanek.pg.common.health.logger.HealthLogger
import io.github.mfvanek.pg.common.health.logger.SimpleHealthLogger
import io.github.mfvanek.pg.common.maintenance.MaintenanceFactoryImpl
import io.github.mfvanek.pg.connection.*
import io.github.mfvanek.pg.model.MemoryUnit
import io.github.mfvanek.pg.model.PgContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.util.function.Consumer

@SpringBootApplication
class PgHealthIndexWrapperApplication

fun main(args: Array<String>) {
    runApplication<PgHealthIndexWrapperApplication>(*args)
}

@Component
class PgIndexHealth(
        private val jdbcTemplate: JdbcTemplate,
        @Value("\${spring.datasource.url:#{null}}") private val datasourceUrl: String? = null,
        @Value("\${spring.datasource.username:#{null}}") private val datasourceUsername: String? = null,
        @Value("\${spring.datasource.password:#{null}}") private val datasourcePass: String? = null
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener(ApplicationReadyEvent::class)
    fun scan() {
        requireNotNull(datasourceUrl) { "spring.datasource.url must be specified" }
        requireNotNull(datasourceUsername) { "spring.datasource.username must be specified" }
        requireNotNull(datasourcePass) { "spring.datasource.password must be specified" }
        val credentials = ConnectionCredentials.ofUrl(datasourceUrl, datasourceUsername, datasourcePass)
        val connectionFactory: HighAvailabilityPgConnectionFactory = HighAvailabilityPgConnectionFactoryImpl(
                PgConnectionFactoryImpl(), PrimaryHostDeterminerImpl())
        val databaseHealthFactory: DatabaseHealthFactory = DatabaseHealthFactoryImpl(MaintenanceFactoryImpl())
        val exclusions = Exclusions.builder()
                .withIndexSizeThreshold(1, MemoryUnit.MB)
                .withTableSizeThreshold(1, MemoryUnit.MB)
                .build()
        val healthLogger: HealthLogger = SimpleHealthLogger(credentials, connectionFactory, databaseHealthFactory)
        logger.info("Index health from db")
        logger.info("########################################")
        getAllSchemas().forEach {
            logger.info("--- Schema $it ---")
            healthLogger.logAll(exclusions, PgContext.of(it)).forEach(Consumer(logger::warn))
        }
    }

    private fun getAllSchemas(): List<String> =
            jdbcTemplate.queryForList("SELECT schema_name FROM information_schema.schemata", String::class.java)
                    .filter { !it.startsWith("pg_") && !it.startsWith("information_schema") }
}