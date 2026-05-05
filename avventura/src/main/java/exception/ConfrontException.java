package exception;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConfrontException extends Exception {

    public ConfrontException(JFrame finestra) {
        JOptionPane.showMessageDialog(finestra, "Reinserisci la PW", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}
