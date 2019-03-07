<table class="table table-hover" id="${'${fid}_list'}" style="border:1px solid #e7ecf1;margin:0px;">
    <thead>
        <tr>
            <th> ${filename} </th>
            <th> ${filetype} </th>
            <th> ${filesize} </th>
            <th> ${upload} </th>
            <th> </th>
        </tr>
    </thead>
    <tbody>
        <#list file as f>
        <tr>
            <td style="vertical-align:middle"> <a target="_blank" href="${contextPath}/sys/attach/file/download?fileId=${f.fileId}&&token=${f._token}">${f.fileName}</a></td>
            <td style="vertical-align:middle">${f.fileType}</td>
            <td style="vertical-align:middle">${f.fileSizeDesc!}</td>
            <td style="vertical-align:middle">${f.uploadDate?string('yyyy-MM-dd HH:mm:ss')}</td>
            <td style="vertical-align:middle">
                <#if enableRemove>
                    <span title="${delete}" style="color:red;cursor:pointer;font-size:16px;" class="fa fa-trash-o" id="${f.fileId}" _token="${f._token}"></span>
                </#if>
            </td>
        </tr>
        </#list>
    </tbody>
</table>

<#if enableUpload=true>
<style>
    .k-upload {
        border:none;
        background:none;
    }
    .k-upload .k-dropzone {
        padding:0px;
        padding-top: 10px;
    }
</style>
    <input type="file" name="files" id="${fid}">
</#if>
<script>

    <#if enableRemove=true>
    function uploadFileDeleteHanlder(e){
        kendo.ui.showConfirmDialog({
            title: $l('jrap.tip.info'),
            message: $l('jrap.tip.delete_confirm')
        }).done(function (event) {
            if (event.button == 'OK') {
                $.ajax({
                    type: "post",
                    url: "${contextPath}/sys/attach/file/delete",
                    contentType: "application/json",
                    data: JSON.stringify({
                        fileId: $(e.target).attr('id'),
                        _token: $(e.target).attr('_token')
                    }),
                    success: function (result) {
                        if (result.success) {
                            var ids = result.rows[0].fileId;
                            ($('#' + ids).parents("tr")).remove();
                            kendo.ui.showInfoDialog({
                                message: $l('jrap.mesg_delete')
                            });
                        } else {
                            kendo.ui.showErrorDialog({
                                message: result.message
                            });
                        }
                    }
                })
            }
        })
    }

    $("#${fid}_list .fa-trash-o").on("click.uploader", uploadFileDeleteHanlder);
    </#if>

    <#if enableUpload=true>
    $("#${fid}").kendoUpload({
        async: {
            saveUrl: '${contextPath}/sys/attach/upload?_csrf=' + $('meta[name=_csrf]').attr('content'),
            autoUpload: true
        },
        showFileList: false,
        upload: function (e) {
            e.data = {
                sourceType: '${sourceType}',
                sourceKey: '${sourceKey}'
            }
        },
        success: function (e) {
            if(e.response.success){
                $.each(e.response.rows,function(i,file){
                    var html = "<tr>" +
                            "<td style='vertical-align:middle'>" +"<a  href='${contextPath}/sys/attach/file/download?fileId=" + file.fileId + "&token=" + file._token + "'>" + file.fileName + "</td>" +
                            "<td style='vertical-align:middle'>" + file.fileType + "</td>" +
                            "<td style='vertical-align:middle'>" + file.fileSizeDesc + "</td>" +
                            "<td style='vertical-align:middle'>" + file.uploadDate + "</td>" +
                            "<td style='vertical-align:middle'>" <#if enableRemove==true> + "<span title='${delete}' style='color:red;cursor:pointer;font-size:16px;' class='fa fa-trash-o' id='" + file.fileId + "'_token='" + file._token + "'></span>" </#if> + "</td>" +
                            "</tr>";
                    <#if unique == 'Y'>
                        $("#${fid}_list tbody").empty();
                    </#if>
                    $("#${fid}_list").append(html);
                    $("#${fid}_list .fa-trash-o").off(".uploader");
                    <#if enableRemove==true>
                        $("#${fid}_list .fa-trash-o").on("click.uploader", uploadFileDeleteHanlder);
                    </#if>
                })
            }
            kendo.ui.showInfoDialog({
                message  : e.response.message
            })
        }
    });
    </#if>
</script>
