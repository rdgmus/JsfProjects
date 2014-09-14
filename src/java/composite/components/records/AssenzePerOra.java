/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.records;

/**
 *
 * @author rdgmus
 */
public class AssenzePerOra {

    private int ora;
    private boolean assenza;

    public AssenzePerOra() {
    }


    public AssenzePerOra(int ora, boolean assenza) {
        this.ora = ora;
        this.assenza = assenza;
    }

    public int getOra() {
        return ora;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }

    public boolean isAssenza() {
        return assenza;
    }

    public void setAssenza(boolean assenza) {
        this.assenza = assenza;
    }

    
}
