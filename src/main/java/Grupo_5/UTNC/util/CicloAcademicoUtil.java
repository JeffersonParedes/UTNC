package Grupo_5.UTNC.util;

public class CicloAcademicoUtil {
    public static boolean esValido(String ciclo) {
        if (ciclo == null) return false;
        return ciclo.matches("\\d{4}-[A-Za-z0-9]+");
    }
}
