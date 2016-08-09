package com.example.conrad.baseidlegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    long UPGRADE_PRICE_1 = 10;
    long UPGRADE_BONUS_1 = 5;
    long UPGRADE_PRICE_2 = 10000;
    long UPGRADE_BONUS_2 = 150;
    long UPGRADE_PRICE_3 = 100000;
    long UPGRADE_BONUS_3 = 1000;
    long UPGRADE_PRICE_4 = 5000000;
    long UPGRADE_BONUS_4 = 25000;
    long UPGRADE_PRICE_5 = 10000000;
    long UPGRADE_BONUS_5 = 100000;
    long UPGRADE_PRICE_6 = 100000000;
    long UPGRADE_BONUS_6 = 10000000;

    Bank mBank = new Bank();
    static final String FILE_NAME = "idledatasave";
    TextView mBalanceView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button reset;
    private Upgrade mUpgrade1 = new Upgrade();
    private Upgrade mUpgrade2 = new Upgrade();
    private Upgrade mUpgrade3 = new Upgrade();
    private Upgrade mUpgrade4 = new Upgrade();
    private Upgrade mUpgrade5 = new Upgrade();
    private Upgrade mUpgrade6 = new Upgrade();
    TextView mBonusView1;
    TextView mBonusView2;
    TextView mBonusView3;
    TextView mBonusView4;
    TextView mBonusView5;
    TextView mBonusView6;

    double currentPrice =10;
    double multiplier = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBalanceView = (TextView) findViewById(R.id.balanceView);
        mBalanceView.setText("$" + Long.toString(mBank.getBalance()) + "\nCurrent Rate: $"
                + Long.toString((long)mBank.getTimeMultiplier()) + "/s\nTAP UP HERE TO GET A DOLLAR!!!");
        mBalanceView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                click();
            }
        });
        int initialDelay = 10;
        int period = 1000;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Message msg = new Message();
                mHandler.sendMessage(msg);
            }
        };
        timer.scheduleAtFixedRate(task, initialDelay, period);



        button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               tapUpgrade(mUpgrade1, mBonusView1, button1);

            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tapUpgrade(mUpgrade2, mBonusView2, button2);
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tapUpgrade(mUpgrade3, mBonusView3, button3);
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tapUpgrade(mUpgrade4, mBonusView4, button4);
            }
        });

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tapUpgrade(mUpgrade5, mBonusView5, button5);

            }
        });

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tapUpgrade(mUpgrade6, mBonusView6, button6);
            }
        });
        reset = (Button) findViewById(R.id.button7);
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clearData();
            }
        });


    }
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            long balance = mBank.getBalance();
            balance += mBank.getTimeMultiplier();
            mBank.setBalance(balance);
            mBalanceView.setText("$" + Long.toString(mBank.getBalance()) + "\nCurrent Rate: $"
            + Long.toString((long)mBank.getTimeMultiplier()) + "/s\nTAP UP HERE TO GET A DOLLAR!!!");
        }
    };
    public void tapUpgrade(Upgrade upgrade, TextView view, Button button){
        if(mBank.getBalance() >= upgrade.getPrice()){
            mBank.updateTimeMultiplier(upgrade.getBonus());
            mBank.updateBalance(-(upgrade.getPrice()));
            upgrade.setPrice(Math.pow(upgrade.getPrice(), 1.2));
            upgrade.setBonus(Math.pow(upgrade.getBonus(), 1.2));
            button.setText("Price: $" + Long.toString((long)upgrade.getPrice()));
            view.setText("Gain $" + Long.toString((long)upgrade.getBonus()) + "/s");

        }
    }

    public void click(){
        long balance = mBank.getBalance();
        balance += mBank.getClickMultiplier();
        mBank.setBalance(balance);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GregorianCalendar calendar = new GregorianCalendar();
        long time = calendar.getTimeInMillis();

        SharedPreferences data = getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = data.edit();
        editor.putLong("time", time);
        editor.putLong("balance", mBank.getBalance());
        editor.putFloat("timeMultiplier", mBank.getTimeMultiplier());
        editor.putFloat("clickMultiplier", mBank.getClickMultiplier());
        editor.putLong("upgrade1Price", (long)mUpgrade1.getPrice());
        editor.putLong("upgrade1Bonus", (long)mUpgrade1.getBonus());
        editor.putLong("upgrade2Price", (long)mUpgrade2.getPrice());
        editor.putLong("upgrade2Bonus", (long)mUpgrade2.getBonus());
        editor.putLong("upgrade3Price", (long)mUpgrade3.getPrice());
        editor.putLong("upgrade3Bonus", (long)mUpgrade3.getBonus());
        editor.putLong("upgrade4Price", (long)mUpgrade4.getPrice());
        editor.putLong("upgrade4Bonus", (long)mUpgrade4.getBonus());
        editor.putLong("upgrade5Price", (long)mUpgrade5.getPrice());
        editor.putLong("upgrade5Bonus", (long)mUpgrade5.getBonus());
        editor.putLong("upgrade6Price", (long)mUpgrade6.getPrice());
        editor.putLong("upgrade6Bonus", (long)mUpgrade6.getBonus());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GregorianCalendar calendar = new GregorianCalendar();
        long time = calendar.getTimeInMillis();
        SharedPreferences data = getSharedPreferences(FILE_NAME, 0);
        time = (time - data.getLong("time",1)) / 1000;

        mBank.setBalance(data.getLong("balance", 0));
        mBank.setTimeMultiplier(data.getFloat("timeMultiplier", 0));
        mBank.setClickMultiplier(data.getFloat("clickMultiplier", 1));
        if(time >= 18000){
            time = 18000;
        }

        long balance = mBank.getBalance();
        Toast.makeText(this, "You earned $" + Long.toString((long)(time * mBank.getTimeMultiplier())) + " while you were away.", Toast.LENGTH_LONG).show();
        balance += (time * mBank.getTimeMultiplier());
        mBank.setBalance(balance);

        mUpgrade1.setPrice(data.getLong("upgrade1Price", UPGRADE_PRICE_1));
        mUpgrade1.setBonus(data.getLong("upgrade1Bonus", UPGRADE_BONUS_1));
        mUpgrade2.setPrice(data.getLong("upgrade2Price", UPGRADE_PRICE_2));
        mUpgrade2.setBonus(data.getLong("upgrade2Bonus", UPGRADE_BONUS_2));
        mUpgrade3.setPrice(data.getLong("upgrade3Price", UPGRADE_PRICE_3));
        mUpgrade3.setBonus(data.getLong("upgrade3Bonus", UPGRADE_BONUS_3));
        mUpgrade4.setPrice(data.getLong("upgrade4Price", UPGRADE_PRICE_4));
        mUpgrade4.setBonus(data.getLong("upgrade4Bonus", UPGRADE_BONUS_4));
        mUpgrade5.setPrice(data.getLong("upgrade5Price", UPGRADE_PRICE_5));
        mUpgrade5.setBonus(data.getLong("upgrade5Bonus", UPGRADE_BONUS_5));
        mUpgrade6.setPrice(data.getLong("upgrade6Price", UPGRADE_PRICE_6));
        mUpgrade6.setBonus(data.getLong("upgrade6Bonus", UPGRADE_BONUS_6));
        button1.setText("Price: $" + Long.toString((long)mUpgrade1.getPrice()));
        button2.setText("Price: $" + Long.toString((long)mUpgrade2.getPrice()));
        button3.setText("Price: $" + Long.toString((long)mUpgrade3.getPrice()));
        button4.setText("Price: $" + Long.toString((long)mUpgrade4.getPrice()));
        button5.setText("Price: $" + Long.toString((long)mUpgrade5.getPrice()));
        button6.setText("Price: $" + Long.toString((long)mUpgrade6.getPrice()));
        mBonusView1 = (TextView) findViewById(R.id.textView);
        mBonusView2 = (TextView) findViewById(R.id.textView2);
        mBonusView3 = (TextView) findViewById(R.id.textView3);
        mBonusView4 = (TextView) findViewById(R.id.textView4);
        mBonusView5 = (TextView) findViewById(R.id.textView5);
        mBonusView6 = (TextView) findViewById(R.id.textView6);
        mBonusView1.setText("Gain $" + Long.toString((long)mUpgrade1.getBonus()) + "/s");
        mBonusView2.setText("Gain $" + Long.toString((long)mUpgrade2.getBonus()) + "/s");
        mBonusView3.setText("Gain $" + Long.toString((long)mUpgrade3.getBonus()) + "/s");
        mBonusView4.setText("Gain $" + Long.toString((long)mUpgrade4.getBonus()) + "/s");
        mBonusView5.setText("Gain $" + Long.toString((long)mUpgrade5.getBonus()) + "/s");
        mBonusView6.setText("Gain $" + Long.toString((long)mUpgrade6.getBonus()) + "/s");

    }

    public void clearData(){
        SharedPreferences data = getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.commit();
        mBank.setBalance(0);
        mBank.setTimeMultiplier(0);
        mBank.setClickMultiplier(1);
        mUpgrade1.setPrice(UPGRADE_PRICE_1);
        mUpgrade1.setBonus(UPGRADE_BONUS_1);
        mUpgrade2.setPrice(UPGRADE_PRICE_2);
        mUpgrade2.setBonus(UPGRADE_BONUS_2);
        mUpgrade3.setPrice(UPGRADE_PRICE_3);
        mUpgrade3.setBonus(UPGRADE_BONUS_3);
        mUpgrade4.setPrice(UPGRADE_PRICE_4);
        mUpgrade4.setBonus(UPGRADE_BONUS_4);
        mUpgrade5.setPrice(UPGRADE_PRICE_5);
        mUpgrade5.setBonus(UPGRADE_BONUS_5);
        mUpgrade6.setPrice(UPGRADE_PRICE_6);
        mUpgrade6.setBonus(UPGRADE_BONUS_6);
        mBalanceView.setText("$" + Long.toString(mBank.getBalance()) + "\nCurrent Rate: $"
                + Long.toString((long)mBank.getTimeMultiplier()) + "/s\nTAP UP HERE TO GET A DOLLAR!!!");
        button1.setText("Price: $" + Long.toString((long)mUpgrade1.getPrice()));
        button2.setText("Price: $" + Long.toString((long)mUpgrade2.getPrice()));
        button3.setText("Price: $" + Long.toString((long)mUpgrade3.getPrice()));
        button4.setText("Price: $" + Long.toString((long)mUpgrade4.getPrice()));
        button5.setText("Price: $" + Long.toString((long)mUpgrade5.getPrice()));
        button6.setText("Price: $" + Long.toString((long)mUpgrade6.getPrice()));
        mBonusView1.setText("Gain $" + Long.toString((long)mUpgrade1.getBonus()) + "/s");
        mBonusView2.setText("Gain $" + Long.toString((long)mUpgrade2.getBonus()) + "/s");
        mBonusView3.setText("Gain $" + Long.toString((long)mUpgrade3.getBonus()) + "/s");
        mBonusView4.setText("Gain $" + Long.toString((long)mUpgrade4.getBonus()) + "/s");
        mBonusView5.setText("Gain $" + Long.toString((long)mUpgrade5.getBonus()) + "/s");
        mBonusView6.setText("Gain $" + Long.toString((long)mUpgrade6.getBonus()) + "/s");
    }
}
