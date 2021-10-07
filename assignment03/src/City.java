import java.awt.*;
import java.awt.geom.Line2D;

/**
 * This class is the data structure for storing information about a city.
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-10-02
 */
public class City {
    private final Rectangle bounds;
    private final String label;

    /**
     * The constructor of the city.
     * @param label City name.
     * @param x City x-coordinate.
     * @param y City y-coordinate.
     * @param w The width of the city rectangle.
     * @param h The high of the city rectangle.
     */
    public City(String label, int x, int y, int w, int h) {
        this.bounds = new Rectangle(x, y, w, h);
        this.label = label;
    }
    /**
     * Get the city x-coordinate.
     * @return X-coordinate.
     */
    public int getX() { return bounds.x; }

    /**
     * Get the city y-coordinate.
     * @return Y-coordinate.
     */
    public int getY() { return bounds.y; }

    /**
     * Get the city name.
     * @return City name.
     */
    public String getLabel() { return label; }

    /**
     * Draw the city in the component.
     * @param g Graphics
     */
    public void draw(Graphics g) {
        int x = bounds.x, y = bounds.y, h = bounds.height, w = bounds.width;
        g.drawRect(x, y, w, h);
        Color c = g.getColor();
        g.setColor(Color.white);
        g.fillRect(x + 1, y + 1, w - 1, h - 1);
        g.setColor(Color.red);
        g.setFont(new Font("Courier", Font.PLAIN, 12));
        g.drawString(label, x + w, y);
        g.setColor(c);
    }

    /**
     * Move the city to somewhere.
     * @param x, X-coordinate of the new place
     * @param y, Y-coordinate of the new place
     */
    public void move(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }
    /**
     * Find the center of the city.
     * @return center of the city.
     */
    private Point center() {
        return new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
    }

    /**
     * Draw a line between two cities.
     * @param b, Another city.
     * @param g, Graphics2D
     */
    public void drawConnect(City b, Graphics2D g) {
        g.draw(new Line2D.Float(center().x, center().y, b.center().x, b.center().y));
    }

    /**
     * Prevent two cities in the same place.
     * @param x City x-coordinate.
     * @param y City y-coordinate.
     * @return valid or not
     */
    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }
}
