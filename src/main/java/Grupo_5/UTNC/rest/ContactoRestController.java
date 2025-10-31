package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.ContactoDTO;
import Grupo_5.UTNC.service.ContactoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/contacto")
public class ContactoRestController {

    private final ContactoService contactoService;

    @Autowired
    public ContactoRestController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> recibirMensaje(@Valid @RequestBody ContactoDTO contactoDTO) {
        contactoService.procesarMensaje(contactoDTO);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Mensaje recibido exitosamente. Nos pondremos en contacto contigo pronto.");

        return ResponseEntity.ok(response);
    }
}
