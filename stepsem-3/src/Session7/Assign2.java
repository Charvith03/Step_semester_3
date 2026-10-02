abstract class ArtPiece {

    private static int pieceCount = 0;

    private final String pieceId;

    protected String title;

    public ArtPiece(String title) {

        this.title = title;

        pieceCount++;

        pieceId = "ART-" + (1000 + pieceCount);
    }

    public abstract String describe();

    public String getPieceId() {
        return pieceId;
    }
}

class Painting extends ArtPiece {

    public Painting(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Painting: "
                + title
                + ", framed on canvas";
    }
}

class Sculpture extends ArtPiece {

    public Sculpture(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Sculpture: "
                + title
                + ", carved from stone";
    }
}

public class Main {

    public static void main(String[] args) {

        Painting p =
                new Painting("Sunset Fields");

        Sculpture s =
                new Sculpture("The Thinker II");

        System.out.println(p.describe());

        System.out.println(s.describe());

        System.out.println(p.getPieceId());

        System.out.println(s.getPieceId());


    }
}