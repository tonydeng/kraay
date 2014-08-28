<#ftl encoding='UTF-8'>
package ${packaging}.dao.entity;

import java.util.Date;

public class ${className} {
<#list fields as field>
    private ${field.type} ${field.lower};
</#list>

<#list fields as field>
    public ${field.type} get${field.upper}() {
        return ${field.lower};
    }

    public void set${field.upper}(${field.type} ${field.lower}) {
        this.${field.lower} = ${field.lower};
    }
</#list>
}
