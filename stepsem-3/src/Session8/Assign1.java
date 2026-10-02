abstract class WashType {
    public abstract int getDuration();
    public abstract double getCharge();
    public abstract String getName();
}

class QuickWash extends WashType {
    public int getDuration() {
        return 30;
    }

    public double getCharge() {
        return 20;
    }

    public String getName() {
        return "Quick";
    }
}

class NormalWash extends WashType {
    public int getDuration() {
        return 45;
    }

    public double getCharge() {
        return 30;
    }

    public String getName() {
        return "Normal";
    }
}

class HeavyWash extends WashType {
    public int getDuration() {
        return 60;
    }

    public double getCharge() {
        return 45;
    }

    public String getName() {
        return "Heavy";
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class WashingMachine {
    private String machineId;
    private boolean busy;

    public WashingMachine(String machineId) {
        this.machineId = machineId;
        this.busy = false;
    }

    public String getMachineId() {
        return machineId;
    }

    public boolean isBusy() {
        return busy;
    }

    private void startMachine() {
        busy = true;
    }

    private void freeMachine() {
        busy = false;
    }

    public WashCycle startWash(Student student, WashType washType) {
        if (busy) {
            System.out.println("Machine " + machineId + " is currently busy.");
            return null;
        }

        startMachine();

        WashCycle cycle = new WashCycle(student, this, washType);

        System.out.println(
                washType.getName() + " wash started on " +
                        machineId + " for " +
                        student.getName() + " (" +
                        washType.getDuration() + " min)."
        );

        System.out.printf("Charge: ₹%.2f%n", washType.getCharge());

        return cycle;
    }

    public void completeWash() {
        if (busy) {
            freeMachine();
            System.out.println(machineId + " cycle completed.");
            System.out.println(machineId + " is now free.");
        }
    }
}

class WashCycle {
    private Student student;
    private WashingMachine machine;
    private WashType washType;

    public WashCycle(Student student, WashingMachine machine, WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }
}

public class Main {
    public static void main(String[] args) {

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        WashType quick = new QuickWash();
        WashType heavy = new HeavyWash();
        WashType normal = new NormalWash();

        // Asha starts Quick wash on M1
        WashCycle cycle1 = m1.startWash(asha, quick);

        // Ravi attempts Heavy wash on busy M1
        m1.startWash(ravi, heavy);

        // Ravi starts Heavy wash on M2
        WashCycle cycle2 = m2.startWash(ravi, heavy);

        // M1 completes
        m1.completeWash();

        // Neha starts Normal wash on M1
        WashCycle cycle3 = m1.startWash(neha, normal);
    }
}