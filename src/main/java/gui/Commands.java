package gui;

import javax.swing.*;

public interface Commands {
    String ulepsaj(JTextPane textPane);
    void export(JTable table);
    void bulkImport(Object lastSelectedPathComponent);
}
