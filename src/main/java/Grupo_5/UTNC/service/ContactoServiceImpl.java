package Grupo_5.UTNC.service;

import Grupo_5.UTNC.dto.ContactoDTO;
import Grupo_5.UTNC.jpa.entity.Contacto;
import Grupo_5.UTNC.jpa.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactoServiceImpl implements ContactoService {

    // Inyectamos el repositorio, pero solo se usará cuando se active la lógica de BD.
    private final ContactoRepository contactoRepository;

    @Autowired
    public ContactoServiceImpl(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    @Override
    public void procesarMensaje(ContactoDTO dto) {

        // --- LÓGICA DE BASE DE DATOS DESACTIVADA TEMPORALMENTE ---
        /*
        Contacto contacto = new Contacto();
        contacto.setName(dto.getName());
        contacto.setEmail(dto.getEmail());
        contacto.setCarrera(dto.getCarrera());
        contacto.setDni(dto.getDni());
        contacto.setAsunto(dto.getSubject());
        contacto.setMensaje(dto.getMessage());

        contactoRepository.save(contacto);
        */

        // Simulación: Imprimir los datos en la consola.
        // En un futuro, aquí también podría ir la lógica para enviar un email.
        System.out.println("*****************************************************");
        System.out.println("MENSAJE DE CONTACTO RECIBIDO (simulación):");
        System.out.println(dto.toString());
        System.out.println("*****************************************************");
    }
}
