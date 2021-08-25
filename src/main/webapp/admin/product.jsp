<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <script type="text/javascript">
        if ("${msg}" != "") {
            alert("${msg}");
        }
    </script>

    <c:remove var="msg"></c:remove>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bright.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addBook.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <title></title>
</head>
<script type="text/javascript">
    function allClick() {
        // 获取全选框的状态
        var flag = $("#all").prop("checked");

        // 改变单选框的状态 首先获取
        $("input[name='ck']").each(function (){
            this.checked = flag;
        });
    }

    function ckClick() {
        // 单选框状态改变  则改变全选框
        // 获取单选框的个数
        var fiveLength = $("input[name='ck']").length;
        // 获取被选中的单选框格个数
        var checkedLength = $("input[name='ck']:checked").length;
        // 判断是否相同  不同则全选不选
        if (fiveLength != checkedLength){
            $("#all").prop("checked", false);
        }else {
            $("#all").prop("checked", true);
        }

    }
</script>
<body>
<div id="brall">
    <div id="nav">
        <p>商品管理>商品列表</p>
    </div>
    <div id="condition" style="text-align: center">
        <form id="myform">
            商品名称：<input name="pname" id="pname">&nbsp;&nbsp;&nbsp;
            商品类型：<select name="typeid" id="typeid">
            <option value="-1">请选择</option>
            <c:forEach items="${typeList}" var="pt">
                <option value="${pt.typeId}">${pt.typeName}</option>
            </c:forEach>
        </select>&nbsp;&nbsp;&nbsp;
            价格：<input name="lprice" id="lprice">——<input name="hprice" id="hprice">
            <input type="button" value="查询" onclick="condition()"><%--ajaxsplit(${info.pageNum})--%>
        </form>
    </div>
    <br>
    <div id="table">

        <c:choose>
            <c:when test="${info.list.size()!=0}">

                <div id="top">
                    <input type="checkbox" id="all" onclick="allClick()" style="margin-left: 50px">&nbsp;&nbsp;全选
                    <a href="${pageContext.request.contextPath}/admin/addproduct.jsp">

                        <input type="button" class="btn btn-warning" id="btn2"
                               value="新增商品">
                    </a>
                    <input type="button" class="btn btn-warning" id="btn2"
                           value="批量删除" onclick="deleteBatch(${info.pageNum})">
                </div>
                <!--显示分页后的商品-->
                <div id="middle">
                    <table class="table table-bordered table-striped">
                        <tr>
                            <th></th>
                            <th>商品名</th>
                            <th>商品介绍</th>
                            <th>定价（元）</th>
                            <th>商品图片</th>
                            <th>商品数量</th>
                            <th>操作</th>
                        </tr>
                        <c:forEach items="${info.list}" var="p">
                            <tr>
                                <td valign="center" align="center"><input type="checkbox" name="ck" id="ck" value="${p.pId}" onclick="ckClick()"></td>
                                <td>${p.pName}</td>
                                <td>${p.pContent}</td>
                                <td>${p.pPrice}</td>
                                <td><img width="55px" height="45px"
                                         src="${pageContext.request.contextPath}/image_big/${p.pImage}"></td>
                                <td>${p.pNumber}</td>
                                    <%--<td><a href="${pageContext.request.contextPath}/admin/product?flag=delete&pid=${p.pId}" onclick="return confirm('确定删除吗？')">删除</a>--%>
                                    <%--&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/admin/product?flag=one&pid=${p.pId}">修改</a></td>--%>
                                <td>
                                    <button type="button" class="btn btn-info "
                                            onclick="one(${p.pId}, ${info.pageNum})">编辑
                                    </button>
                                    <button type="button" class="btn btn-warning" id="mydel"
                                            onclick="del(${p.pId}, ${info.pageNum})">删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <!--分页栏-->
                    <div id="bottom">
                        <div>
                            <nav aria-label="..." style="text-align:center;">
                                <ul class="pagination">
                                    <li>
                                            <%--                                        <a href="${pageContext.request.contextPath}/prod/split.action?page=${info.prePage}" aria-label="Previous">--%>
                                        <a href="javascript:ajaxsplit(${info.prePage})" aria-label="Previous">

                                            <span aria-hidden="true">«</span></a>
                                    </li>
                                    <c:forEach begin="1" end="${info.pages}" var="i">
                                        <c:if test="${info.pageNum==i}">
                                            <li>
                                                    <%--                                                <a href="${pageContext.request.contextPath}/prod/split.action?page=${i}" style="background-color: grey">${i}</a>--%>
                                                <a href="javascript:ajaxsplit(${i})"
                                                   style="background-color: aqua">${i}</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${info.pageNum!=i}">
                                            <li>
                                                    <%--                                                <a href="${pageContext.request.contextPath}/prod/split.action?page=${i}">${i}</a>--%>
                                                <a href="javascript:ajaxsplit(${i})">${i}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                    <li>
                                        <%--  <a href="${pageContext.request.contextPath}/prod/split.action?page=1" aria-label="Next">--%>
                                        <a href="javascript:ajaxsplit(${info.nextPage})" aria-label="Next">
                                            <span aria-hidden="true">»</span></a>
                                    </li>
                                    <li style=" margin-left:150px;color: #0e90d2;height: 35px; line-height: 35px;">总共&nbsp;&nbsp;&nbsp;<ins
                                            style="color:orange;">${info.pages}</ins>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <c:if test="${info.pageNum!=0}">
                                            当前&nbsp;&nbsp;&nbsp;<ins
                                            style="color:orange;">${info.pageNum}</ins>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                        <c:if test="${info.pageNum==0}">
                                            当前&nbsp;&nbsp;&nbsp;<ins
                                            style="color:orange;">1</ins>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <h2 style="width:1200px; text-align: center;color: orangered;margin-top: 100px">暂时没有符合条件的商品！</h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>

<script type="text/javascript">
    function mysubmit() {
        $("#myform").submit();
    }

    //批量删除
    function deleteBatch(page) {
        // 得到所有的复选框对象  根据长度判断是否有选中的商品
        var cks = $("input[name='ck']:checked");
        // 如果有选中的商品 获取value值  拼接字符串
        if (cks.length == 0){
            alert("请选择要删除的商品~")
        }else {
            var str = "";
            var pid = "";
            if (confirm("您确定要删除这"+cks.length+"个商品吗")){
                // 取出查询条件  进行传递
                var pname = $("#pname").val();
                var typeid = $("#typeid").val();
                var lprice = $("#lprice").val();
                var hprice = $("#hprice").val();
                // 进行字符串的拼接
                $.each(cks, function (){
                   pid = $(this).val();// 每一个商品的id
                    // 判断是否为空
                    if (pid != null){
                        str += pid + ",";
                    }
                });
                // 发型ajax请求
                $.ajax({
                   url:"${pageContext.request.contextPath}/prod/deleteBatch.action",
                    data:{"pids":str, "page":page,"pname":pname, "typeid":typeid,"lprice":lprice, "hprice":hprice},
                    type:"post",
                    dataType: "text",
                    success:function (msg){
                       alert(msg);
                       $("#table").load("http://localhost:8080/admin/product.jsp #table");
                    }
                });
            }
        }
    }
/*            //取得所有被选中删除商品的pid
            var zhi=$("input[name=ck]:checked");
            var str="";
            var id="";
            if(zhi.length==0){
                alert("请选择将要删除的商品！");
            }else{
                // 有选中的商品，则取出每个选 中商品的ID，拼提交的ID的数据
                if(confirm("您确定删除"+zhi.length+"条商品吗？")){
                //拼接ID
                    $.each(zhi,function (index,item) {

                        id=$(item).val(); //22 33
                        alert(id);
                        if(id!=null)
                            str += id+",";  //22,33,44
                    });
alert(str+"11111111");
                    //发送请求到服务器端
                   //window.location="${pageContext.request.contextPath}/prod/deletebatch.action?str="+str;

                }
        }*/

    //单个删除
    function del(pid, page) {
        if (confirm("确定删除吗")) {
            // 取出查询条件  进行传递
            var pname = $("#pname").val();
            var typeid = $("#typeid").val();
            var lprice = $("#lprice").val();
            var hprice = $("#hprice").val();
            // 发出ajax请求
            $.ajax({
                url:"${pageContext.request.contextPath}/prod/delete.action",
                data:{"pid":pid,"page":page,"pname":pname, "typeid":typeid,"lprice":lprice, "hprice":hprice},
                type: "post",
                dataType:"text",
                success:function (msg){
                    alert(msg);
                    $("#table").load("http://localhost:8080/admin/product.jsp #table");
                }
            });
          //向服务器提交请求完成删除
          <%--  window.location="${pageContext.request.contextPath}/prod/delete.action?pid="+pid;--%>
        }
    }

    // 向服务器提交请求  传递id
    function one(pid, page) {
        // 取出查询条件  进行传递
        var pname = $("#pname").val();
        var typeid = $("#typeid").val();
        var lprice = $("#lprice").val();
        var hprice = $("#hprice").val();
        // 向服务器提交请求  传递商品id
        var str = "?pid="+pid+"&page="+page+"&pname="+pname+"&typeid"+typeid+"&lprice"+lprice+"&hprice"+hprice;
        location.href="${pageContext.request.contextPath}/prod/one.action" + str;
    }

    function condition(){
        var pname = $("#pname").val();
        var typeid = $("#typeid").val();
        var lprice = $("#lprice").val();
        var hprice = $("#hprice").val();
        $.ajax({
            type: "post",
            url:"${pageContext.request.contextPath}/prod/ajaxsplit.action",
            data:{"pname":pname, "typeid":typeid, "lprice":lprice, "hprice":hprice},
            success:function (){
               // 刷新页面
                $("#table").load("http://localhost:8080/admin/product.jsp #table");
            }
        });
    }
</script>
<!--分页的AJAX实现-->
<script type="text/javascript">
    function ajaxsplit(page) {
        var pname = $("#pname").val();
        var typeid = $("#typeid").val();
        var lprice = $("#lprice").val();
        var hprice = $("#hprice").val();
        //异步ajax分页请求
        $.ajax({
        url:"${pageContext.request.contextPath}/prod/ajaxsplit.action",
            data:{"page":page, "pname":pname, "typeid":typeid, "lprice":lprice, "hprice":hprice},
            type:"post",
            success:function () {
                //重新加载分页显示的组件table
                //location.href---->http://localhost:8080/admin/login.action
                $("#table").load("http://localhost:8080/admin/product.jsp #table");
            }
        });
    };

</script>

</html>