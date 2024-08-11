package camp.model;

import camp.model.Exception.BadInputException;
import camp.model.Exception.NotExistException;

import java.util.List;

public class Management {
    private DataBase db;
    private InOut inOut;
    private CheckValidity ck;

    // 생성자
    public Management(InOut inOut) {
        this.inOut = inOut;
        this.db = this.inOut.getDataBase();
        this.ck = this.inOut.getCheckValidity();
    }

    // 수강생 등록
    public void addStudent() {
        System.out.println("\n==================================");
        System.out.println("수강생 등록 실행 중...\n");
        // 수강생 이름 입력
        String studentName = this.inOut.inStudentName();
        // 수강생 객체 생성
        Student student = new Student(this.db.sequence(DataBase.INDEX_TYPE_STUDENT), studentName); // 수강생 인스턴스 생성 예시 코드

        List<Subject> subjectList = this.db.getSubjectListByType(DataBase.SUBJECT_TYPE_MANDATORY);  // 필수 과목 리스트
        // 필수 과목 수강 신청
        this.addSubjectInStudent(DataBase.SUBJECT_TYPE_MANDATORY, student, subjectList);

        subjectList = this.db.getSubjectListByType(DataBase.SUBJECT_TYPE_CHOICE);  // 선택 과목 리스트
        // 선택 과목 수강 신청
        this.addSubjectInStudent(DataBase.SUBJECT_TYPE_CHOICE, student, subjectList);

        // 상태 입력
        System.out.println("\n----------------------------------");
        System.out.println("수강생 상태 등록 중...\n");
        Status status = this.inOut.inStatus();

        if (status == null) {
            return;
        }

        student.setStatus(status);
        status.addStudent(student);

        // 수강생 등록 확인
        System.out.println("새로운 수강생이 등록되었습니다.");
        this.displayStudentInfo(student);

        // 저장소에 저장
        this.db.addStudent(student);
    }


    // 수강생 목록 조회
    public void displayStudentList() {
        System.out.println("\n==================================");
        System.out.println("수강생 목록 조회 실행 중...\n");
        try {
            List<Student> studentStore = this.db.getStudentStore();

            this.ck.notEmptyStudentStore(studentStore);

            for (Student student : studentStore) {
                String studentId = student.getStudentId();
                String studentName = student.getStudentName();
                System.out.printf("%s. %s\n", studentId, studentName);
            }
            System.out.printf("\n[ 총 %d명의 수강생이 조회되었습니다. ]\n", studentStore.size());
        } catch (NotExistException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getHint());
        }

    }

    // 수강생의 신청 과목 추가 메서드
    public void addSubjectInStudent(String subjectType, Student student, List<Subject> subjectList) {
        boolean flag = true;  // 과목 선택을 더 할 지 여부를 담을 boolean 변수
        int minJoin = 0;
        int totalCnt = 0;

        switch (subjectType) {
            case DataBase.SUBJECT_TYPE_MANDATORY :
                minJoin = this.db.getSujectMinMandatory();
                totalCnt = this.db.getSubjectCntMandatory();
                break;
            case DataBase.SUBJECT_TYPE_CHOICE :
                minJoin = this.db.getSubjectMinChoice();
                totalCnt = this.db.getSubjectCntChoice();
                break;
            default :
        }

        while (flag) {
            System.out.println("\n----------------------------------");
            System.out.printf("수강 %s 과목 등록 중...\n", subjectType);
            List<Subject> joinedSubject = student.getSubjectList(subjectType);
            Subject subject = this.inOut.inSubjectId(subjectType, subjectList, joinedSubject);

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
                    String more = this.inOut.enterType(this.inOut.concatStrings("\n", subjectType, " 과목 수강 신청을 더 하시겠습니까? (더 수강 신청 more 입력)"));
                    flag = more.equals("more");
                }
            } catch (NotReachedMinJoin e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
            }
        }
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    public void addScoreInSubject() {
        System.out.println("\n==================================");
        System.out.println("수강생의 과목별 시험 회차 및 점수 등록 실행 중...\n");

        // 수강생 고유 번호 입력
        Student student = this.inOut.inStudentId();
        String studentId = student.getStudentId();

        while (true) {
            // 과목 분류 선택
            String subjectType = this.inOut.inSubjectType(1);

            // 과목 선택
            Subject subject = this.inOut.inSubjectId(student, subjectType);
            subjectType = subject.getSubjectType();
            String subjectId = subject.getSubjectId();
            String subjectName = subject.getSubjectName();

            // 회차 유효성 검사
            int round = student.getLastRound(subjectId) + 1;

            try {
                this.ck.roundUnder10(round);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
                System.out.println("\n현재 페이지를 종료하고 이전 페이지로 돌아갑니다.");
                return;
            }

            // 점수 등록
            int testScore = this.inOut.inTestScore(subjectName, round);

            // 등급 계산
            String grade = this.getGrade(testScore, subjectType);

            // Score 객체 생성
            Score score = new Score(this.db.sequence(DataBase.INDEX_TYPE_SCORE), studentId, subjectName, round, testScore, grade);

            // 점수 재확인
            System.out.printf("%s번 학생의 %s 과목 %d회차 점수가 %d, 랭크가 %s로 등록됩니다.", score.getStudentId(), score.getSubjectName(), score.getRound(), score.getTestScore(), score.getGrade());

            // 점수 저장
            student.addScore(subjectId, score);
            this.db.addScore(score);

            try {
                this.inOut.inExit("현재 수강생의 과목별 시험 회차 및 점수 등록");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    // 수강생의 과목별 회차 점수 수정
    public void editNthScoreOfSubject() {
        // 수강생 고유 번호 입력
        Student student = this.inOut.inStudentId();

        while (true) {
            // 과목 분류 선택
            String subjectType = this.inOut.inSubjectType(1);
            // 과목 선택
            Subject subject = this.inOut.inSubjectId(student, subjectType);
            subjectType = subject.getSubjectType();
            String subjectId = subject.getSubjectId();
            int round = 0;

            try {
                round = this.inOut.inRound(student, subjectId);
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
                return;
            }

            System.out.println("해당 회차의 점수를 가져오는 중...\n");
            Score score = student.getScore(subjectId, round);
            int newScore = this.inOut.inTestScore(score);

            if (newScore == -1) {
                return;
            }

            String newGrade = this.getGrade(newScore, subjectType);       // 위에서 받은 과목 타입이랑 새로운 점수를 넣어서 새로운 등급을 받는다
            score.setScore(newScore, newGrade);                     // 새로운 점수 수정

            System.out.printf("%s. %s 수강생의 %s 과목 %d 회차의 점수가 %d, 등급이 %s로 수정되었습니다.\n",
                    score.getStudentId(),
                    student.getStudentName(),
                    score.getSubjectName(),
                    score.getRound(),
                    score.getTestScore(),
                    score.getGrade()
            );

            try {
                this.inOut.inExit("현재 수강생의 과목별 회차 점수 수정");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    // 수강생의 특정 과목 회차별 등급 조회
    public void displayGradesOfSubject() {
        System.out.println("\n==================================");
        System.out.println("수강생의 특정 과목 회차별 등급 조회 실행 중...\n");
        // 수강생 고유 번호 입력
        Student student = this.inOut.inStudentId();

        // 과목 분류 선택
        String subjectType = this.inOut.inSubjectType(1);

        // 과목 선택
        Subject subject = this.inOut.inSubjectId(student, subjectType);
        String subjectId = subject.getSubjectId();

        // 점수 리스트 가져오기
        List<Score> scoreList = null;

        try {
            scoreList = student.getScoreList(subjectId);
        } catch (NotExistException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("\n----------------------------------");
        System.out.println("회차별 등급을 조회중...\n");
        System.out.printf("%s. %s 수강생의 %s 과목 회차별 등급\n", student.getStudentId(), student.getStudentName(), subject.getSubjectName());
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("| 회차 |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |  10  |");
        System.out.print("| 등급 |");

        for (int i = 0; i < 10; i++) {
            if (i >= scoreList.size()) {
                System.out.printf("%s  -  |", (i == 9) ? " " : "");
                continue;
            }
            Score score = scoreList.get(i);
            String grade = score.getGrade();

            System.out.printf("  %s  |", grade);
        }
        System.out.println("\n----------------------------------------------------------------------------------------");
    }



    // 수강생 정보 조회
    public void displayStudentInfo() {
        System.out.println("\n==================================");
        System.out.println("수강생 정보 조회 실행 중...\n");
        // 수강생 고유 번호 입력
        Student student = this.inOut.inStudentId();
        this.displayStudentInfo(student);
    }

    // 매개변수로 받은 수강생 정보 조회
    public void displayStudentInfo(Student student) {
        System.out.println("----------------------------------");
        System.out.printf("\n고유 번호 : %s\n이름: %s\n상태: %s\n수강 과목:\n",
                student.getStudentId(),
                student.getStudentName(),
                student.getStatus()
        );

        List<Subject> subjectList = student.getAllSubjects();

        for (Subject subject : subjectList) {
            System.out.printf("- %s\n", subject.getSubjectName());
            try {
                List<Score> scoreList = student.getScoreList(subject.getSubjectId());
                System.out.println("  [회차별 점수(등급)] ");
                System.out.print("  ");
                for (Score score : scoreList) {
                    System.out.printf(" %d회차 : %d(%s) /", score.getRound(), score.getTestScore(), score.getGrade());
                }
                System.out.println();
            } catch (NotExistException e) {
                continue;
            }

        }
    }

    // 수강생 정보 수정
    public void editStudentInfo() {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("수강생 정보 수정 실행 중...\n");
            System.out.println("1. 이름");
            System.out.println("2. 상태");
            System.out.println("3. 이전 페이지로 돌아가기");
            int input = this.inOut.enterType("\n실행할 항목을 선택해 주십시오.", 1, 3, 0);

            switch (input) {
                case 1 :
                    this.editStudentName();
                    break;
                case 2 :
                    this.editStudentStatus();
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
    public void editStudentName() {
        // 수강생 고유 번호 입력
        Student student = this.inOut.inStudentId();
        String preName = student.getStudentName();  // 기존 이름

        // 수정할 이름 입력
        String newName = this.inOut.inStudentName(preName);

        if (newName.equals("ex1t")) {
            return;
        }
        student.setStudentName(newName);
        System.out.printf("%s. %s 수강생의 이름이 %s로 변경되었습니다.\n", student.getStudentId(), preName, student.getStudentName());
    }

    // 수강생 상태 수정
    public void editStudentStatus() {
        // 수강생 고유 번호 입력
        Student student = this.inOut.inStudentId();
        Status preStatus = student.getStatus();  // 기존 상태

        Status newStatus = this.inOut.inStatus(preStatus);

        if (newStatus == null) {
            return;
        }

        preStatus.removeStudent(student);
        newStatus.addStudent(student);
        student.setStatus(newStatus);
        System.out.printf("%s. %s 수강생의 상태가 %s로 변경되었습니다.\n", student.getStudentId(), student.getStudentName(), student.getStatus().toString());
    }

    // 상태별 수강생 목록 조회
    public void displayStudentsInStatus() {
        System.out.println("\n==================================");
        System.out.println("상태별 수강생 목록 조회 실행 중...\n");

        // 조회할 상태 입력
        Status status = this.inOut.inStatus();

        if (status == null) {
            return;
        }

        System.out.println("\n----------------------------------");
        System.out.printf("상태가 %s인 수강생 목록 가져오는 중...\n", status);
        List<Student> studentList = status.getStudentList();
        System.out.printf("\n상태가 %s인 수강생 목록:\n", status);

        try {
            this.inOut.printStudentList(studentList);
        } catch (NotExistException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getHint());
        }
    }

    // 수강생 삭제
    public void deleteStudent() {
        System.out.println("\n==================================");
        System.out.println("수강생 삭제 실행 중...\n");

        // 수강생 고유 번호 입력
        Student student = this.inOut.inRemoveStudentId();
        String studentId = student.getStudentId();
        String studentName = student.getStudentName();

        String remove = this.inOut.enterType(String.format("\n[%s. %s] 수강생을 정말 삭제하시겠습니까? (삭제 : remove 입력)", studentId, studentName));

        if (remove.equals("remove")) {
            // 점수 객체 삭제
            List<Subject> subjectList = student.getAllSubjects();
            for (Subject subject : subjectList) {
                try {
                    List<Score> scoreList = student.getScoreList(subject.getSubjectId());
                    for (Score score : scoreList) {
                        this.db.removeScore(score);
                    }
                } catch (NotExistException e) {
                    continue;
                }
            }

            // Status의 리스트에서 수강생 객체 삭제
            Status status = student.getStatus();
            status.removeStudent(student);

            // 수강생 삭제
            this.db.removeStudent(student);
        }
    }

    // 수강생의 과목별 평균 등급 조회
    public void displaySubjectAvgGrade() {
        System.out.println("\n==================================");
        System.out.println("수강생의 과목별 평균 등급을 조회 실행 중...\n");

        // 수강생 고유 번호 입력
        Student student = this.inOut.inStudentId();
        System.out.printf("\n%s. %s의 과목별 평균 등급 조회 결과\n\n", student.getStudentId(), student.getStudentName());

        this.displaySubjectAvgGradesInSubjectType(student, DataBase.SUBJECT_TYPE_MANDATORY);
        this.displaySubjectAvgGradesInSubjectType(student, DataBase.SUBJECT_TYPE_CHOICE);
    }

    // 수강생의 과목 타입별 과목 평균 등급 조회
    public void displaySubjectAvgGradesInSubjectType(Student student, String subjectType) {
        System.out.printf("[%s 과목의 과목별 평균 등급]\n", subjectType);

        List<Subject> subjectList = student.getSubjectList(subjectType);
        for (Subject subject : subjectList) {
            double subjectTotal = 0;

            try{
                List<Score> scoreList = student.getScoreList(subject.getSubjectId());

                for (Score score : scoreList) {
                    subjectTotal += score.getTestScore();
                }

                double subjectAvg = subjectTotal / scoreList.size();
                String subejectAvgGrade = this.getGrade(subjectAvg, subjectType);
                System.out.printf("- %s : %s 등급\n", subject.getSubjectName(), subejectAvgGrade);
            } catch (NotExistException e) {
                continue;
            }
        }
    }

    // 특정 상태 수강생들의 필수 과목 평균 등급을 조회
    public void displayMandatorySubjectAvgGradeInStatus() {
        System.out.println("\n==================================");
        System.out.println("특정 상태 수강생들의 필수 과목 평균 등급을 조회 중...\n");
        // 상태 입력
        Status status = this.inOut.inStatus();

        if (status == null) {
            return;
        }

        List <Student> studentList = status.getStudentList();

        try {
            this.ck.notEmptyStudentList(studentList);
        } catch (NotExistException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getHint());
            return;
        }

        System.out.println("\n----------------------------------");
        System.out.printf("[ 상태 : %s ] 수강생들의 필수 과목 평균 등급 조회 결과\n", status);
        assert studentList != null;
        for (Student student : studentList) {
            String studentId = student.getStudentId();
            String studentName = student.getStudentName();
            List<Subject> mandatorySubjects = student.getSubjectList(DataBase.SUBJECT_TYPE_MANDATORY);

            int totalScore = 0;
            int count = 0;

            for (Subject subject : mandatorySubjects) {
                try {
                    List<Score> scoreList = student.getScoreList(subject.getSubjectId());

                    for (Score score : scoreList) {
                        totalScore += score.getTestScore(); // 점수 합산
                        count++;
                    }

                } catch (NotExistException e) {
                    continue;
                }
            }

            if (count == 0) {
                System.out.printf("%s. %s [ - ]\n", studentId, studentName);
            } else {
                // 평균 점수 계산
                double avgScore = (double) totalScore / count;
                // 평균 점수를 기반으로 등급 계산
                String avgGrade = this.getGrade(avgScore, DataBase.SUBJECT_TYPE_MANDATORY);
                System.out.printf("%s. %s [ %s등급 ]\n", studentId, studentName, avgGrade);
            }
        }
        System.out.printf("\n[ 총 %d명의 수강생이 조회되었습니다. ]\n", studentList.size());
        System.out.println("(필수 과목 점수가 등록되어 있지 않은 수강생의 등급은 [ - ]로 표시됩니다.)");
    }

    private String getGrade(double score, String subjectType){
        if (subjectType.equals(DataBase.SUBJECT_TYPE_MANDATORY)) {
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
        } else if (subjectType.equals(DataBase.SUBJECT_TYPE_CHOICE)) {
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
}
