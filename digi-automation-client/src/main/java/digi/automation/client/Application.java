package digi.automation.client;

import digi.automation.client.driver.DriverInitializer;

import static digi.automation.client.builder.RuntimeClassBuilder.buildStepClass;
import static digi.automation.client.builder.RuntimeFeatureScriptBuilder.createFeatureFile;

public class Application {

    public static void main(String[] args) throws Throwable {
        //Create feature file
        createFeatureFile();

        //Create step class
        buildStepClass();

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

}



