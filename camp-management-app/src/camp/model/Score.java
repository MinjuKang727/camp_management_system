package camp.model;

public class Score {
    private String scoreId;
    private String studentId;
    private String subjectName;
    private int round;
    private int testScore;
    private String grade;


    public Score(String seq, String studentId, String subjectName, int round, int testScore, String grade) {
        this.scoreId = seq;
        this.studentId = studentId;
        this.subjectName = subjectName;
        this.round = round;
        this.testScore = testScore;
        this.grade = grade;
    }

    // Getter
    public String getScoreId() { return this.scoreId; }

    public String getStudentId() { return this.studentId; }

    public String getSubjectName() { return this.subjectName; }

    public int getRound() { return this.round; }

    public int getTestScore() { return this.testScore; }

    public String getGrade() { return this.grade; }

    // SETTER
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public void setRound(int round) { this.round = round; }

    public void setTestScore(int testScore) { this.testScore = testScore; }

    public void setScore(int testScore, String grade) {
        this.testScore = testScore;
        this.grade = grade;
    }

    public void setGrade(String grade) { this.grade = grade; }


}
