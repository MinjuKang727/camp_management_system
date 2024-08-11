package camp.model.Exception;

public class ExitThisPage extends Throwable {
    public ExitThisPage() { super("\n현재 페이지를 종료하고 이전 페이지로 돌아갑니다."); }
}
