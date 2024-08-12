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