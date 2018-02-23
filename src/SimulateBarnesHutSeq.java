public class SimulateBarnesHutSeq {

    public void buildQuadTree(Body[] bodies){

        double sizeOfTheUniverse = 500;
        double[] startCoordinates = {0,0};
        Vector startVector = new Vector(startCoordinates);

        /* create new Quad */
        /* TODO what size shoule "sizeOfTheUniverse" be? */
        Quad quad = new Quad(startVector, sizeOfTheUniverse);

        /* empty QuadTree */
        QuadTree quadTree = new QuadTree(quad);

        /* build tree */
        for(Body body : bodies){
            quadTree.build(body);
        }
    }
}
