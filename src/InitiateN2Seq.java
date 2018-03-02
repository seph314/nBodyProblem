import java.util.concurrent.CyclicBarrier;

public class InitiateN2Seq{
    private Body[] bodies;
    private int dt;
    private int numberOfBodies;
    private Vector[] forces;
    private int numSteps;


    public InitiateN2Seq(Body[] bodies, int dt, int numberOfBodies, Vector[] forces, int numSteps) {
        this.bodies = bodies;
        this.dt = dt;
        this.numberOfBodies = numberOfBodies;
        this.forces = forces;
        this.numSteps = numSteps;

    }

    public void initiate(){
        SimulateN2Seq simulation = new SimulateN2Seq(bodies, numberOfBodies, dt, forces);
        Draw draw = new Draw();

        try {
            for(int i = 0; i < numSteps; i++) {
                simulation.time(draw);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
