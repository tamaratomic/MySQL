package query.rules;

import query.Rule;

import java.util.List;
import java.util.Map;

public class Where implements Rule {

    private String name = "Where";


    @Override
    public String check(List<String> query, Map<String, List<String>> map, Object object) {

        System.out.println("WHERE");

        if(map.containsKey("WHERE")){
            for(int i = 0; i < query.size(); i++){
                if(query.get(i).equalsIgnoreCase("WHERE")){
                    System.out.println("U IFU");
                    System.out.println(query.get(i).toUpperCase());
                    int i2 = i+1;
                    while(i2 < query.size()  && !map.containsKey(query.get(i2).toUpperCase())){
                        String s = query.get(i2).toUpperCase();
                        System.out.println("U WHILE");
                        if(s.contains("SUM") || s.contains("COUNT") || s.contains("AVG")
                                || s.contains("MIN") || s.contains("MAX")){
                            System.out.println("WHERE NE SME DA SADRZI FUNKCIJU AGREGACIJE");
                            return null;
                        }
                        i2++;
                    }
                }
            }
        }



        return null;
    }
}
