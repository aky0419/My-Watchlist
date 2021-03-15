package com.example.tastytrademobilechallenge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoricalDataModel {

    @SerializedName("close")
    @Expose
    private double close;
    @SerializedName("high")
    @Expose
    private double high;
    @SerializedName("low")
    @Expose
    private double low;
    @SerializedName("open")
    @Expose
    private double open;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("volume")
    @Expose
    private long volume;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("subkey")
    @Expose
    private String subkey;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("updated")
    @Expose
    private long updated;
    @SerializedName("changeOverTime")
    @Expose
    private double changeOverTime;
    @SerializedName("marketChangeOverTime")
    @Expose
    private double marketChangeOverTime;
    @SerializedName("uOpen")
    @Expose
    private double uOpen;
    @SerializedName("uClose")
    @Expose
    private double uClose;
    @SerializedName("uHigh")
    @Expose
    private double uHigh;
    @SerializedName("uLow")
    @Expose
    private double uLow;
    @SerializedName("uVolume")
    @Expose
    private long uVolume;
    @SerializedName("fOpen")
    @Expose
    private double fOpen;
    @SerializedName("fClose")
    @Expose
    private double fClose;
    @SerializedName("fHigh")
    @Expose
    private double fHigh;
    @SerializedName("fLow")
    @Expose
    private double fLow;
    @SerializedName("fVolume")
    @Expose
    private long fVolume;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("change")
    @Expose
    private double change;
    @SerializedName("changePercent")
    @Expose
    private double changePercent;

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSubkey() {
        return subkey;
    }

    public void setSubkey(String subkey) {
        this.subkey = subkey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public double getChangeOverTime() {
        return changeOverTime;
    }

    public void setChangeOverTime(double changeOverTime) {
        this.changeOverTime = changeOverTime;
    }

    public double getMarketChangeOverTime() {
        return marketChangeOverTime;
    }

    public void setMarketChangeOverTime(double marketChangeOverTime) {
        this.marketChangeOverTime = marketChangeOverTime;
    }

    public double getUOpen() {
        return uOpen;
    }

    public void setUOpen(double uOpen) {
        this.uOpen = uOpen;
    }

    public double getUClose() {
        return uClose;
    }

    public void setUClose(double uClose) {
        this.uClose = uClose;
    }

    public double getUHigh() {
        return uHigh;
    }

    public void setUHigh(double uHigh) {
        this.uHigh = uHigh;
    }

    public double getULow() {
        return uLow;
    }

    public void setULow(double uLow) {
        this.uLow = uLow;
    }

    public long getUVolume() {
        return uVolume;
    }

    public void setUVolume(long uVolume) {
        this.uVolume = uVolume;
    }

    public double getFOpen() {
        return fOpen;
    }

    public void setFOpen(double fOpen) {
        this.fOpen = fOpen;
    }

    public double getFClose() {
        return fClose;
    }

    public void setFClose(double fClose) {
        this.fClose = fClose;
    }

    public double getFHigh() {
        return fHigh;
    }

    public void setFHigh(double fHigh) {
        this.fHigh = fHigh;
    }

    public double getFLow() {
        return fLow;
    }

    public void setFLow(double fLow) {
        this.fLow = fLow;
    }

    public long getFVolume() {
        return fVolume;
    }

    public void setFVolume(long fVolume) {
        this.fVolume = fVolume;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

}