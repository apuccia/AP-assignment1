/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuccia.assignment1.exercise12;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alessandro Puccia
 */
public class XMLDeserializer {
    
    public final static double XMLVersion= 1.0;
    public final static Charset encoding = Charset.forName("UTF-8");

    public XMLDeserializer() {
    }
    
    public Object[] deserialize(String fileName, Class objectsClass) {
        if (isSerializable(objectsClass)) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new File(fileName));

                doc.getDocumentElement().normalize();

                Element rootElement = doc.getDocumentElement();
                NodeList nodeList = rootElement.getChildNodes();

                ArrayList<Object> deserializedObjects = new ArrayList<>();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Object obj = getEmptyConstructor(objectsClass).newInstance();
                        
                        for (Field field : objectsClass.getDeclaredFields()) {
                            field.setAccessible(true);
                            Element elementNode = (Element) node;
                            XMLfield annotation = field.getAnnotation(XMLfield.class);
                               
                            Node XMLFieldToDeserialize;
                            if (annotation.name().equals("")) {
                                XMLFieldToDeserialize = elementNode.getElementsByTagName(field.getName()).item(0);
                            }
                            else {
                                XMLFieldToDeserialize = elementNode.getElementsByTagName(annotation.name()).item(0);
                            }
                                
                                
                            Object deserializedField = deserializeField(
                                    XMLFieldToDeserialize.getTextContent(),
                                    XMLFieldToDeserialize.getAttributes().getNamedItem("type").getNodeValue());
                            System.out.println(deserializedField);
                                
                            try {
                                field.set(obj, deserializedField);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(XMLDeserializer.class.getName()).
                                        log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(XMLDeserializer.class.getName()).
                                        log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        deserializedObjects.add(obj);
                    }
                }
                
                return deserializedObjects.toArray();
            } catch (IOException | ParserConfigurationException | SAXException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(XMLDeserializer.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
    public boolean isSerializable(Class className) {
        if (!className.isAnnotationPresent(XMLable.class) && getEmptyConstructor(className) != null) {
            return false;
        }
        
        Field[] classFields = className.getDeclaredFields();
        
        for (Field field : classFields) {
            field.setAccessible(true);
            
            if (Modifier.isStatic(field.getModifiers())
                    && (!field.getType().isPrimitive() || !field.getType().equals(String.class))
                    && !field.isAnnotationPresent(XMLfield.class)) {
                
                return false;
            }
        }
        
        return true;
    }
    
    private Constructor getEmptyConstructor(Class objectClass) {
        try {
            return objectClass.getDeclaredConstructor();
                    } catch (NoSuchMethodException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).
                    log(Level.SEVERE, null, ex);
            
            return null;
        } catch (SecurityException ex) {
            Logger.getLogger(XMLDeserializer.class.getName()).
                    log(Level.SEVERE, null, ex);
            
            return null;
        }
    }
    
    private Object deserializeField(String fieldToDeserialize, String type) {
        Object field;
        switch (type) {
            case "int":
                field = Integer.parseInt(fieldToDeserialize);
                break;
            case "float":
                field = Float.parseFloat(fieldToDeserialize);
                break;
            case "double":
                field = Double.parseDouble(fieldToDeserialize);
                break;
            case "short":
                field = Short.parseShort(fieldToDeserialize);
                break;
            case "long":
                field = Long.parseLong(fieldToDeserialize);
                break;
            case "boolean":
                field = Boolean.parseBoolean(fieldToDeserialize);
                break;
            case "char":
                field = fieldToDeserialize.charAt(0);
                break;
            case "String":
                field = fieldToDeserialize;
                break;
            default:
                field = null;
                break;
        }
        
        return field;
    }
}
