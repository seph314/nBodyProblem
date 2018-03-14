import java.util.Random;

public class Body {

    private Vector position;            //position
    private Vector velocity;            //velocity
    private Vector force;

    private double mass;                //mass
    private double G = 6.67e-11;  // gravitational constant

    /**
     * Body Constructor
     *
     * @param position is the position
     * @param velocity is the velocity
     * @param mass     is the mass
     */
    Body(Vector position, Vector velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.force = new Vector(new double[]{0, 0});
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    private Vector getForce() {
        return force;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    void resetForce() {
        force.setCoordinates(new double[]{0, 0});
    }

    public void setForce(Vector force) {
        this.force = force;
    }

    void addForce(Body b) {
        double dx = b.getXPosition() - this.getXPosition();
        double dy = b.getYPosition() - this.getYPosition();
        double dist = Math.sqrt(dx * dx + dy * dy);
        double F = (G * this.mass * b.mass) / (dist * dist);
        double fx = force.getX();
        double fy = force.getY();

        fx += F * dx / dist;
        fy += F * dy / dist;
        double[] array = {fx, fy};
        this.force.setCoordinates(array);

    }

    void update(double dt) {
        double[] v;
        v = getVelocity();
        v[0] += dt * force.getX() / mass;
        v[1] += dt * force.getY() / mass;
        this.velocity.setCoordinates(v);

        double[] r;
        r = getPosition();
        r[0] += dt * v[0];
        r[1] += dt * v[1];
        this.position.setCoordinates(r);

    }


    /**
     * Calculate new velocity and position for each body
     * Force vector and small time step dt as arguments
     *
     * @param force is the force
     * @param dt    is a small time step
     */
    void moveBodies(Vector force, double dt) {
        Vector acceleration = force.scalarProduct(1 / mass);
        velocity = velocity.add(acceleration.scalarProduct(dt));   // dv = f/m * DT
        position = position.add(velocity.scalarProduct(dt));       // dp = (v + dv/2)
    }

    /**
     * calculate total force for two pair of bodies
     */
    Vector calculateForces(Body otherBody) {
        Vector delta = otherBody.position.subtract(this.position);
        double distance = delta.magnitude();
        double force = (G * this.mass * otherBody.mass) / (distance * distance); // Newtons law of Gravity F = G * m1 * m2 / r 2
        return delta.direction().scalarProduct(force);
    }

    /**
     * Calculates distance between two bodies
     *
     * @param body is the other body
     * @return distance
     */
    double calculateDistance(Body body) {
        double dx = this.position.getX() - body.position.getX();
        double dy = this.position.getY() - body.position.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double[] getPosition() {
        return position.coordinates;
    }

    private double[] getVelocity() {
        return velocity.coordinates;
    }

    private double getXVelocity() {
        double[] array = velocity.coordinates;
        return array[0];
    }

    private double getYVelocity() {
        double[] array = velocity.coordinates;
        return array[1];
    }

    double getXPosition() {
        double[] array = position.coordinates;
        return array[0];
    }

    double getYPosition() {
        double[] array = position.coordinates;
        return array[1];
    }

    private double getMass() {
        return mass;
    }

    /**
     * Tells us if a body is in a Quadrant or not
     *
     * @param quad is the quadrant we are looking in
     * @return true if the body is there
     */
    boolean inQuad(Quad quad) {
        return quad.containsBody(this);
    }

    /**
     * Adds two bodies together
     * to be able to calculate the representation of multiple bodies in the QuadTree
     *
     * @param body is the body we want to aggregate
     * @return the aggregated body
     */
    Body aggregate(Body body) {
        double mass = this.mass + body.mass;
        double x[] = new double[2];
        double v[] = new double[2];
        x[0] = ((body.getXPosition() * body.getMass() + this.getXPosition() * this.getMass())) / mass;
        x[1] = (body.getYPosition() * body.getMass() + this.getYPosition() * this.getMass()) / mass;
        v[0] = this.getXVelocity();
        v[1] = this.getYVelocity();
        Vector pVector = new Vector(x);
        Vector vVector = new Vector(v);

        return new Body(pVector, vVector, mass);
    }

}
