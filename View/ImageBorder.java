/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Model.Database.ResourceHandler;
import Model.Datatypes.BorderImage;
import Model.Datatypes.BorderPiece;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.border.AbstractBorder;

/**
 * Class that paints a custom border around a component.
 * The class uses overrides the paintBorder method and paints a
 * set of images as the border around an object. The class gets
 * the URL for the images from a property file. 
 * @author fredrikmakila
 */
public class ImageBorder extends AbstractBorder {

    private Image topCenterImage;
    private Image topLeftImage;
    private Image topRightImage;

    private Image leftCenterImage;
    private Image rightCenterImage;

    private Image bottomCenterImage;
    private Image bottomLeftImage;
    private Image bottomRightImage;

    private Insets insets;
    //This will later be changed so the class retrieves the URLs from a property file instead
    private BorderImage border;
    private BorderImage defaultBorder = new BorderImage("beam");
    private String topLeftSrc;
    private String topCenterSrc;
    private String topRightSrc;
    private String leftCenterSrc;
    private String rightCenterSrc;
    private String bottomLeftSrc;
    private String bottomCenterSrc;
    private String bottomRightSrc;
    private URL imageURL;
    private String NEWLINE = System.getProperty("line.separator");
    
    /**
     * Empty constructor for this class.
     * The constructor tries to load all the necessary images that make up
     * the border. Uses a defualt border.
     */
    public ImageBorder() {
        border = defaultBorder;
        loader();   
    }
    
    /**
     * Constructor with a defined border.
     * The constructor tries to load all the necessary images that make up
     * the border. 
     * @param border The border to be loaded 
     */
    public ImageBorder(BorderImage border) {
        this.border = border;
        if(border != null) {
            loader(); 
        }
    }
    
    
    
    /**
     * Loads all the necessary border pieces from file. 
     * Uses a debug function for easier error checking if something went wrong during
     * the loading process.
     */
    @SuppressWarnings("unchecked")
    private void loader() {
        ArrayList debug = new ArrayList();
        ResourceHandler rh = new ResourceHandler();
        ArrayList borders = rh.getBorders(border.toString());
        try {
            //Top left
            topLeftSrc = rh.getBorder(new BorderPiece("top_left"), borders);
            imageURL = getClass().getClassLoader().getResource(topLeftSrc);
            topLeftImage = new ImageIcon(imageURL).getImage();
            debug.add("Successful: " + imageURL + NEWLINE);
            //Top center
            topCenterSrc = rh.getBorder(new BorderPiece("top_center"), borders);
            imageURL = getClass().getClassLoader().getResource(topCenterSrc);
            topCenterImage = new ImageIcon(imageURL).getImage();
            debug.add("Successful: " + imageURL + NEWLINE);
            //Top right
            topRightSrc = rh.getBorder(new BorderPiece("top_right"), borders);
            imageURL = getClass().getClassLoader().getResource(topRightSrc);
            topRightImage = new ImageIcon(imageURL).getImage();
            debug.add("Successful: " + imageURL + NEWLINE);
            //Left center
            leftCenterSrc = rh.getBorder(new BorderPiece("left_center"), borders);
            imageURL = getClass().getClassLoader().getResource(leftCenterSrc);
            leftCenterImage = new ImageIcon(imageURL).getImage();
            debug.add("Successful: " + imageURL + NEWLINE); 
            //Right center
            rightCenterSrc = rh.getBorder(new BorderPiece("right_center"), borders);
            imageURL = getClass().getClassLoader().getResource(rightCenterSrc);
            rightCenterImage = new ImageIcon(imageURL).getImage();
            debug.add("Successful: " + imageURL + NEWLINE);
            //Bottom left
            bottomLeftSrc = rh.getBorder(new BorderPiece("bottom_left"), borders);
            imageURL = getClass().getClassLoader().getResource(bottomLeftSrc);
            bottomLeftImage = new ImageIcon(imageURL).getImage();
            debug.add("Successful: " + imageURL + NEWLINE); 
            //Bottom center
            bottomCenterSrc = rh.getBorder(new BorderPiece("bottom_center"), borders);
            imageURL = getClass().getClassLoader().getResource(bottomCenterSrc);
            bottomCenterImage = new ImageIcon(imageURL).getImage();
            debug.add("Successful: " + imageURL + NEWLINE); 
            //Bottom right
            bottomRightSrc = rh.getBorder(new BorderPiece("bottom_right"), borders);
            imageURL = getClass().getClassLoader().getResource(bottomRightSrc);
            bottomRightImage = new ImageIcon(imageURL).getImage();
            debug.add("Successful: " + imageURL + NEWLINE); 
        }
        catch(Exception ex){
            System.out.println(debug);
            System.err.println(ex);
            System.exit(0);
        }
    }
    
    /**
     * Set the insets for this border.
     * @param insets new insets to be set
     * @see Insets
     */
    public void setInsets(Insets insets) {
        this.insets = insets;
    }
    
    /**
     * Sets a new image for the border.
     * More info to follow... not yet implemented
     * @param imageDescription Not yet implemented
     */
    public void setImage(String imageDescription) {
        System.out.println("Not yet implemented...");
    }

    /**
     * Gets the insets for this border.
     * @param c The component to get the inset data from
     * @return the insets of this border
     * @see Insets
     */
    @Override
    public Insets getBorderInsets(Component c) {
        if(border == null) {
            return new Insets(0, 0, 0, 0);
        }
        else if (insets != null) {
            return insets;
        } 
        else {
            return new Insets(topCenterImage.getHeight(null), 
                    leftCenterImage.getWidth(null), 
                    bottomCenterImage.getHeight(null), 
                    rightCenterImage.getWidth(null));
        }
    }

    /**
     * Overridden method to paint the border.
     * @param c The component that will get its border painted
     * @param g The graphics environment
     * @param x The x-position from where the border will be drawn
     * @param y The y-position from where the border will be drawn
     * @param width The width of the component c
     * @param height The height of the component c
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        
        if(border != null) {
            int topLeftWidth = topLeftImage.getWidth(null);
            int topLeftHeight = topLeftImage.getHeight(null);
            int topCenterWidth = topCenterImage.getWidth(null);
            int topCenterHeight = topCenterImage.getHeight(null);
            int topRightWidth = topRightImage.getWidth(null);
            int topRightHeight = topRightImage.getHeight(null);

            int leftCenterWidth = leftCenterImage.getWidth(null);
            int leftCenterHeight = leftCenterImage.getHeight(null);
            int rightCenterWidth = rightCenterImage.getWidth(null);
            int rightCenterHeight = rightCenterImage.getHeight(null);

            int bottomLeftWidth = bottomLeftImage.getWidth(null);
            int bottomLeftHeight = bottomLeftImage.getHeight(null);
            int bottomCenterWidth = bottomCenterImage.getWidth(null);
            int bottomCenterHeight = bottomCenterImage.getHeight(null);
            int bottomRightWidth = bottomRightImage.getWidth(null);
            int bottomRightHeight = bottomRightImage.getHeight(null);

            fillTexture(g2, topLeftImage, x, y, topLeftWidth, topLeftHeight);
            fillTexture(g2, topCenterImage, x + topLeftWidth, y, width - topLeftWidth - topRightWidth, topCenterHeight);
            fillTexture(g2, topRightImage, x + width - topRightWidth, y, topRightWidth, topRightHeight);

            fillTexture(g2, leftCenterImage, x, y + topLeftHeight, leftCenterWidth, height - topLeftHeight - bottomLeftHeight);
            fillTexture(g2, rightCenterImage, x + width - rightCenterWidth, y + topRightHeight, rightCenterWidth, height - topRightHeight - bottomRightHeight);

            fillTexture(g2, bottomLeftImage, x, y + height - bottomLeftHeight, bottomLeftWidth, bottomLeftHeight);
            fillTexture(g2, bottomCenterImage, x + bottomLeftWidth, y + height - bottomCenterHeight, width - bottomLeftWidth - bottomRightWidth, bottomCenterHeight);
            fillTexture(g2, bottomRightImage, x + width - bottomRightWidth, y + height - bottomRightHeight, bottomRightWidth, bottomRightHeight);
        }
    }

    /**
     * Draws a section of the border.
     * @param g2 The 2d graphics environment to be used
     * @param img The image to be drawn
     * @param x The starting x-position from where to draw
     * @param y The starting y-position from where to draw 
     * @param width The width of the section to draw
     * @param height The height of the section to draw
     */
    public void fillTexture(Graphics2D g2, Image img, int x, int y, int width, int height) {
        BufferedImage buff = createBufferedImage(img);
        Rectangle anchor = new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
        TexturePaint paint = new TexturePaint(buff, anchor);
        g2.setPaint(paint);
        g2.fillRect(x, y, width, height);
    }

    /**
     * Creates a BufferedImage of an Image so it can be painted.
     * @param img The image to be converted
     * @return A BufferedImage
     */
    public BufferedImage createBufferedImage(Image img) {
        BufferedImage buff = new BufferedImage(img.getWidth(null), img.getHeight(null),
        BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = buff.createGraphics();
        gfx.drawImage(img, 0, 0, null);
        gfx.dispose();
        return buff;
    }
}
