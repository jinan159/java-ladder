package app.jinan159.ladder.domain;

public class Participant implements LadderEndPoint {

    private final String name;

    public Participant(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
