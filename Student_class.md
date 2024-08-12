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