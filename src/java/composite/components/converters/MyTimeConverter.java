/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "myTimeConverter")
public class MyTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if (value == null) {
            return null;
        }
        Time myTime = null;
        try {
            myTime = Time.valueOf(value + ":00");
        } catch (NumberFormatException ex) {
            JsfUtil.addErrorMessage(ex, "NumberFormatException:" + ex.getMessage() + " Value=" + value);
        } catch (IllegalArgumentException ex) {
            JsfUtil.addErrorMessage(ex, "IllegalArgumentException:" + ex.getMessage() + " Value=" + value);
        }
        //You can use a DateFormat to parse also.

        return myTime;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        String string = null;
        if (value instanceof Date) {
            Date d = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            string = sdf.format(d);
        }
        return string;
    }
}
