package gui;

import javax.swing.*;

public interface Commands {
    void ulepsaj(JTextPane textPane);
    void export(JTable table);
    void bulkImport(Object lastSelectedPathComponent);
}
