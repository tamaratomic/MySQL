package query.rules;

import query.Rule;
import query.Statement;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Redosled implements Rule {

    private String name = "Redosled iskaza";



    @Override
    public boolean check(List<String> query,Map<String, List<String>> map, Object object) {
        System.out.println("REDOSLED");




        List<Statement> list  = (List<Statement>) object;
       // System.out.println("da li je lista 0 " + list.isEmpty());

        int br = 0;
        for(int i = 0; i < query.size(); i ++){
           // if(map.keySet().contains(query.get(i))) {
                for (Statement s : list) {
                   // System.out.println("REDOSLED");
                    if (query.get(i).equalsIgnoreCase(s.getName())) {
                     //   System.out.println("REDOSLED U IFU");
                        if(br <= s.getPriority()) {
                            br = s.getPriority();
                        }else{
                            System.out.println("POGRESTAN REDOSLED");
                            JOptionPane.showMessageDialog(null, "Pogresan redosled." + s.getName() + " nije na ispravnom mestu");
                            return false;
                        }
                        break;
                    }
              //  }
            }
        }


        return true;
    }
}
