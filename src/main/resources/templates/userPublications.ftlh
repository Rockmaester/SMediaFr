<#import "parts/common.ftlh" as cmn>

<@cmn.page>

    <div align="center">
        <#-- добавление кнопки подписаться / отписаться -->
        <#if !isCurrentUser>
            <#if isSubscriber>
                <a class="btn btn-info" href="/user/unsubscribe/${userChannel.id}">Unsubscribe from ${userChannel.username}</a>
            <#else>
                <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Subscribe to ${userChannel.username}</a>
            </#if>
        </#if>
    </div>

    <#include "parts/publicationsPart.ftlh"/>

</@cmn.page>