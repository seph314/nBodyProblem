public class Driver {
    public static void main(String[] args) {
        int gnumBodies = 120;
        int numSteps = 10;//12000;
        double far = 0.5;
        int numWorkers = 4;
        double dt = 2;
        double sizeOfTheUniverse = 200000;
        int program = 2;


        Creation creation = new Creation(gnumBodies, numSteps, far, numWorkers, dt, sizeOfTheUniverse, program);

    }


}
