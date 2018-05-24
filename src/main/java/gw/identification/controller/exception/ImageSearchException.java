package gw.identification.controller.exception;

public class ImageSearchException extends Exception {
    private int errorCode;

    public ImageSearchException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
