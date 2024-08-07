package camp;

import java.util.*;
import java.util.regex.Pattern;

import camp.model.*;


/**
 * Notification
 * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
 * main 메서드를 실행하면 프로그램이 실행됩니다.
 * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
 * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
 * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
 */
public class CampManagementApplication {
    // 데이터 저장소
    private List<Student> studentStore;
    private List<Subject> subjectStore;
    private List<Score> scoreStore;

    private Map<String, Student>  studentMap;  // (key, value) = (studentId, Student)
    private Map<String, Map<String, Subject>>  subjectMap;  // {key, (key, value)} = {subjectType, (subjectId, Subject)}

    // 과목 타입
    private static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static final String SUBJECT_TYPE_CHOICE = "CHOICE";
    private int SUBJECT_CNT_MANDATORY = 5;  // 전체 필수 과목 수
    private int SUBJECT_CNT_CHOICE = 4;  // 전체 선택 과목 수
    private final int SUBJECT_MIN_MANDATORY = 3;  // 필수 과목 최소 수강 신청 수
    private final int SUBJECT_MIN_CHOICE = 2;  // 선택 과목 최소 수강 신청 수

    // index 관리 필드
    private int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private int subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private int scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";

    private CheckValidity ck;  // 입력값 유효성 검사를 위한 CheckValidity 객체
    private Scanner sc;

    public static void main(String[] args) {
        CampManagementApplication cma = new CampManagementApplication();
    }

    // 생성자
    public CampManagementApplication() {
        ck = new CheckValidity();
        sc = new Scanner(System.in);

        this.setInitData();
        try {
            this.displayMainView();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            System.out.println("오류 발생!\n프로그램을 종료합니다.");
        }
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
        this.scoreStore = new ArrayList<>();

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
    }

    // index 자동 증가
    private String sequence(String type) {
        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                this.studentIndex++;
                return INDEX_TYPE_STUDENT + this.studentIndex;
            }
            case INDEX_TYPE_SUBJECT -> {
                this.subjectIndex++;
                return INDEX_TYPE_SUBJECT + this.subjectIndex;
            }
            default -> {
                this.scoreIndex++;
                return INDEX_TYPE_SCORE + this.scoreIndex;
            }
        }
    }

    private void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            boolean noStudent = studentStore.isEmpty();
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...\n");
            System.out.println("1. 수강생 관리");
            System.out.printf("2. 점수 관리%s\n", noStudent ? " (비활성)" : "");
            System.out.println("3. 프로그램 종료");
            int input = this.enterType("\n관리 항목을 선택해 주십시오.", 1, 3, 0);

            if (input == 0) {
                continue;
            }

            switch (input) {
                case 1 -> this.displayStudentView(); // 수강생 관리
                case 2 ->  {  // 점수 관리
                    try {
                        this.ck.notEmptyStudentStore(this);
                        this.displayScoreView();
                    } catch (BadInputException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 3 -> flag = false; // 프로그램 종료
            }
        }

        System.out.println("프로그램을 종료합니다.");
    }

    private void displayStudentView() {
        boolean flag = true;


        while (flag) {
            boolean noStudent = studentStore.isEmpty();

            System.out.println("\n==================================");
            System.out.println("수강생 관리 실행 중...\n");
            System.out.println("1. 수강생 등록");
            System.out.printf("2. 수강생 목록 조회%s\n", (noStudent) ? " (비활성)" : "");
            System.out.printf("3. 수강생 정보 조회%s\n", (noStudent) ? " (비활성)" : "");
            System.out.printf("4. 수강생 정보 수정%s\n", (noStudent) ? " (비활성)" : "");
            System.out.printf("5. 상태별 수강생 목록 조회%s\n", (noStudent) ? " (비활성)" : "");
            System.out.printf("6. 수강생 삭제%s\n", (noStudent) ? " (비활성)" : "");
            System.out.println("7. 메인 화면 이동");
            int input = 0;

            try {
                input = Integer.parseInt(this.enterType("\n관리 항목을 선택해 주십시오."));
                this.ck.selecterRange(1, 7, input);
                if (input != 1 && input != 7) {
                    this.ck.notEmptyStudentStore(this);
                }
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println("1 이상 7이하의 정수를 입력해 주십시오.");
                continue;
            }

            switch (input) {
                case 1 -> this.createStudent(); // 수강생 등록
                case 2 -> this.inquireStudent(); // 수강생 목록 조회
                case 3 -> this.viewStudentDetails(); // 수강생 정보 조회
                case 4 -> this.modifyStudent(); // 수강생 정보 수정
                case 5 -> this.viewStudentsByStatus(); // 상태별 수강생 목록 조회
                case 6 -> this.deleteStudent(); // 수강생 삭제
                case 7 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }

        }
    }

    // Getter
    // Get Student by studentId
    private Student getStudentById(String studentId) throws BadInputException{
        if (this.studentMap.containsKey(studentId)) {
            return this.studentMap.get(studentId);
        }

        throw new BadInputException("존재하지 않는 수강생ID입니다.", "수강생 목록 조회의 수강생ID를 참고하여 수강생ID를 입력해 주세요.");
    }

    // Get Subject by SubjectId
    private Subject getSubjectById(String subjectType, String subjectId) throws BadInputException {
        Map<String, Subject> sm;

        if (subjectType.equals(SUBJECT_TYPE_CHOICE)) {
            sm = this.subjectMap.get(SUBJECT_TYPE_CHOICE);
        } else {
            sm = this.subjectMap.get(SUBJECT_TYPE_MANDATORY);
        }

        if (sm.containsKey(subjectId)) {
            return sm.get(subjectId);
        } else if (subjectType.equals("ALL")) {
            sm = this.subjectMap.get(SUBJECT_TYPE_CHOICE);

            if (sm.containsKey(subjectId)) {
                return sm.get(subjectId);
            }
        }

        throw new BadInputException();
    }

    // Get subjectList by subject type
    private List<Subject> getSubjectListByType(String subjectType) {
        List<Subject> subjectList = new ArrayList<>();

        for (Subject subject : this.subjectStore) {
            if (subject.getSubjectType().equals(subjectType)) {
                subjectList.add(subject);
            }
        }

        return subjectList;
    }

    // Get store by index_type
    public List<Student> getStudentStore() { return this.studentStore; }

    // 수강생 목록 출력
    private void printStudentList(List<Student> studentList) throws BadInputException {
        if (studentList == null || studentList.isEmpty()) {
            throw new BadInputException("해당 상태의 수강생");
        }

        for (Student student : studentList) {
            System.out.printf("%s. %s\n", student.getStudentId(), student.getStudentName());
        }
        System.out.printf("[ 총 %s명의 수강생이 조회되었습니다. ] \n", studentList.size());
    }

    // 과목 목록 출력
    // 과목 목록만 출력
    private void printSubjectList(List<Subject> subjectList) {
        for (Subject subject : subjectList) {
            System.out.printf("%s. %s    ", subject.getSubjectId(), subject.getSubjectName());
        }
        System.out.println("\n");
    }

    // 매개변수로 준 수강생의 해당 과목 점수 등록 회차도 함께 출력
    private void printSubjectList(Student student, List<Subject> subjectList) {
        for (Subject subject : subjectList) {
            int testCnt = student.getScoreCnt(subject.getSubjectId());

            System.out.printf("%s. %s (현재 %d회차까지 점수 등록)\n", subject.getSubjectId(), subject.getSubjectName(), testCnt);
        }
        System.out.println("\n");
    }

    // 수강 중이지 않은 과목만 출력
    private void printSubjectList(List<Subject> subjectList, List<Subject> joinedSubject) {
         for (Subject subject : subjectList) {
            if (!joinedSubject.contains(subject)) {
                System.out.printf("%s. %s    ", subject.getSubjectId(), subject.getSubjectName());
            }
        }
        System.out.println("\n");
    }

    // 수강생의 신청 과목 추가 메서드
    private void joinSubject(String subjectType, Student student, List<Subject> subjectList) {
        boolean flag = true;  // 과목 선택을 더 할 지 여부를 담을 boolean 변수
        String typeKor = "";
        int minJoin = 0;
        int totalCnt = 0;

        switch (subjectType) {
            case SUBJECT_TYPE_MANDATORY :
                minJoin = this.SUBJECT_MIN_MANDATORY;
                typeKor = "필수";
                totalCnt = this.SUBJECT_CNT_MANDATORY;
                break;
            case SUBJECT_TYPE_CHOICE :
                minJoin = this.SUBJECT_MIN_CHOICE;
                typeKor = "선택";
                totalCnt = this.SUBJECT_CNT_CHOICE;
                break;
            default :
        }

        while (flag) {
            System.out.println("\n----------------------------------");
            System.out.printf("수강 %s 과목 등록 중...\n", typeKor);
            List<Subject> joinedSubject = student.getSubjectList(subjectType);
            Subject subject = this.inSubjectId(typeKor, subjectType, subjectList, joinedSubject);

            try {
                student.addSubject(subject);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            try {
                // 필수(or 선택) 과목 추가 신청 여부 결정하는 코드
                flag = this.ck.satisfySubjectCnt(student, subjectType, minJoin, totalCnt);

                if (flag) {
                    String more = this.enterType("\n" + typeKor + " 과목 수강 신청을 더 하시겠습니까? (더 수강 신청 more 입력)");
                    flag = more.equals("more");
                }
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 수강생 등록
    private void createStudent() {
        System.out.println("\n==================================");
        System.out.println("수강생 등록 실행 중...\n");
        // 수강생 이름 입력
        String studentName = this.inStudentName();
        // 수강생 객체 생성
        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName); // 수강생 인스턴스 생성 예시 코드

        List<Subject> subjectList = this.getSubjectListByType(SUBJECT_TYPE_MANDATORY);  // 필수 과목 리스트
        // 필수 과목 수강 신청
        this.joinSubject(SUBJECT_TYPE_MANDATORY, student, subjectList);

        subjectList = this.getSubjectListByType(SUBJECT_TYPE_CHOICE);  // 선택 과목 리스트
        // 선택 과목 수강 신청
        this.joinSubject(SUBJECT_TYPE_CHOICE, student, subjectList);

        // 상태 입력
        Status status = this.inStatus();
        student.setStatus(status);
        status.addStudent(student);

        // 수강생 등록 확인
        System.out.println("새로운 수강생이 등록되었습니다.");
        this.viewStudentDetails(student);

        // 저장소에 저장
        this.studentStore.add(student);
        this.studentMap.put(student.getStudentId(), student);
    }


    // 수강생 목록 조회
    private void inquireStudent() {
        System.out.println("\n==================================");
        System.out.println("수강생 목록 조회 실행 중...\n");

        for (Student student : this.studentStore) {
            String studentId = student.getStudentId();
            String studentName = student.getStudentName();
            System.out.printf("%s. %s\n", studentId, studentName);
        }
        System.out.printf("\n[ 총 %d명의 수강생이 조회되었습니다. ]\n", this.studentStore.size());
    }

    private void displayScoreView() {
        boolean flag = true;

        while (flag) {
            System.out.println("\n==================================");
            System.out.println("점수 관리 실행 중...\n");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            int input = 0;

            try {
                input = Integer.parseInt(this.enterType("\n관리 항목을 선택해 주십시오."));
                this.ck.selecterRange(1, 4, input);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            switch (input) {
                case 1 -> this.createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> this.updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> this.inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
            }
        }
    }

    // 정수형 입력값 받는 메서드
    // 매개변수로 출력 내용, 선택 범위min, max, 예외발생시, 반환값을 줌.
    private int enterType(String msg, int min, int max, int exception) {
        System.out.println(msg);
        int input = exception;

        try {
            input = Integer.parseInt(sc.nextLine());
            this.ck.selecterRange(min, max, input);
        } catch (BadInputException e) {
            System.out.println(e.getMessage());
        }

        return input;
    }

    // 매개변수로 받은 문자열 기준으로 안내메세지 출력하고 입력값 받는 메서드
    private String enterType(String type) {
        return switch (type) {
            case INDEX_TYPE_STUDENT -> {
                System.out.println("수강생ID를 입력하십시오... (ST를 제외한 뒤의 번호만 입력)");
                yield String.format("%s%s", INDEX_TYPE_STUDENT, sc.nextLine());
            }
            case INDEX_TYPE_SUBJECT -> {
                System.out.println("과목ID를 입력하십시오...(SU를 제외한 뒤의 번호만 입력)");
                yield String.format("%s%s", INDEX_TYPE_SUBJECT, sc.nextLine());
            }
            case INDEX_TYPE_SCORE -> {
                System.out.println("점수ID를 입력하십시오...");
                yield String.format("%s%s", INDEX_TYPE_SCORE, sc.nextLine());
            }
            default -> {
                System.out.println(type);
                yield sc.nextLine();
            }
        };
    }

    // 수강생 이름 입력 > 수강생 이름 문자열 반환
    private String inStudentName() {
        while (true) {
            String studentName = this.enterType("수강생의 이름을 입력하십시오.");

            try {
                this.ck.nameIsEngOrKor(studentName);
                return studentName;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 수강생 이름 입력 >> 기존에 등록된 이름과 다른 수강생 이름 문자열 반환
    private String inStudentName(String preName) {
        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("수강생 이름 수정 중...\n");
            System.out.printf("현재 수강생 이름 : %s\n", preName);
            String newName = this.inStudentName();

            try {
                this.ck.notSameName(newName, preName);
                return newName;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }

            String exit = this.enterType("수강생 이름 수정을 종료하시겠습니까? (종료 : exit 입력)");
            if (exit.equals("exit")) {
                return "ex1t";
            }
        }
    }

    // 수강생ID 입력  >> 수강생 객체 반환
    private Student inStudentId() {
        while (true) {
            try {
                System.out.println("----------------------------------");
                String studentId = this.enterType(INDEX_TYPE_STUDENT);
                Student student = this.getStudentById(studentId);
                return student;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 삭제할 수강생ID 입력
    private Student inRemoveStudentId() {
        while (true) {
            try {
                System.out.println("----------------------------------");
                String studentId = this.enterType(INDEX_TYPE_STUDENT);
                Student student = this.getStudentById(studentId);
                return student;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                String exit = this.enterType("\n수강생 삭제를 종료하시겠습니까? (종료 : exit 입력)\n");

                if (exit.equals("exit")) {
                    return null;
                }
            }
        }
    }

    // 수강생 상태 입력
    private Status inStatus() {
        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("수강생 상태 등록 중...\n");
            String strStatus = this.enterType("수강생 상태를 입력해 주십시오. (상태 종류 : GREEN, YELLOW, RED)");

            try {
                Status status = this.ck.statusInGYR(strStatus);
                return status;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 수강생 상태 입력 >> 기존 상태와 비교하여 새로운 상태 반환 혹은 null 반환
    private Status inStatus(Status preStatus) {
        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("수강생 상태 수정 중...\n");
            System.out.printf("현재 수강생 상태 : %s\n", preStatus.toString());
            String status = this.enterType("수정할 상태를 입력하세요.");

            try {
                Status newStatus = this.ck.statusInGYR(status);
                this.ck.notSameStatus(newStatus, preStatus);
                return newStatus;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }

            String exit = this.enterType("수강생 상태 수정을 종료하시겠습니까? (종료 : exit 입력)");
            if (exit.equals("exit")) {
                return null;
            }
        }
    }

    // 과목 분류 입력  >> 과목 타입 문자열 반환
    // type != 1 (default) : 필수/ 선택 과목만 선택
    // type == 1 : + 전체 과목까지 선택
    private String inSubjectType(int type) {
        while(true) {
            System.out.println("\n----------------------------------");
            System.out.println("과목 분류 선택 중...\n");
            int input = 0;

            if (type == 1) {
                System.out.println("1. 필수 과목    2. 선택 과목    3. 전체 과목");
                input = this.enterType("\n과목 분류 선택해 주십시오.", 1, 3, 0);
            } else if (type == 0) {
                System.out.println("1. 필수 과목    2. 선택 과목");
                input = this.enterType("\n과목 분류 선택해 주십시오.", 1, 2, 0);
            }

            if (input == 0) {
                continue;
            }

            switch (input) {
                case 1 :
                    return SUBJECT_TYPE_MANDATORY;
                case 2 :
                    return SUBJECT_TYPE_CHOICE;
                case 3 :
                    if (type == 1) {
                        return "ALL";
                    }
                    break;
                default :
            };
        }
    }

    // 과목 아이디 입력 >> 과목 객체 반환
    // 수강 중이지 않은 과목 선택
    private Subject inSubjectId(String typeKor, String subjectType, List<Subject> subjectList, List<Subject> joinedSubjectList) {
        while (true) {
            System.out.printf("\n[ %s 과목 목록 ]\n", typeKor);
            this.printSubjectList(subjectList, joinedSubjectList);
            String subjectId = this.enterType(INDEX_TYPE_SUBJECT);

            try {
                Subject subject = this.getSubjectById(subjectType, subjectId);
                this.ck.notJoinedSubject(subject, joinedSubjectList);
                return subject;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    // 수강 중인 과목 선택
    private Subject inSubjectId(Student student, String subjectType) {
        List<Subject> subjectList = null;

        if (subjectType.equals("ALL")) {
            subjectList = student.getAllSubjects();
        } else {
            subjectList = student.getSubjectList(subjectType);
        }

        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("과목을 선택 중...\n");
            System.out.println("[ 수강 중인 과목 목록 ]");
            this.printSubjectList(subjectList);
            String subjectId = this.enterType(INDEX_TYPE_SUBJECT);

            try {
                Subject subject = this.getSubjectById(subjectType, subjectId);
                this.ck.isJoinedSubject(subject, subjectList);
                return subject;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("알 수 없는 과목ID를 입력하셨습니다.\n hint : 과목ID('SU0')에서 'SU'를 제외한 뒤의 번호만 입력해 주세요.");
            }

        }
    }

    // 점수 입력
    private int inTestScore(String subjectName, int testCnt) {
        while (true) {
            System.out.println("\n----------------------------------");
            System.out.printf("[ %s ] %d 회차 점수 등록 중...\n", subjectName, testCnt);
            int testScore = this.enterType("점수를 입력해 주십시오.", 0, 100, -1);

            if (testScore == -1) {
                continue;
            }

            return testScore;
        }
    }

    // 수정할 점수 입력 >> int 점수 반환
    private int inTestScore(Score score) {
        String subjectName = score.getSubjectName();
        int testCnt = score.getTestCnt();
        int preScore = score.getTestScore();
        int newScore = -3;
        boolean flag = true;

        while (flag) {
            System.out.println("\n----------------------------------");
            System.out.printf("[ %s ] %d 회차 점수 수정 중...\n", subjectName, testCnt);
            System.out.printf("현재 등록되어 있는 점수 : %d점", preScore);
            newScore = this.enterType("\n점수를 입력해 주십시오.", 0, 100, -3);

            if (newScore == -3) {
                continue;
            }

            try {
                this.ck.notSameScore(newScore, preScore);
                flag = false;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                return this.inEditOrNot();
            }
        }

        return newScore;
    }

    // 회차 입력
    private int inTestCnt(Student student, String subjectId) throws BadInputException {
        int max = student.getScoreCnt(subjectId);

        if (max == 0) {
            throw new BadInputException("해당 과목의 등록된 점수가 존재하지 않습니다.", "점수를 1회차 이상 등록 후, 수정 가능합니다.");
        }

        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("수정 회차 선택 중...\n");
            int testCnt = this.enterType("회차를 입력하십시오. (선택 가능 회차 : 1 ~ " + max + ")", 1, max, 0);

            if (testCnt == 0) {
                continue;
            }

            return testCnt;
        }
    }

    // 선택 항목 출력
    private int inEditOrNot() {
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("[ 실행 가능한 항목 ]");
            System.out.println("1. 현재 수강생의 다른 과목 수정");
            System.out.println("2. 다른 수강생의 성적 수정");
            System.out.println("3. 이전 페이지로 돌아가기");
            int input = this.enterType("실행 할 항목을 선택하십시오.", 1, 3, 0);

            switch (input) {
                case 0 :
                    continue;
                case 1 :
                    return -1;
                case 2 :
                    return -2;
                case 3 :
                    return -3;
            }
        }
    }



    // 수강생의 과목별 시험 회차 및 점수 등록
    private void createScore() {
        System.out.println("\n==================================");
        System.out.println("수강생의 과목별 시험 회차 및 점수 등록 실행 중...\n");

        // 수강생ID 입력
        Student student = this.inStudentId();
        String studentId = student.getStudentId();

        boolean flag = true;

        while (flag) {
            // 과목 분류 선택
            String subjectType = this.inSubjectType(1);

            // 과목 선택
            Subject subject = this.inSubjectId(student, subjectType);
            subjectType = subject.getSubjectType();
            String subjectId = subject.getSubjectId();
            String subjectName = subject.getSubjectName();

            // 회차 유효성 검사
            int testCnt = student.getScoreCnt(subjectId) + 1;

            try {
                ck.testCntOver10(testCnt);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println("이전 페이지로 돌아갑니다.");
                return;
            }

            // 점수 등록
            int testScore = this.inTestScore(subjectName, testCnt);

            // 등급 계산
            String rank = this.ranked(testScore, subjectType);

            // Score 객체 생성
            Score score = new Score(sequence(INDEX_TYPE_SCORE), studentId, subjectName, testCnt, testScore, rank);

            // 점수 재확인
            System.out.printf("%s번 학생의 %s 과목 %d회차 점수가 %d, 랭크가 %s로 등록됩니다.", score.getStudentId(), score.getSubjectName(), score.getTestCnt(), score.getTestScore(), score.getRank());

            // 점수 저장
            student.addScore(subjectId, score);
            this.scoreStore.add(score);

            System.out.println("\n-----------------------------------------------------------------");
            String exit = this.enterType("현재 수강생의 과목별 시험 회차 및 점수 등록을 종료하시겠습니까? (종료 : exit 입력)");
            if (exit.equals("exit")) {
                flag = false;
            }
        }

    }

    // 수강생의 과목별 회차 점수 수정
    private void updateRoundScoreBySubject() {
        while (true) {
        // 수강생 ID 입력
            Student student = this.inStudentId();

            boolean flag = true;

            while (flag) {
                // 과목 분류 선택
                String subjectType = this.inSubjectType(1);
                // 과목 선택
                Subject subject = this.inSubjectId(student, subjectType);
                String subjectId = subject.getSubjectId();
                int testCnt = 0;

                try {
                    testCnt = this.inTestCnt(student, subjectId);
                } catch (BadInputException e) {
                    System.out.println(e.getMessage());
                    int exit = this.inEditOrNot();

                    switch (exit) {
                        case -1:
                            continue;
                        case -2:
                            flag = false;
                            break;
                        default :
                            return;
                    }
                }

                System.out.println("해당 회차의 점수를 가져오는 중...\n");
                Score score = student.getScore(subjectId, testCnt);
                int newScore = this.inTestScore(score);

                switch (newScore) {
                    case -1:
                        continue;
                    case -2:
                        break;
                    case -3 :
                        return;
                    default :
                }

                String newRank = this.ranked(newScore, subjectType);       // 위에서 받은 과목 타입이랑 새로운 점수를 넣어서 새로운 등급을 받는다
                score.setTestScore(newScore, newRank);                     // 새로운 점수 수정

                System.out.printf("%s. %s 수강생의 %s 과목 %d 회차의 점수가 %d, 등급이 %s로 수정되었습니다.\n",
                        score.getStudentId(),
                        student.getStudentName(),
                        score.getSubjectName(),
                        score.getTestCnt(),
                        score.getTestScore(),
                        score.getRank()
                );

                int exit = this.inEditOrNot();

                switch (exit) {
                    case -1:
                        continue;
                    case -2:
                        break;
                    default :
                        return;
                }

            }
        }
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private void inquireRoundGradeBySubject() {
        System.out.println("\n==================================");
        System.out.println("수강생의 특정 과목 회차별 등급 조회 실행 중...\n");
        // 수강생 ID 입력
        Student student = this.inStudentId();

        // 과목 분류 선택
        String subjectType = this.inSubjectType(1);

        // 과목 선택
        Subject subject = this.inSubjectId(student, subjectType);
        String subjectId = subject.getSubjectId();

        // 점수 리스트 가져오기
        List<Score> scoreList = null;

        try {
            scoreList = student.getScoreList(subjectId);
        } catch (BadInputException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("\n----------------------------------");
        System.out.println("회차별 등급을 조회중...\n");
        System.out.printf("%s. %s 수강생의 %s 과목 회차별 등급\n", student.getStudentId(), student.getStudentName(), subject.getSubjectName());
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("| 회차 |   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |   10   |");
        System.out.print("| 등급 |");

        for (int i = 0; i < 10; i++) {
            if (i >= scoreList.size()) {
                System.out.print(" 미등록 |");
                continue;
            }
            Score score = scoreList.get(i);
            String rank = score.getRank();

            System.out.printf("   %s   |", rank);
        }
        System.out.println("\n----------------------------------------------------------------------------------------");
    }

    private String ranked(int score, String subjectType){
        if (subjectType.equals(SUBJECT_TYPE_MANDATORY)) {
            if (score >= 95) {
                return "A";
            } else if (score >= 90) {
                return "B";
            } else if (score >= 80) {
                return "C";
            } else if (score >= 70) {
                return "D";
            } else if (score >= 60) {
                return "F";
            }
        } else if (subjectType.equals(SUBJECT_TYPE_CHOICE)) {
            if (score >= 90) {
                return "A";
            } else if (score >= 80) {
                return "B";
            } else if (score >= 70) {
                return "C";
            } else if (score >= 60) {
                return "D";
            } else if (score >= 50) {
                return "F";
            }
        }

        return "N";
    }

    // 수강생 정보 조회
    private void viewStudentDetails() {
        System.out.println("\n==================================");
        System.out.println("수강생 정보 조회 실행 중...\n");
        // 수강생 ID 입력
        Student student = this.inStudentId();
        viewStudentDetails(student);
    }

    // 매개변수로 받은 수강생 정보 조회
    private void viewStudentDetails(Student student) {
        System.out.println("----------------------------------");
        System.out.println("수강생 정보 조회 결과");
        System.out.printf("\nID: %s\n이름: %s\n상태: %s\n수강 과목:\n",
                student.getStudentId(),
                student.getStudentName(),
                student.getStatus()
        );

        for (Subject subject : student.getAllSubjects()) {
            System.out.printf("- %s\n", subject.getSubjectName());
            try {
                List<Score> scoreList = student.getScoreList(subject.getSubjectId());
                System.out.println("  [회차별 점수(등급)] ");
                for (Score score : scoreList) {
                    System.out.printf(" %d회차 : %d(%s) /", score.getTestCnt(), score.getTestScore(), score.getRank());
                }
                System.out.println();
            } catch (BadInputException ignored) {
            }

        }
    }




    // 수강생 정보 수정
    private void modifyStudent() {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("수강생 정보 수정 실행 중...\n");
            System.out.println("1. 이름");
            System.out.println("2. 상태");
            System.out.println("3. 이전 페이지로 돌아가기");
            int input = 0;

            try {
                input = Integer.parseInt(this.enterType("\n실행할 항목을 선택해 주십시오."));
                this.ck.selecterRange(1, 2, input);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }

            switch (input) {
                case 1 :
                    this.modifyStudentName();
                    break;
                case 2 :
                    this.modifyStudentStatus();
                    break;
                case 3 :
                    flag = false;
                    break;
                default :
                    System.out.println("잘못된 값을 입력하셨습니다. \n이전 페이지로 돌아갑니다.");
                    flag = false;
            }
        }
    }

    // 수강생 이름 수정
    private void modifyStudentName() {
        // 수강생 ID 입력
        Student student = this.inStudentId();
        String preName = student.getStudentName();  // 기존 이름

        // 수정할 이름 입력
        String newName = this.inStudentName(preName);

        if (newName.equals("ex1t")) {
            return;
        }
        student.setStudentName(newName);
        System.out.printf("%s. %s 수강생의 이름이 %s로 변경되었습니다.\n", student.getStudentId(), preName, student.getStudentName());
    }

    // 수강생 상태 수정
    private void modifyStudentStatus() {
        // 수강생 ID 입력
        Student student = this.inStudentId();
        Status preStatus = student.getStatus();  // 기존 상태

        Status newStatus = this.inStatus(preStatus);
        if (newStatus == null) {
            return;
        }

        preStatus.removeStudent(student);
        newStatus.addStudent(student);
        student.setStatus(newStatus);
        System.out.printf("%s. %s 수강생의 상태가 %s로 변경되었습니다.\n", student.getStudentId(), student.getStudentName(), student.getStatus().toString());
    }

    // 상태별 수강생 목록 조회
    private void viewStudentsByStatus() {
        boolean flag = true;
        Status status = null;

        while (flag) {
            System.out.println("\n==================================");
            System.out.println("상태별 수강생 목록 조회 실행 중...\n");
            System.out.println("1. GREEN");
            System.out.println("2. YELLOW");
            System.out.println("3. RED");
            System.out.println("4. 이전 페이지로 돌아가기");
            int input = 0;

            try {
                input = Integer.parseInt(this.enterType("\n조회할 상태를 선택해 주십시오."));
                this.ck.selecterRange(1, 4, input);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }

            switch (input) {
                case 1 :
                    status = Status.GREEN;
                    flag = false;
                    break;
                case 2 :
                    status = Status.YELLOW;
                    flag = false;
                    break;
                case 3 :
                    status = Status.RED;
                    flag = false;
                    break;
                case 4 :
                    return;
                default :
            }
        }

        System.out.println("\n----------------------------------");
        System.out.printf("상태가 %s인 수강생 목록 가져오는 중...\n", status);
        List<Student> studentList = status.getStudentList();
        System.out.printf("\n상태가 %s인 수강생 목록:\n", status);

        try {
            this.printStudentList(studentList);
        } catch (BadInputException e) {
            System.out.println(e.getMessage());
        }
    }

    // 수강생 삭제
    private void deleteStudent() {
        System.out.println("\n==================================");
        System.out.println("수강생 삭제 실행 중...\n");

        // 수강생 ID 입력
        Student student = this.inRemoveStudentId();
        String studentId = student.getStudentId();
        String studentName = student.getStudentName();

        String remove = this.enterType(String.format("\n[%s. %s] 수강생을 정말 삭제하시겠습니까? (삭제 : remove 입력)", studentId, studentName));

        if (remove.equals("remove")) {
            // 점수 객체 삭제
            List<Subject> subjectList = student.getAllSubjects();
            for (Subject subject : subjectList) {
                try {
                    List<Score> scoreList = student.getScoreList(subject.getSubjectId());
                    for (Score score : scoreList) {
                        this.scoreStore.remove(score);
                    }
                } catch (BadInputException ignore) {
                }
            }

            // Status의 리스트에서 수강생 객체 삭제
            Status status = student.getStatus();
            status.removeStudent(student);

            // 수강생 삭제
            this.studentMap.remove(studentId);
            this.studentStore.remove(student);
            System.out.println("수강생 및 관련 정보가 삭제되었습니다.");
        }
    }

}

