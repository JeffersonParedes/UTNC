package Grupo_5.UTNC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class DatabaseConnectivityTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void dataSourceIsAvailable() throws Exception {
        assertNotNull(dataSource, "DataSource no debe ser nulo");
        try (var conn = dataSource.getConnection()) {
            assertNotNull(conn, "Conexión a la BD no debe ser nula");
            assertNotNull(conn.getMetaData(), "Metadata de la BD no debe ser nula");
        }
    }

    @Test
    void canExecuteSimpleQuery() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertEquals(1, result, "La consulta simple debe devolver 1");
    }
}
