package digi.automation.client.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.*;


/**
 * The {RuntimeClassGeneratorUtil} class use to create java byte code using javassist.
 */
public class RuntimeClassGeneratorUtil {

    private static final String className = "MyClass";
    private static final String methodName = "printHello";
    private static final String methodBody = "java.lang.System.out.println(\"Hello world!\");";

    /**
     * This method use to execute generated class by javassist
     */
    public static void execute() {
        try {
            // Use our static method to make a magic
            Class clazz = generateClass(className, methodName, methodBody);
            // Create a new instance of our newly generated class
            Object obj = clazz.newInstance();
            // Find our method in generated class
            Method method = clazz.getDeclaredMethod(methodName);
            // And finally invoke it on instance
            method.invoke(obj);
            //System.out.print(clazz);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method use to generate bytecode
     *
     * @param className:class name
     * @param methodName      : method name
     * @param methodBody      : method body
     * @return : Class
     * @throws CannotCompileException :CannotCompileException
     */
    public static Class generateClass(String className, String methodName, String methodBody)
            throws CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);
        StringBuffer method = new StringBuffer();
        method.append("public void ")
                .append(methodName)
                .append("() {")
                .append(methodBody)
                .append(";}");
        cc.addMethod(CtMethod.make(method.toString(), cc));
        return cc.toClass();
    }
}
