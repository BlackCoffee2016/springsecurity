<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>空中网DB2CSV</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap-datepicker/1.5.1/css/bootstrap-datepicker3.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath}/resources/admin/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/admin/bootstrap/js/bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap-datepicker/1.5.1/js/bootstrap-datepicker.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap-datepicker/1.5.1/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
    	$("#logout-btn").click(function() {
    		$("#logout-form").submit();
    	});
    	$('.datetime-range').datepicker({
    		language: "zh-CN",
    		format: "yyyy-mm-dd"
    	});
    	$(".download-csv").click(function() {
    		var date = $(this).parent().parent().find(".datetime-range").val();
    		if(date) {
    			$(this).attr("href", $(this).attr("data-href") + "&date=" + date);
    		} else {
    			alert("请选择日期");
    		}
    	});
    });
    </script>
  </head>
  
  <body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">

      <!-- Main Header -->
      <header class="main-header">

        <a href="${pageContext.request.contextPath}/" class="logo">
          <span class="logo-mini">KZ</span>
          <span class="logo-lg"><b>KongZhong</b></span>
        </a>

        <nav class="navbar navbar-static-top" role="navigation">
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <!-- Navbar Right Menu -->
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
             
              <li>
                <a>
                  <span>${pageContext.request.remoteUser}</span>
                </a>
              </li>
              <li>
              	<form action="${pageContext.request.contextPath}/logout" method="post" style="display: none;" id="logout-form">
                </form>
                <a href="#" title="登出" id="logout-btn"><i class="fa fa-sign-out"></i></a>
              </li>
            </ul>
          </div>
        </nav>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">

        <section class="sidebar">

          <!-- Sidebar Menu -->
          <ul class="sidebar-menu">
          	<li class="active"><a href="${pageContext.request.contextPath}/"><i class="fa fa-table"></i> <span>CSV下载</span></a></li>
          </ul><!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
      </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
           	 CSV下载
          </h1>
          <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/"><i class="fa fa-home"></i> HOME</a></li>
            <li class="active">CSV下载</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">


<div class="row">
    <div class="col-xs-12">
        
        <div class="box">
            <div class="box-body" style="min-height: 400px;">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>选择日期</th>
                            <th>导出CSV</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="item">
                            <tr>
                                <td><c:out value="${item.name}" /></td>
                                <td>
                                	<input type="text" class="form-control datetime-range">
                                </td>
                                <td>
                                    <a data-href="/csv?id=${item.id}" class="btn btn-primary btn-sm download-csv">导出</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>




        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->

      <footer class="main-footer">
      	<div class="pull-right hidden-xs">
          空中网数据导出CSV程序 <b>Version</b> 1.0.0
        </div>
        <strong>Copyright &copy; 2016 <a href="http://xxgblog.com" target="_blank">http://xxgblog.com</a>.</strong> All rights reserved.
      </footer>

    </div><!-- ./wrapper -->

    <script src="${pageContext.request.contextPath}/resources/admin/dist/js/app.min.js"></script>
  </body>
</html>