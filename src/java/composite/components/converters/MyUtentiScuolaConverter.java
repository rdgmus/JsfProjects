/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.entities.UtentiScuola;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "myUtentiScuolaConverter")
public class MyUtentiScuolaConverter implements Converter {


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        //        throw new UnsupportedOperationException("Not supported yet.");
        
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String string = null;
        if (value instanceof UtentiScuola) {
            UtentiScuola utente = (UtentiScuola) value;
//            string = utente.getEmail();
            string = utente.toString();
        }
        return string;
    }
}
