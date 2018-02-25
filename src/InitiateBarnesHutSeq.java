public class InitiateBarnesHutSeq {

    private Body[] bodies;
    private int dt;

    public InitiateBarnesHutSeq(Body[] bodies, int dt) {
        this.bodies = bodies;
        this.dt = dt;
    }
    public void addforces(Quad quad) {
        QuadTree thetree = new QuadTree(quad);
        // If the body is still on the screen, add it to the tree
        for (int i = 0; i < bodies.length; i++) {
            if (bodies[i].inQuad(quad)) thetree.build(bodies[i]);
        }
        //Now, use out methods in BHTree to update the forces,
        //traveling recursively through the tree
        for (int i = 0; i < bodies.length; i++) {
            bodies[i].resetForce();
            if (bodies[i].inQuad(quad)) {
                thetree.calculateForce(bodies[i]);
                //Calculate the new positions on a time step dt (1e11 here)
                bodies[i].update(dt);
            }
        }
    }

    public void buildQuadTree(Vector[] forces) {

        double sizeOfTheUniverse = 50000000;
        double[] startCoordinates = {50, 50};
        Vector startVector = new Vector(startCoordinates);

        /* create new Quad */
        /* TODO what size shoule "sizeOfTheUniverse" be? */
        Quad quad = new Quad(startVector, sizeOfTheUniverse);

        /* empty QuadTree */
        //QuadTree quadTree = new QuadTree(quad);

        System.out.println("Bodies length " + bodies.length + " " + bodies[0].getXPosition());

        /* build tree */
       // for (Body body : bodies) {
         //   if (body.inQuad(quad)) { /* TODO check this part again */
           //     quadTree.build(body);
           // }
       // }

        /* calculate forces */

        addforces(quad);
        //for (int i = 0; i < bodies.length; i++) {
        //    forces[i] = forces[i].add(quadTree.calculateForce(bodies[i])); /* calculate an array of forces */
        //}

       // for (int i = 0; i < bodies.length; i++) {
        //    bodies[i].movePoints(forces[i], dt); /* move all points */
       // }

//        for (Body body : bodies){
//            body.movePoints(quadTree.calculateForce(body), dt);
//        }
    }
}

