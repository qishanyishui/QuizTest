package fr.epita.quiz.exception;

public class DeleteFailedException extends DataAccessException {


    public DeleteFailedException(Object beanThatWasNotDeleted) {
        super(beanThatWasNotDeleted);
    }

    public DeleteFailedException(Object beanThatWasNotDeleted, Exception initialCause) {
        super(beanThatWasNotDeleted, initialCause);
    }

}
