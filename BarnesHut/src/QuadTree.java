public class QuadTree {
    private static final double THETA = 0.5;

    public Body body;
    public Quad quad;
    public QuadTree NW;
    public QuadTree NE;
    public QuadTree SW;
    public QuadTree SE;

    public QuadTree(Quad quad) {
        this.quad = quad;
        body = null;
        NW = null;
        NE = null;
        SW = null;
        SE = null;
    }


    public void insert(Body b) {
        /* If the node is empty. Set its body to the new body. */
        if (body == null) {
            body = b;
            return;
        }

        /*
         * If the node is internal we have to add the body to the combined
         * "center-of-mass" and place the body in one of the sub quadrants.
         */
        if (!isExternal()) {
            body = body.add(b);
            putBody(b);
        }
        /*
         * If the node is external we create the sub nodes representing each sub
         * quadrant and add both the new body as well as the body that was
         * previously in this node to whichever quadrants they are located in.
         * Lastly we replace the previous body with a new "center-of-mass" body
         * by adding together the new body and the previous one.
         */
        else {
            NW = new QuadTree(quad.NW());
            NE = new QuadTree(quad.NE());
            SE = new QuadTree(quad.SE());
            SW = new QuadTree(quad.SW());

            putBody(body);
            putBody(b);
            body = body.add(b);
        }

    }
    public QuadTree getMyQuadTree(String quadName){
        if (quadName.equals("NW")){
            NW = new QuadTree(quad.NW());
            return this.NW;}
        if (quadName.equals("NE")){
            NE = new QuadTree(quad.NE());
            return this.NE;}
        if (quadName.equals("SW")){
            SW = new QuadTree(quad.SW());
            return this.SW;}
        if (quadName.equals("SE")){
            SE = new QuadTree(quad.SE()); 
            return this.SE;}
        else    {
            System.out.println("Hej");  
            return null;
        }
    }

    private void putBody(Body b) {
        if (quad.NW().contains(b))
            NW.insert(b);
        else if (quad.NE().contains(b))
            NE.insert(b);
        else if (quad.SE().contains(b))
            SE.insert(b);
        else
            SW.insert(b);
    }

    private boolean isExternal() {
        return (NW == null && NE == null && SW == null && SE == null);
    }

    public void updateForce(Body b) {
        
        if (body == null || b.equals(body))
            return;

        // if the current node is external, update net force acting on b
        if (isExternal())
            b.addForce(body);

            // for internal nodes
        else {

            // width of region represented by internal node
            double s = quad.getSideLength();

            // distance between Body b and this node's center-of-mass
            double d = body.distanceTo(b);

            // compare ratio (s / d) to threshold value Theta
            if ((s / d) < THETA)
                b.addForce(body); // b is far away

                // recurse on each of current node's children
            else {
                NW.updateForce(b);
                NE.updateForce(b);
                SW.updateForce(b);
                SE.updateForce(b);
            }
        }
    }

    public void reset(){
        this.body = null;
    }
    public void resetQuads(){
        body = null;
        NW = null;
        NE = null;
        SW = null;
        SE = null;
    }


}
