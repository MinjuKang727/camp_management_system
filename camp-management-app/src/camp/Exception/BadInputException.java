package camp.Exception;

public class BadInputException extends Exception {
    private String message;

    public BadInputException(String message, String hint) {
        this.message = message;
        this.setHint(hint);
    }

    public BadInputException(int min, int max) {
        this.setHint(min, max);
    }

    // GETTER
    public String getMessage() { return this.message; }

    // SETTER

    private void setHint(String hint) {
        StringBuffer sb = new StringBuffer();
        sb.append(this.message);
        sb.append("\n hint : ");
        sb.append(hint);
        sb.append("\n");
        this.message = sb.toString();
    }

    private void setHint(int min, int max) {
        StringBuffer sb = new StringBuffer();
        sb.append(this.message);
        sb.append("\n hint : ");
        sb.append(min);
        sb.append(" 이상 ");
        sb.append(max);
        sb.append(" 이하의 정수만 입력 가능합니다.\n");
        this.message = sb.toString();
    }
}
