package seedu.address.logic;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.quiz.QuizStartCommand;
import seedu.address.logic.parser.ManagementModeParser;
import seedu.address.logic.parser.QuizModeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.quiz.QuizUiDisplayFormatter;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String CHECK_LOGS_MESSAGE = "\nPlease check the logs for more information.";
    public static final String FAIL_SAVE_LESSONS_MESSAGE = "Failed to save some lessons.";
    public static final String FAIL_DELETE_LESSON_MESSAGE = "Failed to delete lesson: \"%1$s\".";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Storage storageManager;
    private final ManagementModel managementModel;
    private final QuizModel quizModel;
    private final CommandHistory history;
    private final ManagementModeParser managementModeParser;
    private final QuizModeParser quizModeParser;

    /**
     * Different mode that will show different UI and have access to different commands.
     */
    public enum Mode {
        MANAGEMENT,
        QUIZ
    }

    public LogicManager(ManagementModel managementModel, QuizModel quizModel, Storage storageManager) {
        this.storageManager = storageManager;
        this.managementModel = managementModel;
        this.quizModel = quizModel;
        history = new CommandHistory();
        managementModeParser = new ManagementModeParser();
        quizModeParser = new QuizModeParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        try {
            Command command;
            if (getMode() == Mode.MANAGEMENT) {
                command = managementModeParser.parse(commandText);
                commandResult = command.execute(managementModel, history);

                // I'm so sorry for this eyesore.
                switch (commandResult.getUpdateStorageType()) {
                case NONE:
                    break;
                case SAVE:
                    int savedCount = storageManager.saveLessonList(managementModel.getLessonList());
                    int totalLessonCount = managementModel.getLessons().size();
                    if (savedCount < totalLessonCount) {
                        commandResult = new CommandResult(FAIL_SAVE_LESSONS_MESSAGE + CHECK_LOGS_MESSAGE);
                    }
                    break;
                case LOAD:
                    Optional<LessonList> lessonListOptional = storageManager.readLessonList();
                    if (lessonListOptional.isPresent()) {
                        managementModel.setLessonList(lessonListOptional.get());
                    } else {
                        commandResult = new CommandResult("Failed to load lessons. Please check "
                            + "the logs for more information." + CHECK_LOGS_MESSAGE);
                    }
                    break;
                case DELETE:
                    try {
                        storageManager.deleteLesson(commandResult.getDeleteLessonName());
                    } catch (IOException e) {
                        commandResult =
                            new CommandResult(String.format(FAIL_DELETE_LESSON_MESSAGE,
                                commandResult.getDeleteLessonName()) + CHECK_LOGS_MESSAGE);
                    }
                    break;
                default:
                }
            } else {
                command = quizModeParser.parse(commandText);
                commandResult = command.execute(quizModel, history);

                if (quizModel.isQuizDone()) {
                    storageManager.saveUser(managementModel.getUser());
                }
            }

            if (command instanceof QuizStartCommand) {
                QuizStartCommand quizStartCommand = (QuizStartCommand) command;
                commandResult = quizStartCommand.executeActual(quizModel, history);
            }
        } finally {
            history.add(commandText);
        }

        return commandResult;
    }

    @Override
    public Mode getMode() {
        return quizModel.isQuizDone() ? Mode.MANAGEMENT : Mode.QUIZ;
    }

    @Override
    public List<Lesson> getLessons() {
        return managementModel.getLessons();
    }

    @Override
    public QuizUiDisplayFormatter getDisplayFormatter() {
        return quizModel.getDisplayFormatter();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return managementModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        managementModel.setGuiSettings(guiSettings);
    }
}
