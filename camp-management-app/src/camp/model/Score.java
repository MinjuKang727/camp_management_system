package camp.model;

public class Score {
    private String scoreId;
    private String studentId;
    private String subjectName;
    private int testCnt;
    private int testScore;
    private String rank;

    public Score(String seq, String studentId, String subjectName, int testCnt, int tsetScore, String rank) {
        this.scoreId = seq;
        this.studentId = studentId;
        this.subjectName = subjectName;
        this.testCnt = testCnt;
        this.testScore = tsetScore;
        this.rank = rank;

        System.out.println("\n성적 등록 성공!");
    }

    // Getter
    public String getScoreId() {
        return scoreId;
    }

    public String getStudentId() { return studentId; }

    public String getSubjectName() { return subjectName; }

    public int getTestCnt() { return testCnt; }

    public int getTestScore() { return testScore; }

    public String getRank() { return rank; }

    public void updateScore(int testScore, String rank) {
        this.testScore = testScore;
        this.rank = rank;
        System.out.println("\n점수 등록 성공!");
    }

}
