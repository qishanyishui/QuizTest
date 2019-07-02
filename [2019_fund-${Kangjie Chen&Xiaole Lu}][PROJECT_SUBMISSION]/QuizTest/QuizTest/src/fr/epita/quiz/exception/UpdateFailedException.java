package fr.epita.quiz.exception;

public class UpdateFailedException extends DataAccessException {


    public UpdateFailedException(Object beanThatWasNotUpdated) {
        super(beanThatWasNotUpdated);
    }

    public UpdateFailedException(Object beanThatWasNotUpdated, Exception initialCause) {
        super(beanThatWasNotUpdated, initialCause);
    }

}
