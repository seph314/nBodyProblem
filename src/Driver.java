import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

public class Driver {

    public static void main(String[] args) throws InterruptedException {

        Body[] bodies;
        int dt = 2;
        int numberOfBodies = 4000;
        int workers = 1;
        CyclicBarrier barrier = new CyclicBarrier(workers);

        System.out.println("N-body problem");
        Creation creation = new Creation(numberOfBodies); /* creates random sized bodies via the Creation constructor */
        bodies = creation.getBodies(); /* get the bodies */

        /* prints the bodies positions */
        for (int i=0; i<bodies.length; i++){
            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
            System.out.println("mass: " + bodies[i].getMass());
        }

        //Simulate simulation = new Simulate(bodies, dt);

        /* simulates bodies over time */
        long t1, t2, t3;
        t1 = System.nanoTime();

        Vector[] forces = new Vector[bodies.length]; /* a new force array with the length of tge bodies array */
        for (int i = 0; i < bodies.length; i++) {
            forces[i] = new Vector(new double[2]);
        }
        int w = 0;
        Worker worker[] = new Worker[workers];
        for(int i = 0; i < worker.length; i++){
            worker[i] = new Worker(w, workers, barrier, bodies, dt, forces);
            w += (numberOfBodies/workers);
            worker[i].start();
        }

        for(int i = 0; i < worker.length; i++){
            worker[i].join();
        }
        t2 = System.nanoTime();
        t3 = t2-t1;

        //new Simulate(bodies, dt).time();

        for (int i = 0; i<bodies.length; i++) {
            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
            System.out.println("mass: " + bodies[i].getMass());
        }
        System.out.println(t3/10000000);




//        /* prints them again after movement */
//        System.out.println("\n\nA F T E R   M O V E M E N T");
//        for (int i=0; i<simulatedBodies.length; i++){
//            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(simulatedBodies[i].getPosition()));
//            System.out.println("velocity: " + Arrays.toString(simulatedBodies[i].getVelocity()));
//
//        }


//        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
//        /* T H I S   I S   O N L Y    T E S T S   T O   S E E   I F   I T   S E E M S   T O   W O  R K */
//        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
//
////        /* prints the bodies positions */
////        for (int i=0; i<bodies.length; i++){
////            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
////            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
////
////        }
////
////        /* calculate forces between a lot of bodies and move bodies */
////        for (int i=0; i<bodies.length-1; i++){
////            Vector force = bodies[i].calculateForces(bodies[i+1]); /* calculate the force between two bodies */
////            bodies[i].movePoints(force, dt); /* movie body */
////            bodies[i+1].movePoints(force, dt); /* movie body */
////        }
////
////        /* prints them again after movement */
////        System.out.println("\n\nA F T E R   M O V E M E N T");
////        for (int i=0; i<bodies.length; i++){
////            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
////            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
////
////        }
    }
}
