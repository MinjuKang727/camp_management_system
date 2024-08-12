package camp.model;

import camp.Exception.BadInputException;
import camp.Exception.NotExistException;

import java.util.*;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;
    private Map<String, List<Subject>> subjectMap; // (key, value) = (SUBJECT_TYPE, subjectList), 과목 타입별 과목을 담을 Map
    private Map<String, List<Score>> scoreMap;  // (key, value) = (subjectId, 길이 10의 Score객체 배열), 과목별 성적을 담을 Map
    private Status status;

    public Student(String seq, String studentName) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjectMap = Map.of(
                DataBase.SUBJECT_TYPE_MANDATORY, new ArrayList<Subject>(),
                DataBase.SUBJECT_TYPE_CHOICE, new ArrayList<Subject>()
        );
        this.scoreMap = new HashMap<String, List<Score>>();
    }

    // Getter
    public String getStudentId() {
        return this.studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public List<Subject> getSubjectList(String subjectType) {
        return this.subjectMap.get(subjectType);
    }

    public int getSubjectCnt(String subjectType) {
        List<Subject> subjectList = this.subjectMap.get(subjectType);
        return subjectList.size();
    }

    public int getLastRound(String subjectId) {
        List<Score> scoreList = this.scoreMap.get(subjectId);
        return scoreList.size();
    }

        public List<Score> getScoreList(String subjectId) throws NotExistException {
        List<Score> scoreList = this.scoreMap.get(subjectId);

        if (scoreList.isEmpty()) {
            throw new NotExistException("해당 과목에 등록된 점수");
        }

        return scoreList;
    }

    public Score getScore(String subjectId, int round) {
        List<Score> scoreList = this.scoreMap.get(subjectId);
        return scoreList.get(round - 1);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjectList = new ArrayList<>();
        subjectList.addAll(this.subjectMap.get(DataBase.SUBJECT_TYPE_MANDATORY));
        subjectList.addAll(this.subjectMap.get(DataBase.SUBJECT_TYPE_CHOICE));
        return subjectList;
    }

    public Status getStatus() {
        return this.status;
    }

    // Add
    public void addSubject(Subject subject) throws BadInputException {
        List<Subject> subjectList = this.subjectMap.get(subject.getSubjectType());
        if (subjectList.contains(subject)) {
            throw new BadInputException("이미 선택한 과목입니다.", "과목 고유 번호 를 다시 입력해 주십시오.");
        }

        try {
            String subjectId = subject.getSubjectId();
            subjectList.add(subject);
            Collections.sort(subjectList, Comparator.comparing(Subject::getSubjectId));

            this.scoreMap.put(subjectId, new ArrayList<>(10));
        } catch (Exception e) {
            throw new BadInputException("선택한 과목의 수강 신청 실패", "과목을 다시 선택해 주십시오.");
        }
    }

    public void addScore(String subjectId, Score score) {
        List<Score> scoreList = this.scoreMap.get(subjectId);
        scoreList.add(score);
    }

    // SETTER
    public void setStatus(Status status) { this.status = status; }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}