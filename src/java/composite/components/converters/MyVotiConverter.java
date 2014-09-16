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
@FacesConverter(value = "myVotiConverter")
public class MyVotiConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        String string = null;
        if (value != null) {
            Double d = new Double(String.valueOf(value));
            if (d.doubleValue() == 0.0) {
                string = "n.c.";
            } else {
                string = String.valueOf(value);
            }
        }
        return string;
    }
}
