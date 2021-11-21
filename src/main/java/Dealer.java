import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dealer {
    private static final int SELL_TIME = 1000;
    private static final int RECEIVE_TIME = 3000;
    private static final int CARS = 10;
    private final List<Car> cars = new ArrayList<>();
    private final Lock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    public void sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
            while (cars.size() == 0) {
                System.out.println("Машин нет");
                condition.await();
            }
            Thread.sleep(SELL_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
            cars.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void receiveCar() {
        for (int i = 0; i < CARS; i++) {
            try {
                lock.lock();
                Thread.sleep(RECEIVE_TIME);
                cars.add(new Car());
                System.out.println("Производитель " + Thread.currentThread().getName() + " выпустил 1 авто");
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
