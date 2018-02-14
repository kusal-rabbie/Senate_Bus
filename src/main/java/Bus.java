public class Bus implements Runnable{

    private final int BUS_CAPACITY      = 50;
    private long delay;
    private long id;

    public Bus(long delay){
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
//            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            this.id = Thread.currentThread().getId();
            Thread.sleep(delay);
            runAndPickUp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void runAndPickUp() {
        try {
            //sets the busArrived flag so that late comers have to wait
            BusStop.setBusArrived(true);
            System.out.println("====================== Bus :" + id + " is arriving ======================");

            //chooses the number of riders to carry
            int no_boarding_riders = Math.min(BusStop.getRiders().get(),BUS_CAPACITY);
            System.out.println("Waiting riders at bus stop  : "+ BusStop.getRiders().get());
            System.out.println("Boarding riders count       : "+ no_boarding_riders);

            for(int i=0;i<no_boarding_riders;i++){
                //allows the riders to get in
                BusStop.getBus_arrived_signal().release();
                //confirmation from rider
                BusStop.getRider_aboard_signal().acquire();
            }

            //acquires the lock for rider count to avoid riders updating it
            BusStop.getRiders_mutex().acquire();
            int total_riders = BusStop.getRiders().get();
            BusStop.getRiders().set(Math.max((total_riders - no_boarding_riders), 0));
            depart();
            //resets the busArrived flag
            BusStop.setBusArrived(false);
        } catch (InterruptedException e) {
            System.out.println("Bus " + id + "could not leave due to technical difficulties!");
        }finally{
            BusStop.getRiders_mutex().release();
        }
    }

    private void depart() {
        System.out.println("=================== Bus with BusID : " + id + " departed ===================");
    }

}
