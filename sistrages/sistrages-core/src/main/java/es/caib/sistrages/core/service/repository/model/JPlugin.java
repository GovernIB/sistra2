package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JPlugin
 */
@Entity
@Table(name = "STG_PLUGIN")
public class JPlugin implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_PLUGIN_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_PLUGIN_SEQ", sequenceName = "STG_PLUGIN_SEQ")
	@Column(name = "PLG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@Column(name = "PLG_AMBITO", nullable = false, length = 1)
	private String ambito;

	@Column(name = "PLG_TIPO", nullable = false, length = 3)
	private String tipo;

	@Column(name = "PLG_DESCR", nullable = false)
	private String descripcion;

	@Column(name = "PLG_CLASS", nullable = false, length = 500)
	private String claseImplementadora;

	@Column(name = "PLG_PROPS", length = 4000)
	private String propiedades;

	@Column(name = "PLG_IDINST", length = 20)
	private String idInstancia;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_PLGENT", joinColumns = {
			@JoinColumn(name = "PLE_CODPLG", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "PLE_CODENT", nullable = false, updatable = false) })
	private Set<JEntidades> entidades = new HashSet<JEntidades>(0);

	public JPlugin() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long plgCodigo) {
		this.codigo = plgCodigo;
	}

	public String getAmbito() {
		return this.ambito;
	}

	public void setAmbito(final String plgAmbito) {
		this.ambito = plgAmbito;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClaseImplementadora() {
		return this.claseImplementadora;
	}

	public void setClaseImplementadora(final String claseImplementadora) {
		this.claseImplementadora = claseImplementadora;
	}

	public String getPropiedades() {
		return this.propiedades;
	}

	public void setPropiedades(final String propiedades) {
		this.propiedades = propiedades;
	}

	public String getIdInstancia() {
		return this.idInstancia;
	}

	public void setIdInstancia(final String idInstancia) {
		this.idInstancia = idInstancia;
	}

	public Set<JEntidades> getEntidades() {
		return this.entidades;
	}

	public void setEntidades(final Set<JEntidades> entidades) {
		this.entidades = entidades;
	}

}
