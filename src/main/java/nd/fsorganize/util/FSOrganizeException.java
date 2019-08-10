package nd.fsorganize.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FSOrganizeException extends RuntimeException {
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
}
