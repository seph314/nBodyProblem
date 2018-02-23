public class InitiateBarnesHutSeq {

    private Body[] bodies;
    private int dt;

    public InitiateBarnesHutSeq(Body[] bodies) {
        this.bodies = bodies;
        this.dt = dt;
    }

    public void buildQuadTree(Vector[] forces) {

        double sizeOfTheUniverse = 500;
        double[] startCoordinates = {0, 0};
        Vector startVector = new Vector(startCoordinates);
        double theta = 0.5;

        /* create new Quad */
        /* TODO what size shoule "sizeOfTheUniverse" be? */
        Quad quad = new Quad(startVector, sizeOfTheUniverse);

        /* empty QuadTree */
        QuadTree quadTree = new QuadTree(quad);

        /* build tree */
        for (Body body : bodies) {
            if (body.inQuad(quad)) { /* TODO check this part again */
                quadTree.build(body);
            }
        }

        /* calculate forces */

//        for (int i = 0; i < bodies.length; i++) {
//            forces[i] = forces[i].add(quadTree.calculateForce(bodies[i])); /* calculate an array of forces */
//        }
//
//        for (int i = 0; i < bodies.length; i++) {
//            bodies[i].movePoints(forces[i], dt); /* move all points */
//        }

        for (Body body : bodies){
            body.movePoints(quadTree.calculateForce(body), dt);
        }
    }
}

