package ${packaging}.repository;

import ${packaging}.entity.${tableName};

import java.util.List;
import java.util.Map;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 */
@MyBatisRepository
public interface ${tableName}Dao {

	${tableName} get(Integer id);

	int contain(Map<String, Object> parameters);

	List<${tableName}> find(Map<String, Object> parameters);

	int count(Map<String, Object> parameters);

	int insert(${tableName} ${lowerTable}) ;

	int update(${tableName} ${lowerTable});

	int delete(Integer id);

	int delete(List<Integer> ids);
}
