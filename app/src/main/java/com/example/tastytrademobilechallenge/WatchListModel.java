package com.example.tastytrademobilechallenge;

import com.example.tastytrademobilechallenge.Models.StockPriceModel;

import java.util.HashMap;
import java.util.List;

class WatchListModel {
    String listName;
    List<String> symbols;
    HashMap<String, StockPriceModel> symbolPrice;

    public WatchListModel() {
    }

    public WatchListModel(String listName, List<String> symbols) {
        this.listName = listName;
        this.symbols = symbols;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return "WatchListModel{" +
                "listName='" + listName + '\'' +
                ", symbols=" + symbols +
                '}';
    }
}
