
<#include "security.ftlh">
<#import "paginator.ftlh" as pgntr>

<div class="m-5" align="center">

    <div>
        <@pgntr.paginator url page pagesize/>
    </div>

    <#list page.content as publication>

        <div class="card my-3" style="width: auto; max-width: 36rem">

            <div>
                <b>${publication.title}</b>
            </div>

            <div>
                author:
                <a href="/user-publications/${publication.author.id}?page=0&size=3">
                    <i>${publication.authorName}</i>
                </a>
            </div>


            <div>
                <#if publication.filename??>
                    <img src="/img/${publication.filename}" class="card-img-top">
                </#if>
            </div>

            <div class="m-2">
                <span>${publication.content}</span>
            </div>

            <div>
                <#if publication.author.id == currentUserId>
                    <a class="btn btn-primary" href="/editPublication/${publication.author.id}?publicationId=${publication.id}">
                        Edit publication
                    </a>
                </#if>
            </div>

        </div>
    <#else>
        No publications
    </#list>

    <@pgntr.paginator url page pagesize/>

</div>