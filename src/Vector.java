
public class Vector {
    private int vectorLength;
    double[] coordinates;

    /**
     * Vector Constructor
     * @param input is input coordinates
     */
    Vector(double[] input) {
        vectorLength = input.length;
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
     * @return dotProduct
     */
    public double dotProduct(Vector argumentVector){
        double dotProduct = 0;
        for (int i = 0; i< vectorLength; i++){
            dotProduct += coordinates[i] * argumentVector.coordinates[i];
        }
        return dotProduct;
    }

    /**
     * Calculates the magnitude
     * To do this we find the hypotenuse h och the triangle x,y,h
     * @return
     */
    public double magnitude(){
        return Math.sqrt(this.dotProduct(this));
    }

    /**
     * Calculates the add of two vectors
     * @param argumentVector
     * @return
     */
    public Vector add(Vector argumentVector){
        double[] resultCoordinates = new double[vectorLength];
        for (int i = 0; i < vectorLength; i++) {
            resultCoordinates[i] = coordinates[i] + argumentVector.coordinates[i];
        }
        return new Vector(resultCoordinates);
    }

    /**
     * Calculates subtract of two vectors
     * TODO double check this implementation
     * @param argumentsVector
     * @return
     */
    public Vector subtract(Vector argumentsVector){
        double[] resultCoordinates = new double[vectorLength];
        for (int i = 0; i < vectorLength; i++) {
            resultCoordinates[i] = coordinates[i] - argumentsVector.coordinates[i];
        }
        return new Vector(resultCoordinates);
    }

    /**
     * Calculates Direction
     * @returna vector with the same direction as this vector
     */
    public Vector direction(){
        return this.scalarProduct(1/this.magnitude());
    }

    public double getX() {
        return coordinates[0];
    }
    public double getY() {
        return coordinates[1];
    }

}
