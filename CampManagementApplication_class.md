### 🔖 CampManagementApplication 클래스
> 수강생 관리 프로그램을 처음 실행시키기 위한 클래스

<br><br>

#### 💫 main 메서드  
  > Viewer 클래스 생성 및 Viewer클래스의 displayMainView() 메서드를 실행하여 수강생 관리 프로그램 실행

<pre lang="java">public static main(String[] args)</pre>
<details>
   <summary>내부 코드 보기</summary>

```java
{  
      Viewer viewer = new Viewer(3, 2);  
        
      try {  
           viewer.displayMainView();  
      } catch (Exception e) {  
           System.out.println("오류 발생!");  
           System.out.print("[ 오류 원인 ]");  
           System.out.println(e.getMessage());  
           System.out.println("\n프로그램을 종료합니다.");  
      }
}
```
</details>