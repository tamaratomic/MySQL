package gui.view;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PrettyImplementation {

    public void ulepsaj(TextArea textArea){
        String text = textArea.getText();
        String formatiranText = SqlFormatter.format(text);

        String p="\\bselect";
        Pattern pt=Pattern.compile(p);
        Matcher m= pt.matcher(formatiranText);
        while ( m.find())
        {  int strt=m.start(); int end=m.end();
            String s1=m.group();
            String s11=s1.toUpperCase();
            String s2=formatiranText.substring(0,strt);
            String s3= formatiranText.substring(end, formatiranText.length());
            formatiranText = s2+s11+ s3;

        }


        textArea.setText(formatiranText);

    }


    /*private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }*/
}
