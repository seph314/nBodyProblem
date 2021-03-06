
class InitiateN2Seq {
    private Body[] bodies;
    private int dt;
    private int numberOfBodies;
    private Vector[] forces;
    private int numSteps;


    InitiateN2Seq(Body[] bodies, int dt, int numberOfBodies, Vector[] forces, int numSteps) {
        this.bodies = bodies;
        this.dt = dt;
        this.numberOfBodies = numberOfBodies;
        this.forces = forces;
        this.numSteps = numSteps;

    }

    void initiate() {
        SimulateN2Seq simulation = new SimulateN2Seq(bodies, numberOfBodies, dt, forces);

        try {
            for (int i = 0; i < numSteps; i++) {
                simulation.time();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
