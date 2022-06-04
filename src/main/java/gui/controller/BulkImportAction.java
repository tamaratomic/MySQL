package gui.controller;

import gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class BulkImportAction extends AbstractAction{

    public BulkImportAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        putValue(NAME, "BI");
        putValue(SHORT_DESCRIPTION, "Bulk Import");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCommands().bulkImport(MainFrame.getInstance().getJTree().getLastSelectedPathComponent());

    }
}
