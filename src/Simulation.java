
import java.util.Scanner;

public class Simulation {

    private Body[] bodies;

    public Simulation() {
        Scanner scanner = new Scanner(System.in); /* create a Scanner */
        System.out.print("Number of bodies: ");
        int numberOfBodies = scanner.nextInt();
        bodies = new Body[numberOfBodies]; /* set the body array tp the right size */

        for (int i = 0; i<numberOfBodies; i++){

            /* read x and y values for the position- and velocity vector */
            System.out.print("Body " + i + " px: ");
            double px = scanner.nextDouble();
            System.out.print("Body " + i + " py: ");
            double py = scanner.nextDouble();
            System.out.print("Body " + i + " vx: ");
            double vx = scanner.nextDouble();
            System.out.print("Body " + i + " vy: ");
            double vy = scanner.nextDouble();
            /* read mass*/
            System.out.print("Body " + i + " mass: ");
            double mass = scanner.nextDouble();
            /* create position and velocity arrays */
            double[] postion = {px, py};
            double[] velocity = {vx, vy};
            /* create position and velocity vectors */
            Vector positionVector = new Vector(postion);
            Vector velocityVector = new Vector(velocity);
            /* add a new body to bodies */
            bodies[i] = new Body(positionVector,velocityVector,mass);
        }
    }

    public Body[] getBodies() {
        return bodies;
    }
}
