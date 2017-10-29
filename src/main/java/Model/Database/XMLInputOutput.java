/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Database;

import Model.Datatypes.*;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class for input and output of data to an XML file.
 * There also exists method to create a new XML file if necessary. Whenever the XML-file
 * is referenced, it's location is defined by XMLSrc.
 * 
 * @author fredrikmakila
 */
 @SuppressWarnings("unchecked") 
public class XMLInputOutput {
     //Check the values of the XMLSrc and DTDSrc. Set them to "calDB.dtd" and "taskDB.xml"
     //when building the jar
    private final String XMLSrc = "taskDB.xml";//"./src/taskDB.xml";
    private final char NEWLINE = '\n';
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat tf = new SimpleDateFormat("HH:mm");
            
            
    /**
     * Constructor for this class.
     * Allows other classes to access methods in this class.
     */
    public XMLInputOutput() {
        initialize();
    }
    /**
     * Do all startup specific things.
     * Creates the XML file and DTD file if necessary
     */
    private void initialize() {
        //DTDCreator dtd = new DTDCreator(DTDSrc);
        
        /*//Check if DTD doesn't exist
        if(!dtd.exists()) {
            System.out.println("DTD create");
            //dtd.createDTD();
            System.out.println("DERPA CREATE");
        }*/
        
        //Check if XML doesn't exist
        if(!exists()) {
            try {
                createXML();
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    /**
     * Creates a new XML file that conforms to the rules layed out in a DTD.
     * The name of the new XML file is defined by XMLSrc that is set in the class
     * header. The method creates a file that is empty but it creates an empty skeleton
     * tree structure. The structure is as follows:
     * 
     * cal
     * --tasks
     * --events
     * --categories
     *
     * @throws IOException if unable to create the file
     */
    private void createXML() throws IOException{
        //DocType doctype = new DocType("cal", "calDB.dtd");
        Comment XMLComment = new Comment("****COMMENT******" + NEWLINE +
            "XML file to store TaskObjects, EventObjects and CategoryObjects." + NEWLINE + 
            "********COMMENT******");
                
        Element cal = new Element("cal");
        Element categories = new Element("categories");
        
        Element tasks = new Element("tasks");
        Element events = new Element("events");
        
        Document doc = new Document();
        doc.addContent(XMLComment);
//        doc.setDocType(doctype);
        doc.setRootElement(cal);
        
        doc.getRootElement().
                addContent(tasks).
                addContent(events).
                addContent(categories); 
        
        writeXML(doc);
    }
    
    /**
     * Writes a document to an XML file. 
     * @param doc The document to be written to the XML file
     * @throws IOException if unable to write to the file
     */
    private void writeXML(Document doc) throws IOException{
        OutputStreamWriter out = null;
        try {
            XMLOutputter outputter = new XMLOutputter();
            out = new OutputStreamWriter(new FileOutputStream(XMLSrc),"UTF-8");
            outputter.setFormat(Format.getPrettyFormat());
            outputter.output(doc, out);
        }
       catch(Exception ex) {
            ex.printStackTrace();
       }

       if (out != null) {
           out.flush();
           out.close();
       }
       

    }
    
    /**
     * Checks if the XML file exists.
     * It checks whethere a file with the name defined by XMLSrc exists.
     * @return true if the file exists, otherwise false 
     */
    
    private boolean exists() {
        File f = new File(XMLSrc);
        return f.exists();
    }
    
    /**
     * Returns an array containing all the IDs that are already occupied.
     * The method can check all the IDs of tasks, events and categories.
     * @param type Specifies what IDs to check, that is "task", "event" or "category"
     * @return an array with occupied IDs
     */
    private ArrayList occupiedIds(String type) {
        ArrayList<String> list = new ArrayList<>();
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        String parent = null;

        switch (type) {
            case "task":
                parent = "tasks";
                break;
            case "event":
                parent = "events";
                break;
            case "category":
                parent = "categories";
                break;
        }
        
        try {
            doc = builder.build(XMLSrc);
        } catch (JDOMException ex) {
            System.err.println("JDOM exception" + NEWLINE + ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("IO exception" + NEWLINE + ex);
            ex.printStackTrace();
        }
        if (doc != null) {
            Element root = doc.getRootElement().getChild(parent);
            List nodes = root.getChildren(type);

            for (Object node1 : nodes) {
                Element node = (Element) node1;
                String id = node.getAttributeValue("id");
                list.add(id);
            }
        }

        return list;
    }
    
    /**
     * Returns the next unoccupied ID for the given type of object.
     * The method checks all the occupied IDs for tasks, events or
     * categories and returns the next unoccupied ID.
     * @param type The type to check IDs for, that is "event", "task" or "category"
     * @return an ID that has not yet been taken by another of the same type.
     */
    private String nextFreeId(String type) {
        ArrayList<String> identifiers;
        int MAX = Integer.MAX_VALUE;
        String strId = null;
        int i;
        identifiers = occupiedIds(type);

        for(i = 0; i<MAX; i++){
            strId = String.valueOf(i);
            if(!identifiers.contains(strId)) {
                identifiers.add(strId);
                return strId;
            }
        }
        return strId;
    }
    
    /**
     * Puts a TaskObject in the XML file.
     * A unique ID is generated for the task before it is being input in the file.
     * 
     * @param t The task to be saved in the XML file
     * @see TaskObject
     */
    public void put(TaskObject t) {
        String type = t.getType();
        String inputName = t.getName();
        String inputInfo = t.getInfo();
        String inputDueDate = t.getDateAsString();
        String inputDueTime = t.getTimeAsString();
        String inputComplete = t.getCompleteAsString();
        String inputPrio = t.getPrioAsString();
        String inputCategory = t.getCategory().getName();
        
        String ID = nextFreeId(type);
        
        Element task = new Element("task");
        Element name = new Element("tname");
        Element info = new Element("tinfo");
        Element due_date = new Element("due_date");
        Element due_time = new Element("due_time");
        Element complete = new Element("complete");
        Element prio = new Element("prio");
        Element category = new Element("tcategory");
        
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        
        try {
            document = builder.build(XMLSrc);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (document != null) {
            Element docRoot = document.getRootElement();

            docRoot.getChild("tasks")
                    .addContent(task.setAttribute("id",ID)
                            .addContent(name.setText(inputName))
                            .addContent(info.setText(inputInfo))
                            .addContent(due_date.setText(inputDueDate))
                            .addContent(due_time.setText(inputDueTime))
                            .addContent(complete.setAttribute("status", inputComplete))
                            .addContent(prio.setAttribute("priority", inputPrio))
                            .addContent(category.setText(inputCategory)));
            try {
                writeXML(document);
            }
            catch(Exception ex) {
                System.err.println("Something went wrong" + NEWLINE + ex);
            }
        }
    }
    
    
    /**
     * Puts an EventObject in the XML file.
     * A unique ID is generated for the event before it is being input in the file.
     * 
     * @param e The event to be saved in the XML file
     * @see EventObject
     */
    public void put(EventObject e) {
        String type = e.getType();
        String inputName = e.getName();
        String inputInfo = e.getInfo();
        String inputStartDate = e.getStartDateAsString();
        String inputStartTime = e.getStartTimeAsString();
        String inputEndDate = e.getEndDateAsString();
        String inputEndTime = e.getEndTimeAsString();
        String inputCategory = e.getCategory().getName();
        
        String ID = nextFreeId(type);
        
        Element event = new Element("event");
        Element name = new Element("ename");
        Element info = new Element("einfo");
        Element start_date = new Element("start_date");
        Element start_time = new Element("start_time");
        Element end_date = new Element("end_date");
        Element end_time = new Element("end_time");
        Element category = new Element("ecategory");
        
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        
        try {
            document = builder.build(XMLSrc);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (document != null) {
            Element docRoot = document.getRootElement();

            docRoot.getChild("events")
                    .addContent(event.setAttribute("id",ID)
                            .addContent(name.setText(inputName))
                            .addContent(info.setText(inputInfo))
                            .addContent(start_date.setText(inputStartDate))
                            .addContent(start_time.setText(inputStartTime))
                            .addContent(end_date.setText(inputEndDate))
                            .addContent(end_time.setText(inputEndTime))
                            .addContent(category.setText(inputCategory)));

            try {
                writeXML(document);
            }
            catch(Exception ex) {
                System.err.println("Something went wrong" + NEWLINE + ex);
            }
        }
    }
    
    /**
     * Puts a CategoryObject in the XML file.
     * A unique ID is generated for the category before it is being input in the file.
     * 
     * @param c The category to be saved in the XML file
     * @see CategoryObject
     */
    public void put(CategoryObject c) {
        String type = c.getType();
        String inputName = c.getName();
        String inputIcon = c.getIcon();
        String inputColor = c.getColor();
        
        String ID = nextFreeId(type);
        
        Element category = new Element("category");
        Element name = new Element("cname");
        Element icon = new Element("icon");
        Element color = new Element("color");
        
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        
        try {
            document = builder.build(XMLSrc);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (document != null) {
            Element docRoot = document.getRootElement();

            docRoot.getChild("categories")
                    .addContent(category.setAttribute("id",ID)
                            .addContent(name.setText(inputName))
                            .addContent(icon.setText(inputIcon))
                            .addContent(color.setText(inputColor)));

            try {
                writeXML(document);
            }
            catch(Exception ex) {
                System.err.println("Something went wrong" + NEWLINE + ex);
            }
        }
    }
    
    /**
     * Returns a list containing all the tasks in the XML file.
     * The list is an ArrayList that contains all the tasks as TaskObjects
     * @return an ArrayList with TaskObjects
     * 
     * @see ArrayList
     * @see TaskObject
     */
    public List<TaskObject> getTasks() {
        ArrayList<TaskObject> list = new ArrayList<>();
        String name;
        String info;
        String due_date;
        String due_time;
        Date date = null;
        Date time = null;
        String complete;
        String prio;
        String category;
        String idToString;
        int id;
        Priority priority;
        Complete finish;
            
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
            doc = builder.build(XMLSrc);


            Element root = doc.getRootElement().getChild("tasks");
            List tasks = root.getChildren("task");

            for (Object task1 : tasks) {
                Element task = (Element) task1;
                idToString = task.getAttributeValue("id");
                name = task.getChild("tname").getText();
                info = task.getChild("tinfo").getText();
                due_date = task.getChild("due_date").getText();
                due_time = task.getChild("due_time").getText();
                complete = task.getChild("complete").getAttributeValue("status");
                prio = task.getChild("prio").getAttributeValue("priority");
                category = task.getChild("tcategory").getText();

                id = Integer.valueOf(idToString);
                priority = new Priority(prio);
                finish = new Complete(complete);
                try {
                    //convert to date format
                    date = df.parse(due_date);
                    time = tf.parse(due_time);
                } catch (ParseException e) {
                    //do nothing
                }

                try {
                    CategoryObject catobj = getCategory(category);
                    TaskObject t = new TaskObject(id, name, info, date, time, finish, priority, catobj);
                    list.add(t);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }


        System.out.println("list size : " + list.size());
        return list;
    }
    
    /**
    * Returns a list containing all the events in the XML file.
    * The list is an ArrayList that contains all the events as EventObjects
    * @return an ArrayList with EventObjects
    * 
    * @see ArrayList
    * @see EventObject
    */
    public List<EventObject> getEvents() {
        ArrayList<EventObject> list = new ArrayList<>();
        String name;
        String info;
        String start_date;
        String start_time;
        String end_date;
        String end_time;
        Date startDate = null;
        Date startTime = null;
        Date endDate = null;
        Date endTime = null;
        String category;
        String idToString;
        int id;
            
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
            doc = builder.build(XMLSrc);

            Element root = doc.getRootElement().getChild("events");
            List tasks = root.getChildren("event");

            for (Object task : tasks) {
                Element event = (Element) task;
                idToString = event.getAttributeValue("id");
                name = event.getChild("ename").getText();
                info = event.getChild("einfo").getText();
                start_date = event.getChild("start_date").getText();
                start_time = event.getChild("start_time").getText();
                end_date = event.getChild("end_date").getText();
                end_time = event.getChild("end_time").getText();
                category = event.getChild("ecategory").getText();

                id = Integer.valueOf(idToString);
                try {
                    //convert to date format
                    startDate = df.parse(start_date);
                    startTime = tf.parse(start_time);
                    endDate = df.parse(end_date);
                    endTime = tf.parse(end_time);
                } catch (ParseException e) {
                    //do nothing
                }

                try {
                    CategoryObject catobj = getCategory(category);
                    EventObject e = new EventObject(id, name, info, startDate, startTime, endDate, endTime, catobj);
                    list.add(e);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

            }

        } catch (JDOMException | IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    
    /**
    * Returns a list containing all the categories in the XML file.
    * The list is an ArrayList that contains all the categories as CategoryObjects
    * @return an ArrayList with CategoryObjects
    * 
    * @see ArrayList
    * @see CategoryObject
    */       
    
    public List<CategoryObject> getCategories() {
        ArrayList<CategoryObject> list = new ArrayList<>();
        String name;
        ImageIcon icon;
        String iconToString;
        Color color;
        String colorToString;
        String idToString;
        int id;

        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
            doc = builder.build(XMLSrc);

            Element root = doc.getRootElement().getChild("categories");
            List tasks = root.getChildren("category");

            for (Object task : tasks) {
                Element category = (Element) task;
                idToString = category.getAttributeValue("id");
                name = category.getChild("cname").getText();
                iconToString = category.getChild("icon").getText();
                colorToString = category.getChild("color").getText();

                id = Integer.valueOf(idToString);
                icon = new ImageIcon(iconToString);
                color = new Color(Integer.valueOf(colorToString));

                try {
                    CategoryObject c = new CategoryObject(id, name, icon, color);
                    list.add(c);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

            }
        } catch (JDOMException ex) {
            System.out.println("WHAT THE FUCK?!");
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    
    /**
     * Gets a category by category name from the XML
     * @param cat The category by name to be retrieved
     * @return The category object with name name. If there is no
     * such category with such a name, then null is returned.
     */
    public CategoryObject getCategory(String cat){
        List<CategoryObject> catList = getCategories();
        for (CategoryObject aCatList : catList) {
            if (aCatList.getName().equals(cat)) {
                return aCatList;
            }
        }
        return new CategoryObject();
    }
}