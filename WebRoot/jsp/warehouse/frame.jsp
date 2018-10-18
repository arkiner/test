<%@ page pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
    String userName = request.getSession().getAttribute("userName").toString();
    String branchName = request.getSession().getAttribute("branchName").toString();
    String branchId = request.getSession().getAttribute("branchId").toString();
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://cdn.bootcss.com/jqueryui/1.12.1/jquery-ui.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <link href="https://cdn.bootcss.com/jqueryui/1.12.1/jquery-ui.theme.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/layer/3.1.0/layer.js"></script>
    <script type="text/javascript" src="jsp/warehouse/frame.js"></script>
    <script type="text/javascript">
        var rowNum = 0;
    </script>
</head>
<body>
  <div style=""><span><%=userName %></span>欢迎你</div>
  <div>
     <table>
        <tr><td><span>物品名称</span></td><td><input type="text" id="goodsName"></td><td><select id="warehouse"></select></td><td><button id="query">查询</button></td></tr>
     </table>
  </div>
  <table id="tableContent">
     <tr><th>序号</th><th>物品名称</th><th>单位</th><th>单价</th><th>数量</th></tr>
  </table>
  <div><button id="add">添加</button><button id="delete">删除</button></div>
  <script type="text/javascript">
      $(function(){
    	  $.ajaxSetup({
				cache : false
			});
    	  
    	  $("#add").button().on("click",function(){
    		  var html = "<tr><td><button class='b' onclick='save(event)'>保存</button></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td></tr>";
    		  $("#tableContent").append(html);
    		  $(".b").button();
    	  });
    	  $("#delete").button().on("click",function(){
    		  $.ajax({
    			  url:"/warehouse/delete",
    			  method:"POST",
    			  data:{goodsId:rowNum,
    				    warehouseId:$("#warehouse").val()},
    			  success:function(data){
    				  var status = data.success;
    				  if(status == 1){
    					  $("#query").click();
    				  }else{
    					  layer.confirm(status,{btn:["确定","取消"],title:"错误消息"},function(){});
    				  }
    			  }
    		  });
    	  });
    	  $("#query").button().on("click",function(){
    		  $.ajax({
        		  url:"/warehouse/store",
        		  method:"POST",
        		  data:{goodsName:$("#goodsName").val(),
        			    warehouseId:$("#warehouse").val()},
        		  success:function(data){
        			  var status = data.success;
        			  if(status == 1){
        				  var list = data.data;
        				  var html = "<tr><th>序号</th><th>物品名称</th><th>单位</th><th>单价</th><th>数量</th></tr>";
        				  html = html + "";
        				  for(var i = 0;i < list.length;i++){
        					  var goods = list[i];
        					  var goodsId = goods.goodsId;
        					  var name = goods.goodsName;
        					  var unit = goods.unit;
        					  var price = goods.price;
        					  var realCount = goods.realCount;
        					  html = html + "<tr class='exsits' onclick='select(event)'><td><span>"+i+"</span><input type='hidden' value='"+goodsId+"'></td><td>"+name+"</td><td>"+unit+"</td><td>"+price+"</td><td>"+realCount+"</td></tr>";
        				  }
        				  $("#tableContent").html(html);
        			  }else{
        				  layer.msg("取数失败",{time:1000});
        			  }
        		  }
        	  });
    	  });
    	  
    	  $.ajax({
    		  url:"/warehouse/list",
    		  method:"POST",
    		  data:{},
    		  success:function(data){
    		      var html = '';
    			  if(data != null){
    				  for(var i = 0;i < data.length;i++){
    					  var warehouse = data[i];
    					  var id = warehouse.warehouseId;
    					  var name = warehouse.warehouseName;
    					  html = html + "<option value='"+id+"'>"+name+"</option>";
    				  }
    			  }
    			  $("#warehouse").html(html);
    		  }
    	  });
    	  
      });
  </script>
</body>