class GymMember {

    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    public GymMember(String memberId, int monthlyFee) {
        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
        this.sessionsAttended = 0;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }

    public String displayInfo() {
        return "Standard | Sessions: "
                + sessionsAttended;
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

    public String getTrainerName() {
        return trainerName;
    }

    @Override
    public String displayInfo() {
        return "Premium | Trainer: "
                + trainerName
                + " | Sessions: "
                + sessionsAttended;
    }
}

public class Main {

    static String batchPrint(GymMember[] members) {

        StringBuilder report =
                new StringBuilder();

        for (GymMember member : members) {

            // Polymorphism
            report.append(member.displayInfo());

            // Safe downcasting
            if (member instanceof PremiumMember) {

                PremiumMember premium =
                        (PremiumMember) member;

                report.append(
                        " [Trainer via downcast: "
                                + premium.getTrainerName()
                                + "]"
                );
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {

        GymMember plain =
                new GymMember("MEM6", 1000);

        PremiumMember premium =
                new PremiumMember(
                        "MEM7",
                        2000,
                        "Coach Riya"
                );

        GymMember[] members = {
                plain,
                premium
        };

        System.out.println(
                batchPrint(members)
        );

        /*
        This would cause ClassCastException:

        PremiumMember bad =
                (PremiumMember) plain;
        */
    }
}