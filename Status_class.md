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