<div id="${id}" <#if style??> style="${style!}" </#if>  <#if class??> class="${class!}" </#if> >
    <div class="k-query-simple ${className!}">   
      ${common!}
    </div>
    <div class="k-query-detail ${className!}">
      ${advance!}
    </div>
</div>
<script>
$("#${id}").kendoQueryPanel(${config});</script>