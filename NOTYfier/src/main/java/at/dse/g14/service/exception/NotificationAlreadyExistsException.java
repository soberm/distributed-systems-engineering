package at.dse.g14.service.exception;

import at.dse.g14.commons.service.exception.ServiceException;

public class NotificationAlreadyExistsException extends ServiceException {

    public NotificationAlreadyExistsException() {
    }

    public NotificationAlreadyExistsException(String message) {
        super(message);
    }

    public NotificationAlreadyExistsException(String message, Exception e) {
        super(message, e);
    }

}
