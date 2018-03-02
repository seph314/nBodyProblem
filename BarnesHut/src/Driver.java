public class Driver {
    public static void main(String[] args) {
        int gnumBodies = 4;
        int numSteps = 1;//12000;
        double far = 0.5;
        int numWorkers = 4;
        double dt = 1e11;
        double sizeOfTheUniverse = 1e18;

        Creation creation = new Creation(gnumBodies, numSteps, far, numWorkers, dt, sizeOfTheUniverse);

    }


}
