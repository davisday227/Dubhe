package ${packageName}.service;

import ${packageName}.entity.*;
import ${packageName}.common.PageList;
import ${packageName}.dao.*;
import ${packageName}.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${classInfo.classComment}业务层实现类
 *
 * @author ${authorName} ${.now?string('yyyy-MM-dd')}
 */
@Service
public class ${classInfo.className}Service {

    @Autowired
	private ${classInfo.className}Dpbc dao;

    public void add(${classInfo.className}AddDTO dto) {
        ${classInfo.className}Entity entity = dto.convertTo();
        dao.insert(entity);
    }

    public void delete(${classInfo.className}OperateDTO dto) {
    	dao.delete(dto);
    }
}