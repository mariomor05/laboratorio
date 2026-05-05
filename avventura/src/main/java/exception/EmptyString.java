package exception;

import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EmptyString extends Exception {

    private JScrollPane scrollpane;

    public EmptyString(JFrame finestra) {
        JOptionPane.showMessageDialog(finestra, "Ci sono dei campi vuoti", "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public EmptyString(JPanel finestra) {
        JOptionPane.showMessageDialog(finestra, "Ci sono dei campi vuoti", "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public EmptyString(JScrollPane finestra) {
        scrollpane = new JScrollPane();
        scrollpane = finestra;
    }

    public void MostraErrore() {
        JOptionPane.showMessageDialog(scrollpane, "Ci sono dei campi vuoti o non corretti", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}
