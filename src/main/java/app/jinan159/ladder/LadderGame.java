package app.jinan159.ladder;

import app.jinan159.ladder.config.GameConfig;
import app.jinan159.ladder.domain.LadderResult;
import app.jinan159.ladder.view.InputView;
import app.jinan159.ladder.view.OutputView;
import app.jinan159.ladder.view.TableOutputView;
import app.jinan159.ladder.domain.gamemap.TableGameMap;
import app.jinan159.ladder.domain.Participant;

import java.io.IOException;
import java.util.List;

public class LadderGame {

    private final TableGameMap gameMap;
    private final GameConfig config;
    private final List<Participant> participants;
    private final List<LadderResult> ladderResults;

    private LadderGame(int nameLength) {
        config = GameConfig.createWithNameLength(nameLength);

        try (InputView inputView = InputView.createWithConfig(config)) {
            this.participants = inputView.readParticipants();
            this.ladderResults = reader.readLadderResults();
            int height = inputView.readHeight();
            this.gameMap = new TableGameMap(participants.size(), height);
        }
    }

    public static LadderGame createWithNameLength(int nameLength) throws IOException {
        return new LadderGame(nameLength);
    }

    // ------- public method ---------
    public void startGame() throws IOException {
        try (OutputView<TableGameMap> writer = TableOutputView.createWithConfig(config)) {
            writer.writeParticipants(this.participants);
            writer.writeGameMap(gameMap);
            writer.writeLadderResults(this.ladderResults);
        }
    }

}
