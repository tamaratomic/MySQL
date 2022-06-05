package query.rules;

import query.Rule;

import java.util.List;
import java.util.Map;

public class ObavezniDelovi implements Rule {

    private String name = "Obavenzi delovi";
    //private int prioritet = 0;


    @Override
    public String check(List<String> l, Map<String, List<String>> map, Object object) {

        if(map.keySet().contains("JOIN")){
            if(!map.keySet().contains("ON") && !map.containsKey("USING")){
                System.out.println("NEISPRAVAN JOIN");
            }
        }
        if(map.containsKey("GROUP") && !map.containsKey("BY")){
            System.out.println("NEISPRAVAN GROUP");
        }

        if(map.containsKey("ORDER") && !map.containsKey("BY")){
            System.out.println("NEISPRAVAN ORDER");
        }

        return null;
    }
}
