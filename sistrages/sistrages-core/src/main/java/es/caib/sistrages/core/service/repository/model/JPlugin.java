package es.caib.sistrages.core.service.repository.model;

import java.util.List;

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

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.util.UtilJSON;

/**
 * JPlugin
 */
@Entity
@Table(name = "STG_PLUGIN")
public class JPlugin implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_PLUGIN_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_PLUGIN_SEQ", sequenceName = "STG_PLUGIN_SEQ")
	@Column(name = "PLG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLG_CODENT", nullable = false)
	private JEntidad entidad;

	public JPlugin() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public String getAmbito() {
		return this.ambito;
	}

	public void setAmbito(final String plgAmbito) {
		this.ambito = plgAmbito;
	}

	public JEntidad getEntidad() {
		return entidad;
	}

	public void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
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

	public Plugin toModel() {
		final Plugin plugin = new Plugin();
		plugin.setId(this.getCodigo());
		plugin.setAmbito(TypeAmbito.fromString(this.getAmbito()));
		plugin.setClassname(this.claseImplementadora);
		plugin.setDescripcion(this.descripcion);
		plugin.setInstancia(this.idInstancia);
		plugin.setTipo(TypePlugin.fromString(this.tipo));
		plugin.setPropiedades((List<Propiedad>) UtilJSON.fromListJSON(propiedades, Propiedad.class));
		return plugin;
	}

	public static JPlugin fromModel(final Plugin plugin) {
		final JPlugin jPlugin = new JPlugin();
		jPlugin.setCodigo(plugin.getId());
		jPlugin.setAmbito(plugin.getAmbito().toString());
		jPlugin.setClaseImplementadora(plugin.getClassname());
		jPlugin.setDescripcion(plugin.getDescripcion());
		jPlugin.setIdInstancia(plugin.getInstancia());
		jPlugin.setPropiedades(UtilJSON.toJSON(plugin.getPropiedades()));
		jPlugin.setTipo(plugin.getTipo().toString());
		return jPlugin;
	}

}
