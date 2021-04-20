/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib.exceptions;

/**
 * @author Indra
 *
 */
public class TablaException extends Exception{


  /** Codigo error. */
  private String codigoError;
  /** String mensaje. */
  private String mensajeError;

  /**
   * Constructor.
   *
   * @param message Mensaje
   * @param cause Causa
   */
  public TablaException(final String message, final Throwable cause) {
    super(message, cause);
  }
  /**
   * Constructor.
   *
   * @param message Mensaje
   */
  public TablaException(final String message) {
    super(message);
  }

  /**
   * Constructor.
   *
   * @param message Mensaje
   * @param atributos atributos
   *
   */
  public TablaException(final String codigoError, final String mensajeError) {
    super();
    this.codigoError = codigoError;
    this.mensajeError = mensajeError;
  }

  /**
   * Obtiene codigoError.
   *
   * @return codigoError
   */
  public String getCodigoError() {
    return codigoError;
  }

  /**
   * Establece codigoError.
   *
   * @param codigoError codigoError a establecer
   */
  public void setCodigoError(final String codigoError) {
    this.codigoError = codigoError;
  }

  /**
   * Obtiene mensajeError.
   *
   * @return mensajeError
   */
  public String getMensajeError() {
    return mensajeError;
  }

  /**
   * Establece mensajeError.
   *
   * @param mensajeError mensajeError a establecer
   */
  public void setMensajeError(final String mensajeError) {
    this.mensajeError = mensajeError;
  }

}
