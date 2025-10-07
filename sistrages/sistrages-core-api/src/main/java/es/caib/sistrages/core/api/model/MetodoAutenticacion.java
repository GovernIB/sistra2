package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeAutenticacion;

import java.util.List;

public class MetodoAutenticacion {

    private TypeAutenticacion tipo;

    private List<String> subtipos;



    public TypeAutenticacion getTipo() {
        return tipo;
    }

    public void setTipo(TypeAutenticacion tipo) {
        this.tipo = tipo;
    }

    public List<String> getSubtipos() {
        return subtipos;
    }

    public void setSubtipos(List<String> subtipos) {
        this.subtipos = subtipos;
    }


    // Builder



    public static class Builder {
        private final MetodoAutenticacion metodoAutenticacion;

        public Builder() {
            this.metodoAutenticacion = new MetodoAutenticacion();
        }

        public Builder tipo(TypeAutenticacion tipo) {
            this.metodoAutenticacion.setTipo(tipo);
            return this;
        }

        public Builder subtipos(List<String> subtipos) {
            this.metodoAutenticacion.setSubtipos(subtipos);
            return this;
        }

        public MetodoAutenticacion build() {
            return this.metodoAutenticacion;
        }
    }


}
