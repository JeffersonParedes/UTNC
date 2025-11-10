package Grupo_5.UTNC.service;

import Grupo_5.UTNC.dto.LoginDTO;
import Grupo_5.UTNC.dto.RegistroDTO;

public interface AuthService {
    
    String registrarUsuario(RegistroDTO registroDTO);

    String iniciarSesion(LoginDTO loginDTO);
}
