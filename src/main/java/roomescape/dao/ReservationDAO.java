package roomescape.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class ReservationDAO {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    @PostConstruct
    public void createTable() {

        final var query = "CREATE TABLE RESERVATION ( " +
                "ID BIGINT unique PRIMARY KEY, " +
                "NAME VARCHAR(20) ," +
                "DATE VARCHAR(50) ," +
                "TIME VARCHAR(50) " +
                ")";

        try (final var connection = getConnection()) {
            connection.createStatement().execute(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void addReservation(Reservation reservation) {
        final var query = "INSERT INTO RESERVATION VALUES(?, ?, ?, ?)";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setLong(1, reservation.getId());
            preparedStatement.setString(2, reservation.getName());
            preparedStatement.setString(3, reservation.getDate().toString());
            preparedStatement.setString(4, reservation.getTime().toString());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Reservation> findReservation(Long id) {
        final var query = "SELECT * FROM RESERVATION WHERE ID = ?";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setLong(1, id);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LocalDate date = LocalDate.parse(resultSet.getString("DATE"), DateTimeFormatter.ISO_DATE);
                LocalTime time = LocalTime.parse(resultSet.getString("TIME"), DateTimeFormatter.ISO_TIME);
                return Optional.of(new Reservation(
                        resultSet.getLong("ID"),
                        resultSet.getString("NAME"),
                        date, time
                ));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void updateReservation(Long reservationId, ReservationRequest reservationRequest) {
        final var query = "UPDATE RESERVATION SET NAME = ?, DATE = ?, TIME = ? WHERE ID = ?";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, reservationRequest.getName());
            preparedStatement.setString(2, reservationRequest.getDate());
            preparedStatement.setString(3, reservationRequest.getTime());
            preparedStatement.setLong(4, reservationId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteReservation(Long reservationId) {
        final var query = "DELETE FROM RESERVATION WHERE ID = ?";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setLong(1, reservationId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
