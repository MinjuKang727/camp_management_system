### 🥏 ExitThisPage 클래스
> 현재 페이지를 종료하기 위해 사용하는 클래스
> Throwable 클래스를 상속 받음

<br><br>

#### 💫 생성자
- **기본 생성자**
  > super()을 이용하여 부모 클래스의 생성자를 이용하여 예외 메세지를 처리
<pre lang="java">public ExitThisPage()</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
 {
      super("\n현재 페이지를 종료하고 이전 페이지로 돌아갑니다.");
 }
```
</details>