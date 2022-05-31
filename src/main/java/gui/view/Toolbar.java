package gui.view;

import gui.MainFrame;

import javax.swing.*;

public class Toolbar extends JToolBar {

    public Toolbar(){
        super(HORIZONTAL);
        setFloatable(false);

        add(MainFrame.getInstance().getActionManager().getBulkImportAction());
        add(MainFrame.getInstance().getActionManager().getExportAction());
        add(MainFrame.getInstance().getActionManager().getPrettyAction());
        add(MainFrame.getInstance().getActionManager().getRunAction());

    }
}
