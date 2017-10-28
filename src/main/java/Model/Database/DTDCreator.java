/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Database;

/**
 * A class for creating a DTD file. 
 * The name of the DTD is defined by the constructor, however it is supposed to be 
 * the same as the name of the XML file but with a .dtd suffix instead of the .xml.
 * The DTD file is used to validate the XML file.
 * @author fredrikmakila
 * @see XMLInputOutput
 */
public class DTDCreator {
    private String source;
    private String NEWLINE = System.getProperty("line.separator");
    private OutputStreamWriter out;
    private char TAB = '\t';
    
    /**
     * Constructor.
     * Sets the variables that is needed to create a valid DTD file
     * @param source the name and path of the file to be created.
     */
    public DTDCreator(String source){
        this.source = source;
    }
    
    /**
     * Creates the DTD file itself.
     * All the rules that the XML file has to conform to is layed out in the XML file.
     * See the created DTD file for further details on what the rules are.
     */
    public void createDTD() {
        try {
            out = new OutputStreamWriter(new FileOutputStream(source),"UTF-8");
        } catch (FileNotFoundException ex) {
            System.err.println("File not found" + NEWLINE + ex);
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported encoding" + NEWLINE + ex);
        }
        try {
            writeln("<?xml version='1.0' encoding='UTF-8'?>");
            writeln("<!-- ", 
                    "This document contains the rules for the calendar XML structure",
                    "and may be used to check its validity -->");
            out.write(NEWLINE);
            
            writeln("<!-- The root node for the calendar -->");
            writeln("<!ELEMENT cal (tasks*|events*|categories*)*>");
            out.write(NEWLINE);
            
            writeln("<!--___________TASK______________-->");
            out.write(NEWLINE);
            
            writeln("<!-- The node that contains all the tasks -->");
            writeln("<!ELEMENT tasks (task)*>");
            out.write(NEWLINE);
            
            writeln("<!-- Specifies what elements make up a task. Each task is",
                    "associated with a unique, mandatory ID -->");
            writeln("<!ELEMENT task (tname|tinfo|due_date|due_time|complete|prio|tcategory)*>");
            writeln("<!ATTLIST task ", 
                    TAB + "id CDATA #REQUIRED",
                    ">");
            out.write(NEWLINE);
            
            writeln("<!--- The name of the task -->");
            writeln("<!ELEMENT tname (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- The additional information attached to the task -->");
            writeln("<!ELEMENT tinfo (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- The date when the task should be completed -->");
            writeln("<!ELEMENT due_date (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- The time when the task should be completed -->");
            writeln("<!ELEMENT due_time (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- Specifies if the task is finished or not -->");
            writeln("<!ELEMENT complete (#PCDATA)>");
            writeln("<!ATTLIST complete",
                    TAB + "status  (yes | no) "  + '\"' + "no" + '\"',
                    ">");
            out.write(NEWLINE);
            
            writeln("<!--- The priority of the task -->");
            writeln("<!ELEMENT prio (#PCDATA)>");
            writeln("<!ATTLIST prio",
                    TAB + "priority  (low | normal | high) "  + '\"' + "normal" + '\"',
                    ">");
            out.write(NEWLINE);
            
            writeln("<!--- The category of the task -->");
            writeln("<!ELEMENT tcategory (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--___________EVENT______________-->");
            out.write(NEWLINE);
            
            writeln("<!-- The node that contains all the events -->");
            writeln("<!ELEMENT events (event)*>");
            out.write(NEWLINE);
            
            writeln("<!-- The elements that make up an event",
                    "each event has a unique, mandatory ID -->");
            writeln("<!ELEMENT event (ename|einfo|start_date|start_time|end_date|end_time|ecategory)*>");
            writeln("<!ATTLIST event ", 
                    TAB + "id CDATA #REQUIRED",
                    ">");
            out.write(NEWLINE);

            writeln("<!--- The name of the event -->");
            writeln("<!ELEMENT ename (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- The additional information attached to the event -->");
            writeln("<!ELEMENT einfo (#PCDATA)>");
            out.write(NEWLINE);            
            
            writeln("<!--- The date when the event should start -->");
            writeln("<!ELEMENT start_date (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- The time when the task should start -->");
            writeln("<!ELEMENT start_time (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- The date when the event should end -->");
            writeln("<!ELEMENT end_date (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- The time when the task should end -->");
            writeln("<!ELEMENT end_time (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--- The category of the task -->");
            writeln("<!ELEMENT ecategory (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!--___________CATEGORIES______________-->");
            out.write(NEWLINE);
            
            writeln("<!-- The node that contains all the categories -->");
            writeln("<!ELEMENT categories (category)*>");
            out.write(NEWLINE);
            
            writeln("<!-- The elements that make up a category",
                    "Each category has a unique id attached to it -->");
            writeln("<!ELEMENT category (cname|icon|color)*>");
            writeln("<!ATTLIST category ", 
                TAB + "id CDATA #REQUIRED",
                ">");
            out.write(NEWLINE);
            
            writeln("<!-- The name of the category -->");
            writeln("<!ELEMENT cname (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!-- The icon for the category -->");
            writeln("<!ELEMENT icon (#PCDATA)>");
            out.write(NEWLINE);
            
            writeln("<!-- The color for the category -->");
            writeln("<!ELEMENT color (#PCDATA)>");
            out.write(NEWLINE);
            
            close();
        }
        catch(Exception Ex) {
            System.err.println("Exception: " + Ex);
        }   
    }    
    
    /**
     * Writes to file.
     * This is exaclty as the write method used by OutPutStreamWriter, however it adds
     * a newline character at the end of each line.
     * @param str The String to be written to the file
     * @see OutputStreamWriter
     */
    public void writeln(String str) {
        try {
            out.write(str + NEWLINE);
        } catch (IOException ex) {
            System.err.println("IO exception" + NEWLINE + ex);
        }
    }
    
    /**
     * Writes to file.
     * This is exaclty as the write method used by OutPutStreamWriter, however it adds
     * a newline character at the end of each line. Each argument is also separated by a
     * newline character.
     * @param str1 A String to be written to the file
     * @param str2 Another String to be written to the file
     */
    public void writeln(String str1, String str2) {
        try {
            out.write(str1 + NEWLINE + str2 + NEWLINE);
        } catch (IOException ex) {
            System.err.println("IO exception" + NEWLINE + ex);
        }
    }
    
     /**
     * Writes to file.
     * This is exaclty as the write method used by OutPutStreamWriter, however it adds
     * a newline character at the end of each line. Each argument is also separated by a
     * newline character.
     * @param str1 A String to be written to the file
     * @param str2 Another String to be written to the file
     * @param str3 Yet another String to be written to the file.
     */
    public void writeln(String str1, String str2, String str3) {
        try {
            out.write(str1 + NEWLINE + str2 + NEWLINE + str3 + NEWLINE);
        } catch (IOException ex) {
            System.err.println("IO exception" + NEWLINE + ex);
        }
    }
    
    /**
     * Closes the stream.
     * Performs both flush and close.
     * 
     * @see OutputStreamWriter
     */
    public void close() {
        try {
            out.flush();
            out.close();
        } catch (IOException ex) {
            System.err.println("IO exception" + NEWLINE + ex);
        }
    }
    
    /**
     * Checks if the file exists.
     * The method checks if a file with this name, as defined by source in the constructor, 
     * and path already exists.
     * @return true if the file exists, otherwise false
     */
    public boolean exists() {
        File f = new File(source);
        return f.exists();
    }
}

        
        
    
    

