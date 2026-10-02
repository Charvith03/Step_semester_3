abstract class GardenTool {

    public GardenTool() {
    }

    public abstract String use();
}

class CuttingTool extends GardenTool {

    public CuttingTool() {
        super();
    }

    @Override
    public String use() {

        return "Using the tool in the garden, " +
                "blade sharpened first";
    }
}

class Pruner extends CuttingTool {

    public Pruner() {
        super();
    }

    @Override
    public String use() {

        String result = super.use();

        return result +
                ", then trimming branches precisely";
    }
}

public class Main {

    public static void main(String[] args) {

        CuttingTool c =
                new CuttingTool();

        Pruner p =
                new Pruner();

        System.out.println(c.use());

        System.out.println(p.use());
    }
}