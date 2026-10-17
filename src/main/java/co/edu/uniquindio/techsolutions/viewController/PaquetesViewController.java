package co.edu.uniquindio.techsolutions.viewController;

import co.edu.uniquindio.techsolutions.controller.PaquetesController;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.PaqueteTecnologico;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PaquetesViewController {

    private final PaquetesController paquetesController = new PaquetesController();

    @FXML
    private TextField txtCodigoPaquete;
    @FXML
    private TextField txtNombrePaquete;
    @FXML
    private TextField txtDescripcionPaquete;

    @FXML
    private TableView<PaqueteTecnologico> tblPaquetes;
    @FXML
    private TableColumn<PaqueteTecnologico, String> colPaqCodigo;
    @FXML
    private TableColumn<PaqueteTecnologico, String> colPaqNombre;
    @FXML
    private TableColumn<PaqueteTecnologico, String> colPaqDescripcion;
    @FXML
    private TableColumn<PaqueteTecnologico, String> colPaqCantidad;
    @FXML
    private TableColumn<PaqueteTecnologico, String> colPaqPresencial;
    @FXML
    private TableColumn<PaqueteTecnologico, String> colPaqCostoTotal;

    @FXML
    private Label lblPaqueteSeleccionado;
    @FXML
    private ComboBox<IComponenteServicio> cmbElementoDisponible;
    @FXML
    private TableView<IComponenteServicio> tblComponentesPaquete;
    @FXML
    private TableColumn<IComponenteServicio, String> colCompCodigo;
    @FXML
    private TableColumn<IComponenteServicio, String> colCompNombre;
    @FXML
    private TableColumn<IComponenteServicio, String> colCompTipo;
    @FXML
    private TableColumn<IComponenteServicio, String> colCompPresencial;
    @FXML
    private TableColumn<IComponenteServicio, String> colCompCosto;
    @FXML
    private Label lblCostoTotalPaquete;

    /**
     * metodo que inicializa los componentes de la vista de paquetes
     */
    @FXML
    public void initialize() {
        colPaqCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo()));
        colPaqNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colPaqDescripcion.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescripcion()));
        colPaqCantidad.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getElementos().size())));
        colPaqPresencial.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().requiereAtencionPresencial() ? "Sí" : "No"));
        colPaqCostoTotal.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().calcularCosto()));

        colCompCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo()));
        colCompNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colCompTipo.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue() instanceof PaqueteTecnologico ? "Paquete" : "Servicio"));
        colCompPresencial.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().requiereAtencionPresencial() ? "Sí" : "No"));
        colCompCosto.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().calcularCosto()));

        tblPaquetes.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> mostrarComponentesPaquete());
        refrescarDatos();
    }

    /**
     * metodo que permite refrescar las tablas y listas de paquetes y elementos disponibles
     */
    public void refrescarDatos() {
        PaqueteTecnologico seleccionado = tblPaquetes.getSelectionModel().getSelectedItem();
        tblPaquetes.setItems(FXCollections.observableArrayList(paquetesController.obtenerPaquetes()));
        tblPaquetes.refresh();
        if (seleccionado != null) {
            tblPaquetes.getSelectionModel().select(seleccionado);
        } else if (!tblPaquetes.getItems().isEmpty()) {
            tblPaquetes.getSelectionModel().selectFirst();
        }

        cmbElementoDisponible.setItems(FXCollections.observableArrayList(paquetesController.obtenerElementosDisponibles()));
        if (!cmbElementoDisponible.getItems().isEmpty() && cmbElementoDisponible.getSelectionModel().isEmpty()) {
            cmbElementoDisponible.getSelectionModel().selectFirst();
        }
        mostrarComponentesPaquete();
    }

    /**
     * metodo que permite mostrar los elementos contenidos dentro del paquete seleccionado
     */
    private void mostrarComponentesPaquete() {
        PaqueteTecnologico paquete = tblPaquetes.getSelectionModel().getSelectedItem();
        if (paquete == null) {
            lblPaqueteSeleccionado.setText("Seleccione un paquete");
            tblComponentesPaquete.setItems(FXCollections.emptyObservableList());
            lblCostoTotalPaquete.setText("$0");
            return;
        }
        lblPaqueteSeleccionado.setText(paquete.getCodigo() + " - " + paquete.getNombre());
        tblComponentesPaquete.setItems(FXCollections.observableArrayList(paquete.getElementos()));
        tblComponentesPaquete.refresh();
        lblCostoTotalPaquete.setText("$" + paquete.calcularCosto());
    }

    /**
     * metodo que permite crear un nuevo paquete tecnologico desde el formulario
     */
    @FXML
    public void onCrearPaquete() {
        try {
            PaqueteTecnologico nuevo = paquetesController.crearPaquete(
                    txtCodigoPaquete.getText(),
                    txtNombrePaquete.getText(),
                    txtDescripcionPaquete.getText()
            );
            txtCodigoPaquete.clear();
            txtNombrePaquete.clear();
            txtDescripcionPaquete.clear();
            refrescarDatos();
            tblPaquetes.getSelectionModel().select(nuevo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Paquete creado: " + nuevo.getNombre());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    /**
     * metodo que permite agregar un elemento disponible al paquete seleccionado
     */
    @FXML
    public void onAgregarElementoAPaquete() {
        try {
            PaqueteTecnologico paquete = tblPaquetes.getSelectionModel().getSelectedItem();
            if (paquete == null) {
                throw new IllegalArgumentException("Seleccione primero un paquete de la tabla.");
            }
            IComponenteServicio elemento = cmbElementoDisponible.getValue();
            paquetesController.agregarElementoAPaquete(paquete.getCodigo(), elemento);
            refrescarDatos();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    /**
     * metodo que permite eliminar un elemento del paquete seleccionado
     */
    @FXML
    public void onEliminarElementoDePaquete() {
        PaqueteTecnologico paquete = tblPaquetes.getSelectionModel().getSelectedItem();
        IComponenteServicio componente = tblComponentesPaquete.getSelectionModel().getSelectedItem();
        if (paquete != null && componente != null) {
            paquetesController.eliminarElementoDePaquete(paquete.getCodigo(), componente);
            refrescarDatos();
        }
    }

    /**
     * metodo que permite mostrar una alerta en pantalla
     * @param tipo
     * @param titulo
     * @param mensaje
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
