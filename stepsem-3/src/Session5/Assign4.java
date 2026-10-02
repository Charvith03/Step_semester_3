class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;

    // Public no-argument constructor
    public LibraryMember() {
    }

    // Membership ID getter
    public String getMembershipId() {
        return membershipId;
    }

    // Membership ID setter
    // Works only on the first call
    public void setMembershipId(String id) {

        if (membershipId == null) {
            membershipId = id;
        }
    }

    // Name getter
    public String getName() {
        return name;
    }

    // Name setter
    public void setName(String name) {
        this.name = name;
    }

    // Boolean getter
    public boolean isPremiumMember() {
        return premiumMember;
    }

    // Boolean setter
    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Write-only security answer
    public void setSecurityAnswer(String answer) {

        if (answer != null) {
            securityAnswer = oneWayTransform(answer);
        }
    }

    // Simple deterministic one-way transformation
    private String oneWayTransform(String value) {

        return Integer.toHexString(
                value.hashCode()
        );
    }
}

public class Main {

    public static void main(String[] args) {

        LibraryMember m = new LibraryMember();

        m.setMembershipId("LIB-8841");
        m.setName("Priya Nair");
        m.setPremiumMember(true);

        System.out.println(
                m.getMembershipId()
        );

        // Second attempt is ignored
        m.setMembershipId("FAKE-0000");

        System.out.println(
                m.getMembershipId()
        );

        System.out.println(
                m.getName()
        );

        System.out.println(
                m.isPremiumMember()
        );

        // Write-only
        m.setSecurityAnswer("BlueMountain");
    }
}