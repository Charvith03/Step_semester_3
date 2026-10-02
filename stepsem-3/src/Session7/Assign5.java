abstract class Drone {

    protected String id;

    public Drone(String id) {
        this.id = id;
    }

    public abstract String fly();
}

interface Trackable {

    String getLocation();
}

class DeliveryDrone extends Drone
        implements Trackable {

    public DeliveryDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {

        return "Delivery drone "
                + id
                + " flying";
    }

    @Override
    public String getLocation() {

        return id + " at Sector 4";
    }
}

class ScoutDrone extends Drone {

    public ScoutDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {

        return "Scout drone "
                + id
                + " flying";
    }
}

class GroundRobot implements Trackable {

    private String id;

    public GroundRobot(String id) {
        this.id = id;
    }

    @Override
    public String getLocation() {

        return id + " at Sector 4";
    }
}

public class Main {

    static String getLocationIfTrackable(Object o) {

        if (o instanceof Trackable) {

            Trackable trackable =
                    (Trackable) o;

            return trackable.getLocation();
        }

        return "Tracking not available";
    }

    public static void main(String[] args) {

        DeliveryDrone d =
                new DeliveryDrone("DR-1");

        ScoutDrone s =
                new ScoutDrone("SC-1");

        GroundRobot g =
                new GroundRobot("GR-1");

        System.out.println(
                getLocationIfTrackable(d)
        );

        System.out.println(
                getLocationIfTrackable(s)
        );

        System.out.println(
                getLocationIfTrackable(g)
        );
    }
}