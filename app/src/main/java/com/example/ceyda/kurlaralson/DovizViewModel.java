package com.example.ceyda.kurlaralson;

import java.io.Serializable;

public class DovizViewModel implements Serializable {
    private String Sembol;
    private double Alis;
    private double Satis;
    private double YFark;
    private double AlisFark;
    private  double SatisFark;

    private double Degisim;

    public DovizViewModel(String sembol, double alis, double satis, double YFark, double alisFark, double satisFark, double degisim) {
        Sembol = sembol;
        Alis = alis;
        Satis = satis;
        this.YFark = YFark;
        AlisFark = alisFark;
        SatisFark = satisFark;
        Degisim = degisim;
    }

    public String getSembol() {
        return Sembol;
    }

    public void setSembol(String sembol) {
        Sembol = sembol;
    }

    public double getAlis() {
        return Alis;
    }

    public void setAlis(double alis) {
        Alis = alis;
    }

    public double getSatis() {
        return Satis;
    }

    public void setSatis(double satis) {
        Satis = satis;
    }

    public double getYFark() {
        return YFark;
    }

    public void setYFark(double YFark) {
        this.YFark = YFark;
    }

    public double getAlisFark() {
        return AlisFark;
    }

    public void setAlisFark(double alisFark) {
        AlisFark = alisFark;
    }

    public double getSatisFark() {
        return SatisFark;
    }

    public void setSatisFark(double satisFark) {
        SatisFark = satisFark;
    }

    public double getDegisim() {
        return Degisim;
    }

    public void setDegisim(double degisim) {
        Degisim = degisim;
    }
}
