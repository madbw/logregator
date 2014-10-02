package com.century.logregator.tag_extractor;

/**
 * wrong pom file
 */
public class WrongPomFileException extends RuntimeException{
    public WrongPomFileException() {
    }

    public WrongPomFileException(String message) {
        super(message);
    }

    public WrongPomFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongPomFileException(Throwable cause) {
        super(cause);
    }

    public WrongPomFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
