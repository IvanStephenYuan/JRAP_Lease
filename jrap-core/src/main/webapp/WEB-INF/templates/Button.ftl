<span id="${id}" class="btn ${className!}" data-bind="${bind!}" <#if style??> style="${style!}" </#if> >${text!}</span>
<script>$("#${id}").kendoButton(${config});</script>