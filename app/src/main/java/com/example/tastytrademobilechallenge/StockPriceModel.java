package com.example.tastytrademobilechallenge;

class StockPriceModel {

    String symbol;
    float bidPrice;
    float askPrice;
    float lastPrice;

    public StockPriceModel() {
    }

    public StockPriceModel(String symbol, float bidPrice, float askPrice, float lastPrice) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.lastPrice = lastPrice;
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

    public void setBidPrice(float bidPrice) {
        this.bidPrice = bidPrice;
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

    @Override
    public String toString() {
        return "StockPriceModel{" +
                "symbol='" + symbol + '\'' +
                ", bidPrice=" + bidPrice +
                ", askPrice=" + askPrice +
                ", lastPrice=" + lastPrice +
                '}';
    }
}
