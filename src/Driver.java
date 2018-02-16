import java.util.Arrays;

public class Driver {

    public static void main(String[] args) {

        Body[] bodies;
        int dt = 2;
        int numberOfBodies = 20;

        System.out.println("N-body problem");
        Creation creation = new Creation(numberOfBodies); /* creates random sized bodies via the Creation constructor */
        bodies = creation.getBodies(); /* get the bodies */

        /* prints the bodies positions */
        for (int i=0; i<bodies.length; i++){
            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
        }

        /* simulates bodies over time */
        new Simulate(bodies, dt).time();

    }
}
