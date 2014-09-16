/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.entities.LezioniMateria;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "mySelectedLessonOreConverter")
public class MySelectedLessonOreConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String string = null;
        if (value instanceof LezioniMateria) {
            LezioniMateria d = (LezioniMateria) value;
            Integer oreLezione = d.getOreLezione();
            string = String.valueOf(oreLezione);
        }
        return string;
    }
    
}
