package gw.identification.controller.exception;

public class ImageSaveException extends Exception {
    private int errorCode;

    public ImageSaveException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
