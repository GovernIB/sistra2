package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JFichero
 */
@Entity
@Table(name = "STG_FICHER")
public class JFichero implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FICHER_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FICHER_SEQ", sequenceName = "STG_FICHER_SEQ")
	@Column(name = "FIC_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@Column(name = "FIC_NOMBRE", nullable = false, length = 500)
	private String nombre;

	public JFichero() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String ficNombre) {
		this.nombre = ficNombre;
	}

}
