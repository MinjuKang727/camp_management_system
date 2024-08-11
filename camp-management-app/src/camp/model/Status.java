package camp.model;

import java.util.ArrayList;
import java.util.List;

public enum Status {
    GREEN, YELLOW, RED;

    private List<Student> greenStudentList = new ArrayList<>();
    private List<Student> yellowStudentList = new ArrayList<>();
    private List<Student> redStudentList = new ArrayList<>();

    public List<Student> getStudentList() {
        return switch (this) {
            case GREEN -> greenStudentList;
            case YELLOW -> yellowStudentList;
            case RED -> redStudentList;
        };
    }

    public void addStudent(Student student) {
        switch (this) {
            case GREEN -> greenStudentList.add(student);
            case YELLOW -> yellowStudentList.add(student);
            case RED -> redStudentList.add(student);
        }
    }

    public void removeStudent(Student student) {
        switch (this) {
            case GREEN -> greenStudentList.remove(student);
            case YELLOW ->yellowStudentList.remove(student);
            case RED ->redStudentList.remove(student);
        }
    }
}
