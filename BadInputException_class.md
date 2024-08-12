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