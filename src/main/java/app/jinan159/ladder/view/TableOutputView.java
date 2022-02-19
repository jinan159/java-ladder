package app.jinan159.ladder.view;

import app.jinan159.ladder.config.GameConfig;
import app.jinan159.ladder.domain.LadderElement;
import app.jinan159.ladder.domain.LadderResult;
import app.jinan159.ladder.domain.Participant;
import app.jinan159.ladder.domain.gamemap.GameMapColumn;
import app.jinan159.ladder.domain.gamemap.GameMapRow;
import app.jinan159.ladder.domain.gamemap.TableGameMap;
import app.jinan159.ladder.utils.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class TableOutputView extends OutputView<TableGameMap> {

    private final OutputStreamWriter writer;
    private final GameConfig config;

    private TableOutputView(GameConfig config) {
        this(System.out, config);
    }

    private TableOutputView(OutputStream outputStream, GameConfig config) {
        super(outputStream, config);
        this.writer = new OutputStreamWriter(outputStream);
        this.config = config;
    }

    public static TableOutputView createWithConfig(GameConfig config) {
        return new TableOutputView(config);
    }

    @Override
    public void write(String s) throws IOException {
        writer.write(s);
    }

    @Override
    public void writeParticipants(List<Participant> participants) throws IOException {
        write(participantsToString(participants));
    }

    @Override
    public void writeGameMap(TableGameMap gameMap) throws IOException {
        write(formatGameMap(gameMap));
    }

    @Override
    public void writeLadderResults(List<LadderResult> ladderResult) throws IOException {
        write(LadderResultsToString(ladderResult));
    }

    private String participantsToString(List<Participant> participants) {
        return participants.stream()
                .map(Participant::getName)
                .map(name -> StringUtils.padLeftRight(name, config.getNameLength()))
                .reduce((nested, name) -> nested + " " + name)
                .orElse("") + "\n";
    }

    private String formatGameMap(TableGameMap gameMap) {
        StringBuilder sb = new StringBuilder();

        for (GameMapRow row : gameMap) {
            sb.append(rowToString(row));
            sb.append('\n');
        }

        return sb.toString();
    }

    private String LadderResultsToString(List<LadderResult> participants) {
        return participants.stream()
                .map(LadderResult::getName)
                .map(name -> StringUtils.padLeftRight(name, config.getNameLength()))
                .reduce((nested, name) -> nested + " " + name)
                .orElse("") + "\n";
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
