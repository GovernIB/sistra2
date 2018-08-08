package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorResetCampos;
import es.caib.sistramit.core.service.component.formulario.UtilsFormulario;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;

/**
 * Acceso a valores de un formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValoresFormulario implements Serializable {

    /**
     * Xml de datos.
     */
    private byte[] xml;

    /**
     * Map con los datos del formulario.
     */
    private final Map<String, ValorCampo> datos;

    /**
     * Acción personalizada.
     */
    private String accionPersonalizada;

    /**
     * Constructor.
     *
     * @param pXml
     *            Xml del formulario.
     */
    public ValoresFormulario(final byte[] pXml) {
        // Interpretamos xml
        final XmlFormulario form = UtilsFormulario.xmlToValores(pXml);

        // Establecemos accion personalizada
        this.accionPersonalizada = form.getAccionPersonalizada();

        // Construimos map de valores
        this.xml = pXml;
        this.datos = new LinkedHashMap<>();
        for (final ValorCampo vc : form.getValores()) {
            this.datos.put(vc.getId(), vc);
        }
    }

    /**
     * Crea valores formulario.
     *
     * @param pXml
     *            Xml del formulario.
     * @return valores formulario
     */
    public static ValoresFormulario createValoresFormulario(final byte[] pXml) {
        return new ValoresFormulario(pXml);
    }

    /**
     * Crea valores formulario vacio.
     *
     * @return ValoresFormulario
     */
    public static ValoresFormulario createValoresFormularioVacio() {
        // Generamos xml vacio
        final List<ValorCampo> listaValores = new ArrayList<>();
        final byte[] res = UtilsFormulario
                .valoresToXml(new XmlFormulario(listaValores, null));

        // Creamos valores formulario
        return new ValoresFormulario(res);

    }

    /**
     * Método de acceso a accionPersonalizada.
     *
     * @return accionPersonalizada
     */
    public String getAccionPersonalizada() {
        return accionPersonalizada;
    }

    /**
     * Método para establecer accionPersonalizada.
     *
     * @param pAccionPersonalizada
     *            accionPersonalizada a establecer
     */
    public void setAccionPersonalizada(final String pAccionPersonalizada) {
        accionPersonalizada = pAccionPersonalizada;
    }

    /**
     * Xml de datos.
     *
     * @return xml
     */
    public byte[] getXml() {
        return xml;
    }

    /**
     * Obtiene el valor de un campo.
     *
     * @param campo
     *            Id del campo
     * @return Valor del campo
     */
    public ValorCampo getValorCampo(final String campo) {
        return datos.get(campo);
    }

    /**
     * Obtiene lista de ids de campo.
     *
     * @return lista de ids de campo
     */
    public List<String> getIdsCampo() {
        final List<String> ids = new ArrayList<String>();
        for (final Iterator<String> it = this.datos.keySet().iterator(); it
                .hasNext();) {
            ids.add(it.next());
        }
        return ids;
    }

    /**
     * Modifica valores formulario.
     *
     * @param valores
     *            Valores a modificar (no se incluyen todos los valores del
     *            formulario, solo los que se modifican)
     */
    public void modificarValoresCampos(final List<ValorCampo> valores) {
        // Actualizamos map de valores
        for (final ValorCampo valorCampo : valores) {
            if (valorCampo instanceof ValorResetCampos) {
                resetearValores();
            } else {
                datos.put(valorCampo.getId(), valorCampo);
            }
        }
        // Actualizamos xml
        this.xml = valoresToXml(this.datos, this.accionPersonalizada);
    }

    /**
     * Resetea valores establecidos poniendolos a vacío.
     */
    private void resetearValores() {
        for (final String key : datos.keySet()) {
            final ValorCampo valor = datos.get(key);
            final ValorCampo valorVacio = UtilsFormulario
                    .crearValorVacio(valor.getId(), valor.getTipo());
            datos.put(valor.getId(), valorVacio);
        }
    }

    /**
     * Convierte a XML.
     *
     * @return XML
     */
    public byte[] toXml() {
        return valoresToXml(this.datos, this.accionPersonalizada);
    }

    /**
     * Convierte map de valores a xml.
     *
     * @param valores
     *            Map de valores
     * @param pAccionPersonalizada
     *            accion personalizada
     * @return XML
     */
    private byte[] valoresToXml(final Map<String, ValorCampo> valores,
            final String pAccionPersonalizada) {
        // Creamos lista valores a partir del map
        final List<ValorCampo> listaValores = new ArrayList<>();
        for (final Map.Entry<String, ValorCampo> entry : valores.entrySet()) {
            listaValores.add(entry.getValue());
        }
        // Generamos xml
        final byte[] res = UtilsFormulario.valoresToXml(
                new XmlFormulario(listaValores, pAccionPersonalizada));
        return res;
    }

}
