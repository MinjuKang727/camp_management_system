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