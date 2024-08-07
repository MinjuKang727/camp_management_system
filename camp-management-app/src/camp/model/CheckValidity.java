package camp.model;

import camp.CampManagementApplication;
import java.util.List;
import java.util.regex.Pattern;

public class CheckValidity {
    // 선택값 범위 유효성 검사
    public void selecterRange(int min, int max, int input) throws BadInputException {
        if (input < min || input > max) {
            throw new BadInputException("\n잘못된 값을 선택하셨습니다.", min + " 이상 " + max + " 이하의 정수만 입력 가능합니다.");
        }
    }

    // 회차 범위 유효성 검사
    public void testCntOver10(int curCnt) throws BadInputException {
        if (curCnt > 10) {
            throw new BadInputException("\n이미 10회차까지 성적을 등록하셨습니다.", "해당 회차의 점수를 수정하고 싶으시면, '수강생의 과목별 회차 점수 수정' 페이지를 이용해 주십시오.");
        }
    }


    //  점수  //
    // 점수 범위 유효성 검사
    public void testScoreRange(int testScore) throws BadInputException {
        if (testScore < 0 || testScore > 100) {
            throw new BadInputException("\n점수를 잘못 입력하셨습니다.", "0 이상 100 이하의 정수만 입력 가능합니다.");
        }
    }

    // 기존에 등록된 점수와 다른지 체크
    public void notSameScore(int newScore, int preScore) throws BadInputException {
        if (newScore == preScore) {
            throw new BadInputException("입력하신 점수가 기존 회차에 등록되어 있는 점수와 동일합니다.", "현재 등록되어 있는 점수와 다른 점수를 입력해 주시거나 Enter 키를 입력하여 점수 수정을 종료해 주십시오.");
        }
    }

    //  이름  //
    // 한글로만 혹은 영어로만 된 이름인지 체크
    public void nameIsEngOrKor(String name) throws BadInputException{
        if (!Pattern.matches("^[a-zA-Z]+$||^[가-힣]+$", name)) {
            throw new BadInputException("\n잘못된 수강생명을 입력하셨습니다.", "영문 이름 혹은 한글 이름만 입력가능합니다.");
        }
    }

    // 기존에 등록된 이름과 다른 이름인지 체크
    public void notSameName(String newName, String preName) throws BadInputException {
        if (newName.equals(preName)) {
            throw new BadInputException("입력한 이름이 기존에 등록되어있는 이름과 동일합니다.", "기존에 등록된 이름과 다른 이름을 입력해 주십시오.");
        }
    }

    //  상태  //
    // 상태값 유효성 검사
    public Status statusInGYR(String status) throws BadInputException {
        if (!Pattern.matches("(GREEN)|(YELLOW)|(RED)", status)) {
                throw new BadInputException("유효하지 않은 상태값을 입력하셨습니다.", "수강생 상태는 GREEN, YELLOW, RED 중 하나의 값만 입력가능합니다.");
        }

        return Status.valueOf(status);
    }

    // 기존 등록된 상태와 다른지 체크
    public void notSameStatus(Status newStatus, Status preStatus) throws BadInputException {
        if (newStatus.equals(preStatus)) {
            throw new BadInputException("입력한 상태가 기존에 등록되어있는 상태와 동일합니다.", "기존에 등록된 상태와 다른 상태를 입력해 주세요.");
        }
    }


    //  수강생  //
    // 수강생이 등록되어 있는지 체크
    public void notEmptyStudentStore(CampManagementApplication cma) throws BadInputException {
        if (cma.getStudentStore().isEmpty()) {
            throw new BadInputException("\n현재 등록된 수강생이 존재하지 않습니다.", "수강생 등록 후, 해당 기능 이용 가능합니다.");
        }
    }


    //  과목  //
    // 과목 타입별 수강신청 최소 과목수 만족 여부 체크
    public boolean satisfySubjectCnt(Student student, String subjectType, int min, int total) throws BadInputException {
        int joinedCnt = student.getSubjectCnt(subjectType);

        if (joinedCnt < min) {
            throw new BadInputException("\n해당 과목은 최소 " + min + "과목 신청하여야합니다.", "현재 " + joinedCnt + "과목 신청\n해당 과목 선택을 계속 진행하겠습니다.");
        } else if (joinedCnt >= total) {
            System.out.println("\n모든 과목을 수강 신청하였습니다.");
            System.out.println("해당 과목 수강 신청이 종료됩니다.");
        }

        return false;
    }

    // 수강 중인 과목인지 체크
    public void isJoinedSubject(Subject subject, List<Subject> subjectList) throws BadInputException {
        if (!subjectList.contains(subject)) {
            throw new BadInputException("수강하고 있지 않은 과목입니다.", "수강 과목 목록에서 과목을 선택해 주십시오.");
        }
    }

    // 미수강 과목인지 체크
    public void notJoinedSubject(Subject subject, List<Subject>joinedSubject) throws BadInputException {
        if (joinedSubject.contains(subject)) {
            throw new BadInputException("이미 수강 중인 과목입니다.", "과목 목록에서 과목을 선택해 주십시오.");
        }
    }
}
