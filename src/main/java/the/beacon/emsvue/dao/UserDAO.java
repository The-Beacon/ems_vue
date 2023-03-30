package the.beacon.emsvue.dao;

import org.apache.ibatis.annotations.Mapper;
import the.beacon.emsvue.entity.User;

@Mapper  // 创建DAO对象
public interface UserDAO {

    void save(User user);

    User findByUserName(String userName);
}
