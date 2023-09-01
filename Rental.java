import javax.management.InvalidAttributeValueException;
import java.security.InvalidParameterException;
import java.util.Date;

public class Rental {
	//Remove Magic number
	public static final int MAX_RENTAL_DAYS = 2;
	private Video video ;
	//ENUM
	private RentalStatus status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		this.status = RentalStatus.RENTED;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public RentalStatus getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == RentalStatus.RENTED ) {
			this.status = RentalStatus.RETURNED;
			returnDate = new Date() ;
		}
	}
	public Date getRentDate() {
		return rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	// ## Feature envy / SRP(date/video)
	public int getDaysRentedLimit() {
		if ( getDaysRented() <= MAX_RENTAL_DAYS)
			return 0;
		else
			return getVideoRentLimit(this.video);
	}

	//todo move this function to video
	private int getVideoRentLimit(Video v) {
		// ## todo: type code 함께 정리
		int limit=0;
		switch ( video.getVideoType() ) {
			case Video.VHS: limit = 5 ; break ;
			case Video.CD: limit = 3 ; break ;
			case Video.DVD: limit = 2 ; break ;
			default:
				throw new InvalidParameterException();
		}
		return limit;
	}

	//Duplicated code
	public int getDaysRented() {
		int daysRented;
		long diff;
		if (getStatus() == RentalStatus.RETURNED) { // returned Video
			diff = returnDate.getTime() - rentDate.getTime();
		} else { // not yet returned
			diff = new Date().getTime() - rentDate.getTime();
		}
		daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		return daysRented;
	}
}
