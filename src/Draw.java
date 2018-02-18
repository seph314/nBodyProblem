import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;


class Draw extends JFrame {

    private Body[] bodies;

    /**
     * Constructor
     */
    Draw(Body[] bodies)  {
        this.bodies = bodies; /* get bodies */
        this.setSize(800,600); /* size of window */
        this.setTitle("Graphics simulation"); /* name of window */
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); /* exit on close */
        this.add(new DrawBody(), BorderLayout.CENTER);
        this.setVisible(true); /* shows window */
    }

    /**
     * Inner class DrawBody
     * This is going to be a new component so it extends JComponent
     */
    private class DrawBody extends JComponent{

        public void paint(Graphics g){
            Graphics2D graphics2D = (Graphics2D)g; /* use Graphics2D to draw 2D graphics */
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON); /* cleaner drawings with antialiasing */

            graphics2D.setPaint(Color.CYAN); /* define color */

            /* draw all bodies */
            for (Body body : bodies) {
                graphics2D.fill(new Arc2D.Double(body.getXPosition(), body.getYPosition(), body.getMass()/10, body.getMass()/10, 360, 360, Arc2D.OPEN));
            }
        }
    }
}