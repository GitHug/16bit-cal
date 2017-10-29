16 bit calendar!
=========

This calendar was created by Kenny Pussinen, Robin Horneman, Robert Lööf and Fredrik Mäkilä as a group project for the course User Interface Programming 2 at Uppsala University. All classes has an author who was responsible for that class so it's easy to recognize who wrote what part of the code. I can only take credit for the classes I have written myself, however the whole project was certainly a group effort!

The program is created in Java with the user interface components created using Java Swing. The requirements for the course was to create a calendar which relies heavily upon drawable objects that we have created. In the main user interface, no buttons or other readily available components available in Java Swing were allowed to be used. 

This project uses JDom, an open source Java-based document object model for XML. It allows for easy to use storing and retrieval of data in XML. Credit goes to the people at www.jdom.org or here at https://github.com/hunterhacker/jdom/ .

The code was written back in 2009 and, especially the code I have written myself, is amateurish at best. It's nice to look back at it though and recognize that I have come very far since then in both my skills as a programmer but also how to structure a project.

Features available:
-----------

-A fancy 16 bit inspired user interface 

-Ability to add, edit and remove  tasks as a to do-list for days in the calendar

-Ability to add events for days in the calendar. Events take place over a period of time and could for example be a meeting or a birthday party. 

-Fancy blinking mouse pointer hover effect for the days in the calendar. 

-Undo/redo capability.

-Ability to select low, medium and/or high priority tasks from the calendar. 

-Ability to start a "Help" system (*Hint* press the help button) which explains different parts of the program which a fancy animation. 

-Super secret easter egg!  

Installation:
-----------

```
mvn install
java -jar target/16bit-cal-1.0-jar-with-dependencies.jar
```

Enjoy!
