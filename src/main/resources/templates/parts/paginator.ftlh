<#macro paginator url page pagesize>

    <div class="mt-3">
        <ul class="pagination justify-content-center pagination-sm">
            <li class="page-item <#if page.getNumber() == 0>disabled</#if>">
                <a class="page-link" href="${url}?page=${page.getNumber() - 1}&size=${pagesize}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Previous</span>
                </a>
            </li>
            <#list 1..page.getTotalPages() as pageInLoop>

                <#if (pageInLoop - 1) == page.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="${url}?page=${page.getNumber()}&size=${pagesize}" tabindex="-1">${pageInLoop}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${pageInLoop - 1}&size=${pagesize}" tabindex="-1">${pageInLoop}</a>
                    </li>
                </#if>
            </#list>
            <li class="page-item <#if page.getNumber() == (page.getTotalPages() - 1)>disabled</#if>">
                <a class="page-link" href="${url}?page=${page.getNumber() + 1}&size=${pagesize}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Next</span>
                </a>
            </li>
        </ul>
    </div>
</#macro>