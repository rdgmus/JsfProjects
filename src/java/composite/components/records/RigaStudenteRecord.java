/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.records;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jpa.entities.VotiLezioniStudente;

/**
 *
 * @author rdgmus
 */
public class RigaStudenteRecord {

    private String cognome;
    private String nome;
    private ArrayList<AssenzePerOra> oreAssenze;
    private List<VotiLezioniStudente> voti;
    private String nota;
    private BigInteger idLezione;
    private Long idStudente;
    private int registroIndex;
    private Boolean ora1Assenza;
    private Boolean ora2Assenza;
    private Boolean ora3Assenza;
    private Date ritiratoData, dataEntrata;
    private Boolean ritardo;
    private Boolean renderRitardo;

    public RigaStudenteRecord() {
    }

    public Boolean isRenderRitardo() {
        return renderRitardo;
    }

    public void setRenderRitardo(Boolean renderRitardo) {
        this.renderRitardo = renderRitardo;
    }

    public Boolean getRitardo() {
        return ritardo;
    }

    public void setRitardo(Boolean ritardo) {
        this.ritardo = ritardo;
    }

    public Boolean getOra1Assenza() {
        return ora1Assenza;
    }

    public void setOra1Assenza(Boolean ora1Assenza) {
        this.ora1Assenza = ora1Assenza;
    }

    public Boolean getOra2Assenza() {
        return ora2Assenza;
    }

    public void setOra2Assenza(Boolean ora2Assenza) {
        this.ora2Assenza = ora2Assenza;
    }

    public Boolean getOra3Assenza() {
        return ora3Assenza;
    }

    public void setOra3Assenza(Boolean ora3Assenza) {
        this.ora3Assenza = ora3Assenza;
    }

    public int getRegistroIndex() {
        return registroIndex;
    }

    public void setRegistroIndex(int registroIndex) {
        this.registroIndex = registroIndex;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<VotiLezioniStudente> getVoti() {
        return voti;
    }

    public void setVoti(List<VotiLezioniStudente> voti) {
        this.voti = voti;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public BigInteger getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(BigInteger idLezione) {
        this.idLezione = idLezione;
    }

    public ArrayList<AssenzePerOra> getOreAssenze() {
        return oreAssenze;
    }

    public void setOreAssenze(ArrayList<AssenzePerOra> oreAssenze) {
        this.oreAssenze = oreAssenze;
        switch (oreAssenze.size()) {
            case 1:
                this.ora2Assenza = null;
                this.ora3Assenza = null;
                break;
            case 2:
                this.ora3Assenza = null;
                break;
            case 3:
                break;
        }
        for (AssenzePerOra a : oreAssenze) {
            switch (a.getOra()) {
                case 1:
                    this.ora1Assenza = a.isAssenza();
                    break;
                case 2:
                    this.ora2Assenza = a.isAssenza();
                    break;
                case 3:
                    this.ora3Assenza = a.isAssenza();
                    break;
            }
        }
    }

    public Long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(Long idStudente) {
        this.idStudente = idStudente;
    }

    public Date getRitiratoData() {
        return ritiratoData;
    }

    public void setRitiratoData(Date ritiratoData) {
        this.ritiratoData = ritiratoData;
    }

    public Date getDataEntrata() {
        return dataEntrata;
    }

    public void setDataEntrata(Date dataEntrata) {
        this.dataEntrata = dataEntrata;
    }
}
