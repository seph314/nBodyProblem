import java.util.Arrays;

public class Simulate {

    private Body[] bodies;
    private int dt;
//    private PointsEx pointsEx;

    /**
     * Constructor
     *
     * @param bodies is our array of bodies
     * @param dt     is the increase in time
     */
    Simulate(Body[] bodies, int dt) {
        this.bodies = bodies;
        this.dt = dt;
    }

    /**
     * Simulates what happens to the bodies over time
     */
    public void time() {

        Vector[] forces = new Vector[bodies.length]; /* a new force array with the length of tge bodies array */
        for (int i = 0; i < bodies.length; i++) {
            forces[i] = new Vector(new double[2]);
        }

        for (int i = 0; i < bodies.length; i++)
            for (int j = 0; j < bodies.length; j++) {
                if (i != j)
                    forces[i] = forces[i].add(bodies[i].calculateForces(bodies[j])); /* calculate an array of forces */
            }

        for (int i = 0; i < bodies.length; i++) {
            bodies[i].movePoints(forces[i], dt); /* move all points */
            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
            System.out.println("mass: " + bodies[i].getMass());
            new Draw(bodies);

//            pointsEx = new PointsEx(bodies); /* try to draw it graphically */
        }
    }
}
