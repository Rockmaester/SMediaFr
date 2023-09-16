<#import "parts/common.ftlh" as cmn>


<@cmn.page>

    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>

    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
        <button class="btn btn-primary" onclick="window.location.href = '/'">Ok</button>
    </#if>

</@cmn.page>