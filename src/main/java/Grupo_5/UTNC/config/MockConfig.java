package Grupo_5.UTNC.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Configuración para el perfil 'mock' que deshabilita la configuración automática
 * de base de datos y JPA cuando se usan repositorios mock en memoria.
 */
@Configuration
@Profile("mock")
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
public class MockConfig {
    
    // Esta clase está vacía intencionalmente.
    // Su propósito es aplicar las exclusiones de autoconfiguración
    // solo cuando el perfil 'mock' está activo.
    
}