class SimulateN2Seq {

    Body[] bodies;
    private int numberOfBodies;
    int dt;
    private Vector forces[];


    /**
     * Constructor
     *
     * @param bodies is our array of bodies
     * @param dt     is the increase in time
     */
    SimulateN2Seq(Body[] bodies, int numberOfBodies, int dt, Vector[] forces) {
        this.bodies = bodies;
        this.numberOfBodies = numberOfBodies;
        this.dt = dt;
        this.forces = forces;
    }

    /**
     * Simulates what happens to the bodies over time
     */
    void time() throws InterruptedException {

        for (int i = 0; i < numberOfBodies; i++)
            for (int j = 0; j < numberOfBodies; j++) {
                if (i != j)
                    forces[i] = forces[i].add(bodies[i].calculateForces(bodies[j])); /* calculate an array of forces */
            }

        for (int i = 0; i < numberOfBodies; i++) {
            bodies[i].moveBodies(forces[i], dt); /* move all points */

        }

    }

}
