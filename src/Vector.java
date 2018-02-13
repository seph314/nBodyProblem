public class Vector {
    int length;
    private double[] coordinates;

    /**
     * Vector Constructor
     * @param input is input coordinates
     */
    public Vector(double[] input) {
        coordinates = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            coordinates[i] = input[i];
        }
    }

    /**
     * Calculates the Scalar Product
     * @param s
     * @return
     */
    public Vector scalarProduct(double s) {
        double[] coords = new double[length];
        for (int i = 0; i < length; i++) {
            coords[i] = s * coordinates[i];
        }
        return new Vector(coords);
    }

    /**
     * Calculates the addition of two vectors
     * @param argumentsVector
     * @return
     */
    public Vector addition(Vector argumentsVector){
        double[] resultCoordinates = new double[length];
        for (int i = 0; i < length; i++) {
            resultCoordinates[i] = coordinates[i] + argumentsVector.coordinates[i];
        }
        return new Vector(resultCoordinates);
    }
}
