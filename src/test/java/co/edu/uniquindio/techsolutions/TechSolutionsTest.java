package co.edu.uniquindio.techsolutions;

import co.edu.uniquindio.techsolutions.model.Cliente;
import co.edu.uniquindio.techsolutions.model.InfoTecnico;
import co.edu.uniquindio.techsolutions.model.NivelPrioridad;
import co.edu.uniquindio.techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.TipoEspecialidad;
import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.builder.SolicitudBuilder;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.PaqueteTecnologico;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TechSolutionsTest {

    private TechSolutions techSolutions;

    @BeforeEach
    void setUp() {
        techSolutions = TechSolutions.getInstance();
        techSolutions.limpiarDatos();
        TechSolutionsApp.cargarDatosIniciales();
    }

    /**
     * metodo que permite probar la creacion y personalizacion de servicios y la inmutabilidad del catalogo
     */
    @Test
    @DisplayName("F1. Gestión y personalización de servicios tecnológicos (Funcionalidad principal requerida)")
    void testF1_GestionYPersonalizacionServicios() {
        // Crear un nuevo servicio en el catálogo
        Servicio nuevo = techSolutions.registrarServicio(
                TipoServicio.INSTALACION_REDES, "SRV-RED", "Red WiFi", "Red inalámbrica");
        assertNotNull(nuevo);
        assertEquals(180000.0, nuevo.calcularCosto());

        // Personalizar un servicio con atención prioritaria (+35.000) y atención a domicilio (+25.000)
        IComponenteServicio personalizado = techSolutions.guardarServicioPersonalizado(
                nuevo, true, false, true);

        // 180.000 + 35.000 + 25.000 = 240.000
        assertEquals(240000.0, personalizado.calcularCosto());
        assertTrue(personalizado.requiereAtencionPresencial());

        // El servicio original en el catálogo no cambia
        assertEquals(180000.0, nuevo.calcularCosto());
        assertFalse(nuevo.requiereAtencionPresencial());
    }

    /**
     * metodo que permite probar la creacion de paquetes tecnologicos y la prevencion de ciclos
     */
    @Test
    @DisplayName("F2. Configuración de paquetes tecnológicos (Funcionalidad principal requerida)")
    void testF2_ConfiguracionPaquetesTecnologicos() {
        Servicio s1 = techSolutions.buscarServicioCatalogo("SRV-001"); // Preventivo $80.000
        Servicio s2 = techSolutions.buscarServicioCatalogo("SRV-002"); // Correctivo $120.000

        PaqueteTecnologico paq = techSolutions.crearPaquete("PAQ-1", "Paquete Básico", "Mantenimientos");
        techSolutions.agregarElementoAPaquete("PAQ-1", s1);
        techSolutions.agregarElementoAPaquete("PAQ-1", s2);

        assertEquals(2, paq.getElementos().size());
        assertEquals(200000.0, paq.calcularCosto());

        // Valida que impida meter un paquete dentro de sí mismo
        assertThrows(IllegalArgumentException.class, () -> paq.agregarElemento(paq));
    }

    /**
     * metodo que permite probar el registro de solicitudes usando el patron Builder
     */
    @Test
    @DisplayName("F3. Registro de solicitudes de servicios (Funcionalidad principal requerida)")
    void testF3_RegistroSolicitudesServicios() {
        Cliente cliente = techSolutions.obtenerClientes().get(0);
        Servicio servicio = techSolutions.buscarServicioCatalogo("SRV-001");

        // Registrar solicitud con datos obligatorios y opcionales usando Builder
        SolicitudServicio solicitud = new SolicitudBuilder()
                .conCodigo("SOL-01")
                .conCliente(cliente)
                .agregarElemento(servicio)
                .conFechaProgramada(LocalDate.now().plusDays(2))
                .conObservacionesTecnicas("Revisar puertos")
                .conNivelPrioridad(NivelPrioridad.ALTA)
                .build();

        techSolutions.registrarSolicitud(solicitud);

        SolicitudServicio guardada = techSolutions.buscarSolicitud("SOL-01");
        assertNotNull(guardada);
        assertEquals(cliente, guardada.getCliente());
        assertEquals(NivelPrioridad.ALTA, guardada.getNivelPrioridad());
        assertEquals(80000.0, guardada.calcularCostoTotal());
    }

    /**
     * metodo que permite probar la consulta de tecnicos y calculo de costos con atencion presencial
     */
    @Test
    @DisplayName("F4. Consulta de técnicos y cálculo del costo de las solicitudes (Funcionalidad principal requerida)")
    void testF4_ConsultaTecnicosYCalculoCostoSolicitudes() {
        // Consultar técnico
        InfoTecnico tec = techSolutions.consultarDisponibilidadTecnico(TipoEspecialidad.MANTENIMIENTO);
        assertTrue(tec.isDisponible());
        assertEquals(30000.0, tec.getCostoDesplazamiento());

        // Solicitud presencial (con atención a domicilio)
        Cliente cliente = techSolutions.obtenerClientes().get(0);
        Servicio base = techSolutions.buscarServicioCatalogo("SRV-001"); // $80.000
        IComponenteServicio conDomicilio = techSolutions.guardarServicioPersonalizado(
                base, false, false, true); // $105.000 presencial

        SolicitudServicio sol = new SolicitudBuilder()
                .conCodigo("SOL-02")
                .conCliente(cliente)
                .agregarElemento(conDomicilio)
                .build();
        techSolutions.registrarSolicitud(sol);

        // Si la especialidad no tiene técnicos disponibles (SEGURIDAD), no deja confirmar
        assertThrows(IllegalStateException.class,
                () -> techSolutions.confirmarSolicitud("SOL-02", TipoEspecialidad.SEGURIDAD));

        // Si hay técnico disponible (MANTENIMIENTO), confirma y suma desplazamiento ($30.000)
        SolicitudServicio confirmada = techSolutions.confirmarSolicitud("SOL-02", TipoEspecialidad.MANTENIMIENTO);
        assertTrue(confirmada.isConfirmada());
        assertEquals(135000.0, confirmada.calcularCostoTotal()); // 105.000 + 30.000
    }

    /**
     * metodo que permite probar la consulta y busqueda de clientes precargados
     */
    @Test
    @DisplayName("F5. Listar clientes precargados: Visualizar y seleccionar los clientes registrados por defecto")
    void testF5_ListarClientesPrecargados() {
        List<Cliente> clientes = techSolutions.obtenerClientes();

        // Deben existir 3 clientes precargados
        assertEquals(3, clientes.size());

        // Se pueden buscar y seleccionar por documento
        Cliente c1 = techSolutions.buscarCliente("1094950111");
        assertNotNull(c1);
        assertEquals("Carlos Andrés Ramírez", c1.getNombreCompleto());

        Cliente c2 = techSolutions.buscarCliente("1094950222");
        assertNotNull(c2);
        assertEquals("Laura Sofía Gómez", c2.getNombreCompleto());

        Cliente c3 = techSolutions.buscarCliente("900123456-1");
        assertNotNull(c3);
        assertEquals("Inversiones Cafeteras S.A.S.", c3.getNombreCompleto());
    }

    /**
     * metodo que permite probar la consulta del catalogo base y sus costos iniciales
     */
    @Test
    @DisplayName("F6. Consultar catálogo base: Visualizar los cuatro tipos de servicios iniciales con sus costos definidos")
    void testF6_ConsultarCatalogoBase() {
        List<Servicio> catalogo = techSolutions.obtenerCatalogoServicios();

        // 4 servicios base definidos inicialmente
        assertEquals(4, catalogo.size());

        Servicio prev = techSolutions.buscarServicioCatalogo("SRV-001");
        assertNotNull(prev);
        assertEquals(80000.0, prev.calcularCosto());

        Servicio corr = techSolutions.buscarServicioCatalogo("SRV-002");
        assertNotNull(corr);
        assertEquals(120000.0, corr.calcularCosto());

        Servicio red = techSolutions.buscarServicioCatalogo("SRV-003");
        assertNotNull(red);
        assertEquals(180000.0, red.calcularCosto());

        Servicio rec = techSolutions.buscarServicioCatalogo("SRV-004");
        assertNotNull(rec);
        assertEquals(150000.0, rec.calcularCosto());
    }

    /**
     * metodo que permite probar la jerarquia recursiva y consulta de componentes de un paquete
     */
    @Test
    @DisplayName("F7. Consultar componentes de paquetes: Visualizar los elementos que conforman un paquete guardado")
    void testF7_ConsultarComponentesDePaquetes() {
        Servicio s1 = techSolutions.buscarServicioCatalogo("SRV-001"); // $80.000
        Servicio s2 = techSolutions.buscarServicioCatalogo("SRV-002"); // $120.000

        // Paquete hijo
        PaqueteTecnologico subPaquete = techSolutions.crearPaquete("PAQ-SUB", "Subpaquete", "Mantenimiento");
        subPaquete.agregarElemento(s1);
        subPaquete.agregarElemento(s2);

        // Paquete padre que contiene al subpaquete y otro servicio
        Servicio s3 = techSolutions.buscarServicioCatalogo("SRV-003"); // $180.000
        PaqueteTecnologico paquetePadre = techSolutions.crearPaquete("PAQ-PADRE", "Paquete Corporativo", "Integral");
        paquetePadre.agregarElemento(subPaquete);
        paquetePadre.agregarElemento(s3);

        // Consultar los componentes del paquete guardado
        List<IComponenteServicio> componentes = paquetePadre.getElementos();
        assertEquals(2, componentes.size());
        assertTrue(componentes.contains(subPaquete));
        assertTrue(componentes.contains(s3));

        // El costo total calcula recursivamente todos los niveles: (80.000 + 120.000) + 180.000 = 380.000
        assertEquals(380000.0, paquetePadre.calcularCosto());
    }

    /**
     * metodo que permite probar la busqueda de una solicitud registrada mediante su codigo unico
     */
    @Test
    @DisplayName("F8. Consultar solicitudes registradas: Buscar una solicitud mediante su código único")
    void testF8_ConsultarSolicitudesRegistradas() {
        Cliente cliente = techSolutions.obtenerClientes().get(1);
        Servicio servicio = techSolutions.buscarServicioCatalogo("SRV-003");

        SolicitudServicio nuevaSolicitud = new SolicitudBuilder()
                .conCodigo("SOL-ABC")
                .conCliente(cliente)
                .agregarElemento(servicio)
                .conObservacionesTecnicas("Instalación sede norte")
                .build();

        techSolutions.registrarSolicitud(nuevaSolicitud);

        // Buscar por su código único
        SolicitudServicio encontrada = techSolutions.buscarSolicitud("SOL-ABC");
        assertNotNull(encontrada);
        assertEquals("SOL-ABC", encontrada.getCodigo());
        assertEquals("Laura Sofía Gómez", encontrada.getCliente().getNombreCompleto());
        assertEquals(180000.0, encontrada.calcularCostoTotal());
        assertEquals("Instalación sede norte", encontrada.getObservacionesTecnicas());
    }
}
