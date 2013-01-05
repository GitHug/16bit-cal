package View;

import Control.Actions.AnimationEvent;
import Control.Actions.DeSelectEvent;
import Control.Actions.SelectedEvent;
import Control.Actions.UpdateEvent;
import Model.Datatypes.BorderImage;
import Model.SixteenBitModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

/**
 * The graphical representation of a day with tasks and events
 *
 * @author Robin
 * @author fredrikmakila
 */
@SuppressWarnings("LeakingThisInConstructor")
public class DayCard extends CustomComponent {

    private String text;
    private Color currentColor;
    private BorderImage defaultBorder = new BorderImage("beam");
    //private JButton button = new JButton();
    private Point p;
    private Dimension d;
    private boolean isSelected;
    private int fontSize = 12;
    private SixteenBitModel model;
    private Color textColor = Color.DARK_GRAY;
    private boolean showBorder = true;
    private boolean borderVisible = true;
    private BorderImage currentBorder;
    private ActionListener action;
    private Font font;
    private int alpha = 0;
    private boolean done = false;
    private String[] fontFamilies;
    private int offset = 5;
    private int increase = 1;
    private Color whitePulse = new Color(255, 255, 255);
    private Color pulseColor = new Color(255, 255, 255, 0);
    private boolean selectable = true;

    /**
     * Constructor to create a daycard containing a string.
     *
     * @param x The x-position of the daycard
     * @param y The y-position of the daycard
     * @param width The width of the daycard
     * @param height The height of the daycard
     * @param text The text of the daycard
     * @param id The id of the daycard
     */
    public DayCard(int x, int y, int width, int height, String text, int id) {
        super(x, y, width, height);
        this.text = text;
        setBackgroundColor(Color.lightGray);
        setBorderByName(defaultBorder);
        setId(id);
        model = SixteenBitModel.getInstance();

        //Gets available fonts and sets a font
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fontFamilies = ge.getAvailableFontFamilyNames();
        font = new Font("Arial", Font.BOLD, fontSize);
    }

    /**
     * Convenience method for public access to setBorderByName.
     *
     * @param border The name of the new border.
     * @see BorderImage
     */
    private void setBorderByName(BorderImage border) {
        currentBorder = border;
        setBorderType(border);
    }

    @Override
    public void notify(Object arg) {
        if (arg instanceof SelectedEvent) {
            setSelected((SelectedEvent) arg);
        }
        if (arg instanceof UpdateEvent) {
            updateDayCard((UpdateEvent) arg);
        }
        if(arg instanceof AnimationEvent) {
            AnimationEvent event = (AnimationEvent) arg;
            if(event.getId() == getId()) {
                animate(event.getState(), event.getColor());
            }
        }
        if(arg instanceof DeSelectEvent) {
            setDeselected((DeSelectEvent) arg);
        }
    }

    /**
     * Sets the font size of the text.
     *
     * @param size The font size.
     */
    public void setFontSize(int size) {
        fontSize = size;
        font = new Font("Arial", Font.BOLD, fontSize);

    }

    /**
     * Method that is called when a daycard is selected.
     *
     * @param event A selected event.
     */
    private void setSelected(SelectedEvent event) {
        int selectedId = (Integer) event.getId();
        if (getId() == selectedId && !getSelected()) {
            isSelected(true);
        }
    }

    /**
     * Updates the daycard.
     *
     * @param event An update event.
     */
    private void updateDayCard(UpdateEvent event) {
        int selectedId = (Integer) event.getId();
        if (getId() == selectedId) {
            text = event.getString();
            textColor = event.getTextColor();
            showBorder = event.getShowBorder();
            selectable = event.getSelectable();
            setSelectable(selectable);
            if (borderVisible && !showBorder) {
                setBorderType(null);
                borderVisible = false;
            } else if (!borderVisible && showBorder) {
                setBorderType(defaultBorder);
                borderVisible = true;
            }
            setBackgroundColor(event.getColor());
        }
    }

    /**
     * Sets the default border for the daycard.
     *
     * @param border
     */
    public void setDefaultBorder(String border) {
        defaultBorder = new BorderImage(border);
    }

    /**
     * Draws the daycard
     *
     * @param g The grahpics for the daycard
     */
    @Override
    public void draw(Graphics g) {
        super.paintComponents(g);
        p = getLocation();
        d = getSize();
        Graphics2D g2d = (Graphics2D) g;
        
        //Create a rectangle
        g.setColor(getBackgroundColor());
        g.fillRect(p.x, p.y, d.width, d.height);

        g.setColor(pulseColor);
        g.fillRect(p.x, p.y, d.width, d.height);


        //Set font color
        g.setColor(textColor);

        g.setFont(font);
        //Gets the width and height of the string
        Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(text, g);
        g2d.drawString(text, p.x + (int) (d.width / 2 - stringBounds.getCenterX()), p.y + (int) (d.height / 2 - stringBounds.getCenterY()));
    }

    /**
     * Adds an action listener to this button.
     *
     * @param action The actionlistener to be attached to the daycard.
     */
    public void addActionListener(ActionListener action) {
        this.action = action;
    }


    @Override
    protected void animate(Boolean state, Color color) {
        pulseColor = color;
       /* if (state) {
            if ((alpha + offset > 255 && increase > 0) || (alpha - offset < 0 && increase < 0)) {
                increase = increase * -1;
            }
            alpha = alpha + offset * increase;
        } else {
            alpha = 0;
        } */
    }

    
     /**
     * Makes the daycard deselected.
     *
     * @param event A deselect event.
     */
    private void setDeselected(DeSelectEvent event) {
        if(event.getId() == getId()) {
            if (action != null) {
                action.actionPerformed(null);
                if (getSelected()) {
                    isSelected(false);
                }
            
            }
        
        }
    
    } 
}

