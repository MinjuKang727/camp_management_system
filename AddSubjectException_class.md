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