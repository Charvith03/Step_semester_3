import java.util.Arrays;

class GymMember {

    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    private int[] lateFeeHistory;
    private int feeCount;

    public GymMember(String memberId, int monthlyFee) {

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
        this.sessionsAttended = 0;

        lateFeeHistory = new int[10];
        feeCount = 0;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }

    protected void chargeLateFee(int amount) {

        if (feeCount < lateFeeHistory.length) {

            lateFeeHistory[feeCount] = amount;
            feeCount++;
        }
    }

    public int[] getLateFeeHistory() {

        return Arrays.copyOf(
                lateFeeHistory,
                feeCount
        );
    }

    public int getTotalLateFees() {

        int total = 0;

        for (int i = 0; i < feeCount; i++) {
            total += lateFeeHistory[i];
        }

        return total;
    }
}

class PremiumMember extends GymMember {

    private String trainerName;

    public PremiumMember(
            String memberId,
            int monthlyFee,
            String trainerName) {

        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    protected void chargeLateFee(int amount) {

        super.chargeLateFee(amount / 2);
    }
}

public class Main {

    public static void main(String[] args) {

        PremiumMember p =
                new PremiumMember(
                        "MEM5",
                        2000,
                        "Coach Riya"
                );

        // Original fee = 200
        // Premium discount = 50%
        p.chargeLateFee(200);

        System.out.println(
                p.getTotalLateFees()
        );

        // Get the history
        int[] history = p.getLateFeeHistory();

        // Try to modify it
        history[0] = 999;

        // Original internal array should remain unchanged
        System.out.println(
                Arrays.toString(
                        p.getLateFeeHistory()
                )
        );
    }
}