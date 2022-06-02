package query;



import java.util.*;

public class Checker {

    private List<Statement> statements = new ArrayList<>();
    private List<Rule> rules = new ArrayList<>();
    private Map<String, List<String>> mapa = new HashMap<>();

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
        List<String> st = Arrays.asList(query.split("\n"));

        System.out.println(st);
        String key = "";
        for(int i = 0; i < st.size(); i++){
            if(st.get(i).equalsIgnoreCase("select") || st.get(i).equalsIgnoreCase("from")
                || st.get(i).equalsIgnoreCase("where")){
                mapa.put(st.get(i), new ArrayList<>());
                key = st.get(i);
            }else{
                String value = st.get(i);
                if(st.get(i).contains(",")) {
                    StringBuilder sb = new StringBuilder(st.get(i));
                    sb.deleteCharAt(st.get(i).length() - 1);
                    value = sb.toString();
                }


                    mapa.get(key).add(value);





            }
        }

        System.out.println(mapa);

        for (Rule r:rules){
            r.check(mapa, statements);
        }

    }
}
