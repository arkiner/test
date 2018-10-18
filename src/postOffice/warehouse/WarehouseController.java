package postOffice.warehouse;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {
	
	@Autowired
	private WarehouseService warehouseServiceImp;
	
	@RequestMapping(value = "/store",method = RequestMethod.GET)
	public String page(){
		return "/warehouse/frame";
	}

	@RequestMapping(value = "/store",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> store(@RequestBody String goodsName,@RequestBody String warehouseId){
		return this.warehouseServiceImp.stroe(goodsName, warehouseId);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public List<Map<String, Object>> warehouseList(){
		return this.warehouseServiceImp.warehouseList();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody String goodsId,@RequestBody String warehouseId){
		return this.warehouseServiceImp.delete(goodsId, warehouseId);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(@RequestBody String goodsName,@RequestBody String unit,@RequestBody String price,@RequestBody String count,@RequestBody String warehouseId){
		return this.warehouseServiceImp.save(goodsName, unit, price, count, warehouseId);
	}
}
