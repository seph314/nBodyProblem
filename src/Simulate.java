public class Simulate {

    private Body[] bodies;
    private int dt;

    /**
     * Constructor
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
    public Body[] time() {

        Vector[] forces = new Vector[bodies.length]; /* a new force array with the length of tge bodies array */
        for (int i = 0; i < bodies.length; i++) {
            forces[i] = new Vector(new double[2]);
        }

        for (int i=0; i<bodies.length; i++)
            for (int j = 0; j < bodies.length; j++) {
                forces[i] = forces[i].add(bodies[j].calculateForces(bodies[i])); /* calculate an array of forces */
            }

        for (int i=0; i<bodies.length; i++)
            bodies[i].movePoints(forces[i], dt); /* move all points */

        return bodies; /* returns the bodies array containing the new values */
    }

}
