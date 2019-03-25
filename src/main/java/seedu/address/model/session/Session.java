package seedu.address.model.session;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.srscard.SrsCard;


/**
 * Represents a session that stores cards based on srs data.
 */
public class Session {
    public static final int CARD_COUNT_MINIMUM = 1;

    private String name;
    private QuizMode mode;
    private int cardCount;
    private List<QuizCard> quizCards;
    private List<SrsCard> srsCards;

    public Session(String name, int cardCount, QuizMode mode) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (cardCount < CARD_COUNT_MINIMUM) {
            throw new IllegalArgumentException("CardCount should not zero");
        }
        if ((mode != QuizMode.LEARN) & (mode != QuizMode.REVIEW) & (mode != QuizMode.PREVIEW)) {
            throw new IllegalArgumentException("Invalid mode");
        }
        this.name = name;
        this.cardCount = cardCount;
        this.mode = mode;
    }

    public Session(String name, int cardCount, QuizMode mode, List<SrsCard> srsCards) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (cardCount < CARD_COUNT_MINIMUM) {
            throw new IllegalArgumentException("CardCount should not be less than five in a single session");
        }
        if ((mode != QuizMode.LEARN) & (mode != QuizMode.REVIEW) & (mode != QuizMode.PREVIEW)) {
            throw new IllegalArgumentException("Invalid mode");
        }

        quizCards = new ArrayList<>();

        this.name = name;
        this.cardCount = cardCount;
        this.mode = mode;
        this.srsCards = srsCards;
    }

    public Session(String name, QuizMode mode, List<SrsCard> srsCards) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Invalid name");
        }
        if ((mode != QuizMode.LEARN) & (mode != QuizMode.REVIEW) & (mode != QuizMode.PREVIEW)) {
            throw new IllegalArgumentException("Invalid mode");
        }

        quizCards = new ArrayList<>();

        this.name = name;
        this.cardCount = 10;
        this.mode = mode;
        this.srsCards = srsCards;
    }

    /**
     * Generate a list of quizCards that will pass to quiz system.
     */
    public List<QuizCard> generateSession() {
        SrsCard currentCard;
        for (int i = 0; i < cardCount; i++) {
            currentCard = srsCards.get(i);
            quizCards.add(new QuizCard(currentCard.getQuestion(), currentCard.getAnswer()));
        }
        return quizCards;
    }

    public QuizMode getMode() {
        return mode;
    }

    public int getCount() {
        return cardCount;
    }

    public String getName() {
        return name;
    }

    public List<SrsCard> getSrsCards() {
        return srsCards;
    }
}