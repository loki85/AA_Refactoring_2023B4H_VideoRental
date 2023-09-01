import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;

    private List<Rental> rentals = new ArrayList<Rental>();

    public Customer(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);

    }

    // Fix SRP, envy
    public String getReport() {
        StringBuilder result = new StringBuilder("Customer Report for " + getName() + "\n");

        double totalCharge = 0;
        int totalPoint = 0;

        for (Rental each : getRentals()) {
            double eachCharge = each.calculateCharge();
            int eachPoint = each.calculatePoint();

            result.append(formatRentalInfo(each, eachCharge, eachPoint));

            totalCharge += eachCharge;
            totalPoint += eachPoint;
        }

        result.append("Total charge: ").append(totalCharge).append("\tTotal Point:").append(totalPoint).append("\n");

        displayCoupons(totalPoint);

        return result.toString();
    }

    private String formatRentalInfo(Rental each, double eachCharge, int eachPoint) {
        return "\t" + each.getVideo().getTitle() + "\tDays rented: " + each.getDaysRented() + "\tCharge: " + eachCharge
                + "\tPoint: " + eachPoint + "\n";
    }

    private void displayCoupons(int totalPoint) {
        if (totalPoint >= 10) {
            System.out.println("Congrats! You earned one free coupon");
        }
        if (totalPoint >= 30) {
            System.out.println("Congrats! You earned two free coupons");
        }
    }
}
