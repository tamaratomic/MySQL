package query.rules;

import query.Rule;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObavezniDeo implements Rule {



    @Override
    public boolean check(List<String> key, Map<String, List<String>> map, Object object) {
        System.out.println("ObavezniDeo");

        if(key.contains("SELECT")){
            System.out.println("select");
            map.put("SELECT", new ArrayList<>());
            if(!key.contains("FROM")) {
                JOptionPane.showMessageDialog(null, "Za SELECT upite je obavezan FROM");
                return false;
            }else {
                map.put("FROM", new ArrayList<>());
            }
        }
        if(key.contains("UPDATE")){
            map.put("UPDATE", new ArrayList<>());
            if(!key.contains("SET")) {
                JOptionPane.showMessageDialog(null, "Za UPDATE upite je obavezan SET");
                return false;
            }else {
                map.put("SET", new ArrayList<>());
            }
        }

        if(key.contains("DELETE")){
            map.put("DELETE", new ArrayList<>());
            if(!key.contains("FROM")) {
                JOptionPane.showMessageDialog(null, "Za DELETE upite je obavezan FROM");
                return false;
            }else {
                map.put("FROM", new ArrayList<>());
            }
        }

        if(key.contains("INSERT")){

            if(!key.contains("INTO")) {
                JOptionPane.showMessageDialog(null, "Za INSERT upite je obavezan INTO");
                return false;
            }
            map.put("INSERT INTO", new ArrayList<>());
        }

        if(key.contains("ORDER")){

            if(!key.contains("BY")) {
                JOptionPane.showMessageDialog(null, "Nispravan ORDER.Nedostaje kljucna rec BY");
                return false;
            }
            map.put("ORDER BY", new ArrayList<>());
        }
        if(key.contains("GROUP")){

            if(!key.contains("BY")) {
                JOptionPane.showMessageDialog(null, "Nispravan GROUP.Nedostaje kljucna rec BY");
                return false;
            }
            map.put("GROUP BY", new ArrayList<>());
        }

        if(key.contains("JOIN")){

            if(!key.contains("ON") && !key.contains("USING") ) {
                JOptionPane.showMessageDialog(null, "Nispravan JOIN.Nedostaje kljucna rec ON ili USING");
                return false;
            }
            if(key.contains("ON")){
                map.put("ON", new ArrayList<>());
            }else {
                map.put("USING", new ArrayList<>());
            }
            map.put("JOIN", new ArrayList<>());
        }

        return true;
    }
}
