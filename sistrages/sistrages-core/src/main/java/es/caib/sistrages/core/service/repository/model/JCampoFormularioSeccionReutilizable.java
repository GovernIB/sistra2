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

import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JCampoFormularioSeccionReutilizable
 */
@Entity
@Table(name = "STG_FORSRU")
public class JCampoFormularioSeccionReutilizable implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FSE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "FSE_CODIGO")
	private JCampoFormulario campoFormulario;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "FSE_CODSRU")
	private JSeccionReutilizable seccionReutilizable;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "FSE_CODIGO")
	private JElementoFormulario elementoFormulario;

	@Column(name = "FSE_LETRA", length = 2)
	private String letra;

	public JCampoFormularioSeccionReutilizable() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}


	/**
	 * @return the seccionReutilizable
	 */
	public JSeccionReutilizable getSeccionReutilizable() {
		return seccionReutilizable;
	}

	/**
	 * @param seccionReutilizable the seccionReutilizable to set
	 */
	public void setSeccionReutilizable(JSeccionReutilizable seccionReutilizable) {
		this.seccionReutilizable = seccionReutilizable;
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



	/**
	 * @return the letra
	 */
	public String getLetra() {
		return letra;
	}

	/**
	 * @param letra the letra to set
	 */
	public void setLetra(String letra) {
		this.letra = letra;
	}

	public ComponenteFormularioCampoSeccionReutilizable toModel() {
		ComponenteFormularioCampoSeccionReutilizable campoTexto = null;

		if (elementoFormulario != null) {
			campoTexto = (ComponenteFormularioCampoSeccionReutilizable) elementoFormulario.toModel(ComponenteFormularioCampoSeccionReutilizable.class);
			campoTexto.setIdSeccionReutilizable(this.getSeccionReutilizable().getCodigo());
			campoTexto.setLetra(this.getLetra());
			campoTexto.setIdentificadorSeccionReutilizable(this.getSeccionReutilizable().getIdentificador());
			if (this.elementoFormulario.getTexto() != null) {
				campoTexto.setTexto(this.elementoFormulario.getTexto().toModel());
			}
		}

		return campoTexto;
	}

	public static JCampoFormularioSeccionReutilizable createDefault(final int pOrden, final JLineaFormulario pJLinea, final boolean isTipoSeccion, final String identificadorSeccion) {
		final JCampoFormularioSeccionReutilizable jModel = new JCampoFormularioSeccionReutilizable();
		jModel.setElementoFormulario(JElementoFormulario.createDefault(TypeObjetoFormulario.SECCION_REUTILIZABLE, pOrden, pJLinea, isTipoSeccion, identificadorSeccion));
		//jModel.setCampoFormulario(JCampoFormulario.createDefault(TypeObjetoFormulario.CAPTCHA, pOrden, pJLinea));
		return jModel;
	}

	public static JCampoFormularioSeccionReutilizable clonar(final JCampoFormularioSeccionReutilizable campoFormularioTexto,
			final JElementoFormulario jelemento) {
		JCampoFormularioSeccionReutilizable jcampoTexto = null;
		if (campoFormularioTexto != null) {
			jcampoTexto = new JCampoFormularioSeccionReutilizable();
			jcampoTexto.setElementoFormulario(jelemento);
			jcampoTexto.setSeccionReutilizable(campoFormularioTexto.getSeccionReutilizable());
		}
		return jcampoTexto;
	}
}
