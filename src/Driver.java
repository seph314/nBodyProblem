import java.util.Arrays;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws InterruptedException {
        int gnumBodies = 1200000;
        int numSteps = 1;
        double far = 0.5;
        int numWorkers = 4;


        double sizeOfTheUniverse = 200000;
        double[] startCoordinates = {sizeOfTheUniverse/2, sizeOfTheUniverse/2};
        Vector[] forces = null;
        Body[] bodies = null;
        int dt = 2;
        long t1, t2, t3 = 0;
        int program =3;

        while(program != 0){
            System.out.println("\n****** N-body problem ****** \n");

            Scanner scanner = new Scanner(System.in);

            // Reads program from user
            System.out.print("Choose a program: \n" +
                    "1 - Sequential N2 \n" +
                    "2 - Parallel N2 \n" +
                    "3 - Sequential Barnes Hut \n" +
                    "4 - Parallel Barnes Hut \n" +
                    "0 - Quit\n");
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
                far = scanner.nextDouble();
            }

            if(program == 2 || program == 4) {
                // Reads number of threads from user (Parallel only)
                System.out.print("Input number of threads: ");
                numWorkers = scanner.nextInt();
            }


            Creation creation = new Creation(gnumBodies, sizeOfTheUniverse, numSteps); /* creates random sized bodies via the Creation constructor */
            bodies = creation.getBodies(); /* get the bodies */
            forces = new Vector[gnumBodies]; /* a new force array with the length of tge bodies array */
            for (int i = 0; i < gnumBodies; i++)
                forces[i] = new Vector(new double[2]);

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
                t2 = System.nanoTime();
                t3 = t2 - t1;
            }
            /* Parallel Barnes Hut program */
            else if (program == 4) {
                t1 = System.nanoTime();
                InitiateBarnesHutParallel inBHP = new InitiateBarnesHutParallel(bodies, dt, far, numSteps, numWorkers, sizeOfTheUniverse, startCoordinates);
                inBHP.buildQuadTree();
                t2 = System.nanoTime();
                t3 = t2 - t1;
            }

            System.out.println("\ngnumBodies = " + gnumBodies);
            System.out.println("numSteps = " + numSteps);
            System.out.println("far = " + far);
            System.out.println("numWorkers = " + numWorkers);
            System.out.println("program = " + program);
            System.out.println("The simulaiton took " + t3);
            System.out.println(bodies[119].getXPosition());

    }
    }

}
