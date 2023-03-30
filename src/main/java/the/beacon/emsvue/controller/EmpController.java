package the.beacon.emsvue.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import the.beacon.emsvue.entity.Emp;
import the.beacon.emsvue.service.EmpService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("emp")
@CrossOrigin
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("findAll")
    public List<Emp> findAll() {
        List<Emp> all = empService.findAll();
        log.info("wtf:"+all);
        return all;
    }

    // 根据id查询员工信息
    @GetMapping("findOne")
    public Emp findOne(String id) {
        return empService.findById(id);
    }

    @Value("${photo.dir}")
    private String savePath;

    //保存员工信息
    @PostMapping("saveEmp")
    public Map<String, Object> saveEmp(Emp emp, MultipartFile photo){
        Map<String, Object> map = new HashMap<>();
        try {
            // 保存头像
            String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(photo.getOriginalFilename());
            photo.transferTo(new File(savePath, newFileName));
            emp.setPath(newFileName);

            empService.save(emp);

            map.put("state", true);
            map.put("msg", "员工信息保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "员工信息保存失败");
        }
        return map;
    }

    // 删除员工信息
    @GetMapping("deleteEmp")
    public Map<String, Object> deleteEmp(String id) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 先删除头像
            Emp emp = empService.findById(id);
            File file = new File(savePath, emp.getPath());
            if (file.exists()) {
                file.delete();
            }
            // 再删除员工
            empService.deleteById(id);
            map.put("state", true);
            map.put("msg", "员工信息已删除");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "删除失败...");
        }

        return map;
    }

    // 修改员工信息
    @PostMapping("updateEmp")
    public Map<String, Object> updateEmp(Emp emp, MultipartFile photo){
        Map<String, Object> map = new HashMap<>();
        try {
            // 如果有更新头像，先保存头像
            if (photo != null && photo.getSize() > 0) {
                String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(photo.getOriginalFilename());
                photo.transferTo(new File(savePath, newFileName));
                emp.setPath(newFileName);
            }

            empService.update(emp);

            map.put("state", true);
            map.put("msg", "员工信息修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "员工信息修改失败");
        }
        return map;
    }

}
