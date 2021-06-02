package ex2;

import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is for save the answers of the poll, it will store the answers in list
 * the class get bufferReader and create the poll answers
 *
 * methods:
 * 1.provide function to get specific answer that required
 * 2.provide function to get number of answer are exist in poll
 *
 */
public class Answers {
    private List<String> pollAnswers;
    public Answers() {
        pollAnswers = new ArrayList();
    }

    /**
     * that method read from the buffer and create the poll answers
     *
     * @param file - BufferedReader for read the answers from file
     */
    public void createAnswersPoll(@NotNull BufferedReader file) {
        try {
            String line = new String();
            while ((line = file.readLine()) != null) {
                pollAnswers.add(line);
            }
        } catch (Exception e) {
        }

    }

    /**
     * @return  return number of answers in the poll
     */
    public Integer getNumOfAnswers() {
        return pollAnswers.size();
    }

    /**
     * @param answerNumber - answer number that requier
     * @return specific answer that require
     */
    public String getAnswer(Integer answerNumber) {
        return pollAnswers.get(answerNumber);
    }
}
