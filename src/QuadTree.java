/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * *
 * *    The Barnes-Hut algorithm assumes that the force from distant enough particles,
 * *    can be approximated with acceptable losses to accuracy by using their center mass and total mass.
 * *    This can reduce or runtime to n log(n).
 * *
 * *    We will use a quad tree (2D) structure to store the points/bodies in.
 * *
 * *    We will continue to divide into quadrants until there is only one particle in a leaf-node.
 * *
 * *    With this Quad-tree we can calculate the total mass and it's center for each "box".
 * *    Traverse the tree for each particle to calculate the force. This parts is done in parallel with multiple threads.
 * *    Best case should be one core for each node.
 * *
 * *    Definitions:
 * *    Leaf
 * *    (less commonly called External node)
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
 * Representation of a QuadTree
 */
public class QuadTree {

    /* a region that a tree represents */
    private Quad quad;

    /* a aggregatedBodies */
    private Body aggregatedBodies;

    /* child quadrants */
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    /* theta */
    private double theta = 0.5;

    /**
     * QuadTree Constructor
     * Creates a QuadTree
     *
     * @param quad
     */
    QuadTree(Quad quad) {
        this.quad = quad;
        this.aggregatedBodies = null;
        this.northWest = null;
        this.northEast = null;
        this.southWest = null;
        this.southEast = null;
    }

    /**
     * Insert aggregatedBodies in tree
     *
     * @param body is the aggregatedBodies we want to insert
     */
    public void build(Body body) {

        /* if this node doesn't contain a aggregatedBodies, insert the new aggregatedBodies here*/
        if (this.aggregatedBodies == null)
            this.aggregatedBodies = body;

        /* if internal nodes */
        if (!external()) {
            aggregatedBodies = aggregatedBodies.aggregate(body); /* aggregate the body if it's internal */
            insert(body);
        } else {
            /* aggregate four new Child nodes to this node */
            northWest = new QuadTree(quad.northWest());
            northEast = new QuadTree(quad.northEast());
            southWest = new QuadTree(quad.southWest());
            southEast = new QuadTree(quad.southEast());

            /* insert bodies */
            insert(aggregatedBodies);
            insert(body);

            /* aggregate aggregatedBodies */
            aggregatedBodies = aggregatedBodies.aggregate(body);
        }
    }

    /**
     * A node is defined as external if it has no children
     *
     * @return true if external
     */
    private boolean external() {
        return northWest == null
                && northEast == null
                && southWest == null
                && southEast == null;
    }

    /**
     * Inserts the body into the right Quadrant
     *
     * @param body is the body that we want to insert
     */
    private void insert(Body body) {
        if (body.inQuad(quad.northWest()))
            northWest.insert(body);
        if (body.inQuad(quad.northEast()))
            northEast.insert(body);
        if (body.inQuad(quad.southWest()))
            southWest.insert(body);
        if (body.inQuad(quad.southEast()))
            southEast.insert(body);
    }

    /**
     * Calculates the combined force the bodies in a QuadTree applies on a body
     *
     * If the mass of an internal node is far away we treat the bodies in that part
     * of the tree as a single body
     *
     * If the body is rather close, we calculate the force and check the children the same way
     *
     * We calculate if a body is close enough by the ratio of the with of the internal nodes region
     * and the distance of the body and the nodes mass center.
     *
     * This value is called Theta. Large Theta increases speed and smaller Theta accuracy.
     * (If Theta is 0, then no internal node is treated as a single body)
     *
     */
    public Vector calculateForce(Body body) {
        /* TODO must force be initated to awoid nullPonterExceptions? */
        double[] array = {1,1};
        Vector force = new Vector(array);

        /* if the node is external we calculates the forces the aggregated bodies applies on this body
        * bodies do not apply forces on them selves ans not from null */
        if (external() && !body.equals(aggregatedBodies)){
            force = body.calculateForces(aggregatedBodies);
        }
        else { /* when the node is internal */
            if (quad.getLength()/aggregatedBodies.calculateDistance(body) > theta) { /* if this is true then the body is far away*/
                force =  body.calculateForces(aggregatedBodies);
            }else {
                northWest.calculateForce(body);
                northEast.calculateForce(body);
                southWest.calculateForce(body);
                southEast.calculateForce(body);
            }
        }
        return force;
    }
}


