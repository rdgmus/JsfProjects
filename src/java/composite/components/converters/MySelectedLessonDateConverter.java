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
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.entities.LezioniMateria;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "mySelectedLessonDateConverter")
public class MySelectedLessonDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
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


        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String string = null;
        if (value instanceof LezioniMateria) {
            LezioniMateria d = (LezioniMateria) value;
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE d");

            string = toCapitalLetter(sdf.format(d.getDataLezione()));
            
        }
        if (value instanceof java.lang.String) {
            string = String.valueOf(value);
        }
        return string;
    }
    
    private String toCapitalLetter(String input){
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }
}
