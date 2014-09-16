/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "myAssenzaConverter")
public class MyAssenzaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if (value == null) {
            return null;
        }
        return Boolean.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if(value instanceof Boolean){
            Boolean b = (Boolean)value;
            return b.toString();
        }
        return "";
    }
}
