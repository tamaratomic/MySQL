package query.rules;

import query.Rule;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class ObavezniDelovi implements Rule {

    private String name = "Obavenzi delovi";
    //private int prioritet = 0;


    @Override
    public boolean check(List<String> l, Map<String, List<String>> map, Object object) {

        System.out.println("OBAVEZNI DELOVI");

        if(map.keySet().contains("JOIN")){
            if(!map.keySet().contains("ON") && !map.containsKey("USING")){
                JOptionPane.showMessageDialog(null, "Nispravan JOIN.Nedostaje kljucna rec ON ili USING");
                return false;
            }
        }
        if(map.containsKey("GROUP") && !map.containsKey("BY")){
            JOptionPane.showMessageDialog(null, "Nispravan GROUP.Nedostaje kljucna rec BY");
            return false;
        }

        if(map.containsKey("ORDER") && !map.containsKey("BY")){
            JOptionPane.showMessageDialog(null, "Nedostaje kljucna rec BY");
            return false;
        }

        if(map.containsKey("SELECT") && !map.containsKey("FROM")){
            JOptionPane.showMessageDialog(null, "Za SELECT upite je obavezan FROM");
            return false;
        }

        if(map.containsKey("UPDATE") && !map.containsKey("SET")){
            JOptionPane.showMessageDialog(null, "Za UPDATE upite je obavezan SET");
            return false;
        }

        if(map.containsKey("DELETE") && !map.containsKey("FROM")){
            JOptionPane.showMessageDialog(null, "Za DELETE upite je obavezan FROM");
            return false;
        }

        return true;
    }
}
