package gui.model;

import org.json.JSONObject;

public class DescriptionRepository {

    JSONObject jsonObject;

    public DescriptionRepository() {
        this.jsonObject = new JSONObject();

        jsonObject.put("Name", "No column");
        jsonObject.put("Description", "There is no column named ${#kolona} in table named ${#tabela}");
        jsonObject.put("Sugestion", "Remove column ${#kolona} from query");

    }



    public JSONObject getJsonObject() {
        return jsonObject;
    }

}
