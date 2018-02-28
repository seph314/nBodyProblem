import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class QuadWorker extends Thread{
    QuadTree qT;
    Body[] bodies;
    QuadTree root;
    ReentrantLock lock;

    public QuadWorker(QuadTree qT, Body[] bodies, QuadTree root, ReentrantLock lock) {
        this.qT = qT;
        this.bodies = bodies;
        this.root = root;
        this.lock = lock;
    }

    public void run() {
        System.out.println(Thread.currentThread());
        qT.threadMagic(bodies, root, lock);
        System.out.println(Thread.currentThread() +"joinar");
    }
}
