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
            rules.add(new ObavezniDeo());
            rules.add(new Redosled());
            //rules.add(new ObavezniDelovi());
            rules.add(new JoinOn());
            rules.add(new TabeleKolone());
            rules.add(new Where());
            rules.add(new GroupBy());
        //    rules.add(new BI_Csv());
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

    public boolean che(String query) {
        ispravnost.clear();
        mapa.clear();

        String[] s = query.split(" ");
        List<String> keys = new ArrayList<>();
        for (String st : s) {
            if (keywords.contains(st.toUpperCase())) {
                keys.add(st.toUpperCase());
            }
        }

        ispravnost.add(rules.get(0).check(keys, mapa, null));
        if (keys.get(0).equalsIgnoreCase("SELECT")) {
            ispravnost.add(rules.get(1).check(keys, mapa, selectStatements));
        } else {
            if (keys.get(0).equalsIgnoreCase("INSERT")) {
                ispravnost.add(rules.get(1).check(keys, mapa, insertStatements));
            } else {
                if (keys.get(0).equalsIgnoreCase("DELETE")) {
                    ispravnost.add(rules.get(1).check(keys, mapa, deleteStatements));
                } else {
                    if (keys.get(0).equalsIgnoreCase("UPDATE")) {
                        ispravnost.add(rules.get(1).check(keys, mapa, updateStatements));
                    } else {
                        JOptionPane.showMessageDialog(null, keys.get(0) + " je neispravna vrednost.");
                        ispravnost.add(false);

                    }


                }
            }
        }


        if(keys.contains("SELECT") || keys.contains("DELETE")){
            List<String> l = new ArrayList<>();
            for(int i = 0; i < s.length; i++){
                if(s[i].equalsIgnoreCase("FROM")){
                    int j = i+1;
                    while(true){
                        if(j >= s.length || keys.contains(s[j].toUpperCase())){
                            break;
                        }
                        l.add(s[j]);
                        j++;
                    }
                }
            }
            List<String> from = new ArrayList<>();
            from.add("FROM");
            ispravnost.add(rules.get(3).check(from, mapa, l));
        }


        if(keys.contains("JOIN")){

            Map<String, List<String>> list = new HashMap<>();
            for(int i = 0; i < s.length; i++){
                if(s[i].equalsIgnoreCase("JOIN")){
                    String key = "JOIN";
                    list.put(key, new ArrayList<>());
                    list.get(key).add(s[i+1]);
                    int j = i+2;
                    while(true){
                        if(j >= s.length || s[j].equalsIgnoreCase("WHERE")){
                            break;
                        }
                        if(s[j].equalsIgnoreCase("ON") || s[j].equalsIgnoreCase("USING")){
                            key = s[j].toUpperCase();
                            list.put(key, new ArrayList<>());
                        }else {
                            list.get(key).add(s[j]);
                        }

                        j++;
                    }
                }
            }

            ispravnost.add(rules.get(2).check(keys, mapa, list));
        }


        if(keys.contains("UPDATE")){
            List<String> l = new ArrayList<>();
            for(int i = 0; i < s.length; i++){
                if(s[i].equalsIgnoreCase("UPDATE")){
                    List<String> from = new ArrayList<>();
                    from.add("TABELA");
                    ispravnost.add(rules.get(3).check(from, mapa, s[i+1]));
                    mapa.get("UPDATE").add(s[i+1]);
                    int j = i+3;
                    while(true){
                        if(j >= s.length){
                            break;
                        }
                        if(!s[j].equalsIgnoreCase("AND") && !s[j].equalsIgnoreCase("OR")
                                && !s[j].equalsIgnoreCase("WHERE")){
                            l.add(s[j]);
                        }

                        j++;
                    }
                }
            }
            List<String> from = new ArrayList<>();
            from.add("UPDATE");
            ispravnost.add(rules.get(3).check(from, mapa, l));

            if(ispravnost.contains(false)){
                return false;
            }else {
                return true;
            }

        }

        //aliasi i vrednosti u select
        if(mapa.containsKey("SELECT")){
            System.out.println("CHECKER");
            List<String> l = new ArrayList<>();
            for(int i = 0; i < s.length; i++){
                if(s[i].equalsIgnoreCase("SELECT")){
                    int j = i+1;
                    while(true){
                        System.out.println(s[j]);
                        if(j >= s.length || (keys.contains(s[j].toUpperCase())&&!s[j].equalsIgnoreCase("as"))){
                            break;
                        }
                        if(s[j].equalsIgnoreCase("AS")){
                            System.out.println("if as");
                            int k = j+1;
                            List<String> aliasi = new ArrayList<>();
                            while(true){
                                System.out.println("while2");
                                if(k >= s.length || keys.contains(s[k].toUpperCase())) {
                                    break;
                                }
                                if(s[k].contains(",")){
                                    aliasi.add(s[k].replace(",", ""));
                                    break;
                                }
                                aliasi.add(s[k]);
                                System.out.println(s[k]);
                                k++;
                            }
                            System.out.println("van while");
                            if(aliasi.size()>1){
                                System.out.println("provera aliasa");
                                if(!aliasi.get(0).startsWith("\"") || !aliasi.get(aliasi.size()-1).endsWith("\"")){
                                    JOptionPane.showMessageDialog(null, "Aliasi moraju biti pod navodnicima");
                                    ispravnost.add(false);
                                    break;
                                }
                            }
                            if(aliasi.size()==1){
                                if((aliasi.get(0).startsWith("\"") && !aliasi.get(0).endsWith("\""))
                                || (!aliasi.get(0).startsWith("\"") && aliasi.get(0).endsWith("\""))){
                                    JOptionPane.showMessageDialog(null, "Neispravan alias " + aliasi.get(0));
                                    ispravnost.add(false);
                                    break;
                                }
                            }
                            j = k;
                        }else {
                            l.add(s[j].replace(",", ""));
                        }
                        if(s[j].equalsIgnoreCase("FROM")){
                            break;
                        }
                        j++;
                    }
                    i = j;
                }
            }
            List<String> from = new ArrayList<>();
            from.add("SELECT");
            ispravnost.add(rules.get(3).check(from, mapa, l));


        }



        if(keys.contains("WHERE")){
            mapa.put("WHERE", new ArrayList<>());
            List<String> list = new ArrayList<>();
            for(int i = 0; i < s.length; i++) {
                if (s[i].equalsIgnoreCase("WHERE")) {
                    int j = i + 1;
                    while (true) {
                        if (j >= s.length || keys.contains(s[j].toUpperCase())) {
                            if(j>=s.length ) {
                                break;
                            }
                            if(s[j].equalsIgnoreCase("IN")){
                                mapa.put("IN", new ArrayList<>());
                                List<String> list1 = new ArrayList<>();
                                int m = j+1;
                                while(true){
                                    if(m >= s.length || keys.contains(s[m])){
                                        break;
                                    }
                                    list1.add(s[m]);
                                    m++;
                                }
                                if(!list1.get(0).startsWith("(") && !list1.get(list1.size()-1).endsWith(")")){
                                    JOptionPane.showMessageDialog(null, "Neispravni argumenti u IN-u");
                                    ispravnost.add(false);
                                    break;
                                }
                            }

                        }
                        list.add(s[j]);
                        j++;
                    }
                }

            }
            List<String> from = new ArrayList<>();
            from.add("WHERE");
            ispravnost.add(rules.get(4).check(from, mapa, list));
            ispravnost.add(rules.get(3).check(from, mapa, list));
         }


        if(keys.contains("GROUP")){
            List<String> l = new ArrayList<>();
            for(int i = 0; i < s.length; i++){
                if(s[i].equalsIgnoreCase("GROUP")){
                    int j = i+2;
                    while(true){
                        if(j >= s.length || keys.contains(s[j].toUpperCase())){
                            break;
                        }
                        if(s[j].contains(",")){
                            l.add(s[j].replace(",", ""));
                        }else {
                            l.add(s[j]);
                        }
                        j++;
                    }
                }
            }

            ispravnost.add(rules.get(5).check(mapa.get("SELECT"), mapa, l));
            List<String> from = new ArrayList<>();
            from.add("GROUP");
            ispravnost.add(rules.get(3).check(from, mapa, l));

        }


        if(keys.contains("INSERT")){
            List<String> l = new ArrayList<>();
            for(int i = 0; i < s.length; i++){
                if(s[i].equalsIgnoreCase("INTO")){
                    List<String> from = new ArrayList<>();
                    from.add("TABELA");
                    ispravnost.add(rules.get(3).check(from, mapa, s[i+1]));
                    mapa.get("INSERT INTO").add(s[i+1]);
                    int j = i+2;
                    while(true){
                        if(j >= s.length || keys.contains(s[j].toUpperCase())){
                            break;
                        }
                        String st = s[j];
                        if(s[j].contains(",")){
                            st = s[j].replace(",", "");
                        }
                        if(st.contains("(")){
                            st = st.replace("(", "");
                        }
                        if(st.contains(")")){
                            st = st.replace(")", "");
                        }
                        l.add(st);

                        j++;
                    }
                }
            }

            List<String> from = new ArrayList<>();
            from.add("INSERT");
            ispravnost.add(rules.get(3).check(from, mapa, l));


        }





            if (ispravnost.contains(false)) {
                    return false;
                } else {
                    return true;
                }



        }

//    public boolean check(String query){
//        MainFrame.getInstance().setKliknutoP(false);
//        mapa.clear();
//        mapa = new HashMap<>();
//        System.out.println(query);
//        String[] str = query.split(" ");
//        List<String> st = new ArrayList<>();
//        System.out.println(str[0]);
//        System.out.println(str[str.length-1]);
//        for(int i = 0; i < str.length;i++){
//
//            if(str[i].equalsIgnoreCase("NOT") || str[i].equalsIgnoreCase("not")){
//                if(str[i+1].equalsIgnoreCase("LIKE")){
//                    st.add("NOT LIKE");
//                    i++;
//                    continue;
//                }
//
//            }
//
//            if(str[i].equalsIgnoreCase("ORDER")){
//                if(str[i+1].equalsIgnoreCase("BY")){
//                    st.add("ORDER BY");
//                    i++;
//                    continue;
//                }
//            }
//
//            if(str[i].equalsIgnoreCase("GROUP")){
//                if(str[i+1].equalsIgnoreCase("BY")){
//                    st.add("GROUP BY");
//                    i++;
//                    continue;
//                }
//            }
//
//            if(i > 0 && str[i].equalsIgnoreCase("as")){
//                i++;
//                while(!keywords.contains(str[i])){
//                    if(str[i].contains(",")){
//                        aliasi.add(str[i].replace(",", ""));
//                        break;
//                    }
//                }
//
//                aliasi.add(str[i]);
//
//
//
//                int br = 0;
//                int i2 = i;
//                while(i2< str.length && !str[i2].contains("\"") && !str[i2].contains(",") && !keywords.contains(str[i2].toUpperCase())){
//                    if(str[i2].length()>1){
//                        br++;
//                    }
//                    i2++;
//                }
//                if(br>=1 && str[i2].contains(",")){
//                    System.out.println(str[i2]);
//                    System.out.println("Aliasi moraju biti pod navodnicima");
//                    //return;
//                }else {
//
//                    String s = "";
//                    if (str[i].length() > 1) {
//                        int navodnici = 0;
//                        while (!str[i].contains(",")) {
//                            System.out.println("WHILE " + str[i]);
//                            if (str[i].contains("\"")) {
//                                navodnici++;
//                                s = (str[i].replace("\"", ""));
//                            } else {
//                                if (!s.equalsIgnoreCase("")) {
//                                    s = s + " ";
//                                }
//                                s = s + (str[i]);
//                            }
//                            // if (!s.equalsIgnoreCase("") && !str[i].contains("\""))
//                            if (keywords.contains(str[i + 1].toUpperCase())) {
//                                break;
//                            } else {
//                                i++;
//                            }
//                        }
//                        if (str[i].contains("\"")) {
//                            s = s + " ";
//                            s = s + str[i].replace("\"", "");
//                            // s.replace(",", "");
//                            if (s.contains(",")) {
//                                s = s.replace(",", "");
//                            }
//                        }
//                        System.out.println("STRING S = " + s);
//
//                        continue;
//                    }
//                }
//
//            }
//                if(!str[i].equalsIgnoreCase("as") && str[i].length()>1) {
//                    st.add(str[i]);
//                }
//
//
//        }
//
//        System.out.println(st);
//        List<Integer> br = new ArrayList<>();
//
//        System.out.println(st);
//
//        int j = 1;
//        String key = "";
//        for(int i = 0; i < st.size(); i++){
//            System.out.println(st.get(i));
//
//            if(keywords.contains(st.get(i).toUpperCase())){
//                System.out.println("U IFUUUUUUU");
//
//                if(mapa.containsKey(st.get(i))){
//                    key = st.get(i)+(++j);
//                    mapa.put(key, new ArrayList<>());
//                }else{
//                    mapa.put(st.get(i).toUpperCase(), new ArrayList<>());
//                    key = st.get(i).toUpperCase();
//                }
//
//            }else {
//                String s = st.get(i);
//                if(key.equalsIgnoreCase("as") && mapa.get("AS") == null){
//                    key = "SELECT";
//                }
//                if(st.get(i).contains(",")){
//                     s = st.get(i).replace(",", "");
//
//                }
//
//                mapa.get(key).add(s);
//
//
//
//            }
//        }
//
//        System.out.println(mapa);
//
//
//        if(mapa.containsKey("SELECT")){
//            System.out.println("SELECT");
//            for (Rule r:rules){
//               ispravnost.add(r.check(st, mapa, selectStatements));
//            }
//        }else {
//            if(mapa.containsKey("INSERT")){
//                for (Rule r:rules){
//                    ispravnost.add(r.check(st, mapa, insertStatements));
//                }
//            }else{
//                if(mapa.containsKey("DELETE")){
//                    for (Rule r:rules){
//                        ispravnost.add(r.check(st, mapa, deleteStatements));
//                    }
//                }else {
//                    if(mapa.containsKey("UPDATE")){
//                        for (Rule r:rules){
//                            ispravnost.add(r.check(st, mapa, updateStatements));
//                        }
//                    }else{
//                        JOptionPane.showMessageDialog(null, st.get(0) + " neispravna rec.");
//                    }
//                }
//            }
//        }
//
//        if(ispravnost.contains(false)){
//            return false;
//        }else {
//            return true;
//        }
//
//    }
        }

