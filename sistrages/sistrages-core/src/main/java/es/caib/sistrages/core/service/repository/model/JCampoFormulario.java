package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JCampoFormulario
 */
@Entity
@Table(name = "STG_FORCAM")
public class JCampoFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FCA_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "FCA_CODIGO")
	private JElementoFormulario elementoFormulario;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "FCA_SCRAUT")
	private JScript scriptAutocalculado;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "FCA_SCRSLE")
	private JScript scriptSoloLectura;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "FCA_SCRVAL")
	private JScript scriptValidaciones;

	@Column(name = "FCA_OBLIGA", nullable = false, precision = 1, scale = 0)
	private boolean obligatorio;

	@Column(name = "FCA_LECTUR", nullable = false, precision = 1, scale = 0)
	private boolean soloLectura;

	@Column(name = "FCA_NOMODI", nullable = false, precision = 1, scale = 0)
	private boolean noModificable;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "campoFormulario", cascade = CascadeType.ALL)
	private JCampoFormularioCasillaVerificacion campoFormularioCasillaVerificacion;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "campoFormulario", cascade = CascadeType.ALL)
	private JCampoFormularioIndexado campoFormularioIndexado;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "campoFormulario", cascade = CascadeType.ALL)
	private JCampoFormularioTexto campoFormularioTexto;

	public JCampoFormulario() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JElementoFormulario getElementoFormulario() {
		return this.elementoFormulario;
	}

	public void setElementoFormulario(final JElementoFormulario elementoFormulario) {
		this.elementoFormulario = elementoFormulario;
	}

	public JScript getScriptAutocalculado() {
		return this.scriptAutocalculado;
	}

	public void setScriptAutocalculado(final JScript scriptAutocalculado) {
		this.scriptAutocalculado = scriptAutocalculado;
	}

	public JScript getScriptSoloLectura() {
		return this.scriptSoloLectura;
	}

	public void setScriptSoloLectura(final JScript scriptSoloLectura) {
		this.scriptSoloLectura = scriptSoloLectura;
	}

	public JScript getScriptValidaciones() {
		return this.scriptValidaciones;
	}

	public void setScriptValidaciones(final JScript scriptValidaciones) {
		this.scriptValidaciones = scriptValidaciones;
	}

	public boolean isObligatorio() {
		return this.obligatorio;
	}

	public void setObligatorio(final boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public boolean isSoloLectura() {
		return this.soloLectura;
	}

	public void setSoloLectura(final boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public boolean isNoModificable() {
		return this.noModificable;
	}

	public void setNoModificable(final boolean noModificable) {
		this.noModificable = noModificable;
	}

	public JCampoFormularioCasillaVerificacion getCampoFormularioCasillaVerificacion() {
		return this.campoFormularioCasillaVerificacion;
	}

	public void setCampoFormularioCasillaVerificacion(
			final JCampoFormularioCasillaVerificacion campoFormularioCasillaVerificacion) {
		this.campoFormularioCasillaVerificacion = campoFormularioCasillaVerificacion;
	}

	public JCampoFormularioIndexado getCampoFormularioIndexado() {
		return this.campoFormularioIndexado;
	}

	public void setCampoFormularioIndexado(final JCampoFormularioIndexado campoFormularioIndexado) {
		this.campoFormularioIndexado = campoFormularioIndexado;
	}

	public JCampoFormularioTexto getCampoFormularioTexto() {
		return this.campoFormularioTexto;
	}

	public void setCampoFormularioTexto(final JCampoFormularioTexto campoFormularioTexto) {
		this.campoFormularioTexto = campoFormularioTexto;
	}

	public <T> ComponenteFormularioCampo toModel(final Class<T> model) {
		ComponenteFormularioCampo newModel = null;

		if (elementoFormulario != null) {
			newModel = (ComponenteFormularioCampo) elementoFormulario.toModel(model);
			if (newModel != null) {
				newModel.setObligatorio(obligatorio);
				newModel.setSoloLectura(soloLectura);
				newModel.setNoModificable(noModificable);

				if (scriptAutocalculado != null) {
					newModel.setScriptAutorrellenable(scriptAutocalculado.toModel());
				}

				if (scriptSoloLectura != null) {
					newModel.setScriptSoloLectura(scriptSoloLectura.toModel());
				}

				if (scriptValidaciones != null) {
					newModel.setScriptValidacion(scriptValidaciones.toModel());
				}
			}
		}

		return newModel;

	}

	public static JCampoFormulario createDefault(final TypeObjetoFormulario pTipoObjeto, final int pOrden,
			final JLineaFormulario pJLinea) {
		final JCampoFormulario jModel = new JCampoFormulario();
		jModel.setObligatorio(false);
		jModel.setSoloLectura(false);
		jModel.setNoModificable(false);
		jModel.setElementoFormulario(JElementoFormulario.createDefault(pTipoObjeto, pOrden, pJLinea));
		return jModel;
	}

	public static JCampoFormulario clonar(final JCampoFormulario campoFormulario, final JElementoFormulario jelemento,
			final boolean cambioArea) {
		JCampoFormulario jcampo = null;
		if (campoFormulario != null) {
			jcampo = new JCampoFormulario();
			jcampo.setElementoFormulario(jelemento);
			jcampo.setScriptAutocalculado(JScript.clonar(campoFormulario.getScriptAutocalculado()));
			jcampo.setScriptSoloLectura(JScript.clonar(campoFormulario.getScriptSoloLectura()));
			jcampo.setScriptValidaciones(JScript.clonar(campoFormulario.getScriptValidaciones()));
			jcampo.setObligatorio(campoFormulario.isObligatorio());
			jcampo.setSoloLectura(campoFormulario.isSoloLectura());
			jcampo.setNoModificable(campoFormulario.isNoModificable());
			jcampo.setCampoFormularioCasillaVerificacion(JCampoFormularioCasillaVerificacion
					.clonar(campoFormulario.getCampoFormularioCasillaVerificacion(), jcampo));
			jcampo.setCampoFormularioIndexado(
					JCampoFormularioIndexado.clonar(campoFormulario.getCampoFormularioIndexado(), jcampo, cambioArea));
			jcampo.setCampoFormularioTexto(
					JCampoFormularioTexto.clonar(campoFormulario.getCampoFormularioTexto(), jcampo));

		}
		return jcampo;
	}

}
