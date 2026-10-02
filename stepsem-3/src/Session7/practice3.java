abstract class Instrument {

    public Instrument() {
    }

    public abstract String play();
}

class StringInstrument extends Instrument {

    public StringInstrument() {
        super();
    }

    @Override
    public String play() {

        String result = superPlay();

        return result;
    }

    private String superPlay() {
        return "Strumming the strings";
    }
}

class Violin extends StringInstrument {

    public Violin() {
        super();
    }

    @Override
    public String play() {

        String result = super.play();

        return result +
                ", with a bow drawn across four strings";
    }
}

public class Main {

    public static void main(String[] args) {

        StringInstrument s =
                new StringInstrument();

        Violin v =
                new Violin();

        System.out.println(s.play());

        System.out.println(v.play());
    }
}