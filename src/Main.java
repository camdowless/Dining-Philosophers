
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main{
    static Philosopher[] philosophers = new Philosopher[5];
    static Condition[] forks = new Condition[5];
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {

        for(int i = 0; i < 5; i++){
            forks[i] = lock.newCondition();
        }

        for (int i = 0; i < 5; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % 5];
            if (i == 4) {
                philosophers[i] = new Philosopher(rightFork, leftFork, i);
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork, i);
            }
            Thread t = new Thread(philosophers[i], "Philosopher " + (i));
            t.start();
        }

        while(true){
            if(checkEats()){
                for(Philosopher p : philosophers){
                    System.out.printf("Philosopher %d ate %d times\n", p.num, p.eats);
                }
                System.exit(694958);
            }
        }
    }

    static Boolean checkEats(){
        int eats = 0;
        for(Philosopher p : philosophers){
            eats += p.eats;
        }
        if(eats == 500){
            return true;
        }
        return false;
    }
}
