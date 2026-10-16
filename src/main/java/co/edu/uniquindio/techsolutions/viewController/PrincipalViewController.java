package co.edu.uniquindio.techsolutions.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class PrincipalViewController {

    @FXML
    private TabPane tabPanePrincipal;

    @FXML
    private ServiciosViewController serviciosViewController;

    @FXML
    private PaquetesViewController paquetesViewController;

    @FXML
    private SolicitudesViewController solicitudesViewController;

    @FXML
    private ConsultasTecnicosViewController consultasTecnicosViewController;

    @FXML
    public void initialize() {
        if (tabPanePrincipal != null) {
            tabPanePrincipal.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
                refrescarTodasLasVistas();
            });
        }
    }

    public void refrescarTodasLasVistas() {
        if (serviciosViewController != null) {
            serviciosViewController.refrescarDatos();
        }
        if (paquetesViewController != null) {
            paquetesViewController.refrescarDatos();
        }
        if (solicitudesViewController != null) {
            solicitudesViewController.refrescarDatos();
        }
        if (consultasTecnicosViewController != null) {
            consultasTecnicosViewController.refrescarDatos();
        }
    }
}
