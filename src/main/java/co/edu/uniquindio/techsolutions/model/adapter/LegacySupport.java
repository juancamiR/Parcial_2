package co.edu.uniquindio.techsolutions.model.adapter;

//nuestro proveedor externo
public class LegacySupport {


    public String verificarDisponibilidadTecnico(int codigoEspecialidad) {
        return switch (codigoEspecialidad) {
            case 1 -> "TEC-105|DISPONIBLE|30000";
            case 2 -> "TEC-208|DISPONIBLE|35000";
            case 3 -> "TEC-312|DISPONIBLE|28000";
            default -> "SIN_TECNICO|NO_DISPONIBLE|0";
        };
    }
}
