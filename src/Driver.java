import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Driver {

    public static void main(String[] args) throws InterruptedException {
        int gnumBodies = 4;
        int numSteps = 10;//12000;
        double far = 0.5;
        int numWorkers = 4;


        double sizeOfTheUniverse = 160;
        double[] startCoordinates = {80, 80};
        boolean firstrun = true;
        Vector[] forces = null;
        Body[] bodies = null;
        int dt = 2;
        long t1, t2, t3 = 0;
        int program = 4;
       // while(program != 0){
           /* System.out.println("\n****** N-body problem ****** \n");

            Scanner scanner = new Scanner(System.in);

            // Reads program from user
            System.out.print("Choose a program: \n" +
                    "1 - Sequential N2 \n" +
                    "2 - Parallel N2 \n" +
                    "3 - Sequential Barnes Hut \n" +
                    "4 - Parallel Barnes Hut \n" +
                    "0 - Quit");
            program = scanner.nextInt();

            // Reads number of bodies from user
            System.out.print("Input number of bodies: ");
            gnumBodies = scanner.nextInt();

            // Reads the number of time steps in the simulation
            System.out.print("Number of time steps: ");
            numSteps = scanner.nextInt();

            if(program == 3 || program == 4){
                // Reads the distance used to decide when to approximate (Barnes-Hut programs only)
                System.out.print("Distance to approximate: ");
                far = scanner.nextInt();
            }

            if(program == 2 || program == 4) {
                // Reads number of threads from user (Parallel only)
                System.out.print("Input number of threads: ");
                numWorkers = scanner.nextInt();
            }*/


            Creation creation = new Creation(gnumBodies, sizeOfTheUniverse); /* creates random sized bodies via the Creation constructor */
            bodies = creation.getBodies(); /* get the bodies */
            forces = new Vector[gnumBodies]; /* a new force array with the length of tge bodies array */
            for (int i = 0; i < gnumBodies; i++)
                forces[i] = new Vector(new double[2]);

            /* prints the bodies positions */
            /*for (int i = 0; i < bodies.length; i++) {
                System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
                System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
                System.out.println("mass: " + bodies[i].getMass());
            }*/

            System.out.println("\nBody " + (bodies.length-1) + "\nposition: " + Arrays.toString(bodies[(bodies.length-1)].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[(bodies.length-1)].getVelocity()));
            System.out.println("mass: " + bodies[(bodies.length-1)].getMass());



            //SimulateN2Parallel simulation = new SimulateN2Parallel(bodies, dt);

            /* Sequential N2 program */
            if (program == 1) {
                t1 = System.nanoTime();
                InitiateN2Seq inN2S = new InitiateN2Seq(bodies, dt, gnumBodies, forces, numSteps);
                inN2S.initiate();
                t2 = System.nanoTime();
                t3 = t2 - t1;
            }
            /* Parallel N2 program */
            else if (program == 2) {
                t1 = System.nanoTime();
                InitiateN2Parallel inN2P = new InitiateN2Parallel(bodies, dt, gnumBodies, numWorkers, forces, numSteps);
                inN2P.initiate();
                t2 = System.nanoTime();
                t3 = t2 - t1;
            }
            /* Sequential Barnes Hut program */
            else if (program == 3) {
                t1 = System.nanoTime();
                InitiateBarnesHutSeq inBHS = new InitiateBarnesHutSeq(bodies, dt, far, numSteps, sizeOfTheUniverse, startCoordinates);
                inBHS.buildQuadTree();
                //inBHS.initiate();
                t2 = System.nanoTime();
                t3 = t2 - t1;
            }
            /* Parallel Barnes Hut program */
            else if (program == 4) {
                t1 = System.nanoTime();
                InitiateBarnesHutParallel inBHP = new InitiateBarnesHutParallel(bodies, dt, far, numSteps, numWorkers, sizeOfTheUniverse, startCoordinates);
                inBHP.buildQuadTree();
                //inBHP.initiate();
                t2 = System.nanoTime();
                t3 = t2 - t1;
            }


            //prints body, velocity and mass;
            for (int i = 0; i<bodies.length; i++) {
                System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
                System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
                System.out.println("mass: " + bodies[i].getMass());
            }

           //prints last body
       /* System.out.println("\nBody " + (bodies.length-2) + "\nposition: " + Arrays.toString(bodies[(bodies.length-2)].getPosition()));
        System.out.println("velocity: " + Arrays.toString(bodies[(bodies.length-2)].getVelocity()));
        System.out.println("mass: " + bodies[(bodies.length-2)].getMass());

            System.out.println("\nBody " + (bodies.length-1) + "\nposition: " + Arrays.toString(bodies[(bodies.length-1)].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[(bodies.length-1)].getVelocity()));
            System.out.println("mass: " + bodies[(bodies.length-1)].getMass());*/


            System.out.println("\ngnumBodies = " + gnumBodies);
            System.out.println("numSteps = " + numSteps);
            System.out.println("far = " + far);
            System.out.println("numWorkers = " + numWorkers);
        System.out.println("program = " + program);
            System.out.println("The simulaiton took " + t3/1000000 + "ms");


        //new Draw(bodies);




       // }
    }

}
