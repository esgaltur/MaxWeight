package cz.esgaltur.maxweight.web.exception;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;

/**
 * Global exception handler for the application.
 * Handles exceptions that are not caught by specific controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle ClientAbortException which occurs when a client disconnects before
     * the server finishes sending the response.
     *
     * @param ex the ClientAbortException
     */
    @ExceptionHandler(ClientAbortException.class)
    @ResponseStatus(HttpStatus.OK)  // Return 200 OK since this is not a server error
    public void handleClientAbortException(ClientAbortException ex) {
        // Log at debug level since this is a normal event
        logger.debug("Client aborted connection: {}", ex.getMessage());
    }

    /**
     * Handle AsyncRequestTimeoutException which occurs when an async request times out.
     *
     * @param ex the AsyncRequestTimeoutException
     */
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex) {
        logger.warn("Async request timed out: {}", ex.getMessage());
    }

    /**
     * Handle AsyncRequestNotUsableException which occurs when an async request
     * cannot be used, typically because the client has disconnected.
     *
     * @param ex the AsyncRequestNotUsableException
     */
    @ExceptionHandler(AsyncRequestNotUsableException.class)
    @ResponseStatus(HttpStatus.OK)  // Return 200 OK since this is not a server error
    public void handleAsyncRequestNotUsableException(AsyncRequestNotUsableException ex) {
        logger.debug("Async request not usable (client likely disconnected): {}", ex.getMessage());
    }

    /**
     * Handle IOException which may occur when a client disconnects.
     *
     * @param ex the IOException
     */
    @ExceptionHandler(java.io.IOException.class)
    @ResponseStatus(HttpStatus.OK)  // Return 200 OK since this is not a server error
    public void handleIOException(java.io.IOException ex) {
        // Check if this is a client abort
        if (ex.getMessage() != null && 
            (ex.getMessage().contains("Broken pipe") || 
             ex.getMessage().contains("Connection reset by peer") ||
             ex.getMessage().contains("An established connection was aborted"))) {
            logger.debug("Client disconnected: {}", ex.getMessage());
        } else {
            logger.error("IO error occurred", ex);
        }
    }
}
