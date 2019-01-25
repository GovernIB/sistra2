package es.caib.sistramit.core.service.component.formulario.interno.utils;

import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteCheckbox;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelector;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoCP;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoEmail;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoExpReg;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoFecha;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoId;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoNormal;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoNumero;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoOculto;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoPassword;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoTelefono;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoVerificacion;
import es.caib.sistramit.core.api.model.formulario.ValoresCampoVerificacion;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.api.model.formulario.types.TypeSeparador;
import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;
import es.caib.sistramit.core.service.component.formulario.UtilsFormulario;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResEstadoCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Clase helper que contiene la logica de obtener la configuracion de los campos
 * de formulario.
 *
 * @author Indra
 *
 */
@Component("configuracionFormularioHelper")
public final class ConfiguracionFormularioHelperImpl implements ConfiguracionFormularioHelper {

	@Override
	public ConfiguracionCampo obtenerConfiguracionCampo(RComponente pCampoDef) {
		// Creamos configuracion campo segun tipo y establecemos props
		// particulares
		ConfiguracionCampo confCampo = null;
		final TypeCampo tipoCampo = TypeCampo.fromString(pCampoDef.getTipo());
		switch (tipoCampo) {
		case TEXTO:
			confCampo = obtenerConfiguracionCampoTexto((RComponenteTextbox) pCampoDef);
			break;
		case VERIFICACION:
			confCampo = obtenerConfiguracionCampoVerificacion((RComponenteCheckbox) pCampoDef);
			break;
		case SELECTOR:
			confCampo = obtenerConfiguracionCampoSelector((RComponenteSelector) pCampoDef);
			break;
		default:
			throw new TipoNoControladoException("Tipo de campo no soportado: " + tipoCampo.name());
		}
		return confCampo;
	}

	@Override
	public ResEstadoCampo evaluarEstadoCampo(DatosSesionFormularioInterno pDatosSesion, RComponente campoDef) {
		// TODO PENDIENTE
		throw new RuntimeException("Pendiente implementar");
	}

	@Override
	public byte[] obtenerPlantillaPdfVisualizacion(DatosSesionFormularioInterno pDatosSesion) {
		// TODO PENDIENTE
		throw new RuntimeException("Pendiente implementar");
	}

	// -----------------------------------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -----------------------------------------------------------------------------------------------

	/**
	 * Obtiene configuracion campo selector.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return configuracion campo selector.
	 */
	private ConfiguracionCampoSelector obtenerConfiguracionCampoSelector(final RComponenteSelector pCampoDef) {
		final TypeSelector tipoSelector = TypeSelector.fromString(pCampoDef.getTipoSelector());
		final ConfiguracionCampoSelector confCampoSelector = new ConfiguracionCampoSelector();
		confCampoSelector.setContenido(tipoSelector);
		return confCampoSelector;
	}

	/**
	 * Obtiene configuracion campo verificacion.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return configuracion campo verificacion
	 */
	private ConfiguracionCampoVerificacion obtenerConfiguracionCampoVerificacion(final RComponenteCheckbox pCampoDef) {
		final ConfiguracionCampoVerificacion confCampoVerif = new ConfiguracionCampoVerificacion();
		final ValoresCampoVerificacion valoresVerif = new ValoresCampoVerificacion();
		valoresVerif.setChecked(pCampoDef.getValorChecked());
		valoresVerif.setNoChecked(pCampoDef.getValorNoChecked());
		confCampoVerif.setValores(valoresVerif);
		return confCampoVerif;
	}

	/**
	 * Obtiene configuración de campo de tipo texto.
	 *
	 * @param pCampoDef
	 *            Definición campo
	 * @return configuración
	 */
	private ConfiguracionCampo obtenerConfiguracionCampoTexto(RComponenteTextbox pCampoDef) {

		ConfiguracionCampo confCampo = null;

		// Creamos configuración específica según el tipo de campo de texto
		final TypeTexto typeTexto = TypeTexto.fromString(pCampoDef.getTipoTexto());
		if (typeTexto == null) {
			throw new ErrorConfiguracionException(
					"Campo de texto de tipo " + pCampoDef.getTipoTexto() + " no soportado");
		}

		switch (typeTexto) {
		case NORMAL: // Normal
			confCampo = obtenerConfCampoTextoNormal(pCampoDef);
			break;
		case NUMERO: // Numero
			confCampo = obtenerConfCampoTextoNumero(pCampoDef);
			break;
		case CODIGO_POSTAL: // Codigo postal
			final ConfiguracionCampoTextoCP confCampoCP = new ConfiguracionCampoTextoCP();
			confCampo = confCampoCP;
			break;
		case EMAIL: // Email
			final ConfiguracionCampoTextoEmail confCampoEmail = new ConfiguracionCampoTextoEmail();
			confCampo = confCampoEmail;
			break;
		case FECHA: // Fecha
			final ConfiguracionCampoTextoFecha confCampoFecha = new ConfiguracionCampoTextoFecha();
			confCampo = confCampoFecha;
			break;
		case IDENTIFICADOR: // Identificador
			confCampo = obtenerConfCampoTextoIdentificador(pCampoDef);
			break;
		case TELEFONO: // Telefono
			confCampo = obtenerConfCampoTextoTelefono(pCampoDef);
			break;
		case EXPRESION_REGULAR: // Expresion regular
			confCampo = obtenerConfCampoTextoExpreg(pCampoDef);
			break;
		case OCULTO: // Oculto
			confCampo = new ConfiguracionCampoTextoOculto();
			break;
		case PASSWORD: // Password
			confCampo = new ConfiguracionCampoTextoPassword();
			break;
		default:
			throw new TipoNoControladoException("Tipo de campo texto no controlado: " + pCampoDef.getTipoTexto());
		}

		// Establecemos propiedades generales
		establecerPropiedadesGenerales(pCampoDef, confCampo);

		return confCampo;
	}

	/**
	 * Configuracion campo texto exp regular.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoExpreg(final RComponenteTextbox pCampoDef) {
		final ConfiguracionCampoTextoExpReg confCampoExp = new ConfiguracionCampoTextoExpReg();
		confCampoExp.getOpciones().setRegexp(pCampoDef.getTextoExpRegular().getExpresionRegular());
		return confCampoExp;
	}

	/**
	 * Configuracion campo texto telefono.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoTelefono(final RComponenteTextbox pCampoDef) {
		final ConfiguracionCampoTextoTelefono confCampoTel = new ConfiguracionCampoTextoTelefono();
		confCampoTel.getOpciones().setFijo(TypeSiNo.fromBoolean(pCampoDef.getTextoTelefono().isFijo()));
		confCampoTel.getOpciones().setMovil(TypeSiNo.fromBoolean(pCampoDef.getTextoTelefono().isMovil()));
		return confCampoTel;
	}

	/**
	 * Configuracion campo texto normal.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoNormal(RComponenteTextbox pCampoDef) {
		ConfiguracionCampo confCampo;
		final ConfiguracionCampoTextoNormal confCampoNormal = new ConfiguracionCampoTextoNormal();
		confCampo = confCampoNormal;
		confCampoNormal.getOpciones().setLineas(pCampoDef.getTextoNormal().getLineas());
		confCampoNormal.getOpciones().setTamanyo(pCampoDef.getTextoNormal().getTamanyoMax());
		return confCampo;
	}

	/**
	 * Configuracion campo texto numero.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoNumero(final RComponenteTextbox pCampoDef) {

		final ConfiguracionCampoTextoNumero confCampoNumero = new ConfiguracionCampoTextoNumero();

		final TypeSeparador typeSeparador = TypeSeparador.fromString(pCampoDef.getTextoNumero().getFormatoNumero());
		if (typeSeparador == null) {
			throw new ErrorConfiguracionException(
					"Formato separador número " + pCampoDef.getTextoNumero().getFormatoNumero() + " no soportado");
		}
		confCampoNumero.getOpciones().setSeparador(typeSeparador);

		confCampoNumero.getOpciones().setEnteros(pCampoDef.getTextoNumero().getPrecisionEntera());
		confCampoNumero.getOpciones().setDecimales(pCampoDef.getTextoNumero().getPrecisionDecimal());
		if (pCampoDef.getTextoNumero().isNegativos()) {
			confCampoNumero.getOpciones().setNegativo(TypeSiNo.SI);
		}

		if (pCampoDef.getTextoNumero().getRangoDesde() >= 0 && pCampoDef.getTextoNumero().getRangoHasta() > 0) {
			confCampoNumero.getOpciones().setRangoMin(pCampoDef.getTextoNumero().getRangoDesde());
			confCampoNumero.getOpciones().setRangoMax(pCampoDef.getTextoNumero().getRangoHasta());
		}

		return confCampoNumero;
	}

	/**
	 * Configuracion campo texto identificador.
	 *
	 * @param pCampoDef
	 *            Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoIdentificador(final RComponenteTextbox pCampoDef) {
		final ConfiguracionCampoTextoId confCampoId = new ConfiguracionCampoTextoId();
		confCampoId.getOpciones().setNif(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isNif()));
		confCampoId.getOpciones().setNie(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isNie()));
		confCampoId.getOpciones().setCif(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isCif()));
		confCampoId.getOpciones().setNss(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isNss()));
		return confCampoId;
	}

	/**
	 * Establece propiedades generales campo.
	 *
	 * @param pCampoDef
	 *            Definición campo
	 * @param confCampo
	 *            Configuración campo
	 */
	private void establecerPropiedadesGenerales(RComponente pCampoDef, ConfiguracionCampo confCampo) {
		// Establecemos props generales
		confCampo.setAyuda(pCampoDef.getAyuda());
		final RPropiedadesCampo propsGenerales = UtilsFormulario.obtenerPropiedadesCampo(pCampoDef);
		confCampo.setId(pCampoDef.getIdentificador());
		if (propsGenerales.isObligatorio()) {
			confCampo.setObligatorio(TypeSiNo.SI);
		} else {
			confCampo.setObligatorio(TypeSiNo.NO);
		}
		if (propsGenerales.isSoloLectura()) {
			confCampo.setSoloLectura(TypeSiNo.SI);
		} else {
			confCampo.setSoloLectura(TypeSiNo.NO);
		}
		if (propsGenerales.isNoModificable()) {
			confCampo.setModificable(TypeSiNo.NO);
		} else {
			confCampo.setModificable(TypeSiNo.SI);
		}
		confCampo.setEvaluar(TypeSiNo.fromBoolean(UtilsSTG.existeScript(propsGenerales.getScriptValidacion())));
	}

}
