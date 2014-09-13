package jsf;

import jpa.entities.PeriodiAnnoScolastico;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.PeriodiAnnoScolasticoFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "periodiAnnoScolasticoController")
@SessionScoped
public class PeriodiAnnoScolasticoController implements Serializable {

    private PeriodiAnnoScolastico current;
    private DataModel items = null;
    @EJB
    private jpa.session.PeriodiAnnoScolasticoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PeriodiAnnoScolasticoController() {
    }

    public PeriodiAnnoScolastico getSelected() {
        if (current == null) {
            current = new PeriodiAnnoScolastico();
            current.setPeriodiAnnoScolasticoPK(new jpa.entities.PeriodiAnnoScolasticoPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private PeriodiAnnoScolasticoFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (PeriodiAnnoScolastico) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new PeriodiAnnoScolastico();
        current.setPeriodiAnnoScolasticoPK(new jpa.entities.PeriodiAnnoScolasticoPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getPeriodiAnnoScolasticoPK().setIdAnnoScolastico(current.getAnniScolastici().getIdAnnoScolastico());
            current.getPeriodiAnnoScolasticoPK().setIdScuola(current.getScuole().getIdScuola());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("PeriodiAnnoScolasticoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (PeriodiAnnoScolastico) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getPeriodiAnnoScolasticoPK().setIdAnnoScolastico(current.getAnniScolastici().getIdAnnoScolastico());
            current.getPeriodiAnnoScolasticoPK().setIdScuola(current.getScuole().getIdScuola());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("PeriodiAnnoScolasticoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (PeriodiAnnoScolastico) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("PeriodiAnnoScolasticoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = PeriodiAnnoScolastico.class)
    public static class PeriodiAnnoScolasticoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PeriodiAnnoScolasticoController controller = (PeriodiAnnoScolasticoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "periodiAnnoScolasticoController");
            return controller.ejbFacade.find(getKey(value));
        }

        jpa.entities.PeriodiAnnoScolasticoPK getKey(String value) {
            jpa.entities.PeriodiAnnoScolasticoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new jpa.entities.PeriodiAnnoScolasticoPK();
            key.setIdPeriodo(Long.parseLong(values[0]));
            key.setIdScuola(Long.parseLong(values[1]));
            key.setIdAnnoScolastico(Long.parseLong(values[2]));
            return key;
        }

        String getStringKey(jpa.entities.PeriodiAnnoScolasticoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPeriodo());
            sb.append(SEPARATOR);
            sb.append(value.getIdScuola());
            sb.append(SEPARATOR);
            sb.append(value.getIdAnnoScolastico());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PeriodiAnnoScolastico) {
                PeriodiAnnoScolastico o = (PeriodiAnnoScolastico) object;
                return getStringKey(o.getPeriodiAnnoScolasticoPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PeriodiAnnoScolastico.class.getName());
            }
        }

    }

}
