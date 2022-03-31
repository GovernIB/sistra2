package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

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

import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCaptcha;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JCampoFormularioCaptcha
 */
@Entity
@Table(name = "STG_FORCPT")
public class JCampoFormularioCaptcha implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FCP_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "FCP_CODIGO")
	private JCampoFormulario campoFormulario;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "FCP_TEXTO")
	private JLiteral texto;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "FCP_CODIGO")
	private JElementoFormulario elementoFormulario;

	public JCampoFormularioCaptcha() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}


	/**
	 * @return the texto
	 */
	public JLiteral getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(JLiteral texto) {
		this.texto = texto;
	}

	/**
	 * @return the campoFormulario
	 */
	public JCampoFormulario getCampoFormulario() {
		return campoFormulario;
	}

	/**
	 * @param campoFormulario the campoFormulario to set
	 */
	public void setCampoFormulario(JCampoFormulario campoFormulario) {
		this.campoFormulario = campoFormulario;
	}

	/**
	 * @return the elementoFormulario
	 */
	public JElementoFormulario getElementoFormulario() {
		return elementoFormulario;
	}

	/**
	 * @param elementoFormulario the elementoFormulario to set
	 */
	public void setElementoFormulario(JElementoFormulario elementoFormulario) {
		this.elementoFormulario = elementoFormulario;
	}



	public ComponenteFormularioCampoCaptcha toModel() {
		ComponenteFormularioCampoCaptcha campoTexto = null;

		if (elementoFormulario != null) {
			campoTexto = (ComponenteFormularioCampoCaptcha) elementoFormulario.toModel(ComponenteFormularioCampoCaptcha.class);
			if (campoTexto != null) {
				campoTexto.setTexto(campoTexto.getTexto());
			}

		}

		return campoTexto;
	}

	public static JCampoFormularioCaptcha createDefault(final int pOrden, final JLineaFormulario pJLinea) {
		final JCampoFormularioCaptcha jModel = new JCampoFormularioCaptcha();
		jModel.setElementoFormulario(JElementoFormulario.createDefault(TypeObjetoFormulario.CAPTCHA, pOrden, pJLinea));
		return jModel;
	}

	public static JCampoFormularioCaptcha clonar(final JCampoFormularioCaptcha campoFormularioTexto,
			final JElementoFormulario jelemento) {
		JCampoFormularioCaptcha jcampoTexto = null;
		if (campoFormularioTexto != null) {
			jcampoTexto = new JCampoFormularioCaptcha();
			jcampoTexto.setElementoFormulario(jelemento);
			jcampoTexto.setTexto(campoFormularioTexto.getTexto());
		}
		return jcampoTexto;
	}
}
