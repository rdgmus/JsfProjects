/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
@FacesConverter(value = "myDateConverter")
public class MyDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if (value == null) {
            return null;
        }
        Date myDate = null;

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

        //You can use a DateFormat to parse also.
        try {
            myDate = df.parse(value);
        } catch (ParseException parseException) {
            JsfUtil.addErrorMessage(parseException, parseException.getMessage());
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        String string = null;
        if (value instanceof Date) {
            Date d = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            string = sdf.format(d);
        }
        return string;
    }
}
