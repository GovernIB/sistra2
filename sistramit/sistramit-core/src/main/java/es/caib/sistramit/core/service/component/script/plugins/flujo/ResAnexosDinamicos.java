package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.service.model.flujo.AnexoDinamico;
import es.caib.sistramit.core.service.model.script.ResAnexosDinamicosInt;

/**
 * Datos para establecer los anexos de forma dinámica.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResAnexosDinamicos implements ResAnexosDinamicosInt {

    /**
     * Anexos definidos dinámicamente.
     */
    private final List<AnexoDinamico> anexos = new ArrayList<>();

    @Override
    public String getPluginId() {
        return ID;
    }

    @Override
    public void addAnexo(final String identificador, final String descripcion,
            final String extensiones, final String tamanyoMax,
            final String urlPlantilla, final boolean obligatorio,
            final boolean pConvertirPDF, final boolean pFirmar)
            throws ScriptException {
        if (!XssFilter.filtroXss(identificador)
                || !XssFilter.filtroXss(descripcion)) {
            throw new ScriptException(
                    "El dato proporcionado como identificador o descripcion contiene caraceteres no permitidos");
        }

        final AnexoDinamico anexo = new AnexoDinamico();
        anexo.setIdentificador(identificador);
        anexo.setDescripcion(descripcion);
        if (StringUtils.isNotBlank(tamanyoMax)) {
            if (!tamanyoMax.endsWith("MB") && !tamanyoMax.endsWith("KB")) {
                throw new ScriptException(
                        "Se debe indicar el tamaño maximo con el formato 'n MB / n KB' ");
            }
            try {
                Integer.parseInt(
                        StringUtils.trim(StringUtils.substring(tamanyoMax, 0,
                                tamanyoMax.length() - ConstantesNumero.N2)));
            } catch (final NumberFormatException nfe) {
                throw new ScriptException(new Exception(
                        "El tamaño máximo se debe especificar con un número entero' ",
                        nfe));
            }
            anexo.setTamanyoMaximo(tamanyoMax);
        }
        if (StringUtils.isBlank(extensiones)) {
            throw new ScriptException(
                    "No se han establecido extensiones para el anexo "
                            + identificador);
        }
        anexo.setExtensiones(extensiones);
        if (StringUtils.isNotBlank(urlPlantilla)) {
            anexo.setUrlPlantilla(urlPlantilla);
        }
        anexo.setObligatorio(obligatorio);
        anexo.setConvertirPDF(pConvertirPDF);
        anexo.setFirmar(pFirmar);

        anexos.add(anexo);

    }

    /**
     * Método de acceso a anexos.
     *
     * @return anexos
     */
    public List<AnexoDinamico> getAnexos() {
        return anexos;
    }

}
