package camp.model;

import camp.model.Exception.BadInputException;
import camp.model.Exception.NotExistException;

import java.util.List;
import java.util.Scanner;

public class InOut {
    private StringBuilder sb;
    private Scanner sc;
    private CheckValidity ck;
    private DataBase db;

    public InOut(DataBase db) {
        this.sc = new Scanner(System.in);
        this.ck = new CheckValidity();
        this.db = db;
    }

    /**
     * StringBuilder로 문자 연결 후, 합친 문자열 반환한다.
     *
     * @params String... str 가변 매개변수 문자열
     * @return 매개변수를 모두 합친 문자열
     */
    public String concatStrings(String...str) {
        this.sb = new StringBuilder();

        for (String s : str) {
            this.sb.append(s);
        }

        return this.sb.toString();
    }

    public String concatString(String title , boolean flag) {
        this.sb = new StringBuilder();
        this.sb.append(title);

        if (flag) {
            this.sb.append(" (비활성)");
        }

        return this.sb.toString();
    }


    /**
     * 정수형 입력값 받는 메서드
     *  정수형 입력값을 받고
     *  유효성 검사(범위: min이상 max이하) 후,
     *  정수형 유효 입력값 or 예외값으로 설정한 정수를 반환한다.
     *
     * @param msg : 입력 안내 메세지 문자열
     * @param min : 입력 유효값 범위의 최소 정수
     * @param max : 입력 유효값 범위의 최대 정수
     * @param noStudent : DataBase의 studentStore에 저장된 Student 객체가 존재하는지 여부
     * @param notAllowed : 선택이 허용되지 않는 값 리스트
     * @return 유효 입력값(min 이상 max 이하의 ((noStudent == true인 경우,) notAllowed에 포함되지 않은) 양의 정수
     */
    public int enterType(String msg, int min, int max, boolean noStudent, List<Integer> notAllowed, int exception) {
            System.out.println(msg);

            try {
                String strInput = sc.nextLine();
                int input = this.ck.isNumber(strInput);
                if (noStudent) {
                    this.ck.selecterRange(min, max, input, notAllowed);
                } else {
                    this.ck.selecterRange(min, max, input);
                }

                return input;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
                return exception;
            }
    }

    public int enterType(String msg, int min, int max, int exception) {
            System.out.println(msg);
            int input = exception;
            String exit = "";

            try {
                String strInput = sc.nextLine();
                input = this.ck.isNumber(strInput);
                this.ck.selecterRange(min, max, input);
                return input;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
                return exception;
            }
    }

    /**
     * 매개변수로 받은 문자열 기준으로 안내메세지 출력하고 입력값 받는 메서드
     *
     * @param type : 입력값의 종류(ST:수강생 고유 번호, SU:과목 고유 번호, SC:점수 고유 번호, 그 외: 입력값 안내 메세지)
     * @return 입력값을 문자열로 반환
     */
    public String enterType(String type) {
        return switch (type) {
            case DataBase.INDEX_TYPE_STUDENT -> {
                System.out.printf("수강생 고유 번호를 입력하십시오... (%s를 제외한 뒤의 번호만 입력)\n", DataBase.INDEX_TYPE_STUDENT);
                yield String.format("%s%s", DataBase.INDEX_TYPE_STUDENT, sc.nextLine());
            }
            case DataBase.INDEX_TYPE_SUBJECT -> {
                System.out.printf("과목 고유 번호 를 입력하십시오...(%s를 제외한 뒤의 번호만 입력)\n", DataBase.INDEX_TYPE_SUBJECT);
                yield String.format("%s%s", DataBase.INDEX_TYPE_SUBJECT, sc.nextLine());
            }
            case DataBase.INDEX_TYPE_SCORE -> {
                System.out.printf("점수 고유 번호 를 입력하십시오...(%s를 제외한 뒤의 번호만 입력)\n", DataBase.INDEX_TYPE_SCORE);
                yield String.format("%s%s", DataBase.INDEX_TYPE_SCORE, sc.nextLine());
            }
            default -> {
                System.out.println(type);
                yield sc.nextLine();
            }


        };
    }

    /**
     * 수강생 이름 입력 > 수강생 이름 문자열 반환
     *
     * @return 수강생 이름 문자열
     */
    public String inStudentName() {
        while (true) {
            String studentName = this.enterType("수강생의 이름을 입력하십시오.");

            try {
                this.ck.nameIsEngOrKor(studentName);
                return studentName;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
            }
        }
    }

    /**
     * 수강생 이름 입력 >> 기존에 등록된 이름과 다른 수강생 이름 문자열 반환
     * @param preName : 기존 등록되어 있는 수강생 이름
     * @return 기존에 등록된 이름과 다른 수강생 이름 문자열
     */
    public String inStudentName(String preName) {
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
                System.out.println(e.getHint());
            }

            try {
                this.inExit("수강생 이름 수정");
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                return "ex1t";
            }
        }
    }

    /**
     * 수강생 고유 번호 입력  >> 수강생 객체 반환
     * @return 수강생 객체
     */
    public Student inStudentId() {
        while (true) {
            try {
                System.out.println("----------------------------------");
                String studentId = this.enterType(DataBase.INDEX_TYPE_STUDENT);
                return this.db.getStudentById(studentId);
            } catch (NotExistException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
            }
        }
    }

    /**
     * 삭제할 수강생 고유 번호 입력 > 수강생 객체 반환
     *
     * @return 수강생 객체
     */
    public Student inRemoveStudentId() {
        while (true) {
            try {
                System.out.println("----------------------------------");
                String studentId = this.enterType(DataBase.INDEX_TYPE_STUDENT);
                return this.db.getStudentById(studentId);
            } catch (NotExistException notExistException) {
                System.out.println(notExistException.getMessage());

                try {
                    this.inExit("수강생 상태 수정");
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                    return null;
                }
            }
        }
    }

    /**
     *  수강생 상태 입력 > 상태 객체 반환
     *
     * @return 상태 객체
     */
    public Status inStatus() {
        while (true) {
            String strStatus = this.enterType("수강생 상태를 입력해 주십시오. (상태 종류 : GREEN, YELLOW, RED)");

            try {
                return this.ck.statusInGYR(strStatus);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
            }
        }
    }


    /**
     * 수강생 상태 입력 >> 기존 상태와 다른 상태의 상태 객체 반환 혹은 null 반환
     * @param preStatus : 기존 등록되어 있는 상태 객체
     * @return 기존 상태 다른 상태 객체 or (다른 상태를 입력하지 않고 종료를 원할 때,) null
     */
    public Status inStatus(Status preStatus) {
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
                System.out.println(e.getHint());
            }

            try {
                this.inExit("수강생 상태 수정");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    /**
     * 과목 분류 입력  >> 과목 타입 문자열 반환
     * @param type : 선택할 분류 항목에 따라 정수를 입력
     *             (0 : 필수/선택/전체, 1 : 필수/선택) 을 선택 항목으로 입력값을 받음
     *
     * @return 선택한 과목 분류 항목 번호 or (유효한 입력값을 입력하지 않고 종료 시,) 0
     *      0(= 예외 값) or 1(= 필수 과목 선택) or 2(= 선택 과목 선택) (or 3(= 전체 과목 선택) (3은 type == 0일 때만 선택 가능))
     *      (type == 1) ? 0 이상 3 이하의 정수
     *      (type == 0) ? 0 이상 2 이하의 정수
     */
    public String inSubjectType(int type) {
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
                    return DataBase.SUBJECT_TYPE_MANDATORY;
                case 2 :
                    return DataBase.SUBJECT_TYPE_CHOICE;
                case 3 :
                    if (type == 1) {
                        return "모든";
                    }
                    break;
                default :
            };
        }
    }

    /**
     * 수강 중이지 않은 과목 선택 (과목 아이디 입력 >> 과목 객체 반환)
     * @param subjectType : 과목 분류 ("필수"/"선택")
     * @param subjectList : subjectType에 해당하는 과목 객체 리스트
     * @param joinedSubjectList : 현재 수강 중인 subjectType에 해당하는 과목 리스트
     * @return 수강 중이지 않은 과목 중 선택한 과목의 객체 반환
     */
    public Subject inSubjectId(String subjectType, List<Subject> subjectList, List<Subject> joinedSubjectList) {
        while (true) {

            System.out.printf("\n[ %s 과목 목록 ]\n", subjectType);
            for (Subject subject : subjectList) {
                if (!joinedSubjectList.contains(subject)) {
                    System.out.printf("%s. %s    ", subject.getSubjectId(), subject.getSubjectName());
                }
            }
            System.out.println("\n");

            String subjectId = this.enterType(DataBase.INDEX_TYPE_SUBJECT);

            try {
                Subject subject = this.db.getSubjectById(subjectType, subjectId);
                this.ck.notJoinedSubject(subject, joinedSubjectList);
                return subject;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
            }

        }
    }

    /**
     *  수강 중인 과목 선택 (과목 아이디 입력 >> 과목 객체 반환)
     * @param student : 학생 객체
     * @param subjectType : 과목 분류 ("필수"/"선택"/"모든")
     * @return 수강 중인 과목 중 선택한 과목 객체
     */
    public Subject inSubjectId(Student student, String subjectType) {
        List<Subject> subjectList;

        if (subjectType.equals("모든")) {
            subjectList = student.getAllSubjects();
        } else {
            subjectList = student.getSubjectList(subjectType);
        }

        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("과목을 선택 중...\n");
            System.out.printf("[ 수강 중인 %s 과목 목록 ]\n", subjectType);

            for (Subject subject : subjectList) {
                int round = student.getScoreCnt(subject.getSubjectId());

                System.out.printf("%s. %s (%d회차까지 점수 등록)\n", subject.getSubjectId(), subject.getSubjectName(), round);
            }
            System.out.println("\n");
            String subjectId = this.enterType(DataBase.INDEX_TYPE_SUBJECT);

            try {
                Subject subject = this.db.getSubjectById(subjectType, subjectId);
                this.ck.isJoinedSubject(subject, subjectList);
                return subject;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
            }

        }
    }

    /**
     * 등록할 점수 입력
     * @param subjectName : 과목 이름
     * @param round : 시험 회차
     *   선택한 회차의 점수를 입력 받고 유효성 검사
     * @return 선택한 회차의 유효 점수 or (유효 점수를 입력하지 않고 종료 시,) -1
     */
    public int inTestScore(String subjectName, int round) {
        while (true) {
            System.out.println("\n----------------------------------");
            System.out.printf("[ %s ] %d 회차 점수 등록 중...\n", subjectName, round);
            int testScore = this.enterType("점수를 입력해 주십시오.", 0, 100, -1);

            if (testScore == -1) {
                continue;
            }

            return testScore;
        }
    }

    /**
     * 수정할 점수 입력 >> 기존 점수와 다른 점수 반환
     * @param score : 점수를 수정할 점수 객체
     * @return 기존에 등록된 점수와 다른 유효 점수(범위: 0 이상 100 이하)
     *          or (유효 점수를 입력하지 않고 종료 시,) -1
     */
    public int inTestScore(Score score) {
        String subjectName = score.getSubjectName();
        int round = score.getRound();
        int preScore = score.getTestScore();
        int newScore = -1;
        boolean flag = true;

        while (flag) {
            System.out.println("\n----------------------------------");
            System.out.printf("[ %s ] %d 회차 점수 수정 중...\n", subjectName, round);
            System.out.printf("현재 등록되어 있는 점수 : %d점", preScore);
            newScore = this.enterType("\n점수를 입력해 주십시오.", 0, 100, -1);

            if (newScore == -1) {
                continue;
            }

            try {
                this.ck.notSameScore(newScore, preScore);
                flag = false;
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
                return -1;
            }
        }

        return newScore;
    }

    /**
     * 점수를 수정할 과목의 회차 입력
     * @param student : 수강생 객체
     * @param subjectId : 과목 고유 번호
     * @return 점수를 수정할 회차 or  (유효한 회차를 입력하지 않고 종료 시,) 0
     * @throws BadInputException : 해당 과목에
     */
    public int inRound(Student student, String subjectId) throws BadInputException {
        int max = student.getScoreCnt(subjectId);

        if (max == 0) {
            throw new BadInputException("해당 과목의 등록된 점수가 존재하지 않습니다.", "점수를 1회차 이상 등록 후, 수정 가능합니다.");
        }

        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("수정 회차 선택 중...\n");
            int round = this.enterType("회차를 입력하십시오. (선택 가능 회차 : 1 ~ " + max + ")", 1, max, 0);

            if (round == 0) {
                continue;
            }

            return round;
        }
    }

    /**
     * 종료 여부 입력
     */
    public void inExit(String message) throws Exception {
        String exit = this.enterType(this.concatStrings("\n-----------------------------------------------------------------\n", message, "을(를) 종료하시겠습니까? (종료 : exit 입력)"));

        if (exit.equals("exit")) {
            throw new Exception("\n현재 페이지를 종료하고 이전 페이지로 돌아갑니다.");
        }
    }


    // 출력

    /**
     * 수강생 목록 출력
     * @param studentList : 수강생 객체 리스트
     * @throws NotExistException 조회된 수강생이 존재하지 않으면 발생하는 예외
     */
    public void printStudentList(List<Student> studentList) throws NotExistException {
        if (studentList == null || studentList.isEmpty()) {
            throw new NotExistException("해당 상태의 수강생");
        }

        for (Student student : studentList) {
            System.out.printf("%s. %s\n", student.getStudentId(), student.getStudentName());
        }
        System.out.printf("[ 총 %s명의 수강생이 조회되었습니다. ] \n", studentList.size());
    }



    // GETTER
    public DataBase getDataBase() { return this.db; }

    public CheckValidity getCheckValidity() { return this.ck; }
}
