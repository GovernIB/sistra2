package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.service.model.script.flujo.ResPagosDinamicosInt;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

/**
 * Datos para establecer los pagos de forma dinámica.
 */
public class ResPagosDinamicos implements ResPagosDinamicosInt {

    /**
     * Pagos dinámicos.
     */
    private final List<ClzPagoDinamico> anexos = new ArrayList<>();

    @Override
    public String getPluginId() {
        return ID;
    }

    @Override
    public ClzPagoDinamico crearPago() {
        return new ClzPagoDinamico();
    }

    @Override
    public void addPago(final ClzPagoDinamico pago) throws ScriptException {
        // Verifica que no exista un pago con el mismo identificador
        for (ClzPagoDinamico p : anexos) {
            if (p.getIdentificador().equals(pago.getIdentificador())) {
                throw new ScriptException("Ja existeix un pagament amb identificador: " + pago.getIdentificador());
            }
        }
        // Valida que tiene datos correctos
        validarPago(pago);

        // Añade a la lista de pagos
        anexos.add(pago);
    }

    /**
     * Devuelve la lista de pagos dinámicos.
     * @return Lista de pagos dinámicos
     */
    public List<ClzPagoDinamico> getPagos() {
        return anexos;
    }

    /**
     * Valida que los datos del pago sean correctos.
     * @param pago Pago a validar
     * @throws ScriptException
     */
    private void validarPago(ClzPagoDinamico pago) throws ScriptException {
        if (StringUtils.isBlank(pago.getIdentificador()) || !XssFilter.filtroXss(pago.getIdentificador())) {
            throw new ScriptException("El codi es buit o conté caràcters no permesos");
        }
        if (StringUtils.isBlank(pago.getModelo()) || !XssFilter.filtroXss(pago.getModelo())) {
            throw new ScriptException("El model es buit o conté caràcters no permesos");
        }
        if (StringUtils.isBlank(pago.getConcepto()) || !XssFilter.filtroXss(pago.getConcepto())) {
            throw new ScriptException("El concepte es buit o conté caràcters no permesos");
        }
        if (StringUtils.isBlank(pago.getDescripcion()) || !XssFilter.filtroXss(pago.getDescripcion())) {
            throw new ScriptException("El concepte es buit o conté caràcters no permesos");
        }
        // Si no esta vacio los datos del contribuyente, valida que sean correctos
        if (pago.getContribuyente() != null) {
            if (!NifUtils.esNifPersonaFisica(pago.getContribuyente().getNif()) && !NifUtils.esNifPersonaJuridica(pago.getContribuyente().getNif())) {
                throw new ScriptException("La dada proporcionada no és un nif vàlid: " + pago.getContribuyente().getNif());
            }
            if (StringUtils.isEmpty(pago.getContribuyente().getNombre()) || !XssFilter.filtroXss(pago.getContribuyente().getNombre())) {
                throw new ScriptException("La dada proporcionada com nom persona està buit o conté caràcters no permesos");
            }
        }
        if (pago.getImporte() <= 0) {
            throw new ScriptException("Import ha de ser major que 0");
        }
        if (pago.getMultiplicador() < 0 || pago.getMultiplicador() > 999) {
            throw new ScriptException("Multiplicador ha de ser major o igual a 0 i menor que 1000");
        }
    }

}
