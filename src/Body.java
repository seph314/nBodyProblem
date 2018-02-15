
public class Body {

    private Vector position;            /* position */
    private Vector velocity;            /* velocity */
    private double mass;                /* mass */
    private final double G = 6.67e-11;  /* gravitational constant */

    /**
     * Body Constructor
     * @param position is the position
     * @param velocity is the velocity
     * @param mass is the mass
     */
    Body(Vector position, Vector velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

    /**
     * Calculate new velocity and position for each body
     * Force vector and small time step dt as arguments
     * @param force is the force
     * @param dt is a small time step
     */
    public void movePoints(Vector force, double dt){
        Vector acceleration = force.scalarProduct(1/mass);
        velocity = velocity.add(acceleration.scalarProduct(dt));   /* dv = f/m * DT */
        position = position.add(velocity.scalarProduct(dt));       /* dp = (v + dv/2) * DT */
    }

    /**
     * calculate total force for every pair of bodies
     */
    public Vector calculateForces(Body otherBody){
        Vector delta = otherBody.position.subtract(this.position);
        double distance = delta.magnitude();
        double force = (G * this.mass * otherBody.mass) / (distance*distance); // Newtons law of Gravity F = G * m1 * m2 / r 2
        return delta.direction().scalarProduct(force);
    }

    public double[] getPosition() {
        return position.coordinates;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getMass() {
        return mass;
    }
}
