package digi.automation.client.util;

import digi.automation.client.driver.DriverInitializer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The JsonHelperUtil class use to hold json supportive functions
 */
public class JsonHelperUtil {

    /**
     * This method use to read json object
     *
     * @return : JsonObject => feature object
     */
    public static JSONObject readJsonObjectFromFile() {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            FileReader reader = new FileReader(DriverInitializer
                    .getProperty("jsonFilePath") + "/feature.json");
            Object obj = jsonParser.parse(reader);
            JSONObject featureJsonObject = (JSONObject) obj;
            jsonObject = featureJsonObject;

        } catch (FileNotFoundException e) {
            return jsonObject;
        } catch (IOException e) {
            return jsonObject;
        } catch (ParseException e) {
            return jsonObject;
        }
        return jsonObject;
    }
}
