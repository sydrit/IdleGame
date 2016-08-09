package com.example.conrad.baseidlegame;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Conrad on 7/28/2016.
 */
public class Bank {
    private long mBalance = 0;
    private float mClickMultiplier = 1;
    private float mTimeMultiplier = 0;






    public void updateTimeMultiplier(double multiplier){
        mTimeMultiplier += multiplier;
    }

    public void updateBalance(double amount){
        mBalance += amount;
    }

    public long getBalance() {
        return mBalance;
    }

    public void setBalance(long balance) {
        mBalance = balance;
    }

    public float getClickMultiplier() {
        return mClickMultiplier;
    }

    public void setClickMultiplier(float clickMultiplier) {
        mClickMultiplier = clickMultiplier;
    }

    public float getTimeMultiplier() {
        return mTimeMultiplier;
    }

    public void setTimeMultiplier(float timeMultiplier) {
        mTimeMultiplier = timeMultiplier;
    }


}
