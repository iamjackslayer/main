package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;

/**
 * Returns statistics that are totals.
 */
public class TotalsCommand extends Command {

    public static final String COMMAND_WORD = "totals";

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) {
        requireAllNonNull(model, analytics);
        return new CommandResult(analytics.getTotalStatistics());
    }
}
