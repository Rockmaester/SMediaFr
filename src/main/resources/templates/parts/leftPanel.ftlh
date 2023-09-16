<style>
    .specificbutton{
        text-align: left;
        padding-left: 0;
        font-size: smaller;
    }
    section{
        overflow:auto;
        min-height: 0;
        position:fixed;
        height: 100%;
        width: 15%;
        min-width: 170px;
        background: #bababa;
        overflow-x: scroll;
    }
</style>

<section>
    <#--Внедрение схлопывающихся списков подписчиков и подписок https://getbootstrap.com/docs/4.1/components/collapse/-->
    <div class="accordion" id="accordionExample">

        <div class="card">
            <div class="card-header" id="headingOne">
                <h6 class="mb-0">
                    <button class="btn btn-link collapsed specificbutton" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                        My subscribers <#if subscribersCount??>(${subscribersCount})</#if>
                    </button>
                </h6>
            </div>

            <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                <div class="card-body">
                    <#include "subscribersPart.ftlh"/>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header" id="headingTwo">
                <h6 class="mb-0">
                    <button class="btn btn-link collapsed specificbutton" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        I'm subscribed to ... <#if subscriptionsCount??>(${subscriptionsCount})</#if>
                    </button>
                </h6>
            </div>
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
                <div class="card-body">
                    <#include "subscriptionsPart.ftlh"/>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header" id="headingThree">
                <h6 class="mb-0">
                    <button class="btn btn-link collapsed specificbutton" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                        Тест скроллинга
                    </button>
                </h6>
            </div>
            <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordionExample" >
                <div class="card-body">
                    С учётом сложившейся международной обстановки, понимание сути ресурсосберегающих технологий напрямую зависит от глубокомысленных рассуждений.
                    Приятно, граждане, наблюдать, как сторонники тоталитаризма в науке будут ограничены исключительно образом мышления.
                    В частности, глубокий уровень погружения предоставляет широкие возможности для экономической целесообразности принимаемых решений.
                    Следует отметить, что внедрение современных направлений прогрессивного развития. Прежде всего, внедрение современных методик способствует
                    повышению качества переосмысления внешнеэкономических политик. Кстати, сделанные на базе интернет-аналитики выводы, которые представляют
                    собой яркий пример континентально-европейского типа политической культуры, будут рассмотрены исключительно в разрезе маркетинговых и финансовых предпосылок.
                </div>
            </div>
        </div>
    </div>
</section>
