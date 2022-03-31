package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.model.flujo.Firmante;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedadFirmante;
import es.caib.sistramit.core.service.model.script.flujo.ResFirmantesInt;

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
	private final List<Firmante> firmantes = new ArrayList<>();

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void addFirmante(final String nif, final String nombre) throws ScriptException {
		addFirmante(nif, nombre, TypeObligatoriedadFirmante.OBLIGATORIO);
	}

	@Override
	public void addFirmanteOpcional(final String nif, final String nombre) throws ScriptException {
		addFirmante(nif, nombre, TypeObligatoriedadFirmante.OPCIONAL);
	}

	/**
	 * Método de acceso a firmantes.
	 *
	 * @return firmantes
	 */
	public List<Firmante> calcularFirmantes() {

		// Calcula lista final firmantes:
		// - Deben firmar los obligatorios
		// - Debe firmar al menos 1 de los opcionales

		final List<Firmante> res = new ArrayList<>();

		// Añadimos todos los obligatorios
		for (final Firmante f : firmantes) {
			if (f.getObligatorio() == TypeObligatoriedadFirmante.OBLIGATORIO
					&& !existeFirmante(res, f.getNif(), null)) {
				res.add(f);
			}
		}

		// Si existe algun obligatorio marcado como opcional, el resto de opcionales
		// pasaran a ser opcionales. En caso contrario serán opcionales requeridos.
		boolean existeObligatorioOpcional = false;
		for (final Firmante f : res) {
			if (existeFirmante(firmantes, f.getNif(), TypeObligatoriedadFirmante.OPCIONAL)) {
				existeObligatorioOpcional = true;
				break;
			}
		}

		// Añadimos opcionales
		for (final Firmante f : firmantes) {
			if (f.getObligatorio() == TypeObligatoriedadFirmante.OPCIONAL && !existeFirmante(res, f.getNif(), null)) {
				if (!existeObligatorioOpcional) {
					f.setObligatorio(TypeObligatoriedadFirmante.OPCIONAL_REQUERIDO);
				}
				res.add(f);
			}
		}

		return res;
	}

	private boolean existeFirmante(final List<Firmante> pFirmantes, final String pNif,
			final TypeObligatoriedadFirmante pObligatoriedad) {
		boolean existe = false;
		for (final Firmante f : pFirmantes) {
			if (f.getNif().equals(pNif) && (pObligatoriedad == null || f.getObligatorio() == pObligatoriedad)) {
				existe = true;
				break;
			}
		}
		return existe;
	}

	/**
	 * Añade firmante (tiene preferencia si se establece obligatorio).
	 *
	 * @param nif
	 *                        Nif
	 * @param nombre
	 *                        Nombre
	 * @param obligatorio
	 *                        obligatorio
	 * @throws ScriptException
	 *                             Excepcion
	 */
	private void addFirmante(final String nif, final String nombre, final TypeObligatoriedadFirmante obligatorio)
			throws ScriptException {
		final String nifNormalizado = NifUtils.normalizarNif(nif);
		if (!NifUtils.esNifPersonaFisica(nifNormalizado) && !NifUtils.esNifPersonaJuridica(nifNormalizado)) {
			throw new ScriptException("La dada proporcionada com nif persona no és un nif vàlid: " + nifNormalizado);
		}
		if (StringUtils.isEmpty(nombre) || !XssFilter.filtroXss(nombre)) {
			throw new ScriptException(
					"La dada proporcionada com nom persona està buida o conté caràcters no permesos");
		}
		// Añadimos firmante
		firmantes.add(new Firmante(nifNormalizado, nombre, obligatorio));
	}

}
