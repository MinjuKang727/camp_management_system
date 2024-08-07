package camp.model;

import java.util.*;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;
    private Map<String, List<Subject>> subjectMap; // (key, value) = (SUBJECT_TYPE, subjectList), 과목 타입별 과목을 담을 Map
    private Map<String, List<Score>> scoreMap;  // (key, value) = (subjectId, 길이 10의 Score객체 배열), 과목별 성적을 담을 Map
    private Status status;

    // 점수 받는걸 넣으면 이거 하나만 쓰면 돼요
    // 리스트로 하면은 리스트가 2개 필요하죠 (필수랑 선택)
    // map (key / value) key: 필수 5개랑 선택 4개  value: 점수
    // Map<String(subjectName),Integer(estScore)> 이름 = new (Linked)HashMap<>(); // 입력된 순서대로 출력 가능하니까

    public Student(String seq, String studentName) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjectMap = Map.of(
                "필수", new ArrayList<Subject>(5),
                "선택", new ArrayList<Subject>(4)
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
        int subjectCnt = subjectList.size();
        return subjectCnt;
    }

    public int getScoreCnt(String subjectId) {
        List<Score> scoreList = this.scoreMap.get(subjectId);
        return scoreList.size();
    }

    public List<Score> getScoreList(String subjectId) throws BadInputException {
        List<Score> scoreList = this.scoreMap.get(subjectId);

        if (scoreList.isEmpty()) {
            throw new BadInputException("해당 과목에 등록된 점수");
        }

        return scoreList;
    }

    public Score getScore(String subjectId, int testCnt){
        List<Score> scoreList = scoreMap.get(subjectId);
        return scoreList.get(testCnt - 1);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> allSubjects = new ArrayList<>();
        allSubjects.addAll(subjectMap.get("필수"));
        allSubjects.addAll(subjectMap.get("선택"));
        return allSubjects;
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