<div><div id="${id}"  <#if class??> class="${class!}" </#if>  <#if style??> style="${style!}" </#if> ></div></div>
<script>
    $("#${id}").kendoGrid(${config});
    <#if bindModel??>
    kendo.bind($("#${id}"), ${bindModel!});
    </#if>
</script>