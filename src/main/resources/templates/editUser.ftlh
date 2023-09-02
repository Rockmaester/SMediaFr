<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit user</title>
    <link rel="stylesheet" href="/static/style.css">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
          crossorigin="anonymous">
</head>
<body>

<#include "parts/navigationPanel.ftlh">

<div class="m-4">

    <h4>User editor</h4>

    <form action="/administration" method="post">
        <input type="text" name="username" value="${userToEdit.username}">

        <#list roles as role>
            <div>
                Роль активна / не активна: <labeL><input type="checkbox" name="${role}" ${userToEdit.roles?seq_contains(role)?string("checked", "")}>${role}</labeL>
            </div>
        </#list>

        <input type="hidden" value="${userToEdit.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <br/>
        <button type="submit">Save</button>
    </form>

</div>



</body>
</html>