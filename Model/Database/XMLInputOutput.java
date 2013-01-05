/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Database;

import Model.Datatypes.*;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

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
    private String DTDSrc = "calDB.dtd";//"./src/calDB.dtd";
    private String XMLSrc = "taskDB.xml";//"./src/taskDB.xml";
    private char NEWLINE = '\n';
    private char TAB = '\t';
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat tf = new SimpleDateFormat("HH:mm");
            
            
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
                System.err.println(ex);
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
     * @see DTDCreator for further details on the structure of the DTD file
     * @throws IOException if unable to create the file
     */
    private void createXML() throws IOException{
        String id = "id";
        //DocType doctype = new DocType("cal", "calDB.dtd");
        Comment XMLComment = new Comment("****COMMENT******" + NEWLINE +
            "XML file to store TaskObjects, EventObjects and CategoryObjects." + NEWLINE + 
            "********COMMENT******");
                
        Element cal = new Element("cal");
        Element task = new Element("task");
        Element event = new Element("event");
        Element categories = new Element("categories");
        
        Element tname = new Element("tname");
        Element ename = new Element("ename");
        Element cname = new Element("cname");
        
        Element tinfo = new Element("tinfo");
        Element einfo = new Element("einfo");
        
        Element tcategory = new Element("tcategory");
        Element ecategory = new Element("ecategory");
        
        Element prio = new Element("prio");
        Element due_date = new Element("due_date");
        Element due_time = new Element("due_time");
        Element complete = new Element("complete");
        Element start_date = new Element("start_date");
        Element start_time = new Element("start_time");
        Element end_date = new Element("end_date");
        Element end_time = new Element("end_time");
        Element icon = new Element("icon");
        Element color = new Element("color");
        
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
            System.err.println(ex);
       }
       out.flush();
       out.close();
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
        ArrayList<String> list = new ArrayList<String>();
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        String parent = null;
        
        if(type.equals("task")) {
            parent = "tasks";
        }
        else if(type.equals("event")) {
            parent = "events";
        }
        else if(type.equals("category")) {
            parent = "categories";
        }
        
        try {
            doc = builder.build(XMLSrc);
        } catch (JDOMException ex) {
            System.err.println("JDOM exception" + NEWLINE + ex);
        } catch (IOException ex) {
            System.err.println("IO exception" + NEWLINE + ex);
        }
        Element root = doc.getRootElement().getChild(parent);
        List nodes = root.getChildren(type);
        Iterator i = nodes.iterator();
        
        while(i.hasNext()) {
            Element node = (Element) i.next();
            String id = node.getAttributeValue("id");
            list.add(id);
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
            strId = new Integer(i).toString();
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
        } catch (JDOMException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        XMLOutputter outputter = new XMLOutputter();
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
        } catch (JDOMException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        XMLOutputter outputter = new XMLOutputter();
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
        } catch (JDOMException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        XMLOutputter outputter = new XMLOutputter();
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
    
    /**
     * Returns a list containing all the tasks in the XML file.
     * The list is an ArrayList that contains all the tasks as TaskObjects
     * @return an ArrayList with TaskObjects
     * 
     * @see ArrayList
     * @see TaskObject
     */
    public ArrayList getTasks() {
        ArrayList<TaskObject> list = new ArrayList<TaskObject>();
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
        Document doc = null;
        try {
            doc = builder.build(XMLSrc);
        } catch (JDOMException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

        Element root = doc.getRootElement().getChild("tasks");
        List tasks = root.getChildren("task");
        Iterator i = tasks.iterator();
        
        while (i.hasNext()) {
            Element task = (Element) i.next();
            idToString = task.getAttributeValue("id");
            name = task.getChild("tname").getText();
            info = task.getChild("tinfo").getText();
            due_date = task.getChild("due_date").getText();
            due_time = task.getChild("due_time").getText();
            complete = task.getChild("complete").getAttributeValue("status");
            prio = task.getChild("prio").getAttributeValue("priority");
            category = task.getChild("tcategory").getText();
            
            id = new Integer(idToString);
            priority = new Priority(prio);
            finish = new Complete(complete);
            try{
                //convert to date format
                date = df.parse(due_date);
                time = tf.parse(due_time);
            }
            catch(ParseException e) {
                //do nothing
            }
        
            try{
                CategoryObject catobj = getCategory(category);
                TaskObject t = new TaskObject (id, name, info, date, time, finish, priority, catobj);
                list.add(t);
            }
            catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        
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
    public ArrayList getEvents() {
        ArrayList<EventObject> list = new ArrayList<EventObject>();
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
        Document doc = null;
        try {
            doc = builder.build(XMLSrc);
        } catch (JDOMException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

        Element root = doc.getRootElement().getChild("events");
        List tasks = root.getChildren("event");
        Iterator i = tasks.iterator();
        
        while (i.hasNext()) {
            Element event = (Element) i.next();
            idToString = event.getAttributeValue("id");
            name = event.getChild("ename").getText();
            info = event.getChild("einfo").getText();
            start_date = event.getChild("start_date").getText();
            start_time = event.getChild("start_time").getText();
            end_date = event.getChild("end_date").getText();
            end_time = event.getChild("end_time").getText();
            category = event.getChild("ecategory").getText();
            
            id = new Integer(idToString);
            try{
                //convert to date format
                startDate = df.parse(start_date);
                startTime = tf.parse(start_time);
                endDate = df.parse(end_date);
                endTime = tf.parse(end_time);
            }
            catch(ParseException e) {
                //do nothing
            }
        
            try{
                CategoryObject catobj = getCategory(category);
                EventObject e = new EventObject(id, name, info, startDate, startTime, endDate, endTime, catobj);
                list.add(e);
            }
            catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        
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
    
    public ArrayList getCategories() {
        ArrayList<CategoryObject> list = new ArrayList<CategoryObject>();
        String name;
        ImageIcon icon;
        String iconToString;
        Color color;
        String colorToString;
        String idToString;
        int id;

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(XMLSrc);
        } catch (JDOMException ex) {
            System.out.println("WHAT THE FUCK?!");
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLInputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

        Element root = doc.getRootElement().getChild("categories");
        List tasks = root.getChildren("category");
        Iterator i = tasks.iterator();
        
        while (i.hasNext()) {
            Element category = (Element) i.next();
            idToString = category.getAttributeValue("id");
            name = category.getChild("cname").getText();
            iconToString = category.getChild("icon").getText();
            colorToString = category.getChild("color").getText();
            
            id = new Integer(idToString);
            icon = new ImageIcon(iconToString);
            color = new Color(new Integer(colorToString));
        
            try{
                CategoryObject c = new CategoryObject(id, name, icon, color);
                list.add(c);
            }
            catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        
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
        ArrayList<CategoryObject> catList = getCategories();
        for(int i = 0; i<catList.size(); i++){
            if(catList.get(i).getName().equals(cat)){
                return catList.get(i);
            }
        }
        return new CategoryObject();
    }
    
    /**
     * Return a list that contains a task-list, event-list and category-list.
     * The list is an ArrayList, which in turn consists of a TaskObject ArrayList,
     * EventObject ArrayList and CategoryObject ArrayList, in that order.
     * 
     * @return an ArrayList containing a TaskObject ArrayList, EventObject ArrayList,
     * and CategoryObject ArrayList
     * 
     * @see TaskObject
     * @see EventObject
     * @see CategoryObject
     * @see ArrayList
     */
    public ArrayList get() {
        ArrayList list = new ArrayList();
        list.add(getTasks());
        list.add(getEvents());
        list.add(getCategories());
        return list;
    }
    
    /**
     * Deletes a task from the XML file.
     * The task is defined by its ID, and all information associated with the task
     * is deleted.
     * 
     * @param id The ID of the task to be deleted
     */
    public void deleteTask(int id){
        String idToString;
        try{
            SAXBuilder builder = new SAXBuilder(); 
            Document document = builder.build(XMLSrc);
            XMLOutputter outputter = new XMLOutputter();
            
            Element docRoot = document.getRootElement().getChild("tasks");
            
            List tasks = docRoot.getChildren("task");
            
            Iterator i = tasks.iterator();
            
            idToString = new Integer(id).toString();
            
            while(i.hasNext()) {
                Element element = (Element) i.next();
                //Checks that the attribute corresponds to the correct task
                if(element.getAttributeValue("id").equals(idToString)){
                   docRoot.removeContent(element);
                   break; //finished after finding the correct element
                }
            }
            writeXML(document);
        }
        catch(IOException e) {
            System.err.println(e);
        }
        catch(JDOMException e) {
            System.err.println(e);
        }
    }
    
    /**
     * Deletes an event from the XML file.
     * The event is defined by its ID, and all information associated with the event
     * is deleted.
     * 
     * @param id The ID of the event to be deleted
     */    
    public void deleteEvent(int id){
        String idToString;
        try{
            SAXBuilder builder = new SAXBuilder(); 
            Document document = builder.build(XMLSrc);
            XMLOutputter outputter = new XMLOutputter();
            
            Element docRoot = document.getRootElement().getChild("events");
            
            List tasks = docRoot.getChildren("event");
            
            Iterator i = tasks.iterator();
            
            idToString = new Integer(id).toString();
            
            while(i.hasNext()) {
                Element element = (Element) i.next();
                //Checks that the attribute corresponds to the correct task
                if(element.getAttributeValue("id").equals(idToString)){
                   docRoot.removeContent(element);
                   break; //finished after finding the correct element
                }
            }
            writeXML(document);
        }
        catch(IOException e) {
            System.err.println(e);
        }
        catch(JDOMException e) {
            System.err.println(e);
        }
    }
    
    /**
     * Deletes a category from the XML file.
     * The category is defined by its ID, and all information associated with the category
     * is deleted.
     * 
     * @param id The ID of the category to be deleted
     */
    public void deleteCategory(int id){
        String idToString;
        try{
            SAXBuilder builder = new SAXBuilder(); 
            Document document = builder.build(XMLSrc);
            XMLOutputter outputter = new XMLOutputter();
            
            Element docRoot = document.getRootElement().getChild("categories");
            
            List tasks = docRoot.getChildren("category");
            
            Iterator i = tasks.iterator();
            
            idToString = new Integer(id).toString();
            
            while(i.hasNext()) {
                Element element = (Element) i.next();
                //Checks that the attribute corresponds to the correct task
                if(element.getAttributeValue("id").equals(idToString)){
                   docRoot.removeContent(element);
                   break; //finished after finding the correct element
                }
            }
            writeXML(document);
        }
        catch(IOException e) {
            System.err.println(e);
        }
        catch(JDOMException e) {
            System.err.println(e);
        }
    }
    
    /**
     * Changes the state of the task from complete or vice versa.
     * If the state of the task is not completed, then the new state of the task
     * will be complete. If the state of the task is complete, the new state will be not 
     * complete.
     * 
     * @param id The ID of the task to be edited. 
     */
    public void setComplete(int id) {
        String idToString = new Integer(id).toString();
        try {
            SAXBuilder builder = new SAXBuilder();    
            Document document = builder.build(XMLSrc);
            XMLOutputter outputter = new XMLOutputter();
            Element docRoot = document.getRootElement().getChild("tasks");

            List tasks = docRoot.getChildren("task");

            Iterator i = tasks.iterator();

            while(i.hasNext()) {
                Element element = (Element) i.next();
                //Checks that the attribute corresponds to the correct task
                if(element.getAttributeValue("id").equals(idToString)){
                    if(element.getChild("complete").getText().equals("no")) {
                        element.getChild("complete").setText("yes");
                    }
                    else {
                        element.getChild("complete").setText("no");   
                    }
                    break; //finished after finding the correct element
                }
            }
            
            writeXML(document);
        }
        catch (Exception e) {
        
        }
        
    }
    
    /**
     * Edit an already existing task in the XML-file.
     * This method edits a task given by its ID. The values are updated with the new
     * values found in t
     * @param t the task to be edited
     */
    public void editTask(TaskObject t) {
        int id = t.getID();
        String inputName = t.getName();
        String inputInfo = t.getInfo();
        String inputDueDate = t.getDateAsString();
        String inputDueTime = t.getTimeAsString();
        String inputComplete = t.getCompleteAsString();
        CategoryObject inputCategory = t.getCategory();
        String inputPrio = t.getPrioAsString();
        
        String idToString = new Integer(id).toString();
        
        try {
            SAXBuilder builder = new SAXBuilder();    
            Document document = builder.build(XMLSrc);
            XMLOutputter outputter = new XMLOutputter();
            Element docRoot = document.getRootElement().getChild("tasks");

            List tasks = docRoot.getChildren("task");

            Iterator i = tasks.iterator();

            while(i.hasNext()) {
                Element element = (Element) i.next();
                //Checks that the attribute corresponds to the correct task
                if(element.getAttributeValue("id").equals(idToString)){
                    element.getChild("tname").setText(inputName);
                    element.getChild("tinfo").setText(inputInfo);
                    element.getChild("due_date").setText(inputDueDate);
                    element.getChild("due_time").setText(inputDueTime);
                    element.getChild("complete").setText(inputComplete);
                    element.getChild("prio").setText(inputPrio);
                    element.getChild("tcategory").setText(inputCategory.getName());

                    break; //finished after finding the correct element
                }
            }
            
            writeXML(document);
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
        
    }
    
    /**
     * Edit an already existing event in the XML-file.
     * This method edits an event given by its ID. The values are updated with the new
     * values found in e
     * @param e the event to be edited
     */
    public void editEvent(EventObject e) {
        int id = e.getID();
        String inputName = e.getName();
        String inputInfo = e.getInfo();
        String inputStartDate = e.getStartDateAsString();
        String inputStartTime = e.getStartTimeAsString();
        String inputEndDate = e.getEndDateAsString();
        String inputEndTime = e.getEndTimeAsString();
        String inputCategory = e.getCategory().getName(); 
        
        String idToString = new Integer(id).toString();
        
        try {
            SAXBuilder builder = new SAXBuilder();    
            Document document = builder.build(XMLSrc);
            XMLOutputter outputter = new XMLOutputter();
            Element docRoot = document.getRootElement().getChild("events");

            List tasks = docRoot.getChildren("event");

            Iterator i = tasks.iterator();

            while(i.hasNext()) {
                Element element = (Element) i.next();
                //Checks that the attribute corresponds to the correct event
                if(element.getAttributeValue("id").equals(idToString)){
                    element.getChild("ename").setText(inputName);
                    element.getChild("einfo").setText(inputInfo);
                    element.getChild("start_date").setText(inputStartDate);
                    element.getChild("start_time").setText(inputStartTime);
                    element.getChild("end_date").setText(inputEndDate);
                    element.getChild("end_time").setText(inputEndTime);
                    element.getChild("ecategory").setText(inputCategory);

                    break; //finished after finding the correct element
                }
            }
            
            writeXML(document);
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
        
    }
    
    /**
     * Edit an already existing category in the XML-file.
     * This method edits a category given by its ID. The values are updated with the new
     * values found in c.
     * @param c the category to be edited.
     */
    public void editCategory(CategoryObject c) {
        int id = c.getID();
        String inputName = c.getName();
        String inputIcon = c.getIcon();
        String inputColor = c.getColor();

        String idToString = new Integer(id).toString();
        
        try {
            SAXBuilder builder = new SAXBuilder();    
            Document document = builder.build(XMLSrc);
            XMLOutputter outputter = new XMLOutputter();
            Element docRoot = document.getRootElement().getChild("categories");

            List tasks = docRoot.getChildren("category");

            Iterator i = tasks.iterator();

            while(i.hasNext()) {
                Element element = (Element) i.next();
                //Checks that the attribute corresponds to the correct category
                if(element.getAttributeValue("id").equals(idToString)){
                    element.getChild("cname").setText(inputName);
                    element.getChild("icon").setText(inputIcon);
                    element.getChild("color").setText(inputColor);

                    break; //finished after finding the correct element
                }
            }
            
            writeXML(document);
        }
        catch (Exception e) {
        
        }
        
    }
}