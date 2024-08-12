### 🏷️ NotExistException 클래스
> 값이 존재하지 않는 경우 발생하는 예외를 처리하기 위한 클래스
> Exception 클래스를 상속 받음.

<br><br>

#### 💫 필드 변수
- **예외 메세지**
<pre lang="java">private String message;</pre>

<br>

#### 💫 생성자
- **예외가 발생한 항목을 문자열 매개변수로 받는 생성자**
  > @param object : 예외가 발생한 항목
<pre lang="java">public NotExistException(String object)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
    this.setMessage(object);
 }
```
</details>

<br>

- **예외가 발생한 항목과 예외 해결을 위한 힌트를 문자열 매개변수로 받는 생성자**
  > @param object : 예외가 발생한 항목
  > @param hint : 힌트 메세지
<pre lang="java">public NotExistException(String object, String hint)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     this.setMessage(object);
     this.setHint(hint);
 }
```
</details>

<br>

#### 💫 GETTER

- **예외 메세지 getter**
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

- **예외 메세지 setter**
  > 예외 메세지를 "(object)이(가) 존재하지 않습니다."로 set
  > @param object : 예외가 발생한 항목
  
<pre lang="java">private void setMessage(String object)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     sb.append(object);
     sb.append("이(가) 존재하지 않습니다.");

     this.message = sb.toString();
 }
```
</details>

<br>

- **힌트 메세지 setter**
  > 예외 메세지에 "\n hint : (hint 메세지)"를 추가
  > @param hint : 힌트 메세지
<pre lang="java">private void setHint (String hint)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
     StringBuilder sb = new StringBuilder();
     sb.append(this.message);
     sb.append("\n hint : ");
     sb.append(hint);
     this.message = sb.toString();
 }
```
</details>