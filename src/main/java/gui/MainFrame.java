package gui;

import app.AppCore;
import gui.controller.ActionManager;
import gui.model.DescriptionRepository;
import gui.view.CommandsImpl;
import gui.view.Toolbar;
import lombok.Data;
import observer.Notification;
import observer.Subscriber;
import tree.implementation.SelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

@Data
public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private AppCore appCore;
    private JTable jTable;
    private JTextPane textPane;
    private JScrollPane jsp;
    private JTree jTree;
    private JPanel left;
    private JPanel right;
    private JToolBar toolBar;
    private ActionManager actionManager;
    private Commands commands;


    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        commands = new CommandsImpl();
        DescriptionRepository dr = new DescriptionRepository();
        System.out.println(dr.getJsonObject());

        actionManager = new ActionManager();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolBar = new Toolbar();

        right = new JPanel(new BorderLayout());

        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(500, 200));
        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        jTable.setFillsViewportHeight(true);
        this.setLayout(new BorderLayout());

        right.add(new JScrollPane(textPane), BorderLayout.NORTH);
        right.add(new JScrollPane(jTable), BorderLayout.SOUTH);

        add(toolBar,BorderLayout.NORTH);
        add(right, BorderLayout.EAST);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
        initialiseTree();
    }

    private void initialiseTree() {
        DefaultTreeModel defaultTreeModel = appCore.loadResource();
        jTree = new JTree(defaultTreeModel);
        jTree.addTreeSelectionListener(new SelectionListener());
        jsp = new JScrollPane(jTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        left = new JPanel(new BorderLayout());
        left.add(jsp, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        pack();
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    @Override
    public void update(Notification notification) {


    }
}
