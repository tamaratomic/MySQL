package query.rules;

import query.Rule;

import java.util.List;
import java.util.Map;

public class InsertInto implements Rule {
    @Override
    public boolean check(List<String> query, Map<String, List<String>> map, Object object) {
        return false;
    }
}
