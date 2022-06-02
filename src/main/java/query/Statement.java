package query;


import lombok.Getter;

@Getter
public class Statement {

    private String name;
    private int priority;


    public Statement(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }




}
