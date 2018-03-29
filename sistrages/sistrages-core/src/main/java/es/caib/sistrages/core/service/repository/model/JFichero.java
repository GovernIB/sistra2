package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.Fichero;

/**
 * JFichero
 */
@Entity
@Table(name = "STG_FICHER")
public class JFichero implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FICHER_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FICHER_SEQ", sequenceName = "STG_FICHER_SEQ")
	@Column(name = "FIC_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@Column(name = "FIC_NOMBRE", nullable = false, length = 500)
	private String nombre;

	@Column(name = "FIC_PUBLIC", nullable = false)
	private boolean publico;

	public JFichero() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(final boolean publico) {
		this.publico = publico;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String ficNombre) {
		this.nombre = ficNombre;
	}

	public Fichero toModel() {
		final Fichero f = new Fichero();
		f.setId(this.codigo);
		f.setNombre(this.nombre);
		f.setPublico(publico);
		return f;
	}

	public static JFichero fromModel(final Fichero fichero) {
		final JFichero jFichero = new JFichero();
		jFichero.setCodigo(fichero.getId());
		jFichero.setNombre(fichero.getNombre());
		jFichero.setPublico(true);
		return jFichero;
	}

}
