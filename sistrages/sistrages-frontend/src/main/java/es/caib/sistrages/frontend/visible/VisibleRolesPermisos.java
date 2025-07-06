package es.caib.sistrages.frontend.visible;

public class VisibleRolesPermisos {

    private boolean permisosGestorTramites;

    private boolean area;

    private boolean helpdeskCau;

    public VisibleRolesPermisos() {
        // Constructor por defecto
        permisosGestorTramites = true;
        area = true;
    }

    public boolean isPermisosGestorTramites() {
        return permisosGestorTramites;
    }

    public void setPermisosGestorTramites(boolean permisosGestorTramites) {
        this.permisosGestorTramites = permisosGestorTramites;
    }

    public boolean isArea() {
        return area;
    }

    public void setArea(boolean area) {
        this.area = area;
    }

    public void setHelpdeskCau(boolean helpdeskCau) {
        this.helpdeskCau = helpdeskCau;
    }

    public boolean isHelpdeskCau() {
        return helpdeskCau;
    }
}
