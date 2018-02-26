/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * *
 * *    The Barnes-Hut algorithm assumes that the force from distant enough particles,
 * *    can be approximated with acceptable losses to accuracy by using their center mass and total mass.
 * *    This can reduce or runtime to n log(n).
 * *
 * *    We will use a quad tree (2D) structure to store the points/bodies in.
 * *
 * *    We will recursively divide the Bodies into groups in out Quad-Tree. Each node can has 4 children, they may be empty.
 * *    The first node represents all the space and this Nodes Children represents the first four Quadrants.
 * *    Each of these Quadrants can/will be subdivided into four new Quadrants and so on.
 * *    Each internal node will represent all the bodies beneath it in the tree. And each External node will
 * *    represent a body.
 * *
 * *    If we want to know the net force on a body, we can traverse the tree (from the root) and if we get to a internal body
 * *    that we think is far enough away that the accuracy is not suffering, we simply use that internal nodes aggravates properties
 * *    to calculate the force instead of all the bodies beneath it in the Tree.
 * *
 * *    With this Quad-tree we can calculate the total mass and it's center for each "box".
 * *    Traverse the tree for each particle to calculate the force. This parts is done in parallel with multiple threads.
 * *    Best case should be one core for each node.
 * *
 * *    Definitions:
 * *    External node (Leaf)
 * *    A node with no children
 * *
 * *    Internal node
 * *    A node with at least one child.
 * *
 * *    Each internal node represents a quadrant in space and can have up to four children
 * *    Each leaf/external node represents a point in space (or is empty)
 * *
 * * * * * * * * * * * * * * * * * * * *
 * *                                * *
 * *     B A R N E S - H U T       * *
 * *                              * *
 * * * * * * * * * * * * * * * * * */

/**
 * represents a Quad in the QuadTree
 */
public class Quad {

    /* center and dimension */
    private Vector center;
    private double length;

    /* min and max X and Y values */
    private double minX;
    private double maxX;

    private double maxY;
    private double minY;


    /**
     * Constructor
     * @param center is the center of this Quadrant represented by a vector with x and y coordinates
     * @param length is half the length of this Quadrant
     */
    public Quad(Vector center, double length) {
        double halfLength = length/2;
        this.minX = center.getX() - halfLength;
        this.maxX = center.getX() + halfLength;
        this.minY = center.getY() - halfLength;
        this.maxY = center.getY() + halfLength;

        this.length = length;
        this.center = center;
    }

    public double getLength() {
        return length;
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
     * @return the new top left quadrant of this quad
     */
    public Quad northWest(){
        double newX = center.getX() - length/4;
        double newY = center.getY() + length/4;
        double newLength = length/2;
        double[] newCenter = {newX,newY};
        return new Quad(new Vector(newCenter), newLength);
    }

    /**
     * @return the new top right quadrant of this quad
     */
    public Quad northEast(){
        double newX = center.getX() + length/4;
        double newY = center.getY() + length/4;
        double newLength = length/2;
        double[] newCenter = {newX,newY};
        return new Quad(new Vector(newCenter), newLength);
    }

    /**
     * @return the new top right quadrant of this quad
     */
    public Quad southWest(){
        double newX = center.getX() - length/4;
        double newY = center.getY() - length/4;
        double newLength = length/2;
        double[] newCenter = {newX,newY};
        return new Quad(new Vector(newCenter), newLength);
    }

    /**
     * @return the new top right quadrant of this quad
     */
    public Quad southEast(){
        double newX = center.getX() + length/4;
        double newY = center.getY() - length/4;
        double newLength = length/2;
        double[] newCenter = {newX,newY};
        return new Quad(new Vector(newCenter), newLength);
    }
}
