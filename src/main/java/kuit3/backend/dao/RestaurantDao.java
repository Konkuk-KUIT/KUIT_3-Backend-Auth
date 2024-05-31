package kuit3.backend.dao;

import kuit3.backend.dto.restaurant.GetSearchResponse;
import kuit3.backend.dto.restaurant.GetTipResponse;
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
        String sql = "insert into store_delivery(category, store_name, delivery_area)" +
                "values(:category, :store_name, :delivery_area)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(postRestaurantRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();


    }

    public boolean hasDuplicateStoreName(String storeName) {
        String sql = "select exists(select store_name from store_delivery where store_name=:store_name and status in ('active', 'dormant'))";
        Map<String, Object> param = Map.of("store_name", storeName);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public GetTipResponse getTip(long restaurantId) {
        String sql = "select delivery_tip from store_delivery where store_id=:store_id";
        Map<String, Object> param = Map.of("store_id", restaurantId);
        return new GetTipResponse(jdbcTemplate.queryForObject(sql, param, Double.class));
    }

    public List<GetSearchResponse> search(String sortBy, double minStarts, double minOrderFee, long page) {
        String sql = "select store_name, like, rating from store_delivery " +
                "where minStarts >= :minStarts and minOrderFee >= :minOrderFee LIMIT 10 OFFSET :page";

        Map<String, Object> param = Map.of(
                "minStarts", "%" + minStarts + "%",
                "minOrderFee", "%" + minOrderFee + "%",
                "page", page);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetSearchResponse(
                        rs.getString("store_name"),
                        rs.getDouble("rating"),
                        rs.getLong("like"))
        );
    }
}
