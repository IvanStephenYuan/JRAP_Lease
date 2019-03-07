<div id="${id}"  <#if class??> class="${class!}" </#if> <#if style??> style="${style!}" </#if>  <#if name??> name="${name!}" </#if>  <#if bind??> data-bind="${bind!}" </#if> ${required!}></div>
<script>
    $("#${id}").kendoRadio(${config});
    <#if bindModel??>
    kendo.bind($("#${id}"), ${bindModel!});
    </#if>
</script>