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