import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Creation {
    private static int gnumBodies;
    private static int numSteps;
    private static double far;
    private static int numWorkers;
    private static double dt;
    private static double sizeOfTheUniverse;
    private static double SOLARMASS = 1.98892e30;
    private double G = 6.67e-11;

    public Creation(int gnumBodies, int numSteps, double far, int numWorkers, double dt, double sizeOfTheUniverse) {
        this.gnumBodies = gnumBodies;
        this.numSteps = numSteps;
        this.far = far;
        this.numWorkers = numWorkers;
        this.dt = dt;
        this.sizeOfTheUniverse = sizeOfTheUniverse;
        create();
    }

    public void create(){
       /* Body[] bodies = new Body[gnumBodies];
        for(int i = 1; i < gnumBodies; i++){
            double positionX = sizeOfTheUniverse * exp(-1.8) * (.5 - Math.random());
            double positionY = sizeOfTheUniverse * exp(-1.8) * (.5 - Math.random());
            double magv = circlev(positionX, positionY);

            double absangle = Math.atan(Math.abs(positionY / positionX));
            double thetav = Math.PI / 2 - absangle;
            double velocityX = -1 * Math.signum(positionY) * Math.cos(thetav) * magv;
            double velocityY = Math.signum(positionX) * Math.sin(thetav) * magv;

            double mass = Math.random() * SOLARMASS * 10 + 1e20;
            int red     = (int) Math.floor(mass*254/(SOLARMASS*10+1e20));
            int blue   = (int) Math.floor(mass*254/(SOLARMASS*10+1e20));
            int green    = 255;
            bodies[i] = new Body(positionX, positionY, velocityX, velocityY, mass);
        }
        Body body = new Body(0, 0, 0, 0, 1e6 * SOLARMASS);
        bodies[0] = body;*/
        Body[] bodies = new Body[gnumBodies];
        bodies[0] = new Body(0.0, 0.0, 0.0, 0.0, 1.98892E36);
        bodies[1] = new Body(3.809639858694135E17, 1.3690226840220046E16, -669.9401070455363, 18642.719105576605, 1.517027186469243E31);
        bodies[2] = new Body(-1.03812766538513408E17, 1.15489014797309664E17, -21736.992806940154, -19539.324700939353, 8.05714212041258E29);
        bodies[3] = new Body(-1.5997336261317848E16, -7.8481837311680464E16, 39877.44458114179, -8128.414319266304, 6.036657211190983E30);
        Quad quad = new Quad(0,0, 2*sizeOfTheUniverse);
        //addForces(quad, bodies);
        addForcesParallel(quad, bodies);
    }

    public double circlev(double rx, double ry) {
        double r2 = Math.sqrt(rx * rx + ry * ry);
        double numerator = (G) * 1e6 * SOLARMASS;
        return Math.sqrt(numerator / r2);
    }
    public double exp(double lambda) {
        return -Math.log(1 -  Math.random()) / lambda;
    }

    public void addForcesParallel(Quad quad, Body[] bodies){
        for (Body body : bodies) {
            System.out.println("\nBody " + body + "\npositionx: " + body.getPositionX()+ "positionx: " + body.getPositionY() );
            System.out.println("velocityX: " + body.getVelocityX() + "velocityY: " + body.getVelocityY() );
            System.out.println("mass: " + body.getMass());
        }
        QuadTree tree = new QuadTree(quad);
        ReentrantLock lock = new ReentrantLock();
        CyclicBarrier barrier = new CyclicBarrier(numWorkers);
        // aggregatedBodies = new Body(new Vector(new double[]{0,0}),new Vector(new double[]{0,0}), 0);
        tree.NW = new QuadTree(quad.NW());
        tree.NE = new QuadTree(quad.NE());
        tree.SW = new QuadTree(quad.SW());
        tree.SE = new QuadTree(quad.SE());
        QuadTree[] qTArray = new QuadTree[]{tree.NW, tree.NE, tree.SW, tree.SE};
        String[] names = new String[]{"NW", "NE", "SW", "SE"};
        Semaphore semaphore = new Semaphore(1);
        int low = 0;
        BHWorker worker[] = new BHWorker[numWorkers];
        for (int i = 0; i < numWorkers; i++) {
            worker[i] = new BHWorker(semaphore, numSteps, numWorkers, low, dt, bodies, tree, quad, qTArray[i], lock, barrier);
            low += (bodies.length / numWorkers);
            worker[i].setName(names[i]);
            worker[i].start();
        }
        try {
            worker[0].join();
            worker[1].join();
            worker[2].join();
            worker[3].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*for (Body body : bodies) {
            if (quad.contains(body)) {
                body.resetForces();
                tree.updateForce(body);
                body.update();
            }
        }*/

        for (Body body : bodies) {
            System.out.println("\nBody " + body + "\npositionx: " + body.getPositionX()+ "positionx: " + body.getPositionY() );
            System.out.println("velocityX: " + body.getVelocityX() + "velocityY: " + body.getVelocityY() );
            System.out.println("mass: " + body.getMass());
        }

    }

    public void addForces(Quad quad, Body[] bodies) {
        // Create the tree
        QuadTree tree = new QuadTree(quad);
       /* for (Body body : bodies) {
            System.out.println("\nBody " + body + "\npositionx: " + body.getPositionX()+ "positionx: " + body.getPositionY() );
            System.out.println("velocityX: " + body.getVelocityX() + "velocityY: " + body.getVelocityY() );
            System.out.println("mass: " + body.getMass());
        }*/
        for (Body body : bodies) {
            System.out.println(body.getPositionX()+ ", " + body.getPositionY()+", "+
                    body.getVelocityX() + ", " + body.getVelocityY()+ ", " +  body.getMass() + ", color");

        }

        // Insert bodies into the tree
        for (Body body : bodies) {
            if (quad.contains(body)) // Ignore bodies outside of the universe
                tree.insert(body);
        }

        // Calculate forces acting on bodies and then move them.
        for (Body body : bodies) {
            if (quad.contains(body)) {
                body.resetForces();
                tree.updateForce(body);
                body.update();
            }
        }

        for (Body body : bodies) {
            System.out.println("\nBody " + body + "\npositionx: " + body.getPositionX()+ "positionx: " + body.getPositionY() );
            System.out.println("velocityX: " + body.getVelocityX() + "velocityY: " + body.getVelocityY() );
            System.out.println("mass: " + body.getMass());
        }
    }


}
