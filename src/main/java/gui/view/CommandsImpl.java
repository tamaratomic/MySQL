package gui.view;



import com.github.vertical_blank.sqlformatter.SqlFormatter;
import gui.Commands;
import gui.model.TableModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
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
    public void bulkImport() {


        String username = "bp_tim24";
        String password = "mmhmUUnS";

        String csvFilePath = "./rez.csv";

        int batchSize = 20;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+"164.92.145.191"+"/"+"bp_tim24",username,password);

            connection.setAutoCommit(false);

            String sql = "INSERT INTO  countries (country_name, region_id, country_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String country_name = data[0];
                String region_id = data[1];
                String country_id = data[2];


                statement.setString(1, country_name);

                int i_region_id = Integer.parseInt(region_id);
                statement.setInt(2, i_region_id);




                statement.setString(3, country_id);

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

        } catch (SQLException e) {
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
