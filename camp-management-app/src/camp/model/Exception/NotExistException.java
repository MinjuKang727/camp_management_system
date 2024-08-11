package camp.model.Exception;

public class NotExistException extends Exception {
    private String message;


    public NotExistException(String object) { this.setMessage(object); }

    public NotExistException(String object, String hint) {
        this.setMessage(object);
        this.setHint(hint);
    }

    // GETTER
    public String getMessage() { return this.message; }

    // SETTER
    private void setMessage(String object) {
        StringBuilder sb = new StringBuilder();
        sb.append(object);
        sb.append("이(가) 존재하지 않습니다.");

        this.message = sb.toString();
    }

    private void setHint (String hint) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.message);
        sb.append("\n hint : ");
        sb.append(hint);
        this.message = sb.toString();
    }
}
