/*
 * The Barnes-Hut algorithm assumes that the force from distant enough particles,
 * can be approximated with acceptable losses to accuracy by using their center mass and total mass.
 * This can reduce or runtime to n log(n).
 *
 * We will use a quad tree (2D) structure to store the points/bodies in.
 *
 * We will continue to divide into quadrants until there is only one particle in a leaf-node.
 *
 * With this Quad-tree we can calculate the total mass and it's center for each "box".
 * Traverse the tree for each particle to calculate the force. This parts is done in parallel with multiple threads.
 * Best case should be one core for each node.
 *
 * ,,,,,,,,,,,,,,,,,,,
 * B A R N E S - H U T
 * ```````````````````
 */

/**
 * represents a Quad in the QuadTree
 */
public class Quad {

    /* center and dimension */
    private Vector center;
    private double halfDimension;

    /* min and max X and Y values */
    private double minX;
    private double maxX;

    private double maxY;
    private double minY;


    /**
     * Constructor
     *
     * @param center        is the center of this Quadrant represented by a vector with x and y coordinates
     * @param halfDimension is the length of this Quadrant
     */
    public Quad(Vector center, double halfDimension) {
        this.minX = center.getX() - halfDimension;
        this.maxX = center.getX() + halfDimension;
        this.minY = center.getY() - halfDimension;
        this.maxY = center.getY() + halfDimension;

        this.halfDimension = halfDimension;
        this.center = center;
    }

    /**
     * Checks if Quadrant contains Body
     * @param body the body we are looking at
     * @return true if Quadrant contains body
     */
    public boolean containsBody(Body body) {
        return !(body.getXPosition() >= maxX)
                && !(body.getXPosition() < minX)
                && !(body.getYPosition() >= maxY)
                && !(body.getYPosition() < minY);
    }

    /**
     * Splits a Quad into a smaller quad which is 1/4th the size of the original
     * @return a new quad that is 1/4th of the original
     */
    public Quad subDivde() {
        double[] newCenter = {center.getX()/4, center.getY()/4};
        return new Quad(new Vector(newCenter), halfDimension / 2);
    }
}
