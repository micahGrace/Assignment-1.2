package testj;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class testDivide {

    int a, b;
    arth2 arth_inst;
    Lock lock = new ReentrantLock();
    Condition cond = lock.newCondition();
    ArrayList<Integer> nums = new ArrayList<>();
    boolean nums_ready = false;
    @Test
  public void testDivide() throws InterruptedException {
      arth_inst = new arth2();
//      a = 10;
//      b = 2;
        int a,b,exp_div;
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

            exp_div = a / b;
            Assert.assertEquals(exp_div, arth_inst.add_two_numbers(a, b));
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
