import java.util.concurrent.CyclicBarrier;

public class InitiateN2Seq{
    private Body[] bodies;
    private int dt;
    private int numberOfBodies;
    private Vector[] forces;


    public InitiateN2Seq(Body[] bodies, int dt, int numberOfBodies, Vector[] forces) {
        this.bodies = bodies;
        this.dt = dt;
        this.numberOfBodies = numberOfBodies;
        this.forces = forces;

    }

    public void initiate(){
        SimulateN2Seq simulation = new SimulateN2Seq(bodies, numberOfBodies, dt, forces);
        try {
            simulation.time();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
