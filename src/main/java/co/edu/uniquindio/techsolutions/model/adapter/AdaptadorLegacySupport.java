package co.edu.uniquindio.techsolutions.model.adapter;

import co.edu.uniquindio.techsolutions.model.InfoTecnico;

public class AdaptadorLegacySupport implements IProveedorTecnico {

    private LegacySupport legacySupport;

    public AdaptadorLegacySupport() {
        this.legacySupport = new LegacySupport();
    }

    public AdaptadorLegacySupport(LegacySupport legacySupport) {
        this.legacySupport = legacySupport;
    }

    @Override
    public InfoTecnico consultarDisponibilidad(String nombreEspecialidad) {
        if (nombreEspecialidad == null || nombreEspecialidad.isBlank()) {
            throw new IllegalArgumentException("El nombre de la especialidad es obligatorio.");
        }

        String especialidad = nombreEspecialidad.trim().toUpperCase();
        int codigo = switch (especialidad) {
            case "MANTENIMIENTO" -> 1;
            case "REDES" -> 2;
            case "RECUPERACION" -> 3;
            case "SEGURIDAD" -> 4;
            default -> 0;
        };

        String respuesta = legacySupport.verificarDisponibilidadTecnico(codigo);
        String[] partes = respuesta.split("\\|");

        String idTecnico = partes[0];
        boolean disponible = partes[1].equalsIgnoreCase("DISPONIBLE");
        double costoDesplazamiento = Double.parseDouble(partes[2]);

        return new InfoTecnico(idTecnico, especialidad, disponible, costoDesplazamiento);
    }
}
