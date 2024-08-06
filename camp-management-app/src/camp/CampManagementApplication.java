package camp;

import java.util.*;
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
    private Map<String, Score>  scoreMap;  // (key, value) = (studentId, Score)

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

        cma.setInitData();
        try {
            cma.displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 생성자
    public CampManagementApplication() {
        ck = new CheckValidity();
        sc = new Scanner(System.in);
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
        this.scoreMap = new HashMap<>();

        for (Subject subject : this.subjectStore) {
            this.subjectMap.get(subject.getSubjectType()).put(subject.getSubjectId(), subject);
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
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            int input = Integer.parseInt(this.enterType("관리 항목"));

            try {
                this.ck.selecterRange(1, 3, input);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                Thread.sleep(2000);
                continue;
            }

            switch (input) {
                case 1 -> this.displayStudentView(); // 수강생 관리
                case 2 ->  {  // 점수 관리
                    try {
                        this.ck.noneEmptySubjectStore();
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
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            int input = Integer.parseInt(this.enterType("관리 항목"));

            try {
                this.ck.selecterRange(1, 3, input);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            switch (input) {
                case 1 -> this.createStudent(); // 수강생 등록
                case 2 -> this.inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
            }

        }
    }

    // Getter
    // Get Student by studentId
    private Student getStudentById(String studentId) throws BadInputException{
        if (this.studentMap.containsKey(studentId)) {
            return this.studentMap.get(studentId);
        }

        throw new BadInputException();
    }

    // Get Subject by SubjectId
    private Subject getSubjectById(String subjectType, String subjectId) throws BadInputException {
        Map<String, Subject> sm = this.subjectMap.get(subjectType);

        if (sm.containsKey(subjectId)) {
            return sm.get(subjectId);
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
    public List<Student> getStudentStore() {
        return this.studentStore;
    }

     private void printSubjectList(String msg, List<Subject> subjectList, List<Subject> joinedSubject) {
         System.out.printf("\n[ %s 과목 목록 ]\n", msg);

         for (Subject subject : subjectList) {
             if (joinedSubject == null || !joinedSubject.contains(subject)) {
                 System.out.printf("%s. %s    ", subject.getSubjectId().replace(INDEX_TYPE_SUBJECT, ""), subject.getSubjectName());
             }
         }
         System.out.println();

     }

    // 수강생의 신청 과목 추가 메서드
    private void joinSubject(String subjectType, Student student, List<Subject> joinedSubject, List<Subject> subjectList) {
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
            System.out.println("----------------------------------");
            System.out.printf("수강 %s 과목 등록 중...\n", typeKor);
            this.printSubjectList(typeKor, subjectList, joinedSubject);
            System.out.print("\n등록할 과목의 ");
            String subjectId = this.enterType(INDEX_TYPE_SUBJECT);
            Subject subject;

            try {
                subject = this.getSubjectById(subjectType, subjectId);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

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
                    String more = this.enterType("\n" + typeKor + "과목 수강 신청을 더 하시려면 more");
                    flag = more.equals("more");
                }
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 수강생 등록
    private void createStudent() {
        boolean flag = true;

        System.out.println("==================================");
        System.out.println("수강생 등록 실행 중...");
        String studentName = "";
        while (flag) {
            studentName = this.enterType("수강생의 이름");

            try {
                flag = this.ck.nameIsEngOrKor(studentName);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }


        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName); // 수강생 인스턴스 생성 예시 코드

        List<Subject> joinedSubject = student.getSubjectList(SUBJECT_TYPE_MANDATORY);  // 신청한 필수 과목 리스트
        List<Subject> subjectList = this.getSubjectListByType(SUBJECT_TYPE_MANDATORY);
        this.joinSubject(SUBJECT_TYPE_MANDATORY, student, joinedSubject, subjectList);

        joinedSubject = student.getSubjectList(SUBJECT_TYPE_CHOICE);  // 신청한 선택 과목 리스트
        subjectList = this.getSubjectListByType(SUBJECT_TYPE_CHOICE);
        this.joinSubject(SUBJECT_TYPE_CHOICE, student, joinedSubject, subjectList);

        // 기능 구현
        this.studentStore.add(student);
        this.studentMap.put(student.getStudentId(), student);
        System.out.println("수강생 등록 성공!\n");
    }


    // 수강생 목록 조회
    private void inquireStudent() {
        System.out.println("==================================");
        System.out.println("수강생 목록 조회 실행 중...");
        // 기능 구현
        if (this.studentStore.isEmpty()) {
            System.out.println("\n등록된 수강생이 존재하지 않습니다.");
        } else {
            for (Student student : this.studentStore) {
                System.out.printf("\n%s. %s", student.getStudentId().replace(INDEX_TYPE_STUDENT, ""), student.getStudentName());
            }
            System.out.println("\n수강생 목록 조회 성공!");
        }
    }

    private void displayScoreView() {
        boolean flag = true;

        while (flag) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            int input = Integer.parseInt(this.enterType("관리 항목"));

            try {
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

    private String enterType(String type) {
        return switch (type) {
            case INDEX_TYPE_STUDENT -> {
                System.out.println("수강생ID를 입력하십시오...");
                yield String.format("%s%s", INDEX_TYPE_STUDENT, sc.nextLine());
            }
            case INDEX_TYPE_SUBJECT -> {
                System.out.println("과목ID를 입력하십시오...");
                yield String.format("%s%s", INDEX_TYPE_SUBJECT, sc.nextLine());
            }
            case INDEX_TYPE_SCORE -> {
                System.out.println("점수ID를 입력하십시오...");
                yield String.format("%s%s", INDEX_TYPE_SCORE, sc.nextLine());
            }
            default -> {
                System.out.printf("%s을 입력하십시오...\n", type);
                yield sc.nextLine();
            }
        };
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private void createScore() {
        System.out.println("==================================");
        System.out.println("수강생의 과목별 시험 회차 및 점수 등록 실행 중...");

        // 수강생ID 입력
        String studentId = "";
        Student student = null;
        boolean flag = true;

        while (flag) {
            try {
                studentId = this.enterType(INDEX_TYPE_STUDENT);
                student = this.getStudentById(studentId);
                flag = false;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }

        // 과목 분류 선택
        flag = true;
        String subjectType = "";

        while(flag) {
            System.out.println("----------------------------------");
            System.out.println("과목 분류 선택 중...");
            System.out.println("1. 필수 과목    2. 선택 과목");
            int typeInput = Integer.parseInt(this.enterType("과목 분류"));

            try {
                flag = this.ck.selecterRange(1, 2, typeInput);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            subjectType = switch (typeInput) {
                case 1 -> SUBJECT_TYPE_MANDATORY;
                case 2 -> SUBJECT_TYPE_CHOICE;
                default -> subjectType;
            };
        }

        // 과목 선택
        flag = true;
        String subjectName = "";
        String subjectId = "";

        while (flag) {
            List<Subject> subjectList = student.getSubjectList(subjectType);

            System.out.println("----------------------------------");
            System.out.println("과목을 선택 중...");
            this.printSubjectList("수강", subjectList, null);
            subjectId = this.enterType(INDEX_TYPE_SUBJECT);

            try {
                Subject subject = this.getSubjectById(subjectType, subjectId);
                subjectName = subject.getSubjectName();
                flag = false;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }

        // 회차 유효성 검사
        int testCnt = student.getScoreCnt(subjectId) + 1;

        try {
            ck.testCntOver10(testCnt);
        } catch (BadInputException e) {
            System.out.println(e.getMessage());
            System.out.println("점수 관리 페이지도 돌아갑니다.");
            return;
        }

        System.out.printf("%d회차의 점수를 등록합니다.", testCnt);

        // 점수 등록
        flag = true;
        int testScore = 0;

        while (flag) {
            System.out.println("----------------------------------");
            System.out.println("과목 점수 등록 중...");
            testScore = Integer.parseInt(this.enterType("점수"));

            try {
                flag = this.ck.testScoreRange(testScore);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }

        }

        // 등급 계산
        String rank = this.ranked(testScore, subjectType);

        // Score 객체 생성
        Score score = new Score(sequence(INDEX_TYPE_SCORE), studentId, subjectName, testCnt, testScore, rank);

        // 점수 등록
        student.addScore(subjectId, score);
        this.scoreStore.add(score);
        this.scoreMap.put(score.getStudentId(), score);
        System.out.println("\n점수 등록 성공!");
    }

    // 수강생의 과목별 회차 점수 수정
    private void updateRoundScoreBySubject() throws InputMismatchException {
         // 관리할 수강생 고유 번호
        boolean flag = true;

        while (flag) {
            String studentId = this.enterType(INDEX_TYPE_STUDENT);
            Student student;

            try {
                student = this.getStudentById(studentId);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            System.out.println("수강생과 과목, 회차 목록");
            List<Subject> subjectList = student.getSubjectList(SUBJECT_TYPE_MANDATORY);
            subjectList.addAll(student.getSubjectList(SUBJECT_TYPE_CHOICE));

            for(Subject subject : subjectList) {
                if (score.getStudentId().equals(studentId)) {
                    System.out.println(score.getStudentId() + " / " + score.getSubjectName() + " / " + score.getTestCnt());
                }
            }

            studentId = this.enterType(INDEX_TYPE_STUDENT);

            System.out.print("수강생의 과목, 회차를 선택해주세요: ");
            String subjectName = "";  // 수강생 과목 선택
            int cnt = 0;              // 수강생 회차 선택

            for(Score score : this.scoreStore) {
                subjectName = sc.nextLine();
                cnt = sc.nextInt();
                if (score.getStudentId().equals(studentId) && score.getTestCnt() == cnt && score.getSubjectName().equals(subjectName)) {

                    System.out.println("바꿀 과목과 회차, 점수를 입력해주세요: ");
                    String changeSubjectName = sc.nextLine();  // 수강생의 바꿀 과목
                    int changeCnt;                         // 수강생의 바꿀 회차
                    int changeTestScore;                   // 수강생의 바꿀 점수

                    try {                                   //회차와 점수의 입력 값이 맞지 않을 때
                        changeCnt = sc.nextInt();
                        changeTestScore = sc.nextInt();
                    } catch (InputMismatchException e){
                        System.out.println("회차랑 점수가 이상합니다.");
                        sc.next();
                        break;
                    }

                    String subjectType = "";            // 과목 타입 찾기

                    for(Subject sj : this.subjectStore){
                        if(sj.getSubjectName().equals(changeSubjectName)){
                            subjectType = sj.getSubjectType();                      //  subject 리스트에 같은 subjectName 이 있으면 그거에 맞는 타입을 받기
                            break;
                        }else {
                            System.out.println("과목이 없습니다.");
                        }

                    }
                    score.setSubjectName(changeSubjectName);                    // 새로운 subject 수정
                    score.setTestCnt(changeCnt);                                // 새로운 회차 수정
                    score.setTestScore(changeTestScore);                        // 새로운 점수 수정
                    String changeRank = this.ranked(changeTestScore, subjectType);       // 위에서 받은 과목 타입이랑 새로운 점수를 넣어서 새로운 등급을 받는다
                    score.setRank(changeRank);                                      // 새로운 등급 수정
                    System.out.println("수정 완료");
                    break;
                }else {
                    System.out.println("입력한 과목, 회차가 없습니다.");
                }
            }
            System.out.println("수정을 다시 하시겠습니까? (yes or no): ");
            String type = this.sc.nextLine();
            if(type.equals("no")){
                flag = false;
            }
        }
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private void inquireRoundGradeBySubject() throws InputMismatchException {
        String studentId = this.enterType(INDEX_TYPE_STUDENT);

        // 기능 구현 (조회할 특정 과목)
        for (Score sc : this.scoreStore) {
            if (!sc.getStudentId().equals(studentId)) {
                System.out.println("조회할 수강생 아이디가 없습니다.");
                System.out.println("다시 돌아갑니다.");
                inquireRoundGradeBySubject();
            }
        }

        for (int i = 0; i < this.subjectStore.size(); i++) {
            Subject sj = this.subjectStore.get(i);
            System.out.println(i + 1 + ". " + sj.getSubjectName());
        }


        System.out.println("조회할 수강생의 과목을 입력해주세요: ");
        String subjectName = sc.nextLine();

        String subjectNameScore = "";
        int subjectNameTestCnt = 0;

        for (Score sc : scoreStore) {
            if (sc.getStudentId().equals(studentId) && sc.getSubjectName().equals(subjectName)) {
                subjectNameScore = sc.getRank();
                subjectNameTestCnt = sc.getTestCnt();
            } else {
                System.out.println("해당 과목의 수강생 아이디가 없습니다.");
                System.out.println("처음으로 돌아갑니다.");
                inquireRoundGradeBySubject();
            }
        }

        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현

        System.out.println("회차: " + subjectNameTestCnt);
        System.out.println("등급: " + subjectNameScore);

        System.out.println("\n등급 조회 성공!");
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
                return "E";
            } else {
                return "N";
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
            } else {
                return "N";
            }
        }
    }


}

