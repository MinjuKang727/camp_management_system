package camp.model;

public class Score {
    private String scoreId;
    private String studentId;
    private String subjectName;
    private int round;
    private int score;
    private String grade;
    private Status status;


    public Score(String seq, String studentId, String subjectName, int round, int score, String grade) {
        this.scoreId = seq;
        this.studentId = studentId;
        this.subjectName = subjectName;
        this.round = round;
        this.score = score;
        this.grade = grade;
    }

    // Getter
    public String getScoreId() { return scoreId; }

    public String getStudentId() { return studentId; }

    public String getSubjectName() { return subjectName; }

    public int getRound() { return round; }

    public int getTestScore() { return score; }

    public String getGrade() { return grade; }

    // SETTER
    public void setSubjectName(String subjectName){ this.subjectName = subjectName; }

    public void setRound(int round) { this.round = round; }

    public void setScore(int score, String grade){
        this.score = score;

        if (!this.grade.equals(grade)) {
            this.setGrade(grade);
        }
    }
    public void setGrade(String grade) { this.grade = grade; }


}
