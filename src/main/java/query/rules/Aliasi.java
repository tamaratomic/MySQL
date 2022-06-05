package query.rules;

import query.Rule;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;

public class Aliasi implements Rule {

    private String name = "Alias";


    @Override
    public String check(List<String> query, Map<String, List<String>> map, Object object) {

//
//        if(map.containsKey("AS")){
//            for(int i = 0; i < query.size(); i++){
//                String as = "";
//                if(query.get(i-1).equalsIgnoreCase("as")){
//                    if(map.containsKey(query.get(i))){
//                        System.out.println("GRESKAAAA");
//                        break;
//                    }else{
//                        if(query.get(i).contains("\"")){
//                            as.concat(query.get(i).replace("\"", ""));
//                        }
//                    }
//                }
//
//                if(query.get(i-1).equalsIgnoreCase("as")){
//                    String as = "";
//                    for(String s : map.get("AS")){
//                        if(s.contains("\"")){
//                            as.concat(s.replace("\"", ""));
//                            continue;
//                        }
//                        if(s.equalsIgnoreCase(map.get("AS").get(map.get("AS").size()-1))){
//                            break;
//                        }
//                        as.concat(s);
//
//                    }
//                }
//            }
//        }
//
//

        return null;
    }
}
