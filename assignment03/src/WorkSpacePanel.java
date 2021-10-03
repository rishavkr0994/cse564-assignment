import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class WorkSpacePanel extends JPanel implements MouseListener, MouseMotionListener{
    private static final int DEFAULT_CITY_HEIGHT = 10;
    private static final int DEFAULT_CITY_WIDTH = 10;

    WorkSpace workSpace = new WorkSpace();

    int preX,preY;
    boolean pressOut = false;

    public WorkSpacePanel() {
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        for (City city : workSpace.getCityList())
            city.draw(g2);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        String inputValue = JOptionPane.showInputDialog("add city");
        City city = new City(inputValue,e.getX(),e.getY(),DEFAULT_CITY_WIDTH, DEFAULT_CITY_HEIGHT);
        workSpace.addNewCity(city);
        repaint();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
    /*  preX = (int)(tempe.getX() - e.getY());
        preY = (int)(tempe.getY() - e.getY());
        if (tempe.contains(e.getX(),e.getY()))
        {
            tempe.move(preX + e.getX(), preY + e.getY());
            repaint();
        }else
        {
            pressOut = true;
        }*/
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
       /* if (tempe.contains(e.getX(), e.getY()))
        {
            tempe.move(preX + e.getX(), preY + e.getY());
            repaint();
        }else
        {
            pressOut = false;
        }*/
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
       /* if (!pressOut)
        {
            tempe.move(preX + e.getX(), preY + e.getY());
            repaint();
        }*/
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
