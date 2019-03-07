<!DOCTYPE html>
<html>
<head>
<title>${SYS_TITLE!'JingRui Application Platform'}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="X-CSRF-TOKEN" />
<link href="${base.contextPath}/resources/upload/favicon.png" rel="shortcut icon" />
<!--[if IE 8]>
<script src="${base.contextPath}/lib/polyfill/respond.min.js"></script>
<script src="${base.contextPath}/lib/polyfill/es5-shim.min.js"></script>
<![endif]-->
<script src="${base.contextPath}/lib/kendoui/js/jquery.min.js"></script>
<script src="${base.contextPath}/lib/kendoui/js/kendo.all.min.js?v=20170711"></script>
<link href="${base.contextPath}/lib/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${base.contextPath}/lib/font-awesome-4.6.3/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${base.contextPath}/lib/kendoui/styles/kendo.common-bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${base.contextPath}/lib/kendoui/styles/kendo.bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${base.contextPath}/lib/kendoui/styles/kendo.jrap.css?v=20170711" rel="stylesheet" type="text/css"/>
<script src="${base.contextPath}/lib/kendoui/js/cultures/kendo.culture.${base.locale.toString()?replace('_','-')}.js"></script>
<script src="${base.contextPath}/lib/kendoui/js/messages/kendo.messages.${base.locale.toString()?replace('_','-')}.js"></script>
<script src="${base.contextPath}/lib/kendoui/js/kendo.jrap.js?v=20170711"></script>
<script src="${base.contextPath}/common/prompts"></script>
<script>
_basePath = '${base.contextPath}',_locale='${base.locale}'; 
kendo.culture("${base.locale.toString()?replace('_','-')}");
</script>
<#include "include/head_custom.html">
</head>
<body>
<#macro lov lovid>${lovProvider.getLov(base.contextPath,base.locale,lovid)}</#macro>
${content}
</body>
</html>