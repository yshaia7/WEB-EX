package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This class stand for poll question and answer
 * the class contain the question of the poll and
 * array of answers and array of votes that count
 *
 * functional
 * getNumOfVote()      - num of vote for specific answer
 * increaseNumOfVote() - increase specific answer vote that choosed
 * getPollQuestion()   - get the question of the poll
 * getAnswer()         - for get specific answer
 * getNumOfAnswers()   - number of answer in the poll
 */
public class Poll {
    private String pollQuestion;
    private Answers answers;
    private Vote voteArr;

    /**
     * initial the parmeter of the class
     *
     * @param url - path of the file to read
     * @throws FileException - if input file are error or num of line in file small rhe three
     */
    public Poll(URL url) throws FileException {
        answers = new Answers();
        voteArr = new Vote();
        createPollQuestionAndAnswers(url);
    }

    /**
     * read the question and forward the stream to the answer
     * class for the answer class read the answers
     *
     * @param url path of the file to read
     * @throws FileException - if input file are error
     */
    private void createPollQuestionAndAnswers(URL url) throws FileException {

        BufferedReader file = null;
        try {
             file = new BufferedReader(new InputStreamReader((url.openStream())));
        } catch (Exception e) {
            throw new FileException();
        }
        String line = new String();

        /*create the poll question and send stream to answers class to create the answers */
        try {
            if ((line = file.readLine()) != null) {
                pollQuestion = line;
                answers.createAnswersPoll(file);
            }
        } catch (IOException e) { }

        /*After we read from the file we need to close the stream */
        finally { try { file.close(); } catch (IOException e) { }
        }
        /* initial the vote arr, same size of the answers arr */
        voteArr.initialVoteStatics(answers.getNumOfAnswers());
    }

    /**
     *   num of vote for specific answer
     * @param place - what answer we want amount that vote
     * @return amount of vote the place require
     */
    public Integer getNumOfVote(Integer place){
        return voteArr.getNumOfVoted(place);
    }

    /**
     *  increase specific answer vote that choosed
     * @param place number of answer to increase
     */
    public void increaseNumOfVote(Integer place){
        voteArr.increaseNumOfVote(place);
    }

    /**
     * @return the question of the poll
     */
    public String getPollQuestion(){
        return pollQuestion;
    }

    /**
     * @return size of answers in the poll
     */
    public Integer getNumOfAnswers(){
        return answers.getNumOfAnswers();
    }

    /**
     * @param answerNumber  for get specific answer
     * @return number that require
     */
    public  String getAnswer(Integer answerNumber){
        return answers.getAnswer(answerNumber);
    }
}

