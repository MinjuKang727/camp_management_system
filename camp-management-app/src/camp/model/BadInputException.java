package camp.model;

public class BadInputException extends Exception{
    public BadInputException() {
        super("알 수 없는 값을 입력하셨습니다.\n다시 입력해 주십시오.");
    }

    public BadInputException(String type) {
        super(type + "이 존재하지 않습니다.");
    }

    public BadInputException(String error, String hint) {
        super(error + "\n hint : " + hint);
    }
}
