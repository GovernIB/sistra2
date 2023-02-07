package es.caib.sistrages.core.api.model;

import java.util.Date;
import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;

/**
 * The Class TramiteVersion.
 */

public class TramiteVersion extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** codigo. */
	private Long codigo;

	/** descripcion. */
	private String descripcion;

	/** Tipo Tramite. */
	private String tipoTramite;

	/** codigo tramite. */
	private Long idTramite;

	/** codigo area (No se mapea, sólo para ir mas rápidos con los permisos). */
	private Long idArea;

	/** identificador area (No se mapea, sólo para ir mas rápidos con los permisos). */
	private String identificadorArea;

	/** numero version. */
	private int numeroVersion;

	/** tipo flujo. */
	private TypeFlujo tipoFlujo;

	/** autenticado. */
	private boolean autenticado;

	/** No autenticado. **/
	private boolean noAutenticado;

	/** nivel QAA. */
	private int nivelQAA;

	/** idiomas soportados. */
	private String idiomasSoportados;

	/** persistencia. */
	private boolean persistencia;

	/** persistencia infinita. */
	private boolean persistenciaInfinita;

	/** persistencia dias. */
	private Integer persistenciaDias;

	/** codigo script personalizacion. */
	private Script scriptPersonalizacion;

	/** codigo script inicializacion tramite. */
	private Script scriptInicializacionTramite;

	/** bloqueada. */
	private Boolean bloqueada;

	/** codigo usuario bloqueo. */
	private String codigoUsuarioBloqueo;

	/** datos usuario bloqueo. */
	private String datosUsuarioBloqueo;

	/** release. */
	private int release;

	/** activa. */
	private boolean activa;

	/** debug. */
	private boolean debug;

	/** limite tramitacion. */
	private boolean limiteTramitacion;

	/** num limite tramitacion. */
	private Integer numLimiteTramitacion;

	/** int limite tramitacion. */
	private Integer intLimiteTramitacion;

	/** desactivacion. */
	private boolean desactivacion;

	/** plazo inicio desactivacion. */
	private Date plazoInicioDesactivacion;

	/** plazo fin desactivacion. */
	private Date plazoFinDesactivacion;

	/** mensaje desactivacion. */
	private Literal mensajeDesactivacion;

	/** Lista de dominios. */
	private List<Long> listaDominios;

	/** Pasos de tramite. */
	private List<TramitePaso> listaPasos;

	/**
	 * Fecha ultima modificacion. Importante, es sólo meramente informativo, no
	 * existe correlación con JVersionTramite. La fecha será el valor más actual del
	 * historial de versiones.
	 */
	private Date fechaUltima;

	/**
	 * Lista de identificadores de dominios. Esto se usa para validar los scripts
	 */
	private List<Dominio> listaAuxDominios;

	/** Huella. **/
	private String huella;

	/** Tipos de autenticacion **/
	private List<TypeAutenticacion> tiposAutenticacion;

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @return the tipoTramite
	 */
	public final String getTipoTramite() {
		return tipoTramite;
	}

	/**
	 * @param tipoTramite the tipoTramite to set
	 */
	public final void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public final String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public final void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public String getIdString() {
		if (codigo == null) {
			return "";
		} else {
			return codigo.toString();
		}
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo el nuevo valor de codigo
	 */
	public void setIdString(final String idString) {
		if (idString == null || idString.isEmpty()) {
			codigo = null;
		} else {
			codigo = Long.valueOf(idString);
		}
	}

	/**
	 * Obtiene el valor de numeroVersion.
	 *
	 * @return el valor de numeroVersion
	 */
	public int getNumeroVersion() {
		return numeroVersion;
	}

	/**
	 * Establece el valor de numeroVersion.
	 *
	 * @param numeroversion el nuevo valor de numeroVersion
	 */
	public void setNumeroVersion(final int numeroversion) {
		this.numeroVersion = numeroversion;
	}

	/**
	 * Obtiene el valor de tipoFlujo.
	 *
	 * @return el valor de tipoFlujo
	 */
	public TypeFlujo getTipoFlujo() {
		return tipoFlujo;
	}

	/**
	 * Establece el valor de tipoFlujo.
	 *
	 * @param flujo el nuevo valor de tipoFlujo
	 */
	public void setTipoFlujo(final TypeFlujo flujo) {
		this.tipoFlujo = flujo;
	}

	/**
	 * Verifica si es activa.
	 *
	 * @return true, si es activa
	 */
	public boolean isActiva() {
		return activa;
	}

	/**
	 * Establece el valor de activa.
	 *
	 * @param activa el nuevo valor de activa
	 */
	public void setActiva(final boolean activa) {
		this.activa = activa;
	}

	/**
	 * Obtiene el valor de release.
	 *
	 * @return el valor de release
	 */
	public int getRelease() {
		return release;
	}

	/**
	 * Establece el valor de release.
	 *
	 * @param release el nuevo valor de release
	 */
	public void setRelease(final int release) {
		this.release = release;
	}

	/**
	 * Obtiene el valor de idTramite.
	 *
	 * @return el valor de idTramite
	 */
	public Long getIdTramite() {
		return idTramite;
	}

	/**
	 * Establece el valor de idTramite.
	 *
	 * @param codigotramite el nuevo valor de idTramite
	 */
	public void setIdTramite(final Long codigotramite) {
		this.idTramite = codigotramite;
	}

	/**
	 * @return the idArea
	 */
	public Long getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(final Long idArea) {
		this.idArea = idArea;
	}

	/**
	 * Verifica si es autenticado.
	 *
	 * @return true, si es autenticado
	 */
	public boolean isAutenticado() {
		return autenticado;
	}

	/**
	 * Establece el valor de autenticado.
	 *
	 * @param autenticado el nuevo valor de autenticado
	 */
	public void setAutenticado(final boolean autenticado) {
		this.autenticado = autenticado;
	}

	/**
	 * @return the noAutenticado
	 */
	public boolean isNoAutenticado() {
		return noAutenticado;
	}

	/**
	 * @param noAutenticado the noAutenticado to set
	 */
	public void setNoAutenticado(final boolean noAutenticado) {
		this.noAutenticado = noAutenticado;
	}

	/**
	 * Obtiene el valor de nivelQAA.
	 *
	 * @return el valor de nivelQAA
	 */
	public int getNivelQAA() {
		return nivelQAA;
	}

	/**
	 * Establece el valor de nivelQAA.
	 *
	 * @param nivelQAA el nuevo valor de nivelQAA
	 */
	public void setNivelQAA(final int nivelQAA) {
		this.nivelQAA = nivelQAA;
	}

	/**
	 * Obtiene el valor de idiomasSoportados.
	 *
	 * @return el valor de idiomasSoportados
	 */
	public String getIdiomasSoportados() {
		return idiomasSoportados;
	}

	/**
	 * Establece el valor de idiomasSoportados.
	 *
	 * @param idiomassoportados el nuevo valor de idiomasSoportados
	 */
	public void setIdiomasSoportados(final String idiomassoportados) {
		this.idiomasSoportados = idiomassoportados;
	}

	/**
	 * Verifica si es persistencia.
	 *
	 * @return true, si es persistencia
	 */
	public boolean isPersistencia() {
		return persistencia;
	}

	/**
	 * Establece el valor de persistencia.
	 *
	 * @param persistencia el nuevo valor de persistencia
	 */
	public void setPersistencia(final boolean persistencia) {
		this.persistencia = persistencia;
	}

	/**
	 * Verifica si es persistencia infinita.
	 *
	 * @return true, si es persistencia infinita
	 */
	public boolean isPersistenciaInfinita() {
		return persistenciaInfinita;
	}

	/**
	 * Establece el valor de persistenciaInfinita.
	 *
	 * @param persistenciaInfinita el nuevo valor de persistenciaInfinita
	 */
	public void setPersistenciaInfinita(final boolean persistenciaInfinita) {
		this.persistenciaInfinita = persistenciaInfinita;
	}

	/**
	 * Obtiene el valor de persistenciaDias.
	 *
	 * @return el valor de persistenciaDias
	 */
	public Integer getPersistenciaDias() {
		return persistenciaDias;
	}

	/**
	 * Establece el valor de persistenciaDias.
	 *
	 * @param persistenciaDias el nuevo valor de persistenciaDias
	 */
	public void setPersistenciaDias(final Integer persistenciaDias) {
		this.persistenciaDias = persistenciaDias;
	}

	public Script getScriptPersonalizacion() {
		return scriptPersonalizacion;
	}

	public void setScriptPersonalizacion(final Script scriptPersonalizacion) {
		this.scriptPersonalizacion = scriptPersonalizacion;
	}

	public Script getScriptInicializacionTramite() {
		return scriptInicializacionTramite;
	}

	public void setScriptInicializacionTramite(final Script scriptInicializacionTramite) {
		this.scriptInicializacionTramite = scriptInicializacionTramite;
	}

	/**
	 * @return the bloqueada
	 */
	public Boolean getBloqueada() {
		return bloqueada;
	}

	/**
	 * @param bloqueada the bloqueada to set
	 */
	public void setBloqueada(final Boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	/**
	 * Obtiene el valor de codigoUsuarioBloqueo.
	 *
	 * @return el valor de codigoUsuarioBloqueo
	 */
	public String getCodigoUsuarioBloqueo() {
		return codigoUsuarioBloqueo;
	}

	/**
	 * Establece el valor de codigoUsuarioBloqueo.
	 *
	 * @param idUsuarioBloqueo el nuevo valor de codigoUsuarioBloqueo
	 */
	public void setCodigoUsuarioBloqueo(final String idUsuarioBloqueo) {
		this.codigoUsuarioBloqueo = idUsuarioBloqueo;
	}

	/**
	 * Obtiene el valor de datosUsuarioBloqueo.
	 *
	 * @return el valor de datosUsuarioBloqueo
	 */
	public String getDatosUsuarioBloqueo() {
		return datosUsuarioBloqueo;
	}

	/**
	 * Establece el valor de datosUsuarioBloqueo.
	 *
	 * @param datosUsuarioBloqueo el nuevo valor de datosUsuarioBloqueo
	 */
	public void setDatosUsuarioBloqueo(final String datosUsuarioBloqueo) {
		this.datosUsuarioBloqueo = datosUsuarioBloqueo;
	}

	/**
	 * Verifica si es debug.
	 *
	 * @return true, si es debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * Establece el valor de debug.
	 *
	 * @param debug el nuevo valor de debug
	 */
	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	/**
	 * Verifica si es limite tramitacion.
	 *
	 * @return true, si es limite tramitacion
	 */
	public boolean isLimiteTramitacion() {
		return limiteTramitacion;
	}

	/**
	 * Establece el valor de limiteTramitacion.
	 *
	 * @param limiteTramitacion el nuevo valor de limiteTramitacion
	 */
	public void setLimiteTramitacion(final boolean limiteTramitacion) {
		this.limiteTramitacion = limiteTramitacion;
	}

	/**
	 * Obtiene el valor de numLimiteTramitacion.
	 *
	 * @return el valor de numLimiteTramitacion
	 */
	public Integer getNumLimiteTramitacion() {
		return numLimiteTramitacion;
	}

	/**
	 * Establece el valor de numLimiteTramitacion.
	 *
	 * @param numLimiteTramitacion el nuevo valor de numLimiteTramitacion
	 */
	public void setNumLimiteTramitacion(final Integer numLimiteTramitacion) {
		this.numLimiteTramitacion = numLimiteTramitacion;
	}

	/**
	 * Obtiene el valor de intLimiteTramitacion.
	 *
	 * @return el valor de intLimiteTramitacion
	 */
	public Integer getIntLimiteTramitacion() {
		return intLimiteTramitacion;
	}

	/**
	 * Establece el valor de intLimiteTramitacion.
	 *
	 * @param intLimiteTramitacion el nuevo valor de intLimiteTramitacion
	 */
	public void setIntLimiteTramitacion(final Integer intLimiteTramitacion) {
		this.intLimiteTramitacion = intLimiteTramitacion;
	}

	/**
	 * Verifica si es desactivacion.
	 *
	 * @return true, si es desactivacion
	 */
	public boolean isDesactivacion() {
		return desactivacion;
	}

	/**
	 * Establece el valor de desactivacion.
	 *
	 * @param desactivacion el nuevo valor de desactivacion
	 */
	public void setDesactivacion(final boolean desactivacion) {
		this.desactivacion = desactivacion;
	}

	/**
	 * Obtiene el valor de plazoInicioDesactivacion.
	 *
	 * @return el valor de plazoInicioDesactivacion
	 */
	public Date getPlazoInicioDesactivacion() {
		if (this.plazoInicioDesactivacion == null) {
			return null;
		} else {
			return (Date) plazoInicioDesactivacion.clone();
		}
	}

	/**
	 * Establece el valor de plazoInicioDesactivacion.
	 *
	 * @param plazoInicioDesactivacion el nuevo valor de plazoInicioDesactivacion
	 */
	public void setPlazoInicioDesactivacion(final Date plazoInicioDesactivacion) {
		if (plazoInicioDesactivacion == null) {
			this.plazoInicioDesactivacion = null;
		} else {
			this.plazoInicioDesactivacion = (Date) plazoInicioDesactivacion.clone();
		}
	}

	/**
	 * Obtiene el valor de plazoFinDesactivacion.
	 *
	 * @return el valor de plazoFinDesactivacion
	 */
	public Date getPlazoFinDesactivacion() {
		if (this.plazoFinDesactivacion == null) {
			return null;
		} else {
			return (Date) plazoFinDesactivacion.clone();
		}
	}

	/**
	 * Establece el valor de plazoFinDesactivacion.
	 *
	 * @param plazoFinDesactivacion el nuevo valor de plazoFinDesactivacion
	 */
	public void setPlazoFinDesactivacion(final Date plazoFinDesactivacion) {
		if (plazoFinDesactivacion == null) {
			this.plazoFinDesactivacion = null;
		} else {
			this.plazoFinDesactivacion = (Date) plazoFinDesactivacion.clone();
		}
	}

	/**
	 * Obtiene el valor de mensajeDesactivacion.
	 *
	 * @return el valor de mensajeDesactivacion
	 */
	public Literal getMensajeDesactivacion() {
		return mensajeDesactivacion;
	}

	/**
	 * Establece el valor de mensajeDesactivacion.
	 *
	 * @param mensajeDesactivacion el nuevo valor de mensajeDesactivacion
	 */
	public void setMensajeDesactivacion(final Literal mensajeDesactivacion) {
		this.mensajeDesactivacion = mensajeDesactivacion;
	}

	/**
	 * Obtiene el valor de listaDominios.
	 *
	 * @return el valor de listaDominios
	 */
	public List<Long> getListaDominios() {
		return listaDominios;
	}

	/**
	 * Establece el valor de listaDominios.
	 *
	 * @param listaDominios el nuevo valor de listaDominios
	 */
	public void setListaDominios(final List<Long> listaDominios) {
		this.listaDominios = listaDominios;
	}

	/**
	 * @return the listaPasos
	 */
	public List<TramitePaso> getListaPasos() {
		return listaPasos;
	}

	/**
	 * @param listaPasos the listaPasos to set
	 */
	public void setListaPasos(final List<TramitePaso> listaPasos) {
		this.listaPasos = listaPasos;
	}

	/**
	 * OBtiene el paso de registrar, si lo tiene.
	 *
	 * @return
	 */
	public TramitePasoRegistrar getPasoRegistrar() {
		TramitePasoRegistrar pasoRegistrar = null;
		if (listaPasos != null) {
			for (final TramitePaso paso : this.listaPasos) {
				if (paso instanceof TramitePasoRegistrar) {
					pasoRegistrar = (TramitePasoRegistrar) paso;
					break;
				}
			}
		}
		return pasoRegistrar;
	}

	/**
	 * OBtiene el paso de rellenar, si lo tiene.
	 *
	 * @return
	 */
	public TramitePasoRellenar getPasoRellenar() {
		TramitePasoRellenar pasoRellenar = null;
		if (listaPasos != null) {
			for (final TramitePaso paso : this.listaPasos) {
				if (paso instanceof TramitePasoRellenar) {
					pasoRellenar = (TramitePasoRellenar) paso;
					break;
				}
			}
		}
		return pasoRellenar;
	}

	public TramitePasoAnexar getPasoAnexar() {
		TramitePasoAnexar pasoAnexar = null;
		if (listaPasos != null) {
			for (final TramitePaso paso : this.listaPasos) {
				if (paso instanceof TramitePasoAnexar) {
					pasoAnexar = (TramitePasoAnexar) paso;
					break;
				}
			}
		}
		return pasoAnexar;
	}

	public TramitePasoTasa getPasoTasa() {
		TramitePasoTasa pasoTasa = null;
		if (listaPasos != null) {
			for (final TramitePaso paso : this.listaPasos) {
				if (paso instanceof TramitePasoTasa) {
					pasoTasa = (TramitePasoTasa) paso;
					break;
				}
			}
		}
		return pasoTasa;
	}

	public TramitePasoDebeSaber getPasoDebeSaber() {
		TramitePasoDebeSaber pasoDebeSaber = null;
		if (listaPasos != null) {
			for (final TramitePaso paso : this.listaPasos) {
				if (paso instanceof TramitePasoDebeSaber) {
					pasoDebeSaber = (TramitePasoDebeSaber) paso;
					break;
				}
			}
		}
		return pasoDebeSaber;
	}

	/**
	 * @return the fechaUltima
	 */
	public Date getFechaUltima() {
		return fechaUltima;
	}

	/**
	 * @param fechaUltima the fechaUltima to set
	 */
	public void setFechaUltima(final Date fechaUltima) {
		this.fechaUltima = fechaUltima;
	}

	public List<Dominio> getListaAuxDominios() {
		return listaAuxDominios;
	}

	public void setListaAuxDominios(final List<Dominio> listaAuxDominios) {
		this.listaAuxDominios = listaAuxDominios;
	}

	/**
	 * @return the huella
	 */
	public String getHuella() {
		return huella;
	}

	/**
	 * @param huella the huella to set
	 */
	public void setHuella(final String huella) {
		this.huella = huella;
	}

	/**
	 * @return the tiposAutenticacion
	 */
	public List<TypeAutenticacion> getTiposAutenticacion() {
		return tiposAutenticacion;
	}

	/**
	 * @param tiposAutenticacion the tiposAutenticacion to set
	 */
	public void setTiposAutenticacion(final List<TypeAutenticacion> tiposAutenticacion) {
		this.tiposAutenticacion = tiposAutenticacion;
	}

	/**
	 * @return the identificadorArea
	 */
	public String getIdentificadorArea() {
		return identificadorArea;
	}

	/**
	 * @param identificadorArea the identificadorArea to set
	 */
	public void setIdentificadorArea(String identificadorArea) {
		this.identificadorArea = identificadorArea;
	}

	/**
	 * Comprueba si tiene un tipo de autenticacion
	 *
	 * @param string
	 * @return
	 */
	public boolean tieneTipoAutenticacion(final String tipo) {
		boolean tiene = false;
		if (tipo != null && !tipo.isEmpty() && this.getTiposAutenticacion() != null
				&& !this.getTiposAutenticacion().isEmpty()) {
			for (final TypeAutenticacion tipoAutenticacion : this.getTiposAutenticacion()) {
				if (tipoAutenticacion.toString().equals(tipo)) {
					tiene = true;
					break;
				}
			}
		}
		return tiene;
	}

	@Override
	public String toString() {
        return toString("", "ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "TramitVersio. ");
           texto.append(tabulacion +"\t Codi:" + codigo + "\n");
           texto.append(tabulacion +"\t NumeroVersio:" + numeroVersion + "\n");
           texto.append(tabulacion +"\t CodigoUsuariBloqueo:" + codigoUsuarioBloqueo + "\n");
           texto.append(tabulacion +"\t DadessUsuariBloqueo:" + datosUsuarioBloqueo + "\n");
           texto.append(tabulacion +"\t Release:" + release + "\n");
           texto.append(tabulacion +"\t Actiu:" + activa + "\n");
           texto.append(tabulacion +"\t debug:" + debug + "\n");
           texto.append(tabulacion +"\t Descripció:" + descripcion + "\n");
           texto.append(tabulacion +"\t TipusTramit:" + tipoTramite + "\n");
           texto.append(tabulacion +"\t IdTramit:" + idTramite + "\n");
           texto.append(tabulacion +"\t IdArea:" + idArea + "\n");
           texto.append(tabulacion +"\t IdentificadorArea:" + identificadorArea + "\n");
           texto.append(tabulacion +"\t TipusFlux:" + tipoFlujo + "\n");
           texto.append(tabulacion +"\t Autenticat:" + autenticado + "\n");
           texto.append(tabulacion +"\t NoAutenticat:" + noAutenticado + "\n");
           texto.append(tabulacion +"\t NivellQAA:" + nivelQAA + "\n");
           texto.append(tabulacion +"\t IdiomasSoportats:" + idiomasSoportados + "\n");
           texto.append(tabulacion +"\t Persistencia:" + persistencia + "\n");
           texto.append(tabulacion +"\t PersistenciaInfinita:" + persistenciaInfinita + "\n");
           texto.append(tabulacion +"\t PersistenciaDies:" + persistenciaDias + "\n");
           if (scriptPersonalizacion != null) {
        	   texto.append(tabulacion +"\t ScriptPersonalitzacio: \n");
        	   texto.append(scriptPersonalizacion.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (scriptInicializacionTramite != null) {
        	   texto.append(tabulacion +"\t ScriptInicialitzacioTramit: \n");
        	   texto.append(scriptInicializacionTramite.toString(tabulacion+"\t", idioma)+ "\n");
           }
           texto.append(tabulacion +"\t LimiteTramitacio:" + limiteTramitacion + "\n");
           texto.append(tabulacion +"\t NumLimiteTramitacio:" + numLimiteTramitacion + "\n");
           texto.append(tabulacion +"\t IntLimiteTramitacio:" + intLimiteTramitacion + "\n");
           texto.append(tabulacion +"\t Desactivacio:" + desactivacion + "\n");
           texto.append(tabulacion +"\t TerminiIniciDesactivacio:" + plazoInicioDesactivacion + "\n");
           texto.append(tabulacion +"\t TerminiFiDesactivacio:" + plazoFinDesactivacion + "\n");
           if (mensajeDesactivacion != null) {
        	   texto.append(tabulacion +"\t MissatgeDesactivacio: \n");
        	   texto.append(mensajeDesactivacion.toString(tabulacion, idioma) + "\n");
           }
           if (listaPasos != null && !listaPasos.isEmpty()) {
        	   texto.append(tabulacion +"\t Pasos: \n");
        	   for(TramitePaso paso : listaPasos) {
        		   if (paso instanceof TramitePasoDebeSaber) {
        			   texto.append(( (TramitePasoDebeSaber) paso ).toString(tabulacion, idioma) + "\n");
        		   } else if (paso instanceof TramitePasoRellenar) {
        			   texto.append(( (TramitePasoRellenar) paso ).toString(tabulacion, idioma) + "\n");
        		   } else if (paso instanceof TramitePasoTasa) {
        			   texto.append(( (TramitePasoTasa) paso ).toString(tabulacion, idioma) + "\n");
        		   } else if (paso instanceof TramitePasoAnexar) {
        			   texto.append(( (TramitePasoAnexar) paso ).toString(tabulacion, idioma) + "\n");
        		   } else if (paso instanceof TramitePasoRegistrar) {
        			   texto.append(( (TramitePasoRegistrar) paso ).toString(tabulacion, idioma) + "\n");
        		   } else {
        			   texto.append(paso.toString(tabulacion, idioma) + "\n");
        		   }
        	   }
           }


           //texto.append(tabulacion +"\t listaDominios:" + listaDominios == null ? "VACIO" : listaDominios.toString() + "\n");
           return texto.toString();
     }

}
