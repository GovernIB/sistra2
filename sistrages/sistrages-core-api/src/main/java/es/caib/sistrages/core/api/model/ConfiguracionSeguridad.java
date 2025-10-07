package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeNivelEidas;
import es.caib.sistrages.core.api.model.types.TypeNivelSeguridad;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionSeguridad {

    private TypeNivelSeguridad nivelSeguridad;

    private TypeNivelEidas nivelEidas;

    private List<MetodoAutenticacion> metodosAutenticacion;

    private boolean verificarFirmanteFormulario;

    private boolean configurableVerificarFirmantesAnexo;

    private boolean opcionalExtensionAnexo;

    public TypeNivelSeguridad getNivelSeguridad() {
        return nivelSeguridad;
    }

    public void setNivelSeguridad(TypeNivelSeguridad nivelSeguridad) {
        this.nivelSeguridad = nivelSeguridad;
    }

    public TypeNivelEidas getNivelEidas() {
        return nivelEidas;
    }

    public void setNivelEidas(TypeNivelEidas nivelEidas) {
        this.nivelEidas = nivelEidas;
    }

    public List<MetodoAutenticacion> getMetodosAutenticacion() {
        return metodosAutenticacion;
    }

    public void setMetodosAutenticacion(List<MetodoAutenticacion> metodosAutenticacion) {
        this.metodosAutenticacion = metodosAutenticacion;
    }

    public boolean isVerificarFirmanteFormulario() {
        return verificarFirmanteFormulario;
    }

    public void setVerificarFirmanteFormulario(boolean verificarFirmanteFormulario) {
        this.verificarFirmanteFormulario = verificarFirmanteFormulario;
    }

    public boolean isConfigurableVerificarFirmantesAnexo() {
        return configurableVerificarFirmantesAnexo;
    }

    public void setConfigurableVerificarFirmantesAnexo(boolean configurableVerificarFirmantesAnexo) {
        this.configurableVerificarFirmantesAnexo = configurableVerificarFirmantesAnexo;
    }

    public boolean isOpcionalExtensionAnexo() {
        return opcionalExtensionAnexo;
    }

    public void setOpcionalExtensionAnexo(boolean opcionalExtensionAnexo) {
        this.opcionalExtensionAnexo = opcionalExtensionAnexo;
    }

    public MetodoAutenticacion getMetodoAutenticacion(TypeAutenticacion typeAutenticacion) {

        if (metodosAutenticacion != null) {
            for (MetodoAutenticacion metodo : metodosAutenticacion) {
                if (typeAutenticacion.equals(metodo.getTipo())) {
                    return metodo;
                }
            }
        }
        return null;
    }

    // patrón builder
    public static class Builder {
        private ConfiguracionSeguridad configuracion;

        public Builder() {
            configuracion = new ConfiguracionSeguridad();
        }

        public Builder nivelSeguridad(TypeNivelSeguridad nivelSeguridad) {
            configuracion.setNivelSeguridad(nivelSeguridad);
            return this;
        }

        public Builder nivelEidas(TypeNivelEidas nivelEidas) {
            configuracion.setNivelEidas(nivelEidas);
            return this;
        }

        public Builder metodosAutenticacion(List<MetodoAutenticacion> metodos) {
            configuracion.setMetodosAutenticacion(metodos);
            return this;
        }

        public Builder verificarFirmanteFormulario(boolean verificar) {
            configuracion.setVerificarFirmanteFormulario(verificar);
            return this;
        }

        public Builder configurableVerificarFirmantesAnexo(boolean verificar) {
            configuracion.setConfigurableVerificarFirmantesAnexo(verificar);
            return this;
        }

        public Builder opcionalExtensionAnexo(boolean cualquierExtension) {
            configuracion.setOpcionalExtensionAnexo(cualquierExtension);
            return this;
        }

        public ConfiguracionSeguridad build() {
            return configuracion;
        }

        // builder list metodosAutenticacion one to one
        public Builder metodoAutenticacion(MetodoAutenticacion metodo) {
            if (configuracion.getMetodosAutenticacion() == null) {
                configuracion.setMetodosAutenticacion(new ArrayList<>());
            }

            configuracion.getMetodosAutenticacion().add(metodo);

            return this;
        }
    }


}
