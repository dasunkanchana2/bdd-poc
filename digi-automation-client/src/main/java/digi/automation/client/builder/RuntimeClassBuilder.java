package digi.automation.client.builder;

import digi.automation.client.driver.DriverInitializer;
import org.json.simple.JSONObject;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import static digi.automation.client.util.JsonHelperUtil.readJsonObjectFromFile;

/**
 * The RuntimeClassBuilder use to build step class dynamically based on the test feature
 */
public class RuntimeClassBuilder {

    /**
     * This method use to build step class
     *
     * @throws :Exception
     */
    public static void buildStepClass() throws Exception {
        // create the source
        File sourceFile = new File(DriverInitializer.getProperty("stepFilePath") + "/Login.java");
        FileWriter writer = new FileWriter(sourceFile);
        final StringBuilder classBuilder = new StringBuilder();
        classBuilder.append("package digi.automation.client.steps;").append("\n").append(importLibraries())
                .append("\n")
                .append(createClass("Login"))
                .append("\n")
                .append(buildStepFunctions());
        writer.flush();
        writer.write(classBuilder.toString() + "\n}");
        writer.close();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler
                .getStandardFileManager(null, null, null);
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays
                .asList(new File(DriverInitializer.getProperty("stepFilePath"))));

        // Compile the file
        compiler.getTask(null,
                fileManager,
                null,
                null,
                null,
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile)))
                .call();
        fileManager.close();
    }

    /**
     * This method use to run the generated file using reflection
     */
    @SuppressWarnings("unchecked")
    public static void runIt() {
        try {
            Class params[] = {};
            Object paramsObj[] = {};
            Class thisClass = Class.forName("Login");
            Object iClass = thisClass.newInstance();
            Method thisMethod = thisClass.getDeclaredMethod("doit", params);
            thisMethod.invoke(iClass, paramsObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method use to build step file functions
     *
     * @return String : content of the step file
     */
    private static String buildStepFunctions() {
        StringBuilder stepBuilder = new StringBuilder();
        JSONObject featureJsonObject = readJsonObjectFromFile();
        String firstName = "";

        //check 'Given'
        if (featureJsonObject.containsKey("Given")) {
            firstName = featureJsonObject.get("Given").toString();
            stepBuilder.append(createGivenFunction(firstName) + contentOfOpenBrowser() + "\n\t}\n\n");
        }

        //check 'When'
        if (featureJsonObject.containsKey("When") || featureJsonObject.containsKey("And")) {
            firstName = featureJsonObject.get("When").toString();
            stepBuilder.append(createWhenFunction(firstName) + contentOfNavigateToLink() + "\n\t}\n\n");
        }

        //check 'And'
        if (featureJsonObject.containsKey("And")) {
            firstName = featureJsonObject.get("And").toString();
            stepBuilder.append(createWhenFunction(firstName) + contentOfNavigateToLink() + "\n\t}\n\n");
        }

        //check 'Then'
        if (featureJsonObject.containsKey("Then")) {
            firstName = featureJsonObject.get("Then").toString();
            stepBuilder.append(createThenFunction(firstName) + contentOfButtonClick("") + "\n\t}\n\n");
        }
        return stepBuilder.toString();
    }

    private static String importLibraries() {
        String libraries = "\nimport digi.automation.client.driver.DriverInitializer;" +
                "\nimport cucumber.api.java.en.*;" +
                "\nimport org.openqa.selenium.*;";
        return libraries;
    }

    private static String createClass(String className) {
        String clzz = "\n\npublic class " + className + "{" +
                "\n\nWebDriver webDriver = null;\n";
        return clzz;
    }

    private static String createGivenFunction(String fname) {
        String trimmedName = fname.replace(" ", "");
        String function = "\t@Given(\"^" + fname + "$\")" +
                "\n\tpublic void " + trimmedName + "() throws Throwable {\n";
        return function;
    }

    private static String createWhenFunction(String fname) {
        String trimmedName = fname.replace(" ", "");
        String function = "\t@When(\"^" + fname + "$\")" +
                "\n\tpublic void " + trimmedName + "() throws Throwable {\n";
        return function;
    }

    private static String createThenFunction(String fname) {
        String trimmedName = fname.replace(" ", "");
        String function = "\t@Then(\"^" + fname + "$\")" +
                "\n\tpublic void " + trimmedName + "() throws Throwable {\n";
        return function;
    }

    private static String contentOfOpenBrowser() {
        String content = "\t\twebDriver = DriverInitializer.getDriver(\"chrome\");";
        return content;
    }

    private static String contentOfNavigateToLink() {
        String content = "\t\twebDriver.get(DriverInitializer.getProperty(\"login.url\"));";
        return content;
    }

    private static String contentOfButtonClick(String id) {
        String content = "\t\ttry {" + "\n\t\t\tWebElement webElement = webDriver.findElement(By.xpath(\"" +
                id + "\"));" + "\n\t\t\twebElement.click();" + "\n\t\t}catch (NoSuchElementException e){" +
                "\n\t\t\tSystem.out.println(e);" + "\n\t\t}";
        return content;
    }

    private static String contentOfGetInputs(String id, String value) {
        String content = "\t\ttry {" + "\n\t\t\tWebElement webElement = webDriver.findElement(By.xpath(\"" +
                id + "\"));" + "\n\t\t\twebElement.sendKeys(\"" + value + "\");" + "\n\t\t}catch (NoSuchElementException e){" +
                "\n\t\t\tSystem.out.println(e);" + "\n\t\t}";

        return content;

    }
}