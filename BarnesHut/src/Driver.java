public class Driver {
    public static void main(String[] args) {
        int gnumBodies = 8;
        int numSteps = 12000;//12000;
        double far = 0.5;
        int numWorkers = 4;
        double dt = 2;
        double sizeOfTheUniverse = 1e11;
        int program = 2;


        Creation creation = new Creation(gnumBodies, numSteps, far, numWorkers, dt, sizeOfTheUniverse, program);

    }


}
