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