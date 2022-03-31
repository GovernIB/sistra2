package es.caib.sistrages.core.api.model.comun;

 import es.caib.sistrages.core.api.model.types.TypeAmbito;

public class ValorIdentificadorCompuesto {

	public static final String SEPARACION_IDENTIFICADOR_COMPUESTO = ".";
	private TypeAmbito ambito;
	private String identificador;
	private String identificadorEntidad;
	private String identificadorArea;

	private boolean error = false;

	/**
	 * @return the ambito
	 */
	public TypeAmbito getAmbito() {
		return ambito;
	}
	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(TypeAmbito ambito) {
		this.ambito = ambito;
	}
	/**
	 * @return the identificadorEntidad
	 */
	public String getIdentificadorEntidad() {
		return identificadorEntidad;
	}
	/**
	 * @param identificadorEntidad the identificadorEntidad to set
	 */
	public void setIdentificadorEntidad(String identificadorEntidad) {
		this.identificadorEntidad = identificadorEntidad;
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
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}
	public  ValorIdentificadorCompuesto(String identificadorCompuesto) {

		if (identificadorCompuesto.startsWith("GLOBAL"+SEPARACION_IDENTIFICADOR_COMPUESTO)) {
			this.setAmbito(TypeAmbito.GLOBAL);
			this.setIdentificador(identificadorCompuesto.substring(7));
		} else {
			String[] idDominioSplit = identificadorCompuesto.split("\\"+SEPARACION_IDENTIFICADOR_COMPUESTO);
			if (idDominioSplit.length == 2) {
				this.setAmbito(TypeAmbito.ENTIDAD);

				//Si sólo hay dos, es tipo entidad. ID-ENTIDAD:IDENTIFICADOR_DOMINIO
				this.setIdentificadorEntidad(idDominioSplit[0]);
				this.setIdentificador(idDominioSplit[1]);
			} else if (idDominioSplit.length == 3) {
				this.setAmbito(TypeAmbito.AREA);

				//Si hay tres, es tipo area. ID-ENTIDAD:ID-AREA:IDENTIFICADOR_DOMINIO~
				this.setIdentificadorEntidad(idDominioSplit[0]);
				this.setIdentificadorArea(idDominioSplit[1]);
				this.setIdentificador(idDominioSplit[2]);
			} else {
				error = true;
			}

		}
	}

}
