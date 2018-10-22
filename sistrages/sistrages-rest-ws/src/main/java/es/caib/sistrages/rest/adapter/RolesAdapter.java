package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.rest.api.interna.RPermisoHelpDesk;

/**
 * Adapter para convertir a modelo rest.
 *
 * @author Indra
 *
 */
@Component
public class RolesAdapter {

	/**
	 * Conversion a modelo rest.
	 *
	 * @param idEntidad
	 *            id entidad
	 * @return modelo rest
	 */
	public List<RPermisoHelpDesk> convertir(final List<Rol> roles) {
		final List<RPermisoHelpDesk> rListaPermisos = new ArrayList<>();
		for (final Rol rol : roles) {
			final RPermisoHelpDesk rPermiso = new RPermisoHelpDesk();
			rPermiso.setCodigoDIR3Entidad(rol.getArea().getCodigoDIR3Entidad());
			rPermiso.setIdentificadorArea(rol.getArea().getIdentificador());
			rPermiso.setTipo(rol.getTipo().toString());
			rPermiso.setValor(rol.getValor());
			rListaPermisos.add(rPermiso);
		}
		return rListaPermisos;
	}

}
