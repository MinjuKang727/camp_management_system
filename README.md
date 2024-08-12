  <p align="center"><a href="https://sequoia-carpet-385.notion.site/13EST-8c48039d6e66406e84d4525c4f964bfc?pvs=4" > <img src="https://github.com/user-attachments/assets/ed91a5bb-9551-4145-a89d-246f5599da34" style="width:30%;"></a></p>



<div align=center>
   <i color="gray">(사진을 클릭하면 노션 페이지로 이동 Go!)</i>
   <br><Br>

<b>기간</b> :  2024.08.01(목) ~ 2024.08.08(목)<br>
<b>소개</b> : 수강생 관리 프로그램<br>
<b>팀 인원</b> : 유현식<i>(팀장)</i>, 강민주, 허담, 정이삭, 김태준<br>
</div>

<br><Br>

## 💡필수 요구사항

+ 수강생 관리
   1. 수강생 정보 등록
   2. 수강생 목록 조회
+ 점수 관리
   1. 수강생 과목별 시험 회차 및 점수 등록
   2. 수강생 과목별 회차 점수 수정
   3. 수강생 특정 과목 회차별 등급 조회
      
## 🚀 추가 요구사항

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
## 💻 Code Description

### 🔖 CampManagementApplication 클래스
> 수강생 관리 프로그램을 처음 실행시키기 위한 클래스

<br><br>

#### 💫 main 메서드  
  > Viewer 클래스 생성 및 Viewer클래스의 displayMainView() 메서드를 실행하여 수강생 관리 프로그램 실행

<pre lang="java">public static main(String[] args)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
{  
      Viewer viewer = new Viewer(3, 2);  
        
      try {  
           viewer.displayMainView();  
      } catch (Exception e) {  
           System.out.println("오류 발생!");  
           System.out.print("[ 오류 원인 ]");  
           System.out.println(e.getMessage());  
           System.out.println("\n프로그램을 종료합니다.");  
      }
}
```
</details>

<br><Br>
### 🔖 Viewer 클래스
> 관리 항목 선택 페이지를 실행시키는 메서드를 모아 놓은 클래스

<br><br>

#### 💫 필드 변수
<pre lang="java">private final InOut inOut;</pre>  
<pre lang="java">private final DataBase db;</pre>
<pre lang="java">private final Management management;</pre>


<br>

#### 💫 생성자
> DataBase, InOut, Management 객체 생성, CheckValidity 객체는 InOut 객체에서 Getter메서드로 가져옴.

<br>

- **기본 생성자**
  > 필수 과목 최소 신청 수는 3, 선택과목 최소 신청 수는 2로 초기화 됨.
  
<pre lang="java">public Viewer()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
{
     this.db = new DataBase();
     this.inOut = new InOut(this.db);
     this.ck = this.inOut.getCheckValidity();
     this.management = new Management(this.inOut);
 }
```
</details>

<br>

- **필수/선택 과목 최소 신청 수를 매개변수로 받아 초기화하는 생성자**
  > @param SUBJECT_MIN_MANDATORY : 수강생 등록 시, 등록해야하는 최소 필수 과목 수  
  > @param SUBJECT_MIN_CHOIECE : 수강생 등록 시, 등록해야 하는 최소 선택 과목 수  
  
<pre lang="java">public Viewer(int SUBJECT_MIN_MANDATORY, int SUBJECT_MIN_CHOICE)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
{
      this.db = new DataBase(SUBJECT_MIN_MANDATORY, SUBJECT_MIN_CHOICE);
      this.inOut = new InOut(this.db);
      this.ck = this.inOut.getCheckValidity();
      this.management = new Management(this.inOut);
}
```
</details>

<br>

#### 💫 메서드
- **메인 화면 뷰**
<pre lang="java">public void displayMainView()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     boolean flag = true;
     List<Integer> notAllowed = List.of(2);

     while (flag) {
         boolean noStudent = this.db.isEmptyStudentStore();

         System.out.println("\n==================================");
         System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...\n");
         System.out.println("1. 수강생 관리");
         System.out.println(this.inOut.concatString("2. 점수 관리", noStudent));
         System.out.println("3. 프로그램 종료");
         int input = this.inOut.enterType("\n관리 항목을 선택해 주십시오.", 1, 3, noStudent, notAllowed, 0);

         switch (input) {
             case 1 -> this.displayStudentView(); // 수강생 관리
             case 2 ->  this.displayScoreView();  // 점수 관리
             case 3 -> flag = false; // 프로그램 종료
         }
     }
     System.out.println("프로그램을 종료합니다.");
 }
```
</details>

<br>

- **수강생 관리 뷰**
<pre lang="java"> public void displayStudentView()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     boolean flag = true;
     List<Integer> notAllowed = List.of(2, 3, 4, 5, 6);

     while (flag) {
         boolean noStudent = this.db.isEmptyStudentStore();

         System.out.println("\n==================================");
         System.out.println("수강생 관리 실행 중...\n");
         System.out.println("1. 수강생 등록");
         System.out.println(this.inOut.concatString("2. 수강생 목록 조회", noStudent));
         System.out.println(this.inOut.concatString("3. 수강생 정보 조회", noStudent));
         System.out.println(this.inOut.concatString("4. 수강생 정보 수정", noStudent));
         System.out.println(this.inOut.concatString("5. 상태별 수강생 목록 조회", noStudent));
         System.out.println(this.inOut.concatString("6. 수강생 삭제", noStudent));
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
```
</details>

<br>

- **점수 관리 뷰**
<pre lang="java">public void displayScoreView()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
```
</details>

<Br><br>

### 🔖 Management 클래스
> 수강생 관리 메서드를 모아 놓은 클래스

<br><br>

#### 💫 필드 변수
<pre lang="java">private final DataBase db;</pre>
<pre lang="java">private final InOut inOut;</pre>
<pre lang="java">private final CheckValidity ck;</pre>

<br>

#### 💫 생성자
> Viewer 클래스에서 생성한 InOut 클래스를 매개변수로 받아 필드변수 초기화
> @param InOut : Inout 객체
<pre lang="java">public Management(InOut inOut)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.inOut = inOut;
     this.db = this.inOut.getDataBase();
     this.ck = this.inOut.getCheckValidity();
 }
```
</details>

<br>

#### 💫 메서드
- **수강생 등록**
  
<pre lang="java">public void addStudent()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
     Status status;

     try {
         status = this.inOut.inStatus();
     } catch (ExitThisPage e) {
         System.out.println(e.getMessage());
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
```
</details>

<br>

- **수강생 목록 조회**
<pre lang="java">public void displayStudentList()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
```
</details>

<br>

- **수강생의 신청 과목 추가**
  > @param subjectType : 과목 분류(필수/선택)  
  > @param student : 수강생 객체  
  > @param subjectList : 과목 객체 리스트
  
<pre lang="java">public void addSubjectInStudent(String subjectType, Student student, List<Subject> subjectList)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
             this.ck.satisfySubjectCnt(student, subjectType, minJoin, totalCnt);

             String more = this.inOut.enterType(this.inOut.concatStrings("\n", subjectType, " 과목 수강 신청을 더 하시겠습니까? (더 수강 신청 more 입력)"));
             flag = more.equals("more");
         } catch (AddSubjectException e) {
             System.out.println(e.getMessage());
             System.out.println(e.getHint());
             flag = e.getFlag();
         }
     }
 }
```
</details>

<br>

- **수강생의 과목별 시험 회차 및 점수 등록**
<pre lang="java">public void addScoreInSubject()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
         System.out.println("\n----------------------------------");
            System.out.printf("%s. %s\n - 과목 : %s\n - 회차 : %d회차\n - 점수 : %d점\n - 랭크 : %s\n", 
                    score.getStudentId(), 
                    student.getStudentName(), 
                    score.getSubjectName(), 
                    score.getRound(), 
                    score.getTestScore(), 
                    score.getGrade()
            );
         System.out.println("\n가 등록되었습니다.");

         // 점수 저장
         student.addScore(subjectId, score);
         this.db.addScore(score);

         try {
             this.inOut.inExit("현재 수강생의 과목별 시험 회차 및 점수 등록");
         } catch (ExitThisPage e) {
             System.out.println(e.getMessage());
             return;
         }
     }
 }
```
</details>

<br>

- **수강생의 과목별 회차 점수 수정**
<pre lang="java">public void editNthScoreOfSubject()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
            } catch (NotExistException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getHint());
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
         } catch (ExitThisPage e) {
             System.out.println(e.getMessage());
             return;
         }
     }
 }
```
</details>

<br>

- **수강생의 특정 과목 회차별 등급 조회**
<pre lang="java">public void displayGradesOfSubject()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
```
</details>

<br>

- **수강생 정보 조회**
<pre lang="java">public void displayStudentInfo()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     System.out.println("\n==================================");
     System.out.println("수강생 정보 조회 실행 중...\n");
     // 수강생 고유 번호 입력
     Student student = this.inOut.inStudentId();
     this.displayStudentInfo(student);
 }
```
</details>

<br>

- **매개변수로 받은 수강생 정보 조회**
  > 수강생 정보 조회 및 수강생 등록 후, 등록 내용 확인용으로 사용
  > @param student : 수강생 객체
  
<pre lang="java">public void displayStudentInfo(Student student)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
         } catch (NotExistException ignore) {}

     }
 }
```
</details>

<br>

- **수강생 정보 수정**
<pre lang="java">public void editStudentInfo()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
```
</details>

<br>

- **수강생 이름 수정**
<pre lang="java">public void editStudentName()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
```
</details>

<br>

- **수강생 상태 수정**
<pre lang="java">public void editStudentStatus()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     // 수강생 고유 번호 입력
     Student student = this.inOut.inStudentId();
     Status preStatus = student.getStatus();  // 기존 상태

     Status newStatus;

     try {
         newStatus = this.inOut.inStatus(preStatus);
     } catch (ExitThisPage e) {
         System.out.println(e.getMessage());
         return;
     }

     preStatus.removeStudent(student);
     newStatus.addStudent(student);
     student.setStatus(newStatus);
     System.out.printf("%s. %s 수강생의 상태가 %s로 변경되었습니다.\n", student.getStudentId(), student.getStudentName(), student.getStatus().toString());
 }
```
</details>

<br>

- **상태 별 수강생 목록 조회**
<pre lang="java">public void displayStudentsInStatus()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     System.out.println("\n==================================");
     System.out.println("상태별 수강생 목록 조회 실행 중...\n");

     // 조회할 상태 입력
     Status status;

     try {
         status = this.inOut.inStatus();
     } catch (ExitThisPage e) {
         System.out.println(e.getMessage());
         return;
     }

     System.out.println("\n----------------------------------");
     System.out.printf("상태가 %s인 수강생 목록 가져오는 중...\n", status);
     List<Student> studentList = status.getStudentList();
     System.out.printf("\n상태가 %s인 수강생 목록:\n", status);

     try {
         if (studentList.isEmpty()) {
             throw new NotExistException("등록된 수강생");
         }

         for (Student student : studentList) {
             System.out.printf("%s. %s\n", student.getStudentId(), student.getStudentName());
         }

         System.out.printf("[ 총 %s명의 수강생이 조회되었습니다. ] \n", studentList.size());
     } catch (NotExistException e) {
         System.out.println(e.getMessage());
         System.out.println(e.getHint());
     }
 }
```
</details>

<br>

- **수강생 삭제**
<pre lang="java">public void deleteStudent()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
             } catch (NotExistException ignore) {}
         }

         // Status의 리스트에서 수강생 객체 삭제
         Status status = student.getStatus();
         status.removeStudent(student);

         // 수강생 삭제
         this.db.removeStudent(student);
     }
 }
```
</details>

<br>

- **수강생의 과목별 평균 등급 조회**
<pre lang="java">public void displaySubjectAvgGrade()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     System.out.println("\n==================================");
     System.out.println("수강생의 과목별 평균 등급을 조회 실행 중...\n");

     // 수강생 고유 번호 입력
     Student student = this.inOut.inStudentId();
     System.out.printf("\n%s. %s의 과목별 평균 등급 조회 결과\n\n", student.getStudentId(), student.getStudentName());

     this.displaySubjectAvgGradesInSubjectType(student, DataBase.SUBJECT_TYPE_MANDATORY);
     this.displaySubjectAvgGradesInSubjectType(student, DataBase.SUBJECT_TYPE_CHOICE);
 }
```
</details>

<br>

- **수강생의 과목 타입별 과목 평균 등급 조회**
  > @param student : 수강생 객체  
  > @param subjectType : 과목 분류(필수/선택)
  
<pre lang="java">public void displaySubjectAvgGradesInSubjectType(Student student, String subjectType)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
         } catch (NotExistException ignore) {}
     }
 }
```
</details>

<br>

- **특정 상태 수강생들의 필수 과목 평균 등급을 조회**
<pre lang="java">public void displayMandatorySubjectAvgGradeInStatus()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     System.out.println("\n==================================");
     System.out.println("특정 상태 수강생들의 필수 과목 평균 등급을 조회 중...\n");
     // 상태 입력
     Status status;

     try {
         status = this.inOut.inStatus();
     } catch (ExitThisPage e) {
         System.out.println(e.getMessage());
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

             } catch (NotExistException ignore) {}
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
```
</details>

<br>

- **점수로 등급 반환 메서드**
  > @param score : 점수  
  > @param subjectType : 과목 분류
  > @return 해당 과목 분류의 점수에 해당하는 등급
  
<pre lang="java">private String getGrade(double score, String subjectType)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
{
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
```
</details>

<br><br><br>

---

### 🔖 Student 클래스
> 수강생 정보 관련 변수 및 Getter, Setter 메서드를 모아 놓은 클래스

<br><br>

#### 필드 메서드
- **수강생 고유 번호**
<pre lang="java">private String studentId;</pre>
  
- **수강생 이름**
<pre lang="java"> private String studentName;</pre>
  
- **과목 타입 별 과목 리스트를 담은 Map**
  > (key, value) -> (과목 타입, 과목 객체 리스트)
  
<pre lang="java">private Map<String, List<Subject>> subjectMap;</pre>

- **과목별 점수 리스트를 담은 Map**
  > (key, value) -> (과목 고유 번호, 길이 10의 점수 객체 리스트)
  
<pre lang="java">private Map<String, List<Score>> scoreMap;</pre>

- **상태**
<pre lang="java">private Status status;</pre>


#### 생성자
> @param seq : 수강생 index  
> @param studentName : 수강생 이름

<pre lang="java">public Student(String seq, String studentName)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     public Student(String seq, String studentName) {
     this.studentId = seq;
     this.studentName = studentName;
     this.subjectMap = Map.of(
             DataBase.SUBJECT_TYPE_MANDATORY, new ArrayList<Subject>(),
             DataBase.SUBJECT_TYPE_CHOICE, new ArrayList<Subject>()
     );
     this.scoreMap = new HashMap<String, List<Score>>();
 }
```
</details>

<br>

#### 💫 GETTER 메서드

- **수강생 고유 번호 getter**
  > @return 수강생 고유 번호
  
<pre lang="java">public String getStudentId()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     return this.studentId;
 }
```
</details>

<br>

- **수강생 이름 getter**
  > @return 수강생의 이름
  
<pre lang="java">public String getStudentName()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     return this.studentName;
 }
```
</details>

<br>

- **과목 분류로 과목 객체 리스트 getter**
  > @param subjectType : 과목 분류(필수/선택)
  > @return 매개변수로 받은 과목 분류에 해당하는 등록한 과목 객체 리스트 반환
  
<pre lang="java">public List<Subject> getSubjectList(String subjectType)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     return this.subjectMap.get(subjectType);
 }
```
</details>

<br>

- **등록한 필수/선택 과목 수 getter**
  > @param subjectType : 과목 분류(필수/선택)
  > @return 매개변수로 받은 과목 분류의 등록한 과목 수
  
<pre lang="java">public int getSubjectCnt(String subjectType)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Subject> subjectList = this.subjectMap.get(subjectType);
     return subjectList.size();
 }
```
</details>

<br>

- **과목에 등록된 점수의 마지막 회차 getter**
  > @param subjectId : 과목 고유 번호
  > @return 과목에 등록된 점수의 마지막 회차
  
<pre lang="java">public int getLastRound(String subjectId)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Score> scoreList = this.scoreMap.get(subjectId);
     return scoreList.size();
 }
```
</details>

<br>

- **과목에 등록된 점수 객체 리스트 getter**
  > @param subjectId : 과목 고유 번호
  > @throws NotExistException : 해당 과목에 등록된 점수가 존재하지 않을 때 발생
  > @return 해당 과목에 등록된 점수 객체 리스트
  
<pre lang="java">public List<Score> getScoreList(String subjectId) throws NotExistException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Score> scoreList = this.scoreMap.get(subjectId);

     if (scoreList.isEmpty()) {
         throw new NotExistException("해당 과목에 등록된 점수");
     }

     return scoreList;
 }
```
</details>

<br>

- **과목의 해당 회차 점수 객체 getter**
  > @param subjectId : 과목 고유 번호
  > @param round : 회차
  > @return : 해당 과목, 회차의 점수 객체
  
<pre lang="java">public Score getScore(String subjectId, int round)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Score> scoreList = this.scoreMap.get(subjectId);
     return scoreList.get(round - 1);
 }
```
</details>

<br>

- **수강생이 등록한 모든 과목 리스트 getter**
  > @return 수강생이 등록한 모든 과목 리스트
  
<pre lang="java">public List<Subject> getAllSubjects()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Subject> subjectList = new ArrayList<>();
     subjectList.addAll(this.subjectMap.get(DataBase.SUBJECT_TYPE_MANDATORY));
     subjectList.addAll(this.subjectMap.get(DataBase.SUBJECT_TYPE_CHOICE));
     return subjectList;
 }
```
</details>

<br>

- **수강생 상태 getter**
  > @return 현재 수강생 상태 객체
   
<pre lang="java">public Status getStatus()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     return this.status;
 }
```
</details>

<br>

#### 💫 SETTER
- **과목 추가**
  > @param subject : 과목 객체
  > @throws BadInputException : 매개변수의 과목 객체가 이미 등록되어 있는 과목일 때, 발생
  
<pre lang="java">public void addSubject(Subject subject) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Subject> subjectList = this.subjectMap.get(subject.getSubjectType());
     if (subjectList.contains(subject)) {
         throw new BadInputException("이미 선택한 과목입니다.", "과목 고유 번호 를 다시 입력해 주십시오.");
     }

     try {
         String subjectId = subject.getSubjectId();
         subjectList.add(subject);
         Collections.sort(subjectList, Comparator.comparing(Subject::getSubjectId));

         this.scoreMap.put(subjectId, new ArrayList<>(10));
     } catch (Exception e) {
         throw new BadInputException("선택한 과목의 수강 신청 실패", "과목을 다시 선택해 주십시오.");
     }
 }
```
</details>

<br>

- **점수 등록**
  > @param subjectId : 과목 고유 번호
  > @param score : 등록할 점수 객체
  
<pre lang="java">public void addScore(String subjectId, Score score)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Score> scoreList = this.scoreMap.get(subjectId);
     scoreList.add(score);
 }
```
</details>

<br>

- **상태 setter**
  > @param status : 등록할 상태 객체
  
<pre lang="java">public void setStatus(Status status)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
   this.status = status;
 }
```
</details>

<br>

- **수강생 이름 setter**
  > @param studentName : 수강생 이름
  
<pre lang="java">public void setStudentName(String studentName)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.studentName = studentName;
 }
```
</details>

<br><br><br>

---

### 🔖 Score 클래스
> 점수 관련 변수 및 Getter, Setter 메서드를 모아 놓은 클래스

<br><br>

#### 💫 필드 변수
- **과목 고유 번호**
<pre lang="java">private String scoreId;</pre>
- **수강생 고유 번호**
<pre lang="java">private String studentId;</pre>
- **과목 이름**
<pre lang="java">private String subjectName;</pre>
- **회차**
<pre lang="java">private int round;</pre>
- **점수**
<pre lang="java">private int testScore;</pre>
- **등급**
<pre lang="java">private String grade;</pre>

<br>

#### 💫 생성자
> @param seq : 점수 index
> @param studentId : 수강생 고유 번호
> @param subjectName : 과목 이름
> @param round : 회차
> @param testScore : 점수
> @param grade : 등급

<pre lang="java">public Score(String seq, String studentId, String subjectName, int round, int testScore, String grade)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.scoreId = seq;
     this.studentId = studentId;
     this.subjectName = subjectName;
     this.round = round;
     this.testScore = testScore;
     this.grade = grade;
 }
```
</details>

<br>

#### 💫 GETTER 메서드

- **점수 고유 번호 getter**
  > @ return 점수 고유 번호
  
<pre lang="java">public String getScoreId()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return scoreId;
 }
```
</details>

<br>

- **수강생 고유 번호 getter**
  > @return 수강생 고유 번호
  
<pre lang="java">public String getStudentId()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return studentId;
 }
```
</details>

<br>

- **과목 이름 getter**
<pre lang="java">public String getSubjectName()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return subjectName;
 }
```
</details>

<br>

- **회차 getter**
  > @return 회차
  
<pre lang="java">public int getRound()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return round;
 }
```
</details>

<br>

- **점수 getter**
  > @return 점수
  
<pre lang="java">public int getTestScore()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return testScore;
 }
```
</details>

<br>

- **등급 getter**
  > @return 등급
<pre lang="java">public String getGrade()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return grade;
 }
```
</details>

<br>

#### 💫 SETTER 메서드
- **과목 이름 setter**
  > @param subjectName : 과목 이름

<pre lang="java">public void setSubjectName(String subjectName)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      this.subjectName = subjectName;
 }
```
</details>

<br>

- **회차 setter**
  > @param round : 회차
  
<pre lang="java">public void setRound(int round)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      this.round = round;
 }
```
</details>

<br>

- **점수 setter**
  >
<pre lang="java">public void setTestScore(int testScore)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      this.testScore = testScore;
 }
```
</details>

<br>

- **점수, 등급 setter**
  > @param testScore : 점수
  > @param grade : 등급
  
<pre lang="java">public void setScore(int testScore, String grade)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.testScore = testScore;
     this.grade = grade;
 }
```
</details>

<br>

- **등급 Setter**
<pre lang="java">public void setGrade(String grade)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      this.grade = grade;
 }
```
</details>

<br><br><br>

---

### 🔖 Status 클래스
> 수강생 상태 관련 열거형(enum) 클래스

<br><br>

#### 💫 열거 상수
<pre lang="java">GREEN, YELLOW, RED;</pre>

#### 💫 필드 변수
- **상태가 GEEN인 수강생 리스트**
<pre lang="java">private List<Student> greenStudentList = new ArrayList<>();</pre>
- **상태가 YELLOW인 수강생 리스트**
<pre lang="java">private List<Student> yellowStudentList = new ArrayList<>();</pre>
- **상태가 RED인 수강생 리스트**
<pre lang="java">private List<Student> redStudentList = new ArrayList<>();</pre>

<br>

#### 💫 메서드
- **현재 상태의 수강생 리트스 getter**
<pre lang="java">public List<Student> getStudentList()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     return switch (this) {
         case GREEN -> greenStudentList;
         case YELLOW -> yellowStudentList;
         case RED -> redStudentList;
     };
 }
```
</details>

<br>

- **해당 상태의 수강생 리스트에 수강생 추가**
  > @param student : 수강생 객체
  
<pre lang="java">public void addStudent(Student student)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     switch (this) {
         case GREEN -> greenStudentList.add(student);
         case YELLOW -> yellowStudentList.add(student);
         case RED -> redStudentList.add(student);
     }
 }
```
</details>

<br>

- **해당 상태의 수강생 리스트에서 수강생 삭제**
  > @param student : 수강생 객체
<pre lang="java">public void removeStudent(Student student)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     switch (this) {
         case GREEN -> greenStudentList.remove(student);
         case YELLOW ->yellowStudentList.remove(student);
         case RED ->redStudentList.remove(student);
     }
 }
```
</details>

<br><br><br>

---

### 🔖 Subject 클래스
> 과목 관련 변수 및 Getter 메서드를 모아 놓은 클래스

<br><br>

#### 💫 필드 변수
- **과목 고유 번호**
<pre lang="java">private String subjectId;</pre>
- **과목 이름**
<pre lang="java">private String subjectName;</pre>
- **과목 분류**
<pre lang="java">private String subjectType;</pre>

<br>

#### 💫 생성자
> @param seq : 과목 index
> @param subjectName : 과목 이름
> @param subjectType : 과목 분류(필수/선택)

<pre lang="java">public Subject(String seq, String subjectName, String subjectType)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.subjectId = seq;
     this.subjectName = subjectName;
     this.subjectType = subjectType;
 }
```
</details>

<br>

#### 💫 메서드
- **과목 고유 번호 getter**
<pre lang="java">public String getSubjectId()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     return subjectId;
 }
```
</details>

<br>

- **과목 이름 getter**
<pre lang="java">public String getSubjectName()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
  {
     return subjectName;
 }
```
</details>

<br>

- **과목 분류 getter**
<pre lang="java">public String getSubjectType()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     return subjectType;
 }
```
</details>

<br><br><br>

---

### 🔖 DataBase 클래스
> 수강생, 과목, 점수 객체 리스트 or Map 및 관련 초기값 설정 변수, 관련 메서드들을 모아 놓은 클래스

<br><br>

#### 💫 데이터 저장 리스트
- **전체 수강생 리스트**
<pre lang="java">private List<Student> studentStore;</pre>
- **전체 과목 리스트**
<pre lang="java">private List<Subject> subjectStore;</pre>
<br>

#### 💫 데이터 저장 맵
- **수강생 Map**
  > (key, value) -> (수강생 고유 번호, 수강생 객체)
<pre lang="java">private Map<String, Student> studentMap;</pre>
- **과목 Map**
  > {key, (key, value)} -> {과목 분류(필수/선택), (과목 고유 번호, 과목 객체)}
<pre lang="java">private Map<String, Map<String, Subject>>  subjectMap;</pre>
<Br>

#### 💫 과목 분류
- **과목 분류 키워드 "필수"**
<pre lang="java">public static final String SUBJECT_TYPE_MANDATORY = "필수";</pre>
- **과목 분류 키워드 "선택"**
<pre lang="java">public static final String SUBJECT_TYPE_CHOICE = "선택";</pre>
- **과목 분류 키워드 "전체"**
<pre lang="java">public static final String SUBJECT_TYPE_ALL = "전체";</pre>

<br>

#### 💫 과목 분류별 전체 과목 수
- **전체 필수 과목 수**
<pre lang="java">private int SUBJECT_CNT_MANDATORY;</pre>
- **전체 선택 과목 수**
<pre lang="java">private int SUBJECT_CNT_CHOICE;</pre>

<br>

#### 💫 수강생 등록 시, 신청해야하는 과목 수
- **수강생 등록 시, 신청해야하는 최소 필수 과목 수**
<pre lang="java">private final int SUBJECT_MIN_MANDATORY;</pre>
- **수강생 등록 시, 신청해야하는 최소 선택 과목 수**
<pre lang="java">private final int SUBJECT_MIN_CHOICE;</pre>
<br>

#### 💫 index 관리 필드
- **수강생 index**
<pre lang="java">private int studentIndex;</pre>
- **수강생 index 분류 키워드**
<pre lang="java">public static final String INDEX_TYPE_STUDENT = "ST";</pre>
- **과목 index**
<pre lang="java">private int subjectIndex;</pre>
- **수강생 index 분류 키워드**
<pre lang="java">public static final String INDEX_TYPE_SUBJECT = "SU";</pre>
- **점수 index**
<pre lang="java">private int scoreIndex;</pre>
- **점수 index 분류 키워드**
<pre lang="java">public static final String INDEX_TYPE_SCORE = "SC";</pre>

<br>

#### 💫 생성자

- **기본 생성자**
  > 과목 분류별 최소 등록 과목 수, 데이터 저장 리스트, 맵 초기화
  > (과목 분류별 최소 등록 과목 수 초기화 - 필수 : 3 / 선택 : 2)
<pre lang="java">public DataBase()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.SUBJECT_MIN_MANDATORY = 3;
     this.SUBJECT_MIN_CHOICE = 2;

     this.setInitData();
 }
```
</details>

<br>

- **과목 분류별 최소 등록 과목 수를 매개변수로 받는 생성자**
  > @param SUBJECT_MIN_MANDATORY : 필수 과목 최소 등록 수
  > @param SUBJECT_MIN_CHOICE : 선택 과목 최소 등록 수
  > 매개변수로 받은 값으로 과목 분류별 최소 등록 수를 초기화
<pre lang="java">public DataBase(int SUBJECT_MIN_MANDATORY, int SUBJECT_MIN_CHOICE)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.SUBJECT_MIN_MANDATORY = SUBJECT_MIN_MANDATORY;
     this.SUBJECT_MIN_CHOICE = SUBJECT_MIN_CHOICE;

     this.setInitData();
 }
```
</details>

<br>

#### 💫 메서드
- **초기 데이터 생성 메서드**
<pre lang="java">private void setInitData()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.studentStore = new ArrayList<>();
     this.subjectStore = List.of(
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

     this.studentMap = new HashMap<>();
     this.subjectMap = Map.of(
             SUBJECT_TYPE_MANDATORY, new HashMap<String, Subject>(),
             SUBJECT_TYPE_CHOICE, new HashMap<String, Subject>()
     );

     for (Subject subject : this.subjectStore) {
         String subjectType = subject.getSubjectType();
         String subjectId = subject.getSubjectId();
         Map<String, Subject> subjectMap2 = this.subjectMap.get(subjectType);
         subjectMap2.put(subjectId, subject);
     }

     this.SUBJECT_CNT_MANDATORY = this.subjectMap.get(SUBJECT_TYPE_MANDATORY).size();
     this.SUBJECT_CNT_CHOICE = this.subjectMap.get(SUBJECT_TYPE_CHOICE).size();
 }
```
</details>

<br>

- **index 자동 증가 메서드**
<pre lang="java">public String sequence(String type)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     
     switch (type) {
            case INDEX_TYPE_STUDENT -> {
                this.studentIndex++;
                sb.append(INDEX_TYPE_STUDENT);
                sb.append(this.studentIndex);

                return sb.toString();
            }
            case INDEX_TYPE_SUBJECT -> {
                this.subjectIndex++;
                sb.append(INDEX_TYPE_SUBJECT);
                sb.append(this.subjectIndex);

                return sb.toString();
            }
            default -> {
                this.scoreIndex++;
                sb.append(INDEX_TYPE_SCORE);
                sb.append(this.scoreIndex);

                return sb.toString();
            }
        }
 }
```
</details>

<br>

- **수강생 고유 번호로 수강생 객체 getter**
  > @param studentId : 수강생 고유 번호
  > @throws NotExistException : 매개변수로 받은 고유 번호의 수강생이 존재하지 않을 때, 발생
  > @return 수강생 객체
<pre lang="java">public Student getStudentById(String studentId) throws NotExistException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (this.studentMap.containsKey(studentId)) {
         return this.studentMap.get(studentId);
     }

     throw new NotExistException("해당 수강생 고유 번호", "수강생 목록 조회의 수강생 고유 번호를 참고하여 입력해 주십시오.");
 }
```
</details>

<br>

- **과목 분류, 고유 번호로 과목 객체 getter**
  > @param subjectType : 과목 분류(필수/선택/전체)
  > @param subjectId : 과목 고유 번호
  > @throws BadInputException : 매개변수로 받은 과목 고유 번호가 존재하지 않을 때, 발생
  > @return 매개변수로 받은 과목 고유 번호의 과목 객체
<pre lang="java">public Subject getSubjectById(String subjectType, String subjectId) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     Map<String, Subject> sm;

     if (subjectType.equals(SUBJECT_TYPE_CHOICE)) {
         sm = this.subjectMap.get(SUBJECT_TYPE_CHOICE);
     } else {
         sm = this.subjectMap.get(SUBJECT_TYPE_MANDATORY);
     }

     if (sm.containsKey(subjectId)) {
         return sm.get(subjectId);
     } else if (subjectType.equals(SUBJECT_TYPE_ALL)) {
         sm = this.subjectMap.get(SUBJECT_TYPE_CHOICE);

         if (sm.containsKey(subjectId)) {
             return sm.get(subjectId);
         }
     }

     throw new BadInputException("해당 과목 고유 번호");
 }
```
</details>

<br>

- **과목 분류(SUBJECT_TYPE: 필수/선택)를 매개변수로 받는 과목 객체 리스트 getter**
  > @param subjectType : 과목 분류(필수/선택)
  > @return 매개변수로 받은 과목 분류의 과목 객체 리스트
  
<pre lang="java">public List<Subject> getSubjectListByType(String subjectType)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Subject> subjectList = new ArrayList<>(this.subjectMap.get(subjectType).values());
     Collections.sort(subjectList, Comparator.comparing(Subject::getSubjectId));

     return subjectList;
 }
```
</details>

<br>

- **등록된 수강생 존재 여부**
  > @return 등록된 수강생이 존재 : true / 등록된 수강생 없음 : false
  
<pre lang="java">public boolean isEmptyStudentStore()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return this.studentStore.isEmpty();
 }
```
</details>

<br>

- **전체 수강생 리스트 getter**
<pre lang="java">public java.util.List<Student> getStudentStore()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     return studentStore;
 }
```
</details>

<br>

- **수강생 등록 시, 신청해야하는 최소 필수 과목 수 getter**
<pre lang="java">public int getSujectMinMandatory()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return this.SUBJECT_MIN_MANDATORY;
 }
```
</details>

<br>

- **수강생 등록 시, 신청해야하는 최소 선택 과목 수 getter**
<pre lang="java">public int getSubjectMinChoice()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return this.SUBJECT_MIN_CHOICE;
 }
```
</details>

<br>

- **전체 필수 과목 수 getter**
<pre lang="java">public int getSubjectCntMandatory()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return this.SUBJECT_CNT_MANDATORY;
 }
```
</details>

<br>

- **전체 선택 과못 수 getter**
<pre lang="java">public int getSubjectCntChoice()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return this.SUBJECT_CNT_CHOICE;
 }
```
</details>

<br>

- **수강생 리스트, 맵에 수강생 추가**
  > @param student : 수강생 객체
  
<pre lang="java">public void addStudent(Student student)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.studentStore.add(student);
     this.studentMap.put(student.getStudentId(), student);
 }
```
</details>

<br>

- **수강생 리스트, 맵에서 수강생 삭제**
<pre lang="java">public void removeStudent(Student student)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.studentMap.remove(student.getStudentId());
     this.studentStore.remove(student);
 }
```
</details>

<br><br><br>

---

### 🔖 InOut 클래스

<br><br>

#### 💫 필드 변수

- **Scanner 객체**
<pre lang="java">private Scanner sc;</pre>
- **유효성 검사 객체**
<pre lang="java">private CheckValidity ck;</pre>
- **DataBase 객체**
<pre lang="java">private DataBase db;</pre>
<br>

#### 💫 생성자
- **매개변수로 DataBase 객체를 받는 생성자**
  > @param db : Viewer에서 생성한 DataBase객체
  >  -> InOut 클래스의 DataBase 객체를 Viewer 클래스의 DataBase 객체와 동일한 객체로 초기화
  > Scanner, CheckValidity 객체 생성
<pre lang="java">public InOut(DataBase db)</pre>

<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.sc = new Scanner(System.in);
     this.ck = new CheckValidity();
     this.db = db;
 }
```
</details>

<br>

#### 💫 메서드

- **가변 매개변수 문자열 합치기**
  > @params String... str : 가변 매개변수 문자열
  > @return 매개변수를 모두 합친 문자열
  
<pre lang="java">public String concatStrings(String...str)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();

     for (String s : str) {
         sb.append(s);
     }

     return sb.toString();
 }
```
</details>

<br>

- **비활성 상태 문자열 합치기**
  > @param flag : 비활성 상태 : true / 활성 상태 : false
  > @param menu : 문자열
  > @return 비활성 상태이면 menu 끝에 " (비활성)"문자열을 StringBuilder로 붙여서 반환
<pre lang="java">public String activatedOrNot(boolean flag, String menu)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     sb.append(menu);

     if (flag) {
         sb.append(" (비활성)");
     }

     return sb.toString();
 }
```
</details>

<br>

- **정수값 입력, 반환**
  > 정수형 입력값을 받고 유효성 검사(범위: min이상 max이하) 후, 정수형 유효 입력값 or 예외값으로 설정한 정수를 반환한다.
  > @param msg : 입력값 받을 때, 출력할 문자열
  > @param min : 입력값 범위의 최소값
  > @param max : 입력값 범위의 최댓값
  > @param flag : DataBase의 studentStore에 저장된 Student 객체가 존재하는지 여부(Viewer 클래스의 메서드 실행 시, 사용 / 그 외에는 그냥 true로 입력)
  > @param notAllowed : min 이상, max 이하의 범위 내에서 허용되지 않는 입력값 리스트
  > @param exception : 유효하지 않은 값 입력시 반환값
  > @return 유효한 입력값 or (유효하지 않은 입력값 입력 시,) -1
<pre lang="java">public int enterType(String msg, int min, int max, boolean flag, List<Integer> notAllowed, int exception)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
         System.out.println(msg);

         try {
             String strInput = sc.nextLine();
             int input = this.ck.isNumber(strInput);
             if (flag) {
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
```
</details>

<br>

- **정수값 입력, 반환**
  > 정수형 입력값을 받고 유효성 검사(범위: min이상 max이하) 후, 정수형 유효 입력값 or 예외값으로 설정한 정수를 반환한다.
  > @param msg : 입력값 받을 때, 출력할 문자열
  > @param min : 입력값 범위의 최소값
  > @param max : 입력값 범위의 최댓값
  > @param exception : 유효하지 않은 값 입력시 반환값
  > @return 유효한 입력값 or (유효하지 않은 입력값 입력 시,) -1
<pre lang="java">public int enterType(String msg, int min, int max, int exception)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
         System.out.println(msg);

         try {
             String strInput = sc.nextLine();
             int input = this.ck.isNumber(strInput);
             this.ck.selecterRange(min, max, input);
             return input;
         } catch (BadInputException e) {
             System.out.println(e.getMessage());
             System.out.println(e.getHint());
             return exception;
         }
 }
```
</details>

<br>

- **수강생/과목/점수 고유 번호 혹은 문자열 입력값 받는 메서드**
  > 매개변수로 받은 문자열 기준으로 안내메세지 출력하고 입력값 받는 메서드
  > @param type : 입력값의 종류(ST:수강생 고유 번호, SU:과목 고유 번호, SC:점수 고유 번호, 그 외: 입력값 안내 메세지)
  > @return ST+(입력값):수강생  or  SU+(입력값):과목  or  SC+(입력값):점수  or  입력 받은 문자열
  
<pre lang="java">public String enterType(String type)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
```
</details>

<br>

- **수강생 이름 입력**
  > @return 한글로만 or 영어로만 이루어진 수강생 이름
<pre lang="java">public String inStudentName()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
```
</details>

<br>

- **기존 등록되어 있는 수강생 이름과 다른 수강생 이름 입력**
  > @param preName : 기존 등록되어 있는 수강생 이름
  > @return 한글로만 or 영어로만 이루어진 기존에 등록된 이름과 다른 수강생 이름 문자열
  
<pre lang="java">public String inStudentName(String preName)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
         } catch (ExitThisPage e) {
             System.out.println(e.getMessage());
             return "ex1t";
         }
     }
 }
```
</details>

<br>

- **수강생 고유 번호 입력**
  > @return 수강생 객체
<pre lang="java">public Student inStudentId()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     while (true) {
         try {
             System.out.println("----------------------------------");
             String studentId = this.enterType(DataBase.INDEX_TYPE_STUDENT);
             Student student = this.db.getStudentById(studentId);
             return student;
         } catch (NotExistException e) {
             System.out.println(e.getMessage());
             System.out.println(e.getHint());
         }
     }
 }
```
</details>

<br>

- **삭제할 수강생 고유 번호 입력**
  > @return 수강생 객체
<pre lang="java">public Student inRemoveStudentId()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     while (true) {
         try {
             System.out.println("----------------------------------");
             String studentId = this.enterType(DataBase.INDEX_TYPE_STUDENT);
             Student student = this.db.getStudentById(studentId);
             return student;
         } catch (NotExistException notExistException) {
             System.out.println(notExistException.getMessage());

             try {
                 this.inExit("수강생 상태 수정");
             } catch (ExitThisPage e) {
                 System.out.println(e.getMessage());
                 return null;
             }
         }
     }
 }
```
</details>

<br>

- **수강생 상태 선택**
  > @throws ExitThisPage : 이전 페이지로 돌아가지 선택시, 발생
  > @return 상태 객체 or (이전 페이지로 돌아가기 선택 시,) null
  
<pre lang="java">public Status inStatus() throws ExitThisPage</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     while (true) {
         System.out.println("1. GREEN");
         System.out.println("2. YELLOW");
         System.out.println("3. RED");
         System.out.println("4. 이전 페이지로 돌아가기");
         int input = this.enterType("상태를 선택해 주십시오.", 1, 4, 0);

         switch (input) {
             case 1 :
                 return Status.GREEN;
             case 2 :
                 return Status.YELLOW;
             case 3 :
                 return Status.RED;
             case 4 :
                 throw new ExitThisPage();
         }
     }
 }
```
</details>

<br>

- **기존에 등록되어 있는 상태와 다른 상태 선택**
  > @param preStatus : 기존 등록되어 있는 상태 객체
  > @return 기존 상태 다른 상태 객체 or (다른 상태를 입력하지 않고 종료를 원할 때,) null
<pre lang="java">public Status inStatus(Status preStatus) throws ExitThisPage</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Integer> notAllowed = List.of(preStatus.ordinal() + 1);

     while (true) {
         System.out.println("\n----------------------------------");
         System.out.println("수강생 상태 수정 중...\n");
         System.out.printf("현재 수강생 상태 : %s\n\n", preStatus);
         System.out.println(this.activatedOrNot(preStatus.equals(Status.GREEN), "1. GREEN"));
         System.out.println(this.activatedOrNot(preStatus.equals(Status.YELLOW), "2. YELLOW"));
         System.out.println(this.activatedOrNot(preStatus.equals(Status.RED), "3. RED"));
         System.out.println("4. 이전 페이지로 돌아가기");
         int input = this.enterType("상태를 선택해 주십시오.", 1, 4, true, notAllowed, 0);

         switch (input) {
             case 1 :
                 return Status.GREEN;
             case 2 :
                 return Status.YELLOW;
             case 3 :
                 return Status.RED;
             case 4 :
                 throw new ExitThisPage();
         }
     }
 }
```
</details>

<br>

- **과목 분류 선택**
  > @param type : 선택할 분류 항목에 따라 정수를 입력
  >               (0 : 필수/선택/전체, 1 : 필수/선택) 을 선택 항목으로 입력값을 받음
  > @return 선택한 과목 분류 항목 번호
  >         1(= 필수 과목 선택) or 2(= 선택 과목 선택) (or 3(= 전체 과목 선택) (3은 type == 0일 때만 선택 가능))

<pre lang="java">public String inSubjectType(int type)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     while(true) {
         System.out.println("\n----------------------------------");
         System.out.println("과목 분류 선택 중...\n");
         int input = 0;

         if (type == 1) {
             System.out.printf("1. %s 과목    2. %s 과목    3. %s 과목\n", DataBase.SUBJECT_TYPE_MANDATORY, DataBase.SUBJECT_TYPE_CHOICE, DataBase.SUBJECT_TYPE_ALL);
             input = this.enterType("\n과목 분류 선택해 주십시오.", 1, 3, 0);
         } else if (type == 0) {
             System.out.printf("1. %s 과목    2. %s 과목\n", DataBase.SUBJECT_TYPE_MANDATORY, DataBase.SUBJECT_TYPE_CHOICE);
             input = this.enterType("\n과목 분류 선택해 주십시오.", 1, 2, 0);
         }

         switch (input) {
             case 1 :
                 return DataBase.SUBJECT_TYPE_MANDATORY;
             case 2 :
                 return DataBase.SUBJECT_TYPE_CHOICE;
             case 3 :
                 return DataBase.SUBJECT_TYPE_ALL;
         };
     }
 }
```
</details>

<br>

- **수강 중이지 않은 과목 선택**
  > 과목 아이디 입력 -> 과목 객체 반환
  > @param subjectType : 과목 분류 (필수/선택)
  > @param subjectList : subjectType에 해당하는 과목 객체 리스트
  > @param joinedSubjectList : 현재 수강 중인 subjectType에 해당하는 과목 리스트
  > @return 수강 중이지 않은 과목 중 선택한 과목의 객체 반환
  
<pre lang="java">public Subject inSubjectId(String subjectType, List<Subject> subjectList, List<Subject> joinedSubjectList)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
         } catch (NotExistException nee) {
             System.out.println(nee.getMessage());
         } catch (BadInputException bie) {
             System.out.println(bie.getMessage());
             System.out.println(bie.getHint());
         }

     }
 }
```
</details>

<br>

- **수강 중인 과목 선택**
  > 과목 아이디 입력 -> 과목 객체 반환
  > @param student : 학생 객체
  > @param subjectType : 과목 분류 (필수/선택/전체)
  > @return 수강 중인 과목 중 선택한 과목 객체
  
<pre lang="java">public Subject inSubjectId(Student student, String subjectType)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     List<Subject> subjectList;

     if (subjectType.equals(DataBase.SUBJECT_TYPE_ALL)) {
         subjectList = student.getAllSubjects();
     } else {
         subjectList = student.getSubjectList(subjectType);
     }

     while (true) {
         System.out.println("\n----------------------------------");
         System.out.println("과목을 선택 중...\n");
         System.out.printf("[ 수강 중인 %s 과목 목록 ]\n", subjectType);

         for (Subject subject : subjectList) {
             int round = student.getLastRound(subject.getSubjectId());

             System.out.printf("%s. %s (%d회차까지 점수 등록)\n", subject.getSubjectId(), subject.getSubjectName(), round);
         }
         System.out.println("\n");
         String subjectId = this.enterType(DataBase.INDEX_TYPE_SUBJECT);

         try {
             Subject subject = this.db.getSubjectById(subjectType, subjectId);
             this.ck.isJoinedSubject(subject, subjectList);
             return subject;
         } catch (BadInputException bie) {
             System.out.println(bie.getMessage());
             System.out.println(bie.getHint());
         } catch (NotExistException nee) {
             System.out.println(nee.getMessage());
         }

     }
 }
```
</details>

<br>

- **등록할 점수 입력**
  > @param subjectName : 과목 이름
  > @param round : 시험 회차
  > 선택한 회차의 점수를 입력 받고 유효성 검사
  > @return 선택한 회차의 유효 점수 or (유효 점수를 입력하지 않고 종료 시,) -1
  
<pre lang="java">public int inTestScore(String subjectName, int round)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     while (true) {
         System.out.println("\n----------------------------------");
         System.out.printf("[ %s ] %d 회차 점수 등록 중...\n", subjectName, round);
         int testScore = this.enterType("점수를 입력해 주십시오.", 0, 100, -1);

         if (testScore != -1) {
             return testScore;
         }
     }
 }
```
</details>

<br>

- **수정할 점수 입력**
  > @param score : 점수를 수정할 점수 객체
  > @return 기존에 등록된 점수와 다른 유효 점수(범위: 0 이상 100 이하)
            or (유효 점수를 입력하지 않고 종료 시,) -1
<pre lang="java">public int inTestScore(Score score)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
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
         } catch (BadInputException be) {
             System.out.println(be.getMessage());
             System.out.println(be.getHint());

             try {
                 this.inExit("점수 수정");
             } catch (ExitThisPage e) {
                 System.out.println(e.getMessage());
                 newScore =  -1;
                 flag = false;
             }
         }
     }

     return newScore;
 }
```
</details>

<br>

- **점수를 수정할 과목의 회차 입력**
  > @param student : 수강생 객체
  > @param subjectId : 과목 고유 번호
  > @return 점수를 수정할 회차 or  (유효한 회차를 입력하지 않고 종료 시,) 0
  > @throws NotExistException : 해당 과목에 등록된 점수가 존재하지 않을 때, 발생
  
<pre lang="java">public int inRound(Student student, String subjectId) throws NotExistException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     int max = student.getLastRound(subjectId);

     if (max == 0) {
         throw new NotExistException("해당 과목의 등록된 점수", "점수를 1회차 이상 등록 후, 수정 가능합니다.");
     }

     while (true) {
         System.out.println("\n----------------------------------");
         System.out.println("수정 회차 선택 중...\n");
         
         StringBuilder sb = new StringBuilder();
         sb.append("회차를 입력하십시오. (선택 가능 회차 : 1");
         if (max > 1) {
             sb.append(" ~ ");
             sb.append(max);
         }
         sb.append(")");
         
         int round = this.enterType(sb.toString(), 1, max, 0);

         if (round != 0) {
             return round;
         }
     }
 }
```
</details>

<br>

- **종료 여부 입력**
  > @param message : 종료할 페이지 문자열
  > @throws ExitThisPage : 입력값으로 "exit"이 들어오면, 발생
  
<pre lang="java">public void inExit(String message) throws ExitThisPage</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     String exit = this.enterType(this.concatStrings("\n-----------------------------------------------------------------\n", message, "을(를) 종료하시겠습니까? (종료 : exit 입력)"));

     if (exit.equals("exit")) {
         throw new ExitThisPage();
     }
 }
```
</details>

<br>

#### 💫 GETTER

- **DataBase 객체 getter**
<pre lang="java">public DataBase getDataBase()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
{
   return this.db;
}
```
</details>

<br>

- **CheckValidity 객체 getter**
<pre lang="java">public CheckValidity getCheckValidity()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
{
   return this.ck;
}
```
</details>

<br><br><br>

---

### 🔖 CheckValidity 클래스
> 입력값 유효성 검사 메서드를 모아놓은 클래스

<br><br>

#### 💫 메서드
- **숫자인지 검사**
  > @param input : 유효성 검사할 정수
  > @throws BadInputException : input이 0 ~ 9의 숫자로만 이루어지지 않았을 때, 발생
  
<pre lang="java">public int isNumber(String input) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (!Pattern.matches("^[0-9]+$", input)) {
         throw new BadInputException("\n숫자가 아닌 값을 입력했습니다.", "자연수를 입력해 주십시오.");
     }

     return Integer.parseInt(input);
 }
```
</details>

<br>

- **유효 범위 안의 정수인지 검사**
  > @param min : 유효 범위 최솟값
  > @param max : 유효 범위 최댓값
  > @param input : 유효성 검사할 정수
  > @throws BadInputException : input이 min 이상 max 이하에 속하지 않을 때, 발생
  
<pre lang="java">public void selecterRange(int min, int max, int input) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (input < min || input > max) {
         throw new BadInputException(min, max);
     }
 }
```
</details>

<br>

- **유효 범위 안의 정수이면서 허용하지 않는 값이 아닌 것을 검사**
  > @param min : 유효 범위 최솟값
  > @param max : 유효 범위 최댓값
  > @param input : 유효성 검사할 정수
  > @param notAllowed : 허용하지 않는 정수값 리스트
  > @throws BadInputException : input이 min 이상 max 이하 범위에 속하지 않거나 notAllowed에 속할 때, 발생
<pre lang="java">public void selecterRange(int min, int max, int input, List<Integer> notAllowed) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (input < min || input > max) {
         throw new BadInputException(min, max);
     }

     if (notAllowed.contains(input)) {
         throw new BadInputException("\n해당 항목은 현재 이용할 수 없습니다.", "다른 항목을 선택해 주십시오.");
     }
 }
```
</details>

<br>

- **회차 범위 유효성 검사**
  > @param round : 유효성 검사할 회차
  > @throws BadInputException : round가 10보다 클 때, 발생
  
<pre lang="java">public void roundUnder10(int round) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (round > 10) {
         throw new BadInputException("\n이미 10회차까지 점수를 등록하셨습니다.", "해당 회차의 점수를 수정하고 싶으시면, '수강생의 과목별 회차 점수 수정' 페이지를 이용해 주십시오.");
     }
 }
```
</details>

<br>

- **기존에 등록된 점수와 다른지 검사**
  > @param newScore : 유효성 검사할 점수
  > @param preScore : 기존에 등록되어 있는 점수
  > @throws BadInputException : newScore과 preScore과 동일한 값일 때, 발생
  
<pre lang="java">public void notSameScore(int newScore, int preScore) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
    {
     if (newScore == preScore) {
         throw new BadInputException("\n입력한 점수가 기존 회차에 등록되어 있는 점수와 동일합니다.", "현재 등록되어 있는 점수와 다른 점수를 입력해 주십시오.");
     }
 }
```
</details>

<br>

- **한글로만 혹은 영어로만 된 이름인지 검사**
  > @param studentName : 유효성 검사할 문자열
  > @throws BadInputException : studentName이 영어 대/소문자로만 혹은 (자음+모음 결합 된 )한글로만 되어 있지 않을 때, 발생
  
<pre lang="java">public void nameIsEngOrKor(String studentName) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (!Pattern.matches("^[a-zA-Z]+$||^[가-힣]+$", studentName)) {
         throw new BadInputException("\n잘못된 수강생 이름을 입력하셨습니다.", "영문 이름 혹은 한글 이름만 입력가능합니다.");
     }
 }
```
</details>

<br>

- **기존에 등록된 이름과 다른지 검사**
  > @param newStudentName : 유효성 검사할 이름
  > @param preStudentName : 기존에 등록되어 있는 이름
  > @throws BadInputException : preStudentName과 newStudentName이 서로 같을 때, 발생
  
<pre lang="java">public void notSameName(String newStudentName, String preStudentName) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (newStudentName.equals(preStudentName)) {
         throw new BadInputException("\n입력한 이름이 기존에 등록되어있는 이름과 동일합니다.", "기존에 등록된 이름과 다른 이름을 입력해 주십시오.");
     }
 }
```
</details>

<br>

- **수강생 리스트에 원소가 존재하는지 검사**
  > @param studentList : 수강생 객체 리스트
  > @throws NotExistException : studentList 비어있을 때, 발생
  
<pre lang="java">public void notEmptyStudentList(List<Student> studentList) throws NotExistException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (studentList.isEmpty()) {
         throw new NotExistException("\n해당 상태의 수강생");
     }
 }
```
</details>

<br>

- **과목 분류(필수/선택)의 과목 수강 신청을 더 해야하는지, 더 할 수 있는지 검사**
  > @param student : 수강생 객체
  > @param subjectType : 과목 분류(필수/선택)
  > @param min : subjectType의 수강 신청 최소 과목 수
  > @param total : subjectType의 전체 과목 수
  > @throws AddSubjectException : 수강 신청 최소 과목 수 미달 혹은 전체 과목을 모두 수강신청 했을 때, 발생
<pre lang="java">public boolean satisfySubjectCnt(Student student, String subjectType, int min, int total) throws AddSubjectException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     int joinedCnt = student.getSubjectCnt(subjectType);

     if (joinedCnt < min) {
         throw new AddSubjectException( min, joinedCnt);
     } else if (joinedCnt >= total) {
         throw new AddSubjectException();
     }
 }
```
</details>

<br>

- **수강 중인 과목인지 검사**
  > @param subject : 유효성 검사할 과목 객체
  > @param subjectList : 수강 중인 과목 리스트
  > @throws BadInputException : subject가 수강 중인 과목에 포함되어 있지 않을 때, 발생
<pre lang="java">public void isJoinedSubject(Subject subject, List<Subject> subjectList) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (!subjectList.contains(subject)) {
         throw new BadInputException("\n수강하고 있지 않은 과목입니다.", "수강 과목 목록에서 과목을 선택해 주십시오.");
     }
 }
```
</details>

<br>

- **미수강 과목인지 검사**
  > @param subject : 유효성 검사할 과목 객체
  > @param joinedSubject : 수강 중인 과목 리스트
  > @throws BadInputException : subject가 수강 중인 과목 리스트에 포함되어 있을 때, 발생
  
<pre lang="java">public void notJoinedSubject(Subject subject, List<Subject>subjectList) throws BadInputException</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     if (subjectList.contains(subject)) {
         throw new BadInputException("\n이미 수강 중인 과목입니다.", "과목 목록에서 과목을 선택해 주십시오.");
     }
 }
```
</details>

<br><br><br>

---

### 🏷️BadInputException 클래스
> 입력값을 받는 과정에서 발생한 예외를 처리하기 위한 클래스
> Exception 클래스를 상속 받음.

<br><br>

#### 💫 필드 변수
- **예외 메세지 저장 변수**
<pre lang="java">private String message;</pre>

<br>

#### 💫 생성자
- **예외 메세지와 hint를 매개변수로 받는 생성자**
  > @param message : 예외 메세지
  > @param hint : 예외 해결을 위한 힌트
<pre lang="java">public BadInputException(String message, String hint)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.setMessage(message);
     this.setHint(hint);
 }
```
</details>

<br>

- **유효 범위의 최대, 최솟값을 매개변수로 받는 생성자**
  > @param min : 유효 범위의 최솟값
  > @param max : 유효 범위의 최댓값
<pre lang="java">public BadInputException(int min, int max)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.setHint(min, max);
 }
```
</details>

<br>

#### 💫 GETTER

- **예외 메세지 getter**
  > @return 예외 메세지
<pre lang="java">public String getMessage()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
    return this.message;
 }
```
</details>

<br>

#### 💫 SETTER
- **힌트 메세지 setter**
  > 예외 메세지에 "\n hint : (hint 내용)"을 추가합니다.
  > @param hint : 힌트 메세지
<pre lang="java">private void setHint(String hint)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     sb.append(this.message);
     sb.append("\n hint : ");
     sb.append(hint);
     sb.append("\n");
     this.message = sb.toString();
 }
```
</details>

<br>

- **힌트 메세지 메세지 setter**
  > 예외 메세지에 "\n hint : min 이상 max 이하의 정수만 입력가능합니다."를 추가합니다.
  > @param min : 유효범위 최솟값
  > @param max : 유효범위 최댓값
<pre lang="java">private void setHint(int min, int max)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     sb.append(this.message);
     sb.append("\n hint : ");
     sb.append(min);
     sb.append(" 이상 ");
     sb.append(max);
     sb.append(" 이하의 정수만 입력 가능합니다.\n");
     this.message = sb.toString();
 }
```
</details>

<br><br><br>

---

### 🏷️ NotExistException 클래스
> 값이 존재하지 않는 경우 발생하는 예외를 처리하기 위한 클래스
> Exception 클래스를 상속 받음.

<br><br>

#### 💫 필드 변수
- **예외 메세지**
<pre lang="java">private String message;</pre>

<br>

#### 💫 생성자
- **예외가 발생한 항목을 문자열 매개변수로 받는 생성자**
  > @param object : 예외가 발생한 항목
<pre lang="java">public NotExistException(String object)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
    this.setMessage(object);
 }
```
</details>

<br>

- **예외가 발생한 항목과 예외 해결을 위한 힌트를 문자열 매개변수로 받는 생성자**
  > @param object : 예외가 발생한 항목
  > @param hint : 힌트 메세지
<pre lang="java">public NotExistException(String object, String hint)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.setMessage(object);
     this.setHint(hint);
 }
```
</details>

<br>

#### 💫 GETTER

- **예외 메세지 getter**
<pre lang="java">public String getMessage()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return this.message;
 }
```
</details>

<br>

#### 💫 SETTER

- **예외 메세지 setter**
  > 예외 메세지를 "(object)이(가) 존재하지 않습니다."로 set
  > @param object : 예외가 발생한 항목
  
<pre lang="java">private void setMessage(String object)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     sb.append(object);
     sb.append("이(가) 존재하지 않습니다.");

     this.message = sb.toString();
 }
```
</details>

<br>

- **힌트 메세지 setter**
  > 예외 메세지에 "\n hint : (hint 메세지)"를 추가
  > @param hint : 힌트 메세지
<pre lang="java">private void setHint (String hint)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     sb.append(this.message);
     sb.append("\n hint : ");
     sb.append(hint);
     this.message = sb.toString();
 }
```
</details>

<br><br><br>

---

### 🏷️ AddSubjectException 클래스
> 과목 수강신청과 관련된 예외를 처리하기 위한 클래스
> Exception 클래스를 상속 받음.

<br><br>

#### 💫 필드 변수
- **예외 메세지**
<pre lang="java">private String message;</pre>
- **논리형 변수**
  > AddSubjectException을 throw한 메서드에서 반복문 실행을 위한 논리형 변수
<pre lang="java">private boolean flag;</pre>

<br>

#### 💫 생성자
- **기본 생성자**
  > 전체 과목을 모두 수강신청하였을 때 throw
<pre lang="java">public AddSubjectException()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.message = "현재 분류의 모든 과목을 수강 신청하였습니다.\n해당 분류의 과목 수강 신청이 종료됩니다.";
     this.flag = false;
 }
```
</details>

<br>

- **최소값과 현재 신청한 과목 수를 매개변수로 받는 생성자**
  > 수강생 등록을 위한 수강 신청 최솟값을 충족하지 못하였을 때, throw
  > @param min : 수강생 등록을 위한 수강 신청 최솟값
  > @param joined : 수강 신청한 과목 수
<pre lang="java">public AddSubjectException(int min, int joined)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.message = "최소 수강 신청 과목수를 충족하지 못하였습니다.";
     this.setHint(min, joined);
     this.flag = true;
 }
```
</details>

<br>

#### 💫 GETTER
- **예외 메세지 getter**
  > @return 예외 메세지
<pre lang="java">public String getMessage()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return this.message;
 }
```
</details>

<br>

- **flag getter**
  > @return flag
<pre lang="java">public boolean getFlag()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      return this.flag;
 }
```
</details>

<br>

#### 💫 SETTER
- **힌트 메세지 setter**
  > 예외 메세지에 "\n (min) 과목 이상 신청하여야합니다. (현재 : (joined) 과목 신청)\n해당 과목 선택을 계속 진행하겠습니다."를 추가
  > @param min : 수강생 등록을 위한 최소 신청 과목 수
  > @param joined : 현재 수강 신청한 과목 수
<pre lang="java">private void setHint(int min, int joined)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     sb.append(this.message);
     sb.append("\n hint : 해당 과목은 최소 ");
     sb.append(min);
     sb.append(" 과목 이상 신청하여야합니다. (현재 : ");
     sb.append(joined);
     sb.append(" 과목 신청)\n해당 과목 선택을 계속 진행하겠습니다.");
     this.message = sb.toString();
 }
```
</details>

<br><br><br>

---

### 🥏 ExitThisPage 클래스
> 현재 페이지를 종료하기 위해 사용하는 클래스
> Throwable 클래스를 상속 받음

<br><br>

#### 💫 생성자
- **기본 생성자**
  > super()을 이용하여 부모 클래스의 생성자를 이용하여 예외 메세지를 처리
<pre lang="java">public ExitThisPage()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      super("\n현재 페이지를 종료하고 이전 페이지로 돌아갑니다.");
 }
```
</details>
