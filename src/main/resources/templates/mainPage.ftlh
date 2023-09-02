<#import "parts/loginAndLogoutPart.ftlh" as lgnlgt>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SMFront</title>
    <link rel="stylesheet" href="/static/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
</head>
<body>
<#include "parts/navigationPanel.ftlh">

<div class="container">

    <div class="row">

        <div class="col">

            <div class="card-columns">

                <#list publications as publication>

                    <div class="card my-3" style="width: 18rem;">

                        <div>
                            <b>${publication.title}</b>
                        </div>

                        <div>
                            <span>${publication.authorName}</span>
                        </div>
                        <div>
                            <#if publication.filename??>

                                <img src="/img/${publication.filename}" class="card-img-top"
                            </#if>
                        </div>

                        <div class="m-2">
                            <span>${publication.content}</span>
                        </div>

                    </div>

                <#else>
                    No publications
                </#list>

            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>