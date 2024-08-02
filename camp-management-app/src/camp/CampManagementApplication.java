package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**  (김태준)연습으로 친거에요..
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

    // 과목 타입
    private static String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static String SUBJECT_TYPE_CHOICE = "CHOICE";

    // index 관리 필드
    private static int studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static int subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static int scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";

    // 스캐너
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        setInitData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }


    }

    // 초기 데이터 생성
    private static void setInitData() {
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
    }

    // index 자동 증가
    private static String sequence(String type) {
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

    private static void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private static void displayStudentView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 수강생 등록
    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();


        // 기능 구현 (필수 과목, 선택 과목)
        System.out.println("필수 과목 목록");

        for(Subject sj : subjectStore){
            if(sj.getSubjectType().equals(SUBJECT_TYPE_MANDATORY)){
                System.out.println(sj.getSubjectId() + ". " + sj.getSubjectName());
            }
        }

        System.out.print("필수 과목 선택(띄어쓰기 없이 적어주세요) :");
        List<String> manda = new ArrayList<>();
        String[] mandatorySubject = sc.next().split(",");


        try {
            if (mandatorySubject[0].equals(mandatorySubject[mandatorySubject.length - 1])) {
                System.out.println("선택하신 필수 과목 중 중복된 과목이 있습니다.");
                System.out.println("처음으로 돌아갑니다.");
                createStudent();
            }

            for (int i = 0; i <= mandatorySubject.length - 1; i++) {
                if (mandatorySubject[i].equals(mandatorySubject[i + 1])) {
                    System.out.println("선택하신 필수 과목 중 중복된 과목이 있습니다.");
                    System.out.println("처음으로 돌아갑니다.");
                    createStudent();
                }
            }
        } catch (Exception e){
            System.out.println("중복된 과목이 있습니다.");
            System.out.println("처음으로 돌아갑니다.");
            createStudent();
        }


        if(mandatorySubject.length < 3){
            System.out.println("필수 과목은 최소 3개를 선택해야 합니다.");
            System.out.println("띄어쓰기를 하면 안 됩니다.");
            System.out.println("처음으로 돌아갑니다.");
            createStudent();
        }



            for(String st : mandatorySubject){
             switch (st){
                 case "SU1", "SU2", "SU3", "SU4", "SU5" -> manda.add(st);
                 default -> {
                     System.out.println("필수 과목이 아니거나 잘못 입력했습니다.");
                     System.out.println("처음으로 돌아갑니다.");
                     createStudent();
                 }
             }
            }


        System.out.println("선택 과목 목록");

        for(Subject sj : subjectStore){
            if(sj.getSubjectType().equals(SUBJECT_TYPE_CHOICE)){
                System.out.println(sj.getSubjectId() + ". " + sj.getSubjectName());
            }
        }

        System.out.print("선택 과목 선택(띄어쓰기 없이 적어주세요) :");
        List<String> choice = new ArrayList<>();
        String[] choiceSubject = sc.next().split(",");

        try {
            if (choiceSubject[0].equals(choiceSubject[choiceSubject.length - 1])) {
                System.out.println("선택하신 선택 과목 중 중복된 과목이 있습니다.");
                System.out.println("처음으로 돌아갑니다.");
                createStudent();
            }

            for (int i = 0; i <= choiceSubject.length - 1; i++) {
                if (choiceSubject[i].equals(choiceSubject[i + 1])) {
                    System.out.println("선택하신 선택 과목 중 중복된 과목이 있습니다.");
                    System.out.println("처음으로 돌아갑니다.");
                    createStudent();
                }
            }
        } catch (Exception e){
            System.out.println("중복된 과목이 있습니다.");
            System.out.println("처음으로 돌아갑니다.");
            createStudent();
        }


        if(choiceSubject.length < 2){
            System.out.println("선택 과목은 최소 2개를 선택해야 합니다.");
            System.out.println("띄어쓰기를 하면 안 됩니다.");
            System.out.println("처음으로 돌아갑니다.");
            createStudent();
        }



        for(String st : choiceSubject){
            switch (st){
                case "SU6", "SU7", "SU8", "SU9" -> choice.add(st);
                default -> {
                    System.out.println("선택 과목이 아니거나 잘못 입력했습니다.");
                    System.out.println("처음으로 돌아갑니다.");
                    createStudent();
                }
            }
        }

        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName, manda, choice);

        System.out.println("수강생 등록 중입니다......");

        studentStore.add(student);

        System.out.println("수강생 등록 성공!\n");
    }



    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        if (studentStore.isEmpty()) {
            System.out.println("\n등록된 수강생이 존재하지 않습니다.");
        } else {
            for (Student student : studentStore) {
                System.out.println("고유 번호 : " + student.getStudentId() + " / 이름 : " + student.getStudentName());
            }
            System.out.println("\n수강생 목록 조회 성공!");
        }

    }

    private static void displayScoreView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        String studentId = getStudentId();

        for(Student student : studentStore){
            if(!student.getStudentId().equals(studentId)){
                System.out.println("없는 수강생입니다.");
                System.out.println("다시 돌아갑니다.");
                createScore();
            }
        }

        System.out.println("시험 점수를 등록합니다...");

        System.out.println("과목 목록");
        for(int i = 0; i < subjectStore.size(); i++){
            Subject sj = subjectStore.get(i);
            System.out.println( i + 1 + ". " + sj.getSubjectName());
        }

        System.out.print("과목과 회차를 입력해주세요.: ");
        String subjectName = "";
        int testCnt = 0;

        try {
            subjectName = sc.next();
            testCnt = sc.nextInt();
        } catch (InputMismatchException e){
            System.out.println("과목이 이상하거나 회차가 이상합니다.");
            createScore();
        }

        for(Subject sj : subjectStore){
            if(!sj.getSubjectName().equals(subjectName)){
                System.out.println("존재하지 않는 과목입니다.");
                System.out.println("처음으로 돌아갑니다.");
                createScore();
            }
        }

        if(testCnt < 1 || testCnt > 10){
            System.out.println("없는 회차 번호입니다.");
            System.out.println("처음으로 돌아갑니다.");
            createScore();
        }

        for(Score score : scoreStore){
            if(score.getStudentId().equals(studentId) &&
                    score.getSubjectName().equals(subjectName) &&
                    score.getTestCnt() == testCnt){
                System.out.println("이미 해당 과목의 회차 점수가 등록 된 수강생입니다.");
            }
        }

        System.out.print("점수를 입력해주세요: ");
        int testScore = 0;
        try {
            testScore = sc.nextInt();
        } catch (InputMismatchException e){
            System.out.println("숫자가 아닌 다른 걸 적었습니다.");
            System.out.println("처음으로 돌아갑니다.");
            createScore();
        }

        if(testScore < 0 || testScore > 100){
            System.out.println("점수가 이상합니다.");
            System.out.println("처음으로 돌아갑니다.");
            createScore();
        }

        String rank = ranked(testScore);

        Score score = new Score(sequence(INDEX_TYPE_SCORE), studentId, subjectName, testCnt, testScore, rank);

        scoreStore.add(score);


        System.out.println("\n점수 등록 성공!");
    }

    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId();

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
        String subjectName = sc.next();

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

    private static String ranked(int score){
        if(score >= 95 && score <= 100){
            return "A";
        } else if (score >= 90 && score <= 94){
            return "B";
        }else if (score >= 80 && score <= 89){
            return "C";
        }else if (score >= 70 && score <= 79){
            return "D";
        }else if (score >= 60 && score <= 69){
            return "E";
        }else if (score >= 0 && score < 60){
            return "N";
        }
        return "N";
    }


}
