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