package jsf;

import jpa.entities.VotiLezioniStudente;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.VotiLezioniStudenteFacade;

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

@ManagedBean(name = "votiLezioniStudenteController")
@SessionScoped
public class VotiLezioniStudenteController implements Serializable {

    private VotiLezioniStudente current;
    private DataModel items = null;
    @EJB
    private jpa.session.VotiLezioniStudenteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public VotiLezioniStudenteController() {
    }

    public VotiLezioniStudente getSelected() {
        if (current == null) {
            current = new VotiLezioniStudente();
            current.setVotiLezioniStudentePK(new jpa.entities.VotiLezioniStudentePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private VotiLezioniStudenteFacade getFacade() {
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
        current = (VotiLezioniStudente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new VotiLezioniStudente();
        current.setVotiLezioniStudentePK(new jpa.entities.VotiLezioniStudentePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getVotiLezioniStudentePK().setIdStudente(current.getStudenti().getIdStudente());
            current.getVotiLezioniStudentePK().setIdLezione(current.getLezioni().getIdLezione());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("VotiLezioniStudenteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (VotiLezioniStudente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getVotiLezioniStudentePK().setIdStudente(current.getStudenti().getIdStudente());
            current.getVotiLezioniStudentePK().setIdLezione(current.getLezioni().getIdLezione());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("VotiLezioniStudenteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (VotiLezioniStudente) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("VotiLezioniStudenteDeleted"));
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

    @FacesConverter(forClass = VotiLezioniStudente.class)
    public static class VotiLezioniStudenteControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VotiLezioniStudenteController controller = (VotiLezioniStudenteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "votiLezioniStudenteController");
            return controller.ejbFacade.find(getKey(value));
        }

        jpa.entities.VotiLezioniStudentePK getKey(String value) {
            jpa.entities.VotiLezioniStudentePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new jpa.entities.VotiLezioniStudentePK();
            key.setIdLezione(Long.parseLong(values[0]));
            key.setIdVoto(Long.parseLong(values[1]));
            key.setIdStudente(Long.parseLong(values[2]));
            return key;
        }

        String getStringKey(jpa.entities.VotiLezioniStudentePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdLezione());
            sb.append(SEPARATOR);
            sb.append(value.getIdVoto());
            sb.append(SEPARATOR);
            sb.append(value.getIdStudente());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof VotiLezioniStudente) {
                VotiLezioniStudente o = (VotiLezioniStudente) object;
                return getStringKey(o.getVotiLezioniStudentePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + VotiLezioniStudente.class.getName());
            }
        }

    }

}
