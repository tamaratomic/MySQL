package query;

import database.Repository;

import java.util.List;
import java.util.Map;

public interface Rule {




    boolean check(List<String> query, Map<String, List<String>> map,  Object object);


}
