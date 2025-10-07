package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

public class ObjetoNavegacion {

   private  TypeObjetoFormulario tipoComponente;

   private String idComponente;

    public ObjetoNavegacion(TypeObjetoFormulario tipoComponente, String idComponente) {
         this.tipoComponente = tipoComponente;
         this.idComponente = idComponente;
    }


    public TypeObjetoFormulario getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(TypeObjetoFormulario tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(String idComponente) {
        this.idComponente = idComponente;
    }
}
