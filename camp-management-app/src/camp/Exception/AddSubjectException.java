package camp.Exception;

public class AddSubjectException extends Exception {
    private String message;
    private boolean flag;

    public AddSubjectException() {
        this.message = "현재 분류의 모든 과목을 수강 신청하였습니다.\n해당 분류의 과목 수강 신청이 종료됩니다.";
        this.flag = false;
    }

    public AddSubjectException(int min, int joined) {
        this.message = "최소 수강 신청 과목수를 충족하지 못하였습니다.";
        this.setHint(min, joined);
        this.flag = true;
    }

    // GETTER
    public String getMessage() { return this.message; }

    public boolean getFlag() { return this.flag; }

    // SETTER
    private void setHint(int min, int joined) {
        StringBuffer sb = new StringBuffer();
        sb.append(this.message);
        sb.append("\n hint : 해당 과목은 최소 ");
        sb.append(min);
        sb.append(" 과목 이상 신청하여야합니다. (현재 : ");
        sb.append(joined);
        sb.append(" 과목 신청)\n해당 과목 선택을 계속 진행하겠습니다.");
        this.message = sb.toString();
    }
}
