<#import "parts/common.ftlh" as cmn>

<@cmn.page>

    <h4> Profile of <b>${username}</b>.</h4>
    ${message!}

    <form  method="post">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Set the password: </label>
            <div class="col-sm-6">
                <input type="password"
                       name="password"
                       class="form-control ${(passwordError??)?string('is-invalid','')}"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Set the email: </label>
            <div class="col-sm-6">
                <input type="email"
                       name="email"
                       class="form-control ${(emailError??)?string('is-invalid','')}"
                       placeholder="some@some.com"
                       value="${email!''}"/>
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@cmn.page>