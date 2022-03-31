package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.service.model.script.flujo.ResAnexosDinamicosInt;

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
	private final List<ClzAnexoDinamico> anexos = new ArrayList<>();

	/** Indica si los anexos dinámicos van antes que los anexos fijos. */
	private boolean precedenciaSobreAnexosFijos;

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public ClzAnexoDinamico crearAnexo() {
		return new ClzAnexoDinamico();
	}

	@Override
	public void addAnexo(final ClzAnexoDinamico anexo) throws ScriptException {
		// Verifica datos obligatorios y formato
		if (StringUtils.isBlank(anexo.getIdentificador())) {
			throw new ScriptException("No s'ha indicat identificador annex");
		}
		if (StringUtils.isBlank(anexo.getDescripcion())) {
			throw new ScriptException("No s'ha indicat descripció annex " + anexo.getIdentificador());
		}
		if (!XssFilter.filtroXss(anexo.getIdentificador()) || !XssFilter.filtroXss(anexo.getDescripcion())) {
			throw new ScriptException(
					"La dada proporcionada com identificador o descripció conté caràceters no permesos per annex "
							+ anexo.getIdentificador());
		}
		if (StringUtils.isNotBlank(anexo.getTamanyoMaximo())) {
			if (!anexo.getTamanyoMaximo().endsWith("MB") && !anexo.getTamanyoMaximo().endsWith("KB")) {
				throw new ScriptException("S'ha d'indicar la mida màxima amb el format 'n MB / n KB' per annex "
						+ anexo.getIdentificador());
			}
			try {
				Integer.parseInt(StringUtils.trim(StringUtils.substring(anexo.getTamanyoMaximo(), 0,
						anexo.getTamanyoMaximo().length() - ConstantesNumero.N2)));
			} catch (final NumberFormatException nfe) {
				throw new ScriptException(
						new Exception("La mida màxima s'ha d'especificar amb un nombre sencer per annex "
								+ anexo.getIdentificador(), nfe));
			}
		}
		if (StringUtils.isBlank(anexo.getExtensiones())) {
			throw new ScriptException("No s'han establert extensions per l'annex " + anexo.getIdentificador());
		}
		if (anexo.getMaxInstancias() <= 0) {
			throw new ScriptException("El nombre d'instancies ha de ser major a 0 (" + anexo.getMaxInstancias() + ")");
		}

		anexos.add(anexo);
	}

	/**
	 * Método de acceso a anexos.
	 *
	 * @return anexos
	 */
	public List<ClzAnexoDinamico> getAnexos() {
		return anexos;
	}

	/**
	 * Método de acceso a precedenciaSobreAnexosFijos.
	 *
	 * @return precedenciaSobreAnexosFijos
	 */
	public boolean isPrecedenciaSobreAnexosFijos() {
		return precedenciaSobreAnexosFijos;
	}

	/**
	 * Método para establecer precedenciaSobreAnexosFijos.
	 *
	 * @param precedenciaSobreAnexosFijos
	 *                                        precedenciaSobreAnexosFijos a
	 *                                        establecer
	 */
	@Override
	public void setPrecedenciaSobreAnexosFijos(final boolean precedenciaSobreAnexosFijos) {
		this.precedenciaSobreAnexosFijos = precedenciaSobreAnexosFijos;
	}

}
