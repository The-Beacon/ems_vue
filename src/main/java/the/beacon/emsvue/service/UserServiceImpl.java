package the.beacon.emsvue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import the.beacon.emsvue.dao.UserDAO;
import the.beacon.emsvue.entity.User;

import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void register(User user) {
        // 0. 根据用户名判断用户是否存在，防止重复用户名 （毕竟没有显式ID）
        if (userDAO.findByUserName(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在...");
        }
        // 1. 生成用户状态
        user.setStatus("已激活");
        // 2. 设置用户注册时间
        user.setRegisterTime(new Date());
        // 3. 调用DAO （其它内容由表单提交或自动生成）
        userDAO.save(user);
    }

    @Override
    public User login(User user) {
        // 1. 根据用户输入用户名进行查询
        User userDB = userDAO.findByUserName(user.getUsername());
        if (!ObjectUtils.isEmpty(userDB)) {
            if (userDB.getPassword().equals(user.getPassword())) {
                return userDB;
            } else {
                throw new RuntimeException("密码错误...");
            }
        } else {
            throw new RuntimeException("用户名不存在...");
        }
    }
}
