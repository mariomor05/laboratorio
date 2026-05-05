package gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exception.ConfrontException;
import exception.EmptyString;
import interfaccia.InterfaceLogIn;

import javax.swing.BoxLayout;

public class LogIn implements InterfaceLogIn {
    //Dichairazioni delle componenti 

    private JFrame finestra;
    private JPanel namePanel;
    private JPanel pssw1Panel;
    private JPanel pssw2Panel;
    private JPanel buttonPanel;
    private JLabel nameLabel;
    private JLabel pssw1Label;
    private JLabel pssw2Label;
    private JTextField nameField;
    private JPasswordField pssw1Field;
    private JPasswordField pssw2Field;
    private JButton button;
    private Container contenitore;
    private String nameString;
    private String pssw1String;
    private String pssw2String;
    private Boolean correttezza;

    //Costruttore della classe
    public LogIn() {
        finestra = new JFrame("VirusZ");
        namePanel = new JPanel();
        pssw1Panel = new JPanel();
        pssw2Panel = new JPanel();
        buttonPanel = new JPanel();
        nameLabel = new JLabel("Nome  :");
        pssw1Label = new JLabel("Password  :");
        pssw2Label = new JLabel("Conferma PW  :");
        nameField = new JTextField(15);
        pssw1Field = new JPasswordField(15);
        pssw2Field = new JPasswordField(15);
        button = new JButton("Gioca");
        contenitore = finestra.getContentPane();
        nameString = new String();
        pssw1String = new String();
        pssw2String = new String();
        correttezza = false;
    }
    //Medodo che realizza effetivamente la GUI

    public String Presentazione() {
        contenitore.setLayout(new BoxLayout(contenitore, BoxLayout.Y_AXIS));
        finestra.setBounds(200, 200, 600, 200);

        contenitore.add(namePanel);
        contenitore.add(pssw1Panel);
        contenitore.add(pssw2Panel);
        contenitore.add(buttonPanel);

        namePanel.setLayout(new FlowLayout());
        pssw1Panel.setLayout(new FlowLayout());
        pssw2Panel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new FlowLayout());

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        pssw1Panel.add(pssw1Label);
        pssw1Panel.add(pssw1Field);

        pssw2Panel.add(pssw2Label);
        pssw2Panel.add(pssw2Field);

        buttonPanel.add(button);
        button.addActionListener(new clicBottone());

        finestra.setVisible(true);
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        do {
            System.out.println("");
        } while (correttezza == false);
        return nameString + ":" + pssw1String;
    }

    private void Acquisizione() throws ConfrontException, EmptyString {
        String name = new String();
        String pssw1 = new String();
        String pssw2 = new String();
        name = nameField.getText();
        pssw1 = pssw1Field.getText();
        pssw2 = pssw2Field.getText();
        if (name.equals("") || pssw1.equals("") || pssw2.equals("")) {
            throw new EmptyString(finestra);
        }
        if (!(pssw1.equals(pssw2))) {
            throw new ConfrontException(finestra);
        } else {
            correttezza = true;
        }
        nameString = name;
        pssw1String = pssw1;
        pssw2String = pssw2;
    }

    public void Chiudifinestra() {
        finestra.dispose();
    }

    private class clicBottone implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            try {
                Acquisizione();
            } catch (ConfrontException e) {

            } catch (EmptyString s) {

            }
        }
    }
}
