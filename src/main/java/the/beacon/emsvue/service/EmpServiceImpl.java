package the.beacon.emsvue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import the.beacon.emsvue.dao.EmpDAO;
import the.beacon.emsvue.entity.Emp;

import java.util.List;

@Service
@Transactional
public class EmpServiceImpl implements EmpService{

    @Autowired
    private EmpDAO empDAO;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Emp> findAll() {
        return empDAO.findAll();
    }

    @Override
    public void save(Emp emp) {
        empDAO.save(emp);
    }

    @Override
    public void deleteById(String id) {
        empDAO.deleteById(id);
    }

    @Override
    public Emp findById(String id) {
        return empDAO.findById(id);
    }

    @Override
    public void update(Emp emp) {
        empDAO.update(emp);
    }
}
