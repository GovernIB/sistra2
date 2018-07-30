package es.caib.sistramit.core.service.component.script.plugins;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;

/**
 * Clase de acceso a un valor de un campo lista indexados desde los plugins.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzValorCampoMultiple implements ClzValorCampoMultipleInt {

    /**
     * Lista de valores.
     */
    private final List<ClzValorCampoCompuesto> valores = new ArrayList<ClzValorCampoCompuesto>();

    @Override
    public void addValor(final String codigo, final String descripcion) {
        valores.add(new ClzValorCampoCompuesto(codigo, descripcion));
    }

    @Override
    public int getNumeroValores() {
        return valores.size();
    }

    @Override
    public ClzValorCampoCompuestoInt getValor(final int index) {
        return valores.get(index);
    }

}
