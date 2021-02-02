package project;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Panel extends JFrame   {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel labelUsername = new JLabel("Enter username: ");
    JLabel labelFindLine = new JLabel("Enter Line Number: ");
    JTextField textFindLine = new JTextField(20);
    public static   JTextField fieldAdresse = new JTextField(20);
    JButton buttonSave = new JButton("Save");
    JLabel labelSearch=new JLabel("enter word: ");
    public static JTextField fieldSearch=new JTextField(6);
    JButton buttonSearch = new JButton("Search");
    JButton buttonLine=new JButton("Go To");
    JLabel labelReplace=new JLabel("enterFirstWord:");
    JTextField firstWordField=new JTextField(20);
    JLabel labelNewWord=new JLabel("enterSecondWord:");
    JTextField secondWordField=new JTextField(20);
    JButton buttonReplace=new JButton("Replace");


    public Panel() {


    }
    
    public void fff(){
        this.dispose();
    }
    
    public void goToLine(){
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);


        constraints.gridx = 0;
        constraints.gridy = 1;
        newPanel.add(labelFindLine, constraints);

        constraints.gridx = 1;
        newPanel.add(textFindLine, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(buttonLine, constraints);
        buttonLine.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                //Frame.goToLineNum=textFindLine.getText();
                //Frame.lineNumber=Integer.parseInt(Frame.goToLineNum);
                //System.out.println(Frame.goToLineNum);
                //Frame.isButtonPressed=true;
                fff();
            }
        });


        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Search Panel"));

        // add the panel to this frame
        add(newPanel);

        pack();
        setLocationRelativeTo(null);



    }

    public void findText() {
        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);


        constraints.gridx = 0;
        constraints.gridy = 1;
        newPanel.add(labelSearch, constraints);

        constraints.gridx = 1;
        newPanel.add(fieldSearch, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(buttonSearch, constraints);
        buttonSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                //Frame.saveSearchWord=fieldSearch.getText();
                //System.out.println(Frame.saveSearchWord);
                //Frame.isButtonPressed=true;
                fff();
            }
        });

        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Search Panel"));

        // add the panel to this frame
        add(newPanel);

        pack();
        setLocationRelativeTo(null);


    }

    
    public Frame Replace(Frame frm){

        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(labelReplace, constraints);

        constraints.gridx = 1;
        newPanel.add(firstWordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        newPanel.add(labelNewWord, constraints);

        constraints.gridx = 1;
        newPanel.add(secondWordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(buttonReplace, constraints);
        ActionListener a=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Frame.firstWord=firstWordField.getText();
                //Frame.lastWord=secondWordField.getText();
                frm.replace(firstWordField.getText(),secondWordField.getText());
                //Frame.ischanged=true;
                fff();
            }
        };
        buttonReplace.addActionListener(a);

        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Panel"));

        // add the panel to this frame
        add(newPanel);

        pack();
        setLocationRelativeTo(null);
        return frm;
    }

}





