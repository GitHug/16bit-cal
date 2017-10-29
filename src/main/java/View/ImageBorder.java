/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Model.Database.ResourceHandler;
import Model.Datatypes.BorderImage;
import Model.Datatypes.BorderPiece;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that paints a custom border around a component.
 * The class uses overrides the paintBorder method and paints a
 * set of images as the border around an object. The class gets
 * the URL for the images from a property file. 
 * @author fredrikmakila
 */
class ImageBorder extends AbstractBorder {

    private Image topCenterImage;
    private Image topLeftImage;
    private Image topRightImage;

    private Image leftCenterImage;
    private Image rightCenterImage;

    private Image bottomCenterImage;
    private Image bottomLeftImage;
    private Image bottomRightImage;

    //This will later be changed so the class retrieves the URLs from a property file instead
    private final BorderImage border;
    private final String NEWLINE = System.getProperty("line.separator");

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
        List<String> debug = new ArrayList<>();
        ResourceHandler rh = new ResourceHandler();
        List<ResourceHandler.BorderType> borders = rh.getBorders(border.toString());
        try {
            //Top left
            topLeftImage = createBorder(debug, rh, borders, "top_left");
            //Top center
            topCenterImage = createBorder(debug, rh, borders, "top_center");
            //Top right
            topRightImage = createBorder(debug, rh, borders, "top_right");
            //Left center
            leftCenterImage = createBorder(debug, rh, borders, "left_center");
            //Right center
            rightCenterImage = createBorder(debug, rh, borders, "right_center");
            //Bottom left
            bottomLeftImage = createBorder(debug, rh, borders, "bottom_left");
            //Bottom center
            bottomCenterImage = createBorder(debug, rh, borders, "bottom_center");
            //Bottom right
            bottomRightImage = createBorder(debug, rh, borders, "bottom_right");
        }
        catch(Exception ex){
            System.out.println(debug);
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private Image createBorder(List<String> debug, ResourceHandler rh, List<ResourceHandler.BorderType> borders,
                              String borderPieceName) throws Exception {
        String src = rh.getBorder(new BorderPiece(borderPieceName), borders);
        URL imageURL = getClass().getClassLoader().getResource(src);

        if (imageURL == null) {
            throw new Exception("ImageURL missing for " + src);
        }
        Image image = new ImageIcon(imageURL).getImage();
        debug.add("Successful: " + imageURL + NEWLINE);

        return image;
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
        } else {
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
            int topCenterHeight = topCenterImage.getHeight(null);
            int topRightWidth = topRightImage.getWidth(null);
            int topRightHeight = topRightImage.getHeight(null);

            int leftCenterWidth = leftCenterImage.getWidth(null);
            int rightCenterWidth = rightCenterImage.getWidth(null);

            int bottomLeftWidth = bottomLeftImage.getWidth(null);
            int bottomLeftHeight = bottomLeftImage.getHeight(null);
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
    private void fillTexture(Graphics2D g2, Image img, int x, int y, int width, int height) {
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
    private BufferedImage createBufferedImage(Image img) {
        BufferedImage buff = new BufferedImage(img.getWidth(null), img.getHeight(null),
        BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = buff.createGraphics();
        gfx.drawImage(img, 0, 0, null);
        gfx.dispose();
        return buff;
    }
}
