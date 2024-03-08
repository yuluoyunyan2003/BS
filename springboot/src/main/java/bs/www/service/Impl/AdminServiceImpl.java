package bs.www.service.Impl;

import bs.www.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean outLogin() {
        return false;
    }
}
