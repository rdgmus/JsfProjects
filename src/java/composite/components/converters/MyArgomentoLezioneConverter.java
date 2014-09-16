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
@FacesConverter(value = "myArgomentoLezioneConverter")
public class MyArgomentoLezioneConverter implements Converter {

   

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        String string = null;
        if (value != null) {
            if (value instanceof LezioniMateria) {
                LezioniMateria l = (LezioniMateria) value;
                string = l.getArgomento();
            }
        }
        return string;
    }
}
