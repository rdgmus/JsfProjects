/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.records;

import java.util.Date;

/**
 *
 * @author rdgmus
 */
public class RisultatiPeriodoRecord {

    private int registroIndex;
    private String cognome;
    private String nome;
    private Long idPeriodo;
    private Long idMateria;
    private Long idStudente;
    private Integer numAssenze;
    private Integer totaleOrePeriodo;
    private Double percentualeAssenzePeriodo;
    private Double giudizio;
    private String giudizioAsString;
    private Double scritto;
    private Double orale;
    private Double pratico;
    private Double condotta;
    private String condottaStr;
    private Integer countMaxNumGiudizio;
    private Integer countMaxNumScritto;
    private Integer countMaxNumOrale;
    private Integer countMaxNumPratico;
    private boolean insuffScritto;
    private boolean insuffOrale;
    private boolean insuffPratico;
    private boolean insuffGiudizio;
    private boolean insuffCondotta;
    private boolean recordWasSaved;
    private Date ritiratoData, dataEntrata;

    public RisultatiPeriodoRecord() {
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

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(Long idStudente) {
        this.idStudente = idStudente;
    }

    public Integer getNumAssenze() {
        return numAssenze;
    }

    public void setNumAssenze(Integer numAssenze) {
        this.numAssenze = numAssenze;
    }

    public Integer getTotaleOrePeriodo() {
        return totaleOrePeriodo;
    }

    public void setTotaleOrePeriodo(Integer totaleOrePeriodo) {
        this.totaleOrePeriodo = totaleOrePeriodo;
    }

    public Double getPercentualeAssenzePeriodo() {
        return percentualeAssenzePeriodo;
    }

    public void setPercentualeAssenzePeriodo(Double percentualeAssenzePeriodo) {
        this.percentualeAssenzePeriodo = percentualeAssenzePeriodo;
    }

    public Double getGiudizio() {
        return giudizio;
    }

    public void setGiudizio(Double giudizio) {
        setInsuffGiudizio(giudizio < 5.5);
        this.giudizio = giudizio;
    }

    public String getGiudizioAsString() {
        return giudizioAsString;
    }

    public void setGiudizioAsString(String giudizioAsString) {
        this.giudizioAsString = giudizioAsString;
    }

    public Double getScritto() {
        return scritto;
    }

    public void setScritto(Double scritto) {
        setInsuffScritto(scritto < 5.5);
        this.scritto = scritto;
    }

    public Double getOrale() {
        return orale;
    }

    public void setOrale(Double orale) {
        setInsuffOrale(orale < 5.5);
        this.orale = orale;
    }

    public Double getPratico() {
        return pratico;
    }

    public void setPratico(Double pratico) {
        setInsuffPratico(pratico < 5.5);
        this.pratico = pratico;
    }

    public Double getCondotta() {
        return condotta;
    }

    public void setCondotta(Double condotta) {
        if (condotta == null) {
            return;
        }
        setInsuffCondotta(condotta < 8.0);
        this.condotta = condotta;
    }

    public String getCondottaStr() {
        return condottaStr;
    }

    public void setCondottaStr(String condottaStr) {
        this.condottaStr = condottaStr;
    }

    
    public boolean isInsuffScritto() {
        return insuffScritto;
    }

    public void setInsuffScritto(boolean insuffScritto) {
        this.insuffScritto = insuffScritto;
    }

    public boolean isInsuffOrale() {
        return insuffOrale;
    }

    public void setInsuffOrale(boolean insuffOrale) {
        this.insuffOrale = insuffOrale;
    }

    public boolean isInsuffPratico() {
        return insuffPratico;
    }

    public void setInsuffPratico(boolean insuffPratico) {
        this.insuffPratico = insuffPratico;
    }

    public boolean isInsuffGiudizio() {
        return insuffGiudizio;
    }

    public void setInsuffGiudizio(boolean insuffGiudizio) {
        this.insuffGiudizio = insuffGiudizio;
    }

    public boolean isInsuffCondotta() {
        return insuffCondotta;
    }

    public void setInsuffCondotta(boolean insuffCondotta) {
        this.insuffCondotta = insuffCondotta;
    }

    public boolean isRecordWasSaved() {
        return recordWasSaved;
    }

    public void setRecordWasSaved(boolean recordWasSaved) {
        this.recordWasSaved = recordWasSaved;
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

    public Integer getCountMaxNumGiudizio() {
        return countMaxNumGiudizio;
    }

    public void setCountMaxNumGiudizio(Integer countMaxNumGiudizio) {
        this.countMaxNumGiudizio = countMaxNumGiudizio;
    }

    public Integer getCountMaxNumScritto() {
        return countMaxNumScritto;
    }

    public void setCountMaxNumScritto(Integer countMaxNumScritto) {
        this.countMaxNumScritto = countMaxNumScritto;
    }

    public Integer getCountMaxNumOrale() {
        return countMaxNumOrale;
    }

    public void setCountMaxNumOrale(Integer countMaxNumOrale) {
        this.countMaxNumOrale = countMaxNumOrale;
    }

    public Integer getCountMaxNumPratico() {
        return countMaxNumPratico;
    }

    public void setCountMaxNumPratico(Integer countMaxNumPratico) {
        this.countMaxNumPratico = countMaxNumPratico;
    }
}
