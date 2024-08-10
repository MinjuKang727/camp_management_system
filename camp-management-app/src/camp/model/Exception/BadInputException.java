package camp.model.Exception;

public class BadInputException extends Exception {
    private StringBuilder sb;
    private String message = "\n알 수 없는 값을 입력하셨습니다.";
    private String hint = "";

    public BadInputException() {}

    public BadInputException(String message) {
        this.message = message;
    }

    public BadInputException(String message, String hint) {
        this.setMessage(message);
        this.setHint(hint);
    }

    public BadInputException(int min, int max) {
        this.setHint(min, max);
    }

    // GETTER
    public String getMessage() { return this.message; }

    public String getHint() { return this.hint; }

    // SETTER
    private void setMessage(String message) { this.message = message; }

    private void setHint(String hint) {
        this.sb = new StringBuilder();
        this.sb.append(" hint : ");
        this.sb.append(hint);
        this.sb.append("\n");
        this.hint = this.sb.toString();
    }

    private void setHint(int min, int max) {
        this.sb = new StringBuilder();
        this.sb.append(" hint : ");
        this.sb.append(min);
        this.sb.append(" 이상 ");
        this.sb.append(max);
        this.sb.append(" 이하의 정수만 입력 가능합니다.\n");
        this.hint = sb.toString();
    }

}
