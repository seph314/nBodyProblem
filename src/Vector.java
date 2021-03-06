
public class Vector {
    private int vectorLength;
    double[] coordinates;

    /**
     * Vector Constructor
     *
     * @param input is input coordinates
     */
    Vector(double[] input) {
        vectorLength = input.length;
        coordinates = new double[input.length];
        System.arraycopy(input, 0, coordinates, 0, input.length);
    }

    /**
     * Calculates the Scalar Product
     *
     * @param s the point used for calulation
     * @return resulting scalar product cordinate.
     */
    Vector scalarProduct(double s) {
        double[] coords = new double[vectorLength];
        for (int i = 0; i < vectorLength; i++) {
            coords[i] = s * coordinates[i];
        }
        return new Vector(coords);
    }


    /**
     * Calculates the dotProduct
     * of two Vectors a = [a1,a2,..,an] and b =[b1,b2,..,bn]
     * as a1*b1 + a2*b2 + ... + an*bn
     *
     * @return dotProduct
     */
    private double dotProduct(Vector argumentVector) {
        double dotProduct = 0;
        for (int i = 0; i < vectorLength; i++) {
            dotProduct += coordinates[i] * argumentVector.coordinates[i];
        }
        return dotProduct;
    }


    /**
     * Calculates the magnitude
     * To do this we find the hypotenuse h och the triangle x,y,h
     *
     * @return magnitude of two vectors
     */
    double magnitude() {
        return Math.sqrt(this.dotProduct(this));
    }

    /**
     * Calculates the aggregate of two vectors
     *
     * @param argumentVector vector to add
     * @return resulting coordinates
     */
    Vector add(Vector argumentVector) {
        double[] resultCoordinates = new double[vectorLength];
        for (int i = 0; i < vectorLength; i++) {
            resultCoordinates[i] = coordinates[i] + argumentVector.coordinates[i];
        }
        return new Vector(resultCoordinates);
    }

    /**
     * Calculates subtract of two vectors
     *
     * @param argumentsVector vector to subtract
     * @return resulting vector
     */
    Vector subtract(Vector argumentsVector) {
        double[] resultCoordinates = new double[vectorLength];
        for (int i = 0; i < vectorLength; i++) {
            resultCoordinates[i] = coordinates[i] - argumentsVector.coordinates[i];
        }
        return new Vector(resultCoordinates);
    }

    /**
     * Calculates Direction
     *
     * @return vector with the same direction as this vector
     */
    Vector direction() {
        return this.scalarProduct(1 / this.magnitude());
    }

    double getX() {
        return coordinates[0];
    }

    double getY() {
        return coordinates[1];
    }

    void setCoordinates(double[] coordinates) {
        this.coordinates[0] = coordinates[0];
        this.coordinates[1] = coordinates[1];
    }
}
