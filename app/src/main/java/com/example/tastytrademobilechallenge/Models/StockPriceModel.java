package com.example.tastytrademobilechallenge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockPriceModel {

    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("securityType")
    @Expose
    private String securityType;
    @SerializedName("bidPrice")
    @Expose
    private long bidPrice;
    @SerializedName("bidSize")
    @Expose
    private long bidSize;
    @SerializedName("askPrice")
    @Expose
    private long askPrice;
    @SerializedName("askSize")
    @Expose
    private long askSize;
    @SerializedName("lastUpdated")
    @Expose
    private long lastUpdated;
    @SerializedName("lastSalePrice")
    @Expose
    private double lastSalePrice;
    @SerializedName("lastSaleSize")
    @Expose
    private long lastSaleSize;
    @SerializedName("lastSaleTime")
    @Expose
    private long lastSaleTime;
    @SerializedName("volume")
    @Expose
    private long volume;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public long getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(long bidPrice) {
        this.bidPrice = bidPrice;
    }

    public long getBidSize() {
        return bidSize;
    }

    public void setBidSize(long bidSize) {
        this.bidSize = bidSize;
    }

    public long getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(long askPrice) {
        this.askPrice = askPrice;
    }

    public long getAskSize() {
        return askSize;
    }

    public void setAskSize(long askSize) {
        this.askSize = askSize;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getLastSalePrice() {
        return lastSalePrice;
    }

    public void setLastSalePrice(double lastSalePrice) {
        this.lastSalePrice = lastSalePrice;
    }

    public long getLastSaleSize() {
        return lastSaleSize;
    }

    public void setLastSaleSize(long lastSaleSize) {
        this.lastSaleSize = lastSaleSize;
    }

    public long getLastSaleTime() {
        return lastSaleTime;
    }

    public void setLastSaleTime(long lastSaleTime) {
        this.lastSaleTime = lastSaleTime;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

}