package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;

/**
 * Documento Anexo.
 *
 * @author Indra
 */
@SuppressWarnings("serial")
public final class Anexo extends DocumentoFirmado {

	/** Tipo presentación. */
	private TypePresentacion presentacion;

	/** Anexar firmado. */
	private TypeSiNo anexarfirmado;

	/**
     * Indica el número máximo de instancias que se pueden anexar (si no es
     * genérico será siempre 1).
     */
    private int maxInstancias = ConstantesNumero.N1;

    /**
     * En caso de que se haya anexado, indica el nombre del fichero anexado. Si
     * es genérico puede haber varios ficheros y cada uno tendrá un título
     * particular.
     */
    private List<Fichero> ficheros = new ArrayList<>();

    /**
     * Lista de extensiones separadas por coma.
     */
    private String extensiones;

    /**
     * Tamaño máximo: con el sufijo KB o MB.
     */
    private String tamMax;

    /**
     * Texto de ayuda (HTML).
     */
    private String ayuda;

    /**
     * Indica si tiene plantilla (nulo si no tiene).
     */
    private PlantillaAnexo plantilla;

    /**
     * Indica si debe transformarse a PDF.
     */
    private TypeSiNo convertirPDF = TypeSiNo.NO;

    /**
     * Método de acceso a extensiones.
     *
     * @return extensiones
     */
    public String getExtensiones() {
        return extensiones;
    }

    /**
     * Método para establecer extensiones.
     *
     * @param pExtensiones
     *            extensiones a establecer
     */
    public void setExtensiones(final String pExtensiones) {
        extensiones = pExtensiones;
    }

    /**
     * Método de acceso a tamMax.
     *
     * @return tamMax
     */
    public String getTamMax() {
        return tamMax;
    }

    /**
     * Método para establecer tamMax.
     *
     * @param pTamMax
     *            tamMax a establecer
     */
    public void setTamMax(final String pTamMax) {
        tamMax = pTamMax;
    }

    /**
     * Método de acceso a ayuda.
     *
     * @return ayuda
     */
    public String getAyuda() {
        return ayuda;
    }

    /**
     * Método para establecer ayuda.
     *
     * @param pAyuda
     *            ayuda a establecer
     */
    public void setAyuda(final String pAyuda) {
        ayuda = pAyuda;
    }

    /**
     * Método de acceso a ficheros.
     *
     * @return ficheros
     */
    public List<Fichero> getFicheros() {
        return ficheros;
    }

    /**
     * Método de acceso a maxInstancias.
     *
     * @return maxInstancias
     */
    public int getMaxInstancias() {
        return maxInstancias;
    }

    /**
     * Método para establecer maxInstancias.
     *
     * @param pMaxInstancias
     *            maxInstancias a establecer
     */
    public void setMaxInstancias(final int pMaxInstancias) {
        maxInstancias = pMaxInstancias;
    }

    /**
     * Elimina la instancia de los ficheros.
     *
     * @param instancia
     *            Número de instancia
     */
    public void borrarFichero(final int instancia) {
        final Fichero f = this.getFicheros().get(
                instancia - ConstantesNumero.N1);
        this.getFicheros().remove(f);
    }

    /**
     * Método para establecer ficheros.
     *
     * @param pFicheros
     *            ficheros a establecer
     */
    public void setFicheros(final List<Fichero> pFicheros) {
        ficheros = pFicheros;
    }

    /**
     * Método para Crea new anexo de la clase Anexo.
     *
     * @return el anexo
     */
    public static Anexo createNewAnexo() {
        return new Anexo();
    }

    /**
     * Comprueba si el documento ha sido firmado por todos los firmantes.
     *
     * @return True si ha sido firmado por todos los firmantes.
     */
    public TypeSiNo getFirmado() {
        TypeSiNo firmado = TypeSiNo.SI;
        if (this.getFicheros().size() == 0) {
            firmado = TypeSiNo.NO;
        }
        for (final Fichero fichero : this.ficheros) {
            if (fichero.getFirmas().size() == 0) {
                firmado = TypeSiNo.NO;
                break;
            }
            for (final Firma f : fichero.getFirmas()) {
                if (f.getEstadoFirma() != TypeEstadoFirma.FIRMADO) {
                    firmado = TypeSiNo.NO;
                    break;
                }
            }
            if (firmado == TypeSiNo.NO) {
                break;
            }
        }
        return firmado;
    }

    /**
     * Comprueba si el documento ha sido firmado por un firmante.
     *
     * @param indiceFirmante
     *            Parámetro indice firmante
     * @return True si ha sido firmado por todos los firmantes.
     */
    public TypeSiNo getFirmado(final int indiceFirmante) {
        TypeSiNo firmado = TypeSiNo.SI;
        if (this.ficheros.size() == 0) {
            firmado = TypeSiNo.NO;
        }
        for (final Fichero fichero : this.ficheros) {
            if (fichero.getFirmas().get(indiceFirmante).getEstadoFirma() != TypeEstadoFirma.FIRMADO) {
                firmado = TypeSiNo.NO;
                break;
            }
        }
        return firmado;
    }

    /**
     * Comprueba si una instancia del documento ha sido firmado por un firmante.
     *
     * @param indiceFirmante
     *            Parámetro indice firmante
     * @param instancia
     *            Parámetro instancia (empieza en 1)
     * @return True si ha sido firmado por todos los firmantes.
     */
    public TypeSiNo getFirmado(final int indiceFirmante, final int instancia) {
        TypeSiNo firmado = TypeSiNo.NO;
        if (instancia <= this.ficheros.size()) {
            final Fichero fichero = this.ficheros.get(instancia
                    - ConstantesNumero.N1);
            if (fichero.getFirmas().get(indiceFirmante).getEstadoFirma() == TypeEstadoFirma.FIRMADO) {
                firmado = TypeSiNo.SI;
            }
        }
        return firmado;
    }

    /**
     * Método de acceso a plantilla.
     *
     * @return plantilla
     */
    public PlantillaAnexo getPlantilla() {
        return plantilla;
    }

    /**
     * Método para establecer plantilla.
     *
     * @param pPlantilla
     *            plantilla a establecer
     */
    public void setPlantilla(final PlantillaAnexo pPlantilla) {
        plantilla = pPlantilla;
    }

    /**
     * Método de acceso a convertirPDF.
     *
     * @return convertirPDF
     */
    public TypeSiNo getConvertirPDF() {
        return convertirPDF;
    }

    /**
     * Método para establecer convertirPDF.
     *
     * @param pConvertirPDF
     *            convertirPDF a establecer
     */
    public void setConvertirPDF(final TypeSiNo pConvertirPDF) {
        convertirPDF = pConvertirPDF;
    }

    public TypePresentacion getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(TypePresentacion presentacion) {
		this.presentacion = presentacion;
	}

	public TypeSiNo getAnexarfirmado() {
		return anexarfirmado;
	}

	public void setAnexarfirmado(TypeSiNo anexarfirmado) {
		this.anexarfirmado = anexarfirmado;
	}

}
