import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

//refactor starts here. ## indicates coursework comment

public class VRUI {
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
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: ui.listCustomers(vrManager.getCustomers()) ; break ;
				case 2: ui.listVideos(vrManager.getVideos()) ; break ;
				case 3: ui.register("customer") ; break ;
				case 4: ui.register("video") ; break ;
				case 5: ui.rentVideo() ; break ;
				case 6: ui.returnVideo() ; break ;
				case 7: ui.getCustomerReport() ; break;
				case 8: ui.clearRentals() ; break ;
				case -1: vrManager.init() ; break ;
				default: break ;
			}
		}
		System.out.println("Bye");
	}

	public void clearRentals() {
		print("Enter customer name: ") ;
		String customerName = scanner.next() ;
		// ## Duplication
		Customer foundCustomer = vrManager.findCustomer(customerName) ;

		if ( foundCustomer == null ) {
			print("No customer found") ;
		} else {
			// ## CQRS
			// ## query
			print("Name: " + foundCustomer.getName() +
					"\tRentals: " + foundCustomer.getRentals().size()) ;
			for ( Rental rental: foundCustomer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
			}
			// ## command
			List<Rental> rentals = new ArrayList<Rental>() ;
			foundCustomer.setRentals(rentals);
		}
	}

	public void returnVideo() {
		print("Enter customer name: ") ;
		String customerName = scanner.next() ;

		Customer foundCustomer = vrManager.findCustomer(customerName) ;
		if ( foundCustomer == null ) return ;

		print("Enter video title to return: ") ;
		String videoTitle = scanner.next() ;

		vrManager.returnVideo(foundCustomer, videoTitle);
	}

	public void listVideos(List<Video> videos ) {
		print("List of videos");

		for ( Video video: videos ) {
			print("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
		}
		print("End of list");
	}

	public void listCustomers(List<Customer> customers) {
		print("List of customers");
		for ( Customer customer: customers ) {
			print("Name: " + customer.getName() +
					"\tRentals: " + customer.getRentals().size()) ;
			for ( Rental rental: customer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
			}
		}
		print("End of list");
	}

	// ## delegate
	public void getCustomerReport() {
		print("Enter customer name: ") ;
		String customerName = scanner.next() ;
		print(vrManager.getReport(customerName)) ;
	}

	private void print(String message) {
		System.out.println(message);
	}
	

	// ## wrong method
	public void rentVideo() {
		print("Enter customer name: ") ;
		String customerName = scanner.next() ;

		Customer foundCustomer = vrManager.findCustomer(customerName);
		if ( foundCustomer == null ) return ;

		print("Enter video title to rent: ") ;
		String videoTitle = scanner.next() ;
		Video foundVideo = vrManager.findVideo(videoTitle);
		if ( foundVideo == null ) return ;

		vrManager.setRentals(foundVideo, foundCustomer);
	}


	// ## SRP 위반 registerCustomer, registerVideo
	public void register(String object) {
		if ( object.equals("customer") ) {
			print("Enter customer name: ") ;
			String name = scanner.next();
			vrManager.registerCustomer(name);
		}
		else {
			print("Enter video title to register: ") ;
			String title = scanner.next() ;

			print("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
			int videoType = scanner.nextInt();

			print("Enter price code( 1 for Regular, 2 for New Release ):") ;
			int priceCode = scanner.nextInt();

			vrManager.registerVideo(title, videoType, priceCode);
		}
	}

	// ## SRP? 일수도 있다 priority 낮음
	public int showCommand() {
		print("\nSelect a command !");
		print("\t 0. Quit");
		print("\t 1. List customers");
		print("\t 2. List videos");
		print("\t 3. Register customer");
		print("\t 4. Register video");
		print("\t 5. Rent video");
		print("\t 6. Return video");
		print("\t 7. Show customer report");
		print("\t 8. Show customer and clear rentals");

		int command = scanner.nextInt() ;

		return command ;

	}
}
