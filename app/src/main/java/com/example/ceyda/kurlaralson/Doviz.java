package com.example.ceyda.kurlaralson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import java.util.List;

public class Doviz implements Serializable{
    @SerializedName("USD")
    @Expose
    private Usd usd;
    @SerializedName("EUR")
    @Expose
    private Usd eur;
    @SerializedName("GBP")
    @Expose
    private Usd gbp;
    @SerializedName("GA")
    @Expose
    private Usd ga;
    @SerializedName("C")
    @Expose
    private Usd c;
    @SerializedName("GAG")
    @Expose
    private Usd gag;
    @SerializedName("BTC")
    @Expose
    private Usd btc;
    @SerializedName("ETH")
    @Expose
    private Usd eth;

    public Doviz() {
        usd = new Usd();
        eur = new Usd();
        gbp = new Usd();
        ga = new Usd();
        c = new Usd();
        gag = new Usd();
        btc = new Usd();
        eth = new Usd();
    }

    public Usd getUsd() {
        return usd;
    }

    public void setUsd(Usd usd) {
        this.usd = usd;
    }

    public Usd getEur() {
        return eur;
    }

    public void setEur(Usd eur) {
        this.eur = eur;
    }

    public Usd getGbp() {
        return gbp;
    }

    public void setGbp(Usd gbp) {
        this.gbp = gbp;
    }

    public Usd getGa() {
        return ga;
    }

    public void setGa(Usd ga) {
        this.ga = ga;
    }

    public Usd getC() {
        return c;
    }

    public void setC(Usd c) {
        this.c = c;
    }

    public Usd getGag() {
        return gag;
    }

    public void setGag(Usd gag) {
        this.gag = gag;
    }

    public Usd getBtc() {
        return btc;
    }

    public void setBtc(Usd btc) {
        this.btc = btc;
    }

    public Usd getEth() {
        return eth;
    }

    public void setEth(Usd eth) {
        this.eth = eth;
    }

    public double calculateDifference(double oldValue, double newValue) {
        return newValue - oldValue;
    }


}
