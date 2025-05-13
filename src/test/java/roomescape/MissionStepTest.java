package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.Nested;
import roomescape.entity.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Nested
    class 이단계 {
        @Test
        void 예약확인페이지로_이동하면_200_응답을_전송해야한다() {
            RestAssured.given().log().all()
                    .when().get("/reservation")
                    .then().log().all()
                    .statusCode(200);
        }

        @Test
        void 예약_조회_요청이_성공하면_200_응답을_전송해야한다() {
            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(0));
        }
    }

    @Nested
    class 삼단계 {

        private Map<String, String> params;

        @BeforeEach
        void setUp() {
            params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2023-08-05");
            params.put("time", "15:40");
        }

        @Test
        void 예약_등록_요청이_성공하면_201_응답을_전송해야한다() {
            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservations")
                    .body("id", is(1));
        }

        @Test
        void 예약_조회_요청이_성공하면_200_응답을_전송해야한다() {
            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(0));
        }

        @Test
        void 예약_삭제_요청이_성공하면_204_응답을_전송해야한다() {

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservations")
                    .body("id", is(1));

            RestAssured.given().log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(204);
        }
    }

    @Nested
    class 사단계 {
        private Map<String, String> params;

        @Test
        void 삭제할_예약이_없다면_성공하면_400_응답을_전송해야한다() {
            RestAssured.given().log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(400)
                    .body("title", is("예약 정보 없음"));
        }

        @Test
        void 빈_값으로_예약_등록_요청하면_400_응답을_전송해야한다() {
            Map<String, String> emptyParams = new HashMap<>();
            emptyParams.put("name", "");
            emptyParams.put("date", "2023-08-05");
            emptyParams.put("time", "15:40");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(emptyParams)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400)
                    .body("title", is("필수 입력값 누락"));
        }

        @Test
        void 올바르지_않는_시간_형식으로_예약_등록_요청하면_400_응답을_전송해야한다() {
            Map<String, String> emptyParams = new HashMap<>();
            emptyParams.put("name", "브라운");
            emptyParams.put("date", "2023-8-5");
            emptyParams.put("time", "15:40");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(emptyParams)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400)
                    .body("title", is("시간 포맷 오류"));
        }
    }

    @Test
    void 오단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 육단계() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }
}
