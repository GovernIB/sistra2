package es.caib.sistrages.core.service.repository.model;

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
import javax.persistence.UniqueConstraint;

import org.apache.commons.codec.binary.Base64;

import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * JDominio
 */
@Entity
@Table(name = "STG_ENVREM", uniqueConstraints = @UniqueConstraint(columnNames = "EVR_IDENTI"))
public class JEnvioRemoto implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_ENVREM_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_ENVREM_SEQ", sequenceName = "STG_ENVREM_SEQ")
	@Column(name = "EVR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@Column(name = "EVR_AMBITO", nullable = false, length = 1)
	private String ambito;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ER_CODENT", nullable = true)
	private JEntidad entidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVR_CODARE", nullable = true)
	private JArea area;

	@Column(name = "EVR_IDENTI", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "EVR_DESCR", nullable = false)
	private String descripcion;

	@Column(name = "EVR_URL", length = 500)
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVR_CODCAU")
	private JConfiguracionAutenticacion configuracionAutenticacion;

	@Column(name = "EVR_TIMEOUT", precision = 4)
	private Long timeout;

	public JEnvioRemoto() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the entidad
	 */
	public JEntidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(JEntidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the area
	 */
	public JArea getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(JArea area) {
		this.area = area;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the configuracionAutenticacion
	 */
	public JConfiguracionAutenticacion getConfiguracionAutenticacion() {
		return configuracionAutenticacion;
	}

	/**
	 * @param configuracionAutenticacion the configuracionAutenticacion to set
	 */
	public void setConfiguracionAutenticacion(JConfiguracionAutenticacion configuracionAutenticacion) {
		this.configuracionAutenticacion = configuracionAutenticacion;
	}

	/**
	 * @return the timeout
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public EnvioRemoto toModel() {
		final EnvioRemoto envio = new EnvioRemoto();
		envio.setCodigo(this.codigo);
		envio.setIdentificador(this.identificador);
		if (this.ambito.equals(TypeAmbito.AREA.toString())) {
			JArea jarea = this.area;
			String idArea = jarea.getIdentificador();
			String idEntidad = jarea.getEntidad().getIdentificador();
			envio.setIdArea(idArea);
			envio.setIdentificadorCompuesto(idEntidad + ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO
					+ idArea + ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + this.identificador);
			envio.setAmbito(TypeAmbito.AREA);
		} else if (this.ambito.equals(TypeAmbito.ENTIDAD.toString())) {
			String idEntidad = this.entidad.getIdentificador();
			envio.setIdentificadorCompuesto(
					idEntidad + ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + this.identificador);
			envio.setAmbito(TypeAmbito.ENTIDAD);
		}
		envio.setDescripcion(this.descripcion);
		envio.setTimeout(this.timeout);

		if (this.getUrl() != null) {
			envio.setUrl(this.getUrl());
		}
		if (this.getConfiguracionAutenticacion() != null) {
			envio.setConfiguracionAutenticacion(this.getConfiguracionAutenticacion().toModel());
		}
		if (this.getArea() != null) {
			envio.setArea(this.area.toModel());
		}
		if (this.getEntidad() != null) {
			envio.setEntidad(this.getEntidad().toModel());
		}
		return envio;
	}

	public static JEnvioRemoto fromModelStatic(final EnvioRemoto envio) {
		JEnvioRemoto jenvio = null;
		if (envio != null) {
			jenvio = new JEnvioRemoto();
			jenvio.fromModel(envio);
		}
		return jenvio;
	}

	public void fromModel(final EnvioRemoto envio) {
		if (envio != null) {
			this.setCodigo(envio.getCodigo());
			this.setIdentificador(envio.getIdentificador());
			this.setDescripcion(envio.getDescripcion());
			this.setTimeout(envio.getTimeout());
			if (envio.getConfiguracionAutenticacion() == null) {
				this.setConfiguracionAutenticacion(null);
			} else {
				this.setConfiguracionAutenticacion(
						JConfiguracionAutenticacion.fromModel(envio.getConfiguracionAutenticacion()));
			}
			if (envio.getArea() != null) {
				this.setArea(JArea.fromModel(envio.getArea()));
				// JEntidad jentidad = new JEntidad();
				// jentidad.setCodigo(envio.getEntidad().getCodigo());
				// jentidad.setIdentificador(envio.getEntidad().getIdentificador());
				// this.setEntidad(jentidad);
			} else {
				JEntidad jentidad = new JEntidad();
				jentidad.setCodigo(envio.getEntidad().getCodigo());
				jentidad.setIdentificador(envio.getEntidad().getIdentificador());
				this.setEntidad(jentidad);
			}
			this.setAmbito(envio.getAmbito().toString());
			this.setUrl(envio.getUrl());
		}
	}

	public static String encodeSql(final String sqlPlain) {
		return Base64.encodeBase64String(sqlPlain.getBytes());
	}

	public static String decodeSql(final String sqlEncoded) {
		if (sqlEncoded == null) {
			return null;
		} else {
			return new String(Base64.decodeBase64(sqlEncoded));
		}
	}

	/**
	 * Clona un dominio.
	 *
	 * @param dominio
	 * @param nuevoIdentificador
	 * @param jareas
	 * @param jfuenteDatos
	 * @param jentidad
	 * @return
	 */
	public static JEnvioRemoto clonar(final JEnvioRemoto envio, final String nuevoIdentificador, final JArea jareas,
			final JEntidad jentidad) {
		JEnvioRemoto jenvio = null;
		if (envio != null) {
			jenvio = new JEnvioRemoto();
			jenvio.setAmbito(envio.getAmbito());
			jenvio.setArea(jareas);
			jenvio.setDescripcion(envio.getDescripcion());
			jenvio.setEntidad(jentidad);
			jenvio.setTimeout(envio.timeout);
			jenvio.setConfiguracionAutenticacion(envio.getConfiguracionAutenticacion());
			jenvio.setIdentificador(nuevoIdentificador);
			jenvio.setUrl(envio.getUrl());
		}
		return jenvio;
	}
}
