package com.company;

import java.util.Scanner;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

    public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);

	    System.out.print("Enter square limit: ");//полиморфизм
	    Integer input = scanner.nextInt();
        ArrayOfSquares[] arr = new ArrayOfSquares[input];
        for(int i = 0; i<input; i++){
            arr[i] = new ArrayOfSquares(i+1);
            System.out.println(arr[i].getSquare());
        }
        xmlCreate xmlWrite = xmlCreate.getInstance();
        xmlWrite.setSquareArray(arr);
        createFile fileWrite;
        fileWrite = xmlWrite;
        fileWrite.write();
    }
}

class ArrayOfSquares{
    //инкапсуляция
    private boolean isEven;
    private int square;

    public ArrayOfSquares(int x){
        square = x*x;
        isEven = x%2 == 0 ? true : false;
    }
    public int getSquare(){
        return this.square;
    }
    public boolean getIsEven(){
        return this.isEven;
    }
}

abstract class createFile{
    //инкапсуляция
    private String filePath;

    public createFile(String path){
        this.filePath=path;
    }

    public String getPath() { return filePath; }

    abstract void write();
}

class SizeException extends Exception{

    public SizeException(String message){
        super(message);
    }
}

final class xmlCreate extends createFile{
    private ArrayOfSquares[] squareArray;

    private xmlCreate(){
        super("xmlfile.xml");
    }

    void setSquareArray(ArrayOfSquares[] squareArray){
        this.squareArray = squareArray;
    }

    private static class SingletonHolder {
        public static final xmlCreate HOLDER_INSTANCE = new xmlCreate();
    }

    public static xmlCreate getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    void write() {
            try {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();

                // root element
                Element root = document.createElement("squareArray");
                document.appendChild(root);

                for(int i = 0; i < squareArray.length; i++){
                    Element element = document.createElement("element");
                    root.appendChild(element);

                    Attr attr = document.createAttribute("id");
                    attr.setValue(Integer.toString(i+1));
                    element.setAttributeNode(attr);

                    Element value = document.createElement("square");
                    value.appendChild(document.createTextNode(Integer.toString(squareArray[i].getSquare())));
                    element.appendChild(value);

                    Element isEven = document.createElement("isEven");
                    isEven.appendChild(document.createTextNode(Boolean.toString(squareArray[i].getIsEven())));
                    element.appendChild(isEven);
                }

                //transform the DOM Object to an XML File
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);

                File file = new File(super.getPath());
                StreamResult streamResult = new StreamResult(file);

                StreamResult result = new StreamResult(System.out);

                System.out.println(result.getWriter());
                transformer.transform(domSource, streamResult);
                if(file.length()>40) throw new SizeException("Too large file");
                System.out.println(file.length());
                System.out.println("XML File created");
            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            } catch (SizeException x){
                x.printStackTrace();
            }
        }
}