package camp;

import camp.model.*;

import java.lang.reflect.Array;
import java.security.spec.ECField;
import java.sql.SQLOutput;
import java.util.*;

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
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    private static Map<String, Student>  studentMap;  // (key, value) = (studentId, Student)
    private static Map<String, Map<String, Subject>>  subjectMap;  // (key, value) = (subjectId, Subject)
    private static Map<String, Score>  scoreMap;  // (key, value) = (scoreId, Score)

    // 과목 타입
    private static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static final String SUBJECT_TYPE_CHOICE = "CHOICE";
    private static int SUBJECT_CNT_MANDATORY = 5;  // 전체 필수 과목 수
    private static int SUBJECT_CNT_CHOICE = 4;  // 전체 선택 과목 수
    private static final int SUBJECT_MIN_MANDATORY = 3;  // 필수 과목 최소 수강 신청 수
    private static final int SUBJECT_MIN_CHOICE = 2;  // 선택 과목 최소 수강 신청 수

    // index 관리 필드
    private static int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static int subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static int scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";

    // 스캐너
    private static Scanner sc = new Scanner(System.in);
    private static CheckValidity ck = new CheckValidity();  // 입력값 유효성 검사를 위한 CheckValidity 객체
    private static CampManagementApplication cma = new CampManagementApplication();  // CampManagementApplication 클래스의 NoneStatic 메서드를 실행하기 위해 생성한 객체

    public static void main(String[] args) {
        cma.setInitData();
        try {
            cma.displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private void setInitData() {
        studentStore = new ArrayList<>();
        subjectStore = List.of(
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
        scoreStore = new ArrayList<>();
        studentMap = new HashMap<>();
        subjectMap = Map.of(
                SUBJECT_TYPE_MANDATORY, new HashMap<String, Subject>(),
                SUBJECT_TYPE_CHOICE, new HashMap<String, Subject>( )
        );
        scoreMap = new HashMap<>();

        for (Subject subject : subjectStore) {
            subjectMap.get(subject.getSubjectType()).put(subject.getSubjectId(), subject);
        }
    }

    // index 자동 증가
    private String sequence(String type) {
        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                studentIndex++;
                return INDEX_TYPE_STUDENT + studentIndex;
            }
            case INDEX_TYPE_SUBJECT -> {
                subjectIndex++;
                return INDEX_TYPE_SUBJECT + subjectIndex;
            }
            default -> {
                scoreIndex++;
                return INDEX_TYPE_SCORE + scoreIndex;
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
            int input = Integer.parseInt(cma.enterType("관리 항목"));

            try {
                ck.selecterRange(1, 3, input);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                Thread.sleep(2000);
                continue;
            }

            switch (input) {
                case 1 -> cma.displayStudentView(); // 수강생 관리
                case 2 ->  {  // 점수 관리
                    try {
                        ck.noneEmptySubjectStore();
                        cma.displayScoreView();
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
            int input = Integer.parseInt(cma.enterType("관리 항목"));

            try {
                ck.selecterRange(1, 3, input);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            switch (input) {
                case 1 -> cma.createStudent(); // 수강생 등록
                case 2 -> cma.inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
            }

        }
    }

    // Getter
    // Get Student by studentId
    private Student getStudentById(String studentId) throws BadInputException{
        if (studentMap.containsKey(studentId)) {
            return studentMap.get(studentId);
        }

        throw new BadInputException();
    }

    // Get Subject by SubjectId
    private Subject getSubjectById(String subjectType, String subjectId) throws BadInputException {
        Map<String, Subject> sm = subjectMap.get(subjectType);

        if (sm.containsKey(subjectId)) {
            return sm.get(subjectId);
        }

        throw new BadInputException();
    }

    // Get subjectList by subject type
    private List<Subject> getSubjectListByType(String subjectType) {
        List<Subject> subjectList = new ArrayList<>();

        for (Subject subject : subjectStore) {
            if (subject.getSubjectType().equals(subjectType)) {
                subjectList.add(subject);
            }
        }

        return subjectList;
    }

    // Get store by index_type
    public List<Student> getStudentStore() {
        return studentStore;
    }

    // Get ScoreList by StudentId & SubjectId
//    private static List<Score> getScoreList(String studentId, String subjectName) {
//        ArrayList<Score> scoreList = new ArrayList<>();
//
//        for (Score score : scoreStore) {
//            if (score.getStudentId().equals(studentId) && score.getSubjectName().equals(subjectName)) {
//                scoreList.add(score);
//            }
//        }
//
//        return scoreList;
//    }

     private void printSubjectList(String msg, List<Subject> subjectList, List<Subject> joinedSubject) {
         System.out.printf("%s\n", msg);

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
                minJoin = SUBJECT_MIN_MANDATORY;
                typeKor = "필수";
                totalCnt = SUBJECT_CNT_MANDATORY;
                break;
            case SUBJECT_TYPE_CHOICE :
                minJoin = SUBJECT_MIN_CHOICE;
                typeKor = "선택";
                totalCnt = SUBJECT_CNT_CHOICE;
                break;
            default :
        }

        while (flag) {
            System.out.println("----------------------------------");
            System.out.printf("수강 %s 과목 등록 중...\n", typeKor);
            cma.printSubjectList(String.format("\n[ %s 과목 목록 ]", typeKor), subjectList, joinedSubject);
            System.out.print("\n등록할 과목의 ");
            String subjectId = cma.enterType(INDEX_TYPE_SUBJECT);
            Subject subject;

            try {
                subject = cma.getSubjectById(subjectType, subjectId);
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
                flag = ck.satisfySubjectCnt(student, subjectType, minJoin, totalCnt);

                if (flag) {
                    String more = cma.enterType("\n" + typeKor + "과목 수강 신청을 더 하시려면 more");
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
            studentName = cma.enterType("수강생의 이름");

            try {
                flag = ck.nameIsEngOrKor(studentName);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }


        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName); // 수강생 인스턴스 생성 예시 코드

        List<Subject> joinedSubject = student.getSubjectList(SUBJECT_TYPE_MANDATORY);  // 신청한 필수 과목 리스트
        List<Subject> subjectList = cma.getSubjectListByType(SUBJECT_TYPE_MANDATORY);
        cma.joinSubject(SUBJECT_TYPE_MANDATORY, student, joinedSubject, subjectList);

        joinedSubject = student.getSubjectList(SUBJECT_TYPE_CHOICE);  // 신청한 선택 과목 리스트
        subjectList = cma.getSubjectListByType(SUBJECT_TYPE_CHOICE);
        cma.joinSubject(SUBJECT_TYPE_CHOICE, student, joinedSubject, subjectList);

        // 기능 구현
        studentStore.add(student);
        studentMap.put(student.getStudentId(), student);
        System.out.println("수강생 등록 성공!\n");
    }



    // 수강생 목록 조회
    private void inquireStudent() {
        System.out.println("==================================");
        System.out.println("수강생 목록 조회 실행 중...");
        // 기능 구현
        if (studentStore.isEmpty()) {
            System.out.println("\n등록된 수강생이 존재하지 않습니다.");
        } else {
            for (Student student : studentStore){
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
            int input = Integer.parseInt(cma.enterType("관리 항목"));

            try {
                ck.selecterRange(1, 4, input);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            switch (input) {
                case 1 -> cma.createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> cma.updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> cma.inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
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
        boolean flag = true;

        System.out.println("==================================");
        System.out.println("수강생의 과목별 시험 회차 및 점수 등록 실행 중...");

        // 수강생ID 입력
        String studentId = "";
        Student student = null;

        while (flag) {
            try {
                student = cma.getStudentById(cma.enterType(INDEX_TYPE_STUDENT));
                flag = false;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }

        flag = true;
        String subjectType = "";

        while(flag) {
            System.out.println("----------------------------------");
            System.out.println("과목 분류 선택 중...");
            System.out.println("1. 필수 과목    2. 선택 과목");
            int typeInput = Integer.parseInt(cma.enterType("과목 분류"));

            try {
                flag = ck.selecterRange(1, 2, typeInput);
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


        flag = true;
        String subjectName = "";
        String subjectId = "";

        while (flag) {
            List<Subject> subjectList = student.getSubjectList(subjectType);

            System.out.println("----------------------------------");
            System.out.println("과목을 선택 중...");
            cma.printSubjectList("[ 수강 중인 과목 목록 ]", subjectList, null);
            subjectId = cma.enterType(INDEX_TYPE_SUBJECT);

            try {
                Subject subject = cma.getSubjectById(subjectType, subjectId);
                subjectName = subject.getSubjectName();
                flag = false;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }
        }

        flag = true;
        int testCnt = 0;

        while (flag) {
            System.out.println("----------------------------------");
            System.out.println("점수 등록 회차 선택 중...");
            testCnt = Integer.parseInt(cma.enterType("회차"));

            try {
                flag = ck.testCntRange(testCnt);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }

        }

        flag = true;
        int testScore = 0;

        while (flag) {
            System.out.println("----------------------------------");
            System.out.println("과목 점수 등록 중...");
            testScore = Integer.parseInt(cma.enterType("점수"));

            try {
                flag = ck.testScoreRange(testScore);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            }

        }

        String rank = cma.ranked(testScore, subjectType);
        Score score = new Score(sequence(INDEX_TYPE_SCORE), studentId, subjectName, testCnt, testScore, rank);

        try {
            flag = student.addScore(subjectId, score);
            if (!flag) {
                scoreStore.add(score);
                scoreMap.put(score.getScoreId(), score);
                System.out.println("\n점수 등록 성공!");
            }
        } catch (BadInputException e) {
            System.out.println(e.getMessage());
        }
    }

    // 수강생의 과목별 회차 점수 수정
    private void updateRoundScoreBySubject() throws InputMismatchException{
        String studentId = cma.enterType(INDEX_TYPE_STUDENT); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private void inquireRoundGradeBySubject() throws InputMismatchException{
        String studentId = cma.enterType(INDEX_TYPE_STUDENT);

        // 기능 구현 (조회할 특정 과목)
        for(Score sc : scoreStore){
            if(!sc.getStudentId().equals(studentId)){
                System.out.println("조회할 수강생 아이디가 없습니다.");
                System.out.println("다시 돌아갑니다.");
                inquireRoundGradeBySubject();
            }
        }

        System.out.println("과목 목록");
        for(int i = 0; i < subjectStore.size(); i++){
            Subject sj = subjectStore.get(i);
            System.out.println( i + 1 + ". " + sj.getSubjectName());
        }


        System.out.println("조회할 수강생의 과목을 입력해주세요: ");
        String subjectName = sc.nextLine();

        String subjectNameScore = "";
        int subjectNameTestCnt = 0;

        for(Score sc : scoreStore){
            if(sc.getStudentId().equals(studentId) && sc.getSubjectName().equals(subjectName)){
                subjectNameScore = sc.getRank();
                subjectNameTestCnt = sc.getTestCnt();
            }else {
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
        } else {
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

