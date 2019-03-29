package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Instant;
import java.util.HashMap;

/**
 * Represents a Card which contains the data and hashcode that can be called by session
 */
public class CardSrsData {
    private int hashCode; // This corresponds to a Card's hashCode
    private int numOfAttempts; // Number of attempts for this Card by a User
    private int streak;
    private Instant srsDueDate; // SRS Due Date for a User
    private HashMap <Integer, CardSrsData> cardMap = new HashMap<>();

    /**
     * Constructor for the CardData
     * Takes in the hashCode, numOfAttempts, streak and the srsDueDate
     * @param hashCode this corresponds to a Card's hashCode
     * @param numOfAttempts this corresponds to the number of attempts
     * @param streak this corresponds to the number of correct answers consecutively
     * @param srsDueDate this corresponds to the space repetition storage due date
     */
    public CardSrsData(int hashCode, int numOfAttempts, int streak, Instant srsDueDate) {
        requireAllNonNull(hashCode, numOfAttempts, streak, srsDueDate);
        this.hashCode = hashCode;
        this.numOfAttempts = numOfAttempts;
        this.streak = streak;
        this.srsDueDate = srsDueDate;
    }
    /**
     * Function for session management to get list of card datas
     * @param hashCode must be given
     * @return card from hashmap
     */
    public CardSrsData getCard(int hashCode) {
        return cardMap.get(hashCode);
    }

    public void setCard(int hashCode, CardSrsData values) {
        cardMap.put(hashCode, values);
    }

    public int getHashCode() {
        return hashCode;
    }

    public int getNumOfAttempts() {
        return numOfAttempts;
    }

    public void setNumOfAttempts(int numOfAttempts) {
        this.numOfAttempts = numOfAttempts;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }


    public Instant getSrsDueDate() {
        return srsDueDate;
    }

    @Override
    public boolean equals(Object obj) {
        CardSrsData other = (CardSrsData) obj;

        if (this.hashCode != other.getHashCode()) {
            return false;
        }
        if (this.numOfAttempts != other.getNumOfAttempts()) {
            return false;
        }
        if (this.streak != other.getStreak()) {
            return false;
        }
        if (this.srsDueDate != other.getSrsDueDate()) {
            return false;
        }

        return true;
    }
}
