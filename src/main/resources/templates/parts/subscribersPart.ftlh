<#if mysubscribers??>
    <#list mysubscribers as subscriber>
        <div>
            <a href="/user-publications/${subscriber.id}">${subscriber.getUsername()}</a>
        </div>
    <#else>
        No subscribers
    </#list>
</#if>