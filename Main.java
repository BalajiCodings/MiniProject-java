import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Test DB connection
        DatabaseUtil.testConnection();
        boolean loop = true;

        while(loop) {
            System.out.println("\nChoose any one \n 1.Book ticket \n 2.Cancel ticket" +
             "\n 3.Display Confirmed list \n 4.Display RAC list" +
              "\n 5.Display waiting list \n 6.Exit");
            
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();

            switch(n) {
                case 1:
                    System.out.println("Enter name : ");
                    String name = sc.next();
                    System.out.println("Enter age : ");
                    int age = sc.nextInt();
                    System.out.println("Enter berth : ");
                    char preference = sc.next().charAt(0);
                    if(preference == 'U' || preference == 'M' || preference == 'L') {
                        TicketBooking.bookTicket(new Passenger(name, age, preference));
                    }else {
                        System.out.println("Invalid berth");
                    }
                    break;

                case 2:
                    System.out.println("Enter your Ticket id : ");
                    int id = sc.nextInt();
                    System.out.println(TicketCancelling.cancelling(id));
                    break;

                case 3:
                    TicketBooking.displayConfirmed();
                    break;

                case 4:
                    TicketBooking.displayRAC();
                    break;

                case 5:
                    TicketCancelling.displayWaiting();
                    break;
                
                case 6:
                    System.out.println("\tThank you!");
                    sc.close();
                    loop = false;
                    break;
            }
        }
    }
}