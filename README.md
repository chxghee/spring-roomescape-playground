## 🚀 1단계 - 홈 화면

### 📝 기능 요구사항
✅ localhost:8080 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.  

### 💻 구현 전략

1. 의존성 추가  
    ➡️ `spring-boot-starter-web` : HTTP 요청/응답 처리 및 웹 서버 구동을 위한 기본 라이브러리    
    ➡️ `spring-boot-starter-thymeleaf` : HTML View 랜더링 위한 템플릿 엔진 라이브러리    


2. `/`GET 요청을 받아 HTML 페이지를 응답하는 컨트롤러 작성

<br>

---

## 🚀 2단계 - 예약 조회


### 📝 기능 요구사항
✅ /reservation 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.  
✅ 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

### 💻 구현 전략

1. `GET: /reservation` 요청 받아 view응답하는 컨트롤러 작성   
   ➡️ `HomeController`     reservation.html를 응답하는 컨트롤러


2. Reservation 엔티티  
   ➡️ id, 이름, 등록일, 등록시간 정보를 갖는다.  


3. api 구현  
    ➡️ URI: `GET: /reservations`  
   ➡️ api 응답을 위한 `ReservationController` 작성 -> @RestController  
   ➡️ 응답 시에는 `ReservationResponse DTO`로 변환하여 응답한다.   

   
  
<br>

---

## 🚀 3단계 - 예약 추가 / 취소


### 📝 기능 요구사항
✅ API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.  
✅ 예약 추가와 취소가 잘 동작해야합니다.


### 💻 구현 전략

1. 예약 추가 API  
   ➡️ URI: `POST: /reservations`  
   ➡️ RequestBody -> 요청 JSON 데이터를 `ReservationRequest DTO` 객체로 매핑한다.
    ```json
   {
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
    }
   ```    
   ➡️ 응답: `201 Created` + 새롭게 추가된 예약 내용을 `ReservationResponse DTO`로 변환하여 응답한다.


2. 예약 삭제 API  
   ➡️ URI: `DELETE: /reservations/{reservationId}`  
   ➡️ 응답: `204 No Content` 응답 


3. ReservationService 클래스 작성  
   ➡️ 조회, 생성, 삭제 로직은 서비스 레이어에서 담당하도록 변경  
