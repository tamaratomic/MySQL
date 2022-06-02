package database.settings;

public interface Settings {

    //prosledjujemo podatke ime baze sifru i ostalo
    Object getParameter(String parameter);
    void addParameter(String parameter, Object value);
}
