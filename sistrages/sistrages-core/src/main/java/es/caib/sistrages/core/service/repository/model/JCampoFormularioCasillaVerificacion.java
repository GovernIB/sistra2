package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JCampoFormularioCasillaVerificacion
 */
@Entity
@Table(name = "STG_FORCHK")
public class JCampoFormularioCasillaVerificacion implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CCK_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "CCK_CODIGO")
	private JCampoFormulario campoFormulario;

	@Column(name = "CCK_VALCHK", nullable = false, length = 100)
	private String valorChecked;

	@Column(name = "CCK_VALNCH", nullable = false, length = 100)
	private String valorNoChecked;

	public JCampoFormularioCasillaVerificacion() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JCampoFormulario getCampoFormulario() {
		return this.campoFormulario;
	}

	public void setCampoFormulario(final JCampoFormulario campoFormulario) {
		this.campoFormulario = campoFormulario;
	}

	public String getValorChecked() {
		return this.valorChecked;
	}

	public void setValorChecked(final String valorChecked) {
		this.valorChecked = valorChecked;
	}

	public String getValorNoChecked() {
		return this.valorNoChecked;
	}

	public void setValorNoChecked(final String valorNoChecked) {
		this.valorNoChecked = valorNoChecked;
	}

	public ComponenteFormularioCampoCheckbox toModel() {
		ComponenteFormularioCampoCheckbox campoCheckbox = null;

		if (campoFormulario != null) {
			campoCheckbox = (ComponenteFormularioCampoCheckbox) campoFormulario
					.toModel(ComponenteFormularioCampoCheckbox.class);
			if (campoCheckbox != null) {
				campoCheckbox.setValorChecked(valorChecked);
				campoCheckbox.setValorNoChecked(valorNoChecked);
			}
		}

		return campoCheckbox;
	}

	public static JCampoFormularioCasillaVerificacion createDefault(final int pOrden, final JLineaFormulario pJLinea) {
		final JCampoFormularioCasillaVerificacion jModel = new JCampoFormularioCasillaVerificacion();
		jModel.setValorChecked("S");
		jModel.setValorNoChecked("N");
		jModel.setCampoFormulario(JCampoFormulario.createDefault(TypeObjetoFormulario.CHECKBOX, pOrden, pJLinea));
		return jModel;
	}

	public static JCampoFormularioCasillaVerificacion clonar(
			final JCampoFormularioCasillaVerificacion campoFormularioCasillaVerificacion,
			final JCampoFormulario jcampo) {
		JCampoFormularioCasillaVerificacion jcampoVerificacion = null;
		if (campoFormularioCasillaVerificacion != null) {
			jcampoVerificacion = new JCampoFormularioCasillaVerificacion();
			jcampoVerificacion.setCampoFormulario(jcampo);
			jcampoVerificacion.setValorChecked(campoFormularioCasillaVerificacion.getValorChecked());
			jcampoVerificacion.setValorNoChecked(campoFormularioCasillaVerificacion.getValorNoChecked());
		}
		return jcampoVerificacion;
	}

}
