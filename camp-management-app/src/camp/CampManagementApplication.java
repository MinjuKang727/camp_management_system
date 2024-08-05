package camp;

import camp.model.Score;
import camp.model.Status;
import camp.model.Student;
import camp.model.Subject;


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
            System.out.println("3. 수강생 정보 조회");
            System.out.println("4. 수강생 정보 수정");
            System.out.println("5. 상태별 수강생 목록 조회");
            System.out.println("6. 수강생 삭제");
            System.out.println("7. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = Integer.parseInt(sc.nextLine());

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> viewStudentDetails(); // 수강생 정보 조회
                case 4 -> modifyStudent(); // 수강생 정보 수정
                case 5 -> viewStudentsByStatus(); // 상태별 수강생 목록 조회
                case 6 -> deleteStudent(); // 수강생 삭제
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
    private static Student getStudentById(String studentId) {
        for (Student student : studentStore) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }

        System.out.println("존재하지 않는 수강생입니다.");
        return null;
    }

    // Get Subject by SubjectId
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

        System.out.println("알 수 없는 과목ID를 입력하셨습니다.");
        return null;
    }



    // 수강생 등록
    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.nextLine();

        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName); // 수강생 인스턴스 생성 예시 코드

        boolean flag = true;  // 과목 선택을 더 할 지 여부를 담을 boolean 변수
        List<Subject> subjectList = student.getSubjectList(SUBJECT_TYPE_MANDATORY);  // 필수 과목 리스트

        // 기능 구현 (필수 과목)
        while (flag) {
            System.out.println("\n[ 필수 과목 목록 ]");
            for (int i = 0; i < SUBJECT_CNT_MANDATORY; i++) {
                Subject subject = subjectStore.get(i);

                // 현재 선택한 과목은 제외한 필수 과목의 ID와 이름 출력
                if (subject.getSubjectType().equals(SUBJECT_TYPE_MANDATORY) && !subjectList.contains(subject)) {
                    System.out.printf("%s. %s    ", subject.getSubjectId().replace(INDEX_TYPE_SUBJECT, ""), subject.getSubjectName());
                }
            }

            System.out.println("\n필수 과목을 선택하세요(최소 3과목 신청)...");
            String subjectId = String.format("%s%s", INDEX_TYPE_SUBJECT, sc.nextLine());
            Subject selectedSubject = getSubjectById(subjectId, SUBJECT_TYPE_MANDATORY);
            flag = selectedSubject == null;
            if (flag) {
                continue;
            }

            flag = !student.addSubject(selectedSubject);
            if (flag) {
                continue;
            }

            // 필수 과목 추가 신청 여부 결정하는 코드
            int signUpCnt = student.getSignUpSJCnt(SUBJECT_TYPE_MANDATORY);  // 현재 신청한 필수 과목 수

            if (signUpCnt < 3) {
                flag = true;
            } else if (signUpCnt < SUBJECT_CNT_MANDATORY) {
                System.out.println("필수 과목을 더 신청하시겠습니까? (더 신청: more 입력)");
                String more = sc.nextLine();
                flag = more.equals("more");
            }
        }

        flag = true;  // 과목 선택을 더 할 지 여부를 boolean 타입으로 담는 변수
        subjectList = student.getSubjectList(SUBJECT_TYPE_CHOICE);  // 선택 과목 리스트

        // 기능 구현 (선택 과목)
        while (flag) {
            System.out.println("\n[ 선택 과목 목록 ]");
            for (int i = SUBJECT_CNT_MANDATORY; i < SUBJECT_CNT_MANDATORY + SUBJECT_CNT_CHOICE; i++) {
                Subject subject = subjectStore.get(i);

                // 현재 선택한 과목은 제외한 선택 과목의 ID와 이름 출력
                if (subject.getSubjectType().equals(SUBJECT_TYPE_CHOICE) && !subjectList.contains(subject)) {
                    System.out.printf("%s. %s    ", subject.getSubjectId().replace(INDEX_TYPE_SUBJECT, ""), subject.getSubjectName());
                }
            }

            System.out.println("\n선택 과목을 선택하세요(최소 2과목 신청)...");
            String subjectId = String.format("SU%s", sc.nextLine());
            Subject selectedSubjectChoice = getSubjectById(subjectId, SUBJECT_TYPE_CHOICE);
            flag = selectedSubjectChoice == null;
            if (flag) {
                continue;
            }

            flag = !student.addSubject(selectedSubjectChoice);
            if (flag) {
                continue;
            }

            // 선택 과목 추가 신청 여부 결정하는 코드
            int signUpCnt = student.getSignUpSJCnt(SUBJECT_TYPE_CHOICE);  // 현재 신청한 선택 과목 수

            if (signUpCnt < 2) {
                flag = true;
            } else if (signUpCnt < SUBJECT_CNT_CHOICE) {
                System.out.println("필수 과목을 더 신청하시겠습니까? (더 신청: more 입력)");
                String more = sc.nextLine();
                flag = more.equals("more");
            }
        }

        // 기능 구현
        boolean signUp = studentStore.add(student);
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
                System.out.printf("\n%s. %s", student.getStudentId().replace(INDEX_TYPE_STUDENT, ""), student.getStudentName());
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
            int input = Integer.parseInt(sc.nextLine());

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
        return String.format("%s%s", INDEX_TYPE_STUDENT, sc.nextLine());
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() throws InputMismatchException {
        String studentId = "";
        Student student = null;
        boolean flag = true;

        while (flag) {
            studentId = getStudentId();
            student = getStudentById(studentId);
            if (student == null) {
                System.out.println("알 수 없는 수강생 번호를 입력하셨습니다. 수강생 번호는 양의 정수로 입력해 주세요.");
                studentId = getStudentId();
                student = getStudentById(studentId);
            } else {
                flag = false;
            }
        }

        System.out.println("시험 점수를 등록합니다...");
        System.out.println("\n과목 분류를 선택해주세요...");
        System.out.println("1. 필수 과목    2. 선택 과목");

        flag = true;
        String subjectType = "";
        while (flag) {
            flag = false;
            int typeInput = Integer.parseInt(sc.nextLine());
            switch (typeInput) {
                case 1:
                    subjectType = SUBJECT_TYPE_MANDATORY;
                    break;
                case 2:
                    subjectType = SUBJECT_TYPE_CHOICE;
                    break;
                default:
                    System.out.println("알 수 없는 과목 분류입니다. 과목 분류는 1과 2 중에 선택해 주십시오.");
                    flag = true;
            }
        }

        flag = true;
        String subjectName = "";

        while (flag) {
            System.out.println("[ 수강 중인 과목 목록 ]");
            List<Subject> subjectList = student.getSubjectList(subjectType);
            for (int i = 0; i < subjectList.size(); i++) {
                Subject subject = subjectList.get(i);
                System.out.printf("%d. %s    ", i + 1, subject.getSubjectName());
            }

            System.out.println("\n점수를 등록할 과목을 선택하세요...");
            int input = Integer.parseInt(sc.nextLine());

            if (input < 1 || input >= subjectList.size()) {
                System.out.println("알 수 없는 과목을 선택하셨습니다. 1이상 " + subjectList.size() + "이하의 값을 입력해 주세요.");
            } else {
                flag = false;
                Subject subject = subjectList.get(input);
                subjectName = subject.getSubjectName();
            }
        }

        System.out.println("점수를 등록할 회차를 선택하세요...");

        flag = true;
        int testCnt = 0;

        while (flag) {
            testCnt = Integer.parseInt(sc.nextLine());

            if (testCnt <= 0 || testCnt > 10) {
                System.out.println("알 수 없는 회차를 입력하셨습니다. 회차는 1이상 10 이하의 정수로 입력해 주십시오.");
                testCnt = Integer.parseInt(sc.nextLine());
            } else {
                flag = false;
            }
        }

        System.out.println("과목 점수를 입력하세요.");

        flag = true;
        int testScore = 0;

        while (flag) {
            testScore = Integer.parseInt(sc.nextLine());

            if (testScore < 0 || testScore > 100) {
                System.out.println("알 수 없는 점수를 입력하셨습니다. 점수는 0이상 100이하의 정수로 입력해 주십시오.");
                testScore = Integer.parseInt(sc.nextLine());
            } else {
                flag = false;
            }
        }

        String rank = ranked(testScore, subjectType);
        Score score = new Score(sequence(INDEX_TYPE_SCORE), studentId, subjectName, testCnt, testScore, rank);
        // 이 부분 중복된 회차 성적 등록할 경우 앞의 성적 삭제 코드 필요
        scoreStore.add(score);
        System.out.println("\n점수 등록 성공!");
    }

    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() throws InputMismatchException {
         // 관리할 수강생 고유 번호
        boolean flag = true;

        while (flag) {
            String studentId = "";

            System.out.println("수강생과 과목, 회차 목록");
            for(Score score : scoreStore) {
                if (score.getStudentId().equals(studentId)) {
                    System.out.println(score.getStudentId() + " / " + score.getSubjectName() + " / " + score.getTestCnt());
                }
            }

            studentId = getStudentId();

            System.out.print("수강생의 과목, 회차를 선택해주세요: ");
            String subjectName = "";  // 수강생 과목 선택
            int cnt = 0;              // 수강생 회차 선택

            for(Score score : scoreStore) {
                subjectName = sc.next();
                cnt = sc.nextInt();
                if (score.getStudentId().equals(studentId) && score.getTestCnt() == cnt && score.getSubjectName().equals(subjectName)) {

                    System.out.println("바꿀 과목과 회차, 점수를 입력해주세요: ");
                    String changeSubjectName = sc.next();  // 수강생의 바꿀 과목
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

                    for(Subject sj : subjectStore){
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
                    String changeRank = ranked(changeTestScore, subjectType);       // 위에서 받은 과목 타입이랑 새로운 점수를 넣어서 새로운 등급을 받는다
                    score.setRank(changeRank);                                      // 새로운 등급 수정
                    System.out.println("수정 완료");
                    break;
                }else {
                    System.out.println("입력한 과목, 회차가 없습니다.");
                }



            }
            System.out.println("수정을 다시 하시겠습니까?(yes or no): ");
            String type = sc.next();
            if(type.equals("no")){
                flag = false;
            }
        }
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() throws InputMismatchException {
        String studentId = getStudentId();

        // 기능 구현 (조회할 특정 과목)
        for (Score sc : scoreStore) {
            if (!sc.getStudentId().equals(studentId)) {
                System.out.println("조회할 수강생 아이디가 없습니다.");
                System.out.println("다시 돌아갑니다.");
                inquireRoundGradeBySubject();
            }
        }

        System.out.println("과목 목록");
        for (int i = 0; i < subjectStore.size(); i++) {
            Subject sj = subjectStore.get(i);
            System.out.println(i + 1 + ". " + sj.getSubjectName());
        }


        System.out.println("조회할 수강생의 과목을 입력해주세요: ");
        String subjectName = sc.next();

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

    private static String ranked(int score, String subjectType) {
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

    private static void viewStudentDetails() {
        System.out.print("\n조회할 수강생의 고유 번호를 입력하세요: ");
        String studentId = String.format("%s%s", INDEX_TYPE_STUDENT, sc.nextLine());
        Student student = getStudentById(studentId);

        if (student != null) {
            System.out.printf("\n고유 번호: %s\n이름: %s\n상태: %s\n선택한 과목:\n",
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getStatus());

            for (Subject subject : student.getSubjects()) {
                System.out.printf("- %s\n", subject.getSubjectName());
            }
        }
    }

    private static void modifyStudent() {
        System.out.print("\n수정할 수강생의 고유 번호를 입력하세요: ");
        String studentId = String.format("%s%s", INDEX_TYPE_STUDENT, sc.nextLine());
        Student student = getStudentById(studentId);

        if (student != null) {
            System.out.print("수정할 이름을 입력하세요(수정하지 않으려면 enter): ");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) {
                student.setStudentName(newName);
            }

            System.out.print("수정할 상태를 입력하세요(Green, Red, Yellow): ");
            String newStatus = sc.nextLine();
            try {
                student.setStatus(Status.valueOf(newStatus));
            } catch (IllegalArgumentException e) {
                System.out.println("잘못된 상태 입력입니다. 수정하지 않았습니다.");
            }

            System.out.println("수강생 정보가 수정되었습니다.");
        }
    }

    private static void viewStudentsByStatus() {
        System.out.print("조회할 상태를 입력하세요(GREEN, RED, YELLOW): ");
        String statusInput = sc.nextLine();

        try {
            Status status = Status.valueOf(statusInput);
            System.out.printf("\n상태가 %s인 수강생 목록:\n", status);

            for (Student student : studentStore) {
                if (student.getStatus() == status) {
                    System.out.printf("고유 번호: %s, 이름: %s\n", student.getStudentId(), student.getStudentName());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 상태 입력입니다.");
        }
    }

    private static void deleteStudent() {
        System.out.print("\n삭제할 수강생의 고유 번호를 입력하세요: ");
        String studentId = String.format("%s%s", INDEX_TYPE_STUDENT, sc.nextLine());
        Student student = getStudentById(studentId);

        if (student != null) {
            studentStore.remove(student);
            scoreStore.removeIf(score -> score.getStudentId().equals(studentId));
            System.out.println("수강생 및 관련 점수 기록이 삭제되었습니다.");
        }
    }

}
