package query;

import java.util.ArrayList;
import java.util.List;

public class Checker {

    private List<Statement> statements = new ArrayList<>();
    private List<Rule> rules = new ArrayList<>();

    public Checker() {
        if(rules.isEmpty()){
            rules.add(new Redosled());
            rules.add(new ObavezniDelovi());
        }


        if(statements.isEmpty()){
            statements.add(new Statement("SELECT", 1));
            statements.add(new Statement("FROM", 2));
            statements.add(new Statement("WHERE", 3));
        }


    }


    public void check(String query){

        for (Rule r:rules){
            r.check(query);
        }

    }
}
