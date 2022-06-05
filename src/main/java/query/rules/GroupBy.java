package query.rules;

import query.Rule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GroupBy implements Rule {


    private String name = "Group by";


    @Override
    public String check(List<String> query, Map<String, List<String>> map, Object object) {

        if(map.get("SELECT").size() > 1){
            for(String s : map.get("SELECT")){
                String st = s.toUpperCase();
                //COUNT, AVG,
                //MIN, MAX i SUM.
                if(st.contains("SUM") || st.contains("COUNT") || st.contains("AVG")
                        || st.contains("MIN") || st.contains("MAX")){
                    if(!map.containsKey("GROUP BY")){
                        System.out.println("POTREBAN GROUP BY");
                        return null;
                    }
                }
            }
        }



        return null;
    }
}
