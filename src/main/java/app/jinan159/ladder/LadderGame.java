package app.jinan159.ladder;

import app.jinan159.ladder.config.GameConfig;
import app.jinan159.ladder.domain.LadderResult;
import app.jinan159.ladder.view.InputView;
import app.jinan159.ladder.view.OutputView;
import app.jinan159.ladder.domain.gamemap.GameMap;
import app.jinan159.ladder.domain.Participant;

import java.io.IOException;
import java.util.List;

public class LadderGame {

    private final GameMap gameMap;
    private final GameConfig config;
    private final List<Participant> participants;
    private final List<LadderResult> ladderResults;

    private LadderGame(int nameLength) {
        config = GameConfig.createWithNameLength(nameLength);

        try (InputView inputView = InputView.createWithConfig(config)) {
            this.participants = inputView.readParticipants();
            this.ladderResults = inputView.readLadderResults(participants.size());
            int height = inputView.readHeight();
            this.gameMap = new GameMap(participants.size(), height);
        }
    }

    public static LadderGame createWithNameLength(int nameLength) throws IOException {
        return new LadderGame(nameLength);
    }

    // ------- public method ---------
    public void startGame() throws IOException {
        try (OutputView outputView = OutputView.createWithConfig(config)) {
            outputView.writeEndPoints(this.participants);
            outputView.writeGameMap(this.gameMap);
            outputView.writeEndPoints(this.ladderResults);
        }
    }

}
