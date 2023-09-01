import java.util.Date;

//refactor starts here. ## indicates coursework comment

// ## Data Class. 
// ## divergent change인지는 모르겠음
public class Video {
	private String title ;

	// ## subclassing(x) enum
	private int priceCode ;
	public static final int REGULAR = 1 ;
	public static final int NEW_RELEASE =2 ;

	// ## Type code: subclassing
	private int videoType ;
	public static final int VHS = 1 ;
	public static final int CD = 2 ;
	public static final int DVD = 3 ;

	private Date registeredDate ;
	private boolean rented ;

	// ## long method는 아닐듯
	public Video(String title, int videoType, int priceCode, Date registeredDate) {
		this.setTitle(title) ;
		this.setVideoType(videoType) ;
		this.setPriceCode(priceCode) ;
		this.registeredDate = registeredDate ;
	}

	public int getLateReturnPointPenalty() {
		int pentalty = 0 ;
		// ## type code 정리되면 정리됨
		switch ( videoType ) {
			case VHS: pentalty = 1 ; break ;
			case CD: pentalty = 2 ; break ;
			case DVD: pentalty = 3 ; break ;
		}
		return pentalty ;
	}
	public int getPriceCode() {
		return priceCode;
	}

	public void setPriceCode(int priceCode) {
		this.priceCode = priceCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isRented() {
		return rented;
	}

	// ## IsRented 우선순위 하
	public void setRented(boolean rented) {
		this.rented = rented;
	}

	// ## dead code
	public Date getRegisteredDate() {
		return registeredDate;
	}

	// ## dead code
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public int getVideoType() {
		return videoType;
	}

	public void setVideoType(int videoType) {
		this.videoType = videoType;
	}
}
