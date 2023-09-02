<#import "parts/common.ftlh" as cmn>

<@cmn.page>

    <div class="form-group mt-3">

        <form method="post" action="/savePublication" enctype="multipart/form-data">

            <div class="form-group">
                <input type="text"
                       name="title"
                        class="form-control ${(titleError??)?string('is-invalid','')}"
                        <#--это для сохранения написанного в форме, если при валидации будут ошибки в других полях-->
                       value="<#if publication??>${publication.title}</#if>"
                       placeholder="enter publication title">

                <#if titleError??>
                    <div class="invalid-feedback">
                        ${titleError}
                    </div>
                </#if>

            </div>

            <div class="form-group">
                <textarea name="content"
                          class="form-control ${(contentError??)?string('is-invalid','')}"
                          <#--это для сохранения написанного в форме, если при валидации будут ошибки в других полях-->
                          value="<#if publication??>${publication.content}</#if>"
                          placeholder="Enter text of publication here"
                          cols="100"
                          rows="10">
                </textarea>

                <#if contentError??>
                    <div class="invalid-feedback">
                        ${contentError}
                    </div>
                </#if>

            </div>

            <div class="form-group">
                <input type="file" name="file">
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}">

            <div class="form-group">
                <button type="submit">Add</button>
            </div>

        </form>

    </div>

</@cmn.page>