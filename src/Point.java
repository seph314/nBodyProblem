
public class Point {

    private Vector position;        /* position */
    private Vector velocity;        /* velocity */
    private double mass;            /* mass */
    private double G = 6.67e-11;    /* gravitational constant */

    /**
     * Point Constructor
     * @param position is the position
     * @param velocity is the velocity
     * @param mass is the mass
     */
    public Point(Vector position, Vector velocity, double mass) {
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
        velocity = velocity.addition(acceleration.scalarProduct(dt));   /* dv = f/m * DT */
        position = position.addition(velocity.scalarProduct(dt));       /* dp = (v + dv/2) * DT */
    }


}
