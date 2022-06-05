package gui.controller;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import gui.MainFrame;

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
//        String text = MainFrame.getInstance().getTextPane().getText();
//        String formatiranText = SqlFormatter.format(text);

        if(MainFrame.getInstance().isKliknutoP()){
            MainFrame.getInstance().getAppCore().run(MainFrame.getInstance().getStringZaRun());
        }
        else{
            MainFrame.getInstance().getAppCore().run(MainFrame.getInstance().getTextPane().getText());
        }

    }
}
