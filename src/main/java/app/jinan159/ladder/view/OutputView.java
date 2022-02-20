package app.jinan159.ladder.view;

import app.jinan159.ladder.config.GameConfig;
import app.jinan159.ladder.domain.LadderElement;
import app.jinan159.ladder.domain.LadderEndPoint;
import app.jinan159.ladder.domain.LadderResult;
import app.jinan159.ladder.domain.Participant;
import app.jinan159.ladder.domain.gamemap.GameMap;
import app.jinan159.ladder.domain.gamemap.GameMapColumn;
import app.jinan159.ladder.domain.gamemap.GameMapRow;
import app.jinan159.ladder.utils.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class OutputView implements Closeable {

    private final OutputStreamWriter writer;
    private final GameConfig config;

    private OutputView(GameConfig config) {
        this(System.out, config);
    }

    private OutputView(OutputStream outputStream, GameConfig config) {
        this.writer = new OutputStreamWriter(outputStream);
        this.config = config;
    }

    public static OutputView createWithConfig(GameConfig config) {
        return new OutputView(config);
    }

    public void write(String s) throws IOException {
        writer.write(s);
    }

    public void writeEndPoints(List<? extends LadderEndPoint> endPoints) throws IOException {
        write(ladderEntPointsToString(endPoints));
    }

    public void writeGameMap(GameMap gameMap) throws IOException {
        write(formatGameMap(gameMap));
    }

    private String ladderEntPointsToString(List<? extends LadderEndPoint> endPoints) {
        return endPoints.stream()
                .map(LadderEndPoint::getName)
                .map(name -> StringUtils.padLeftRight(name, config.getNameLength()))
                .reduce((nested, name) -> nested + " " + name)
                .orElse("") + "\n";
    }

    private String formatGameMap(GameMap gameMap) {
        StringBuilder sb = new StringBuilder();

        for (GameMapRow row : gameMap) {
            sb.append(rowToString(row));
            sb.append('\n');
        }

        return sb.toString();
    }

    private String rowToString(GameMapRow row) {
        StringBuilder sb = new StringBuilder();
        for (GameMapColumn col : row) {
            sb.append(formatElement(col.getElement()));
        }

        return sb.toString();
    }

    private String formatElement(LadderElement element) {
        switch (element) {
            case L_PAD:
                return StringUtils.repeatCharacter(element.getMark(), config.getLadderSize() / 2);
            case V_LINE:
                return String.valueOf(element.getMark());
            case EMPTY:
            case H_LINE:
                return StringUtils.repeatCharacter(element.getMark(), config.getLadderSize());
            default:
                return "";
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
