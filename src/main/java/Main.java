public class Main {
    private static final int CARS_MAX = 10;

    public static void main(String[] args) {
        final Dealer dealer = new Dealer();

        for (int i = 0; i < CARS_MAX; i++) {
            new Thread(null, dealer::sellCar, "Покупатель " + (i + 1)).start();
        }
        new Thread(null, dealer::receiveCar, "Toyota").start();
    }
}
