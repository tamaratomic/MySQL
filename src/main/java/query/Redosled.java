package query;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Redosled implements Rule{

    private String name = "Redosled iskaza";


    @Override
    public String check(Map<String, List<String>> map, Object object) {

        if(object instanceof Statement){
            List<Statement> list = (List<Statement>) object;
            for(int j = 1; j < map.keySet().size(); j++){
                for(int i = j; i < list.size(); i++){


                }
            }


        }

        return null;
    }
}
