package the.beacon.emsvue.service;

import the.beacon.emsvue.entity.Emp;

import java.util.List;

public interface EmpService {

    List<Emp> findAll();

    void save(Emp emp);

    void deleteById(String id);

    Emp findById(String id);

    void update(Emp emp);
}
