class LibraryMember {

    private String membershipPin;
    String branchCode;              // default
    protected double finesOwed;
    public String displayName;

    public LibraryMember(String membershipPin,
                         String branchCode,
                         double finesOwed,
                         String displayName) {

        this.membershipPin = membershipPin;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }
}

public class AccessChecker {

    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {

        // private
        if (fieldModifier.equals("private")) {

            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        // default
        if (fieldModifier.equals("default")) {

            if (accessorContext.equals("SAME_CLASS") ||
                    accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        // protected
        if (fieldModifier.equals("protected")) {

            if (accessorContext.equals("SAME_CLASS") ||
                    accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        // public
        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeByModifier(String[][] attempts) {

        String[] modifiers = {
                "private",
                "default",
                "protected",
                "public"
        };

        int[] allowed = new int[4];
        int[] denied = new int[4];

        for (int i = 0; i < attempts.length; i++) {

            String modifier = attempts[i][0];
            String context = attempts[i][1];

            String result = classifyAccess(modifier, context);

            int index = -1;

            if (modifier.equals("private")) {
                index = 0;
            } else if (modifier.equals("default")) {
                index = 1;
            } else if (modifier.equals("protected")) {
                index = 2;
            } else if (modifier.equals("public")) {
                index = 3;
            }

            if (result.equals("ALLOWED")) {
                allowed[index]++;
            } else {
                denied[index]++;
            }
        }

        return "private: " + allowed[0] + " allowed / " + denied[0]
                + " denied | default: " + allowed[1] + " allowed / "
                + denied[1] + " denied | protected: " + allowed[2]
                + " allowed / " + denied[2] + " denied | public: "
                + allowed[3] + " allowed / " + denied[3] + " denied";
    }

    public static void main(String[] args) {

        System.out.println(
                classifyAccess("private", "SAME_CLASS")
        );

        System.out.println(
                classifyAccess("protected", "DIFFERENT_PACKAGE")
        );

        String[][] attempts = {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                summarizeByModifier(attempts)
        );
    }
}