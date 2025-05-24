import java.util.*;

public class TicketCancelling extends TicketBooking {

    private static char preferenceTracker = '\0';
    private static int canceledSeatNumber = 0;

    private static Map<Integer, Character> seatNumberwithBerth = new HashMap<>();

    public static String cancelling(int id) {
        for(Passenger p : confirmedList) {
            if(p.getId() == id) {
                cancel(p);
                return "Success";
            }
        }
        for(Passenger p : racQueue) {
            if(p.getId() == id) {
                cancel(p);
                return "Success";
            }
        }
        for(Passenger p : waitingQueue) {
            if(p.getId() == id) {
                cancel(p);
                return "Success";
            }
        }
        return "Invalid id";
    }

    public static void cancel(Passenger p) {
        if(p.getTicketType().equals("berth")) {
            preferenceTracker = p.getPreference();
            canceledSeatNumber = p.getSeatNumber();

            seatNumberwithBerth.put(canceledSeatNumber, preferenceTracker);

            deleteFromLists(p);
            addRacToBerth(racQueue.poll());
            addWaitingToRac(waitingQueue.poll());
        }
        else if(p.getTicketType() == "rac") {
            racQueue.remove(p);
            addWaitingToRac(waitingQueue.poll());
        }
        else {
            waitingQueue.remove(p);
        }
    }

    public static void addRacToBerth(Passenger p) {
        if(p != null) {
            p.setPreference(preferenceTracker);
            p.setSeatNumber(canceledSeatNumber);
            p.setTicketType("berth");

            if(preferenceTracker == 'U') {
                upperList.add(p);
            }
            else if(preferenceTracker == 'M') {
                middleList.add(p);
            }
            else {
                lowerList.add(p);
            }

            confirmedList.add(p);
            seatNumberwithBerth.remove(canceledSeatNumber);
            preferenceTracker = '\0';
            canceledSeatNumber = 0;
        }
    }

    public static void addWaitingToRac(Passenger p) {
        if(p != null) {
            p.setTicketType("rac");
            racQueue.add(p);
        }
    }

    public static void deleteFromLists(Passenger p) {
        confirmedList.remove(p);
        upperList.remove(p);
        middleList.remove(p);
        lowerList.remove(p);
    }

    public static Map<Integer, Character> getSeatNumberWithBerth() {
        return seatNumberwithBerth;
    }


}

