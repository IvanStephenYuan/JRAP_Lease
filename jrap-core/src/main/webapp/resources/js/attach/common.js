//从附件列表中删除此附件
function rmfile(id) {
	if (confirm("是否删除此附件")) {
		var file = {
			fileId : id
		}
		fileAjax("/sys/attach/remove", file)
		$("#" + id).remove();
	}
}

function fileAjax(purl, pdata) { //发起文件请求
	var aj = $.ajax({
		url : $("#contextPath").val() + purl,//路径 
		data : pdata,
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.message == "success") {
				console.log("delete success");
			} else {
			}
		},
		error : function() {
			alert("网络出错等！");
		}
	});
}