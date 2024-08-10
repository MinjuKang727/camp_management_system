package camp.model.Exception;

public class NotExistException extends Exception {
    private StringBuilder sb;
    private String hint = "";
    private String message = "해당 값이 존재하지 않습니다.";

    public NotExistException(String object) { this.setMessage(object); }

    public NotExistException(String object, String hint) {
        this.hint = hint;
        this.setMessage(object);
    }

    // GETTER
    public String getMessage() { return this.message; }

    public String getHint() { return this.hint; }

    // SETTER
    private void setMessage(String object) {
        this.sb = new StringBuilder();
        this.sb.append(object);
        this.sb.append("이(가) 존재하지 않습니다.");

        this.message = sb.toString();
    }

    private void setHint (String hint) {
        this.sb = new StringBuilder();
        this.sb.append(" hint : ");
        this.sb.append(hint);
        this.hint = this.sb.toString();
    }
}
