package ${packageName}.funservice;

import ${packageName}.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ${classInfo.classComment}接口
 *
 * @author ${authorName} ${.now?string('yyyy-MM-dd')}
 */
@Controller
@RequestMapping(value = "/${classInfo.modelName}")
public class ${classInfo.className}FunService {

    @Autowired
    private ${classInfo.className}Service service;

    /**
     * 新增${classInfo.classComment}
     */
    @RequestMapping(value = "/add")
    public void add(${classInfo.className}AddDTO entity) {
        service.add(entity);
    }

    /**
     * 删除${classInfo.classComment}
     */
    @RequestMapping(value = "/delete")
    public void delete(${classInfo.className}OperateDTO entity) {
        service.delete(entity);
    }

    /**
     * 修改${classInfo.classComment}
     */
    @RequestMapping(value = "/update")
    public void update(${classInfo.className}UpdateDTO entity) {
        service.update(entity);
    }

    /**
     * 分页查询${classInfo.classComment}
     */
    @RequestMapping(value = "/query")
    public Page<${classInfo.className}PageQueryVO> update(${classInfo.className}QueryDTO entity) {
        return service.query(entity);
    }

    /**
     * 详情查询${classInfo.classComment}
     */
    @RequestMapping(value = "/getOne")
    public ${classInfo.className}DetailVO getOne(${classInfo.className}OperateDTO entity) {
        return service.getOne(entity);
    }
}
