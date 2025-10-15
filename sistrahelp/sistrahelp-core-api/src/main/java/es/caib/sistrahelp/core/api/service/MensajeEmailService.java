package es.caib.sistrahelp.core.api.service;

import es.caib.sistrahelp.core.api.model.DatosResumen;
import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;

public interface MensajeEmailService {

    String mensajeResumenDiario(String idioma, String nombre, String logo, byte[] imageBytes, DatosResumen datosResumen,
                          TypeRoleAcceso rolUsuario);
}
