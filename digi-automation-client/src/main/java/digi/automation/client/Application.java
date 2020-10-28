package digi.automation.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Application {

    public static void main(String[] args) throws Throwable {
        //Create feature file
        createFeatureFile();
        //Execute test process
        executeProcess();
    }

    /**
     * This method use to execute test process
     */
    private static void executeProcess() {
        try {
            cucumber.api.cli.Main.main(new String[]{
                    "--strict",
                    "--glue",
                    "digi.automation.client.steps",
                    DriverInitializer.getProperty("filePath") + "/login.feature"});
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    /**
     * This method use to create feature in the resource folder
     */
    private static void createFeatureFile() {

        try {
            File file = new File(DriverInitializer.getProperty("filePath") + "/login.feature");
            if (file.createNewFile()) {
                writeTestScenarioInTheFile();
            } else {
                writeTestScenarioInTheFile();
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
    }

    /**
     * This method use to write jerkin commands into feature file
     */
    private static void writeTestScenarioInTheFile() {
        try {
            FileWriter fw = new FileWriter(DriverInitializer.getProperty("filePath") + "/login.feature");
            fw.write("");
            fw.flush();
            fw.write(createGherkinScript());
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }


    }

    /**
     * This method use to create Gherkin script
     *
     * @return : String => GherkinScript
     */
    private static String createGherkinScript() {
        JSONObject featureJsonObject = readJsonObjectFromFile();
        final StringBuilder featureBuilder = new StringBuilder("Feature");
        featureBuilder.append(":")
                .append(featureJsonObject.get("Feature")).append("\n")
                .append("Scenario")
                .append(":")
                .append(featureJsonObject.get("Scenario")).append("\n")
                .append("Given ")
                .append(featureJsonObject.get("Given")).append("\n")
                .append("When ")
                .append(featureJsonObject.get("When")).append("\n")
                .append("And ")
                .append(featureJsonObject.get("And")).append("\n")
                .append("Then ")
                .append(featureJsonObject.get("Then")).append("\n");
        return featureBuilder.toString();
    }

    /**
     * This method use to read json object
     *
     * @return : JsonObject => feature object
     */
    private static JSONObject readJsonObjectFromFile() {
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



