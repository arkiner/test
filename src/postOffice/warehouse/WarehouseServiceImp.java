package postOffice.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WarehouseServiceImp implements WarehouseService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> stroe(String goodsName, String warehouseId) {
		String sql1 = "SELECT goodsId FROM t_goods WHERE goodsName LIKE '%"
				+ goodsName + "%'";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1);
		if (list != null && list.size() > 0) {
			List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m : list) {
				String goodsId = m.get("goodsId").toString();
				String sql2 = "SELECT * FROM t_goods WHERE warehouseId = '"
						+ warehouseId + "' AND goodsId = '" + goodsId + "'";
				Map<String, Object> result = this.jdbcTemplate
						.queryForMap(sql2);
				l.add(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", 1);
			map.put("data", l);
			return map;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", 0);
			return map;
		}
	}

	@Override
	public List<Map<String, Object>> warehouseList() {
		String sql = "SELECT warehouseId,warehouseName FROM t_warehouse ";
		return this.jdbcTemplate.queryForList(sql);
	}

	@Override
	@Transactional
	public Map<String, Object> delete(String goodsId, String warehouseId) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "delete from t_goods WHERE warehouseId = '" + warehouseId
				+ "' AND goodsId = '" + goodsId + "'";
		try {
			this.jdbcTemplate.execute(sql);
			result.put("success", 1);
		} catch (Exception e) {
			result.put("success", e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> save(String goodsName, String unit,
			String price, String count, String warehouseId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String sql1 = "SELECT goodsId FROM t_goods WHERE goodsName = '"
					+ goodsName + "' AND warehouseId = '" + warehouseId + "'";
			List<Map<String, Object>> list1 = this.jdbcTemplate
					.queryForList(sql1);
			if (list1 != null && list1.size() == 1) {
				String id = list1.get(0).get("goodsId").toString();
				String sql2 = "UPDATE t_goods SET count = count + " + count
						+ ",realCount = realCount + " + count
						+ " WHERE goodsId = '" + id + "' AND warehouseId = '"
						+ warehouseId + "'";
				this.jdbcTemplate.execute(sql2);
				result.put("success", 1);
				result.put("goodsId", id);
			} else if (list1 != null && list1.size() > 1) {
				// 基本上不可能
				result.put("success", "在该仓库，该物品名称对应的id不止一个，请检查数据库");
			} else {
				String sql3 = "SELECT goodsId FROM t_goods WHERE goodsName == '"
						+ goodsName + "' ";
				List<Map<String, Object>> list2 = this.jdbcTemplate
						.queryForList(sql3);
				if (list2.size() == 1) {
					String id = list2.get(0).get("goodsId").toString();
					String sql4 = "INSERT INTO t_goods (goodsId,warehouseId,goodsName,unit,count,realCount,price) VALUES ('"
							+ id
							+ "','"
							+ warehouseId
							+ "','"
							+ goodsName
							+ "','"
							+ unit
							+ "',"
							+ count
							+ ","
							+ count
							+ ","
							+ price + ")";
					this.jdbcTemplate.execute(sql4);
					result.put("goodsId", id);
					result.put("success", 1);
				} else if (list2.size() == 0) {
					String sql5 = "select max(goodsId) as goodsId from t_goods";
					Map<String, Object> map = this.jdbcTemplate
							.queryForMap(sql5);
					String id = map != null && map.get("goodsId") != null
							&& !map.get("goodsId").toString().equals("") ? map
							.get("goodsId").toString() : "0";
					id = String.valueOf(Integer.valueOf(id) + 1);
					String sql6 = "INSERT INTO t_goods (goodsId,warehouseId,goodsName,unit,count,realCount,price) VALUES ('"
							+ id
							+ "','"
							+ warehouseId
							+ "','"
							+ goodsName
							+ "','"
							+ unit
							+ "',"
							+ count
							+ ","
							+ count
							+ ","
							+ price + ")";
					this.jdbcTemplate.execute(sql6);
					result.put("success", 1);
					result.put("goodsId", id);
				} else {
					result.put("success", "在该仓库，该物品的名称在数据库有多个，请检查！");
				}
			}
			return result;
		} catch (Exception e) {
			result.put("success", e.getMessage());
			return result;
		} finally {
			throw new RuntimeException();
		}
	}
}
