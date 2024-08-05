package camp.model;

public class BadInputException extends Exception{
    public BadInputException() {
        super("알 수 없는 값을 입력하셨습니다.\n다시 입력해 주십시오.");
    }

    public BadInputException(String type) {
        super("잘못된 입력입니다!\n hint : " + type + "을(를) 입력해주십시오.");
    }

    public BadInputException(String error, String hint) {
        super(error + "\n hint : " + hint);
    }
}
