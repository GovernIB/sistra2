package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JIdioma
 */
@Entity
@Table(name = "STG_IDIOMA")
public class JIdioma implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDI_IDENTI", unique = true, nullable = false, length = 2)
	private String identificador;

	public JIdioma() {
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String idiIdenti) {
		this.identificador = idiIdenti;
	}

}
