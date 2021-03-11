package com.example.tastytrademobilechallenge.DataBase;

public class StockModel {
    String listName;
    String symbol;
    float bidPrice;
    float askPrice;
    float lastPrice;

    public StockModel(String listName, String symbol, float bidPri, float askPrice, float lastPrice) {
        this.listName = listName;
        this.symbol = symbol;
        this.bidPrice = bidPri;
        this.askPrice = askPrice;
        this.lastPrice = lastPrice;
    }

    public StockModel() {
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(float bidPri) {
        this.bidPrice = bidPri;
    }

    public float getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(float askPrice) {
        this.askPrice = askPrice;
    }

    public float getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(float lastPrice) {
        this.lastPrice = lastPrice;
    }
}
