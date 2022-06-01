package gui.controller;

import gui.MainFrame;
import gui.view.PrettyImplementation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PrettyAction extends AbstractAction {

    public PrettyAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        putValue(NAME, "P");
        putValue(SHORT_DESCRIPTION, "Pretty");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        new PrettyImplementation().ulepsaj(MainFrame.getInstance().getTextArea());
    }
}
