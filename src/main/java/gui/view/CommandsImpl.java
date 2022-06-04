package gui.view;



import com.github.vertical_blank.sqlformatter.SqlFormatter;
import gui.Commands;
import gui.model.TableModel;
import tree.TreeItem;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;


public class CommandsImpl implements Commands {
    List<String> keywords;


    public void ulepsaj(JTextPane textPane){
        keywords = new ArrayList<>();

        keywords = addKeyWords();

        String text = textPane.getText();
        String formatiranText = SqlFormatter.format(text);



        for(String keyword:keywords) {
            String p = keyword;
            Pattern pt = Pattern.compile(p);
            Matcher m = pt.matcher(formatiranText);
            while (m.find()) {
                int strt = m.start();
                int end = m.end();
                String s1 = m.group();
                String s11 = s1.toUpperCase();
                String s2 = formatiranText.substring(0, strt);
                String s3 = formatiranText.substring(end, formatiranText.length());
                formatiranText = s2 + s11 + s3;

            }
        }

        textPane.setText(formatiranText);

        /* StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("", null);
        StyleConstants.setForeground(style, Color.BLUE);

       try {
            doc.insertString(doc.getLength(), "select", style);
            doc.insertString(doc.getLength(), "*", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }*/


        /*Pattern p = Pattern.compile("\\b[A-Z]{4,}\\b");
        Matcher m = p.matcher(formatiranText);
        while (m.find()) {
            String word = m.group();
            System.out.println(word);
        }*/




    }

    @Override
    public void export(JTable table) {
        try
        {
            File archiv = new File("rez.csv");
            TableModel model = (TableModel) table.getModel();
            FileWriter writter = new FileWriter(archiv);
            for(int i = 0; i < model.getColumnCount(); i++)
            {
                writter.write(model.getColumnName(i) + ",");
            }
            writter.write("\n");

            for(int i=0; i< model.getRowCount(); i++)
            {
                for(int j=0; j < model.getColumnCount(); j++)
                {
                    String data = (String)model.getValueAt(i, j);
                    if(data == "null")
                    {
                        data="";
                    }
                    writter.write(data+",");
                }
                writter.write("\n");
            }

            writter.close();
            JOptionPane.showMessageDialog(null, "CSV fajl kreiran");

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void bulkImport(Object lastSelectedPathComponent) {


        TreeItem node = (TreeItem)lastSelectedPathComponent;
        String tabela = " ";
        try {
            tabela = node.getName();
        }catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Tabela mora biti selektovana");
        }


        String username = "bp_tim24";
        String password = "mmhmUUnS";

        String csvFilePath = "./"+tabela+".csv";

        int batchSize = 20;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+"164.92.145.191"+"/"+"bp_tim24",username,password);

            connection.setAutoCommit(false);
            String sql = null;
            if(tabela.equals("countries"))
                sql = "INSERT INTO " + tabela + "(country_name, region_id, country_id) VALUES (?, ?, ?)";
            else if(tabela.equals("departments"))
                sql = "INSERT INTO " + tabela + "(department_id, manager_id, department_name, location_id) VALUES (?, ?, ?, ?)";
            else if(tabela.equals("employees"))
                sql = "INSERT INTO " + tabela + "(commission_pct, manager_id, department_id, job_id, employee_id, last_name, phone_number, hire_date, salary, first_name, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            else if(tabela.equals("jobs"))
                sql = "INSERT INTO " + tabela + "(max_salary, job_id, min_salary, job_title) VALUES (?, ?, ?, ?)";
            else if(tabela.equals("job_history"))
                sql = "INSERT INTO " + tabela + "(end_date, department_id, job_id, employee_id, start_date) VALUES (?, ?, ?, ?, ?)";


            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                if(tabela.equals("countries")) {
                    String country_name = data[0];
                    String region_id = data[1];
                    String country_id = data[2];



                    statement.setString(1, country_name);

                    int i_region_id = Integer.parseInt(region_id);
                    statement.setInt(2, i_region_id);

                    statement.setString(3, country_id);
                }
                else if(tabela.equals("departments")) {
                    String department_id = data[0];
                    String manager_id = data[1];
                    String department_name = data[2];
                    String location_id = data[3];

                    int i_department_id = Integer.parseInt(department_id);
                    statement.setInt(1, i_department_id);

                    int i_manager_id = Integer.parseInt(manager_id);
                    statement.setInt(2, i_manager_id);

                    statement.setString(3, department_name);

                    int i_location_id = Integer.parseInt(location_id);
                    statement.setInt(4, i_location_id);


                }

                else if(tabela.equals("jobs")) {
                    String max_salary = data[0];
                    String job_id = data[1];
                    String min_salary = data[2];
                    String job_title = data[3];

                    float f_max_salary = Float.parseFloat(max_salary);
                    statement.setFloat(1, f_max_salary);

                    statement.setString(2, job_id);

                    float f_min_salary = Float.parseFloat(min_salary);
                    statement.setFloat(3, f_min_salary);

                    statement.setString(4, job_title);

                }
                else if(tabela.equals("job_history")) {
                    String end_date = data[0];
                    String department_id = data[1];
                    String job_id = data[2];
                    String employee_id = data[3];
                    String start_date = data[4];

                    Date d_end_date = Date.valueOf(end_date);
                    statement.setDate(1, d_end_date);

                    int i_department_id = Integer.valueOf(department_id);
                    statement.setInt(2, i_department_id);

                    statement.setString(3, job_id);

                    int i_employee_id = Integer.valueOf(employee_id);
                    statement.setInt(4, i_employee_id);

                    Date d_start_date = Date.valueOf(start_date);
                    statement.setDate(5, d_start_date);

                }
                else if(tabela.equals("employees")) {
                    String commission_pct = data[0];
                    String manager_id = data[1];
                    String department_id = data[2];
                    String job_id = data[3];
                    String employee_id = data[4];
                    String last_name = data[5];
                    String phone_number = data[6];
                    String hire_date = data[7];
                    String salary = data[8];
                    String first_name = data[9];
                    String email = data[10];

                    if(!(commission_pct.equals("null"))){
                        float f_commission_pct = Float.parseFloat(commission_pct);
                        statement.setFloat(1, f_commission_pct);
                    }else{
                        statement.setNull(1, Types.NULL);
                    }

                    if(!(manager_id.equals("null"))) {
                        int i_manager_id = Integer.parseInt(manager_id);
                        statement.setInt(2, i_manager_id);
                    }else{
                        statement.setNull(2, Types.NULL);
                    }

                    int i_department_id = Integer.parseInt(department_id);
                    statement.setInt(3, i_department_id);

                    statement.setString(4, job_id);

                    int i_employee_id = Integer.parseInt(employee_id);
                    statement.setInt(5, i_employee_id);

                    statement.setString(6, last_name);

                    statement.setString(7, phone_number);

                    Date d_hire_date = Date.valueOf(hire_date);
                    statement.setDate(8, d_hire_date);

                    float f_salary = Float.parseFloat(salary);
                    statement.setFloat(9, f_salary);

                    statement.setString(10, first_name);

                    statement.setString(11, email);
                }



                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

            JOptionPane.showMessageDialog(null, "Uspesno dodavanje entiteta u tabelu " + tabela);

        } catch (BatchUpdateException e) {
            JOptionPane.showMessageDialog(null, "Vrednost iz CSV fajla se vec nalazi u bazi");
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }







        /*try (
                Reader reader = Files.newBufferedReader(Paths.get("./rez.csv"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for(CSVRecord csvRecord : csvParser){
                String countryName = csvRecord.get(0);
                String regionId = csvRecord.get(1);
                String countryId = csvRecord.get(2);


            }

        }catch (IOException e){
            e.printStackTrace();
        }*/
    }


    private List<String> addKeyWords(){
        List<String> keywords = new ArrayList<>();

        keywords.add("create");
        keywords.add("primary key");
        keywords.add("insert");
        keywords.add("select");
        keywords.add("from");
        keywords.add("alter");
        keywords.add("add");
        keywords.add("distinct");
        keywords.add("update");
        keywords.add("set");
        keywords.add("delete");
        keywords.add("truncate");
        keywords.add("as");
        keywords.add("order by");
        keywords.add("asc");
        keywords.add("desc");
        keywords.add("between");
        keywords.add("where");
        keywords.add("and");
        keywords.add("or");
        keywords.add("not");
        keywords.add("limit");
        keywords.add("is null");
        keywords.add("drop");
        keywords.add("drop column");
        keywords.add("drop database");
        keywords.add("drop table");
        keywords.add("group by");
        keywords.add("having");
        keywords.add("right join");
        keywords.add("join");
        keywords.add("in");
        keywords.add("union");
        keywords.add("union all");
        keywords.add("exists");
        keywords.add("like");
        keywords.add("case");




        return keywords;
    }
}
