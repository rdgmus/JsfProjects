/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jpa.entities.Studenti;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@FacesConverter(value = "myStudentConverter")
public class MyStudentConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        throw new UnsupportedOperationException("Not supported yet.");
        //<h:outputText value="idStudente:#{studente.idStudente}-#{studente.cognome}"/>

        String string = null;
        if (value instanceof Studenti) {
            Studenti studente = (Studenti) value;
            JsfUtil.setRequestMapValue("Studente", studente);
            string = "idStudente:"+studente.getIdStudente()+"-"+studente.getCognome();
        }
        return string;
    }
}
