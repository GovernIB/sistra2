package es.caib.sistramit.core.service.component.formulario.interno;

// TODO PARA BORRAR
public class SimulacionFormularioMock {

    private static String datosSimulados;

    /**
     * Método de acceso a datosSimulados.
     * 
     * @return datosSimulados
     */
    public static String getDatosSimulados() {
        return datosSimulados;
    }

    /**
     * Método para establecer datosSimulados.
     * 
     * @param datosSimulados
     *            datosSimulados a establecer
     */
    public static void setDatosSimulados(String datosSimulados) {
        SimulacionFormularioMock.datosSimulados = datosSimulados;
    }

}
