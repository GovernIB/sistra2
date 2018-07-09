package es.caib.sistramit.core.service.component.flujo.reglas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.service.ApplicationContextProvider;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseEjecucion;

/**
 * Lógica general para establecer las reglas de un flujo. Los diferentes flujos
 * deben extender de esta clase y establecer las reglas.
 *
 * @author Indra
 *
 */
public abstract class ReglasFlujoImpl implements ReglasFlujo {

    /**
     * Reglas del flujo.
     */
    private final Map<TypeFaseEjecucion, List<ReglaTramitacion>> reglas = new HashMap<>();

    /**
     * Añade regla de tramitación al flujo.
     *
     * @param idRegla
     *            Id regla
     * @param fase
     *            Fase
     */
    protected final void addRegla(final String idRegla,
            final TypeFaseEjecucion fase) {
        List<ReglaTramitacion> reglasFase = reglas.get(fase);
        if (reglasFase == null) {
            reglasFase = new ArrayList<ReglaTramitacion>();
            reglas.put(fase, reglasFase);
        }

        final ReglaTramitacion rt = (ReglaTramitacion) ApplicationContextProvider
                .getApplicationContext().getBean(idRegla);

        reglasFase.add(rt);
    }

    @Override
    public final List<ReglaTramitacion> getReglasTramitacion(
            final TypeFaseEjecucion pFaseEjecucion) {
        return reglas.get(pFaseEjecucion);
    }

}
