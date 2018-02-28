
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Creation {

    private Body[] bodies;


    /**
     * Creates a number of bodies with random values
     *
     * @param numberOfBodies is the number of random bodies
     */
    Creation(int numberOfBodies, double sizeOfTheUniverse) {

        bodies = new Body[numberOfBodies]; /* set the body array tp the right size */
        int m = 1000;
        int p = 0;
        int v = 0;
        double mid = sizeOfTheUniverse/2;


        for (int i = 0; i < numberOfBodies; i++) {
            m += 100;
            p += 10;
            v += 1;

            double mass = m;

            double NWpx = mid - p;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double NEpx = mid + p;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double SWpx = mid - p;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double SEpx = mid + p;// ThreadLocalRandom.current().nextInt(10, 100 + 1);

            double NWpy = mid + p;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double NEpy = mid + p;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double SWpy = mid - p;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double SEpy = mid - p;// ThreadLocalRandom.current().nextInt(10, 100 + 1);

            double NWvx = -v;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double NEvx = v;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double SWvx = -v;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double SEvx = v;// ThreadLocalRandom.current().nextInt(10, 100 + 1);

            double NWvy = v;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double NEvy = v;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double SWvy = -v;// ThreadLocalRandom.current().nextInt(10, 100 + 1);
            double SEvy = - v;// ThreadLocalRandom.current().nextInt(10, 100 + 1);

            double[] postionNW = {NWpx, NWpy};
            double[] postionNE = {NEpx, NEpy};
            double[] postionSW = {SWpx, SWpy};
            double[] postionSE = {SEpx, SEpy};

            double[] velocityNW = {NWvx, NWvy};
            double[] velocityNE = {NEvx, NEvy};
            double[] velocitySW = {SWvx, SWvy};
            double[] velocitySE = {SEvx, SEvy};

            Vector positionVectorNW = new Vector(postionNW);
            Vector positionVectorNE = new Vector(postionNE);
            Vector positionVectorSW = new Vector(postionSW);
            Vector positionVectorSE = new Vector(postionSE);

            Vector velocityVectorNW = new Vector(velocityNW);
            Vector velocityVectorNE = new Vector(velocityNE);
            Vector velocityVectorSW = new Vector(velocitySW);
            Vector velocityVectorSE = new Vector(velocitySE);

            /* aggregate a new body to bodies */
            bodies[i++] = new Body(positionVectorNW, velocityVectorNW, mass);
            bodies[i++] = new Body(positionVectorNE, velocityVectorNE, mass);
            bodies[i++] = new Body(positionVectorSW, velocityVectorSW, mass);
            bodies[i] = new Body(positionVectorSE, velocityVectorSE, mass);
        }
        double mass = 20000;


        double px = mid;


        double py = mid;


        double vx = 0;


        double vy = 0;


        double[] postion = {px, py};


        double[] velocity = {vx, vy};


        Vector positionVector = new Vector(postion);


        Vector velocityVector = new Vector(velocity);

        //bodies[bodies.length-1] = new Body(positionVector, velocityVector, mass);
        bodies[1].setMass(50);
        //bodies[1].setVelocity(velocityVector);


    }

    /**
     * Getter that returns the bodies we created
     *
     * @return
     */
    public Body[] getBodies() {
        return bodies;
    }








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
            /* aggregate a new body to bodies */
            bodies[i] = new Body(positionVector, velocityVector, mass);
        }
    }
}
