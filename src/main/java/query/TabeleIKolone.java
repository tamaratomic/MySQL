package query;

import database.Database;
import database.MYSQLrepository;
import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.data.Row;
import tree.TreeItem;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabeleIKolone implements Rule{

    private String name = "Kolone i tabele";


    @Override
    public String check(List<String> l, Map<String, List<String>> map, Object object) {

        TreeItem root = MainFrame.getInstance().getAppCore().getTree().getRoot();
        List<DBNode> list = ((DBNodeComposite) root.getDbNode()).getChildren();


        List<String> nazivTabele = map.get("FROM");
        System.out.println("NAZIV TABELE " + nazivTabele.get(0));

        List<DBNode> listaAtributa = null;


        for(DBNode node : list){
            if(node.getName().equalsIgnoreCase(nazivTabele.get(0))) {
                listaAtributa = ((DBNodeComposite) node).getChildren();
                break;
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
            }
            for(String s: map.get(query)){
                if(nazivi.contains(s) || s.contains("%") || s.contains("(") || s.contains(")")){
                    continue;
                }else{

                    System.out.println("Tabela " + nazivTabele + "ne sadrzi " + s);
                }
            }

        }


        return null;
    }
}
