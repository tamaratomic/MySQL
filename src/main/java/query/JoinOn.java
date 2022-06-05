package query;

import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import tree.TreeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoinOn implements Rule{

    private String name = "Join on";


    @Override
    public String check(List<String> l, Map<String, List<String>> map, Object object) {

        if(!map.keySet().contains("JOIN")){
            return null;
        }

        TreeItem root = MainFrame.getInstance().getAppCore().getTree().getRoot();
        List<DBNode> list = ((DBNodeComposite) root.getDbNode()).getChildren();
        System.out.println("LIST " + list.isEmpty());


        List<String> nazivTabele = map.get("FROM");
        List<String> nazivTabeleJoin = map.get("JOIN");
        System.out.println(nazivTabele.get(0));

        List<DBNode> listaAtributa = new ArrayList<>();
        List<DBNode> listaAtributaJoin = new ArrayList<>();

        for(DBNode node : list){
            System.out.println(node.getName());
            if(node.getName().equalsIgnoreCase(nazivTabele.get(0))) {
                System.out.println("LISTA ATRIBUTA");
                listaAtributa = ((DBNodeComposite) node).getChildren();

            }
            if(node.getName().equalsIgnoreCase(nazivTabeleJoin.get(0))) {
                System.out.println("LISTA ATRIBUTA JOIN");
                listaAtributaJoin = ((DBNodeComposite) node).getChildren();

            }
            if(!listaAtributaJoin.isEmpty() && !listaAtributa.isEmpty()){
                break;
            }
        }

        String straniKljuc = "";
        if(map.keySet().contains("ON")) {
            List<String> s = map.get("ON");
            straniKljuc = s.get(1).replace(")", "");
            String[] k = straniKljuc.split("\\.");
            straniKljuc = k[1];
            System.out.println("STRANI KLJUC = " + straniKljuc);
        }else{
            List<String> s = map.get("USING");
            String h = s.get(0).replace(")", "");
            straniKljuc = h.replace("(", "");
            System.out.println("STRANI KLJUC  using = " + straniKljuc);
        }


        for(DBNode node: listaAtributa){
            if(node.getName().equalsIgnoreCase(straniKljuc)){
                System.out.println("if1");
                for(DBNode node1: listaAtributaJoin){
                    System.out.println("for 2");
                    if(node1.getName().equalsIgnoreCase(straniKljuc)){
                        System.out.println("STRANI KLJUC");
                        break;
                    }
                    if(listaAtributaJoin.get(listaAtributaJoin.size()-1).equals(node1)){
                        System.out.println("NIJE STRANI KLJUC");
                    }
                }
            }
            if(listaAtributa.get(listaAtributa.size()-1).equals(node)){
                //System.out.println("NIJE STRANI KLJUC");
            }
        }

        return null;
    }
}
