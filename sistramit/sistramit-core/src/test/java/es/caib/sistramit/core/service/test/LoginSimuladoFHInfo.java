package es.caib.sistramit.core.service.test;

import es.caib.sistramit.core.api.model.flujo.PersonaDesglosado;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

public class LoginSimuladoFHInfo {

    private UsuarioAutenticadoInfo usuarioAutenticado;

    private PersonaDesglosado funcionarioHabilitado;

    public LoginSimuladoFHInfo(UsuarioAutenticadoInfo usuarioAutenticado, PersonaDesglosado funcionarioHabilitado) {
        this.usuarioAutenticado = usuarioAutenticado;
        this.funcionarioHabilitado = funcionarioHabilitado;
    }

    public UsuarioAutenticadoInfo getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public PersonaDesglosado getFuncionarioHabilitado() {
        return funcionarioHabilitado;
    }

}
