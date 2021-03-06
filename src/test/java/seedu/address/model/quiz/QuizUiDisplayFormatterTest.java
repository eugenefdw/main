package seedu.address.model.quiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class QuizUiDisplayFormatterTest {
    private static final String QUESTION_HEADER = "Question";
    private static final String QUESTION = "some question";
    private static final String ANSWER_HEADER = "Answer";
    private static final String ANSWER = "some answer";
    private static final QuizMode MODE = QuizMode.LEARN;

    private static final QuizUiDisplayFormatter formatter = new QuizUiDisplayFormatter(QUESTION_HEADER, QUESTION,
        ANSWER_HEADER, ANSWER, MODE);
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fullConstructor_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new QuizUiDisplayFormatter(null, null, null, null, null);
    }

    @Test
    public void withoutAnswerConstructor_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new QuizUiDisplayFormatter(null, null, null, null);
    }

    @Test
    public void getQuestonHeader() {
        assertEquals(QUESTION_HEADER, formatter.getQuestionHeader());
    }

    @Test
    public void getQuestion() {
        assertEquals(QUESTION, formatter.getQuestion());
    }

    @Test
    public void getAnswerHeader() {
        assertEquals(ANSWER_HEADER, formatter.getAnswerHeader());
    }

    @Test
    public void getAnswer() {
        assertEquals(ANSWER, formatter.getAnswer());
    }

    @Test
    public void getMode() {
        assertEquals(MODE, formatter.getMode());
    }

    @Test
    public void equals() {
        final QuizUiDisplayFormatter anotherFormatter = new QuizUiDisplayFormatter(QUESTION_HEADER, QUESTION,
            ANSWER_HEADER, ANSWER, MODE);
        final QuizUiDisplayFormatter formatterWithoutAnswer = new QuizUiDisplayFormatter(QUESTION_HEADER, QUESTION,
            ANSWER_HEADER, MODE);

        // same object
        assertEquals(formatter, formatter);

        // same value
        assertEquals(formatter, anotherFormatter);

        // different obj
        assertNotEquals(formatter, new Object());

        // different types
        assertNotEquals("random things", formatter);

        // different values
        assertNotEquals(formatter, formatterWithoutAnswer);
    }

    @Test
    public void hashcode() {
        final QuizUiDisplayFormatter anotherFormatter = new QuizUiDisplayFormatter(QUESTION_HEADER, QUESTION,
            ANSWER_HEADER, ANSWER, MODE);
        final QuizUiDisplayFormatter formatterWithoutAnswer = new QuizUiDisplayFormatter(QUESTION_HEADER, QUESTION,
            ANSWER_HEADER, MODE);

        // same value
        assertEquals(formatter.hashCode(), anotherFormatter.hashCode());

        // without answer
        assertNotEquals(formatter.hashCode(), formatterWithoutAnswer.hashCode());
    }


}
