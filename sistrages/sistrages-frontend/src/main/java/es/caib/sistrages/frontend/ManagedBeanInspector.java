package es.caib.sistrages.frontend;


import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import java.beans.FeatureDescriptor;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Utility class to inspect managed beans in a JSF application.
 */
public class ManagedBeanInspector {

    /**
     * Lists all managed beans in the current JSF application.
     */
    public static void listManagedBeans() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        Iterator<FeatureDescriptor> iter = application.getELResolver().getFeatureDescriptors(facesContext.getELContext(), null);
        Set<String> beanNames = new HashSet<>();
        Set<String> viewScopedBeanNames = new HashSet<>();
        while(iter.hasNext()){
            FeatureDescriptor descriptor = iter.next();
            String beanName = descriptor.getName();

            beanNames.add(beanName);
        }


        System.out.println("Managed Beans:");
        viewScopedBeanNames.forEach(System.out::println);
        beanNames.forEach(System.out::println);
    }

    public static void printAllManagedBeans() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        Map<String, Object> beans = facesContext.getExternalContext().getApplicationMap();

        System.out.println("Managed Beans app map:");
        beans.forEach((name, bean) -> {
            if (elContext.getELResolver().getValue(elContext, null, name) != null) {
                System.out.println(name + " -> " + bean.getClass().getName());
            }
        });
    }

    public static void listActiveViewScopedBeans() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<Object, Object> activeViewMaps = (Map<Object, Object>) facesContext.getAttributes()
                .get("com.sun.faces.application.view.activeViewMaps");

        if (activeViewMaps != null) {
            System.out.println("Active ViewScoped Beans:");
            activeViewMaps.forEach((viewId, viewMap) -> {
                System.out.println("View ID: " + viewId);
                if (viewMap instanceof Map) {
                    ((Map<?, ?>) viewMap).forEach((beanName, beanInstance) -> {
                        System.out.println("  Bean Name: " + beanName + " -> " + beanInstance.getClass().getName());
                    });
                }
            });
        } else {
            System.out.println("No active ViewScoped beans found.");
        }
    }
}
