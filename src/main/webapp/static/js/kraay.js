/**
 * 根据指定的值来显示或者隐藏指定的input
 * */
function show_input(value,input){
    if(value == 0){
        $(input).removeAttr("style");
    }else{
        $(input).attr({"style":"display:none;"});
    }
}


function getDatabase(){
    var user = $("#user").val();
    var password = $("#password").val();
    var host = $("#host").val();
    var port = $("#port").val();
    if(user == "" || password =="" || host == "" || port ==""){
        alert("请输入数据库配置");
        return;
    }
    var url = "ajax/db.do?u="+user+"&p="+password+"&h="+host+"&P="+port;
    $.getJSON( url, function( data ) {
        var items = [];
        $.each( data, function( key, val ) {
            items.push( "<option value='" + val + "'>" + val + "</option>" );

        });
        $("#database").html(items.join(""));
    });
}

function getTables(){
    var user = $("#user").val();
    var password = $("#password").val();
    var host = $("#host").val();
    var port = $("#port").val();
    var database = $("#database").val();
    if(user == "" || password =="" || host == "" || port ==""){
        alert("请输入数据库配置");
        return;
    }
    if(database ==""){
        alert("请选择数据库");
        return;
    }
    var url = "ajax/table.do?u="+user+"&p="+password+"&h="+host+"&P="+port+"&db="+database;
    $.getJSON( url, function( data ) {
        var items = [];
        items.push("<option value=''>全部</option>");
        $.each( data, function( key, val ) {
            items.push( "<option value='" + val + "'>" + val + "</option>" );
        });
        $("#table").html(items.join(""));
    });
}

function build(){
    var user = $("#user").val();
    var password = $("#password").val();
    var host = $("#host").val();
    var port = $("#port").val();
    var database = $("#database").val();
    var packaging = $("#packaging").val();
    if(user == "" || password =="" || host == "" || port ==""){
        alert("请输入数据库配置");
        return;
    }
    if(database ==""){
        alert("请选择数据库");
        return;
    }
    if(packaging ==""){
        alert("请填写生成文件的package");
        return;
    }
    $("form").submit();
}