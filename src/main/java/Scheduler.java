import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Scheduler implements Runnable {

    long delay;

    Generator busDelayGenerator = new Generator(1200);
    Generator riderDelayGenerator = new Generator(30);

    static class RiderScheduler extends Scheduler{

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(100);

        @Override
        public void run() {
            while(true){
                try {
                    delay = riderDelayGenerator.generateDelay();
                    scheduler.execute(new Rider(delay*1000));
                    //sleeps before spawning another rider thread
                    Thread.sleep(delay*1000);
                } catch (InterruptedException e) {
                    System.out.println("Schedular crashed");
                }
            }
        }
    }

    static class BusScheduler extends Scheduler{

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        @Override
        public void run() {
            while(true){
                try {
                    delay = busDelayGenerator.generateDelay();
                    System.out.println("**************** Next Bus is arriving in " + delay/60 + " minutes and " + delay%60 + " seconds ****************");
                    scheduler.execute(new Bus(delay*1000));
                    //sleeps before spawning another rider thread
                    Thread.sleep(delay*1000);
                } catch (InterruptedException e) {
                    System.out.println("Schedular crashed");
                }
            }
        }
    }

    //helper class for sampling of exponential distribution
    static class Generator {

        private int mean;

        public Generator(int mean){
            this.mean = mean;
        }

//        public long generateDelay(){
//            return Math.round(-mean * Math.log(Math.random()) / Math.log(2));
//        }

        public long generateDelay(){
            Random rand = new Random();
            return Math.round(-mean * Math.log(1 - rand.nextFloat()));
        }
    }


}
