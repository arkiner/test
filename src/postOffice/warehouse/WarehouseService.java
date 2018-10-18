package postOffice.warehouse;

import java.util.List;
import java.util.Map;

public interface WarehouseService {

	Map<String, Object> stroe(String goodsName, String warehouseId);

	List<Map<String, Object>> warehouseList();

	Map<String, Object> delete(String goodsId, String warehouseId);

	Map<String, Object> save(String goodsName, String unit, String price,
			String count, String warehouseId);
}
