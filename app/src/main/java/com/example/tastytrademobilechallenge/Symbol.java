package com.example.tastytrademobilechallenge;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (tableName = "symbols")

public class Symbol {

    @PrimaryKey (autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "symbolName", index = true)
    public String symbol;
    private double bidPrice;
    private double askPrice;
    private double lastPrice;

    public Symbol(String symbol, double bidPrice, double askPrice, double lastPrice) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.lastPrice = lastPrice;
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
