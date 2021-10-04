import java.awt.*;
import java.awt.geom.Line2D;

public class City {
    private final Rectangle bounds;
    private final String label;

    public City(String label, int x, int y, int w, int h) {
        this.bounds = new Rectangle(x, y, w, h);
        this.label = label;
    }

    public int getX() { return bounds.x; }
    public int getY() { return bounds.y; }

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

    public void move(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }

    private Point center() {
        return new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
    }

    public void drawConnect(City b, Graphics2D g) {
        g.draw(new Line2D.Float(center().x, center().y, b.center().x, b.center().y));
    }

    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }
}
