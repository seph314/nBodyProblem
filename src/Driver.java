import java.util.Arrays;

public class Driver {

    public static void main(String[] args) {

        Body[] bodies;
        int dt = 2;
        int numberOfBodies = 20;

        System.out.println("N-body problem");
//        Simulation simulation = new Simulation(); /* creates bodies via the Simulation constructor */
        Simulation simulation = new Simulation(numberOfBodies); /* creates random sized bodies via the Simulation constructor */
        bodies = simulation.getBodies(); /* get the bodies */

        /* prints the bodies positions */
        for (int i=0; i<bodies.length; i++){
            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));

        }

        /* calculate forces between a lot of bodies and move bodies */
        for (int i=0; i<bodies.length-1; i++){
            Vector force = bodies[i].calculateForces(bodies[i+1]); /* calculate the force between two bodies */
            bodies[i].movePoints(force, dt); /* movie body */
            bodies[i+1].movePoints(force, dt); /* movie body */
        }

        /* prints them again after movement */
        System.out.println("\n\nA F T E R   M O V E M E N T");
        for (int i=0; i<bodies.length; i++){
            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));

        }

    }
}
