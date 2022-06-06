package query;



import gui.MainFrame;

import query.rules.*;

import javax.swing.*;
import java.util.*;

public class Checker {

    private List<Statement> selectStatements = new ArrayList<>();
    private List<Statement> insertStatements = new ArrayList<>();
    private List<Statement> deleteStatements = new ArrayList<>();
    private List<Statement> updateStatements = new ArrayList<>();
    private List<Rule> rules = new ArrayList<>();
    private Map<String, List<String>> mapa = new HashMap<>();
    private List<String> keywords = new ArrayList<>();
    private List<Boolean> ispravnost = new ArrayList<>();
    private List<String> aliasi = new ArrayList<>();

    public Checker() {
        if(rules.isEmpty()){
            rules.add(new Redosled());
            rules.add(new ObavezniDelovi());
            rules.add(new JoinOn());
            rules.add(new Where());
            rules.add(new GroupBy());
            rules.add(new TabeleIKolone());

        }


        if(selectStatements.isEmpty()){
            selectStatements.add(new Statement("SELECT", 1));
            selectStatements.add(new Statement("FROM", 2));
            selectStatements.add(new Statement("JOIN", 3));
            selectStatements.add(new Statement("WHERE", 4));
            selectStatements.add(new Statement("GROUP BY", 5));
            selectStatements.add(new Statement("HAVING", 6));
            selectStatements.add(new Statement("ORDER BY", 7));
        }


        if(insertStatements.isEmpty()){
            insertStatements.add(new Statement("INSERT", 1));
            insertStatements.add(new Statement("INTO", 2));
            insertStatements.add(new Statement("VALUES", 3));
        }


        if(deleteStatements.isEmpty()){
            deleteStatements.add(new Statement("DELETE", 1));
            deleteStatements.add(new Statement("FROM", 2));
            deleteStatements.add(new Statement("WHERE", 3));
        }


        if(updateStatements.isEmpty()){
            updateStatements.add(new Statement("UPDATE", 1));
            updateStatements.add(new Statement("SET", 2));
            updateStatements.add(new Statement("WHERE", 3));
        }


        if(keywords.isEmpty()){
            keywords.add("ALTER");
            keywords.add("AND");
            keywords.add("AS");
            keywords.add("BETWEEN");
            keywords.add("BY");
            keywords.add("CASE");
            keywords.add("CAST");
            keywords.add("CONSTRAINT");
            keywords.add("CREATE");
            keywords.add("CROSS");
            keywords.add("CUBE");
            keywords.add("CURRENT_DATE");
            keywords.add("CURRENT_PATH");
            keywords.add("CURRENT_TIME");
            keywords.add("CURRENT_TIMESTAMP");
            keywords.add("CURRENT_USER");
            keywords.add("DEALLOCATE");
            keywords.add("DELETE");
            keywords.add("DESCRIBE");
            keywords.add("DISTINCT");
            keywords.add("DROP");
            keywords.add("ELSE");
            keywords.add("END");
            keywords.add("ESCAPE");
            keywords.add("EXCEPT");
            keywords.add("EXECUTE");
            keywords.add("EXISTS");
            keywords.add("EXTRACT");
            keywords.add("FALSE");
            keywords.add("FIRST");
            keywords.add("FOR");
            keywords.add("FROM");
            keywords.add("FULL");
            keywords.add("GROUP");
            keywords.add("GROUP BY");
            keywords.add("GROUPING");
            keywords.add("HAVING");
            keywords.add("IN");
            keywords.add("INNER");
            keywords.add("INSERT");
            keywords.add("INTERSECT");
            keywords.add("INTO");
            keywords.add("IS");
            keywords.add("JOIN");
            keywords.add("LAST");
            keywords.add("LEFT");
            keywords.add("LIKE");
            keywords.add("LOCALTIME");
            keywords.add("LOCALTIMESTAMP");
            keywords.add("NATURAL");
            keywords.add("NORMALIZE");
            keywords.add("NOT");
            keywords.add("NOT LIKE");
            keywords.add("NULL");
            keywords.add("OF");
            keywords.add("ON");
            keywords.add("OR");
            keywords.add("ORDER");
            keywords.add("ORDER BY");
            keywords.add("OUTER");
            keywords.add("PREPARE");
            keywords.add("RECURSIVE");
            keywords.add("RIGHT");
            keywords.add("ROLLUP");
            keywords.add("SELECT");
            keywords.add("TABLE");
            keywords.add("THEN");
            keywords.add("TRUE");
            keywords.add("UNESCAPE");
            keywords.add("UNION");
            keywords.add("UNNEST");
            keywords.add("USING");
            keywords.add("VALUES");
            keywords.add("WHEN");
            keywords.add("WHERE");
            keywords.add("WITH");
            keywords.add("AVG");
            keywords.add("MIN");
            keywords.add("MAX");
            keywords.add("COUNT");
            keywords.add("SUM");
            keywords.add("UPDATE");
            keywords.add("SET");
            keywords.add("REPLACE");
            keywords.add("CREATE");
            keywords.add("FUNCTION");
            keywords.add("PROCEDURE");
            keywords.add("PROC");
            keywords.add("OR");
            keywords.add("BEGIN");
            keywords.add("RETURN");
            keywords.add("DECLARE");
        }


    }

    public boolean check(String query){
        MainFrame.getInstance().setKliknutoP(false);
        mapa.clear();
        mapa = new HashMap<>();
        System.out.println(query);
        String[] str = query.split(" ");
        List<String> st = new ArrayList<>();
        System.out.println(str[0]);
        System.out.println(str[str.length-1]);
        for(int i = 0; i < str.length;i++){

            if(str[i].equalsIgnoreCase("NOT") || str[i].equalsIgnoreCase("not")){
                if(str[i+1].equalsIgnoreCase("LIKE")){
                    st.add("NOT LIKE");
                    i++;
                    continue;
                }

            }

            if(str[i].equalsIgnoreCase("ORDER")){
                if(str[i+1].equalsIgnoreCase("BY")){
                    st.add("ORDER BY");
                    i++;
                    continue;
                }
            }

            if(str[i].equalsIgnoreCase("GROUP")){
                if(str[i+1].equalsIgnoreCase("BY")){
                    st.add("GROUP BY");
                    i++;
                    continue;
                }
            }

            if(i > 0 && str[i].equalsIgnoreCase("as")){
                i++;
                while(!keywords.contains(str[i])){
                    if(str[i].contains(",")){
                        aliasi.add(str[i].replace(",", ""));
                        break;
                    }
                }

                aliasi.add(str[i]);



                int br = 0;
                int i2 = i;
                while(i2< str.length && !str[i2].contains("\"") && !str[i2].contains(",") && !keywords.contains(str[i2].toUpperCase())){
                    if(str[i2].length()>1){
                        br++;
                    }
                    i2++;
                }
                if(br>=1 && str[i2].contains(",")){
                    System.out.println(str[i2]);
                    System.out.println("Aliasi moraju biti pod navodnicima");
                    //return;
                }else {

                    String s = "";
                    if (str[i].length() > 1) {
                        int navodnici = 0;
                        while (!str[i].contains(",")) {
                            System.out.println("WHILE " + str[i]);
                            if (str[i].contains("\"")) {
                                navodnici++;
                                s = (str[i].replace("\"", ""));
                            } else {
                                if (!s.equalsIgnoreCase("")) {
                                    s = s + " ";
                                }
                                s = s + (str[i]);
                            }
                            // if (!s.equalsIgnoreCase("") && !str[i].contains("\""))
                            if (keywords.contains(str[i + 1].toUpperCase())) {
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (str[i].contains("\"")) {
                            s = s + " ";
                            s = s + str[i].replace("\"", "");
                            // s.replace(",", "");
                            if (s.contains(",")) {
                                s = s.replace(",", "");
                            }
                        }
                        System.out.println("STRING S = " + s);

                        continue;
                    }
                }

            }
                if(!str[i].equalsIgnoreCase("as") && str[i].length()>1) {
                    st.add(str[i]);
                }


        }

        System.out.println(st);
        List<Integer> br = new ArrayList<>();

        System.out.println(st);

        int j = 1;
        String key = "";
        for(int i = 0; i < st.size(); i++){
            System.out.println(st.get(i));

            if(keywords.contains(st.get(i).toUpperCase())){
                System.out.println("U IFUUUUUUU");

                if(mapa.containsKey(st.get(i))){
                    key = st.get(i)+(++j);
                    mapa.put(key, new ArrayList<>());
                }else{
                    mapa.put(st.get(i).toUpperCase(), new ArrayList<>());
                    key = st.get(i).toUpperCase();
                }

            }else {
                String s = st.get(i);
                if(key.equalsIgnoreCase("as") && mapa.get("AS") == null){
                    key = "SELECT";
                }
                if(st.get(i).contains(",")){
                     s = st.get(i).replace(",", "");

                }

                mapa.get(key).add(s);



            }
        }

        System.out.println(mapa);


        if(mapa.containsKey("SELECT")){
            System.out.println("SELECT");
            for (Rule r:rules){
               ispravnost.add(r.check(st, mapa, selectStatements));
            }
        }else {
            if(mapa.containsKey("INSERT")){
                for (Rule r:rules){
                    ispravnost.add(r.check(st, mapa, insertStatements));
                }
            }else{
                if(mapa.containsKey("DELETE")){
                    for (Rule r:rules){
                        ispravnost.add(r.check(st, mapa, deleteStatements));
                    }
                }else {
                    if(mapa.containsKey("UPDATE")){
                        for (Rule r:rules){
                            ispravnost.add(r.check(st, mapa, updateStatements));
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, st.get(0) + " neispravna rec.");
                    }
                }
            }
        }

        if(ispravnost.contains(false)){
            return false;
        }else {
            return true;
        }

    }
}
