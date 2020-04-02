public class Philosopher implements Runnable, DiningServer{

    private Object left;
    private Object right;
    public int eats = 0;
    public int num;


    public Philosopher(Object l, Object r, int num) {
        this.left = l;
        this.right = r;
        this.num = num;
    }

    private void doAction(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep(((int) (Math.random() * 100)));
    }

    @Override
    public void run() {
        try {
            while (true) {
                doAction("thinking");
                takeForks(num);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }

    @Override
    public void takeForks(int num) throws InterruptedException {
        synchronized (left) {
            doAction("picked up left fork");
            synchronized (right) {
                doAction("picked up right fork - eating");
                this.eats++;
                returnForks(num);
            }
        }
    }

    @Override
    public void returnForks(int num) throws InterruptedException {
        doAction("put down right fork");
        doAction("put down left fork - thinking");
    }
}
