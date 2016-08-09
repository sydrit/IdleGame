package com.example.conrad.baseidlegame;

/**
 * Created by Conrad on 7/30/2016.
 */
public class Upgrade {
    double mPrice = 0;
    double mBonus = 0;

    public Upgrade(){

    }
    public Upgrade(long price, double bonus){
        mBonus = bonus;
        mPrice = price;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public double getBonus() {
        return mBonus;
    }

    public void setBonus(double bonus) {
        mBonus = bonus;
    }
}
