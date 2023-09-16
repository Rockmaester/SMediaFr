<#if subscriptions??>
    <#list subscriptions as subscription>
        <div>
            <a href="/user-publications/${subscription.id}">${subscription.getUsername()}</a>
        </div>
        <#else>
            No subscriptions
    </#list>
</#if>

