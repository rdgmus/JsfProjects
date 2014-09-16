/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "myMesiCalendarConverter")
public class MyMesiCalendarConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if (value == null) {
            return null;
        }
        Date myDate = null;

        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);

        //You can use a DateFormat to parse also.
        try {
            myDate = df.parse(value);
        } catch (ParseException parseException) {
        }

        return myDate;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        String string = null;
        if (value instanceof Date) {
            Date d = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat("MMMMM - yyyy");
            string = sdf.format(d.getTime());
        }
        return string.toUpperCase();
    }
    
}
