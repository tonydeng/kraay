package ${packaging}.dao.repository;

import ${packaging}.dao.entity.${className};

import java.util.List;
import java.util.Map;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 */
@MyBatisRepository
public interface ${className}Dao {

	${className} get(Integer id);

	int contain(Map<String, Object> parameters);

	List<${className}> find(Map<String, Object> parameters);

	int count(Map<String, Object> parameters);

	int insert(${className} ${lowerClassName}) ;

	int update(${className} ${lowerClassName});

	int delete(Integer id);

	int delete(List<Integer> ids);

	int insertBatch(List<${className}> list);

	int updateBatch(List<${className}> list);
}
