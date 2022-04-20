package com.company;

import com.company.Scoring;
import com.company.Yahtzee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;


public class YahtzeeGUI extends JFrame{

    private static JFrame frame;
    private JPanel panel, dicePanel, mainPanel, scorePanel, scoreBtnPanel, helpPanel, settingsPanel;

    private JButton btnRoll;
    private static JButton[] btnDice = new JButton[5];

    private JLabel[] scoreLabel = new JLabel[20];
    private static JButton[] btnScore = new JButton[20];

    private Scoring scoring = new Scoring();

    private int rBackground = 100, gBackground = 150, bBackground = 12, rForeground = 255, gForeground = 255, bForeground = 255;

    private File helpFile;

    private static Yahtzee game = new Yahtzee();
    public static Yahtzee getGame(){
        return game;
    }


    public YahtzeeGUI(){
        createWindow(355,620);
        addButtonRoll();
        addButtonDice();
        addMainPanel();
        ScoreNames();   // adding score names in
        addScoreButtons();

        frame.add(panel);
        frame.setVisible(true);
    }

    public void createWindow(int width, int height){
        frame = new JFrame();
        frame.setTitle("Yahtzee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        panel = new JPanel(new BorderLayout());
        dicePanel = new JPanel();
        mainPanel = new JPanel();
        scorePanel = new JPanel();
        scoreBtnPanel = new JPanel();

    }


    public void addMainPanel(){
        mainPanel.setLayout(new BorderLayout());
        panel.add(mainPanel, BorderLayout.CENTER);
    }

    public void ScoreNames(){
        scoreLabel[0] = new JLabel("UPPER SECTION  ");
        scoreLabel[1] = new JLabel("Aces  ");
        scoreLabel[2] = new JLabel("Twos  ");
        scoreLabel[3] = new JLabel("Threes  ");
        scoreLabel[4] = new JLabel("Fours  ");
        scoreLabel[5] = new JLabel("Fives  ");
        scoreLabel[6] = new JLabel("Sixes  ");
        scoreLabel[7] = new JLabel("TOTAL SCORE  ");
        scoreLabel[8] = new JLabel("BONUS  ");
        scoreLabel[9] = new JLabel("TOTAL UPPER  ");
        scoreLabel[10] = new JLabel("LOWER SECTION  ");
        scoreLabel[11] = new JLabel("3 of a Kind  ");
        scoreLabel[12] = new JLabel("4 of a Kind  ");
        scoreLabel[13] = new JLabel("Full House  ");
        scoreLabel[14] = new JLabel("Small Straight  ");
        scoreLabel[15] = new JLabel("Large Straight  ");
        scoreLabel[16] = new JLabel("Yahtzee!  ");
        scoreLabel[17] = new JLabel("Chance  ");
        scoreLabel[18] = new JLabel("TOTAL LOWER  ");
        scoreLabel[19] = new JLabel("GRAND TOTAL  ");

        mainPanel.add(scorePanel, BorderLayout.WEST);
        scorePanel.setLayout(new BoxLayout(scorePanel,BoxLayout.Y_AXIS));
        scorePanel.setBackground(new Color(rBackground,gBackground,bBackground));

        for(int i = 0; i < scoreLabel.length; i++){
            scoreLabel[i].setHorizontalAlignment(SwingConstants.RIGHT);
            scoreLabel[i].setPreferredSize(new Dimension(150,50));
            scoreLabel[i].setMaximumSize(new Dimension(150,50));
            scoreLabel[i].setForeground(new Color(rForeground,gForeground,bForeground));
            scorePanel.add(scoreLabel[i]);
        }
    }

    public void addScoreButtons(){

        mainPanel.add(scoreBtnPanel, BorderLayout.CENTER);
        scoreBtnPanel.setLayout(new BoxLayout(scoreBtnPanel,BoxLayout.Y_AXIS));
        scoreBtnPanel.setBackground(new Color(rBackground,gBackground,bBackground));
        game.iniScoreCanChange();

        for (int i = 0; i < btnScore.length; i++){

            btnScore[i] = new JButton("-");
            btnScore[i].addActionListener(new ScoreHandler());
            btnScore[i].setMaximumSize(new Dimension(200,50));
            scoreBtnPanel.add (btnScore[i]);
        }

        btnScore[0].setText("SCORE");
        btnScore[0].setBackground(null);
        btnScore[0].setForeground(Color.WHITE);

        btnScore[10].setText("SCORE");
        btnScore[10].setBackground(null);
        btnScore[10].setForeground(Color.WHITE);
    }

    public void addButtonRoll(){
        btnRoll = new JButton ("Roll the Dice");
        btnRoll.addActionListener(new RollHandler());

        dicePanel.setBackground(new Color(rBackground,gBackground,bBackground));
        dicePanel.add (btnRoll);
        panel.add(dicePanel, BorderLayout.SOUTH);
    }

    public void addButtonDice(){    // creating dice

        for (int i = 0; i < btnDice.length; i++){
            btnDice[i] = new JButton("-");
            btnDice[i].addActionListener(new HoldHandler());
            btnDice[i].setPreferredSize(new Dimension(41,26));
            dicePanel.add (btnDice[i]);
        }
        panel.add(dicePanel, BorderLayout.SOUTH);
    }

    public static JButton[] getButtonScore(){
        return btnScore;
    }

    public JButton getButtonScore(int i) {
        return btnScore[i];
    }

    public static void setButtonScore(int scorePosition, int score) {

        if (!String.valueOf(btnScore[scorePosition]).equals("0")
                && game.getScoreCanChange()[scorePosition]){ // asks if btnScore[i] != 0 and if the score can change
            btnScore[scorePosition].setText(String.valueOf(score));
            btnScore[scorePosition].setForeground(Color.BLUE);

        }

    }

    public static JButton[] getButtonDice(){
        return btnDice;
    }

    public static int getButtonDiceValue(int i){
        return Integer.valueOf(btnDice[i].getText());
    }

    public void RollAlert(){
        JOptionPane.showMessageDialog(frame, "You need to pick a score before rolling again", "Pick a score", 1);

    }

    public static void endGame() {
        JOptionPane.showMessageDialog(frame, "You have completed this game of Yahtzee. Your score is: " + game.getFinalTotal(), "Well done!", 1);
        restartDialog();
    }

    public static void restartDialog(){
        String[] options = {"Restart", "Close Game"};

        int selection = JOptionPane.showOptionDialog(frame, "Would you like to restart the game?", "Restart", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (selection == 0){
            game.restart();
        } else if (selection == 1){
            System.exit(0);
        }

    }


    public void setGameBackground(){

        scorePanel.setBackground(new Color(rBackground,gBackground,bBackground));
        scoreBtnPanel.setBackground(new Color(rBackground,gBackground,bBackground));
        dicePanel.setBackground(new Color(rBackground,gBackground,bBackground));
        settingsPanel.setBackground(new Color(rBackground,gBackground,bBackground));

        for (int i = 0 ; i < scoreLabel.length; i++){
            scoreLabel[i].setForeground(new Color(rForeground,gForeground,bForeground));
        }

        btnScore[0].setForeground(new Color(rForeground,gForeground,bForeground));
        btnScore[10].setForeground(new Color(rForeground,gForeground,bForeground));

    }


    public void rollMessage(){
        JOptionPane.showMessageDialog(frame, "Please roll before selecting a Score", "Roll The Dice", 1);
    }

    public void holdMessage(){
        JOptionPane.showMessageDialog(frame, "Please roll before holding the dice", "Roll The Dice", 1);
    }


    class ScoreHandler implements ActionListener {
        public void actionPerformed (ActionEvent event){

            Object source = event.getSource(); // this gets the source so I can get the value from it.

            if (game.getRolls() == 0){
                rollMessage();
            } else {

                for(int i = 1; i < 7; i++){

                    if(source == btnScore[i] && game.getScoreCanChange()[i] && !game.getScoreIsSet()[i]){
                        game.setScore(i, ((AbstractButton) source).getText());
                        btnScore[i].setForeground(new Color(100,150,12));

                        game.updateTotal_Upper();
                        game.updateBonus();
                        game.updateFinalTotal_Upper();
                        game.update_FinalTotal();
                        game.resetRolls();
                        game.resetScoreButtons();
                        game.resetDice();
                        break;
                    }
                }
                for(int i = 10; i < 18; i++){
                    if(source == btnScore[i] && game.getScoreCanChange()[i] && !game.getScoreIsSet()[i]){
                        game.setScore(i, ((AbstractButton) source).getText());
                        btnScore[i].setForeground(new Color(100,150,12));

                        game.updateTotal_Lower();
                        game.update_FinalTotal();
                        game.resetRolls();
                        game.resetScoreButtons();
                        game.resetDice();
                        break;
                    }
                }
                game.checkEndGame();
            }
        }
    }

    class RollHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            game.checkRolls();

            if (game.ableToRoll() == false){
                RollAlert();
            } else {
                game.increaseRolls();


                for(int i = 0; i < game.getDiceNumber(); i++){
                    if (game.getDice(i).get_KeepValue() != true){
                        game.getDice(i).randomRoll();
                        btnDice[i].setText(String.valueOf(game.getDice(i).get_value()));
                    }
                }

                scoring.isthereAces();
                scoring.isthereTwos();
                scoring.isthereThrees();
                scoring.isthereFours();
                scoring.isthereFives();
                scoring.isthereSixes();
                scoring.isthere3OAK();
                scoring.isthere4OAK();
                scoring.isthereFullHouse();
                scoring.isthereSmallStraight();
                scoring.isthereLargeStraight();
                scoring.isthereYahtzee();
                scoring.isthereChance();
            }


        }
    }

    class HoldHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {


            if (game.getRolls() == 0){
                holdMessage();
            } else{
                for (int i = 0; i < btnDice.length; i++){
                    if (event.getSource()== btnDice[i]){ // Picks the pressed button after running through them all.
                        if (game.getDice(i).get_KeepValue() == false){
                            game.getDice(i).set_KeepValue(true);
                            btnDice[i].setBackground(new Color(156,0,0));
                            btnDice[i].setForeground(new Color(255,255,255));
                        } else if (game.getDice(i).get_KeepValue() == true){
                            game.getDice(i).set_KeepValue(false);
                            btnDice[i].setBackground(UIManager.getColor("Button.background"));
                            btnDice[i].setForeground(UIManager.getColor("Button.foreground"));
                        }
                    }
                }
            }
        }
    }
}
