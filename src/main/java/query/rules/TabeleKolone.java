package query.rules;

import com.mysql.cj.conf.PropertyDefinitions;
import gui.MainFrame;
import query.Rule;
import resource.DBNode;
import resource.DBNodeComposite;
import tree.TreeItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TabeleKolone implements Rule {
    @Override
    public boolean check(List<String> tabel, Map<String, List<String>> map, Object object) {
        System.out.println("Tabele i kolone");

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
                String st = s.toUpperCase();
               if (st.startsWith("SUM(") || st.startsWith("COUNT(") || st.startsWith("AVG(")
                       || st.startsWith("MIN(") || st.startsWith("MAX(")) {
                        if(!st.endsWith(")")){
                            JOptionPane.showMessageDialog(null, "Sintaksna greska kod  " + st);
                            return false;
                        }
                        String[] s1 = st.split("\\(");
                        String s2 = s1[1].replace(")", "");
                        if(!naziviAtributa.contains(s2.toLowerCase())){
                            JOptionPane.showMessageDialog(null, "Kolona " + s2 + " ne postoji.");
                            return false;
                        }

               }else {

                   if (!naziviAtributa.contains(str)) {
                       if(map.containsKey("JOIN")){
                           List<DBNode> listaJoinNaziva = new ArrayList<>();
                           for(DBNode node : list){
                               if(node.getName().equalsIgnoreCase(map.get("JOIN").get(0))) {
                                   listaJoinNaziva = ((DBNodeComposite) node).getChildren();
                                   break;
                               }
                           }
                           for(DBNode node: listaJoinNaziva){
                               if(node.getName().equalsIgnoreCase(str)){
                                   break;
                               }
                               if(node.getName().equalsIgnoreCase(listaJoinNaziva.get(listaJoinNaziva.size()-1).getName())){
                                   JOptionPane.showMessageDialog(null, "Kolona " + str + " ne postoji.");
                                   return false;
                               }
                           }
                       }else {
                           JOptionPane.showMessageDialog(null, "Kolona " + str + " ne postoji.");
                           return false;
                       }
                   }
               }
               map.get("SELECT").add(str);
           }



        }

        if(tabel.get(0).equalsIgnoreCase("WHERE")){
            List<String> kolone = (List<String>) object;


            TreeItem root = MainFrame.getInstance().getAppCore().getTree().getRoot();
            List<DBNode> list = ((DBNodeComposite) root.getDbNode()).getChildren();

            List<DBNode> listaAtributa = new ArrayList<>();


            for(DBNode node : list){
                if(node.getName().equalsIgnoreCase(map.get("FROM").get(0))) {
                    listaAtributa = ((DBNodeComposite) node).getChildren();
                    break;
                }
            }

            List<String> naziviAtributa = new ArrayList<>();
            for(DBNode node:listaAtributa){
                naziviAtributa.add(node.getName());
            }

            for(String s : kolone){
                if(s.contains("=") || s.contains(">") || s.contains("<")){

                }else {

                    try {
                        int i = Integer.valueOf(s);
                    } catch (Exception e) {
                        try {
                        float f = Float.valueOf(s);
                    } catch (Exception ex) {


                        if (s.startsWith("'") && s.endsWith("'")) {

                        } else {
                            if (!naziviAtributa.contains(s)) {
                                JOptionPane.showMessageDialog(null, "Kolona " + s + " ne postoji.");
                                return false;
                            }
                        }
                    }
                }

                }
            }


        }


        if(tabel.get(0).equalsIgnoreCase("GROUP")) {
            List<String> kolone = (List<String>) object;


            TreeItem root = MainFrame.getInstance().getAppCore().getTree().getRoot();
            List<DBNode> list = ((DBNodeComposite) root.getDbNode()).getChildren();

            List<DBNode> listaAtributa = new ArrayList<>();


            for (DBNode node : list) {
                if (node.getName().equalsIgnoreCase(map.get("FROM").get(0))) {
                    listaAtributa = ((DBNodeComposite) node).getChildren();
                    break;
                }
            }

            List<String> naziviAtributa = new ArrayList<>();
            for (DBNode node : listaAtributa) {
                naziviAtributa.add(node.getName());
            }


            for(String s : kolone){
                if(!naziviAtributa.contains(s)){
                    JOptionPane.showMessageDialog(null, "Argument " + s + " nije ispravan.");
                    return false;
                }
            }

        }

            return true;
    }
}

