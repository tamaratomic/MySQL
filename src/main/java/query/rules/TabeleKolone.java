package query.rules;

import gui.MainFrame;
import query.Rule;
import resource.DBNode;
import resource.DBNodeComposite;
import tree.TreeItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabeleKolone implements Rule {
    @Override
    public boolean check(List<String> tabel, Map<String, List<String>> map, Object object) {

        String tabela = "";
        List<String> nazivi = new ArrayList<>();

        if(tabel.size() == 1){
            if(tabel.get(0).equalsIgnoreCase("FROM")){

                List<String> listFrom = (List<String>) object;
                System.out.println("FROM " + listFrom);

                TreeItem root = MainFrame.getInstance().getAppCore().getTree().getRoot();
                List<DBNode> list = ((DBNodeComposite) root.getDbNode()).getChildren();

                for(DBNode node : list){
                    if(node.getName().equalsIgnoreCase(listFrom.get(0))) {
                        map.get("FROM").add(listFrom.get(0));
                        return true;
                    }
                    if(node.equals(list.get(list.size()-1))){
                        JOptionPane.showMessageDialog(null, "Tabela " + listFrom.get(0) + " ne postoji.");
                        return false;
                    }
                }
            }

        }
        if(tabel.get(0).equalsIgnoreCase("SELECT")) {
            List<String> kolone = (List<String>) object;


            TreeItem root = MainFrame.getInstance().getAppCore().getTree().getRoot();
            List<DBNode> list = ((DBNodeComposite) root.getDbNode()).getChildren();

            List<DBNode> listaAtributa = new ArrayList<>();


            for(DBNode node : list){
                if(node.getName().equalsIgnoreCase(map.get("FROM").get(0))) {
                    listaAtributa = ((DBNodeComposite) node).getChildren();
                    break;
                }
//                if(node.equals(list.get(list.size()-1))){
//                    JOptionPane.showMessageDialog(null, "Tabela " + nazivTabele + " ne postoji.");
//                }
            }

            List<String> naziviAtributa = new ArrayList<>();
           for(DBNode node:listaAtributa){
               naziviAtributa.add(node.getName());
           }

           for(String s:kolone){
               String str = s;
               if(s.contains(",")){
                   str = s.replace(",", "");
               }
               if(s.contains("*") || s.contains("/") || s.contains("%") || s.contains("+")
                       || s.contains("-")){
                   String[] c = s.split("[-+*%/]");
                   str = c[0];
               }
               if(!naziviAtributa.contains(str)){
                   JOptionPane.showMessageDialog(null, "Kolona " + str + " ne postoji.");
                   return false;
               }
           }



        }
        return true;
    }
}

