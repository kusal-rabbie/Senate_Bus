import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {

    public static void main(String[] args) {

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.execute(new Scheduler.BusScheduler());
        scheduler.execute(new Scheduler.RiderScheduler());

    }


}
