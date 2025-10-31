package Grupo_5.UTNC.service;

import Grupo_5.UTNC.dto.InscripcionDTO;
import org.springframework.stereotype.Service;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    // Se ha eliminado la dependencia a InscripcionRepository temporalmente
    // para permitir que la aplicación se ejecute sin una base de datos configurada.

    public InscripcionServiceImpl() {
        // Constructor vacío
    }

    @Override
    public void registrarInscripcion(InscripcionDTO dto) {
        // Simulación: Imprimir los datos en la consola para verificar que la API funciona.
        // El desarrollador de backend deberá añadir aquí la lógica de guardado real.
        System.out.println("*****************************************************");
        System.out.println("INSCRIPCIÓN RECIBIDA (simulación, no guardado en BD):");
        System.out.println(dto.toString());
        System.out.println("*****************************************************");
    }
}