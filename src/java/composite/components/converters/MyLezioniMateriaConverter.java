/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.entities.LezioniMateria;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "myLezioniMateriaConverter")
public class MyLezioniMateriaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
//        LezioniMateria l = null;
//        if (component.getId().equals("lezioneDaRimuovere")) {
//
//            RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
//                    .getSessionMap().get("registriInsegnanteBean");
//            if (controller != null) {
//                BigInteger i = new BigInteger(String.valueOf(value));
//                l = controller.getLezioniMateriaFacade().find(i);
//
//                CreaLezioniBean bean = (CreaLezioniBean) FacesContext.getCurrentInstance().getExternalContext()
//                        .getSessionMap().get("creaLezioniBean");
//                if (bean != null) {
//                    bean.setLezioneDaRimuovere(l);
//                }
//            }
//        }
//        return l;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        String string = null;
        if (value instanceof LezioniMateria) {
            LezioniMateria m = (LezioniMateria) value;
            if (component.getId().equals("lezioneDaRimuovere")) {
                string = String.valueOf(m.getIdLezione());
            } else {
                Date d = (Date) m.getDataLezione();
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE d MMMM yyyy");

                string = sdf.format(d);
            }
        }
        return string;
    }
}
