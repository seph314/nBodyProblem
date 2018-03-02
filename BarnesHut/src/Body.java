public class Body {
    private Vector position;
    private Vector velocity;
    private Vector force;
    private double mass;
    private double dt;
    private double G = 6.67e-11;

    public Body(double pX, double pY, double vX, double vY, double mass) {
        this.position = new Vector(pX, pY);
        this.velocity = new Vector(vX, vY);
        this.force = new Vector(0, 0);
        this.mass = mass;
    }

    public Vector getPosition() {
        return position;
    }
    public double getPositionX() {
        return position.getX();
    }
    public double getPositionY() {
        return position.getY();
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
    public void setPositionX(double x) {
        this.position.setX(x);
    }
    public void setPositionY(double y) {
        this.position.setY(y);
    }

    public Vector getVelocity() {
        return velocity;
    }
    public double getVelocityX() {
        return velocity.getX();
    }
    public double getVelocityY() {
        return velocity.getY();
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
    public void setVelocityX(double x) {
        this.velocity.setX(x);
    }
    public void setVelocityY(double y) {
        this.velocity.setX(y);
    }

    public Vector getForce() {
        return force;
    }
    public double getForceX() {
        return force.getX();
    }
    public double getForceY() {
        return force.getY();
    }

    public void setForce(Vector force) {
        this.force = force;
    }
    public void setForceX(double x) {
        this.force.setX(x);
    }
    public void setForceY(double y) {
        this.force.setX(y);
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getG() {
        return G;
    }

    public void setG(double g) {
        G = g;
    }

    public Body add(Body b) {
        double m = getMass() + b.getMass();
        double x = (getPositionX() * getMass() + b.getPositionX() * b.getMass()) / m;
        double y = (getPositionY() * getMass() + b.getPositionY() * b.getMass()) / m;

        return new Body(x, y, 0, 0, m);
    }

    public void addForce(Body body) {
        double a = Math.pow(body.getPositionX() - getPositionX(), 2);
        double b = Math.pow(body.getPositionY() - getPositionY(),2);
        double distance = Math.sqrt(a+b);
        double magnitute = (G*body.getMass()*getMass()) / Math.pow(distance,2);
        double directionX = body.getPositionX() - getPositionX();
        double directionY = body.getPositionY() - getPositionY();
        force.setX(force.getX() + magnitute * directionX / distance);
        force.setY(force.getY() + magnitute * directionY / distance);
    }

    public double distanceTo(Body b) {
        double dx = getPositionX() - b.getPositionX();
        double dy = getPositionY() - b.getPositionY();
        return Math.sqrt(dx*dx + dy*dy);
    }

    public void resetForces() {
        setForceX(0);
        setForceY(0);
    }

    public void update() {
        double deltaVx = getForceX()/getMass()*dt;
        double deltaVy = getForceY()/getMass()*dt;
        double deltaPx = (getVelocityX() + deltaVx/2) * dt;
        double deltaPy = (getVelocityY() + deltaVy/2) * dt;

        setVelocityX(getVelocityX()+deltaVx);
        setVelocityY(getVelocityY()+deltaVy);

        setPositionX(getPositionX() + deltaPx);
        setPositionY(getPositionY() + deltaPy);
    }


}
