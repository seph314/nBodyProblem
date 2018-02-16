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




//        /* prints them again after movement */
//        System.out.println("\n\nA F T E R   M O V E M E N T");
//        for (int i=0; i<simulatedBodies.length; i++){
//            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(simulatedBodies[i].getPosition()));
//            System.out.println("velocity: " + Arrays.toString(simulatedBodies[i].getVelocity()));
//
//        }


//        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
//        /* T H I S   I S   O N L Y    T E S T S   T O   S E E   I F   I T   S E E M S   T O   W O  R K */
//        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
//
////        /* prints the bodies positions */
////        for (int i=0; i<bodies.length; i++){
////            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
////            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
////
////        }
////
////        /* calculate forces between a lot of bodies and move bodies */
////        for (int i=0; i<bodies.length-1; i++){
////            Vector force = bodies[i].calculateForces(bodies[i+1]); /* calculate the force between two bodies */
////            bodies[i].movePoints(force, dt); /* movie body */
////            bodies[i+1].movePoints(force, dt); /* movie body */
////        }
////
////        /* prints them again after movement */
////        System.out.println("\n\nA F T E R   M O V E M E N T");
////        for (int i=0; i<bodies.length; i++){
////            System.out.println("\nBody " + i + "\nposition: " + Arrays.toString(bodies[i].getPosition()));
////            System.out.println("velocity: " + Arrays.toString(bodies[i].getVelocity()));
////
////        }
    }
}
