package digi.automation.client.builder;

import digi.automation.client.driver.DriverInitializer;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static digi.automation.client.util.JsonHelperUtil.readJsonObjectFromFile;

/**
 * The RuntimeFeatureScriptBuilder class use to build cucumber feature file
 */
public class RuntimeFeatureScriptBuilder {

    /**
     * This method use to create feature in the resource folder
     */
    public static void createFeatureFile() {

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
        final StringBuilder featureBuilder = new StringBuilder();
        featureBuilder.append("Feature")
                .append(":")
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
}
