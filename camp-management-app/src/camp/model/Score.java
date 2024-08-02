package camp.model;

public class Score {
    private String scoreId;
    private String studentId;
    private String subjectName;
    private int testCnt;
    private int tsetScore;
    private String rank;

    public Score(String seq, String studentId, String subjectName, int testCnt, int tsetScore, String rank) {
        this.scoreId = seq;
        this.studentId = studentId;
        this.subjectName = subjectName;
        this.testCnt = testCnt;
        this.tsetScore = tsetScore;
        this.rank = rank;
    }

    // Getter
    public String getScoreId() {
        return scoreId;
    }

    public String getStudentId() { return studentId; }

    public String getSubjectName() { return subjectName; }

    public int getTestCnt() { return testCnt; }

    public int getTsetScore() { return tsetScore; }

    public String getRank() { return rank; }

    public void setSubjectName(String subjectName1){ this.subjectName = subjectName1; }

}
