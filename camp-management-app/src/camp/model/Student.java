package camp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String studentName;
    private List<String> mandatoryList;
    private List<String> choiceList;
//    private HashMap<String, ArrayList<Subject>> subjectMap;

    public Student(String seq, String studentName, List<String> mandatoryList, List<String> choiceList) {
        this.studentId = seq;
        this.studentName = studentName;
        this.mandatoryList = mandatoryList;
        this.choiceList = choiceList;
//        this.subjectMap = new HashMap<String, ArrayList<Subject>>();
//        this.subjectMap.put("MANDATORY", new ArrayList<>());
//        this.subjectMap.put("CHOICE", new ArrayList<>());
    }

    // Getter
    public String getStudentId() {
        return this.studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public List<String> getMandatoryList(){
        return this.mandatoryList;
    }

    public List<String> getChoiceList(){
        return this.choiceList;
    }


//    public HashMap<String, ArrayList<Subject>> getSubjectMap() { return this.subjectMap; }
//
//    public boolean addSubject(Subject subject) {
//        try {
//            ArrayList<Subject> subjectList = this.subjectMap.get(subject.getSubjectType());
//            subjectList.add(subject);
//        } catch (Exception e) {
//            System.out.println("과목 등록이 실패하였습니다.");
//            return false;
//        }
//
//        return true;
//    }


}
