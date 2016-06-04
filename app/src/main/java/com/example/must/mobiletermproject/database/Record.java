package com.example.must.mobiletermproject.database;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by must on 03.06.2016.
 */
public class Record implements Serializable{
    //kaydedilecek özellikler
    private double enlem;
    private double boylam;
    private int sinyalGucu;
    private String operatorAdi;
    private Date zaman; //sqlite ile kaydedilirken string e cast edilecek

    //constructor
    public Record(double enlem, double boylam, int sinyalGucu, Date zaman, String operatorAdi) {
        this.enlem = enlem;
        this.boylam = boylam;
        this.sinyalGucu = sinyalGucu;
        this.zaman = zaman;
        this.operatorAdi = operatorAdi;
    }

    public Record(){
        //non-args constructor
    }

    //setter metotlar
    public void setZaman(Date zaman) {
        this.zaman = zaman;
    }

    public void setOperatorAdi(String operatorAdi) {
        this.operatorAdi = operatorAdi;
    }

    public void setSinyalGucu(int sinyalGucu) {
        this.sinyalGucu = sinyalGucu;
    }

    public void setBoylam(double boylam) {
        this.boylam = boylam;
    }

    public void setEnlem(double enlem) {
        this.enlem = enlem;
    }

    //getter metotlar
    public double getBoylam() {
        return boylam;
    }

    public double getEnlem() {
        return enlem;
    }

    public int getSinyalGucu() {
        return sinyalGucu;
    }

    public Date getZaman() {
        return zaman;
    }

    public String getOperatorAdi() {
        return operatorAdi;
    }
}
