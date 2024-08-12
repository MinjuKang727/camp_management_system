package camp.model;

import camp.Exception.NotExistException;

import java.util.*;

public class DataBase {
    // 데이터 저장소 리스트
    private List<Student> studentStore;
    private List<Subject> subjectStore;
//    private List<Score> scoreStore;

    // 데이터 저장소 Map
    private Map<String, Student> studentMap;  // (key, value) = (studentId, Student)
    private Map<String, Map<String, Subject>>  subjectMap;  // {key, (key, value)} = {subjectType, (subjectId, Subject)}

    // 과목 타입
    public static final String SUBJECT_TYPE_MANDATORY = "필수";
    public static final String SUBJECT_TYPE_CHOICE = "선택";
    public static final String SUBJECT_TYPE_ALL = "전체";

    // 과목 수
    private int SUBJECT_CNT_MANDATORY;  // 전체 필수 과목 수
    private int SUBJECT_CNT_CHOICE;  // 전체 선택 과목 수

    // 최소 신청 과목 수
    private final int SUBJECT_MIN_MANDATORY;  // 필수 과목 최소 수강 신청 수
    private final int SUBJECT_MIN_CHOICE;  // 선택 과목 최소 수강 신청 수

    // index 관리 필드
    private int studentIndex;
    public static final String INDEX_TYPE_STUDENT = "ST";
    private int subjectIndex;
    public static final String INDEX_TYPE_SUBJECT = "SU";
    private int scoreIndex;
    public static final String INDEX_TYPE_SCORE = "SC";

    // 생성자
    public DataBase() {
        this.SUBJECT_MIN_MANDATORY = 3;
        this.SUBJECT_MIN_CHOICE = 2;

        this.setInitData();
    }

    public DataBase(int SUBJECT_MIN_MANDATORY, int SUBJECT_MIN_CHOICE) {
        this.SUBJECT_MIN_MANDATORY = SUBJECT_MIN_MANDATORY;
        this.SUBJECT_MIN_CHOICE = SUBJECT_MIN_CHOICE;

        this.setInitData();
    }

    // 초기 데이터 생성
    private void setInitData() {
        this.studentStore = new ArrayList<>();
        this.subjectStore = List.of(
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Java",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "객체지향",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "JPA",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MySQL",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "디자인 패턴",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring Security",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Redis",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MongoDB",
                        SUBJECT_TYPE_CHOICE
                )
        );
//        this.scoreStore = new ArrayList<>();

        this.studentMap = new HashMap<>();
        this.subjectMap = Map.of(
                SUBJECT_TYPE_MANDATORY, new HashMap<String, Subject>(),
                SUBJECT_TYPE_CHOICE, new HashMap<String, Subject>()
        );

        for (Subject subject : this.subjectStore) {
            String subjectType = subject.getSubjectType();
            String subjectId = subject.getSubjectId();
            Map<String, Subject> subjectMap2 = this.subjectMap.get(subjectType);
            subjectMap2.put(subjectId, subject);
        }

        this.SUBJECT_CNT_MANDATORY = this.subjectMap.get(SUBJECT_TYPE_MANDATORY).size();
        this.SUBJECT_CNT_CHOICE = this.subjectMap.get(SUBJECT_TYPE_CHOICE).size();
    }

    // index 자동 증가
    public String sequence(String type) {
        StringBuilder sb = new StringBuilder();

        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                this.studentIndex++;
                sb.append(INDEX_TYPE_STUDENT);
                sb.append(this.studentIndex);

                return sb.toString();
            }
            case INDEX_TYPE_SUBJECT -> {
                this.subjectIndex++;
                sb.append(INDEX_TYPE_SUBJECT);
                sb.append(this.subjectIndex);

                return sb.toString();
            }
            default -> {
                this.scoreIndex++;
                sb.append(INDEX_TYPE_SCORE);
                sb.append(this.scoreIndex);

                return sb.toString();
            }
        }
    }


    // GETTER
    // Get Student by studentId
    public Student getStudentById(String studentId) throws NotExistException {
        if (this.studentMap.containsKey(studentId)) {
            return this.studentMap.get(studentId);
        }

        throw new NotExistException("해당 수강생 고유 번호", "수강생 목록 조회의 수강생 고유 번호를 참고하여 입력해 주십시오.");
    }

    // Get Subject by SubjectId
    public Subject getSubjectById(String subjectType, String subjectId) throws NotExistException {
        Map<String, Subject> sm;

        if (subjectType.equals(SUBJECT_TYPE_CHOICE)) {
            sm = this.subjectMap.get(SUBJECT_TYPE_CHOICE);
        } else {
            sm = this.subjectMap.get(SUBJECT_TYPE_MANDATORY);
        }

        if (sm.containsKey(subjectId)) {
            return sm.get(subjectId);
        } else if (subjectType.equals(SUBJECT_TYPE_ALL)) {
            sm = this.subjectMap.get(SUBJECT_TYPE_CHOICE);

            if (sm.containsKey(subjectId)) {
                return sm.get(subjectId);
            }
        }

        throw new NotExistException("해당 과목 고유 번호");
    }

    /**
     * 과목 분류(SUBJECT_TYPE: 필수/선택)를 매개변수로 받아 과목 객체 리스트 반환
     * @param subjectType : 과목 분류(필수/선택)
     * @return 해당 분류의 과목 객체 리스트
     */
    public List<Subject> getSubjectListByType(String subjectType) {
        List<Subject> subjectList = new ArrayList<>(this.subjectMap.get(subjectType).values());
        Collections.sort(subjectList, Comparator.comparing(Subject::getSubjectId));

        return subjectList;
    }

    /**
     * studentStore 비어있는지 확인 메서드 (CheckValidity에서 등록된 수강생이 존재하는지 유효성 검사를 위해 사용)
     * @return true : studentStore에 등록된 수강생 없음,
     *         false : studentStore에 등록된 수강생 존재
     */
    public boolean isEmptyStudentStore() { return this.studentStore.isEmpty(); }

    public java.util.List<Student> getStudentStore() {
        return studentStore;
    }

    public int getSujectMinMandatory() { return this.SUBJECT_MIN_MANDATORY; }

    public int getSubjectMinChoice() { return this.SUBJECT_MIN_CHOICE; }

    public int getSubjectCntMandatory() { return this.SUBJECT_CNT_MANDATORY; }

    public int getSubjectCntChoice() { return this.SUBJECT_CNT_CHOICE; }

    // SETTER
    public void addStudent(Student student) {
        this.studentStore.add(student);
        this.studentMap.put(student.getStudentId(), student);
    }
    public void removeStudent(Student student) {
        this.studentMap.remove(student.getStudentId());
        this.studentStore.remove(student);
    }
}
