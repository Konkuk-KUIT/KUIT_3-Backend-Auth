package kuit.server.dao;

import kuit.server.domain.Store;
import kuit.server.dto.store.response.GetCategoryResponse;
import kuit.server.dto.store.response.JoinStoreCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class StoreDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 상점 조회
     **/
    public Store findById(long store_id) {
        String sql = "select store_id, name, minimum_price, status from store where store_id=:store_id";
        Map<String, Object> param = Map.of("store_id", store_id);
        return jdbcTemplate.queryForObject(sql, param, (rs, rowNum) -> new Store(
                Long.parseLong(rs.getString("store_id")),
                rs.getString("name"),
                Long.parseLong(rs.getString("minimum_price")),
                rs.getString("status")
        ));
    }

    /**
     * 상점 생성
     **/

    public Long createStore(Store store) {

        String sql = "insert into store(store_id, name, minimum_price, status) " +
                "values(:storeId, :name, :minimumPrice, :status)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(store);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return 1L;
    }

    /**
     * 상점 + 카테고리 조회
     **/

    public JoinStoreCategory findByIdWithCategory(long storeId) {
        String sql = "select s.store_id, s.name as store_name, minimum_price, status, c.name as category_name " +
                "from store s left join category c on s.store_id = c.store_id where s.store_id = :store_id";
        Map<String, Object> param = Map.of("store_id", storeId);

        List<JoinStoreCategory> results = jdbcTemplate.query(sql, param, (rs) -> {
            HashMap<Long,JoinStoreCategory>map=new HashMap<>();

            while (rs.next()) {
                JoinStoreCategory joinStoreCategory = map.get(rs.getLong("store_id"));
                if (joinStoreCategory == null) {
                    Long key=rs.getLong("store_id");
                    joinStoreCategory = new JoinStoreCategory(
                            key,
                            rs.getString("store_name"),
                            rs.getLong("minimum_price"),
                            new ArrayList<>()
                    );
                    map.put(key,joinStoreCategory);
                }
                joinStoreCategory.pushCategory(new GetCategoryResponse(
                        rs.getString("status"),
                        rs.getString("category_name")
                ));

            }
            return new ArrayList<>(map.values());
        });

        return results.isEmpty() ? null : results.get(0);
    }
}