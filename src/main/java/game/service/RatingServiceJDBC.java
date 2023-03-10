package game.service;

import game.entity.Rating;

import java.sql.*;


public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "123456789";
    public static final String SELECT = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ?";
    public static final String SELECT_AVG = "SELECT rating FROM rating WHERE game = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?,?) ON CONFLICT (game, player) WHERE game = ? DO UPDATE SET rating=EXCLUDED.rating";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.setString(5, rating.getGame());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Problem inserting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_AVG)
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                double avg = 0;
                double count = 0;
                while (rs.next()) {
                    avg = avg + rs.getInt(1);
                    count++;
                }
                double result = (avg / count) + 0.5;
                return (int) result;
            }
        } catch (SQLException e) {
            throw new CommentException("Problem selecting rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (game.equals(rs.getString(1)) && player.equals(rs.getString(2))) {
                        return rs.getInt(3);
                    }
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new CommentException("Problem selecting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new CommentException("Problem deleting rating", e);
        }
    }
}
