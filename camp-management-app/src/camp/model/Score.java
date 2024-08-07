package camp.model;

public class Score {
    private String scoreId;
    private String studentId;
    private String subjectName;
    private int testCnt;
    private int testScore;
    private String rank;
    private Status status;


    public Score(String seq, String studentId, String subjectName, int testCnt, int testScore, String rank) {
        this.scoreId = seq;
        this.studentId = studentId;
        this.subjectName = subjectName;
        this.testCnt = testCnt;
        this.testScore = testScore;
        this.rank = rank;
    }

    // Getter
    public String getScoreId() { return scoreId; }

    public String getStudentId() { return studentId; }

    public String getSubjectName() { return subjectName; }

    public int getTestCnt() { return testCnt; }

    public int getTestScore() { return testScore; }

    public String getRank() { return rank; }

    // SETTER
    public void setSubjectName(String subjectName){ this.subjectName = subjectName; }

    public void setTestCnt(int testCnt) { this.testCnt = testCnt; }

    public void setTestScore(int testScore, String rank){
        this.testScore = testScore;

        if (!this.rank.equals(rank)) {
            this.setRank(rank);
        }
    }

    public void setRank(String rank) { this.rank = rank; }
}
