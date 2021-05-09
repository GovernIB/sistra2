package es.caib.sistra2.commons.plugins.dominio.mock;

import java.util.Base64;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.dominio.api.DominioPluginException;
import es.caib.sistra2.commons.plugins.dominio.api.FicheroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.IDominioPlugin;
import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;

/**
 * Interface email plugin.
 *
 * @author Indra
 *
 */
public class DominioPluginMock extends AbstractPluginProperties implements IDominioPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "dominiomock.";

	public DominioPluginMock(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public ValoresDominio invocarDominio(final String idDominio, final String url,
			final List<ParametroDominio> parametros, final String user, final String pass, final Long timeout)
			throws DominioPluginException {
		ValoresDominio retorno;
		if (url != null && url.toLowerCase().contains("fichero")) {
			retorno = getValorDominioFichero(parametros);
		} else if (url != null && url.toLowerCase().contains("municipio")) {
			retorno = getValorDominioMuncipio(parametros);
		} else if (url != null && url.toLowerCase().contains("provincia")) {
			retorno = getValorDominioProvincia(parametros);
		} else {
			retorno = getValorDominioSimple(parametros);
		}

		return retorno;
	}

	/**
	 * Obtiene los dominios de la provincia.
	 *
	 * @return
	 */
	private ValoresDominio getValorDominioProvincia(final List<ParametroDominio> parametros) {
		final ValoresDominio valores = new ValoresDominio();

		if (checkValido("01", "Alava", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "01");
			valores.setValor(posicion, "DESC", "Alava");
		}
		if (checkValido("02", "Albacete", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "02");
			valores.setValor(posicion, "DESC", "Albacete");
		}
		if (checkValido("03", "Alicante", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "03");
			valores.setValor(posicion, "DESC", "Alicante");
		}
		if (checkValido("04", "Almería", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "04");
			valores.setValor(posicion, "DESC", "Almería");
		}
		if (checkValido("05", "Ávila", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "05");
			valores.setValor(posicion, "DESC", "Ávila");
		}
		if (checkValido("06", "Badajoz", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "06");
			valores.setValor(posicion, "DESC", "Badajoz");

		}
		if (checkValido("07", "Baleares", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "07");
			valores.setValor(posicion, "DESC", "Baleares");

		}
		if (checkValido("08", "Barcelona", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "08");
			valores.setValor(posicion, "DESC", "Barcelona");

		}
		if (checkValido("09", "Burgos", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "09");
			valores.setValor(posicion, "DESC", "Burgos");

		}
		if (checkValido("10", "Cáceres", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "10");
			valores.setValor(posicion, "DESC", "Cáceres");

		}
		if (checkValido("11", "Cádiz", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "11");
			valores.setValor(posicion, "DESC", "Cádiz");

		}
		if (checkValido("12", "Castellón", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "12");
			valores.setValor(posicion, "DESC", "Castellón");

		}
		if (checkValido("13", "Ciudad Real", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "13");
			valores.setValor(posicion, "DESC", "Ciudad Real");

		}
		if (checkValido("14", "Córdoba", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "14");
			valores.setValor(posicion, "DESC", "Córdoba");

		}
		if (checkValido("15", "Coruña", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "15");
			valores.setValor(posicion, "DESC", "Coruña");

		}
		if (checkValido("16", "Cuenca", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "16");
			valores.setValor(posicion, "DESC", "Cuenca");

		}
		if (checkValido("17", "Gerona", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "17");
			valores.setValor(posicion, "DESC", "Gerona");

		}
		if (checkValido("18", "Granada", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "18");
			valores.setValor(posicion, "DESC", "Granada");

		}
		if (checkValido("19", "Guadalajara", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "19");
			valores.setValor(posicion, "DESC", "Guadalajara");

		}
		if (checkValido("20", "Guipúzcoa", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "20");
			valores.setValor(posicion, "DESC", "Guipúzcoa");

		}
		if (checkValido("21", "Huelva", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "21");
			valores.setValor(posicion, "DESC", "Huelva");

		}
		if (checkValido("22", "Huesca", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "22");
			valores.setValor(posicion, "DESC", "Huesca");

		}
		if (checkValido("23", "Jaén", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "23");
			valores.setValor(posicion, "DESC", "Jaén");

		}
		if (checkValido("24", "León", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "24");
			valores.setValor(posicion, "DESC", "León");

		}
		if (checkValido("25", "Lérida", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "25");
			valores.setValor(posicion, "DESC", "Lérida");

		}
		if (checkValido("26", "La Rioja", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "26");
			valores.setValor(posicion, "DESC", "La Rioja");

		}
		if (checkValido("27", "Lugo", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "27");
			valores.setValor(posicion, "DESC", "Lugo");

		}
		if (checkValido("28", "Madrid", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "28");
			valores.setValor(posicion, "DESC", "Madrid");

		}
		if (checkValido("29", "Málaga", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "29");
			valores.setValor(posicion, "DESC", "Málaga");

		}
		if (checkValido("30", "Murcia", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "30");
			valores.setValor(posicion, "DESC", "Murcia");

		}
		if (checkValido("31", "Navarra", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "31");
			valores.setValor(posicion, "DESC", "Navarra");

		}
		if (checkValido("32", "Orense", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "32");
			valores.setValor(posicion, "DESC", "Orense");

		}
		if (checkValido("33", "Asturias", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "33");
			valores.setValor(posicion, "DESC", "Asturias");

		}
		if (checkValido("34", "Palencia", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "34");
			valores.setValor(posicion, "DESC", "Palencia");

		}
		if (checkValido("35", "Las Palmas", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "35");
			valores.setValor(posicion, "DESC", "Las Palmas");

		}
		if (checkValido("36", "Pontevedra", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "36");
			valores.setValor(posicion, "DESC", "Pontevedra");

		}
		if (checkValido("37", "Salamanca", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "37");
			valores.setValor(posicion, "DESC", "Salamanca");

		}
		if (checkValido("38", "Santa Cruz de Tenerife", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "38");
			valores.setValor(posicion, "DESC", "Santa Cruz de Tenerife");

		}
		if (checkValido("39", "Cantabria", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "39");
			valores.setValor(posicion, "DESC", "Cantabria");

		}
		if (checkValido("40", "Segovia", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "40");
			valores.setValor(posicion, "DESC", "Segovia");

		}
		if (checkValido("41", "Sevilla", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "41");
			valores.setValor(posicion, "DESC", "Sevilla");

		}
		if (checkValido("42", "Soria", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "42");
			valores.setValor(posicion, "DESC", "Soria");

		}
		if (checkValido("43", "Tarragona", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "43");
			valores.setValor(posicion, "DESC", "Tarragona");

		}
		if (checkValido("44", "Teruel", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "44");
			valores.setValor(posicion, "DESC", "Teruel");

		}
		if (checkValido("45", "Toledo", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "45");
			valores.setValor(posicion, "DESC", "Toledo");

		}
		if (checkValido("46", "Valencia", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "46");
			valores.setValor(posicion, "DESC", "Valencia");

		}
		if (checkValido("47", "Valladolid", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "47");
			valores.setValor(posicion, "DESC", "Valladolid");

		}
		if (checkValido("48", "Vizcaya", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "48");
			valores.setValor(posicion, "DESC", "Vizcaya");

		}
		if (checkValido("49", "Zamora", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "49");
			valores.setValor(posicion, "DESC", "Zamora");

		}
		if (checkValido("50", "Zaragoza", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "50");
			valores.setValor(posicion, "DESC", "Zaragoza");

		}
		if (checkValido("51", "Ceuta", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "51");
			valores.setValor(posicion, "DESC", "Ceuta");

		}
		if (checkValido("52", "Melilla", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "52");
			valores.setValor(posicion, "DESC", "Melilla");
		}
		return valores;
	}

	private ValoresDominio getValorDominioMuncipio(final List<ParametroDominio> parametros) {
		final ValoresDominio valores = new ValoresDominio();

		if (checkValido("0027", "Alaior", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0027");
			valores.setValor(posicion, "DESC", "Alaior");
		}
		if (checkValido("0012", "Alaró", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0012");
			valores.setValor(posicion, "DESC", "Alaró");
		}
		if (checkValido("0033", "Alcúdia", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0033");
			valores.setValor(posicion, "DESC", "Alcúdia");
		}
		if (checkValido("0048", "Algaida", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0048");
			valores.setValor(posicion, "DESC", "Algaida");
		}
		if (checkValido("0051", "Andratx", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0051");
			valores.setValor(posicion, "DESC", "Andratx");
		}
		if (checkValido("9013", "Ariany", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "9013");
			valores.setValor(posicion, "DESC", "Ariany");
		}
		if (checkValido("0064", "Artà", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0064");
			valores.setValor(posicion, "DESC", "Artà");
		}
		if (checkValido("0070", "Banyalbufar", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0070");
			valores.setValor(posicion, "DESC", "Banyalbufar");
		}
		if (checkValido("0086", "Binissalem", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0086");
			valores.setValor(posicion, "DESC", "Binissalem");
		}
		if (checkValido("0099", "Búger", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0099");
			valores.setValor(posicion, "DESC", "Búger");
		}
		if (checkValido("0103", "Bunyola", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0103");
			valores.setValor(posicion, "DESC", "Bunyola");
		}
		if (checkValido("0110", "Calvià", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0110");
			valores.setValor(posicion, "DESC", "Calvià");
		}
		if (checkValido("0125", "Campanet", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0125");
			valores.setValor(posicion, "DESC", "Campanet");
		}
		if (checkValido("0131", "Campos", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0131");
			valores.setValor(posicion, "DESC", "Campos");
		}
		if (checkValido("0146", "Capdepera", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0146");
			valores.setValor(posicion, "DESC", "Capdepera");
		}
		if (checkValido("0645", "Castell (Es)", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0645");
			valores.setValor(posicion, "DESC", "Castell (Es)");
		}
		if (checkValido("0159", "Ciutadella de Menorca", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0159");
			valores.setValor(posicion, "DESC", "Ciutadella de Menorca");
		}
		if (checkValido("0162", "Consell", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0162");
			valores.setValor(posicion, "DESC", "Consell");
		}
		if (checkValido("0178", "Costitx", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0178");
			valores.setValor(posicion, "DESC", "Costitx");
		}
		if (checkValido("0184", "Deyá", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0184");
			valores.setValor(posicion, "DESC", "Deyá");
		}
		if (checkValido("0260", "Eivissa", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0260");
			valores.setValor(posicion, "DESC", "Eivissa");
		}
		if (checkValido("0197", "Escorca", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0197");
			valores.setValor(posicion, "DESC", "Escorca");
		}
		if (checkValido("0201", "Esporles", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0201");
			valores.setValor(posicion, "DESC", "Esporles");
		}
		if (checkValido("0218", "Estellencs", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0218");
			valores.setValor(posicion, "DESC", "Estellencs");
		}
		if (checkValido("0223", "Felanitx", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0223");
			valores.setValor(posicion, "DESC", "Felanitx");
		}
		if (checkValido("0239", "Ferreries", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0239");
			valores.setValor(posicion, "DESC", "Ferreries");
		}
		if (checkValido("0244", "Formentera", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0244");
			valores.setValor(posicion, "DESC", "Formentera");
		}
		if (checkValido("0257", "Fornalutx", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0257");
			valores.setValor(posicion, "DESC", "Fornalutx");
		}
		if (checkValido("0276", "Inca", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0276");
			valores.setValor(posicion, "DESC", "Inca");
		}
		if (checkValido("0282", "Lloret de Vistalegre", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0282");
			valores.setValor(posicion, "DESC", "Lloret de Vistalegre");
		}
		if (checkValido("0295", "Lloseta", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0295");
			valores.setValor(posicion, "DESC", "Lloseta");
		}
		if (checkValido("0309", "Llubí", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0309");
			valores.setValor(posicion, "DESC", "Llubí");
		}
		if (checkValido("0316", "Llucmajor", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0316");
			valores.setValor(posicion, "DESC", "Llucmajor");
		}
		if (checkValido("0337", "Manacor", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0337");
			valores.setValor(posicion, "DESC", "Manacor");
		}
		if (checkValido("0342", "Mancor de la Vall", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0342");
			valores.setValor(posicion, "DESC", "Mancor de la Vall");
		}
		if (checkValido("0321", "Maó", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0321");
			valores.setValor(posicion, "DESC", "Maó");
		}
		if (checkValido("0355", "Maria de la Salut", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0355");
			valores.setValor(posicion, "DESC", "Maria de la Salut");
		}
		if (checkValido("0368", "Marratxí", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0368");
			valores.setValor(posicion, "DESC", "Marratxí");
		}
		if (checkValido("0374", "Mercadal (Es)", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0374");
			valores.setValor(posicion, "DESC", "Mercadal (Es)");
		}
		if (checkValido("9028", "Migjorn Gran (Es)", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "9028");
			valores.setValor(posicion, "DESC", "Migjorn Gran (Es)");
		}
		if (checkValido("0380", "Montuïri", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0380");
			valores.setValor(posicion, "DESC", "Montuïri");
		}
		if (checkValido("0393", "Muro", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0393");
			valores.setValor(posicion, "DESC", "Muro");
		}
		if (checkValido("0407", "Palma de Mallorca", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0407");
			valores.setValor(posicion, "DESC", "Palma de Mallorca");
		}
		if (checkValido("0414", "Petra", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0414");
			valores.setValor(posicion, "DESC", "Petra");
		}
		if (checkValido("0440", "Pobla (Sa)", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0440");
			valores.setValor(posicion, "DESC", "Pobla (Sa)");
		}
		if (checkValido("0429", "Pollença", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0429");
			valores.setValor(posicion, "DESC", "Pollença");
		}
		if (checkValido("0435", "Porreres", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0435");
			valores.setValor(posicion, "DESC", "Porreres");
		}
		if (checkValido("0453", "Puigpunyent", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0453");
			valores.setValor(posicion, "DESC", "Puigpunyent");
		}
		if (checkValido("0598", "Salines (Ses)", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0598");
			valores.setValor(posicion, "DESC", "Salines (Ses)");
		}
		if (checkValido("0466", "Sant Antoni de Portmany", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0466");
			valores.setValor(posicion, "DESC", "Sant Antoni de Portmany");
		}
		if (checkValido("0491", "Sant Joan", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0491");
			valores.setValor(posicion, "DESC", "Sant Joan");
		}
		if (checkValido("0504", "Sant Joan de Labritja", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0504");
			valores.setValor(posicion, "DESC", "Sant Joan de Labritja");
		}
		if (checkValido("0488", "Sant Josep de sa Talaia", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0488");
			valores.setValor(posicion, "DESC", "Sant Josep de sa Talaia");
		}
		if (checkValido("0511", "Sant Llorenç des Cardassar", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0511");
			valores.setValor(posicion, "DESC", "Sant Llorenç des Cardassar");
		}
		if (checkValido("0526", "Sant Lluís", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0526");
			valores.setValor(posicion, "DESC", "Sant Lluís");
		}
		if (checkValido("0532", "Santa Eugènia", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0532");
			valores.setValor(posicion, "DESC", "Santa Eugènia");
		}
		if (checkValido("0547", "Santa Eulalia del Río", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0547");
			valores.setValor(posicion, "DESC", "Santa Eulalia del Río");
		}
		if (checkValido("0550", "Santa Margalida", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0550");
			valores.setValor(posicion, "DESC", "Santa Margalida");
		}
		if (checkValido("0563", "Santa María del Camí", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0563");
			valores.setValor(posicion, "DESC", "Santa María del Camí");
		}
		if (checkValido("0579", "Santanyí", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0579");
			valores.setValor(posicion, "DESC", "Santanyí");
		}
		if (checkValido("0585", "Selva", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0585");
			valores.setValor(posicion, "DESC", "Selva");
		}
		if (checkValido("0472", "Sencelles", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0472");
			valores.setValor(posicion, "DESC", "Sencelles");
		}
		if (checkValido("0602", "Sineu", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0602");
			valores.setValor(posicion, "DESC", "Sineu");
		}
		if (checkValido("0619", "Sóller", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0619");
			valores.setValor(posicion, "DESC", "Sóller");
		}
		if (checkValido("0624", "Son Servera", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0624");
			valores.setValor(posicion, "DESC", "Son Servera");
		}
		if (checkValido("0630", "Valldemossa", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0630");
			valores.setValor(posicion, "DESC", "Valldemossa");
		}
		if (checkValido("0658", "Vilafranca de Bonany", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "0658");
			valores.setValor(posicion, "DESC", "Vilafranca de Bonany");
		}

		return valores;
	}

	private ValoresDominio getValorDominioFichero(final List<ParametroDominio> parametros) {
		final ValoresDominio valores = new ValoresDominio();
		final FicheroDominio fichero1 = new FicheroDominio("Fichero1.txt",
				Base64.getEncoder().encodeToString("Contenido del fichero 1.".getBytes()));
		fichero1.setDescripcion("Desc fichero 1");
		final FicheroDominio fichero2 = new FicheroDominio("Fichero2.txt",
				Base64.getEncoder().encodeToString("Contenido del fichero 2.".getBytes()));
		fichero1.setDescripcion("Desc fichero 2");
		if (checkValido("FIC1", null, parametros)) {
			valores.addFichero("FIC1", fichero1);
		}
		if (checkValido("FIC2", null, parametros)) {
			valores.addFichero("FIC2", fichero2);
		}
		return valores;
	}

	private ValoresDominio getValorDominioSimple(final List<ParametroDominio> parametros) {
		final ValoresDominio valores = new ValoresDominio();

		if (checkValido("ID1", "VALOR_11", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "ID1");
			valores.setValor(posicion, "COL1", "VALOR_11");
			valores.setValor(posicion, "COL2", "VALOR_12");
			valores.setValor(posicion, "COL3", "VALOR_13");
		}

		if (checkValido("ID2", "VALOR_12", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "ID2");
			valores.setValor(posicion, "COL1", "VALOR_21");
			valores.setValor(posicion, "COL2", "VALOR_22");
			valores.setValor(posicion, "COL3", "VALOR_23");
		}

		if (checkValido("ID3", "VALOR_13", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "ID3");
			valores.setValor(posicion, "COL1", "VALOR_31");
			valores.setValor(posicion, "COL2", "VALOR_32");
			valores.setValor(posicion, "COL3", "VALOR_33");
		}

		if (checkValido("ID4", "VALOR_14", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "ID4");
			valores.setValor(posicion, "COL1", "VALOR_41");
			valores.setValor(posicion, "COL2", "VALOR_42");
			valores.setValor(posicion, "COL3", "VALOR_43");
		}

		if (checkValido("ID5", "VALOR_15", parametros)) {
			final int posicion = valores.addFila();
			valores.setValor(posicion, "ID", "ID5");
			valores.setValor(posicion, "COL1", "VALOR_51");
			valores.setValor(posicion, "COL2", "VALOR_52");
			valores.setValor(posicion, "COL3", "VALOR_53");
		}
		return valores;
	}

	/**
	 * Método que se encarga de comprobar si el dato se debe introducir. Se dará por
	 * true si:<br />
	 * <ul>
	 * <li>- Parametros es nulo o vacio.</li>
	 * <li>- Ningun parametro filtra el dato (sólo se filtra por ID y DESC)</li>
	 * <li>- Si tiene el parametro ID o/y DESC, coincide parte del texto.</li>
	 * </ul>
	 *
	 * @param string
	 * @param string2
	 * @param parametros
	 * @return
	 */
	private boolean checkValido(final String id, final String descripcion, final List<ParametroDominio> parametros) {

		if (parametros == null || parametros.isEmpty()) {
			return true;
		}

		for (final ParametroDominio parametro : parametros) {

			if (parametro.getValor() != null && !parametro.getValor().isEmpty() && parametro.getCodigo() != null
					&& !parametro.getCodigo().isEmpty()) {

				if ("ID".equalsIgnoreCase(parametro.getCodigo()) && id != null
						&& !id.toLowerCase().contains(parametro.getValor().toLowerCase())) {
					return false;
				}

				if ("DESC".equalsIgnoreCase(parametro.getCodigo()) && descripcion != null
						&& !descripcion.toLowerCase().contains(parametro.getValor().toLowerCase())) {
					return false;
				}
			}
		}

		return true;
	}

}
