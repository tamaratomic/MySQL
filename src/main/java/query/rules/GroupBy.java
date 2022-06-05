package query.rules;

import query.Rule;

import java.util.List;
import java.util.Map;

public class GroupBy implements Rule {


    private String name = "Group by";


    @Override
    public String check(List<String> query, Map<String, List<String>> map, Object object) {
        return null;
    }
}
