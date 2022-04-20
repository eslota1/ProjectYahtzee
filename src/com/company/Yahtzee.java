package com.company;
import java.awt.Color;
import javax.swing.UIManager;

public class Yahtzee {
    private Dice[] dice = new Dice[5];

    private int countOfRolls = 0;
    private boolean arethereRolls = true;

    private static boolean[] scoreCanChange = new boolean[20];		// this one is to change at an earlier time than scoreIsSet. i.e scoreCanChange can e.g. false while score is set can be false too
    private boolean[] scoreIsSet = new boolean[20];					// to check when scoring so no values are duplicated.
    private int score_Count = 0;

    private int total_upper = 0;
    private int total_lower = 0;
    private int finalTotal_upper = 0;
    private int bonus = 0;
    private int finalTotal = 0;
    private boolean isthereBonus = false;

    public Yahtzee(){
        for(int i = 0; i < dice.length; i++){
            dice[i] = new Dice();
        }
    }


    public void restart(){
        countOfRolls = 0;
        arethereRolls = true;

        score_Count = 0;

        total_upper = 0;
        total_lower = 0;
        finalTotal_upper = 0;
        bonus = 0;
        finalTotal = 0;
        isthereBonus = false;

        resetDice();
        iniScoreCanChange();
        iniScoreIsSet();

        resetAllScores();
    }


    public int getFinalTotal(){
        return finalTotal;
    }


    public void resetDice(){
        for (int i = 0; i < dice.length; i++){
            dice[i].set_value(0);
            dice[i].set_KeepValue(false);
            YahtzeeGUI.getButtonDice()[i].setBackground(UIManager.getColor("Button.background"));
            YahtzeeGUI.getButtonDice()[i].setForeground(UIManager.getColor("Button.foreground"));
            YahtzeeGUI.getButtonDice()[i].setText("-");
        }
    }

    /**
     * Initiates the scoreCanChange array for all the score values.
     */
    public void iniScoreCanChange(){
        for(int i = 0 ; i < scoreCanChange.length; i++){
            scoreCanChange[i] = true;
        }

        scoreCanChange[0] = false;
        scoreCanChange[10] = false;
    }

    /**
     *
     * @return whether the score can change.
     */
    public boolean[] getScoreCanChange(){
        return scoreCanChange;
    }

    /**
     * Sets the state of a score button at the specified position
     *
     * @param i the position of the array
     * @param b the state of the score button
     */
    public void setScoreCanChange(int i, boolean b){
        scoreCanChange[i] = b;
    }

    /**
     * Initiates the ScoreIsSet array for each button.
     *
     */
    public void iniScoreIsSet(){
        for(int i = 0 ; i < scoreIsSet.length; i++){
            scoreIsSet[i] = false;
        }
    }

    /**
     * Checks if the score is set or not
     *
     * @return if the score is set.
     */
    public boolean[] getScoreIsSet(){
        return scoreIsSet;
    }


    public void setScore(int i, String score){

        int scoreInt = Integer.parseInt(score);
        YahtzeeGUI.setButtonScore(i, scoreInt);

        setScoreCanChange(i, false);
    }


    public void resetScoreButtons(){
        for (int i = 1; i < 7 ; i++){

            if (scoreCanChange[i]){
                YahtzeeGUI.getButtonScore()[i].setText("-");
            }
        }
        for (int i = 11; i < 18 ; i++){

            if (scoreCanChange[i]){
                YahtzeeGUI.getButtonScore()[i].setText("-");
            }
        }
    }

    public void updateTotal_Upper(){
        for (int i = 1 ; i < 7 ; i++){
            if (!scoreCanChange[i] && !scoreIsSet[i]){
                int scoreAddition = 0;

                String buttonText = YahtzeeGUI.getButtonScore()[i].getText();
                scoreAddition = Integer.parseInt(buttonText); //gets the text in the button converts it to int

                total_upper += scoreAddition; //adds that to the upper total.

                scoreIsSet[i] = true;
                score_Count++;

            }
        }

        YahtzeeGUI.setButtonScore(7, total_upper);
        YahtzeeGUI.getButtonScore()[7].setForeground(Color.black);
    }


    public void updateFinalTotal_Upper(){
        int bonus = 0;
        if (isthereBonus) {
            bonus = 63;
        }

        finalTotal_upper = total_upper + bonus;

        YahtzeeGUI.setButtonScore(9, finalTotal_upper);
        YahtzeeGUI.getButtonScore()[9].setForeground(Color.black);
    }

    public void updateTotal_Lower(){
        for (int i = 11 ; i < 18 ; i++){
            if (!scoreCanChange[i] && !scoreIsSet[i]){
                int scoreAddition = 0;

                String buttonText = YahtzeeGUI.getButtonScore()[i].getText();

                scoreAddition = Integer.parseInt(buttonText);

                total_lower += scoreAddition;

                scoreIsSet[i] = true;
                score_Count++;

            }
        }

        YahtzeeGUI.setButtonScore(18, total_lower);
        YahtzeeGUI.getButtonScore()[18].setForeground(Color.black);
    }

    public void updateBonus(){
        if (total_upper >= 63){
            YahtzeeGUI.setButtonScore(8, 35);
            YahtzeeGUI.getButtonScore()[8].setForeground(Color.black);
            isthereBonus = true;
        }
    }


    public void update_FinalTotal(){
        int bonus = 0;

        if (isthereBonus){
            bonus = 35;
        }

        finalTotal = total_lower + total_upper + bonus;

        YahtzeeGUI.setButtonScore(19, finalTotal);
        YahtzeeGUI.getButtonScore()[19].setForeground(Color.black);
    }

    public void resetAllScores(){
        for (int i = 1; i <= 9 ; i++ ){
            YahtzeeGUI.getButtonScore()[i].setText("-");
        }
        for (int i = 11; i <= 19 ; i++ ){
            YahtzeeGUI.getButtonScore()[i].setText("-");
        }

    }


    public int getRolls(){
        return countOfRolls;
    }
    public void increaseRolls(){
        countOfRolls++;
    }
    public void resetRolls(){
        countOfRolls = 0;
    }
    public void checkRolls(){
        if (countOfRolls >= 3){
            arethereRolls = false;
        } else {
            arethereRolls = true;
        }
    }

    public boolean ableToRoll(){
        return arethereRolls;
    }

    public Dice getDice(int i){
        return dice[i];
    }
    public Dice[] getDice(){
        return dice;
    }


    public int getDiceNumber(){
        return dice.length;
    }

    public void checkEndGame(){
        if (score_Count == 13){
            YahtzeeGUI.endGame();
        }
    }

    public static void main(String[] args) {
        new YahtzeeGUI();
    }

}