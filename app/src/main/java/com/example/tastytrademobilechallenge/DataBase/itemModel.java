package com.example.tastytrademobilechallenge.DataBase;

public class itemModel {
    String listName;
    String symbol;

    public itemModel(String listName, String symbol) {
        this.listName = listName;
        this.symbol = symbol;
    }

    public itemModel() {
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

    @Override
    public String toString() {
        return "itemModel{" +
                "listName='" + listName + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
