package com.company;
import java.util.Random;

public class Dice {

    private int value;
    private boolean keep_value = false;

    public void randomRoll(){
        Random roll = new Random();
        value = roll.nextInt(6) + 1;
    }

    public void hold(){
        keep_value = true;
    }

    public void set_KeepValue(boolean value){
        this.keep_value = value;
    }
    public boolean get_KeepValue(){
        return keep_value;
    }


    public int get_value(){
        return value;
    }
    public void set_value(int value){
        this.value = value;
    }
}