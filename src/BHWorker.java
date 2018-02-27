public class BHWorker extends Thread{
    QuadTree qT;
    Quad quad;
    InitiateBarnesHutParallel iBHP;
    Body[] bodies;
    double dt;
    int part;
    int workers;

    public BHWorker(int workers, int part, double dt, Body[] bodies, QuadTree qT, Quad quad){
        this.qT = qT;
        this.quad = quad;
        this.bodies = bodies;
        this.dt = dt;
        this.part = part;
        this.workers = workers;
    }

    public void run(){
        long t1, t2, t3;
        t1 = System.nanoTime();
        for(int i = part; i < (part+(bodies.length/(double)workers)); i++){
            bodies[i].resetForce();
            if (bodies[i].inQuad(quad)) {
                qT.calculateForce(bodies[i]);
                bodies[i].update(dt);
            }
        }
        t2 = System.nanoTime();
        t3 = t2 - t1;
        System.out.println(currentThread() +":"+ t3/1000000);

    }
}
