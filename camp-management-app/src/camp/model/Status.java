package camp.model;

import java.util.ArrayList;
import java.util.List;

public enum Status {
    GREEN, YELLOW, RED;

    private List<Student> greenStudentList = new ArrayList<>();
    private List<Student> yellowStudentList = new ArrayList<>();
    private List<Student> redStudentList = new ArrayList<>();

    public List<Student> getStudentList() {
        if (this.equals(GREEN)) {
            return greenStudentList;
        } else if (this.equals(YELLOW)) {
            return yellowStudentList;
        } else if (this.equals(RED)) {
            return redStudentList;
        } else {
            System.out.println("잘못된 상태값이 매개변수로 들어왔습니다.");
            return null;
        }
    }

    public void addStudent(Student student) {
        if (this.equals(GREEN)) {
            greenStudentList.add(student);
        } else if (this.equals(YELLOW)) {
            yellowStudentList.add(student);
        } else if (this.equals(RED)) {
            redStudentList.add(student);
        } else {
            System.out.println("잘못된 상태값이 매개변수로 들어왔습니다.");
        }
    }

    public boolean removeStudent(Student student) {
        if (this.equals(GREEN)) {
            return greenStudentList.remove(student);
        } else if (this.equals(YELLOW)) {
            return yellowStudentList.remove(student);
        } else if (this.equals(RED)) {
            return redStudentList.remove(student);
        } else {
            System.out.println("해당 수강생 객체가 존재하지 않습니다.");
            return true;
        }
    }
}
