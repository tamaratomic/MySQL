package gui.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RunAction extends AbstractAction {

    public RunAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        putValue(NAME, "R");
        putValue(SHORT_DESCRIPTION, "Run");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
