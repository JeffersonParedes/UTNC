package Grupo_5.UTNC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import Grupo_5.UTNC.jpa.entity.Carrera;
import Grupo_5.UTNC.jpa.entity.Curso;
import Grupo_5.UTNC.jpa.repository.CarreraRepository;
import Grupo_5.UTNC.jpa.repository.CursoRepository;

@SpringBootApplication
public class UtncApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UtncApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UtncApplication.class, args);
    }

    @Bean
    @Transactional
    public org.springframework.boot.CommandLineRunner seedData(CarreraRepository carreraRepository, CursoRepository cursoRepository) {
        return args -> {
            Carrera carrera = carreraRepository.findByNombreCarreraIgnoreCase("Ingeniería de Sistemas").orElseGet(() -> {
                Carrera c = new Carrera();
                c.setCodigoCarrera("IS01");
                c.setNombreCarrera("Ingeniería de Sistemas");
                c.setDuracionSemestres(10);
                c.setTituloOtorgado("Ingeniero de Sistemas");
                c.setEstado("activo");
                return carreraRepository.save(c);
            });

            if (cursoRepository.findByCarrera_IdCarreraAndSemestre(carrera.getIdCarrera(), 1).isEmpty()) {
                Curso c1 = new Curso();
                c1.setCarrera(carrera);
                c1.setCodigoCurso("IS101");
                c1.setNombreCurso("Introducción a la Programación");
                c1.setSemestre(1);
                c1.setCreditos(4);
                c1.setHorasSemanales(6);
                cursoRepository.save(c1);

                Curso c2 = new Curso();
                c2.setCarrera(carrera);
                c2.setCodigoCurso("IS102");
                c2.setNombreCurso("Matemática Básica");
                c2.setSemestre(1);
                c2.setCreditos(3);
                c2.setHorasSemanales(4);
                cursoRepository.save(c2);
            }
        };
    }

}
