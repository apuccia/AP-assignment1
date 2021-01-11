/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuccia.assignment1.exercise23;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 *
 * @author Alessandro Puccia
 */
public class XMLSerializer {

    public final static double XMLVersion= 1.0;
    public final static Charset encoding = Charset.forName("UTF-8");

    public XMLSerializer() {
    }

    public void serialize(Object[] arr, String fileName) throws IllegalAccessException,
            SecurityException,
            IllegalArgumentException {

        Class classToSerialize = arr[0].getClass();

        if (classToSerialize.isAnnotationPresent(XMLable.class)) {
            StringBuilder stringToBuild = new StringBuilder();

            stringToBuild.append("<?xml version=\"")
                    .append(XMLVersion)
                    .append("\" encoding=\"")
                    .append(encoding)
                    .append("\"?>\n");

            stringToBuild.append("<Array>\n");
            for (Object obj : arr) {
                stringToBuild.append("\t<")
                        .append(classToSerialize.getSimpleName())
                        .append(">\n");

                Field[] fields = obj.getClass().getDeclaredFields();

                for (Field field : fields) {
                    XMLfield fieldAnnotation = field.getAnnotation(XMLfield.class);
                    // used in order to access private fields
                    field.setAccessible(true);

                    // checks that the field is annotated and belongs to primitive or String type
                    if (fieldAnnotation != null && checkFieldType(field)) {
                        
                        String name = fieldAnnotation.name().equals("") ? 
                                field.getName() : fieldAnnotation.name().replaceAll("\\s","");

                        stringToBuild.append("\t\t<")
                                .append(name)
                                .append(" type=\"").append(fieldAnnotation.type())
                                .append("\">")
                                .append(field.get(obj))
                                .append("</").append(name).append(">\n");
                    }
                }

                stringToBuild.append("\t</")
                        .append(classToSerialize.getSimpleName())
                        .append(">\n");
            }
            stringToBuild.append("</Array>");
            
            try (FileWriter fw = new FileWriter(new File(fileName), encoding);
                BufferedWriter writer = new BufferedWriter(fw)) {

                writer.write(stringToBuild.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean checkFieldType(Field field) {
        Class fieldType = field.getType();
        
        return fieldType.isPrimitive() || fieldType.equals(String.class);
    }
}
