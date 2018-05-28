package es.caib.sistramit.core.service.component.script.plugins;

import java.util.List;

import es.caib.sistramit.core.service.model.integracion.FicheroDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
import es.caib.sistramit.core.service.model.script.ClzFicheroInt;
import es.caib.sistramit.core.service.model.script.ClzValoresDominioInt;

/**
 * Modela datos de un dominio.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzValoresDominio implements ClzValoresDominioInt {

	/**
	 * Valores dominio.
	 */
	private ValoresDominio valores = new ValoresDominio();

	/**
	 * Constructor.
	 *
	 * @param pValores
	 *            valores dominio
	 */
	public ClzValoresDominio(final ValoresDominio pValores) {
		super();
		valores = pValores;
	}


	@Override
	public String getValor(final int numfila, final String cod) {
		return valores.getValor(numfila, cod);
	}


	@Override
	public int getNumeroFilas() {
		return valores.getNumeroFilas();
	}


	@Override
	public boolean isError() {
		return valores.isError();
	}


	@Override
	public String getDescripcionError() {
		return valores.getDescripcionError();
	}


	@Override
	public ClzFicheroInt getFichero(final String id) {
		ClzFicheroInt res = null;
		final FicheroDominio fic = valores.getFichero(id);
		if (fic != null) {
			res = new ClzFichero(valores.getFichero(id));
		}
		return res;
	}


	@Override
	public List<String> getNombreColumnas() {
		return valores.getNombreColumnas();
	}


	@Override
	public String getCodigoError() {
		return valores.getCodigoError();
	}

}
