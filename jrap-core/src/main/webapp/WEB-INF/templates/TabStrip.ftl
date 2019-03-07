<div id="${id}"  <#if class??> class="${class!}" </#if> <#if style??> style="${style!}" </#if>>
    <ul>${tab!}</ul>
    ${tabContent!}
</div>
<script>
$("#${id}").kendoTabStrip(${config});</script>