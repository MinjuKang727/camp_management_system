# **🎥 13BEST **
+ 기간 :  2024.08.01(목) ~ 2024.08.08(목)
+ 소개 : 수강생들을 관리하는 프로그램
+ 팀 인원: 강민주, 유현식, 허담, 정이삭, 김태준

 **💡필수 요구사항**
+ 수강생 관리
   1. 수강생 정보 등록
   2. 수강생 목록 조회
+ 점수 관리
   1. 수강생 과목별 시험 회차 및 점수 등록
   2. 수강생 과목별 회차 점수 수정
   3. 수강생 특정 과목 회차별 등급 조회 
  **추가 요구사항**
+  수강생 관리
   1. 수강생 상태 관리
   2. 수강생 정보 조회
   3. 수강생 정보 수정
   4. 상태별 수강생 목록 조회
   5. 수강생 삭제
+  점수 관리
   1. 수강생 과목별 평균 등급 조회
   2. 특정 상태 수강생 필수 과목 평균등급 조회
- - - - - - - - - -
## Code Description

### CampManagementApplication
sequence(String type): 고유한 인덱스를 생성하는 메서드입니다 <br><br>
displayMainView(): 메인 화면을 표시<br><br>
displayStudentView(): 수강생 관리 화면을 표시<br><br>
createStudent(): 수강생을 등록<br><br>
inquireStudent(): 수강생 목록을 조회<br><br>
displayScoreView(): 점수 관리 화면을 표시<br><br>
getStudentId(): 관리할 수강생의 고유 번호를 입력<br><br>
createScore(): 수강생의 과목별 시험 회차 및 점수를 등록<br><br>
updateRoundScoreBySubject(): 수강생의 과목별 회차 점수를 수정<br><br>
inquireRoundGradeBySubject(): 수강생의 특정 과목 회차별 등급을 조회<br><br>

-----------------------------
### Score

+ scoreId : 각 점수 항목의 고유 ID 
  + getScoreId()

+ studentId : 학생 고유 ID
  + getStudentId()

+ subjectName : 과목명
  + getSubjectName()

+ testCnt : 시험 회차
  + getTestCnt() 

+ tsetScore : 시험 점수
  + getTsetScore() 

+ rank : 점수 등급
  + getRank() 

### Student
+ Student : 학생의 정보를 저장하고, 학생이 신청한 과목들을 관리하는 역할
  +학생의 ID와 이름을 매개변수로 받아 초기화

+ studentId : 학생 고유 ID (getStudentId)

+ studentName : 학생 이름 (getStudentName)

+ subjectMap : 필수 과목과 선택 과목을 구분하여 저장
  
  + HashMap<String, ArrayList<Subject>> 필드 추가
 
  + 생성자(subjectMap)초기화
    
  + 두 개의 키("MANDATORY"와 "CHOICE")를 가지며, 객체가 생성될 때마다 ArrayList가 각각의 키에 할당
 
   
+ addSubject() : 학생이 과목을 신청할 때 사용되며, 과목을 맵에 추가하는 기능

  + Subject 객체를 매개변수로 받아 해당 과목의 타입에 따라 subjectMap에 추가
   
  + 성공되면 true를 반환 실패하면 false를 반환



------------------------------------
