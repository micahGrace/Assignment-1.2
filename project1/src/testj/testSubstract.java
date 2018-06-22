package testj;

import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class testSubstract {

    int a, b;
    Lock lock = new ReentrantLock();
    Condition cond = lock.newCondition();
    ArrayList<Integer> nums = new ArrayList<>();
    boolean nums_ready = false;
    arth2 arth_inst;

/*In parametrized test our main concern is to test the
same test by using different values
*/
    @Test
    public void testSubstract() throws InterruptedException {
        arth_inst = new arth2();
//        a = 10;
//        b = 2;
        Runnable r = new randominput();
        new Thread(r).start();
        int a, b, exp_sub;

        for(int i = 0; i< 10; i++) {
            //System.out.println(a+ " "+ b);
            lock.lock();

            //have two wait untill it finishes since
            // we can only do the job for two vars at a time.

            while (nums.size() < 2) {
                cond.await();
            }

            a = nums.remove(0);
            b = nums.remove(0);

            exp_sub = a - b;
            Assert.assertEquals(exp_sub, arth_inst.add_two_numbers(a, b));
            System.out.println("completed successfully!");
            lock.unlock();
        }
    }

    class randominput implements Runnable{
        public void run(){
            int a, b;
            for (int i = 0; i < 10; i++) {
                lock.lock();
                a = (int)(Math.random()*100000);
                b = (int)(Math.random()*100000);
                nums.add(a);
                nums.add(b);
                cond.signal();
                lock.unlock();
            }

        }
    }

}
