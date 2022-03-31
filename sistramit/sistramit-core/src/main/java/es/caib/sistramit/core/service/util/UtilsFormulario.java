package es.caib.sistramit.core.service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistra2.commons.xml.formulario.v1.model.CAMPO;
import es.caib.sistra2.commons.xml.formulario.v1.model.ELEMENTO;
import es.caib.sistra2.commons.xml.formulario.v1.model.FORMULARIO;
import es.caib.sistra2.commons.xml.formulario.v1.model.FORMULARIODocument;
import es.caib.sistra2.commons.xml.formulario.v1.model.VALOR;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.XmlFormularioException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaElementos;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorElemento;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;

/**
 * Clase de utilidades para formularios.
 *
 * @author Indra
 *
 */
public class UtilsFormulario {

	/**
	 * Crea valor vacío según tipo de campo.
	 *
	 * @param idCampo
	 *                    Id campo
	 * @param tipo
	 *                    Tipo de campo
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
		case LISTA_ELEMENTOS:
			res = ValorCampoListaElementos.createValorVacio(idCampo);
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
	 *                 Xml datos formulario
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
					datos.add(valorXmlToValorCampo(campo));
				}
			}
		} catch (final UnsupportedEncodingException uee) {
			throw new XmlFormularioException("Encoding UTF-8 no suportat", uee);
		} catch (final XmlException e) {
			throw new XmlFormularioException("Error al interpretar xml", e);
		}
		final XmlFormulario res = new XmlFormulario();
		res.setValores(datos);
		res.setAccionPersonalizada(accionPersonalizada);
		return res;
	}

	/**
	 * Convierte valor xml a valor campo.
	 *
	 * @param campo
	 *                  valor xml
	 * @return
	 */
	private static ValorCampo valorXmlToValorCampo(final CAMPO campo) {
		ValorCampo vc = null;
		switch (campo.getTipo().intValue()) {
		case CAMPO.Tipo.INT_SIMPLE:
			final ValorCampoSimple vcfs = ValorCampoSimple.createNewValorCampoSimple();
			vcfs.setId(campo.getId());
			vcfs.setValor(campo.getVALORArray()[0].getStringValue());
			vc = vcfs;
			break;
		case CAMPO.Tipo.INT_COMPUESTO:
			final ValorCampoIndexado vcfi = ValorCampoIndexado.createNewValorCampoIndexado();
			vcfi.setId(campo.getId());
			if (campo.getVALORArray().length > 0) {
				vcfi.setValor(ValorIndexado.createNewValorIndexado(campo.getVALORArray()[0].getCodigo(),
						campo.getVALORArray()[0].getStringValue()));
			}
			vc = vcfi;
			break;
		case CAMPO.Tipo.INT_MULTIVALUADO:
			final ValorCampoListaIndexados lstValor = ValorCampoListaIndexados.createNewValorCampoListaIndexados();
			lstValor.setId(campo.getId());
			for (final VALOR valor : campo.getVALORArray()) {
				lstValor.addValorIndexado(valor.getCodigo(), valor.getStringValue());
			}
			vc = lstValor;
			break;
		case CAMPO.Tipo.INT_LISTA:
			final ValorCampoListaElementos vcle = new ValorCampoListaElementos();
			vcle.setId(campo.getId());
			if (campo.getELEMENTOArray() != null) {
				for (final ELEMENTO e : campo.getELEMENTOArray()) {
					final ValorElemento elemento = new ValorElemento();
					if (e.getCAMPOArray() != null) {
						final List<ValorCampo> valoresElemento = new ArrayList<ValorCampo>();
						for (final CAMPO c : e.getCAMPOArray()) {
							final ValorCampo vce = valorXmlToValorCampo(c);
							valoresElemento.add(vce);
						}
						elemento.setElemento(valoresElemento);
					}
					vcle.addElemento(elemento);
				}
				vc = vcle;
			}
			break;
		default:
			throw new TipoNoControladoException("Tipus de camp " + campo.getTipo() + " no controlat");
		}
		return vc;
	}

	/**
	 * Genera XML formulario.
	 *
	 * @param xmlFormulario
	 *                          xml formulario
	 *
	 * @return xml formulario
	 */
	public static byte[] valoresToXml(final XmlFormulario form) {
		final String accionPersonalizada = form.getAccionPersonalizada();
		final List<ValorCampo> campos = form.getValores();
		return generarXml(accionPersonalizada, campos);
	}

	/**
	 * Comprueba si el campo tiene caracteres permitidos.
	 *
	 * @param campo
	 *                  Campo
	 * @return boolean
	 */
	public static boolean comprobarCaracteresPermitidos(final ValorCampo campo) {
		boolean res = true;
		if (!campo.esVacio()) {
			switch (campo.getTipo()) {
			case SIMPLE:
				final String v1 = ((ValorCampoSimple) campo).getValor();
				res = comprobarCaracteresPermitidos(v1);
				break;
			case INDEXADO:
				final ValorIndexado vi1 = ((ValorCampoIndexado) campo).getValor();
				res = comprobarCaracteresPermitidos(vi1.getValor());
				if (res) {
					res = comprobarCaracteresPermitidos(vi1.getDescripcion());
				}
				break;
			case LISTA_INDEXADOS:
				final List<ValorIndexado> vli = ((ValorCampoListaIndexados) campo).getValor();
				for (final ValorIndexado vi : vli) {
					res = comprobarCaracteresPermitidos(vi.getValor());
					if (res) {
						res = comprobarCaracteresPermitidos(vi.getDescripcion());
					}
					if (!res) {
						break;
					}
				}
				break;
			case LISTA_ELEMENTOS:
				final List<ValorElemento> vle = ((ValorCampoListaElementos) campo).getValor();
				for (final ValorElemento ve : vle) {
					for (final ValorCampo vce : ve.getElemento()) {
						res = comprobarCaracteresPermitidos(vce);
						if (!res) {
							break;
						}
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
	 * Comprueba caracteres permitidos.
	 *
	 * @param vi
	 *               Valor indexado
	 * @return boolean
	 */
	public static boolean comprobarCaracteresPermitidos(final ValorIndexado vi) {
		boolean res = UtilsFormulario.comprobarCaracteresPermitidos(vi.getValor());
		if (res) {
			res = UtilsFormulario.comprobarCaracteresPermitidos(vi.getDescripcion());
		}
		return res;
	}

	/**
	 * Validacion estandar de los caracteres permitidos. Todos excepto "<" y ">".
	 *
	 * @param valor
	 *                  Valor
	 * @return boolean
	 */
	public static boolean comprobarCaracteresPermitidos(final String valor) {
		return XssFilter.filtroXss(valor);
	}

	// ------------------------------------------------------------------------
	// Funciones auxiliares
	// ------------------------------------------------------------------------

	/**
	 * Método para Msg tipo valor campo no controlado de la clase UtilsFormulario.
	 *
	 * @param campo
	 *                  Parámetro campo
	 * @return el string
	 */
	private static String msgTipoValorCampoNoControlado(final String campo) {
		return "Tipo de valor campo " + campo + " no controlado";
	}

	/**
	 * Genera xml.
	 *
	 * @param pAccionPersonalizada
	 *                                 Accion personalizada
	 * @param pCampos
	 *                                 Campos
	 * @return XML
	 */
	private static byte[] generarXml(final String pAccionPersonalizada, final List<ValorCampo> pCampos) {
		final XmlOptions xmlOptions = new XmlOptions().setCharacterEncoding(Constantes.UTF8);
		final FORMULARIODocument fd = FORMULARIODocument.Factory.newInstance();
		final FORMULARIO formulario = fd.addNewFORMULARIO();
		formulario.setAccion(pAccionPersonalizada);
		for (final ValorCampo valorCampo : pCampos) {
			final CAMPO campoXml = formulario.addNewCAMPO();
			establecerValorXml(valorCampo, campoXml);
		}

		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream(pCampos.size() * ConstantesNumero.N60);
			fd.save(baos, xmlOptions);
			final byte[] xml = baos.toByteArray();
			return xml;
		} catch (final UnsupportedEncodingException uee) {
			throw new XmlFormularioException("Encoding UTF-8 no suportat", uee);
		} catch (final IOException e) {
			throw new XmlFormularioException("Error al generar xml", e);
		} finally {
			IOUtils.closeQuietly(baos);
		}
	}

	/**
	 * Establecer valor XML a partir valor campo.
	 *
	 * @param valorCampo
	 *                       valor campo
	 * @param campoXml
	 *                       valor xml
	 */
	private static void establecerValorXml(final ValorCampo valorCampo, final CAMPO campoXml) {
		switch (valorCampo.getTipo()) {
		case SIMPLE:
			establecerValorXmlSimple(valorCampo, campoXml);
			break;
		case INDEXADO:
			establecerValorXmlCompuesto(valorCampo, campoXml);
			break;
		case LISTA_INDEXADOS:
			establecerValorXmlMultivaluado(valorCampo, campoXml);
			break;
		case LISTA_ELEMENTOS:
			establecerValorXmlLista(valorCampo, campoXml);
			break;
		default:
			throw new TipoNoControladoException("Tipus de camp no permés: " + valorCampo.getTipo().name());
		}
	}

	/**
	 * Establecer valor XML a partir valor campo.
	 *
	 * @param valorCampo
	 *                       valor campo
	 * @param campoXml
	 *                       valor xml
	 */
	private static void establecerValorXmlLista(final ValorCampo valorCampo, final CAMPO campoXml) {
		final ValorCampoListaElementos vcl = (ValorCampoListaElementos) valorCampo;
		campoXml.setId(valorCampo.getId());
		campoXml.setTipo(CAMPO.Tipo.LISTA);
		if (vcl.getValor() != null) {
			for (final ValorElemento ve : vcl.getValor()) {
				final ELEMENTO elemento = campoXml.addNewELEMENTO();
				for (final ValorCampo vce : ve.getElemento()) {
					final CAMPO campoXmlElemento = elemento.addNewCAMPO();
					establecerValorXml(vce, campoXmlElemento);
				}
			}
		}

	}

	/**
	 * Establecer valor XML a partir valor campo.
	 *
	 * @param valorCampo
	 *                       valor campo
	 * @param campoXml
	 *                       valor xml
	 */
	private static void establecerValorXmlMultivaluado(final ValorCampo valorCampo, final CAMPO campoXml) {
		final ValorCampoListaIndexados vcl = (ValorCampoListaIndexados) valorCampo;
		campoXml.setId(valorCampo.getId());
		campoXml.setTipo(CAMPO.Tipo.MULTIVALUADO);
		if (vcl.getValor() != null) {
			for (final ValorIndexado vi : vcl.getValor()) {
				final VALOR valorListaIndexadoXml = campoXml.addNewVALOR();
				valorListaIndexadoXml.setCodigo(vi.getValor());
				valorListaIndexadoXml.setStringValue(vi.getDescripcion());
			}
		}
	}

	/**
	 * Establecer valor XML a partir valor campo.
	 *
	 * @param valorCampo
	 *                       valor campo
	 * @param campoXml
	 *                       valor xml
	 */
	private static void establecerValorXmlCompuesto(final ValorCampo valorCampo, final CAMPO campoXml) {
		final ValorCampoIndexado vci = (ValorCampoIndexado) valorCampo;
		campoXml.setId(valorCampo.getId());
		campoXml.setTipo(CAMPO.Tipo.COMPUESTO);
		if (vci.getValor() != null) {
			final VALOR valorIndexadoXml = campoXml.addNewVALOR();
			valorIndexadoXml.setCodigo(vci.getValor().getValor());
			valorIndexadoXml.setStringValue(vci.getValor().getDescripcion());
		}
	}

	/**
	 * Crea valor simple
	 *
	 * @param valorCampo
	 *                       Valor campo
	 * @param campoXml
	 *                       Campo xml
	 */
	private static void establecerValorXmlSimple(final ValorCampo valorCampo, final CAMPO campoXml) {
		final ValorCampoSimple vcs = (ValorCampoSimple) valorCampo;
		campoXml.setId(valorCampo.getId());
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
	}

}
