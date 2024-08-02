package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.security.spec.ECField;
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

    // 과목 타입
    private static String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static String SUBJECT_TYPE_CHOICE = "CHOICE";
    private static int SUBJECT_CNT_MANDATORY = 5;
    private static int SUBJECT_CNT_CHOICE = 4;

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
            int input = Integer.parseInt(sc.nextLine());

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
            int input = Integer.parseInt(sc.nextLine());

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

    // Getter Subject by SubjectId
    private static Subject getSubjectById(String subjectId, String subjectType) {
        if (subjectType.equals(SUBJECT_TYPE_MANDATORY)) {
            for (int i = 0; i < SUBJECT_CNT_MANDATORY; i++) {
                Subject subject = subjectStore.get(i);

                if (subject.getSubjectId().equals(subjectId)) {
                    return subject;
                }
            }
        } else if (subjectType.equals(SUBJECT_TYPE_CHOICE)) {
            for (int i = SUBJECT_CNT_MANDATORY; i < SUBJECT_CNT_CHOICE + SUBJECT_CNT_MANDATORY; i++) {
                Subject subject = subjectStore.get(i);

                if (subject.getSubjectId().equals(subjectId)) {
                    return subject;
                }
            }
        }

        return null;
    }

    // 수강생 등록
    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.nextLine();

        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName); // 수강생 인스턴스 생성 예시 코드

        boolean moreSignUp = true;  // 과목 선택을 더 할 지 여부를 담을 boolean 변수
        ArrayList<Subject> subjectList = student.getSubjectList(SUBJECT_TYPE_MANDATORY);  // 필수 과목 리스트
        // 기능 구현 (필수 과목, 선택 과목)
        while (moreSignUp) {
            System.out.println("\n[ 필수 과목 목록 ]");
            for (int i = 0; i < SUBJECT_CNT_MANDATORY; i++) {
                Subject subject = subjectStore.get(i);

                // 현재 선택한 과목은 제외한 필수 과목의 ID와 이름 출력
                if (subject.getSubjectType().equals(SUBJECT_TYPE_MANDATORY) && !subjectList.contains(subject)) {
                    System.out.print(subject.getSubjectId().replace("SU", "") + ". " + subject.getSubjectName()+ "    ");
                }
            }

            System.out.println("\n필수 과목을 선택하세요(최소 3과목 신청)...");
            String subjectId = "SU" + sc.nextLine();

            try {
                 student.addSubject(getSubjectById(subjectId, SUBJECT_TYPE_MANDATORY));
            } catch (Exception e) {
                System.out.println("알 수 없는 과목을 선택하셨습니다.");
                continue;
            }

            // 필수 과목 추가 신청 여부 결정하는 코드
            int signUpCnt = student.getSignUpSJCnt(SUBJECT_TYPE_MANDATORY);  // 현재 신청한 필수 과목 수
            if (signUpCnt < 3) {
                moreSignUp = true;
            } else if (signUpCnt < SUBJECT_CNT_MANDATORY){
                System.out.println("필수 과목을 더 신청하시겠습니까? (더 신청: more 입력)");
                String more = sc.nextLine();
                moreSignUp = more.equals("more");
            } else {
                moreSignUp = false;
            }
        }

        moreSignUp = true;  // 과목 선택을 더 할 지 여부를 boolean 타입으로 담는 변수
        subjectList = student.getSubjectList(SUBJECT_TYPE_CHOICE);  // 선택 과목 리스트

        // 기능 구현 (필수 과목, 선택 과목)
        while (moreSignUp) {
            System.out.println("\n[ 선택 과목 목록 ]");
            for (int i = SUBJECT_CNT_MANDATORY; i < SUBJECT_CNT_MANDATORY + SUBJECT_CNT_CHOICE; i++) {
                Subject subject = subjectStore.get(i);

                // 현재 선택한 과목은 제외한 선택 과목의 ID와 이름 출력
                if (subject.getSubjectType().equals(SUBJECT_TYPE_CHOICE) && !subjectList.contains(subject)) {
                    System.out.print(subject.getSubjectId().replace("SU", "") + ". " + subject.getSubjectName()+ "    ");
                }
            }

            System.out.println("\n필수 과목을 선택하세요(최소 3과목 신청)...");
            String subjectId = "SU" + sc.nextLine();

            try {
                student.addSubject(getSubjectById(subjectId, SUBJECT_TYPE_CHOICE));
            } catch (Exception e) {
                System.out.println("알 수 없는 과목을 선택하셨습니다.");
                continue;
            }

            // 필수 과목 추가 신청 여부 결정하는 코드
            int signUpCnt = student.getSignUpSJCnt(SUBJECT_TYPE_CHOICE);  // 현재 신청한 필수 과목 수
            if (signUpCnt < 3) {
                moreSignUp = true;
            } else if (signUpCnt < SUBJECT_CNT_CHOICE){
                System.out.println("선택 과목을 더 신청하시겠습니까? (더 신청: more 입력)");
                String more = sc.nextLine();
                moreSignUp = more.equals("more");
            } else {
                moreSignUp = false;
            }
        }

        // 기능 구현
        boolean signUp = studentStore.add(student);
        if (signUp) {
            System.out.println("수강생 등록 성공!\n");
        } else {
            System.out.println("수강생 등록 실패!\n");
        }

    }



    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        if (studentStore.isEmpty()) {
            System.out.println("\n등록된 수강생이 존재하지 않습니다.");
        } else {
            for (Student student : studentStore){
                System.out.println("[" + student.getStudentId() + "] " + student.getStudentName());
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
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");

        System.out.println("과목 목록");
        for(int i = 0; i < subjectStore.size(); i++){
            Subject sj = subjectStore.get(i);
            System.out.println(sj.getSubjectName());
        }

        System.out.print("과목과 회차를 입력해주세요.: ");
        String subjectName = "";
        int testCnt = 0;

        try {
            subjectName = sc.next();
            testCnt = sc.nextInt();
        } catch (InputMismatchException e){
            System.out.println("과목이 아니거나 회차가 이상합니다.");
            createScore();
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
                System.out.println("이미 과목에 점수가 등록 된 수강생입니다.");
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
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (조회할 특정 과목)
        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
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
