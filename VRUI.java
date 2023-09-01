import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//refactor starts here. ## indicates coursework comment

public class VRUI {
	public enum Command {
		QUIT(0),
		LIST_CUSTOMERS(1),
		LIST_VIDEOS(2),
		REGISTER_CUSTOMER(3),
		REGISTER_VIDEO(4),
		RENT_VIDEO(5),
		RETURN_VIDEO(6),
		GET_CUSTOMER_REPORT(7),
		CLEAR_RENTALS(8),
		INIT(-1),
		DEFAULT(-999); // 예외 처리를 위한 기본 값

		private final int value;

		Command(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Command fromValue(int value) {
			for (Command command : Command.values()) {
				if (command.value == value) {
					return command;
				}
			}
			return DEFAULT; // 지정된 값에 매칭되는 Enum 상수가 없는 경우 기본값 반환
		}
	}
	private static Scanner scanner = new Scanner(System.in) ;

	// ## domain-presentation 섞여 있음 (분리하거나 MVC하거나...)
	// ## UI Presentation 먼저 하기
	// ## Large Class (SRP) <-- 

	public static VRManager vrManager;

	public static void main(String[] args) {
		VRUI ui = new VRUI() ;
		vrManager = new VRManager();

		boolean quit = false ;
		// ## command enum
		// ## command pattern은 too much
		while ( ! quit ) {
			Command command = Command.fromValue(ui.showCommand()) ;
			switch ( command ) {
				case QUIT: quit = true ; break ;
				case LIST_CUSTOMERS: ui.listCustomers(vrManager.getCustomers()) ; break ;
				case LIST_VIDEOS: ui.listVideos(vrManager.getVideos()) ; break ;
				case REGISTER_CUSTOMER: ui.registerCustomer() ; break ;
				case REGISTER_VIDEO: ui.registerVideo(); ; break ;
				case RENT_VIDEO: ui.rentVideo() ; break ;
				case RETURN_VIDEO: ui.returnVideo() ; break ;
				case GET_CUSTOMER_REPORT: ui.getCustomerReport() ; break;
				case CLEAR_RENTALS: ui.clearRentals() ; break ;
				case INIT: vrManager.init() ; break ;
				default: break ;
			}
		}
		System.out.println("Bye");
	}

	public void clearRentals() {
		println("Enter customer name: ") ;
		String customerName = scanner.next() ;

		Customer foundCustomer = vrManager.findCustomer(customerName) ;
		if ( foundCustomer == null ) {
			println("No customer found") ;
		} else {
			print(vrManager.clearRentals(foundCustomer));
		}
	}


	private void print(String message) {
		System.out.print(message);
	}
	public void returnVideo() {
		println("Enter customer name: ") ;
		String customerName = scanner.next() ;

		Customer foundCustomer = vrManager.findCustomer(customerName) ;
		if ( foundCustomer == null ) return ;

		println("Enter video title to return: ") ;
		String videoTitle = scanner.next() ;

		vrManager.returnVideo(foundCustomer, videoTitle);
	}

	public void listVideos(List<Video> videos ) {
		println("List of videos");

		for ( Video video: videos ) {
			println("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
		}
		println("End of list");
	}

	public void listCustomers(List<Customer> customers) {
		println("List of customers");
		for ( Customer customer: customers ) {
			println("Name: " + customer.getName() +
					"\tRentals: " + customer.getRentals().size()) ;
			for ( Rental rental: customer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
			}
		}
		println("End of list");
	}

	// ## delegate
	public void getCustomerReport() {
		println("Enter customer name: ") ;
		String customerName = scanner.next() ;
		println(vrManager.getReport(customerName)) ;
	}

	private void println(String message) {
		System.out.println(message);
	}
	

	// ## wrong method
	public void rentVideo() {
		println("Enter customer name: ") ;
		String customerName = scanner.next() ;

		Customer foundCustomer = vrManager.findCustomer(customerName);
		if ( foundCustomer == null ) return ;

		println("Enter video title to rent: ") ;
		String videoTitle = scanner.next() ;
		Video foundVideo = vrManager.findVideo(videoTitle);
		if ( foundVideo == null ) return ;

		vrManager.setRentals(foundVideo, foundCustomer);
	}
	public void registerCustomer() {
		println("Enter customer name: ") ;
		String name = scanner.next();
		vrManager.registerCustomer(name);
	}

	public void registerVideo() {
		println("Enter video title to register: ") ;
		String title = scanner.next() ;

		println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
		int videoType = scanner.nextInt();

		println("Enter price code( 1 for Regular, 2 for New Release ):") ;
		int priceCode = scanner.nextInt();

		vrManager.registerVideo(title, videoType, priceCode);
	}

	// ## SRP? 일수도 있다 priority 낮음
	private int showCommand() {
		println("\nSelect a command !");
		println("\t 0. Quit");
		println("\t 1. List customers");
		println("\t 2. List videos");
		println("\t 3. Register customer");
		println("\t 4. Register video");
		println("\t 5. Rent video");
		println("\t 6. Return video");
		println("\t 7. Show customer report");
		println("\t 8. Show customer and clear rentals");

        return scanner.nextInt();
	}
}
