package app.jinan159.ladder.view;

import app.jinan159.ladder.config.GameConfig;
import app.jinan159.ladder.domain.LadderResult;
import app.jinan159.ladder.domain.Participant;
import app.jinan159.ladder.domain.gamemap.GameMap;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public abstract class OutputView<T extends GameMap> implements Closeable {

    protected final OutputStreamWriter writer;
    protected final GameConfig config;

    public OutputView(OutputStream outputStream, GameConfig config) {
        this.writer = new OutputStreamWriter(outputStream);
        this.config = config;
    }

    public abstract void write(String s) throws IOException;

    public abstract void writeParticipants(List<Participant> participants) throws IOException;

    public abstract void writeGameMap(T t) throws IOException;

    public abstract void writeLadderResults(List<LadderResult> ladderResult) throws IOException;

}
