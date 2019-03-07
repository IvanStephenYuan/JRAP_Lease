<div id="${id}"  <#if class??> class="${class!}" </#if> <#if style??> style="${style!}" </#if>></div>
<script>$("#${id}").kendoTreeList(${config});</script>