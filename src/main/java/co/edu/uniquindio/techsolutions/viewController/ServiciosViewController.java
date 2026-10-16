package co.edu.uniquindio.techsolutions.viewController;

import co.edu.uniquindio.techsolutions.controller.ServiciosController;
import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ServiciosViewController {

    private final ServiciosController serviciosController = new ServiciosController();

    @FXML
    private ComboBox<TipoServicio> cmbTipoServicio;
    @FXML
    private TextField txtCodigoServicio;
    @FXML
    private TextField txtNombreServicio;
    @FXML
    private TextField txtDescripcionServicio;
    @FXML
    private CheckBox chkPresencialNuevo;

    @FXML
    private TableView<Servicio> tblCatalogoServicios;
    @FXML
    private TableColumn<Servicio, String> colCatCodigo;
    @FXML
    private TableColumn<Servicio, String> colCatNombre;
    @FXML
    private TableColumn<Servicio, String> colCatTipo;
    @FXML
    private TableColumn<Servicio, String> colCatDescripcion;
    @FXML
    private TableColumn<Servicio, String> colCatPresencial;
    @FXML
    private TableColumn<Servicio, String> colCatCostoBase;

    @FXML
    private Label lblServicioSeleccionado;
    @FXML
    private CheckBox chkAtencionPrioritaria;
    @FXML
    private CheckBox chkSoporte30Dias;
    @FXML
    private CheckBox chkAtencionDomicilio;
    @FXML
    private Label lblCostoBase;
    @FXML
    private Label lblIncrementoComplementos;
    @FXML
    private Label lblCostoFinal;
    @FXML
    private Label lblRequierePresencial;

    @FXML
    private TableView<IComponenteServicio> tblServiciosPersonalizados;
    @FXML
    private TableColumn<IComponenteServicio, String> colPersCodigo;
    @FXML
    private TableColumn<IComponenteServicio, String> colPersNombre;
    @FXML
    private TableColumn<IComponenteServicio, String> colPersPresencial;
    @FXML
    private TableColumn<IComponenteServicio, String> colPersCostoTotal;

    @FXML
    public void initialize() {
        cmbTipoServicio.setItems(FXCollections.observableArrayList(TipoServicio.values()));
        cmbTipoServicio.getSelectionModel().selectFirst();

        colCatCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo()));
        colCatNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colCatTipo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipoServicio().name()));
        colCatDescripcion.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescripcion()));
        colCatPresencial.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().requiereAtencionPresencial() ? "Sí" : "No"));
        colCatCostoBase.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().calcularCosto()));

        colPersCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo()));
        colPersNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colPersPresencial.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().requiereAtencionPresencial() ? "Sí" : "No"));
        colPersCostoTotal.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().calcularCosto()));

        tblCatalogoServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> actualizarPrevisualizacion());
        chkAtencionPrioritaria.selectedProperty().addListener((obs, oldV, newV) -> actualizarPrevisualizacion());
        chkSoporte30Dias.selectedProperty().addListener((obs, oldV, newV) -> actualizarPrevisualizacion());
        chkAtencionDomicilio.selectedProperty().addListener((obs, oldV, newV) -> actualizarPrevisualizacion());

        refrescarDatos();
    }

    public void refrescarDatos() {
        tblCatalogoServicios.setItems(FXCollections.observableArrayList(serviciosController.obtenerCatalogoServicios()));
        if (!tblCatalogoServicios.getItems().isEmpty() && tblCatalogoServicios.getSelectionModel().isEmpty()) {
            tblCatalogoServicios.getSelectionModel().selectFirst();
        }
        tblServiciosPersonalizados.setItems(FXCollections.observableArrayList(serviciosController.obtenerServiciosPersonalizados()));
        actualizarPrevisualizacion();
    }

    @FXML
    public void onRegistrarServicio() {
        try {
            boolean esPresencial = chkPresencialNuevo != null && chkPresencialNuevo.isSelected();
            Servicio creado = serviciosController.registrarServicio(
                    cmbTipoServicio.getValue(),
                    txtCodigoServicio.getText(),
                    txtNombreServicio.getText(),
                    txtDescripcionServicio.getText(),
                    esPresencial
            );
            txtCodigoServicio.clear();
            txtNombreServicio.clear();
            txtDescripcionServicio.clear();
            if (chkPresencialNuevo != null) {
                chkPresencialNuevo.setSelected(false);
            }
            refrescarDatos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Servicio creado: " + creado.getNombre());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void actualizarPrevisualizacion() {
        Servicio base = tblCatalogoServicios.getSelectionModel().getSelectedItem();
        if (base == null) {
            lblServicioSeleccionado.setText("Seleccione un servicio de la tabla");
            lblCostoBase.setText("$0");
            lblIncrementoComplementos.setText("+$0");
            lblCostoFinal.setText("$0");
            lblRequierePresencial.setText("No");
            return;
        }

        IComponenteServicio previsualizado = serviciosController.previsualizarPersonalizacion(
                base,
                chkAtencionPrioritaria.isSelected(),
                chkSoporte30Dias.isSelected(),
                chkAtencionDomicilio.isSelected()
        );

        double costoBase = base.calcularCosto();
        double costoTotal = previsualizado.calcularCosto();

        lblServicioSeleccionado.setText(base.getCodigo() + " - " + base.getNombre());
        lblCostoBase.setText("$" + costoBase);
        lblIncrementoComplementos.setText("+$" + (costoTotal - costoBase));
        lblCostoFinal.setText("$" + costoTotal);
        lblRequierePresencial.setText(previsualizado.requiereAtencionPresencial() ? "Sí" : "No");
    }

    @FXML
    public void onGuardarServicioPersonalizado() {
        try {
            Servicio base = tblCatalogoServicios.getSelectionModel().getSelectedItem();
            IComponenteServicio personalizado = serviciosController.guardarServicioPersonalizado(
                    base,
                    chkAtencionPrioritaria.isSelected(),
                    chkSoporte30Dias.isSelected(),
                    chkAtencionDomicilio.isSelected()
            );

            chkAtencionPrioritaria.setSelected(false);
            chkSoporte30Dias.setSelected(false);
            chkAtencionDomicilio.setSelected(false);
            refrescarDatos();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                    "Servicio personalizado guardado: " + personalizado.getNombre() + " ($" + personalizado.calcularCosto() + ")");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
