package camp.model;

import java.util.*;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;
    private Status status;
    private Map<String, List<Subject>> subjectMap;

    public Student(String seq, String studentName) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjectMap = new HashMap<String, List<Subject>>();
        this.subjectMap.put("MANDATORY", new ArrayList<Subject>());
        this.subjectMap.put("CHOICE", new ArrayList<Subject>());
    }

    // Getter
    public String getStudentId() {
        return this.studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public List<Subject> getSubjectList(String subjectType) { return this.subjectMap.get(subjectType); }

    public int getSignUpSJCnt(String subjectType) {
        List<Subject> subjectList = this.subjectMap.get(subjectType);
        return subjectList.size();
    }

    public boolean addSubject(Subject subject) {
        try {
            List<Subject> subjectList = this.subjectMap.get(subject.getSubjectType());
            if (subjectList.contains(subject)) {
                System.out.println("이미 선택한 과목입니다.");
                return false;
            }

            subjectList.add(subject);
            subjectMap.put(subject.getSubjectType(), subjectList);
        } catch (Exception e) {
            System.out.println("알 수 없는 과목을 선택하였습니다.");
            return false;
        }

        return true;
    }

    public List<Subject> getSubjects() {
        List<Subject> allSubjects = new ArrayList<>();
        allSubjects.addAll(subjectMap.get("MANDATORY"));
        allSubjects.addAll(subjectMap.get("CHOICE"));
        return allSubjects;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}