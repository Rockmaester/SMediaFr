<#import "parts/common.ftlh" as cmnPg>
<#import "parts/loginAndLogoutPart.ftlh" as lgn>

<@cmnPg.page>

  <h2>Login page</h2>

  <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-danger" role="alert">
      ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
  </#if>

  <#if message??>
    <div class="alert alert-${messageType}" role="alert">
      ${message}
    </div>
  </#if>

  <@lgn.login "/login" false/>

</@cmnPg.page>