package app.jinan159.ladder.domain;

public class LadderResult implements LadderEndPoint {

    private final int id;
    private final String name;

    public LadderResult(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

}
