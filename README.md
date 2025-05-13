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

<br>

---

## 🚀 4단계 - 예외 처리


### 📝 기능 요구사항
✅ 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요.    



### 💻 구현 전략

1. 누락된 예약 요청 데이터  
   ➡️ 예약 데이터(date,name,time) 중 하나라도 null이거나 빈 문자열일떄 `EmptyValueException`가 발생한다.    


2. 입력 시간의 형식 오류  
   ➡️ 입력된 시간 관련 데이터가 형식에 맞지 않을때 `DateTimeParseException`가 발생한다.

3. 존재하지 않는 예약의 삭제  
   ➡️ 존재하지 않는 예약 삭제 요청시 `NotFoundReservationException`가 발생한다.

### 응답되는 예외 데이터 구조
`ErrorResult`
```json
{
    "title": 오류에 대한 간략한 요약,
    "status": Http 상태 코드,
    "detail": 오류에 대한 자세한 설명,
    "instance": 오류가 발생한 요청 경로 URI
}
```

<br>

---

## 🚀 5, 6 단계 - DB 적용하기 


### 📝 기능 요구사항
✅ h2 데이터베이스를 활용하여 데이터를 저장하도록 수정하세요.  
✅ 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정하세요.  



### 💻 구현 전략

1. H2 데이터베이스 적용하기   
   ➡️ properties 파일의 `spring.sql.init.mode=embedded` 설정으로 
   어플리케이션 로드 시점에  `schema.sql`파일의 DDL 실행하여 DB 초기화  


2. Spring JDBC를 활용한 DAO 구현  
   ➡️ 기존 페어 프로그래밍에서 작성했던 순수 JDBC 코드를 Spring JDBC로 변경




<br>

---

## API 명세

### 1. 전체 예약 조회
> 모든 예약 데이터를 조회하는 API


#### 1. Request
HTTP Method: `GET`  
URI: `/reservations`

<br>

#### 2. Response
상태 코드: `200 Ok`  
Body
```json
[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00"
    },
    {
        "id": 2,
        "name": "브라운",
        "date": "2023-01-02",
        "time": "11:00"
    }
]
```
<br>

### 2. 새로운 에약 등록
> 예약자, 예약 날짜, 예약 시간을 입력받아 새로운 예약을 등록하는 API


#### 1. Request
HTTP Method: `POST`  
URI: `/reservations`
Body
```json
{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

<br>

#### 2. Response
상태 코드: `201 Created`  
Body
```json
{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

<br>

#### 3. 예외 - 요청 데이터 누락

> 필수 예약 데이터(name, date, time)가 누락 되었을때 발생한다 



#### 1) Request  
Body
```json
{
    "date": " ",
    "name": "브라운",
    "time": "15:40"
}
```

예약 데이터의 필드가 null이거나 빈 문자열일때 예외가 발생한다

#### 2) Response  
상태 코드: `400 Bad Request`   
Body
```json
{
    "title": "필수 입력값 누락",
    "status": 400,
    "detail": "[date]값이 비어 있습니다!",
    "instance": "/reservations"
}
```

<br>

#### 4. 예외 - 예약 날짜 포맷 불일치

> 예약 날짜 형식이 `date: "yyyy-MM-dd"` / `time: "HH:mm"` 를 만족해야 한다.


#### 1) Request
Body
```json
{
    "date": "2024-1-5",
    "name": "브라운",
    "time": "15:40"
}
```
월-일 표현이 `01-05` 형식과 일치하지 않아 예외가 발생한다.

#### 2) Response  
상태 코드: `400 Bad Request`   
Body
```json
{
  "title": "시간 포맷 오류",
  "status": 400,
  "detail": "시간은 yyyy-MM-dd / HH:mm 형식이어야 합니다.",
  "instance": "/reservations"
}
```


<br>

### 3. 예약 삭제

> 특정 예약을 삭제하는 API 


#### 1. Request
HTTP Method: `DELETE`  
URI: `/reservations/{reservationId}`

<br>

#### 2. Response
상태 코드: `204 No Content`  

<br>

#### 3. 예외 - 삭제 요청한 예약 정보 없음

> 삭제를 요청한 예약 정보가 존재하지 않을 때 예외가 발생한다.

#### 1. Request
HTTP Method: `DELETE`  
URI: `/reservations/102`  
(존재하지 않는 102번 예약을 삭제요청)

#### 2. Response  

상태 코드: `400 Bad Request`   
Body
```json
{
  "title": "예약 정보 없음",
  "status": 400,
  "detail": "삭제 요청한 102번 예약은 존재하지 않아 삭제가 불가능합니다!",
  "instance": "/reservations/102"
}
```
