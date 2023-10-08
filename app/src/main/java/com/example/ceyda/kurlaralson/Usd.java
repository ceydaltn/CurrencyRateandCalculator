package com.example.ceyda.kurlaralson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usd implements Serializable {
    @SerializedName("satis")
    @Expose
    private double satis;
    @SerializedName("alis")
    @Expose
    private double alis;
    @SerializedName("degisim")
    @Expose
    private double degisim;
    @SerializedName("d_oran")
    @Expose
    private String dOran;
    @SerializedName("d_yon")
    @Expose
    private String dYon;

    public double getSatis() {
        return satis;
    }

    public void setSatis(double satis) {
        this.satis = satis;
    }

    public double getAlis() {
        return alis;
    }

    public void setAlis(double alis) {
        this.alis = alis;
    }

    public double getDegisim() {
        return degisim;
    }

    public void setDegisim(double degisim) {
        this.degisim = degisim;
    }

    public String getdOran() {
        return dOran;
    }

    public void setdOran(String dOran) {
        this.dOran = dOran;
    }

    public String getdYon() {
        return dYon;
    }

    public void setdYon(String dYon) {
        this.dYon = dYon;
    }

}
