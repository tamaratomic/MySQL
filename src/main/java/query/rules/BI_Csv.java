package query.rules;

import gui.MainFrame;
import query.Rule;
import tree.TreeItem;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class BI_Csv implements Rule {

    private String name = "BI_Csv";

    @Override
    public boolean check(List<String> query, Map<String, List<String>> map, Object object) {


        TreeItem node = (TreeItem) MainFrame.getInstance().getJTree().getLastSelectedPathComponent();
        String tabela = " ";
        try {
            tabela = node.getName();
        }catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Tabela mora biti selektovana");
            return false;
        }

        String csvFilePath = "./"+tabela+".csv";

        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));

            String lineText = null;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                if (tabela.equals("countries")) {

                    if(data.length != 3)
                        JOptionPane.showMessageDialog(null, "Prosledjeni broj kolona nije odovarajuci za tabelu " + tabela);
                       // return false;
                    String country_name = data[0];
                    String region_id = data[1];
                    String country_id = data[2];

                    try {
                        int i_region_id = Integer.parseInt(region_id);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu region_id");
                        return false;
                    }

                    if(country_id.length() > 2){
                        JOptionPane.showMessageDialog(null, "country_id je predugacak");
                        return false;
                    }


                } else if (tabela.equals("departments")){

                    if(data.length != 4)
                        JOptionPane.showMessageDialog(null, "Prosledjeni broj kolona nije odovarajuci za tabelu " + tabela);
                   // return false;
                    String department_id = data[0];
                    String manager_id = data[1];
                    String department_name = data[2];
                    String location_id = data[3];


                    try {
                        int i_department_id = Integer.parseInt(department_id);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu department_id");
                        return false;
                    }

                    try {
                        int i_manager_id = Integer.parseInt(manager_id);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu manager_id");
                        return false;
                    }

                    try {
                        int i_location_id = Integer.parseInt(location_id);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu location_id");
                        return false;
                    }

                }

                else if (tabela.equals("employees")){

                    if(data.length != 11)
                        JOptionPane.showMessageDialog(null, "Prosledjeni broj kolona nije odovarajuci za tabelu " + tabela);
                    //return false;
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

                    try {
                        int i_employee_id = Integer.parseInt(employee_id);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu employee_id");
                        return false;
                    }

                    try {
                        int i_manager_id = Integer.parseInt(manager_id);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu manager_id");
                        return false;
                    }

                    try {
                        int i_department_id  = Integer.parseInt(department_id );
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu department_id ");
                        return false;
                    }

                    try {
                        float f_salary  = Float.parseFloat(salary);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu salary ");
                        return false;
                    }

                    try {
                        float f_commission_pct  = Float.parseFloat(commission_pct);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu commission_pct ");

                        return false;
                    }

                    //IllegalArgumentException

                    try{
                        Date d_hire_date = Date.valueOf(hire_date);

                    }catch (IllegalArgumentException e){
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu hire_date ");
                        return false;
                    }

                }

                else if (tabela.equals("jobs")){

                    if(data.length != 4)
                        JOptionPane.showMessageDialog(null, "Prosledjeni broj kolona nije odovarajuci za tabelu " + tabela);
                    //return false;

                    String max_salary = data[0];
                    String job_id = data[1];
                    String min_salary = data[2];
                    String job_title = data[3];

                    if(job_id.length() > 10){
                        JOptionPane.showMessageDialog(null, "job_id je predugacak");
                        return false;
                    }

                    try {
                        float f_min_salary  = Float.parseFloat(min_salary);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu min_salary ");
                        return false;
                    }

                    try {
                        float f_max_salary  = Float.parseFloat(max_salary);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu max_salary ");
                        return false;
                    }

                }

                else if (tabela.equals("job_history")){

                    if(data.length != 5)
                        JOptionPane.showMessageDialog(null, "Prosledjeni broj kolona nije odovarajuci za tabelu " + tabela);
//return false;
                    String end_date = data[0];
                    String department_id = data[1];
                    String job_id = data[2];
                    String employee_id = data[3];
                    String start_date = data[4];


                    try{
                        Date d_end_date = Date.valueOf(end_date);

                    }catch (IllegalArgumentException e){
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu end_date ");
                        return false;
                    }

                    try{
                        Date d_start_date = Date.valueOf(start_date);

                    }catch (IllegalArgumentException e){
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu start_date ");
                        return false;
                    }

                    try {
                        int i_department_id  = Integer.parseInt(department_id );
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu department_id ");
                        return false;
                    }

                    try {
                        int i_employee_id  = Integer.parseInt(employee_id );
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu employee_id ");
                        return false;
                    }

                    try {
                        int i_job_id  = Integer.parseInt(job_id );
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu job_id ");
                        return false;
                    }

                }

                else if (tabela.equals("locations")){

                    if(data.length != 6)
                        JOptionPane.showMessageDialog(null, "Prosledjeni broj kolona nije odovarajuci za tabelu " + tabela);
//return false;
                    String street_address = data[0];
                    String city = data[1];
                    String state_province = data[2];
                    String postal_code = data[3];
                    String location_id = data[4];
                    String country_id = data[5];

                    try {
                        int i_location_id   = Integer.parseInt(location_id  );
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu location_id  ");
                        return false;
                    }

                    if(country_id .length() > 2){
                        JOptionPane.showMessageDialog(null, "country_id je predugacak");
                        return false;
                    }

                }

                else if (tabela.equals("regions")){

                    if(data.length != 2)
                        JOptionPane.showMessageDialog(null, "Prosledjeni broj kolona nije odovarajuci za tabelu " + tabela);
//return false;
                    String region_id = data[0];
                    String region_name = data[1];

                    try {
                        int i_region_id   = Integer.parseInt(region_id);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Prosledjen je pogresan tip za kolonu region_id  ");
                        return false;
                    }
                }
            }
        }  catch (FileNotFoundException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
