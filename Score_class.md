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