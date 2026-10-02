abstract class MembershipPlan {

    protected double baseRate = 1000;

    public abstract double calculateFee();

    public abstract String getPlanName();
}

class MonthlyPlan extends MembershipPlan {

    @Override
    public double calculateFee() {
        return baseRate;
    }

    @Override
    public String getPlanName() {
        return "Monthly";
    }
}

class QuarterlyPlan extends MembershipPlan {

    @Override
    public double calculateFee() {
        return baseRate * 3 * 0.90;
    }

    @Override
    public String getPlanName() {
        return "Quarterly";
    }
}

class AnnualPlan extends MembershipPlan {

    @Override
    public double calculateFee() {
        return baseRate * 12 * 0.75;
    }

    @Override
    public String getPlanName() {
        return "Annual";
    }
}

class Member {

    private String name;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum MembershipStatus {
    ACTIVE,
    FROZEN,
    EXPIRED
}

class Membership {

    private Member member;
    private MembershipPlan plan;
    private MembershipStatus status;

    public Membership(
            Member member,
            MembershipPlan plan) {

        this.member = member;
        this.plan = plan;
        this.status = MembershipStatus.ACTIVE;
    }

    public void checkIn() {

        if (status == MembershipStatus.ACTIVE) {

            System.out.println(
                    member.getName() +
                            " checked in successfully."
            );

        } else {

            System.out.println(
                    "Check-in denied: " +
                            member.getName() +
                            "'s membership is " +
                            status + "."
            );
        }
    }

    public void freeze() {

        if (status == MembershipStatus.ACTIVE) {

            status = MembershipStatus.FROZEN;

            System.out.println(
                    member.getName() +
                            "'s membership frozen. Status: Frozen."
            );

        } else if (status == MembershipStatus.EXPIRED) {

            System.out.println(
                    "Cannot freeze an Expired membership."
            );

        } else {

            System.out.println(
                    "Membership is already Frozen."
            );
        }
    }

    public void unfreeze() {

        if (status == MembershipStatus.FROZEN) {

            status = MembershipStatus.ACTIVE;

            System.out.println(
                    member.getName() +
                            "'s membership unfrozen. Status: Active."
            );

        } else if (status == MembershipStatus.EXPIRED) {

            System.out.println(
                    "Cannot unfreeze an Expired membership."
            );

        } else {

            System.out.println(
                    "Membership is already Active."
            );
        }
    }

    public void expire() {

        if (status != MembershipStatus.EXPIRED) {

            status = MembershipStatus.EXPIRED;

            System.out.println(
                    member.getName() +
                            "'s membership expired. Status: Expired."
            );
        }
    }
}

class MembershipSystem {

    public Membership buyMembership(
            Member member,
            MembershipPlan plan) {

        Membership membership =
                new Membership(member, plan);

        System.out.println(
                plan.getPlanName() +
                        " membership created for " +
                        member.getName() +
                        "."
        );

        System.out.printf(
                "Fee: ₹%.2f%n",
                plan.calculateFee()
        );

        System.out.println("Status: Active.");

        return membership;
    }
}

public class Main {

    public static void main(String[] args) {

        MembershipSystem system =
                new MembershipSystem();

        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        Membership ashaMembership =
                system.buyMembership(
                        asha,
                        new QuarterlyPlan()
                );

        Membership raviMembership =
                system.buyMembership(
                        ravi,
                        new MonthlyPlan()
                );

        // Asha checks in
        ashaMembership.checkIn();

        // Asha freezes
        ashaMembership.freeze();

        // Asha tries to check in
        ashaMembership.checkIn();

        // Ravi expires
        raviMembership.expire();

        // Ravi tries to freeze
        raviMembership.freeze();
    }
}