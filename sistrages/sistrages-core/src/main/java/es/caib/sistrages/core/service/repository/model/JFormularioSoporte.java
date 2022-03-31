package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.types.TypeAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeFormularioSoporte;

/**
 * JFormularioSoporte
 */
@Entity
@Table(name = "STG_FORSOP")
public class JFormularioSoporte implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORSOP_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORSOP_SEQ", sequenceName = "STG_FORSOP_SEQ")
	@Column(name = "FSO_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FSO_CODENT", nullable = false)
	private JEntidad entidad;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.ALL })
	@JoinColumn(name = "FSO_TIPINC", nullable = false)
	private JLiteral TipoIncidencia;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.ALL })
	@JoinColumn(name = "FSO_DSCTIP", nullable = false)
	private JLiteral descripcion;

	@Column(name = "FSO_TIPDST", nullable = false, length = 5)
	private String tipoDestinatario;

	@Column(name = "FSO_LSTEMA", length = 4000)
	private String listaEmails;

	public JFormularioSoporte() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JEntidad getEntidad() {
		return this.entidad;
	}

	public void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
	}

	public JLiteral getTipoIncidencia() {
		return this.TipoIncidencia;
	}

	public void setTipoIncidencia(final JLiteral literalTipoIncidencia) {
		this.TipoIncidencia = literalTipoIncidencia;
	}

	public JLiteral getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final JLiteral descripcionTipoIncidencia) {
		this.descripcion = descripcionTipoIncidencia;
	}

	public String getTipoDestinatario() {
		return this.tipoDestinatario;
	}

	public void setTipoDestinatario(final String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public String getListaEmails() {
		return this.listaEmails;
	}

	public void setListaEmails(final String listaEmails) {
		this.listaEmails = listaEmails;
	}

	public FormularioSoporte toModel() {
		final FormularioSoporte fst = new FormularioSoporte();
		fst.setCodigo(codigo);
		fst.setTipoIncidencia(TipoIncidencia.toModel());
		fst.setDescripcion(descripcion.toModel());
		final List<TypeFormularioSoporte> listDest = new ArrayList<>();
		if (this.getTipoDestinatario() != null && !this.getTipoDestinatario().isEmpty()) {
			final String[] tipos = this.getTipoDestinatario().split(";");
			for (final String tipo : tipos) {
				final TypeFormularioSoporte typeForm = TypeFormularioSoporte.fromString(tipo);
				if (tipo != null && !listDest.contains(typeForm)) {
					listDest.add(typeForm);
				}
			}
		}
		fst.setTipoDestinatario(listDest);
		fst.setListaEmails(listaEmails);
		return fst;
	}

	public static JFormularioSoporte fromModel(final FormularioSoporte model) {
		JFormularioSoporte jModel = null;
		if (model != null) {
			jModel = new JFormularioSoporte();
			jModel.setCodigo(model.getCodigo());
			jModel.setTipoIncidencia(JLiteral.fromModel(model.getTipoIncidencia()));
			jModel.setDescripcion(JLiteral.fromModel(model.getDescripcion()));
			if (model.getTipoDestinatario() != null) {
				final StringBuilder tipoDest = new StringBuilder();
				for (final TypeFormularioSoporte tipoForm : model.getTipoDestinatario()) {
					if (tipoDest != null) {
						tipoDest.append(tipoForm.toString());
						tipoDest.append(";");
					}
				}

				String tipoDestString = tipoDest.toString();
				if (tipoDestString.endsWith(";")) {
					tipoDestString = tipoDestString.substring(0, tipoDestString.length() - 1);
				}
				jModel.setTipoDestinatario(tipoDestString);
			} else {
				jModel.setTipoDestinatario(null);
			}
			jModel.setListaEmails(model.getListaEmails());
		}
		return jModel;
	}

}
