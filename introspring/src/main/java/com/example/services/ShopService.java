package com.example.services;

import java.util.ArrayList;

public class ShopService {
    private final ArrayList<String> shopList = new ArrayList<>();

    public void addItem(String item){
        shopList.add(item);
    }

    public ArrayList<String> getShopList(){
        return shopList;
    }

}
