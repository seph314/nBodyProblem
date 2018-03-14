
import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Creation {

    private Body[] bodies;
    private static double SOLARMASS = 1.98892e30;
    private static double GRAVITATION = 6.67e-11;


    /**
     * Creates bodies evenly distributed over the universe.
     *
     * @param numberOfBodies is the number of random bodies
     */
    Creation(int numberOfBodies, double sizeOfTheUniverse, int numSteps) {
        bodies = new Body[numberOfBodies]; //set the body array tp the right size
        int m = 100;
        double p = 0;
        int v = 0;
        double mid = sizeOfTheUniverse/2;
        double step = mid/numberOfBodies/12000;

        for (int i = 0; i < numberOfBodies; i++) {
            p += step;
            v += 1;
            int part =0;

            double mass = m;

            double NWpx = mid - p;
            double NEpx = mid + p;
            double SWpx = mid - p;
            double SEpx = mid + p;

            double NWpy = mid + p;
            double NEpy = mid + p;
            double SWpy = mid - p;
            double SEpy = mid - p;

            double NWvx = -v;
            double NEvx = v;
            double SWvx = -v;
            double SEvx = v;

            double NWvy = v;
            double NEvy = v;
            double SWvy = -v;
            double SEvy = - v;

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

            bodies[i++] = new Body(positionVectorNW, velocityVectorNW, mass);
            bodies[i++] = new Body(positionVectorNE, velocityVectorNE, mass);
            bodies[i++] = new Body(positionVectorSW, velocityVectorSW, mass);
            bodies[i] = new Body(positionVectorSE, velocityVectorSE, mass);
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
