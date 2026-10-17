package co.edu.uniquindio.techsolutions.model.adapter;

/**
 * Clase externa heredada (Adaptee en el patrón Adapter) perteneciente al proveedor externo.
 * Recibe un código numérico de especialidad y retorna una cadena separada por '|'.
 * Esta clase simula una biblioteca de terceros cuya firma no debe ser modificada.
 */
public class LegacySupport {

    /**
     * Verifica la disponibilidad de un técnico según el código numérico de especialidad:
     * 1 = Mantenimiento, 2 = Redes, 3 = Recuperación, 4 = Seguridad (sin técnico disponible).
     *
     * @param codigoEspecialidad código entero de la especialidad
     * @return cadena con formato "ID_TECNICO|ESTADO|COSTO_DESPLAZAMIENTO"
     */
    public String verificarDisponibilidadTecnico(int codigoEspecialidad) {
        return switch (codigoEspecialidad) {
            case 1 -> "TEC-105|DISPONIBLE|30000";
            case 2 -> "TEC-208|DISPONIBLE|35000";
            case 3 -> "TEC-312|DISPONIBLE|28000";
            default -> "SIN_TECNICO|NO_DISPONIBLE|0";
        };
    }
}
