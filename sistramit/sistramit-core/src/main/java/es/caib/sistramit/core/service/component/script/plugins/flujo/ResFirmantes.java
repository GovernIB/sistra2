package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.NifCif;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.service.model.script.ResFirmantesInt;

/**
 *
 * Datos para establecer los firmantes de un documento.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResFirmantes implements ResFirmantesInt {

    /**
     * Indica lista de firmantes.
     */
    private final List<Persona> firmantes = new ArrayList<>();

    @Override
    public String getPluginId() {
        return ID;
    }

    @Override
    public void addFirmante(final String nif, final String nombre)
            throws ScriptException {

        final String nifNormalizado = NifCif.normalizarNif(nif);

        if (!ValidacionesTipo.getInstance().esNif(nifNormalizado)
                && !ValidacionesTipo.getInstance().esCif(nifNormalizado)
                && !ValidacionesTipo.getInstance().esNie(nifNormalizado)) {
            throw new ScriptException(
                    "El dato proporcionado como nif persona no es nif/nie/cif: "
                            + nifNormalizado);
        }
        if (StringUtils.isEmpty(nombre) || !XssFilter.filtroXss(nombre)) {
            throw new ScriptException(
                    "El dato proporcionado como nombre persona esta vacio o contiene caraceteres no permitidos");
        }
        firmantes.add(new Persona(nifNormalizado, nombre));
    }

    /**
     * Método de acceso a firmantes.
     * 
     * @return firmantes
     */
    public List<Persona> getFirmantes() {
        return firmantes;
    }

}
