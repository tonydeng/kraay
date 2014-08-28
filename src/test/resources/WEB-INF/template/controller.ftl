package ${packaging}.back.controller;

import ${packaging}.back.service.${className}Service;
import ${packaging}.common.orm.Page;
import ${packaging}.dao.entity.${className};
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 */
@Controller
@RequestMapping("/${lowerClassName}/*")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${lowerClassName}Service;

    @RequestMapping("list.do")
    @ResponseBody
    public Page<${className}Dto> list(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "limit", required = false) Integer limit) {

        return ${lowerClassName}Service
                .getPage(id, start, limit);
    }

    @RequestMapping("save.do")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute("${className}") ${className} ${lowerClassName},
                                    @SessionAttribute("adminUser") AdminUser adminUser) {

        Map<String, Object> responseMap = Maps.newHashMap();
        responseMap.put("success", "false");

        ResultDto result = ${lowerClassName}Service.save(${lowerClassName}, adminUser);

        responseMap.put("success", result.getSuccess());
        responseMap.put("info", result.getInfo());

        return responseMap;
    }

    @RequestMapping("del.do")
    @ResponseBody
    public Map<String, Object> del(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "ids", required = false) String ids) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("success", "false");

        if (StringUtils.isNotEmpty(ids)) {
            String[] data = ids.split(",");
            List<Integer> list = new ArrayList<Integer>();
            for (String id : data) {
                list.add(Integer.valueOf(id));
            }

            if (${lowerClassName}Service.delete(list)) {
                responseMap.put("success", "true");
                responseMap.put("info", "成功删除用户！");
            } else {
                responseMap.put("info", "删除用户失败！");
            }
        } else {
            responseMap.put("info", "请选择要删除的用户！");
        }

        return responseMap;
    }
}
