<#import "loginAndLogoutPart.ftlh" as lgnlgt>
<#include "security.ftlh">

<nav class="navbar sticky-top navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">SocialMedia</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>

            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/user-publications/${currentUserId}?page=0&size=3">My publications</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/createPublication">Create new publication</a>
                </li>

            </#if>

            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/administration">User administration</a>
                </li>
            </#if>

            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/administration/profile">Profile</a>
                </li>
            </#if>

        </ul>

        <div class="navbar-text mr-3">
            ${name}
        </div>

        <div>
            <#if user??>
                <@lgnlgt.logout/>
            </#if>
        </div>
    </div>
</nav>