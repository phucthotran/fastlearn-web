package library;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import anotation.*;

/**
 *
 * @author ITExplore
 */
public class ModelDriven {

    private static HttpServletRequest request;

    public static void setRequest(HttpServletRequest servletRequest) {
        request = servletRequest;
    }

    public static String[] getPropertyNames() {
        ArrayList<String> propertyNames = new ArrayList<String>();
        String[] pNames;

        for(String name : request.getParameterMap().keySet()) {
            propertyNames.add(name);
        }

        pNames = new String[propertyNames.size()];

        for(int i = 0; i < propertyNames.size(); i++) {
            pNames[i] = propertyNames.get(i);
        }

        return pNames;
    }

    public static String[] getPropertyValues() {
        ArrayList<String> propertyValues = new ArrayList<String>();
        String[] pValues;

        for(String[] values : request.getParameterMap().values()) {
            for(String value : values) {
                propertyValues.add(value);
            }
        }

        pValues = new String[propertyValues.size()];
        
        for(int i = 0; i < propertyValues.size(); i++) {
            pValues[i] = propertyValues.get(i);
        }

        return pValues;
    }

    public static void parser(Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Field[] fields = clazz.getDeclaredFields();
        String[] pNames = null;
        String[] pValues = null;
        int pNames_FIELD_POS = -1;
        int pValues_FIELD_POS = -1;
        ArrayList<Field> pNames_FIELD = new ArrayList<Field>();
        ArrayList<Field> pValues_FIELD = new ArrayList<Field>();
        ArrayList<Field> MDO_FIELD = new ArrayList<Field>();

        for(Field f :fields) {
            f.setAccessible(true);
            if(f.isAnnotationPresent(PropertyNames.class)){                
                pNames_FIELD.add(f);
            }
            else if(f.isAnnotationPresent(PropertyValues.class)){                
                pValues_FIELD.add(f);
            }
            else if(f.isAnnotationPresent(MDO.class)){                
                MDO_FIELD.add(f);
            }
        }

        for(Field f : MDO_FIELD) {            
            String MDO_NAME = f.getAnnotation(MDO.class).name();

            for(int i = 0; i < pNames_FIELD.size(); i++){
                PropertyNames anotation = pNames_FIELD.get(i).getAnnotation(PropertyNames.class);

                if(anotation.name().equalsIgnoreCase(MDO_NAME)) {
                    pNames = (String[])pNames_FIELD.get(i).get(pNames);
                    pNames_FIELD_POS = i;
                    break;
                }
            }

            for(int i = 0; i < pValues_FIELD.size(); i++) {
                PropertyValues anotation = pValues_FIELD.get(i).getAnnotation(PropertyValues.class);

                if(anotation.name().equalsIgnoreCase(MDO_NAME)) {
                    pValues = (String[])pValues_FIELD.get(i).get(pValues);
                    pValues_FIELD_POS = i;
                    break;
                }
            }

            if(pNames  != null && pValues != null) {
                if(pNames_FIELD_POS != -1 && pValues_FIELD_POS != -1){
                    Class<?> c = f.getType();
                    f.set(c, build(c, pNames, pValues));
                }
            }
        }
        /*
        for(Field f : fields) {
            if(f.isAnnotationPresent(PropertyNames.class)){
                PropertyNames anotation = f.getAnnotation(PropertyNames.class);
                pNames_NAME_ATTR = anotation.name();
                pNames = (String[])f.get(pNames);
            }
            else if(f.isAnnotationPresent(PropertyValues.class)){
                PropertyValues anotation = f.getAnnotation(PropertyValues.class);
                pValues_NAME_ATTR = anotation.name();
                pValues = (String[])f.get(pValues);
            }
            else if(f.isAnnotationPresent(MDO.class)){
                MDO anotation = f.getAnnotation(MDO.class);
                MDO_NAME = anotation.name();

                if(pNames  != null && pValues != null) {
                    if(pNames_NAME_ATTR.equalsIgnoreCase(MDO_NAME) && pValues_NAME_ATTR.equalsIgnoreCase(MDO_NAME)){
                        Class<?> c = f.getType();
                        f.set(c, buildObject(c, pNames, pValues));
                    }
                }
            }
        }
        */
    }

    private static Object build(Class<?> objectClass, String[] paramNames, String[] paramValues) {
        Class<?> c = objectClass;
        Object obj = null;
        try {
            obj = objectClass.newInstance();
            //Find "set" method and invoke it
            for (Method m : c.getDeclaredMethods()) {
                if (m.getName().startsWith("set")) {
                    String propertyMethod = m.getName().substring(3);
                    //System.out.println(propertyMethod);
                    for (int i = 0; i < paramNames.length; i++) {
                        String propertyName = paramNames[i];
                        String propertyValue = paramValues[i];

                        if (propertyName.equalsIgnoreCase(propertyMethod)) {
                            Class<?> paramType = m.getParameterTypes()[0];
                            Object convertedObj = propertyValue;
                            if (paramType == Integer.class || paramType == int.class) {
                                convertedObj = Integer.parseInt(propertyValue);
                            } else if (paramType == Double.class || paramType == double.class) {
                                convertedObj = Double.parseDouble(propertyValue);
                            } else if (paramType == Long.class || paramType == long.class) {
                                convertedObj = Long.parseLong(propertyValue);
                            } else if (paramType == Float.class || paramType == float.class) {
                                convertedObj = Float.parseFloat(propertyValue);
                            } else if (paramType == Boolean.class || paramType == boolean.class) {
                                convertedObj = Boolean.parseBoolean(propertyValue);
                            }
                            m.invoke(obj, convertedObj);
                            //Method invokeMethod = c.getDeclaredMethod(m.getName(), new Class[]{ paramType });
                            //invokeMethod.invoke(obj, new Object[] { tmpProperyValue });
                            break;
                        }
                    }
                }
            }            
        } catch (InstantiationException ex) {
            Logger.getLogger(ModelDriven.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ModelDriven.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ModelDriven.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex)         {
            Logger.getLogger(ModelDriven.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj;
    }

}
