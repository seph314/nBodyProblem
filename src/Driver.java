import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Driver {

    public static void main(String[] args) throws InterruptedException {

        Body[] bodies;
        int dt = 2;
        int numberOfBodies = 5000;
        int workers = 8;
        long t1, t2, t3 = 0;
        int program = 2;

        System.out.println("****** N-body problem ****** \n");

        Scanner scanner = new Scanner(System.in); /* create a Scanner */
        // Reads program from user
        System.out.print("Choose a program: \n" +
                "1 - Sequential N2 \n" +
                "2 - Parallel N2 \n" +
                "3 - Sequential Barnes Hut \n" +
                "4 - Parallel Barnes Hut \n");
        program = scanner.nextInt();

        /* Reads number of threads from user */
        if(program == 2 || program == 4){
            System.out.print("Input number of threads: ");
            workers = scanner.nextInt();
        }



        Creation creation = new Creation(numberOfBodies); /* creates random sized bodies via the Creation constructor */
        bodies = creation.getBodies(); /* get the bodies */

        /* prints the bodies positions */
        for (int i=0; i<bodies.length; i++){
            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
            System.out.println("mass: " + bodies[i].getMass());
        }

        Vector[] forces = new Vector[numberOfBodies]; /* a new force array with the length of tge bodies array */
        for (int i = 0; i < numberOfBodies; i++) {
            forces[i] = new Vector(new double[2]);
        }


        //Simulate simulation = new Simulate(bodies, dt);

        /* Sequential N2 program */
        if(program == 1){
            t1 = System.nanoTime();
            InitiateN2Seq inN2S = new InitiateN2Seq(bodies, dt, numberOfBodies, forces);
            inN2S.initiate();
            t2 = System.nanoTime();
            t3 = t2-t1;
        }
        /* Parallel N2 program */
        else if(program == 2){
            t1 = System.nanoTime();
            InitiateN2Parallel inN2P = new InitiateN2Parallel(bodies, dt, numberOfBodies, workers, forces);
            inN2P.initiate();
            t2 = System.nanoTime();
            t3 = t2-t1;
        }
        /* Sequential Barnes Hut program */
        else if(program == 3){
            t1 = System.nanoTime();
            InitiateBarnesHutSeq inBHS = new InitiateBarnesHutSeq();
            //inBHS.initiate();
            t2 = System.nanoTime();
            t3 = t2-t1;
        }
        /* Parallel Barnes Hut program */
        else if(program == 4){
            t1 = System.nanoTime();
            InitiateBarnesHutParallel inBHP = new InitiateBarnesHutParallel();
            //inBHP.initiate();
            t2 = System.nanoTime();
            t3 = t2-t1;
        }


        //prints body, velocity and mass;
        for (int i = 0; i<bodies.length; i++) {
            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
            System.out.println("mass: " + bodies[i].getMass());
        }
        System.out.println(t3/10000000);
       // new Draw(bodies);


    }

}
