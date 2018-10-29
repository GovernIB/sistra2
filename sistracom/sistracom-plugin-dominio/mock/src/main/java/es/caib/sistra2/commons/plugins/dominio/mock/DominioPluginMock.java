package es.caib.sistra2.commons.plugins.dominio.mock;

import java.util.Base64;
import java.util.List;

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
public class DominioPluginMock implements IDominioPlugin {

	/**
	 * Invoca dominio remoto.
	 *
	 * @param idDominio
	 *            id dominio
	 * @param url
	 *            url
	 * @param parametros
	 *            parametros
	 * @return valores dominio
	 * @throws DominioPluginException
	 */
	@Override
	public ValoresDominio invocarDominio(final String idDominio, final String url,
			final List<ParametroDominio> parametros) throws DominioPluginException {
		ValoresDominio retorno = null;
		if (idDominio == null) {
			retorno = getValorDominioSimple(parametros);
		} else {
			switch (idDominio) {
			case "fichero":
				retorno = getValorDominioFichero(parametros);
				break;
			case "municipio":
				retorno = getValorDominioMuncipio(parametros);
				break;
			case "provincia":
				retorno = getValorDominioProvincia(parametros);
				break;
			case "defecto":
			default:
				retorno = getValorDominioSimple(parametros);
				break;
			}
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

		int posicion = 0;
		if (checkValido("01", "Alava", parametros)) {
			valores.setValor(posicion, "ID", "01");
			valores.setValor(posicion, "DESC", "Alava");
			posicion++;
		}
		if (checkValido("02", "Albacete", parametros)) {
			valores.setValor(posicion, "ID", "02");
			valores.setValor(posicion, "DESC", "Albacete");
			posicion++;
		}
		if (checkValido("03", "Alicante", parametros)) {
			valores.setValor(posicion, "ID", "03");
			valores.setValor(posicion, "DESC", "Alicante");
			posicion++;
		}
		if (checkValido("04", "Almería", parametros)) {
			valores.setValor(posicion, "ID", "04");
			valores.setValor(posicion, "DESC", "Almería");
			posicion++;
		}
		if (checkValido("05", "Ávila", parametros)) {
			valores.setValor(posicion, "ID", "05");
			valores.setValor(posicion, "DESC", "Ávila");
			posicion++;
		}
		if (checkValido("06", "Badajoz", parametros)) {
			valores.setValor(posicion, "ID", "06");
			valores.setValor(posicion, "DESC", "Badajoz");
			posicion++;
		}
		if (checkValido("07", "Baleares", parametros)) {
			valores.setValor(posicion, "ID", "07");
			valores.setValor(posicion, "DESC", "Baleares");
			posicion++;
		}
		if (checkValido("08", "Barcelona", parametros)) {
			valores.setValor(posicion, "ID", "08");
			valores.setValor(posicion, "DESC", "Barcelona");
			posicion++;
		}
		if (checkValido("09", "Burgos", parametros)) {
			valores.setValor(posicion, "ID", "09");
			valores.setValor(posicion, "DESC", "Burgos");
			posicion++;
		}
		if (checkValido("10", "Cáceres", parametros)) {
			valores.setValor(posicion, "ID", "10");
			valores.setValor(posicion, "DESC", "Cáceres");
			posicion++;
		}
		if (checkValido("11", "Cádiz", parametros)) {
			valores.setValor(posicion, "ID", "11");
			valores.setValor(posicion, "DESC", "Cádiz");
			posicion++;
		}
		if (checkValido("12", "Castellón", parametros)) {
			valores.setValor(posicion, "ID", "12");
			valores.setValor(posicion, "DESC", "Castellón");
			posicion++;
		}
		if (checkValido("13", "Ciudad Real", parametros)) {
			valores.setValor(posicion, "ID", "13");
			valores.setValor(posicion, "DESC", "Ciudad Real");
			posicion++;
		}
		if (checkValido("14", "Córdoba", parametros)) {
			valores.setValor(posicion, "ID", "14");
			valores.setValor(posicion, "DESC", "Córdoba");
			posicion++;
		}
		if (checkValido("15", "Coruña", parametros)) {
			valores.setValor(posicion, "ID", "15");
			valores.setValor(posicion, "DESC", "Coruña");
			posicion++;
		}
		if (checkValido("16", "Cuenca", parametros)) {
			valores.setValor(posicion, "ID", "16");
			valores.setValor(posicion, "DESC", "Cuenca");
			posicion++;
		}
		if (checkValido("17", "Gerona", parametros)) {
			valores.setValor(posicion, "ID", "17");
			valores.setValor(posicion, "DESC", "Gerona");
			posicion++;
		}
		if (checkValido("18", "Granada", parametros)) {
			valores.setValor(posicion, "ID", "18");
			valores.setValor(posicion, "DESC", "Granada");
			posicion++;
		}
		if (checkValido("19", "Guadalajara", parametros)) {
			valores.setValor(posicion, "ID", "19");
			valores.setValor(posicion, "DESC", "Guadalajara");
			posicion++;
		}
		if (checkValido("20", "Guipúzcoa", parametros)) {
			valores.setValor(posicion, "ID", "20");
			valores.setValor(posicion, "DESC", "Guipúzcoa");
			posicion++;
		}
		if (checkValido("21", "Huelva", parametros)) {
			valores.setValor(posicion, "ID", "21");
			valores.setValor(posicion, "DESC", "Huelva");
			posicion++;
		}
		if (checkValido("22", "Huesca", parametros)) {
			valores.setValor(posicion, "ID", "22");
			valores.setValor(posicion, "DESC", "Huesca");
			posicion++;
		}
		if (checkValido("23", "Jaén", parametros)) {
			valores.setValor(posicion, "ID", "23");
			valores.setValor(posicion, "DESC", "Jaén");
			posicion++;
		}
		if (checkValido("24", "León", parametros)) {
			valores.setValor(posicion, "ID", "24");
			valores.setValor(posicion, "DESC", "León");
			posicion++;
		}
		if (checkValido("25", "Lérida", parametros)) {
			valores.setValor(posicion, "ID", "25");
			valores.setValor(posicion, "DESC", "Lérida");
			posicion++;
		}
		if (checkValido("26", "La Rioja", parametros)) {
			valores.setValor(posicion, "ID", "26");
			valores.setValor(posicion, "DESC", "La Rioja");
			posicion++;
		}
		if (checkValido("27", "Lugo", parametros)) {
			valores.setValor(posicion, "ID", "27");
			valores.setValor(posicion, "DESC", "Lugo");
			posicion++;
		}
		if (checkValido("28", "Madrid", parametros)) {
			valores.setValor(posicion, "ID", "28");
			valores.setValor(posicion, "DESC", "Madrid");
			posicion++;
		}
		if (checkValido("29", "Málaga", parametros)) {
			valores.setValor(posicion, "ID", "29");
			valores.setValor(posicion, "DESC", "Málaga");
			posicion++;
		}
		if (checkValido("30", "Murcia", parametros)) {
			valores.setValor(posicion, "ID", "30");
			valores.setValor(posicion, "DESC", "Murcia");
			posicion++;
		}
		if (checkValido("31", "Navarra", parametros)) {
			valores.setValor(posicion, "ID", "31");
			valores.setValor(posicion, "DESC", "Navarra");
			posicion++;
		}
		if (checkValido("32", "Orense", parametros)) {
			valores.setValor(posicion, "ID", "32");
			valores.setValor(posicion, "DESC", "Orense");
			posicion++;
		}
		if (checkValido("33", "Asturias", parametros)) {
			valores.setValor(posicion, "ID", "33");
			valores.setValor(posicion, "DESC", "Asturias");
			posicion++;
		}
		if (checkValido("34", "Palencia", parametros)) {
			valores.setValor(posicion, "ID", "34");
			valores.setValor(posicion, "DESC", "Palencia");
			posicion++;
		}
		if (checkValido("35", "Las Palmas", parametros)) {
			valores.setValor(posicion, "ID", "35");
			valores.setValor(posicion, "DESC", "Las Palmas");
			posicion++;
		}
		if (checkValido("36", "Pontevedra", parametros)) {
			valores.setValor(posicion, "ID", "36");
			valores.setValor(posicion, "DESC", "Pontevedra");
			posicion++;
		}
		if (checkValido("37", "Salamanca", parametros)) {
			valores.setValor(posicion, "ID", "37");
			valores.setValor(posicion, "DESC", "Salamanca");
			posicion++;
		}
		if (checkValido("38", "Santa Cruz de Tenerife", parametros)) {
			valores.setValor(posicion, "ID", "38");
			valores.setValor(posicion, "DESC", "Santa Cruz de Tenerife");
			posicion++;
		}
		if (checkValido("39", "Cantabria", parametros)) {
			valores.setValor(posicion, "ID", "39");
			valores.setValor(posicion, "DESC", "Cantabria");
			posicion++;
		}
		if (checkValido("40", "Segovia", parametros)) {
			valores.setValor(posicion, "ID", "40");
			valores.setValor(posicion, "DESC", "Segovia");
			posicion++;
		}
		if (checkValido("41", "Sevilla", parametros)) {
			valores.setValor(posicion, "ID", "41");
			valores.setValor(posicion, "DESC", "Sevilla");
			posicion++;
		}
		if (checkValido("42", "Soria", parametros)) {
			valores.setValor(posicion, "ID", "42");
			valores.setValor(posicion, "DESC", "Soria");
			posicion++;
		}
		if (checkValido("43", "Tarragona", parametros)) {
			valores.setValor(posicion, "ID", "43");
			valores.setValor(posicion, "DESC", "Tarragona");
			posicion++;
		}
		if (checkValido("44", "Teruel", parametros)) {
			valores.setValor(posicion, "ID", "44");
			valores.setValor(posicion, "DESC", "Teruel");
			posicion++;
		}
		if (checkValido("45", "Toledo", parametros)) {
			valores.setValor(posicion, "ID", "45");
			valores.setValor(posicion, "DESC", "Toledo");
			posicion++;
		}
		if (checkValido("46", "Valencia", parametros)) {
			valores.setValor(posicion, "ID", "46");
			valores.setValor(posicion, "DESC", "Valencia");
			posicion++;
		}
		if (checkValido("47", "Valladolid", parametros)) {
			valores.setValor(posicion, "ID", "47");
			valores.setValor(posicion, "DESC", "Valladolid");
			posicion++;
		}
		if (checkValido("48", "Vizcaya", parametros)) {
			valores.setValor(posicion, "ID", "48");
			valores.setValor(posicion, "DESC", "Vizcaya");
			posicion++;
		}
		if (checkValido("49", "Zamora", parametros)) {
			valores.setValor(posicion, "ID", "49");
			valores.setValor(posicion, "DESC", "Zamora");
			posicion++;
		}
		if (checkValido("50", "Zaragoza", parametros)) {
			valores.setValor(posicion, "ID", "50");
			valores.setValor(posicion, "DESC", "Zaragoza");
			posicion++;
		}
		if (checkValido("51", "Ceuta", parametros)) {
			valores.setValor(posicion, "ID", "51");
			valores.setValor(posicion, "DESC", "Ceuta");
			posicion++;
		}
		if (checkValido("52", "Melilla", parametros)) {
			valores.setValor(posicion, "ID", "52");
			valores.setValor(posicion, "DESC", "Melilla");
		}
		return valores;
	}

	private ValoresDominio getValorDominioMuncipio(final List<ParametroDominio> parametros) {
		final ValoresDominio valores = new ValoresDominio();
		int posicion = 0;
		if (checkValido("0027", "Alaior", parametros)) {
			valores.setValor(posicion, "ID", "0027");
			valores.setValor(posicion, "DESC", "Alaior");
			posicion++;
		}
		if (checkValido("0012", "Alaró", parametros)) {
			valores.setValor(posicion, "ID", "0012");
			valores.setValor(posicion, "DESC", "Alaró");
			posicion++;
		}
		if (checkValido("0033", "Alcúdia", parametros)) {
			valores.setValor(posicion, "ID", "0033");
			valores.setValor(posicion, "DESC", "Alcúdia");
			posicion++;
		}
		if (checkValido("0048", "Algaida", parametros)) {
			valores.setValor(posicion, "ID", "0048");
			valores.setValor(posicion, "DESC", "Algaida");
			posicion++;
		}
		if (checkValido("0051", "Andratx", parametros)) {
			valores.setValor(posicion, "ID", "0051");
			valores.setValor(posicion, "DESC", "Andratx");
			posicion++;
		}
		if (checkValido("9013", "Ariany", parametros)) {
			valores.setValor(posicion, "ID", "9013");
			valores.setValor(posicion, "DESC", "Ariany");
			posicion++;
		}
		if (checkValido("0064", "Artà", parametros)) {
			valores.setValor(posicion, "ID", "0064");
			valores.setValor(posicion, "DESC", "Artà");
			posicion++;
		}
		if (checkValido("0070", "Banyalbufar", parametros)) {
			valores.setValor(posicion, "ID", "0070");
			valores.setValor(posicion, "DESC", "Banyalbufar");
			posicion++;
		}
		if (checkValido("0086", "Binissalem", parametros)) {
			valores.setValor(posicion, "ID", "0086");
			valores.setValor(posicion, "DESC", "Binissalem");
			posicion++;
		}
		if (checkValido("0099", "Búger", parametros)) {
			valores.setValor(posicion, "ID", "0099");
			valores.setValor(posicion, "DESC", "Búger");
			posicion++;
		}
		if (checkValido("0103", "Bunyola", parametros)) {
			valores.setValor(posicion, "ID", "0103");
			valores.setValor(posicion, "DESC", "Bunyola");
			posicion++;
		}
		if (checkValido("0110", "Calvià", parametros)) {
			valores.setValor(posicion, "ID", "0110");
			valores.setValor(posicion, "DESC", "Calvià");
			posicion++;
		}
		if (checkValido("0125", "Campanet", parametros)) {
			valores.setValor(posicion, "ID", "0125");
			valores.setValor(posicion, "DESC", "Campanet");
			posicion++;
		}
		if (checkValido("0131", "Campos", parametros)) {
			valores.setValor(posicion, "ID", "0131");
			valores.setValor(posicion, "DESC", "Campos");
			posicion++;
		}
		if (checkValido("0146", "Capdepera", parametros)) {
			valores.setValor(posicion, "ID", "0146");
			valores.setValor(posicion, "DESC", "Capdepera");
			posicion++;
		}
		if (checkValido("0645", "Castell (Es)", parametros)) {
			valores.setValor(posicion, "ID", "0645");
			valores.setValor(posicion, "DESC", "Castell (Es)");
			posicion++;
		}
		if (checkValido("0159", "Ciutadella de Menorca", parametros)) {
			valores.setValor(posicion, "ID", "0159");
			valores.setValor(posicion, "DESC", "Ciutadella de Menorca");
			posicion++;
		}
		if (checkValido("0162", "Consell", parametros)) {
			valores.setValor(posicion, "ID", "0162");
			valores.setValor(posicion, "DESC", "Consell");
			posicion++;
		}
		if (checkValido("0178", "Costitx", parametros)) {
			valores.setValor(posicion, "ID", "0178");
			valores.setValor(posicion, "DESC", "Costitx");
			posicion++;
		}
		if (checkValido("0184", "Deyá", parametros)) {
			valores.setValor(posicion, "ID", "0184");
			valores.setValor(posicion, "DESC", "Deyá");
			posicion++;
		}
		if (checkValido("0260", "Eivissa", parametros)) {
			valores.setValor(posicion, "ID", "0260");
			valores.setValor(posicion, "DESC", "Eivissa");
			posicion++;
		}
		if (checkValido("0197", "Escorca", parametros)) {
			valores.setValor(posicion, "ID", "0197");
			valores.setValor(posicion, "DESC", "Escorca");
			posicion++;
		}
		if (checkValido("0201", "Esporles", parametros)) {
			valores.setValor(posicion, "ID", "0201");
			valores.setValor(posicion, "DESC", "Esporles");
			posicion++;
		}
		if (checkValido("0218", "Estellencs", parametros)) {
			valores.setValor(posicion, "ID", "0218");
			valores.setValor(posicion, "DESC", "Estellencs");
			posicion++;
		}
		if (checkValido("0223", "Felanitx", parametros)) {
			valores.setValor(posicion, "ID", "0223");
			valores.setValor(posicion, "DESC", "Felanitx");
			posicion++;
		}
		if (checkValido("0239", "Ferreries", parametros)) {
			valores.setValor(posicion, "ID", "0239");
			valores.setValor(posicion, "DESC", "Ferreries");
			posicion++;
		}
		if (checkValido("0244", "Formentera", parametros)) {
			valores.setValor(posicion, "ID", "0244");
			valores.setValor(posicion, "DESC", "Formentera");
			posicion++;
		}
		if (checkValido("0257", "Fornalutx", parametros)) {
			valores.setValor(posicion, "ID", "0257");
			valores.setValor(posicion, "DESC", "Fornalutx");
			posicion++;
		}
		if (checkValido("0276", "Inca", parametros)) {
			valores.setValor(posicion, "ID", "0276");
			valores.setValor(posicion, "DESC", "Inca");
			posicion++;
		}
		if (checkValido("0282", "Lloret de Vistalegre", parametros)) {
			valores.setValor(posicion, "ID", "0282");
			valores.setValor(posicion, "DESC", "Lloret de Vistalegre");
			posicion++;
		}
		if (checkValido("0295", "Lloseta", parametros)) {
			valores.setValor(posicion, "ID", "0295");
			valores.setValor(posicion, "DESC", "Lloseta");
			posicion++;
		}
		if (checkValido("0309", "Llubí", parametros)) {
			valores.setValor(posicion, "ID", "0309");
			valores.setValor(posicion, "DESC", "Llubí");
			posicion++;
		}
		if (checkValido("0316", "Llucmajor", parametros)) {
			valores.setValor(posicion, "ID", "0316");
			valores.setValor(posicion, "DESC", "Llucmajor");
			posicion++;
		}
		if (checkValido("0337", "Manacor", parametros)) {
			valores.setValor(posicion, "ID", "0337");
			valores.setValor(posicion, "DESC", "Manacor");
			posicion++;
		}
		if (checkValido("0342", "Mancor de la Vall", parametros)) {
			valores.setValor(posicion, "ID", "0342");
			valores.setValor(posicion, "DESC", "Mancor de la Vall");
			posicion++;
		}
		if (checkValido("0321", "Maó", parametros)) {
			valores.setValor(posicion, "ID", "0321");
			valores.setValor(posicion, "DESC", "Maó");
			posicion++;
		}
		if (checkValido("0355", "Maria de la Salut", parametros)) {
			valores.setValor(posicion, "ID", "0355");
			valores.setValor(posicion, "DESC", "Maria de la Salut");
			posicion++;
		}
		if (checkValido("0368", "Marratxí", parametros)) {
			valores.setValor(posicion, "ID", "0368");
			valores.setValor(posicion, "DESC", "Marratxí");
			posicion++;
		}
		if (checkValido("0374", "Mercadal (Es)", parametros)) {
			valores.setValor(posicion, "ID", "0374");
			valores.setValor(posicion, "DESC", "Mercadal (Es)");
			posicion++;
		}
		if (checkValido("9028", "Migjorn Gran (Es)", parametros)) {
			valores.setValor(posicion, "ID", "9028");
			valores.setValor(posicion, "DESC", "Migjorn Gran (Es)");
			posicion++;
		}
		if (checkValido("0380", "Montuïri", parametros)) {
			valores.setValor(posicion, "ID", "0380");
			valores.setValor(posicion, "DESC", "Montuïri");
			posicion++;
		}
		if (checkValido("0393", "Muro", parametros)) {
			valores.setValor(posicion, "ID", "0393");
			valores.setValor(posicion, "DESC", "Muro");
			posicion++;
		}
		if (checkValido("0407", "Palma de Mallorca", parametros)) {
			valores.setValor(posicion, "ID", "0407");
			valores.setValor(posicion, "DESC", "Palma de Mallorca");
			posicion++;
		}
		if (checkValido("0414", "Petra", parametros)) {
			valores.setValor(posicion, "ID", "0414");
			valores.setValor(posicion, "DESC", "Petra");
			posicion++;
		}
		if (checkValido("0440", "Pobla (Sa)", parametros)) {
			valores.setValor(posicion, "ID", "0440");
			valores.setValor(posicion, "DESC", "Pobla (Sa)");
			posicion++;
		}
		if (checkValido("0429", "Pollença", parametros)) {
			valores.setValor(posicion, "ID", "0429");
			valores.setValor(posicion, "DESC", "Pollença");
			posicion++;
		}
		if (checkValido("0435", "Porreres", parametros)) {
			valores.setValor(posicion, "ID", "0435");
			valores.setValor(posicion, "DESC", "Porreres");
			posicion++;
		}
		if (checkValido("0453", "Puigpunyent", parametros)) {
			valores.setValor(posicion, "ID", "0453");
			valores.setValor(posicion, "DESC", "Puigpunyent");
			posicion++;
		}
		if (checkValido("0598", "Salines (Ses)", parametros)) {
			valores.setValor(posicion, "ID", "0598");
			valores.setValor(posicion, "DESC", "Salines (Ses)");
			posicion++;
		}
		if (checkValido("0466", "Sant Antoni de Portmany", parametros)) {
			valores.setValor(posicion, "ID", "0466");
			valores.setValor(posicion, "DESC", "Sant Antoni de Portmany");
			posicion++;
		}
		if (checkValido("0491", "Sant Joan", parametros)) {
			valores.setValor(posicion, "ID", "0491");
			valores.setValor(posicion, "DESC", "Sant Joan");
			posicion++;
		}
		if (checkValido("0504", "Sant Joan de Labritja", parametros)) {
			valores.setValor(posicion, "ID", "0504");
			valores.setValor(posicion, "DESC", "Sant Joan de Labritja");
			posicion++;
		}
		if (checkValido("0488", "Sant Josep de sa Talaia", parametros)) {
			valores.setValor(posicion, "ID", "0488");
			valores.setValor(posicion, "DESC", "Sant Josep de sa Talaia");
			posicion++;
		}
		if (checkValido("0511", "Sant Llorenç des Cardassar", parametros)) {
			valores.setValor(posicion, "ID", "0511");
			valores.setValor(posicion, "DESC", "Sant Llorenç des Cardassar");
			posicion++;
		}
		if (checkValido("0526", "Sant Lluís", parametros)) {
			valores.setValor(posicion, "ID", "0526");
			valores.setValor(posicion, "DESC", "Sant Lluís");
			posicion++;
		}
		if (checkValido("0532", "Santa Eugènia", parametros)) {
			valores.setValor(posicion, "ID", "0532");
			valores.setValor(posicion, "DESC", "Santa Eugènia");
			posicion++;
		}
		if (checkValido("0547", "Santa Eulalia del Río", parametros)) {
			valores.setValor(posicion, "ID", "0547");
			valores.setValor(posicion, "DESC", "Santa Eulalia del Río");
			posicion++;
		}
		if (checkValido("0550", "Santa Margalida", parametros)) {
			valores.setValor(posicion, "ID", "0550");
			valores.setValor(posicion, "DESC", "Santa Margalida");
			posicion++;
		}
		if (checkValido("0563", "Santa María del Camí", parametros)) {
			valores.setValor(posicion, "ID", "0563");
			valores.setValor(posicion, "DESC", "Santa María del Camí");
			posicion++;
		}
		if (checkValido("0579", "Santanyí", parametros)) {
			valores.setValor(posicion, "ID", "0579");
			valores.setValor(posicion, "DESC", "Santanyí");
			posicion++;
		}
		if (checkValido("0585", "Selva", parametros)) {
			valores.setValor(posicion, "ID", "0585");
			valores.setValor(posicion, "DESC", "Selva");
			posicion++;
		}
		if (checkValido("0472", "Sencelles", parametros)) {
			valores.setValor(posicion, "ID", "0472");
			valores.setValor(posicion, "DESC", "Sencelles");
			posicion++;
		}
		if (checkValido("0602", "Sineu", parametros)) {
			valores.setValor(posicion, "ID", "0602");
			valores.setValor(posicion, "DESC", "Sineu");
			posicion++;
		}
		if (checkValido("0619", "Sóller", parametros)) {
			valores.setValor(posicion, "ID", "0619");
			valores.setValor(posicion, "DESC", "Sóller");
			posicion++;
		}
		if (checkValido("0624", "Son Servera", parametros)) {
			valores.setValor(posicion, "ID", "0624");
			valores.setValor(posicion, "DESC", "Son Servera");
			posicion++;
		}
		if (checkValido("0630", "Valldemossa", parametros)) {
			valores.setValor(posicion, "ID", "0630");
			valores.setValor(posicion, "DESC", "Valldemossa");
			posicion++;
		}
		if (checkValido("0658", "Vilafranca de Bonany", parametros)) {
			valores.setValor(posicion, "ID", "0658");
			valores.setValor(posicion, "DESC", "Vilafranca de Bonany");
		}

		return valores;
	}

	private ValoresDominio getValorDominioFichero(final List<ParametroDominio> parametros) {
		final ValoresDominio valores = new ValoresDominio();
		final FicheroDominio fichero1 = new FicheroDominio("Fichero1.txt",
				Base64.getEncoder().encodeToString("Contenido del fichero 1.".getBytes()));
		final FicheroDominio fichero2 = new FicheroDominio("Fichero2.txt",
				Base64.getEncoder().encodeToString("Contenido del fichero 2.".getBytes()));
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
			valores.setValor(0, "ID", "ID1");
			valores.setValor(0, "COL1", "VALOR_11");
			valores.setValor(0, "COL2", "VALOR_12");
			valores.setValor(0, "COL3", "VALOR_13");
		}

		if (checkValido("ID2", "VALOR_12", parametros)) {
			valores.setValor(1, "ID", "ID2");
			valores.setValor(1, "COL1", "VALOR_21");
			valores.setValor(1, "COL2", "VALOR_22");
			valores.setValor(1, "COL3", "VALOR_23");
		}

		if (checkValido("ID3", "VALOR_13", parametros)) {

			valores.setValor(2, "ID", "ID3");
			valores.setValor(2, "COL1", "VALOR_31");
			valores.setValor(2, "COL2", "VALOR_32");
			valores.setValor(2, "COL3", "VALOR_33");
		}

		if (checkValido("ID4", "VALOR_14", parametros)) {
			valores.setValor(3, "ID", "ID4");
			valores.setValor(3, "COL1", "VALOR_41");
			valores.setValor(3, "COL2", "VALOR_42");
			valores.setValor(3, "COL3", "VALOR_43");
		}

		if (checkValido("ID5", "VALOR_15", parametros)) {
			valores.setValor(4, "ID", "ID5");
			valores.setValor(4, "COL1", "VALOR_51");
			valores.setValor(4, "COL2", "VALOR_52");
			valores.setValor(4, "COL3", "VALOR_53");
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
