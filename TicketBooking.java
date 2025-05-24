import java.util.*;

public class TicketBooking {
    private static int berthLimit = 2;
    private static int racLimit = 1;
    private static int waitingListLimit = 1;

    private static int upperSeatNumber = 1;
    private static int middleSeatNumber = 2;
    private static int lowerSeatNumber = 3;

    static ArrayList<Passenger> confirmedList = new ArrayList<>();

    static ArrayList<Passenger> upperList = new ArrayList<>();
    static ArrayList<Passenger> middleList = new ArrayList<>();
    static ArrayList<Passenger> lowerList = new ArrayList<>();

    static Queue<Passenger> racQueue = new LinkedList<>();
    static Queue<Passenger> waitingQueue = new LinkedList<>();

    public static void bookTicket(Passenger p) {
        if(upperList.size() == berthLimit && middleList.size() == berthLimit && lowerList.size() == berthLimit) {
            if(updateRacQueue(p)) {
                System.out.println("Added to RAC\nYour ticket id is " + p.getId());
                // Insert RAC passenger to DB
                DatabaseUtil.insertPassenger(p.getName(), p.getAge(), p.getPreference(), "rac", -1);
            }else if(updateWaitingQueue(p)) {
                System.out.println("Added to Waiting List\nYour ticket id is " + p.getId());
                // Insert waiting passenger to DB
                DatabaseUtil.insertPassenger(p.getName(), p.getAge(), p.getPreference(), "waitingList", -1);
            }else {
                p.setId(p.getId() - 1);
                System.out.println("Ticket not available");
            }
        }else if(checkAvailability(p)) {
            System.out.println("Booking Confirmed\nYour ticket id is " + p.getId());
            p.setTicketType("berth");
            confirmedList.add(p);
            // Insert confirmed passenger to DB
            DatabaseUtil.insertPassenger(p.getName(), p.getAge(), p.getPreference(), "berth", p.getSeatNumber());
        }else {
            System.out.println(p.getPreference() + " is not available");
            p.setId(p.getId() - 1);
            availableList();

        }
    }

    public static boolean updateWaitingQueue(Passenger p) {
        if(waitingQueue.size() < waitingListLimit) {
            p.setTicketType("waitingList");
            waitingQueue.add(p);
            return true;
        }
        return false;
    }

    public static boolean updateRacQueue(Passenger p) {
        if(racQueue.size() < racLimit) {
            p.setTicketType("rac");
            racQueue.add(p);
            return true;
        }
        return false;
    }

    public static void availableList() {
        System.out.println("Available seats:");
        System.out.println("Upper: " + (berthLimit - upperList.size()));
        System.out.println("Middle: " + (berthLimit - middleList.size()));
        System.out.println("Lower: " + (berthLimit - lowerList.size()));
    }

    public static boolean checkAvailability(Passenger p) {
        Map<Integer, Character> map = TicketCancelling.getSeatNumberWithBerth();

        if(p.getPreference() == 'U') {
            if(upperList.size() < berthLimit) {
                if(!map.isEmpty()) {
                    getSeatDetails(map, p);
                }else {
                    p.setSeatNumber(upperSeatNumber);
                    upperSeatNumber += 3;
                }

                upperList.add(p);
                return true;
            }
        }
        else if(p.getPreference() == 'M') {
            if(middleList.size() < berthLimit) {
                if(!map.isEmpty()) {
                    getSeatDetails(map, p);
                }else {
                    p.setSeatNumber(middleSeatNumber);
                    middleSeatNumber += 3;
                }

                middleList.add(p);
                return true;
            }
        } else {
            if(lowerList.size()<berthLimit)
            {
                if(!map.isEmpty())
                {
                    getSeatDetails(map, p);
                }
                else
                {
                    p.setSeatNumber(lowerSeatNumber);
                    lowerSeatNumber+=3;
                }

                lowerList.add(p);
                return true;
            }
        }
        return false;
    }

    public static void getSeatDetails(Map<Integer, Character> map, Passenger p) {
        int seatNumber = checkPreferenceAvailability(map, p.getPreference());

        p.setSeatNumber(seatNumber);

        map.remove(seatNumber);
    }

    public static int checkPreferenceAvailability(Map<Integer, Character> map, char preference) {
        int seatNumber = 0;

        for(Map.Entry<Integer, Character> entry : map.entrySet()) {
            if(preference == entry.getValue()) {
                seatNumber = entry.getKey();
                break;
            }
        }
        return seatNumber;

    }
    public static void displayConfirmed()
	{
		System.out.println("-------------------------");
		for(Passenger p : confirmedList)
		{
			System.out.println(p.toString());
			System.out.println("-------------------------");
		}
	}
	
	public static void displayRAC()
	{
		System.out.println("-------------------------");
		for(Passenger p : racQueue)
		{
			System.out.println(p.toString());
			System.out.println("-------------------------");
		}
	}
	public static void displayWaiting()
	{
		System.out.println("-------------------------");
		for(Passenger p : waitingQueue)
		{
			System.out.println(p.toString());
			System.out.println("-------------------------");
		}
	}

}