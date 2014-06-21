import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by seyren on 6/16/14.
 */
public class JsonTest {
    public static void main(String args[]) {
        String json = "{    \"error\":0,    \"status\":\"success\",    \"date\":\"2014-06-16\",    \"results\":[        {            \"currentCity\":\"林\",            \"weather_data\":[      {                     }]}]}";
        JSONObject jsonObject = new JSONObject(json);
        System.out.println(jsonObject);
        JSONObject data = jsonObject.getJSONObject("results");
        JSONObject jsona = data.getJSONObject("weather_data");
        System.out.print(jsona);
    }
}
