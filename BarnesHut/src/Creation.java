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
    private int program;
    public QuadTree shared;
    private Quad quad;

    public Creation(int gnumBodies, int numSteps, double far, int numWorkers, double dt, double sizeOfTheUniverse, int program) {
        this.gnumBodies = gnumBodies;
        this.numSteps = numSteps;
        this.far = far;
        this.numWorkers = numWorkers;
        this.dt = dt;
        this.sizeOfTheUniverse = sizeOfTheUniverse;
        this.program = program;
        this.quad = new Quad(0,0, 2*sizeOfTheUniverse);
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
            bodies[i] = new Body(positionX, positionY, velocityX, velocityY, mass, dt);
        }
        Body body = new Body(0, 0, 0, 0, 1e6 * SOLARMASS, dt);
        bodies[0] = body;*/
        Body[] bodies = new Body[gnumBodies];
        bodies[0] = new Body(0.01, 0.01, 0.0, 0.0, 1.98892E36, dt);
        bodies[1] = new Body(-9.7931852131575E8, -1.0181952999165366E9, 2.2086002273093686E8, -2.1242713543890244E8, 1.1296817349602967E31, dt);
        bodies[2] = new Body(-1.3002285403837226E8, -2.125995429661797E9, 2.4910036673933232E8, -1.523462382541696E7, 1.8226506438833028E31, dt);
        bodies[3] = new Body(1.947171244218845E9, -1.3373505917622253E10, 9.804289233273485E7, 1.4274962887539351E7, 2.6732587866773563E30, dt);
        bodies[4] = new Body(-5.003529277153006E9, 1.2495325098504513E10, -9.21631266051269E7, -3.6905074386412136E7, 1.8731817248339255E31, dt);
        bodies[5] = new Body(7.756319974536308E9, -1.0446708649255674E9, 1.7378477254364368E7, 1.2902918496215086E8, 6.594601312440683E30, dt);
        bodies[6] = new Body(1.4081704597265278E10, -3.322214510147346E9, 2.198737000752703E7, 9.31967663048446E7, 8.841727217288336E30, dt);
        bodies[7] = new Body(5.164267886672828E10, 1.9893594519048283E9, -1950242.9146291981, 5.062723504084273E7, 6.859491236797967E30, dt);



        long t1, t2, t3;
        t1 = System.nanoTime();
        if(program == 1)
            addForces(quad, bodies);
        if (program == 2)
            addForcesParallel(quad, bodies);
        t2 = System.nanoTime();
        t3 = t2 - t1;
        System.out.println("TIME: " + ":" + t3 / 10000);
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
            worker[i] = new BHWorker(this, semaphore, numSteps, numWorkers, low, dt, bodies, tree, quad, qTArray[i], lock, barrier);
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
        System.out.println("\nPARALLEL");

    }

    public void addForces(Quad quad, Body[] bodies) {
        for (Body body : bodies) {
            System.out.println("\nBody " + body + "\npositionx: " + body.getPositionX()+ "positionx: " + body.getPositionY() );
            System.out.println("velocityX: " + body.getVelocityX() + "velocityY: " + body.getVelocityY() );
            System.out.println("mass: " + body.getMass());
        }
        /*for (Body body : bodies) {
            System.out.println(body.getPositionX()+ ", " + body.getPositionY()+", "+
                    body.getVelocityX() + ", " + body.getVelocityY()+ ", " +  body.getMass() + ", color);");

        }*/
        for (int i = 0; i < numSteps; i++){
        // Create the tree
        QuadTree tree = new QuadTree(quad);

        // Insert bodies into the tree
        for (Body body : bodies) {
            if (quad.contains(body)){ // Ignore bodies outside of the universe
                tree.insert(body);
            }
        }

        // Calculate forces acting on bodies and then move them.
        for (Body body : bodies) {
            if (quad.contains(body)) {
                body.resetForces();
                tree.updateForce(body);
                body.update();
            }
        }

        }

        for (Body body : bodies) {
            System.out.println("\nBody " + body + "\npositionx: " + body.getPositionX()+ "positionx: " + body.getPositionY() );
            System.out.println("velocityX: " + body.getVelocityX() + "velocityY: " + body.getVelocityY() );
            System.out.println("mass: " + body.getMass());
        }
        System.out.println("\nSEQUENTIAL");
    }

    public void newTree(){
        this.shared = new QuadTree(quad);
    }


}
