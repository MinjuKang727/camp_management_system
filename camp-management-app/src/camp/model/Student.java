package camp.model;

import java.util.*;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;
    private Map<String, List<Subject>> subjectMap; // (key, value) = (SUBJECT_TYPE, subjectList)
    private Map<String, Score[]> scoreMap;  // (key, value) = (subjectId, 길이 10의 Score객체 배열)

    public Student(String seq, String studentName) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjectMap = new HashMap<String, List<Subject>>();
        this.subjectMap.put("MANDATORY", new ArrayList<Subject>());
        this.subjectMap.put("CHOICE", new ArrayList<Subject>());
        this.scoreMap = new HashMap<String, Score[]>();
    }

    // Getter
    public String getStudentId() {
        return this.studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public List<Subject> getSubjectList(String subjectType) { return this.subjectMap.get(subjectType); }

    public int getJoinedSJCnt(String subjectType) {
        List<Subject> subjectList = this.subjectMap.get(subjectType);
        return subjectList.size();
    }

    public void addSubject(Subject subject) throws BadInputException {
        List<Subject> subjectList = this.subjectMap.get(subject.getSubjectType());
        if (subjectList.contains(subject)) {
            throw new BadInputException("이미 선택한 과목입니다.", "과목ID를 다시 입력해 주십시오.");
        }

        try {
            subjectList.add(subject);
            scoreMap.put(subject.getSubjectId(), new Score[10]);
        } catch (Exception e) {
            throw new BadInputException("선택한 과목의 수강 신청 실패", "과목을 다시 선택해 주십시오.");
        }
    }

    public boolean addScore(String subjectId, Score score) throws BadInputException {
        if (scoreMap.get(subjectId)[score.getTestCnt() - 1] != null) {
            throw new BadInputException("해당 회차에 이미 점수가 등록되어 있습니다.", "점수를 등록할 회차를 다시 입력해 주시거나 수정 페이지에서 성적을 수정하십시오.");
        } else {
            scoreMap.get(subjectId)[score.getTestCnt() - 1] = score;
            System.out.println(scoreMap.get(subjectId)[score.getTestCnt() - 1].getScoreId());
            return false;
        }
    }

    public Score getScore(String subjectId, int testCnt) throws BadInputException {
        if (!scoreMap.containsKey(subjectId)) {
            throw new BadInputException("수강하고 있지 않은 과목입니다.", "관리할 과목을 다시 선택해 주십시오.");
        }

        Score[] scoreArr = scoreMap.get(subjectId);

        return scoreArr[testCnt - 1];
    }
}
