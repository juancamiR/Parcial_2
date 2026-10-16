package co.edu.uniquindio.techsolutions;

import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.TipoServicio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TechSolutionsApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        cargarDatosIniciales();

        FXMLLoader fxmlLoader = new FXMLLoader(
                TechSolutionsApp.class.getResource("/co/edu/uniquindio/techsolutions/view/PrincipalView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 1150, 740);
        stage.setTitle("TechSolutions - Gestión de Servicios Tecnológicos");
        stage.setScene(scene);
        stage.show();
    }

    public static void cargarDatosIniciales() {
        TechSolutions techSolutions = TechSolutions.getInstance();
        if (!techSolutions.obtenerClientes().isEmpty()) {
            return;
        }

        // 3 clientes precargados
        techSolutions.registrarCliente("1094950111", "Carlos Andrés Ramírez", "carlos@pyme.co");
        techSolutions.registrarCliente("1094950222", "Laura Sofía Gómez", "laura@empresa.com");
        techSolutions.registrarCliente("900123456-1", "Inversiones Cafeteras S.A.S.", "contacto@inversiones.co");

        // 4 servicios base iniciales
        techSolutions.registrarServicio(TipoServicio.MANTENIMIENTO_PREVENTIVO,
                "SRV-001", "Mantenimiento Preventivo", "Limpieza y diagnóstico preventivo");
        techSolutions.registrarServicio(TipoServicio.MANTENIMIENTO_CORRECTIVO,
                "SRV-002", "Mantenimiento Correctivo", "Reparación de fallas de hardware o software");
        techSolutions.registrarServicio(TipoServicio.INSTALACION_REDES,
                "SRV-003", "Instalación de Redes", "Cableado estructurado y configuración de red");
        techSolutions.registrarServicio(TipoServicio.RECUPERACION_INFORMACION,
                "SRV-004", "Recuperación de Información", "Restauración de datos desde discos dañados");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
