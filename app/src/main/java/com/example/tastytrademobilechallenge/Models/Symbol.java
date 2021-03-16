package com.example.tastytrademobilechallenge.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "symbols")

public class Symbol {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "symbolName", index = true)
    public String symbol;
    private double bidPrice;
    private double askPrice;
    private double lastPrice;
    private double openPrice;

    public Symbol(String symbol, double bidPrice, double askPrice, double lastPrice, double openPrice) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.lastPrice = lastPrice;
        this.openPrice = openPrice;
    }

    public boolean isPositive() {
        return this.lastPrice > this.openPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public double getLastPrice() {
        return lastPrice;
    }
}
