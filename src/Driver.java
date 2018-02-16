import java.util.Arrays;

public class Driver {

    public static void main(String[] args) {

        Body[] bodies;
        int dt = 2;
        int numberOfBodies = 20;

        System.out.println("N-body problem (or maybe, opportunity?)");
//        Simulation simulation = new Simulation(); /* creates bodies via the Simulation constructor */
        Simulation simulation = new Simulation(numberOfBodies); /* creates random sized bodies via the Simulation constructor */
        bodies = simulation.getBodies(); /* get the bodies */

        /* prints the bodies positions */
        for (int i=0; i<bodies.length; i++){
            System.out.println("Position för body " + i + " " + Arrays.toString(bodies[i].getPosition()));
        }

        Vector force = bodies[0].calculateForces(bodies[1]); /* calculate the force between two bodies */
        bodies[0].movePoints(force, dt);

        System.out.println(Arrays.toString(bodies[0].getPosition()));
    }
}
