package kuit3.backend.dao;

import kuit3.backend.dto.restaurants.GetRestaurantResponse;
import kuit3.backend.dto.restaurants.PostRestaurantRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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

    public List<GetRestaurantResponse> getRestaurants(String name, String category, String status) {
        String sql = "select category, name, min_price,store_image, store_phone_number,status from Store " +
                "where name like :name and category like :category and status=:status";

        Map<String, Object> param = Map.of(
                "name", "%" + name + "%",
                "category", "%" + category + "%",
                "status", status);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetRestaurantResponse(
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("min_price"),
                        rs.getString("store_phone_number"),
                        rs.getString("store_image"),
                        rs.getString("status"))
        );
    }

    public long createRestaurant(PostRestaurantRequest postRestaurantRequest) {
        String sql = "insert into Store(name, category, min_price,store_phone_number,store_image) " +
                "values(:name, :category, :min_price,:phone_number,:store_image)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postRestaurantRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }


}
