package query;

import java.nio.charset.StandardCharsets;

public class Redosled implements Rule{

    private String name = "Redosled iskaza";


    @Override
    public String check(String query) {
        System.out.println(query);

        return null;
    }
}
