package camp.model;

import camp.CampManagementApplication;

import java.util.regex.Pattern;

public class CheckValidity {
    public boolean selecterRange(int min, int max, int input) throws BadInputException {
        if (input < min || input > max) {
            throw new BadInputException("\n항목를 잘못 선택하셨습니다.", min + " 이상 " + max + " 이하의 정수만 입력 가능합니다.");
        } else {
            return false;
        }
    }

    public boolean testCntRange(int testCnt) throws BadInputException {
        if (testCnt <= 0 || testCnt > 10) {
            throw new BadInputException("\n회차를 잘못 입력하셨습니다.", "1 이상 10 이하의 정수만 입력 가능합니다.");
        } else {
            return false;
        }
    }

    public boolean testScoreRange(int testScore) throws BadInputException {
        if (testScore < 0 || testScore > 100) {
            throw new BadInputException("\n점수를 잘못 입력하셨습니다.", "0 이상 100 이하의 정수만 입력 가능합니다.");
        } else {
            return false;
        }
    }

    public boolean satisfySubjectCnt(Student student, String subjectType, int min, int total) throws BadInputException {
        int joinedCnt = student.getJoinedSJCnt(subjectType);

        if (joinedCnt < min) {
            throw new BadInputException("\n해당 과목의 최소 수강신청 수를 만족하지 못하였습니다.", "현재 " + joinedCnt + "과목 신청\n해당 과목 선택을 계속 진행하겠습니다.");
        } else if (joinedCnt >= total) {
            System.out.println("\n모든 과목을 수강 신청하였습니다.");
            System.out.println("해당 과목 수강 신청이 종료됩니다.");
            return false;
        }

        return true;
    }

    public boolean nameIsEngOrKor(String name) throws BadInputException{
        if (!Pattern.matches("^[a-zA-Z]+$||^[가-힣]+$", name)) {
            throw new BadInputException("\n잘못된 수강생명을 입력하셨습니다.", "영문 이름 혹은 한글 이름만 입력가능합니다.");
        }

        return false;
    }

    public void noneEmptySubjectStore() throws BadInputException {
        CampManagementApplication cma = new CampManagementApplication();

        if (cma.getStudentStore().isEmpty()) {
            throw new BadInputException("\n현재 등록된 수강생이 존재하지 않습니다.", "수강생 등록 후, 해당 기능 이용 가능합니다.");
        }
    }
}
