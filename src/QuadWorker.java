public class QuadWorker extends Thread{
    QuadTree qT;
    Body[] bodies;
    QuadTree root;

    public QuadWorker(QuadTree qT, Body[] bodies, QuadTree root) {
        this.qT = qT;
        this.bodies = bodies;
        this.root = root;
    }

    public void run() {
        System.out.println(Thread.currentThread());
        qT.threadMagic(bodies, root);
        System.out.println(Thread.currentThread() +"joinar");
    }
}
