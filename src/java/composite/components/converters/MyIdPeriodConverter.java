/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.entities.PeriodiAnnoScolastico;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "myIdPeriodConverter")
public class MyIdPeriodConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        if (value == null) {
            return null;
        }
        long idPeriod = Long.valueOf(value).longValue();
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        String string = null;
        if (value instanceof PeriodiAnnoScolastico) {
            PeriodiAnnoScolastico period = (PeriodiAnnoScolastico) value;
            string = String.valueOf(period.getPeriodo()).toUpperCase();
        }
        return string;
    }
}
