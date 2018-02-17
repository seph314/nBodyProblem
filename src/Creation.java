
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Creation {

    private Body[] bodies;

    /**
     * Generates custom value bodies
     */
    Creation() {
        Scanner scanner = new Scanner(System.in); /* create a Scanner */
        System.out.print("Number of bodies: ");
        int numberOfBodies = scanner.nextInt();
        bodies = new Body[numberOfBodies]; /* set the body array tp the right size */

        for (int i = 0; i < numberOfBodies; i++) {

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
            bodies[i] = new Body(positionVector, velocityVector, mass);
        }
    }


    /**
     * Creates a number of bodies with random values
     *
     * @param numberOfBodies is the number of random bodies
     */
    Creation(int numberOfBodies) {

        bodies = new Body[numberOfBodies]; /* set the body array tp the right size */
        int m = 0;
        int p = 0;
        int v = 0;

        for (int i = 0; i < numberOfBodies; i++) {
            m += 200;
            p += 10;
            v += 10;

            /* generate random x and y position coordinates */
            double px = 10 + p;//ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double py = 10 + p;//ThreadLocalRandom.current().nextInt(10, 100 + 1);

            /* generate random x and y velocity coordinates */
            double vx = 50 + v;//ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double vy = 50 + v;//ThreadLocalRandom.current().nextInt(10, 100 + 1);

            /* generate random mass */
            double mass = 100 + m;//ThreadLocalRandom.current().nextInt(100, 1000 + 1);

            /* create position and velocity arrays */
            double[] postion = {px, py};
            double[] velocity = {vx, vy};

            /* create position and velocity vectors */
            Vector positionVector = new Vector(postion);
            Vector velocityVector = new Vector(velocity);

            /* add a new body to bodies */
            bodies[i] = new Body(positionVector, velocityVector, mass);
        }
    }

    /**
     * Getter that returns the bodies we created
     *
     * @return
     */
    public Body[] getBodies() {
        return bodies;
    }
}
