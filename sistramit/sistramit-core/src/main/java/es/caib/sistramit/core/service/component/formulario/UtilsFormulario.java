package es.caib.sistramit.core.service.component.formulario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistra2.commons.xml.formulario.v1.model.CAMPO;
import es.caib.sistra2.commons.xml.formulario.v1.model.FORMULARIO;
import es.caib.sistra2.commons.xml.formulario.v1.model.FORMULARIODocument;
import es.caib.sistra2.commons.xml.formulario.v1.model.VALOR;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteCheckbox;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RLineaComponentes;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RParametroDominio;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.XmlFormularioException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTexto;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeListaValores;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeParametroDominio;
import es.caib.sistramit.core.service.model.script.formulario.PlgDatosFormularioInt;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Clase de utilidades para formularios.
 *
 * @author Indra
 *
 */
public class UtilsFormulario {

	/** Pattern que identifica els noms de variables. */
	private static final Pattern PLUGINFORM_PATTERN = Pattern
			.compile(PlgDatosFormularioInt.ID + "\\.get\\w*\\(['|\"](\\w*-?\\w*)*");

	/**
	 * Crea valor vacío según tipo de campo.
	 *
	 * @param idCampo
	 *            Id campo
	 * @param tipo
	 *            Tipo de campo
	 * @return Valor campo vacio
	 */
	public static ValorCampo crearValorVacio(final String idCampo, final TypeValor tipo) {
		ValorCampo res = null;
		switch (tipo) {
		case SIMPLE:
			res = ValorCampoSimple.createValorVacio(idCampo);
			break;
		case INDEXADO:
			res = ValorCampoIndexado.createValorVacio(idCampo);
			break;
		case LISTA_INDEXADOS:
			res = ValorCampoListaIndexados.createValorVacio(idCampo);
			break;
		default:
			throw new TipoNoControladoException(msgTipoValorCampoNoControlado(tipo.name()));
		}
		return res;
	}

	/**
	 * Interpreta xml de formulario y devuelve lista de valores de los campos.
	 *
	 * @param pXml
	 *            Xml datos formulario
	 * @return Lista de valores de los campos.
	 */
	public static XmlFormulario xmlToValores(final byte[] pXml) {
		final List<ValorCampo> datos = new ArrayList<ValorCampo>();
		String accionPersonalizada = "";
		String xmlStr = null;

		try {
			if (pXml != null) {
				xmlStr = new String(pXml, Constantes.UTF8);
				final FORMULARIODocument doc = FORMULARIODocument.Factory.parse(xmlStr);

				accionPersonalizada = doc.getFORMULARIO().getAccion();

				for (final CAMPO campo : doc.getFORMULARIO().getCAMPOArray()) {
					if (campo.getTipo() == CAMPO.Tipo.SIMPLE) {
						final ValorCampoSimple vcfs = ValorCampoSimple.createNewValorCampoSimple();
						vcfs.setId(campo.getId());
						vcfs.setValor(campo.getVALORArray()[0].getStringValue());
						datos.add(vcfs);
					}

					if (campo.getTipo() == CAMPO.Tipo.COMPUESTO) {
						final ValorCampoIndexado vcfi = ValorCampoIndexado.createNewValorCampoIndexado();
						vcfi.setId(campo.getId());
						if (campo.getVALORArray().length > 0) {
							vcfi.setValor(ValorIndexado.createNewValorIndexado(campo.getVALORArray()[0].getCodigo(),
									campo.getVALORArray()[0].getStringValue()));
						}
						datos.add(vcfi);
					}

					if (campo.getTipo() == CAMPO.Tipo.MULTIVALUADO) {
						final ValorCampoListaIndexados lstValor = ValorCampoListaIndexados
								.createNewValorCampoListaIndexados();
						lstValor.setId(campo.getId());
						for (final VALOR valor : campo.getVALORArray()) {
							lstValor.addValorIndexado(valor.getCodigo(), valor.getStringValue());
						}
						datos.add(lstValor);
					}

				}
			}
		} catch (final UnsupportedEncodingException uee) {
			throw new XmlFormularioException("Encoding UTF-8 no soportado", uee);
		} catch (final XmlException e) {
			throw new XmlFormularioException("Error al interpretar xml", e);
		}

		final XmlFormulario res = new XmlFormulario();
		res.setValores(datos);
		res.setAccionPersonalizada(accionPersonalizada);
		return res;
	}

	/**
	 * Genera XML formulario.
	 *
	 * @param xmlFormulario
	 *            xml formulario
	 *
	 * @return xml formulario
	 */
	public static byte[] valoresToXml(XmlFormulario form) {
		final String accionPersonalizada = form.getAccionPersonalizada();
		final List<ValorCampo> campos = form.getValores();
		return generarXml(accionPersonalizada, campos);
	}

	/**
	 * Comprueba si el campo tiene caracteres permitidos.
	 *
	 * @param campo
	 *            Campo
	 * @return boolean
	 */
	public static boolean comprobarCaracteresPermitidos(final ValorCampo campo) {
		boolean res = true;
		if (!campo.esVacio()) {
			switch (campo.getTipo()) {
			case SIMPLE:
				final String v1 = ((ValorCampoSimple) campo).getValor();
				res = validarCaracteresPermitidos(v1);
				break;
			case INDEXADO:
				final ValorIndexado vi1 = ((ValorCampoIndexado) campo).getValor();
				res = validarCaracteresPermitidos(vi1.getValor());
				if (res) {
					res = validarCaracteresPermitidos(vi1.getDescripcion());
				}
				break;
			case LISTA_INDEXADOS:
				final List<ValorIndexado> vli = ((ValorCampoListaIndexados) campo).getValor();
				for (final ValorIndexado vi : vli) {
					res = validarCaracteresPermitidos(vi.getValor());
					if (res) {
						res = validarCaracteresPermitidos(vi.getDescripcion());
					}
					if (!res) {
						break;
					}
				}
				break;
			default:
				throw new TipoNoControladoException(msgTipoValorCampoNoControlado(campo.getTipo().name()));
			}
		}

		return res;
	}

	/**
	 * Método para Msg tipo valor campo no controlado de la clase UtilsFormulario.
	 *
	 * @param campo
	 *            Parámetro campo
	 * @return el string
	 */
	private static String msgTipoValorCampoNoControlado(final String campo) {
		return "Tipo de valor campo " + campo + " no controlado";
	}

	/**
	 * Validacion estandar de los caracteres permitidos. Todos excepto "<" y ">".
	 *
	 * @param valor
	 *            Valor
	 * @return boolean
	 */
	private static boolean validarCaracteresPermitidos(final String valor) {
		return XssFilter.filtroXss(valor);
	}

	/**
	 * Genera xml.
	 *
	 * @param pAccionPersonalizada
	 *            Accion personalizada
	 * @param pCampos
	 *            Campos
	 * @return XML
	 */
	private static byte[] generarXml(final String pAccionPersonalizada, final List<ValorCampo> pCampos) {
		final XmlOptions xmlOptions = new XmlOptions().setCharacterEncoding(Constantes.UTF8);
		final FORMULARIODocument fd = FORMULARIODocument.Factory.newInstance();
		final FORMULARIO formulario = fd.addNewFORMULARIO();
		formulario.setAccion(pAccionPersonalizada);
		for (final ValorCampo valorCampo : pCampos) {

			final CAMPO campoXml = formulario.addNewCAMPO();
			campoXml.setId(valorCampo.getId());

			switch (valorCampo.getTipo()) {
			case SIMPLE:
				final ValorCampoSimple vcs = (ValorCampoSimple) valorCampo;
				campoXml.setTipo(CAMPO.Tipo.SIMPLE);
				final VALOR valorXml = campoXml.addNewVALOR();
				String valor = vcs.getValor();
				if (valor != null) {
					valor = StringUtils.replace(valor, "`", "'");
					valor = StringUtils.replace(valor, "´", "'");
					valor = StringUtils.replace(valor, "‘", "'");
					valor = StringUtils.replace(valor, "’", "'");
				}
				valorXml.setStringValue(valor);
				break;
			case INDEXADO:
				final ValorCampoIndexado vci = (ValorCampoIndexado) valorCampo;
				campoXml.setTipo(CAMPO.Tipo.COMPUESTO);
				if (vci.getValor() != null) {
					final VALOR valorIndexadoXml = campoXml.addNewVALOR();
					valorIndexadoXml.setCodigo(vci.getValor().getValor());
					valorIndexadoXml.setStringValue(vci.getValor().getDescripcion());
				}
				break;
			case LISTA_INDEXADOS:
				final ValorCampoListaIndexados vcl = (ValorCampoListaIndexados) valorCampo;
				campoXml.setTipo(CAMPO.Tipo.MULTIVALUADO);
				if (vcl.getValor() != null) {
					for (final ValorIndexado vi : vcl.getValor()) {
						final VALOR valorListaIndexadoXml = campoXml.addNewVALOR();
						valorListaIndexadoXml.setCodigo(vi.getValor());
						valorListaIndexadoXml.setStringValue(vi.getDescripcion());
					}
				}
				break;
			default:
				throw new TipoNoControladoException("Tipo de campo no permitido: " + valorCampo.getTipo().name());
			}

		}

		byte[] xml = null;

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(pCampos.size() * ConstantesNumero.N60)) {
			fd.save(baos, xmlOptions);
			xml = baos.toByteArray();
		} catch (final UnsupportedEncodingException uee) {
			throw new XmlFormularioException("Encoding UTF-8 no soportado", uee);
		} catch (final IOException e) {
			throw new XmlFormularioException("Error al generar xml", e);
		}

		return xml;
	}

	/**
	 * Comprueba si es un campo oculto (tipo texto y subtipo oculto).
	 *
	 * @param configuracionCampo
	 *            Configuracion campo
	 * @return Indica si es campo oculto
	 */
	public static boolean esCampoOculto(final ConfiguracionCampo configuracionCampo) {
		return configuracionCampo.getTipo() == TypeCampo.TEXTO
				&& ((ConfiguracionCampoTexto) configuracionCampo).getContenido() == TypeTexto.OCULTO;
	}

	/**
	 * Busca valor campo.
	 *
	 * @param valoresCampo
	 *            Lista de valores
	 * @param idCampo
	 *            Id campo
	 * @return Valor campo (null si no lo encuentra)
	 */
	public static ValorCampo buscarValorCampo(final List<ValorCampo> valoresCampo, final String idCampo) {
		ValorCampo res = null;
		for (final ValorCampo vc : valoresCampo) {
			if (vc.getId().equals(idCampo)) {
				res = vc;
				break;
			}
		}
		return res;
	}

	/**
	 * Devuelve lista de campos de la página.
	 *
	 * @param defPagina
	 *            Definición página
	 * @return Lista campos
	 */
	public static List<RComponente> devuelveListaCampos(RPaginaFormulario defPagina) {
		final List<RComponente> campos = new ArrayList<>();
		for (final RLineaComponentes linea : defPagina.getLineas()) {
			for (final RComponente c : linea.getComponentes()) {
				if (esCampo(c)) {
					campos.add(c);
				}
			}
		}
		return campos;
	}

	/**
	 * Verifica si el componente es un campo de datos.
	 *
	 * @param componente
	 * @return boolean
	 */
	public static boolean esCampo(final RComponente pCampoDef) {
		final TypeCampo tipoCampo = TypeCampo.fromString(pCampoDef.getTipo());
		return (tipoCampo != null);
	}

	/**
	 * Crea valor vacío según tipo de campo.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return Valor campo vacio
	 */
	public static ValorCampo crearValorVacio(final RComponente pCampoDef) {
		final TypeValor tipoValor = obtenerTipoValorCampo(pCampoDef);
		final ValorCampo res = crearValorVacio(pCampoDef.getIdentificador(), tipoValor);
		return res;
	}

	/**
	 * Busca dependencias con otros campos.
	 *
	 * @param pCampoDef
	 *            Definición campo
	 * @return Lista de dependencias con otros campos
	 */
	public static DependenciaCampo buscarDependenciasCampo(final RComponente pCampoDef) {

		final DependenciaCampo res = new DependenciaCampo(pCampoDef.getIdentificador());

		final TypeCampo tipoCampo = TypeCampo.fromString(pCampoDef.getTipo());
		final RPropiedadesCampo propsCampo = obtenerPropiedadesCampo(pCampoDef);

		// Si es un selector, buscamos si referencia algun campo
		if (tipoCampo == TypeCampo.SELECTOR) {
			res.addDependenciasSelector(UtilsFormulario.buscarDependenciasSelector((RComponenteSelector) pCampoDef));
		}
		// Script autorrellenable
		if (UtilsSTG.existeScript(propsCampo.getScriptAutorrellenable())) {
			res.addDependenciasAutorrellenable(
					UtilsFormulario.buscarDependenciasScript(propsCampo.getScriptAutorrellenable()));
		}
		// Script obligatoriedad
		if (UtilsSTG.existeScript(propsCampo.getScriptEstado())) {
			res.addDependenciasEstado(UtilsFormulario.buscarDependenciasScript(propsCampo.getScriptEstado()));
		}

		// Devolvemos dependencias
		return res;
	}

	/**
	 * Obtiene propiedades generales campo datos.
	 *
	 * @param pCampoDef
	 *            Campo
	 * @return
	 */
	public static RPropiedadesCampo obtenerPropiedadesCampo(RComponente pCampoDef) {
		final TypeCampo tipoCampo = TypeCampo.fromString(pCampoDef.getTipo());
		RPropiedadesCampo propsCampo = null;
		switch (tipoCampo) {
		case TEXTO:
			propsCampo = ((RComponenteTextbox) pCampoDef).getPropiedadesCampo();
			break;
		case SELECTOR:
			propsCampo = ((RComponenteSelector) pCampoDef).getPropiedadesCampo();
			break;
		case VERIFICACION:
			propsCampo = ((RComponenteCheckbox) pCampoDef).getPropiedadesCampo();
			break;
		default:
			throw new TipoNoControladoException("Tipo de campo " + tipoCampo + " no controlado");
		}
		return propsCampo;
	}

	// ---------------------------------------------------------------------------------------------------
	// Funciones auxiliares
	// ---------------------------------------------------------------------------------------------------

	/**
	 * Busca dependencias con otros campos dentro de un script.
	 *
	 * @param script
	 *            Script SCript
	 * @return Lista de dependencias con otros campos
	 */
	private static List<String> buscarDependenciasScript(final RScript rScript) {
		final List<String> deps = new ArrayList<String>();
		final String script = rScript.getScript();
		// Quitamos todos los espacios en blanco y saltos de línea
		String scriptSearch = script.replaceAll("\\r*\\n", "");
		scriptSearch = scriptSearch.replaceAll("\\s", "");
		// Buscamos PLUGIN_FORMULARIOS y se queda con el primer parametro (idcampo)
		final Matcher matcher = PLUGINFORM_PATTERN.matcher(scriptSearch);
		while (matcher.find()) {
			final String sentencia = matcher.group();
			final String[] params = sentencia.split("\\(");
			deps.add(params[ConstantesNumero.N1].replaceAll("['|\"]", ""));
		}
		return deps;
	}

	/**
	 * Obtiene tipo de valor de un campo.
	 *
	 * @param pCampoDef
	 *            Definición campo
	 * @return Tipo valor
	 */
	private static TypeValor obtenerTipoValorCampo(final RComponente pCampoDef) {
		TypeValor tipoValor = null;
		final TypeCampo tipoCampo = TypeCampo.fromString(pCampoDef.getTipo());
		switch (tipoCampo) {
		case TEXTO:
			tipoValor = TypeValor.SIMPLE;
			break;
		case SELECTOR:
			final TypeSelector tipoSelector = TypeSelector
					.fromString(((RComponenteSelector) pCampoDef).getTipoSelector());
			if (tipoSelector == TypeSelector.MULTIPLE) {
				tipoValor = TypeValor.LISTA_INDEXADOS;
			} else {
				tipoValor = TypeValor.INDEXADO;
			}
			break;
		case VERIFICACION:
			tipoValor = TypeValor.SIMPLE;
			break;
		default:
			throw new TipoNoControladoException("Tipo de campo " + tipoCampo + " no controlado");
		}
		return tipoValor;
	}

	/**
	 * Busca dependencias con otros campos dentro de la configuración de un
	 * selector.
	 *
	 * @param pCampoDef
	 *            Definición campo selector
	 * @return Lista de dependencias con otros campos
	 */
	private static List<String> buscarDependenciasSelector(final RComponenteSelector pCampoDef) {

		final List<String> deps = new ArrayList<>();

		final TypeListaValores tipoListaValores = TypeListaValores.fromString(pCampoDef.getTipoListaValores());

		// Si es de tipo dominio, buscamos si tiene parametros referenciados a campos
		if (tipoListaValores == TypeListaValores.DOMINIO) {
			if (pCampoDef.getListaDominio().getParametros() != null) {
				for (final RParametroDominio param : pCampoDef.getListaDominio().getParametros()) {
					if (TypeParametroDominio.fromString(param.getTipo()) == TypeParametroDominio.CAMPO) {
						deps.add(param.getValor());
					}
				}
			}
		}

		// Si es de tipo script, busca dependencias script
		if (tipoListaValores == TypeListaValores.SCRIPT) {
			if (UtilsSTG.existeScript(pCampoDef.getScriptListaValores())) {
				deps.addAll(UtilsFormulario.buscarDependenciasScript(pCampoDef.getScriptListaValores()));
			}
		}

		return deps;
	}

}
