/**
 * Created by Alexander on 1/7/2017.
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextArea result = new JTextArea(1, 20);
    private JButton[] buttons = new JButton[19];
    private String[] buttonDisplay = {"7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", ".", "/", "C", "sqrt", "+/-", "=", "0"};
    private JPanel[] buttonSetter = new JPanel[5];
    private JMenuBar menu;
    private JMenu help;
    private JMenu about;
    private NumericOperations nuop = new NumericOperations();
    private int[] dimW = {300,45,100,90};
    private int[] dimH = {35, 40};
    private Dimension displayDimension = new Dimension(dimW[0], dimH[0]);
    private Dimension regularDimension = new Dimension(dimW[1], dimH[1]);
    private Dimension rColumnDimension = new Dimension(dimW[2], dimH[1]);
    private Dimension zeroButDimension = new Dimension(dimW[3], dimH[1]);
    private boolean[] function = new boolean[4];
    private double[] temporary = {0, 0};

    public Calculator(){
        initializeGUI();
    }

    private void initializeGUI(){
        //Setting the frame
        setTitle("Basic Calculator by alex_andros14");
        setResizable(false);
        setSize(390, 290);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout buttonGrid = new GridLayout(5,5);
        setLayout(buttonGrid);
        setVisible(true);

        for (int i=0; i<4; i++){
            function[i] = false;
        }

        //Setting the menu
        /*menu = new JMenuBar();
        help = new JMenu("Help");
        about = new JMenu("About...");
        menu.add(help);
        menu.add(about);
        menu.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(menu);*/

        //Setting the text field where the result will be shown
        result.setFont(new Font("Tahoma", Font.BOLD, 17));
        result.setEditable(false);
        result.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        result.setPreferredSize(displayDimension);

        //Setting the panel
        for (int i=0; i<5; i++){
            buttonSetter[i] = new JPanel();
        }
        buttonSetter[0].setLayout(new FlowLayout(FlowLayout.CENTER));
        for (int i=1; i<5; i++){
            buttonSetter[i].setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
        }

        //Setting the buttons for the calculator and interacting with the buttonSetter created above
        for (int i=0; i<19; i++) {
            buttons[i] = new JButton();
            buttons[i].setText(buttonDisplay[i]);
            buttons[i].setFont(new Font("Tahoma", Font.BOLD, 13));
            buttons[i].addActionListener(this);
        }
        for(int i = 0; i < 14; i++) {
            buttons[i].setPreferredSize(regularDimension);
        }
        for(int i = 14; i < 18; i++) {
            buttons[i].setPreferredSize(rColumnDimension);
        }
        buttons[18].setPreferredSize(zeroButDimension);
        buttonSetter[0].add(result);
        add(buttonSetter[0]);

        //ROW 1
        for(int i=0; i<4; i++){
            buttonSetter[1].add(buttons[i]);
        }
        buttonSetter[1].add(buttons[14]);
        add(buttonSetter[1]);

        //ROW 2
        for (int i=4; i<8; i++){
            buttonSetter[2].add(buttons[i]);
        }
        buttonSetter[2].add(buttons[15]);
        add(buttonSetter[2]);

        //ROW 3
        for (int i=8; i<12; i++){
            buttonSetter[3].add(buttons[i]);
        }
        buttonSetter[3].add(buttons[16]);
        add(buttonSetter[3]);

        //ROW 4
        buttonSetter[4].add(buttons[18]);
        for(int i=12; i<14; i++){
            buttonSetter[4].add(buttons[i]);
        }
        buttonSetter[4].add(buttons[17]);
        add(buttonSetter[4]);
    }
    public void clear() {
        try {
            result.setText("");
            for (int i = 0; i < 4; i++) {
                function[i] = false;
            }
            for (int i = 0; i < 2; i++) {
                temporary[i] = 0;
            }
        }
        catch (NullPointerException NPE){}
    }
    public void showResult() {
        double R = 0;
        double n1, n2;
        temporary[1] = Double.parseDouble(result.getText());
        String temp0 = Double.toString(temporary[0]);
        String temp1 = Double.toString(temporary[1]);
        try {
            if(temp0.contains("-")) {
                String[] temp00 = temp0.split("-", 2);
                temporary[0] = (Double.parseDouble(temp00[1]) * -1);
            }
            if(temp1.contains("-")) {
                String[] temp11 = temp1.split("-", 2);
                temporary[1] = (Double.parseDouble(temp11[1]) * -1);
            }
        } catch(ArrayIndexOutOfBoundsException AIOBE) {
        }
        try {
            n1 = temporary[0];
            n2 = temporary[1];
            //Whenever a certain function is true it will do a certain numeric operation (the methods are in the NumericOperation class
            if(function[0]) R = nuop.add(n1, n2); //Addition
            else if(function[1]) R = nuop.subtract(n1, n2); //Subtraction
            else if(function[2]) R = nuop.multiply(n1, n2); //Multiplication
            else if(function[3]) R = nuop.divide(n1, n2); //Division
            result.setText(Double.toString(R));
            for(int i = 0; i < 4; i++)
            function[i] = false;
        } catch(NumberFormatException NFE) {
        }
    }


    public void actionPerformed(ActionEvent e) {

        //Pressing a number's button
        for (int i = 0; i < 19; i++) {
            if (e.getSource() == buttons[i]) {
                if (buttonDisplay[i].matches("[0-9]+")) {
                    result.append(buttonDisplay[i]);
                }
            }
        }

        //Pressing the "+" button
        if (e.getSource() == buttons[3]) {
            temporary[0] = Double.parseDouble(result.getText());
            function[0] = true;
            result.setText("");
        }

        //Pressing the "-" button
        else if (e.getSource() == buttons[7]){
            temporary[0] = Double.parseDouble(result.getText());
            function[1] = true;
            result.setText(" ");
        }

        //Pressing the "*" button
        else if (e.getSource() == buttons[11]){
            temporary[0] = Double.parseDouble(result.getText());
            function[2] = true;
            result.setText("");
        }

        //Pressing the "/" button
        else if (e.getSource() == buttons[13]){
            temporary[0] = Double.parseDouble(result.getText());
            function[3] = true;
            result.setText("");
        }

        //Pressing the "C" button
        else if (e.getSource() == buttons[14]){
            clear();
        }

        //Pressing the "sqrt" button
        else if (e.getSource() == buttons[15]){
            temporary[0] = Double.parseDouble(result.getText());
            result.setText(Double.toString(nuop.root(temporary[0])));
        }

        //Pressing the "+/-" button
        else if (e.getSource() == buttons[16]) {
            temporary[0] = Double.parseDouble(result.getText());
            if (temporary[0] != 0) {
                result.setText(Double.toString(nuop.sign(temporary[0])));
            }
        }

        //Pressing the "." button
        else if (e.getSource() == buttons[12]){
            result.append(".");
            //buttons[12].setEnabled(false);
        }

        //Pressing the "=" button
        else if (e.getSource() == buttons[17]) {
            showResult();
        }
    }

    public static void main(String[] args){
        new Calculator();
    }


}
