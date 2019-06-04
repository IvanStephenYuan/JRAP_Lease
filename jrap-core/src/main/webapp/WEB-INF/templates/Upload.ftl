<table class="table table-hover" id="${'${fid}_list'}" style="border:1px solid #e7ecf1;margin:0px;">
    <tbody id="${'${fid}_b'}">
    <#list file as f>
        <tr>
            <td style="vertical-align:middle"><a
                        href="javascript:showPicture(${f.attribute2},${f.fileId})">${f.fileName}</a>
            </td>
            <td style="vertical-align:middle">
                <#if enableRemove>
                    <span title="${delete}" style="color:red;cursor:pointer;font-size:16px;" class="fa fa-trash-o"
                          id="${f.fileId}" _token="${f._token}"></span>
                </#if>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<#if enableUpload=true>
    <style>
        .k-upload {
            border: none;
            background: none;
        }

        .k-upload .k-dropzone {
            padding: 0px;
            padding-top: 10px;
        }

    </style>
    <input type="file" name="files" id="${fid}">
</#if>
<script>

    <#if enableRemove=true>
    function uploadFileDeleteHanlder(e) {
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
            saveUrl: '${contextPath}/sys/attach/upload?_csrf=' + $('meta[name=_csrf]').attr('content') + '&tier=${tier}',
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
            if (e.response.success) {
                $.each(e.response.rows, function (i, file) {
                    var html = "<tr>" +
                        "<td style='vertical-align:middle'>" + "<a  href='javascript:showPicture(\"" + file.attribute2 + "\",\"" + file.fileId + "\")'>" + file.fileName + "</td>" +
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
            } else {
                kendo.ui.showInfoDialog({
                    message: e.response.message
                })
            }
        }
    });
    </#if>
    function showImg(url) {
        var imgBox = document.getElementById('imgBox');
        var targetImg = document.getElementById('targetImg');
        targetImg.src = url;
        targetImg.style.width = '80%';
        targetImg.style.height = 'auto';
        imgBox.style.zIndex = 1000000;
        imgBox.style.position = 'fixed';
        imgBox.style.top = 0;
        imgBox.style.left = 0;
        imgBox.style.width = '100%';
        imgBox.style.height = '100%';
        imgBox.style.background = 'rgba(0, 0, 0, 0.5)';
        imgBox.style.textAlign = 'center';
        imgBox.style.display = 'block';
        imgBox.onclick = function (ev) {
            imgBox.style.display = 'none';
            $("#targetImg").smartZoom("destroy");
        }
        $('#targetImg').smartZoom({
            'containerClass': 'zoomableContainer',
            mouseMoveEnabled: false,
            moveCursorEnabled: false
        });
    }
    function showPicture(tier, fileId) {
        // 根据id查询
        $.ajax({
            url: _basePath + '/sys/attachment/selectByFileId?fileId=' + fileId,
            type: "POST",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (args) {
                if (tier == "0") {
                    showImg('/jrap/resources/images/PICTURE/' + args.attribute3 + '/' + args.attribute1);
                } else if (tier == "1") {
                    showImg('/jrap/resources/images/PICTURE/' + args.attribute3 + '/' + args.attribute1);
                } else if (tier == "2") {
                    showImg('/jrap/resources/images/PICTURE/' + args.attribute3 + '/' + args.attribute1);
                }
            },

        });
    }
</script>
