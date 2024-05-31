package kuit3.backend.dao;

import kuit3.backend.dto.restaurant.GetRestaurantResponse;
import kuit3.backend.dto.restaurant.PostRestaurantRequest;
import kuit3.backend.dto.user.GetUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class RestaurantDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RestaurantDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public long createRestaurant(PostRestaurantRequest postRestaurantRequest) {
        String sql = "insert into restaurant(address, category, latitude, longitude, min_price, name, star, picture_url) " +
                "values(:address, :category, :latitude, :longitude, :min_price, :name, :star, :picture_url)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postRestaurantRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<GetRestaurantResponse> getRestaurants(double star, Long lastId) {
        String sql = "SELECT id, name FROM restaurant WHERE star >= :star AND id > :lastId " +
                "ORDER BY id LIMIT 10";

        Map<String, Object> param = Map.of(
                "star", star,
                "lastId", lastId);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetRestaurantResponse(
                        rs.getLong("id"),
                        rs.getString("name")));
    }

}
