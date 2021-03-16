package com.example.tastytrademobilechallenge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SymbolAutocompleteModel {

    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("cik")
    @Expose
    private String cik;
    @SerializedName("securityName")
    @Expose
    private String securityName;
    @SerializedName("securityType")
    @Expose
    private String securityType;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("exchange")
    @Expose
    private String exchange;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("currency")
    @Expose
    private String currency;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCik() {
        return cik;
    }

    public void setCik(String cik) {
        this.cik = cik;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}