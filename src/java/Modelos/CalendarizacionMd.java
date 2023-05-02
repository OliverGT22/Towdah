/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author kevin
 */
public class CalendarizacionMd {
    private String id;
    private String Dia;
    private String Hora;
    private String Alu;
    private String Inst;
    private String Cat;

    public CalendarizacionMd() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String Dia) {
        this.Dia = Dia;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public String getAlu() {
        return Alu;
    }

    public void setAlu(String Alu) {
        this.Alu = Alu;
    }

    public String getInst() {
        return Inst;
    }

    public void setInst(String Inst) {
        this.Inst = Inst;
    }

    public String getCat() {
        return Cat;
    }

    public void setCat(String Cat) {
        this.Cat = Cat;
    }

}
