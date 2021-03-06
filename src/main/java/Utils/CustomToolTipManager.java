/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;


import Model.SixteenBitModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Manages tooltips for Shrimp. <br>
 * This is a very mangled version of javax.swing.ToolTipManager.
 * We've copied this code instead of extending it because we
 * need access to some package/private variables.
 *
 * The intention here is to provide a Swing tooltip that pop-ups when the
 * mouse stops moving (or very shortly thereafter), and remains showing until
 * the mouse is moved again.
 * This is different from the behaviour of javax.swing.ToolTipManager.
 *
 * @see javax.swing.ToolTipManager
 *
 * @author Dave Moore (javax.swing.ToolTipManager version 1.6.5)
 * @author Rich Schiavi (javax.swing.ToolTipManager version 1.6.5)
 * @author Jeff Michaud, Rob Lintern, Chris Callendar
 */
@SuppressWarnings("deprecation")
public class CustomToolTipManager extends MouseAdapter implements MouseMotionListener {
	
	private final Timer mouseStoppedTimer;

	//Timer enterTimer, exitTimer, insideTimer;
	private String toolTipText;
	private Point preferredLocation;
	private JComponent insideComponent;
	private MouseEvent mouseEvent;

	private boolean showImmediately;
	private final static CustomToolTipManager sharedInstance = new CustomToolTipManager();
	private transient Popup tipWindow;
	/** The Window tip is being displayed in. This will be non-null if
	 * the Window tip is in differs from that of insideComponent's Window.
	 */
	private Window window;
	private JToolTip tip;

	private Rectangle popupRect = null;
	private Rectangle popupFrameRect = null;

	private boolean tipShowing = false;

	private final KeyStroke postTip;
	private final KeyStroke hideTip;
	private final Action postTipAction;
	private final Action hideTipAction;

	private FocusListener focusChangeListener = null;
	private MouseMotionListener moveBeforeEnterListener = null;

	// PENDING(ges)
        /**
         * Sets the lightweightpopupenabled to true.
	 */

	private final SixteenBitModel model = SixteenBitModel.getInstance();

	private CustomToolTipManager() {
		mouseStoppedTimer = new Timer (300, new mouseStoppedTimerAction());
		mouseStoppedTimer.setInitialDelay(300);
		mouseStoppedTimer.setRepeats(false);

		//enterTimer = new Timer(750, new insideTimerAction());
		//enterTimer.setRepeats(false);
		//exitTimer = new Timer(500, new outsideTimerAction());
		//exitTimer.setRepeats(false);
		//insideTimer = new Timer(4000, new stillInsideTimerAction());
		//insideTimer.setRepeats(false);

		// create accessibility actions
		postTip = KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_MASK);
		postTipAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
				if (tipWindow != null) { // showing we unshow
					hideTipWindow();
					insideComponent = null;
				} else {
					hideTipWindow(); // be safe
					mouseStoppedTimer.stop();
					//enterTimer.stop();
					//exitTimer.stop();
					//insideTimer.stop();
					insideComponent = (JComponent) e.getSource();
					if (insideComponent != null) {
						toolTipText = insideComponent.getToolTipText();
						preferredLocation = new Point(10, insideComponent.getHeight() + 10); // manual set
						showTipWindow();
						// put a focuschange listener on to bring the tip down
						if (focusChangeListener == null) {
							focusChangeListener = createFocusChangeListener();
						}
						insideComponent.addFocusListener(focusChangeListener);
					}
				}
			}
		};
		hideTip = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		hideTipAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
				hideTipWindow();
				JComponent jc = (JComponent) e.getSource();
				jc.removeFocusListener(focusChangeListener);
				preferredLocation = null;
				insideComponent = null;
			}
            @Override
			public boolean isEnabled() {
				// Only enable when the tooltip is showing, otherwise
				// we will get in the way of any UI actions.
				return tipShowing;
			}
		};

		moveBeforeEnterListener = new MoveBeforeEnterListener();
	}

	private void showTipWindow() {
		if (insideComponent == null || !insideComponent.isShowing() || (toolTipText.trim().length() == 0)) {
			return;
		}
		Dimension size;
		Point screenLocation = insideComponent.getLocationOnScreen();
		Point location = new Point();
		Rectangle sBounds = insideComponent.getGraphicsConfiguration().getBounds();
		//boolean leftToRight = SwingUtilities.isLeftToRight(insideComponent);

		// Just to be paranoid
		hideTipWindow();

		tip = insideComponent.createToolTip();
		tip.setTipText(toolTipText);
		size = tip.getPreferredSize();

		if (preferredLocation != null) {
            location.x = screenLocation.x + preferredLocation.x;
            location.y = screenLocation.y + preferredLocation.y;
        } else {
            location.x = screenLocation.x + mouseEvent.getX();
            location.y = screenLocation.y + mouseEvent.getY() + 20;
        }

		// we do not adjust x/y when using awt.Window tips

		if (popupRect == null) {
            popupRect = new Rectangle();
        }
		popupRect.setBounds(location.x, location.y, size.width, size.height);

		int y = getPopupFitHeight(popupRect, insideComponent);
		int x = getPopupFitWidth(popupRect, insideComponent);

		if (y > 0) {
            location.y -= y;
        }
		if (x > 0) {
            // adjust
            location.x -= x;
        }


		// Fit as much of the tooltip on screen as possible
		if (location.x < sBounds.x) {
            location.x = sBounds.x;
        } else if (location.x - sBounds.x + size.width > sBounds.width) {
            location.x = sBounds.x + Math.max(0, sBounds.width - size.width);
        }
		if (location.y < sBounds.y) {
            location.y = sBounds.y;
        } else if (location.y - sBounds.y + size.height > sBounds.height) {
            location.y = sBounds.y + Math.max(0, sBounds.height - size.height);
        }

		PopupFactory popupFactory = PopupFactory.getSharedInstance();
		//if (lightWeightPopupEnabled) {
		//popupFactory.setPopupType(PopupFactory.LIGHT_WEIGHT_POPUP);
		//} else {
		//popupFactory.setPopupType(PopupFactory.MEDIUM_WEIGHT_POPUP);
		//}
		tipWindow = popupFactory.getPopup(insideComponent, tip, location.x, location.y);
		//popupFactory.setPopupType(PopupFactory.LIGHT_WEIGHT_POPUP);

		tipWindow.show();

		Window componentWindow = SwingUtilities.windowForComponent(insideComponent);

		window = SwingUtilities.windowForComponent(tip);
		if (window != null && window != componentWindow) {
            window.addMouseListener(this);
        } else {
            window = null;
        }

		//insideTimer.start();
		tipShowing = true;

	}

	private void hideTipWindow() {
		if (tipWindow != null) {
			if (window != null) {
				window.removeMouseListener(this);
				window = null;
			}
			tipWindow.hide();
			tipWindow = null;
			tipShowing = false;
			(tip.getUI()).uninstallUI(tip);
			tip = null;
			//insideTimer.stop();
		}
	}

	/**
	 * Returns a shared <code>ToolTipManager</code> instance.
	 *
	 * @return a shared <code>ToolTipManager</code> object
	 */
	public static CustomToolTipManager sharedInstance() {
		return sharedInstance;
	}

	// add keylistener here to trigger tip for access
	/**
	 * Registers a component for tooltip management.
	 * <p>
	 * This will register key bindings to show and hide the tooltip text
	 * only if <code>component</code> has focus bindings. This is done
	 * so that components that are not normally focus traversable, such
	 * as <code>JLabel</code>, are not made focus traversable as a result
	 * of invoking this method.
	 *
	 * @param component  a <code>JComponent</code> object to add
	 * @see JComponent#isFocusTraversable
	 */
	public void registerComponent(JComponent component) {
		component.removeMouseListener(this);
		component.addMouseListener(this);
		component.removeMouseMotionListener(moveBeforeEnterListener);
		component.addMouseMotionListener(moveBeforeEnterListener);

		if (shouldRegisterBindings()) {
			// register our accessibility keybindings for this component
			// this will apply globally across L&F
			// Post Tip: Ctrl+F1
			// Unpost Tip: Esc and Ctrl+F1
			InputMap inputMap = component.getInputMap(JComponent.WHEN_FOCUSED);
			ActionMap actionMap = component.getActionMap();

			if (inputMap != null && actionMap != null) {
				inputMap.put(postTip, "postTip");
				inputMap.put(hideTip, "hideTip");
				actionMap.put("postTip", postTipAction);
				actionMap.put("hideTip", hideTipAction);
			}
		}
	}

	/**
	 * Returns whether or not bindings should be registered on the given
	 * <code>JComponent</code>. This is implemented to return true if the
	 * tool tip manager has a binding in any one of the
	 * <code>InputMaps</code> registered under the condition
	 * <code>WHEN_FOCUSED</code>.
	 * <p>
	 * This does not use <code>isFocusTraversable</code> as
	 * some components may override <code>isFocusTraversable</code> and
	 * base the return value on something other than bindings. For example,
	 * <code>JButton</code> bases its return value on its enabled state.
	 *
	 */
	@SuppressWarnings("SameReturnValue")
	private boolean shouldRegisterBindings() {
		/*
		InputMap inputMap = component.getInputMap(JComponent.WHEN_FOCUSED, false);
		while (inputMap != null && inputMap.size() == 0) {
			inputMap = inputMap.getParent();
		}
		return (inputMap != null);
		*/
		return true;
	}

	// implements java.awt.event.MouseListener
	/**
	 *  Called when the mouse enters the region of a component.
	 *  This determines whether the tool tip should be shown.
	 *
	 *  @param event  the event in question
	 */
    @Override
	public void mouseEntered(MouseEvent event) {
		initiateToolTip(event);
                redispatchMouseEvent(event);
	}

	private void initiateToolTip(MouseEvent event) {
		if (event.getSource() == window) {
			return;
		}
		JComponent component = (JComponent) event.getSource();
		component.removeMouseMotionListener(moveBeforeEnterListener);

		//exitTimer.stop();

		Point location = event.getPoint();
		// ensure tooltip shows only in proper place
		if (location.x < 0 || location.x >= component.getWidth() || location.y < 0 || location.y >= component.getHeight()) {
			return;
		}


		// A component in an unactive internal frame is sent two
		// mouseEntered events, make sure we don't end up adding
		// ourselves an extra time.
		component.removeMouseMotionListener(this);
		component.addMouseMotionListener(this);

		boolean sameComponent = (insideComponent == component);

		insideComponent = component;
		if (tipWindow != null) {
			mouseEvent = event;
			if (showImmediately) {
				String newToolTipText = component.getToolTipText(event);
				Point newPreferredLocation = component.getToolTipLocation(event);
				boolean sameLoc = (preferredLocation != null) ? preferredLocation.equals(newPreferredLocation) : (newPreferredLocation == null);

				if (!sameComponent || !toolTipText.equals(newToolTipText) || !sameLoc) {
					toolTipText = newToolTipText;
					preferredLocation = newPreferredLocation;
					showTipWindow();
				}
			}
		}
	}

	// implements java.awt.event.MouseListener
	/**
	 *  Called when the mouse exits the region of a component.
	 *  Any tool tip showing should be hidden.
	 *
	 *  @param event  the event in question
	 */
    @Override
	public void mouseExited(MouseEvent event) {
		boolean shouldHide = true;

		if (window != null && event.getSource() == window) {
			// if we get an exit and have a heavy window
			// we need to check if it if overlapping the inside component
			Container insideComponentWindow = insideComponent.getTopLevelAncestor();
			Point location = event.getPoint();
			SwingUtilities.convertPointToScreen(location, window);

			location.x -= insideComponentWindow.getX();
			location.y -= insideComponentWindow.getY();

			location = SwingUtilities.convertPoint(null, location, insideComponent);
            shouldHide = location.x < 0 || location.x >= insideComponent.getWidth() || location.y < 0 || location.y >= insideComponent.getHeight();
		} else if (event.getSource() == insideComponent && tipWindow != null) {
			Window win = SwingUtilities.getWindowAncestor(insideComponent);
			if (win != null) { // insideComponent may have been hidden (e.g. in a menu)
				Point location = SwingUtilities.convertPoint(insideComponent, event.getPoint(), win);
				Rectangle bounds = insideComponent.getTopLevelAncestor().getBounds();
				location.x += bounds.x;
				location.y += bounds.y;

				Point loc = new Point(0, 0);
				SwingUtilities.convertPointToScreen(loc, tip);
				bounds.x = loc.x;
				bounds.y = loc.y;
				bounds.width = tip.getWidth();
				bounds.height = tip.getHeight();

                shouldHide = location.x < bounds.x || location.x >= (bounds.x + bounds.width) || location.y < bounds.y || location.y >= (bounds.y + bounds.height);
			}
		}

		if (shouldHide) {
			//enterTimer.stop();
			if (insideComponent != null) {
				insideComponent.removeMouseMotionListener(this);
			}
			insideComponent = null;
			toolTipText = null;
			mouseEvent = null;
			hideTipWindow();
			//exitTimer.restart();
		}
                redispatchMouseEvent(event);
	}

	// implements java.awt.event.MouseListener
	/**
	 *  Called when the mouse is pressed.
	 *  Any tool tip showing should be hidden.
	 *
	 *  @param event  the event in question
	 */
    @Override
	public void mousePressed(MouseEvent event) {
		//hideTipWindow();
		//enterTimer.stop();
		//showImmediately = false;
		//insideComponent = null;
		//mouseEvent = null;
                redispatchMouseEvent(event);
	}
    	/**
	 *  Called when the mouse is released.
	 *  
	 *
	 *  @param event  the event in question
	 */
    @Override
    public void mouseReleased(MouseEvent event) {
        redispatchMouseEvent(event);
    }

	// implements java.awt.event.MouseMotionListener
	/**
	 *  Called when the mouse is pressed and dragged.
	 *  Does nothing.
	 *
	 *  @param event  the event in question
	 */
    @Override
	public void mouseDragged(MouseEvent event) {
		hideTipWindow();
		//enterTimer.stop();
		showImmediately = false;
		insideComponent = null;
		mouseEvent = null;
                redispatchMouseEvent(event);
	}

	// implements java.awt.event.MouseMotionListener
	/**
	 *  Called when the mouse is moved.
	 *  Determines whether the tool tip should be displayed.
	 *
	 *  @param event  the event in question
	 */
    @Override
	public void mouseMoved(MouseEvent event) {
		if (tipShowing) {
			hideTipWindow();
			//enterTimer.stop();
			showImmediately = false;
			//insideComponent = null;
			//mouseEvent = null;
			//checkForTipChange(event);
		} else if (showImmediately) {
			JComponent component = (JComponent) event.getSource();
			toolTipText = component.getToolTipText(event);
			if (toolTipText != null) {
				preferredLocation = component.getToolTipLocation(event);
				mouseEvent = event;
				insideComponent = component;
				//exitTimer.stop();
				//showTipWindow();
			}
		} else {
			// Lazily lookup the values from within insideTimerAction
			insideComponent = (JComponent) event.getSource();
			mouseEvent = event;
			toolTipText = null;
			//enterTimer.restart();
		}
		mouseStoppedTimer.restart();
                redispatchMouseEvent(event);
	}

	/*
	private void checkForTipChange(MouseEvent event) {
		JComponent component = (JComponent) event.getSource();
		String newText = component.getToolTipText(event);
		Point newPreferredLocation = component.getToolTipLocation(event);

		if (newText != null || newPreferredLocation != null) {
			mouseEvent = event;
			if (((newText != null && newText.equals(toolTipText)) || newText == null)
				&& ((newPreferredLocation != null && newPreferredLocation.equals(preferredLocation)) || newPreferredLocation == null)) {
				if (tipWindow != null) {
					//insideTimer.restart();
				} else {
					//enterTimer.restart();
				}
			} else {
				toolTipText = newText;
				preferredLocation = newPreferredLocation;
				if (showImmediately) {
					hideTipWindow();
					showTipWindow();
					//exitTimer.stop();
				} else {
					//enterTimer.restart();
				}
			}
		} else {
			toolTipText = null;
			preferredLocation = null;
			mouseEvent = null;
			insideComponent = null;
			hideTipWindow();
			//enterTimer.stop();
			//exitTimer.restart();
		}
	}
	*/

	// should fire when the mouse stops
     /**
    * Class for mousestoppedtimer actions
    */
	 class mouseStoppedTimerAction implements ActionListener {
            
    /**
    * Performs the chosen action.
    * @param e the action event
    */
        @Override
		public void actionPerformed(ActionEvent e) {
			if (insideComponent != null && insideComponent.isShowing()) {
				// Lazy lookup
				if (toolTipText == null && mouseEvent != null) {
					toolTipText = insideComponent.getToolTipText(mouseEvent);
					preferredLocation = insideComponent.getToolTipLocation(mouseEvent);
				}
				if (toolTipText != null) {
					showImmediately = true;
					showTipWindow();
				} else {
					insideComponent = null;
					toolTipText = null;
					preferredLocation = null;
					mouseEvent = null;
					hideTipWindow();
				}
			}
		}
	}

	/* This listener is registered when the tooltip is first registered
	 * on a component in order to catch the situation where the tooltip
	 * was turned on while the mouse was already within the bounds of
	 * the component.  This way, the tooltip will be initiated on a
	 * mouse-entered or mouse-moved, whichever occurs first.  Once the
	 * tooltip has been initiated, we can remove this listener and rely
	 * solely on mouse-entered to initiate the tooltip.
	 */
	private class MoveBeforeEnterListener extends MouseMotionAdapter {
        @Override
		public void mouseMoved(MouseEvent e) {
			initiateToolTip(e);
		}
	}

	private FocusListener createFocusChangeListener() {
		return new FocusAdapter() {
            @Override
			public void focusLost(FocusEvent evt) {
				hideTipWindow();
				insideComponent = null;
				JComponent c = (JComponent) evt.getSource();
				c.removeFocusListener(focusChangeListener);
			}
		};
	}

	// Returns: 0 no adjust
	//         -1 can't fit
	//         >0 adjust value by amount returned
	private int getPopupFitWidth(Rectangle popupRectInScreen, Component invoker) {
		if (invoker != null) {
			Container parent;
			for (parent = invoker.getParent(); parent != null; parent = parent.getParent()) {
				// fix internal frame size bug: 4139087 - 4159012
				if (parent instanceof JFrame || parent instanceof JDialog || parent instanceof JWindow) { // no check for awt.Frame since we use Heavy tips
					return getWidthAdjust(parent.getBounds(), popupRectInScreen);
				} else if (parent instanceof JApplet || parent instanceof JInternalFrame) {
					if (popupFrameRect == null) {
						popupFrameRect = new Rectangle();
					}
					Point p = parent.getLocationOnScreen();
					popupFrameRect.setBounds(p.x, p.y, parent.getBounds().width, parent.getBounds().height);
					return getWidthAdjust(popupFrameRect, popupRectInScreen);
				}
			}
		}
		return 0;
	}

	// Returns:  0 no adjust
	//          >0 adjust by value return
	private int getPopupFitHeight(Rectangle popupRectInScreen, Component invoker) {
		if (invoker != null) {
			Container parent;
			for (parent = invoker.getParent(); parent != null; parent = parent.getParent()) {
				if (parent instanceof JFrame || parent instanceof JDialog || parent instanceof JWindow) {
					return getHeightAdjust(parent.getBounds(), popupRectInScreen);
				} else if (parent instanceof JApplet || parent instanceof JInternalFrame) {
					if (popupFrameRect == null) {
						popupFrameRect = new Rectangle();
					}
					Point p = parent.getLocationOnScreen();
					popupFrameRect.setBounds(p.x, p.y, parent.getBounds().width, parent.getBounds().height);
					return getHeightAdjust(popupFrameRect, popupRectInScreen);
				}
			}
		}
		return 0;
	}

	private int getHeightAdjust(Rectangle a, Rectangle b) {
		if (b.y >= a.y && (b.y + b.height) <= (a.y + a.height)) {
			return 0;
		}

		return (((b.y + b.height) - (a.y + a.height)) + 5);
	}

	// Return the number of pixels over the edge we are extending.
	// If we are over the edge the ToolTipManager can adjust.
	// REMIND: what if the Tooltip is just too big to fit at all - we currently will just clip
	private int getWidthAdjust(Rectangle a, Rectangle b) {
		//    System.out.println("width b.x/b.width: " + b.x + "/" + b.width +
		//		       "a.x/a.width: " + a.x + "/" + a.width);
		if (b.x >= a.x && (b.x + b.width) <= (a.x + a.width)) {
			return 0;
		}
		return (((b.x + b.width) - (a.x + a.width)) + 5);
	}


	private void redispatchMouseEvent(MouseEvent me) {
            Control.Canvas canvas = model.getCanvas();
                
            if(insideComponent != null) {
                Point canvasPoint = SwingUtilities.convertPoint(insideComponent, me.getPoint(), canvas);
                canvas.dispatchEvent(
                            new MouseEvent(
                                canvas,
                                me.getID(),
                                me.getWhen(),
                                me.getModifiersEx(),
                                canvasPoint.x,
                                canvasPoint.y,
                                me.getClickCount(),
                                me.isPopupTrigger()));
        }
        
            
            }
        
        
        
}
