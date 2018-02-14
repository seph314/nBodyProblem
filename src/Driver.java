import java.util.Arrays;

public class Driver {

    public static void main(String[] args) {

        double[] arrayOne = {2,3};
        double[] arrayTwo = {4,5};
        Vector vectorOne = new Vector(arrayOne);
        Vector vectorTwo = new Vector(arrayTwo);

        Vector force = new Vector(arrayOne);
        double dt = 22;

        Body bodyOne = new Body(vectorOne,vectorTwo,10);
        Body bodyTwo = new Body(vectorTwo,vectorOne,200);

        bodyOne.calculateForces(bodyTwo);
        bodyOne.movePoints(force, dt);

    }

}
