import java.util.Arrays;

public class Driver {

    public static void main(String[] args) {

        Body[] bodies;
        int dt = 2;
        System.out.println("N-body problem (or maybe, opportunity?)");
        Simulation simulation = new Simulation(); /* creates bodies via the Simulation constructor */
        bodies = simulation.getBodies();

        System.out.println(Arrays.toString(bodies[0].getPosition()));
        System.out.println(Arrays.toString(bodies[1].getPosition()));
        System.out.println(Arrays.toString(bodies[2].getPosition()));


        Vector force = bodies[0].calculateForces(bodies[1]); /* calculate the force between two bodies */
        bodies[0].movePoints(force, dt);

        System.out.println(Arrays.toString(bodies[0].getPosition()));
    }
}
