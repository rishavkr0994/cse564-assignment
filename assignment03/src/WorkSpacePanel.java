import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A panel to display the cities and the route between them. It allows marking of a new city with a mouse click and
 * also allows to move an existing city to a new location by clicking the city and dragging it to a new location.
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-10-02
 */
public class WorkSpacePanel extends JPanel implements MouseListener, MouseMotionListener {
    private static final int DEFAULT_CITY_HEIGHT = 20;
    private static final int DEFAULT_CITY_WIDTH = 20;

    City clickedCity = null;
    int preX, preY;

    /**
     * Default constructor. It defines the listener for the mouse actions.
     */
    public WorkSpacePanel() {
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * Plots the cities (as rectangles) and routes (as lines) onto the display area. The city names are also displayed
     * alongside the cities.
     *
     * @param g graphics object to plot the content
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (City city : WorkSpace.getInstance().getCityList())
            city.draw(g2);

        for (Route route : WorkSpace.getInstance().getRouteList())
            route.getSrc().drawConnect(route.getDest(), g2);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) { }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * <p>
     * A new city is marked when the user clicks on an empty space, else if the user clicks on a city, the city movement
     * operation is initiated.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        clickedCity = WorkSpace.getInstance().getCityList().stream()
                .filter(x -> x.contains(e.getX(), e.getY()))
                .findFirst().orElse(null);

        if (clickedCity != null) {
            preX = clickedCity.getX() - e.getX();
            preY = clickedCity.getY() - e.getY();
            WorkSpace.getInstance().moveExistingCity(clickedCity, preX + e.getX(), preY + e.getY());
            repaint();
        }
        else {
            String cityName = JOptionPane.showInputDialog(this, "Enter City Name");
            if (cityName != null && !cityName.isEmpty()) {
                City city = new City(cityName, e.getX(), e.getY(), DEFAULT_CITY_WIDTH, DEFAULT_CITY_HEIGHT);
                WorkSpace.getInstance().addNewCity(city);
                repaint();
            }
        }
    }

    /**
     * Invoked when a mouse button is pressed on a component and then dragged. {@code MOUSE_DRAGGED} events will
     * continue to be delivered to the component where the drag originated until the mouse button is released
     * (regardless of whether the mouse position is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,{@code MOUSE_DRAGGED} events may not be delivered during
     * a native Drag&amp;Drop operation.
     * <p>
     * If a city movement is in progress, it updates the co-ordinates of the moving city based on the current cursor
     * location on screen.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (clickedCity != null) {
            WorkSpace.getInstance().moveExistingCity(clickedCity, preX + e.getX(), preY + e.getY());
            repaint();
        }
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) { }

    /**
     * Invoked when a mouse button has been released on a component.
     * <p>
     * Commits the city movement operation and updates the city co-ordinates to the current cursor location on screen.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (clickedCity != null && clickedCity.contains(e.getX(), e.getY())) {
            clickedCity.move(preX + e.getX(), preY + e.getY());
            clickedCity = null;
            repaint();
        }
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) { }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) { }
}
