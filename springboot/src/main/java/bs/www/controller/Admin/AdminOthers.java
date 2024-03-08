package bs.www.controller.Admin;

import bs.www.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminOthers {
    @Autowired
    private AdminService adminService;
}
