import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;


class Draw extends JFrame {

    private Body[] bodies;
    private DrawBody drawBody = new DrawBody();

    /**
     * Constructor
     */
    Draw(/*Body[] bodies*/) {
//        this.bodies = bodies; /* get bodies */
        this.setSize(800, 800); /* size of window */
        this.setTitle("Graphics simulation"); /* name of window */
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); /* exit on close */
//        this.add(/*new DrawBody()*/drawBody, BorderLayout.CENTER);
//        this.setVisible(true); /* shows window */
    }



    public void updateMovement(Body[] bodies) {
        this.bodies = bodies;
        this.add(drawBody, BorderLayout.CENTER);
        this.setVisible(true); /* shows window */
    }

    /**
     * Inner class DrawBody
     * This is going to be a new component so it extends JComponent
     */
    private class DrawBody extends JComponent {

        public void paint(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g; /* use Graphics2D to draw 2D graphics */
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON); /* cleaner drawings with antialiasing */

            graphics2D.setPaint(Color.CYAN); /* define color */


            /* draw all bodies */
            for (Body body : bodies) {
                //graphics2D.fill(new Arc2D.Double(body.getXPosition() / 20, body.getYPosition() / 20, body.getMass() / 800, body.getMass() / 800, 360, 360, Arc2D.OPEN));
                graphics2D.fill(new Arc2D.Double(body.getXPosition() / 1000, body.getYPosition() / 1000, body.getMass() / 20, body.getMass() / 20, 360, 360, Arc2D.OPEN));
            }
        }
    }
}
