class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    // Constructor
    public LoanReceipt(String memberId, String[] bookIds) {

        this.memberId = memberId;

        // Defensive copy while receiving the array
        this.bookIds = bookIds.clone();
    }

    // Defensive copy while returning the array
    public String[] getBookIds() {

        return bookIds.clone();
    }

    // With-style method
    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        // Make a copy of the original array
        String[] newBookIds = bookIds.clone();

        // Change the copy
        newBookIds[index] = newId;

        // Create and return a completely new object
        return new LoanReceipt(
                memberId,
                newBookIds
        );
    }
}


// Reference-only receipt
class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);

        this.roomNumber = roomNumber;
    }
}

class CirculationLedger {

    static String branchCode;

    static {
        branchCode = "PT-001";
    }

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {

            // Null safety
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            // Check actual object type
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + referenceOnly
                + " reference-only | "
                + regular
                + " regular";
    }
}


public class Main {

    public static void main(String[] args) {


        LoanReceipt r =
                new LoanReceipt(
                        "LIB-8841",
                        new String[]{"BK-100", "BK-101"}
                );

        String[] ids = r.getBookIds();

        ids[0] = "HACKED";

        System.out.println(
                r.getBookIds()[0]
        );


        // --------------------------------
        // Wither pattern test
        // --------------------------------

        LoanReceipt corrected =
                r.withCorrectedBookId(
                        1,
                        "BK-102"
                );

        System.out.println(
                r.getBookIds()[0]
        );

        System.out.println(
                r.getBookIds()[1]
        );

        System.out.println(
                corrected.getBookIds()[0]
        );

        System.out.println(
                corrected.getBookIds()[1]
        );

        LoanReceipt[] receipts = {

                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        new String[]{"BK-200"},
                        "Reading Room 3"
                ),

                null,

                new LoanReceipt(
                        "LIB-002",
                        new String[]{"BK-201"}
                )
        };

        System.out.println(
                CirculationLedger.processNightlyCirculation(
                        receipts
                )
        );
    }
}