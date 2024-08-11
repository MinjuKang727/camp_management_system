package camp.model;

import camp.model.Exception.AddSubjectException;
import camp.model.Exception.BadInputException;
import camp.model.Exception.NotExistException;

import java.util.List;
import java.util.regex.Pattern;

// 입력값 유효성 검사 클래스
public class CheckValidity {

    // 선택값 범위 유효성 검사
    public int isNumber(String input) throws BadInputException {
        if (!Pattern.matches("^[0-9]+$", input)) {
            throw new BadInputException("\n숫자가 아닌 값을 입력했습니다.", "자연수를 입력해 주십시오.");
        }

        return Integer.parseInt(input);
    }
    public void selecterRange(int min, int max, int input) throws BadInputException {
        if (input < min || input > max) {
            throw new BadInputException(min, max);
        }
    }

    public void selecterRange(int min, int max, int input, List<Integer> notAllowed) throws BadInputException {
        if (input < min || input > max) {
            throw new BadInputException(min, max);
        }

        if (notAllowed.contains(input)) {
            throw new BadInputException("\n해당 항목은 현재 이용할 수 없습니다.", "다른 항목을 선택해 주십시오.");
        }
    }

    // 회차 범위 유효성 검사
    public void roundUnder10(int round) throws BadInputException {
        if (round > 10) {
            throw new BadInputException("\n이미 10회차까지 점수를 등록하셨습니다.", "해당 회차의 점수를 수정하고 싶으시면, '수강생의 과목별 회차 점수 수정' 페이지를 이용해 주십시오.");
        }
    }


    //  점수  //
    // 점수 범위 유효성 검사
//    public void scoreRange0to100(int score) throws BadInputException {
//        if (score < 0 || score > 100) {
//            throw new BadInputException(0, 100);
//        }
//    }

    // 기존에 등록된 점수와 다른지 체크
    public void notSameScore(int newScore, int preScore) throws BadInputException {
        if (newScore == preScore) {
            throw new BadInputException("\n입력한 점수가 기존 회차에 등록되어 있는 점수와 동일합니다.", "현재 등록되어 있는 점수와 다른 점수를 입력해 주십시오.");
        }
    }

    //  이름  //
    // 한글로만 혹은 영어로만 된 이름인지 체크
    public void nameIsEngOrKor(String studentName) throws BadInputException {
        if (!Pattern.matches("^[a-zA-Z]+$||^[가-힣]+$", studentName)) {
            throw new BadInputException("\n잘못된 수강생 이름을 입력하셨습니다.", "영문 이름 혹은 한글 이름만 입력가능합니다.");
        }
    }

    // 기존에 등록된 이름과 다른 이름인지 체크
    public void notSameName(String newStudentName, String preStudentName) throws BadInputException {
        if (newStudentName.equals(preStudentName)) {
            throw new BadInputException("\n입력한 이름이 기존에 등록되어있는 이름과 동일합니다.", "기존에 등록된 이름과 다른 이름을 입력해 주십시오.");
        }
    }

    //  상태  //
    // 상태값 유효성 검사
//    public Status statusInGYR(String status) throws BadInputException {
//        if (!Pattern.matches("(GREEN)|(YELLOW)|(RED)", status)) {
//                throw new BadInputException("\n유효하지 않은 상태값을 입력하셨습니다.", "수강생 상태는 GREEN, YELLOW, RED 중 하나의 값만 입력가능합니다.");
//        }
//
//        return Status.valueOf(status);
//    }
//
//    // 기존 등록된 상태와 다른지 체크
//    public void notSameStatus(Status newStatus, Status preStatus) throws BadInputException {
//        if (newStatus.equals(preStatus)) {
//            throw new BadInputException("\n입력한 상태가 기존에 등록되어있는 상태와 동일합니다.", "기존에 등록된 상태와 다른 상태를 입력해 주세요.");
//        }
//    }


    //  수강생  //
    // 조회된 수강생 리스트에 수강생이 존재하는지 체크
    public void notEmptyStudentList(List<Student> studentList) throws NotExistException {
        if (studentList.isEmpty()) {
            throw new NotExistException("\n해당 상태의 수강생");
        }
    }


    //  과목  //
    // 과목 타입별 수강신청 최소 과목수 만족 여부 체크
    public void satisfySubjectCnt(Student student, String subjectType, int min, int total) throws AddSubjectException {
        int joinedCnt = student.getSubjectCnt(subjectType);

        if (joinedCnt < min) {
            throw new AddSubjectException( min, joinedCnt);
        } else if (joinedCnt >= total) {
            throw new AddSubjectException();
        }
    }

    // 수강 중인 과목인지 체크
    public void isJoinedSubject(Subject subject, List<Subject> subjectList) throws BadInputException {
        if (!subjectList.contains(subject)) {
            throw new BadInputException("\n수강하고 있지 않은 과목입니다.", "수강 과목 목록에서 과목을 선택해 주십시오.");
        }
    }

    // 미수강 과목인지 체크
    public void notJoinedSubject(Subject subject, List<Subject>subjectList) throws BadInputException {
        if (subjectList.contains(subject)) {
            throw new BadInputException("\n이미 수강 중인 과목입니다.", "과목 목록에서 과목을 선택해 주십시오.");
        }
    }
}
