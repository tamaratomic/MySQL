package query.rules;

import database.Database;
import database.MYSQLrepository;
import gui.MainFrame;
import query.Rule;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.data.Row;
import tree.TreeItem;

import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabeleIKolone implements Rule {

    private String name = "Kolone i tabele";


    @Override
    public boolean check(List<String> l, Map<String, List<String>> map, Object object) {
        System.out.println("TABELE I KOLONE");

        TreeItem root = MainFrame.getInstance().getAppCore().getTree().getRoot();
        List<DBNode> list = ((DBNodeComposite) root.getDbNode()).getChildren();


        List<String> nazivTabele = map.get("FROM");
//        System.out.println(map.get("FROM"));
//        System.out.println("NAZIV TABELE " + nazivTabele.get(0));

        List<DBNode> listaAtributa = new ArrayList<>();


        for(DBNode node : list){
            if(node.getName().equalsIgnoreCase(nazivTabele.get(0))) {
                listaAtributa = ((DBNodeComposite) node).getChildren();
                break;
            }
            if(node.equals(list.get(list.size()-1))){
                JOptionPane.showMessageDialog(null, "Tabela " + nazivTabele + " ne postoji.");
            }
        }


        for (String query: map.keySet()){
            if(query.equalsIgnoreCase("from")){
                //System.out.println("U IFUUUU 11111111");
                continue;
            }
//            System.out.println(map.get(query));
//            System.out.println(listaAtributa);
            List<String> nazivi = new ArrayList<>();
            for(DBNode node : listaAtributa){
                nazivi.add(node.getName());
                //System.out.println(node.getName());
            }
            if(!query.equalsIgnoreCase("in")) {
                for (String s : map.get(query)) {
                    if (nazivi.contains(s.toLowerCase()) || s.contains("%") || s.contains("(") || s.contains(")")
                            || s.contains("=") || s.contains("*")|| s.contains("/")|| s.contains("%")) {
                        continue;
                    } else {

                        JOptionPane.showMessageDialog(null, "Tabela " + nazivTabele + " ne sadrzi " + s);
                        return false;
                    }
                }
            }

        }


        return true;
    }
}
