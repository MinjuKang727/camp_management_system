package camp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

public class Student {
    private String studentId;
    private String studentName;
    private HashMap<String, ArrayList<Subject>> subjectMap;

    public Student(String seq, String studentName) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjectMap = new HashMap<String, ArrayList<Subject>>();
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

    public HashMap<String, ArrayList<Subject>> getSubjectMap() { return this.subjectMap; }

    public boolean addSubject(Subject subject) {
        try {
            ArrayList<Subject> subjectList = this.subjectMap.get(subject.getSubjectType());
            subjectList.add(subject);
        } catch (Exception e) {
            System.out.println("과목 등록이 실패하였습니다.");
            return false;
        }

        return true;
    }
}
