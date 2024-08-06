package camp.model;

import java.util.*;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;
    private Map<String, List<Subject>> subjectMap; // (key, value) = (SUBJECT_TYPE, subjectList)
    private Map<String, List<Score>> scoreMap;  // (key, value) = (subjectId, 길이 10의 Score객체 배열)

    public Student(String seq, String studentName) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjectMap = new HashMap<String, List<Subject>>();
        this.subjectMap.put("MANDATORY", new ArrayList<Subject>());
        this.subjectMap.put("CHOICE", new ArrayList<Subject>());
        this.scoreMap = new HashMap<String, List<Score>>();
    }

    // Getter
    public String getStudentId() {
        return this.studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public List<Subject> getSubjectList(String subjectType) { return this.subjectMap.get(subjectType); }

    public int getSubjectCnt(String subjectType) {
        List<Subject> subjectList = this.subjectMap.get(subjectType);
        int subjectCnt = subjectList.size();
        return subjectCnt;
    }

    public int getScoreCnt(String subjectId) {
        List<Score> scoreList = this.scoreMap.get(subjectId);
        return scoreList.size();
    }

    public void addSubject(Subject subject) throws BadInputException {
        List<Subject> subjectList = this.subjectMap.get(subject.getSubjectType());
        if (subjectList.contains(subject)) {
            throw new BadInputException("이미 선택한 과목입니다.", "과목ID를 다시 입력해 주십시오.");
        }

        try {
            String subjectId = subject.getSubjectId();

            subjectList.add(subject);
            scoreMap.put(subjectId, new ArrayList<>());
        } catch (Exception e) {
            throw new BadInputException("선택한 과목의 수강 신청 실패", "과목을 다시 선택해 주십시오.");
        }
    }

    public void addScore(String subjectId, Score score) {
        List<Score> scoreList = scoreMap.get(subjectId);
        scoreList.add(score);
    }

    public Score getScore(String subjectId, int testCnt) throws BadInputException {
        if (!scoreMap.containsKey(subjectId)) {
            throw new BadInputException("수강하고 있지 않은 과목입니다.", "관리할 과목을 다시 선택해 주십시오.");
        }

        List<Score> scoreList = scoreMap.get(subjectId);

        if (scoreList.isEmpty() || scoreList.size() < testCnt) {
            throw new BadInputException("해당 회차의 점수가 등록되어 있지 않습니다.", "다른 회차를 입력해 주십시오.");
        }

        return scoreList.get(testCnt - 1);
    }
}