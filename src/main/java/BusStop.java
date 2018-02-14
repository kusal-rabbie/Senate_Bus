import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public final class BusStop {

    private static AtomicInteger riders            = new AtomicInteger(0);
    private static Semaphore riders_mutex          = new Semaphore(1);
    private static Semaphore bus_arrived_signal    = new Semaphore(0);
    private static Semaphore rider_aboard_signal   = new Semaphore(0);

    private static boolean busArrived = false;

    public static boolean isBusArrived() {
        return BusStop.busArrived;
    }

    public static AtomicInteger getRiders() {
        return riders;
    }

    public static Semaphore getRiders_mutex() {
        return riders_mutex;
    }

    public static Semaphore getBus_arrived_signal() {
        return bus_arrived_signal;
    }

    public static Semaphore getRider_aboard_signal() {
        return rider_aboard_signal;
    }

    public static void setBusArrived(boolean busArrived) {
        BusStop.busArrived = busArrived;
    }

}
