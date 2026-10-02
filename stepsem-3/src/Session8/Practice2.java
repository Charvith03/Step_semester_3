import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

abstract class Employee {

    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean canTakeLeave(int days);
}

class FullTimeEmployee extends Employee {

    public FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(int days) {
        return days <= 30;
    }
}

class PartTimeEmployee extends Employee {

    public PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(int days) {
        return days <= 10;
    }
}

class Contractor extends Employee {

    public Contractor(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(int days) {
        return days <= 5;
    }
}

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class LeaveRequest {

    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public boolean approve() {

        if (status != LeaveStatus.PENDING) {

            System.out.println(
                    "Cannot change leave request status from "
                            + status
                            + " to Approved."
            );

            return false;
        }

        int days = getNumberOfDays();

        if (!employee.canTakeLeave(days)) {

            System.out.println(
                    employee.getName()
                            + " is not eligible for "
                            + days
                            + " days of leave."
            );

            return false;
        }

        status = LeaveStatus.APPROVED;
        return true;
    }

    public boolean reject() {

        if (status != LeaveStatus.PENDING) {

            System.out.println(
                    "Cannot change leave request status from "
                            + status
                            + " to Rejected."
            );

            return false;
        }

        status = LeaveStatus.REJECTED;
        return true;
    }

    public boolean changeStatus(LeaveStatus newStatus) {

        if (status != LeaveStatus.PENDING) {

            System.out.println(
                    "Cannot change leave request status from "
                            + status
                            + " to "
                            + newStatus
            );

            return false;
        }

        status = newStatus;
        return true;
    }

    public int getNumberOfDays() {

        return (int) (
                ChronoUnit.DAYS.between(
                        startDate,
                        endDate
                ) + 1
        );
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public String getEmployeeName() {
        return employee.getName();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

class LeaveSystem {

    public LeaveRequest submitLeave(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        LeaveRequest request =
                new LeaveRequest(
                        employee,
                        startDate,
                        endDate
                );

        System.out.println(
                "Leave request submitted for "
                        + employee.getName()
                        + " ("
                        + startDate
                        + "-"
                        + endDate
                        + "). Status: Pending."
        );

        return request;
    }
}

public class Main {

    public static void main(String[] args) {

        LeaveSystem system =
                new LeaveSystem();

        Employee john =
                new FullTimeEmployee("John");

        Employee jane =
                new PartTimeEmployee("Jane");

        // John submits leave
        LeaveRequest johnRequest =
                system.submitLeave(
                        john,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 5)
                );

        // Manager approves
        if (johnRequest.approve()) {

            System.out.println(
                    "John's leave request approved. "
                            + "Status: "
                            + johnRequest.getStatus()
            );
        }

        // John tries to change approved request
        johnRequest.changeStatus(
                LeaveStatus.PENDING
        );

        // Jane submits leave
        LeaveRequest janeRequest =
                system.submitLeave(
                        jane,
                        LocalDate.of(2026, 2, 10),
                        LocalDate.of(2026, 2, 11)
                );

        // Manager rejects
        if (janeRequest.reject()) {

            System.out.println(
                    "Jane's leave request rejected. "
                            + "Status: "
                            + janeRequest.getStatus()
            );
        }
    }
}