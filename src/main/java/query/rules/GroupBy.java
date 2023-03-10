package query.rules;

import query.Rule;

import javax.swing.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GroupBy implements Rule {


    private String name = "Group by";


    @Override
    public boolean check(List<String> select, Map<String, List<String>> map, Object object) {

        System.out.println("GROUP BY");

            if (select.size() > 1) {
                for (String s : select) {
                    String st = s.toUpperCase();
                    //COUNT, AVG,
                    //MIN, MAX i SUM.
                    if (st.contains("SUM(") || st.contains("COUNT(") || st.contains("AVG(")
                            || st.contains("MIN(") || st.contains("MAX(")) {
                            return true;

                    }
                }
                return false;
            }



        return true;
    }
}
