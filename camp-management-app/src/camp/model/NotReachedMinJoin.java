package camp.model;

public class NotReachedMinJoin extends Exception {
    private StringBuilder sb;
    private String message;
    private String hint = "";

    public NotReachedMinJoin(int min, int joinedCnt) {
        this.message = "최소 수강 신청 과목수 미충족";
        this.setHint(min, joinedCnt);
    }

    // GETTER
    public String getMessage() { return this.message; }

    public String getHint() { return this.hint; }

    // SETTER
    protected void setHint(String hint) {
        this.sb = new StringBuilder();
        this.sb.append(" hint : ");
        this.sb.append(hint);
        this.hint = this.sb.toString();
    }

    private void setHint(int min, int joinedCnt) {
        this.sb = new StringBuilder();
        this.sb.append("\n hint : 해당 과목은 최소 ");
        this.sb.append(min);
        this.sb.append(" 과목 이상 신청하여야합니다. (현재 : ");
        this.sb.append(joinedCnt);
        this.sb.append(" 과목 신청)\n해당 과목 선택을 계속 진행하겠습니다.");
        this.hint = this.sb.toString();
    }
}
