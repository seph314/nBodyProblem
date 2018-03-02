public class SimulateN2Seq {

    Body[] bodies;
    int numberOfBodies;
    int dt;
    Vector forces[];


    /**
     * Constructor
     *
     * @param bodies is our array of bodies
     * @param dt     is the increase in time
     */
    public SimulateN2Seq(Body[] bodies, int numberOfBodies, int dt, Vector[] forces) {
        this.bodies = bodies;
        this.numberOfBodies = numberOfBodies;
        this.dt = dt;
        this.forces = forces;
    }

    /**
     * Simulates what happens to the bodies over time
     */
    public void time(Draw draw) throws InterruptedException {


        /*Vector[] forces = new Vector[bodies.length]; // a new force array with the length of tge bodies array
        for (int i = 0; i < bodies.length; i++) {
            forces[i] = new Vector(new double[2]);
        }*/
        for (int i = 0; i < numberOfBodies; i++)
            for (int j = 0; j < numberOfBodies; j++) {
                if (i != j)
                    forces[i] = forces[i].add(bodies[i].calculateForces(bodies[j])); /* calculate an array of forces */
            }



//        int numberOfTimes = 0;
//        while (numberOfTimes < 99) {
//            numberOfTimes++;
//            draw.setBodies(bodies);
////        draw.updateMovement();
//            new Draw(bodies);

//        draw.setBodies(bodies);

            for (int i = 0; i < numberOfBodies; i++) {
                bodies[i].movePoints(forces[i], dt); /* move all points */

//                new Draw(bodies);
            }
//        draw.updateMovement(bodies);




                /*for (int i = w; i<(w+(bodies.length/workers)); i++) {
                    System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
                    System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
                    System.out.println("mass: " + bodies[i].getMass());
                }*/
        }

//        new Draw(bodies); /* graphical representation of bodies */

//    }
}
