<input id="${id}"  <#if class??> class="${class!}" </#if> <#if style??> style="${style!}" </#if> <#if name??> name="${name!}" </#if> <#if bind??> data-bind="${bind!}" </#if> ${dataMsg!} ${validationMessage!}  ${required!}/>
<script>
    $("#${id}").kendoLov(${config});
    <#if bindModel??>
    kendo.bind($("#${id}"), ${bindModel!});
    </#if>
</script>