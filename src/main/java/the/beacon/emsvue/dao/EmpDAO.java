package the.beacon.emsvue.dao;

import org.apache.ibatis.annotations.Mapper;
import the.beacon.emsvue.entity.Emp;

import java.util.List;

/**
* @author TheBeacon
* @description 针对表【t_emp】的数据库操作Mapper
* @createDate 2023-03-28 15:40:04
* @Entity the.beacon.emsvue.entity.Emp
*/
@Mapper
public interface EmpDAO {

    List<Emp> findAll();

    void save(Emp emp);

    void deleteById(String id);

    Emp findById(String id);

    void update(Emp emp);
}




