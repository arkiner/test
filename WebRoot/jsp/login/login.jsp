<%@ page pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
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
</head>
<body>
  <div style="text-align:center;">
     <table style="text-align:center;">
         <tr><td>用户名</td><td><input id="userName" type="text"></td></tr>
         <tr><td>密码</td><td><input id="password" type="password"></td></tr>
         <tr><td><button id="login">登录</button></td><td></td></tr>
     </table>
  </div>
  <script type="text/javascript">
      $(function(){
    	  $.ajaxSetup({
				cache : false
			});
    	  $("#login").button().on("click",function(){
    		  var name = $("#userName").val();
    		  var password = $("#password").val();
    		  if(name == "" || password == ""){
    			  layer.msg("请填写用户名和密码",{time:1000});
    		  }else{
    			  $.ajax({
    				  url:"/login/login",
    				  method:"POST",
    				  data:{userName:name,
    					    password:password},
    				  success:function(data){
    					  var status = data.success;
    					  if(status == 1){
    						  //跳转到管理页面
    						  window.location.href = "<%=path%>" + "/warehouse/store";
    					  }else{
    						  layer.msg("登录失败",{time:1000});
    					  }
    				  }
    			  });
    		  }
    	  });
      });
  </script>
</body>