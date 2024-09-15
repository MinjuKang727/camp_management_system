  <p align="center"><a href="https://sequoia-carpet-385.notion.site/13EST-8c48039d6e66406e84d4525c4f964bfc?pvs=4" > <img src="https://github.com/MinjuKang727/camp_management_system/blob/main/dancing-13EST.gif" style="width:40%;"></a></p>



<div align=center>
   <i color="gray">(사진을 클릭하면 노션 페이지로 이동 Go!)</i>
   <br><Br>

<b>기간</b> :  2024.08.01(목) ~ 2024.08.08(목)<br>
<b>소개</b> : 수강생 관리 프로그램<br>
<b>팀 인원</b> : 유현식<i>(팀장)</i>, 강민주, 허담, 정이삭, 김태준<br>
</div>

<br><Br>

## 💡필수 요구사항

+ 수강생 관리
   1. 수강생 정보 등록
   2. 수강생 목록 조회
+ 점수 관리
   1. 수강생 과목별 시험 회차 및 점수 등록
   2. 수강생 과목별 회차 점수 수정
   3. 수강생 특정 과목 회차별 등급 조회
      
## 🚀 추가 요구사항

+  수강생 관리
   1. 수강생 상태 관리
   2. 수강생 정보 조회
   3. 수강생 정보 수정
   4. 상태별 수강생 목록 조회
   5. 수강생 삭제
+  점수 관리
   1. 수강생 과목별 평균 등급 조회
   2. 특정 상태 수강생 필수 과목 평균등급 조회
---
# 💻 Code Description

#### 🔖 [CampManagementApplication 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/CampManagementApplication_class.md)
> 수강생 관리 프로그램을 처음 실행시키기 위한 클래스
<br>

---

### 📦 display 패키지
#### 🔖 [Viewer 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/Viewer_class.md)
> 관리 항목 선택 페이지를 실행시키는 메서드를 모아 놓은 클래스
<br>

#### 🔖 [Management 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/Management_class.md)
> 수강생 관리 메서드를 모아 놓은 클래스
<br>

#### 🔖 [InOut 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/InOut_class.md)
> 입출력 관련 메서드를 모다 놓은 클래스

<br>

---
### 📦 model 패키지
#### 🔖 [Student 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/Student_class.md)
> 수강생 정보 관련 변수 및 Getter, Setter 메서드를 모아 놓은 클래스
<br>

#### 🔖 [Score 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/Score_class.md)
> 점수 관련 변수 및 Getter, Setter 메서드를 모아 놓은 클래스

<br>

#### 🔖 [Status 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/Status_class.md)
> 수강생 상태 관련 열거형(enum) 클래스

<br>

#### 🔖 [Subject 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/Subject_class.md)
> 과목 관련 변수 및 Getter 메서드를 모아 놓은 클래스

<br>

#### 🔖 [DataBase 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/DataBase_class.md)
> 수강생, 과목, 점수 객체 리스트 or Map 및 관련 초기값 설정 변수, 관련 메서드들을 모아 놓은 클래스

<Br>

---

### 📦 Exception 패키지

#### 🔖 [CheckValidity 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/CheckValidity_class.md)
> 입력값 유효성 검사 메서드를 모아놓은 클래스

<br>

#### 🏷️[BadInputException 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/BadInputException_class.md)
> 입력값을 받는 과정에서 발생한 예외를 처리하기 위한 클래스
> Exception 클래스를 상속 받음.

<br>

#### 🏷️ [NotExistException 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/NotExistException_class.md)
> 값이 존재하지 않는 경우 발생하는 예외를 처리하기 위한 클래스
> Exception 클래스를 상속 받음.

<br>

#### 🏷️ [AddSubjectException 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/AddSubjectException_class.md)
> 과목 수강신청과 관련된 예외를 처리하기 위한 클래스
> Exception 클래스를 상속 받음.

<br>

#### 🥏 [ExitThisPage 클래스](https://github.com/13BEST-0801/camp_management_system/blob/mjk/ExitThisPage_class.md)
> 현재 페이지를 종료하기 위해 사용하는 클래스
> Throwable 클래스를 상속 받음

