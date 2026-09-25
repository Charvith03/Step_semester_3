class FeeAccount {

    String regNo;
    double totalFee;

    FeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
    }

    void pay(double amount) {
        System.out.println("Paid in one go (day-scholar account)");
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }

    void payInTwoInstallments(double amount) {
        System.out.println("Paid in two installments (hostel account)");
    }
}

public class M5 {

    static int hostelCount = 0;
    static int dayScholarCount = 0;

    static void processPayment(FeeAccount account, double amount) {

        if (account instanceof HostelFeeAccount) {

            HostelFeeAccount hostel =
                    (HostelFeeAccount) account;

            hostel.payInTwoInstallments(amount);
            hostelCount++;

        } else {

            account.pay(amount);
            dayScholarCount++;
        }
    }

    public static void main(String[] args) {

        FeeAccount[] accounts = {
                new HostelFeeAccount("H001", 200000),
                new HostelFeeAccount("H002", 180000),
                new FeeAccount("F001", 150000),
                new FeeAccount("F002", 160000)
        };

        for (int i = 0; i < accounts.length; i++) {
            processPayment(accounts[i], 60000);
        }

        System.out.println(
                "Hostel accounts processed: " + hostelCount +
                        " | Day-scholar accounts processed: " + dayScholarCount
        );
    }
}