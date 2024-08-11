package camp.model;

import java.util.List;

public class Viewer {
    private final InOut inOut;
    private final DataBase db;
    private final Management management;

//    public Viewer() {
//        this.db = new DataBase();
//        this.inOut = new InOut(this.db);
//        this.management = new Management(this.inOut);
//    }

    public Viewer(int SUBJECT_MIN_MANDATORY, int SUBJECT_MIN_CHOICE) {
        this.db = new DataBase(SUBJECT_MIN_MANDATORY, SUBJECT_MIN_CHOICE);
        this.inOut = new InOut(this.db);
        this.management = new Management(this.inOut);
    }

    /**
     *  메인 화면
     *   [선택 항목]
     *    1. 수강생 관리
     *    2. 점수 관리 : 등록된 수강생이 존재할 때만 선택 가능
     *    3. 프로그램 종료
     */
    public void displayMainView() {
        boolean flag = true;
        List<Integer> notAllowed = List.of(2);

        while (flag) {
            boolean noStudent = this.db.isEmptyStudentStore();

            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...\n");
            System.out.println("1. 수강생 관리");
            System.out.println(this.inOut.activatedOrNot(noStudent, "2. 점수 관리"));
            System.out.println("3. 프로그램 종료");
            int input = this.inOut.enterType("\n관리 항목을 선택해 주십시오.", 1, 3, noStudent, notAllowed, 0);

            switch (input) {
                case 1 -> this.displayStudentView(); // 수강생 관리
                case 2 -> this.displayScoreView();  // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    /**
     * 수강생 관리 화면
     *  [선택 항목]
     *   1. 수강생 등록
     *   2. 수강생 목록 조회
     *   3. 수강생 정보 조회
     *   4. 수강생 정보 수정
     *   5. 상태별 수강생 목록 조회
     *   6. 수강생 삭제
     *   7. 메인 화면 이동
     *   (2 ~ 6번 항목은 DataBase 객체의 studentStore에 등록된 수강생이 존재할 때, 선택 가능)
     */
    public void displayStudentView() {
        boolean flag = true;
        List<Integer> notAllowed = List.of(2, 3, 4, 5, 6);

        while (flag) {
            boolean noStudent = this.db.isEmptyStudentStore();

            System.out.println("\n==================================");
            System.out.println("수강생 관리 실행 중...\n");
            System.out.println("1. 수강생 등록");
            System.out.println(this.inOut.activatedOrNot(noStudent, "2. 수강생 목록 조회"));
            System.out.println(this.inOut.activatedOrNot(noStudent, "3. 수강생 정보 조회"));
            System.out.println(this.inOut.activatedOrNot(noStudent, "4. 수강생 정보 수정"));
            System.out.println(this.inOut.activatedOrNot(noStudent, "5. 상태별 수강생 목록 조회"));
            System.out.println(this.inOut.activatedOrNot(noStudent, "6. 수강생 삭제"));
            System.out.println("7. 메인 화면 이동");
            int input = this.inOut.enterType("\n관리 항목을 선택해 주십시오.",1, 7, noStudent, notAllowed, 0);

            switch (input) {
                case 1 -> this.management.addStudent(); // 수강생 등록
                case 2 -> this.management.displayStudentList(); // 수강생 목록 조회
                case 3 -> this.management.displayStudentInfo(); // 수강생 정보 조회
                case 4 -> this.management.editStudentInfo(); // 수강생 정보 수정
                case 5 -> this.management.displayStudentsInStatus(); // 상태별 수강생 목록 조회
                case 6 -> this.management.deleteStudent(); // 수강생 삭제
                case 7 -> flag = false; // 메인 화면 이동
            }

        }
    }

    /**
     * 점수 관리 화면
     *  [선택 항목]
     *   1. 수강생의 과목별 시험 회차 및 점수 등록
     *   2. 수강생의 과목별 회차 점수 수정
     *   3. 수강생의 특정 과목 회차별 등급 조회
     *   4. 수강생의 과목별 평균 등급 조회
     *   5. 특정 상태 수강생들의 필수 과목 평균 등급 조회
     *   6. 메인 화면 이동
     *
     */
    public void displayScoreView() {
        boolean flag = true;

        while (flag) {
            System.out.println("\n==================================");
            System.out.println("점수 관리 실행 중...\n");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 수강생의 과목별 평균 등급 조회");
            System.out.println("5. 특정 상태 수강생들의 필수 과목 평균 등급 조회");
            System.out.println("6. 메인 화면 이동");
            int input = this.inOut.enterType("관리 항목을 선택하세요...", 1, 6, 0);

            switch (input) {
                case 1 -> this.management.addScoreInSubject(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> this.management.editNthScoreOfSubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> this.management.displayGradesOfSubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> this.management.displaySubjectAvgGrade();
                case 5 -> this.management.displayMandatorySubjectAvgGradeInStatus();
                case 6 -> flag = false; // 메인 화면 이동
            }
        }
    }
}
