package org.server.spring.controllers;

import org.server.spring.dao.CachedDao;
import org.server.spring.models.api.HelpdeskQueueApi;
import org.server.spring.models.api.WalletLog;
import org.server.spring.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final CachedDao cachedDao;

    @Autowired
    public AdminController(AdminService adminService, CachedDao cachedDao) {
        this.adminService = adminService;
        this.cachedDao = cachedDao;
    }

    @PostMapping("/item/activate")
    public String activateItem(@RequestParam int id) {
        return adminService.activateItem(id);
    }

    @PostMapping("/user/wallet/log")
    public List<WalletLog> walletLog(@RequestParam int id) {
        return adminService.getTransactionsFromWalletLogByUserId(id);
    }

    @PostMapping("/hd/group/count")
    public List<HelpdeskQueueApi> hdGroupCount() {
        return cachedDao.getGroupHelpDeskCount();
    }

}
