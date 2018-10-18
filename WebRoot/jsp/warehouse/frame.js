function select(e){
	var $this = $(e.target);
	rowNum = $this.children("td:first").find("input[type='hidden']").val();
}

function save(e){
	var $this = $(e.target);
	var name = $this.parent().parent().children("td").eq(1).find("input").val();
	var unit = $this.parent().parent().children("td").eq(2).find("input").val();
	var price = $this.parent().parent().children("td").eq(3).find("input").val();
	var count = $this.parent().parent().children("td").eq(4).find("input").val();
	var warehouseId = $("#warehouse").val();
	if(parseFloat(price).toString() == 'NaN'){
		layer.msg("请把价格输入数字类型",{time:1000});
		return;
	}
	if(parseInt(count).toString() == 'NaN'){
		layer.msg("请把数量输入数字类型",{time:1000});
		return;
	}
	$.ajax({
		url:"/warehouse/save",
		method:"POST",
		data:{goodsName:name,
			  unit:unit,
			  price:price,
			  count:count,
			  warehouseId:warehouseId},
		success:function(data){
			var status = data.success;
			if(status == 1){
				var row = $this.parent().parent().parent().children("tr.exsits").last().children("td:first").find("span").html();
				var id = data.goodsId;
				var html = "<span>" + (row + 1) + "</span><input type='text' value='"+id+"'>";
				$this.parent().append(html);
				$this.parent().parent().addClass("exsits");
				$this.parent().parent().on("click",function(event){select(event.originalEvent);});
				$this.remove();
			}else{
				layer.msg(status,{time:10000});
			}
		}
	});
}