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

public class JoinOn implements Rule {

    private String name = "Join on";


    @Override
    public boolean check(List<String> l, Map<String, List<String>> map, Object object) {
        System.out.println("JOIN");



            TreeItem root = MainFrame.getInstance().getAppCore().getTree().getRoot();
            List<DBNode> list = ((DBNodeComposite) root.getDbNode()).getChildren();
            System.out.println("LIST " + list.isEmpty());

            Map<String, List<String>> mapaJoin = (Map<String, List<String>>) object;




            String nazivTabele = map.get("FROM").get(0);
            String nazivTabeleJoin = mapaJoin.get("JOIN").get(0);
            System.out.println("Naziv TABELE " +nazivTabele);
            System.out.println("Naziv TABELE join " +nazivTabeleJoin);

            List<DBNode> listaAtributa = new ArrayList<>();
            List<DBNode> listaAtributaJoin = new ArrayList<>();

            for (DBNode node : list) {
                System.out.println(node.getName());
                if (node.getName().equalsIgnoreCase(nazivTabele)) {
                    System.out.println("LISTA ATRIBUTA");
                    listaAtributa = ((DBNodeComposite) node).getChildren();

                }
                if (node.getName().equalsIgnoreCase(nazivTabeleJoin)) {
                    map.get("JOIN").add(node.getName());
                    System.out.println("LISTA ATRIBUTA JOIN");
                    listaAtributaJoin = ((DBNodeComposite) node).getChildren();

                }
                if (!listaAtributaJoin.isEmpty() && !listaAtributa.isEmpty()) {
                    if(map.get("JOIN").isEmpty()){
                        JOptionPane.showMessageDialog(null,   "Tabela " + nazivTabeleJoin + " ne postoji.");
                        return false;
                    }
                    break;
                }
            }

            String straniKljuc = "";
            if (map.keySet().contains("ON")) {
                List<String> s = mapaJoin.get("ON");
                if(!s.get(0).contains("(") && !s.get(2).contains(")") && !s.get(1).contains("=")){
                    JOptionPane.showMessageDialog(null, "Neispravna sintaksa za ON");
                    return false;
                }
                System.out.println(s);
                straniKljuc = s.get(2).replace(")", "");
                String[] k = straniKljuc.split("\\.");
                straniKljuc = k[1];
                System.out.println("STRANI KLJUC = " + straniKljuc);
            } else {
                List<String> s = mapaJoin.get("USING");
                if(!s.get(0).contains("(") && !s.get(1).contains(")")){
                    JOptionPane.showMessageDialog(null, "Neispravna sintaksa za USING");
                    return false;
                }
                String h = s.get(0).replace(")", "");
                straniKljuc = h.replace("(", "");
                System.out.println("STRANI KLJUC  using = " + straniKljuc);
            }


            for (DBNode node : listaAtributa) {
                //System.out.println("for1");
                if (node.getName().equalsIgnoreCase(straniKljuc)) {
                    // System.out.println("if1");
                    for (DBNode node1 : listaAtributaJoin) {
                        // System.out.println("for 2");
                        if (node1.getName().equalsIgnoreCase(straniKljuc)) {
                            System.out.println("STRANI KLJUC");
                            map.get("JOIN").add(nazivTabeleJoin);
                            return true;
                        }
                        if (listaAtributaJoin.get(listaAtributaJoin.size() - 1).equals(node1)) {
                            JOptionPane.showMessageDialog(null, straniKljuc + " nije strani kljuc.");
                            return false;
                        }
                    }
                }
                if (listaAtributa.get(listaAtributa.size() - 1).equals(node)) {
                    JOptionPane.showMessageDialog(null, straniKljuc + " nije strani kljuc.");
                    return false;
                }
            }

        return true;
    }
}
