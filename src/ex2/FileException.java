package ex2;

/**
 * This class stand for handle exception when failed open the file
 *
 * Also the class is empty, the reason i create that class is for throw
 * same exception in 2 situation, file cant be read, file as less
 * then one question and 2 answers
 */
public class FileException extends Exception {
    public FileException() {
    }
}
