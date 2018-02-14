public class Rider implements Runnable{

    private long delay;
    private long id;

    public Rider(long delay){
        this.delay = delay;
    }

    public void arriveAndWait() {
        try {

            //checks whether the bus has arrived and waits without getting in to the queue
            if (BusStop.isBusArrived()) {
                System.out.println("Rider " + id + " missed the chance to get into the bus");
                while(BusStop.isBusArrived()){
                    //waits until the bus leaves to get in to the queue
                }
            }

            //rider is trying to access the bus stop
            BusStop.getRiders_mutex().acquire();
            //rider added to the simulation
            BusStop.getRiders().incrementAndGet();
            System.out.println("Rider " + id + " is waiting in the queue to board the bus");

        } catch (InterruptedException e) {
            System.out.println("Rider " + id + " interrupted by a friend");
        }finally{
            //avoids deadlock incase of a crash
            BusStop.getRiders_mutex().release();
        }
    }

    public void boardBus() {
        //rider gets in to the bus
        try {
            BusStop.getBus_arrived_signal().acquire();
            System.out.println("Rider "+ id +" boarded the bus");
        }catch (InterruptedException e) {
            System.out.println("Rider " + id + " interrupted by a friend");
        }finally{
            //avoids deadlock incase of a crash
            BusStop.getRider_aboard_signal().release();
        }
    }

    @Override
    public void run() {
        try {
            this.id = Thread.currentThread().getId();
            Thread.sleep(delay);
            arriveAndWait();
            boardBus();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
