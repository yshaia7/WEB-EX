package ex2;

import java.util.ArrayList;

/**
 * This class is for save each question the amounts of votes
 *
 * the class get size and create the poll vote arr
 *
 * methods:
 * 1. provide how much votes each question are already voted
 * 2. increase specific question vote number that required
 *
 */
public class Vote {
    private ArrayList<Integer> arrayVot;

    public Vote() {
            arrayVot = new ArrayList();
    }

    /**
     * initilaze array that save vote numbers
     * @param size - size of array to create
     */
    public void initialVoteStatics(Integer size) {
        for (int i = 0; i < size; ++i)
            arrayVot.add(0);
    }

    /**
     * @param place - get the number of question that want num of votes
     * @return - number of vote in specific question that required
     */
    public synchronized Integer getNumOfVoted(Integer place) {
        return arrayVot.get(place);
    }

    /**
     * @param place - increase the number of votes for specific question that required
     */
    public synchronized void increaseNumOfVote(Integer place) {
        arrayVot.set(place, arrayVot.get(place) + 1);
    }
}
