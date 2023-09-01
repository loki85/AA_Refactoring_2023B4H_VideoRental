import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRManager {
    private List<Customer> customers = new ArrayList<Customer>() ;

    private List<Video> videos = new ArrayList<Video>() ;

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void init() {
        Customer james = new Customer("James") ;
        Customer brown = new Customer("Brown") ;
        customers.add(james) ;
        customers.add(brown) ;

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date()) ;
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
        videos.add(v1) ;
        videos.add(v2) ;

        Rental r1 = new Rental(v1) ;
        Rental r2 = new Rental(v2) ;

        james.addRental(r1) ;
        james.addRental(r2) ;
    }


    void registerCustomer(String name) {
        Customer customer = new Customer(name) ;
        customers.add(customer) ;
    }

    void registerVideo(String title, int videoType, int priceCode) {
        Date registeredDate = new Date();
        Video video = new Video(title, videoType, priceCode, registeredDate) ;
        videos.add(video) ;
    }

    Customer findCustomer(String customerName) {
        Customer foundCustomer = null ;
        for ( Customer customer:customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }
        return foundCustomer;
    }

     Video findVideo(String videoTitle) {
        Video foundVideo = null ;
        for ( Video video: videos ) {
            if ( video.getTitle().equals(videoTitle) && video.isRented() == false ) {
                foundVideo = video ;
                break ;
            }
        }
        return foundVideo;
    }

    void setRentals(Video foundVideo, Customer foundCustomer) {
        Rental rental = new Rental(foundVideo) ;

        foundVideo.setRented(true);

        // ## encapsulate collenction (priority:low)
        List<Rental> customerRentals = foundCustomer.getRentals() ;
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }

    void returnVideo(Customer foundCustomer, String videoTitle) {
        List<Rental> customerRentals = foundCustomer.getRentals() ;
        for ( Rental rental: customerRentals ) {
            // ## message chain? > introduce delegation, explaining variable
            if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break ;
            }
        }
    }

    String getReport(String customerName) {
        String result;
        Customer foundCustomer = findCustomer(customerName) ;
        if ( foundCustomer == null ) {
            result = "No customer found";
        } else {
            result = foundCustomer.getReport() ;
        }
        return result;
    }

    String clearRentals(String customerName) {
        StringBuilder result = new StringBuilder();

        Customer foundCustomer = findCustomer(customerName) ;
        if ( foundCustomer == null ) {
            result.append("No customer found" + "\n");
        }

        // ## CQRS
        // ## query
        result.append("Name: " + foundCustomer.getName() +
                "\tRentals: " + foundCustomer.getRentals().size() + "\n");
        for ( Rental rental: foundCustomer.getRentals() ) {
            result.append("\tTitle: ").append(rental.getVideo().getTitle()).append(" ")
                    .append("\tPrice Code: ").append(rental.getVideo().getPriceCode());
        }

        // ## command
        List<Rental> rentals = new ArrayList<Rental>() ;
        foundCustomer.setRentals(rentals);
        return result.toString();
    }
}
