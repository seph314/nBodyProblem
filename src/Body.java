
public class Body {

    private Vector position;            /* position */
    private Vector velocity;            /* velocity */
    private double mass;                /* mass */
    public double fx, fy;
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

    public void resetForce() {
        fx = 0;
        fy = 0;
    }

    public void addForce(Body b) {
        Body a = this;
        double EPS = 3E4; // softening parameter
        double dx = b.getXPosition() - a.getXPosition();
        double dy = b.getYPosition() - a.getYPosition();
        double dist = Math.sqrt(dx*dx + dy*dy);
        double F = (G * a.mass * b.mass) / (dist*dist);// + EPS*EPS);
        a.fx += F * dx / dist;
        a.fy += F * dy / dist;
    }

    public void update(double dt) {
        double[] v;
        v = getVelocity();
        v[0] += dt * fx / mass;
        v[1] += dt * fy / mass;
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
     * @param force is the force
     * @param dt is a small time step
     */
    public void movePoints(Vector force, double dt){
        Vector acceleration = force.scalarProduct(1/mass);
        velocity = velocity.add(acceleration.scalarProduct(dt));   /* dv = f/m * DT */
        position = position.add(velocity.scalarProduct(dt));       /* dp = (v + dv/2) * DT */
    }

    /**
     * calculate total force for two pair of bodies
     */
    public Vector calculateForces(Body otherBody){
        Vector delta = otherBody.position.subtract(this.position);
        double distance = delta.magnitude();
        double force = (G * this.mass * otherBody.mass) / (distance*distance); // Newtons law of Gravity F = G * m1 * m2 / r 2
        return delta.direction().scalarProduct(force);
    }

    /**
     * Calculates distance between two bodies
     * @param body is the other body
     * @return distance
     */
    public double calculateDistance(Body body){
        double dx = this.position.getX() - body.position.getX();
        double dy = this.position.getY() - body.position.getY();
        return Math.sqrt(dx*dx + dy*dy);
    }

    public double[] getPosition() {
        return position.coordinates;
    }

    public double[] getVelocity() {
        return velocity.coordinates;
    }

    public double getXVelocity(){
        double[] array = velocity.coordinates;
        return array[0];
    }
    public double getYVelocity(){
        double[] array = velocity.coordinates;
        return array[1];
    }

    public double getXPosition(){
        double[] array = position.coordinates;
        return array[0];
    }

    public double getYPosition(){
        double[] array = position.coordinates;
        return array[1];
    }

    public double getMass() {
        return mass;
    }

    /**
     * Tells us if a body is in a Quadrant or not
     * @param quad is the quadrant we are looking in
     * @return true if the body is there
     */
    public boolean inQuad(Quad quad){
        return quad.containsBody(this);
    }

    /**
     * Adds two bodies together
     * to be able to calculate the representation of multiple bodies in the QuadTree
     * @param body is the body we want to aggregate
     * @return the aggregated body
     */
    public Body aggregate(Body body){
      //  Vector position = this.position.add(body.position);
        //Vector velocity = this.velocity.add(body.velocity);
        double mass = this.mass + body.mass;
        double x[] = new double[2];
        double v[] = new double[2];
        x[0] = ((body.getXPosition()*body.getMass() + this.getXPosition()*this.getMass()))/mass;
        x[1] = (body.getYPosition()*body.getMass() + this.getXPosition()*this.getMass())/mass;
        v[0] = this.getXVelocity();
        v[1] = this.getYVelocity();
        Vector pVector = new Vector(x);
        Vector vVector = new Vector(v);

        return new Body(pVector, vVector, mass);
    }
}
