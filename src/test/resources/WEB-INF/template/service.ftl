package ${packaging}.back.service;

import ${packaging}.common.orm.Page;
import com.duoqu.rs.dao.entity.AdminUser;
import ${packaging}.dao.entity.${className};
import ${packaging}.dao.repository.${className}Dao;
import ${packaging}.common.dto.ResultDto;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("${lowerClassName}Service")
public class ${className}Service {

    @Autowired
    private ${className}Dao ${lowerClassName}Dao;

    private Map<Integer, ${className}> ${lowerClassName}Map;

    @PostConstruct
    public synchronized void reload() {
        ${lowerClassName}Map = Maps.newHashMap();

        List<${className}> list = ${lowerClassName}Dao.find(null);
        for (${className} row : list) {
            ${lowerClassName}Map.put(row.getId(), row);
        }
    }

    public ${className} get(Integer id) {
        return ${lowerClassName}Map.get(id);
    }


    public boolean isExist(Integer id) {
        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("id", id);
        return  ${lowerClassName}Dao.contain(parameters) > 0;
    }


    public boolean add(${className} ${lowerClassName}) {
        return ${lowerClassName}Dao.insert(${lowerClassName}) > 0;
    }

    public boolean update(${className} ${lowerClassName}) {
        return ${lowerClassName}Dao.update(${lowerClassName}) > 0;
    }

    public ResultDto save(${className} ${lowerClassName}, AdminUser user) {
        ResultDto result = new ResultDto();
        result.setSuccess(false);
        result.setInfo("更新成功！");
        ${lowerClassName}.setEditDate(new Date());
        ${lowerClassName}.setEditorId(user.getId());

        if (${lowerClassName}.getId() != null) {//update
            if (!isExist(${lowerClassName}.getId())) {
                if (update(${lowerClassName})) {
                    result.setSuccess(true);
                    result.setInfo("更新成功！");
                } else {
                    result.setInfo("更新失败！");
                }
            } else {
                result.setInfo("数据已存在");
            }
        } else {//insert
            ${lowerClassName}.setCreateDate(new Date());
            ${lowerClassName}.setCreatorId(user.getId());

            if (add(${lowerClassName})) {
                result.setSuccess(true);
                result.setInfo("保存成功！");
            } else {
                result.setInfo("保存失败！");
            }
        }

        if (result.getSuccess()) {
            reload();
        }

        return result;
    }

    public List<${className}> get${className}s(Integer id, Integer startIndex, Integer pageSize) {
        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("id", id);
        parameters.put("startIndex", startIndex);
        parameters.put("pageSize", pageSize);
        List<${className}> list = ${lowerClassName}Dao.find(parameters);
        if (list != null && list.size() > 0) {
            for (${className} row : list) {
            }
        }
        return list;
    }

    public int count(Integer id) {
        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("id", id);
        return ${lowerClassName}Dao.count(parameters);
    }

    public Page<${className}> getPage(Integer id, Integer startIndex, Integer pageSize) {
        Page<${className}> page = new Page<>();
        if (startIndex != null && pageSize != null) {
            page.setPageNo(startIndex / pageSize + 1);
            page.setPageSize(pageSize);
        }
        page.setResult(get${className}s(id, startIndex,
                pageSize));
        page.setTotalItems(count(id));
        return page;

    }

    public boolean delete(List<Integer> ids) {
        return ${lowerClassName}Dao.delete(ids) > 0;
    }
}
