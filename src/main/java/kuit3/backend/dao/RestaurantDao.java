package kuit3.backend.dao;

import kuit3.backend.dto.restaurant.GetRestaurantResponse;
import kuit3.backend.dto.restaurant.PostRestaurantRequest;
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
    private final int LIMIT_NUM = 5;

    public RestaurantDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /*
    신규 식당 추가
     */
    public long create(PostRestaurantRequest postRestaurantRequest){
        String sql = "insert into restaurant(name, address, phone_number, category) " +
                "values(:name, :address, :phone_number, :category);";
        SqlParameterSource param = new BeanPropertySqlParameterSource(postRestaurantRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<GetRestaurantResponse> findRestaurantsByCategory(Integer categoryId, Long lastId) {
        String sql = "select * " +
                "from restaurant where category = :category";

        String nextPageSQL = "select EXISTS(" + sql + " AND restaurant_id >= :nextId)";
        Map<String, Object> nextPageParams = Map.of("nextId", lastId + LIMIT_NUM);
        Boolean hasNext = Boolean.TRUE.equals(jdbcTemplate.queryForObject(nextPageSQL, nextPageParams, boolean.class));

        sql += " AND restaurant_id > :lastId LIMIT :limit";
        Map<String, Object> param = Map.of(
                "category", categoryId,
                "lastId", lastId,
                "limit", LIMIT_NUM
        );
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetRestaurantResponse(
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("business_hour"),
                        rs.getString("closed_day"),
                        Integer.parseInt(rs.getString("category")),
                        Integer.parseInt(rs.getString("minimum_order_price")),
                        rs.getString("status"),
                        Integer.parseInt(rs.getString("star_rate")),
                        Integer.parseInt(rs.getString("delivery_fee")),
                        hasNext // 수정
                )
        );
    }

    public boolean existsWithId(long restaurantId) {
        log.info("exists:: ");
        String sql = "select exists(select name from restaurant where restaurant_id = :restaurant_id)";
        Map<String, Long> param = Map.of("restaurant_id", restaurantId);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public int modifyStatusAsClosed(Long restaurantId) {
        String sql = "update restaurant set status=:status where restaurant_id=:id";
        Map<String, Object> param = Map.of(
                "status", "closed",
                "id", restaurantId);
        return jdbcTemplate.update(sql, param);
    }


    public int modifyBusinessHour(Long restaurantId, String businessHour) {
        String sql = "UPDATE restaurant SET business_hour = :hour where restaurant_id = :id";
        Map<String, Object> param = Map.of(
                "hour", businessHour,
                "id", restaurantId
        );
        return jdbcTemplate.update(sql, param);
    }

    public List<GetRestaurantResponse> search(String keyword, String minStar, String maxDeliveryFee, Long lastId) {
        String sql = "SELECT * " +
                "FROM restaurant WHERE 1=1";

        if(keyword != null && !keyword.isEmpty()){
            sql += " AND name LIKE '%" + keyword + "%'";
        }
        if(minStar != null && !minStar.isEmpty()){
            sql += " AND star_rate >=" + minStar;
        }
        if(maxDeliveryFee != null && !maxDeliveryFee.isEmpty()){
            sql += " AND delivery_fee <= " + maxDeliveryFee;
        }

        // 다음 페이지가 존재하는지 로직을 짜려고 했는데 생각보다 어렵네요...
        String nextPageSQL = "select EXISTS(" + sql + " AND restaurant_id >= :nextId)";
        Map<String, Object> nextPageParams = Map.of("nextId", lastId + LIMIT_NUM);
        Boolean hasNext = Boolean.TRUE.equals(jdbcTemplate.queryForObject(nextPageSQL, nextPageParams, boolean.class));

        sql += " AND restaurant_id > :lastId LIMIT :limit";
        Map<String, Object> params = Map.of(
                "lastId", lastId,
                "limit", LIMIT_NUM
        );

        log.info("restaurantService.search :: sql = " + sql);

        return jdbcTemplate.query(sql,
                params,
                (rs, rowNum) -> new GetRestaurantResponse(
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("business_hour"),
                        rs.getString("closed_day"),
                        Integer.parseInt(rs.getString("category")),
                        Integer.parseInt(rs.getString("minimum_order_price")),
                        rs.getString("status"),
                        Integer.parseInt(rs.getString("star_rate")),
                        Integer.parseInt(rs.getString("delivery_fee")),
                        hasNext
                )
        );
    }
}
