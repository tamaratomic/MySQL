package query;

import database.Repository;

import java.util.List;
import java.util.Map;

public interface Rule {




    String check(Map<String, List<String>> map,  Object object);


}
