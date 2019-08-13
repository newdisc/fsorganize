package nd.fsorganize.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FSOrganizeException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(FSOrganizeException.class);
    private static final long serialVersionUID = 4865455517913198172L;

    public FSOrganizeException(String message) {
        super(message);
        log.debug(message);
    }
    public FSOrganizeException(Throwable cause) {
        super(cause);
        log.debug(cause.getMessage());
    }
    public FSOrganizeException(String message, Throwable cause) {
        super(message, cause);
        log.debug(message, cause);
    }
    public FSOrganizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        log.debug(message, cause);
    }
    public static FSOrganizeException raiseAndLog(final String message, final Exception e, final Logger logger) {
        final String err = message;
        logger.error(err, e);
        return new FSOrganizeException(err, e);
    }
}
