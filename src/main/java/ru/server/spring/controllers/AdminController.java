package ru.server.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.server.spring.models.api.WalletLog;
import ru.server.spring.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/item/activate")
    public String activateItem(@RequestBody int id) {
        return adminService.activateItem(id);
    }

    @PostMapping("/user/wallet/log")
    public List<WalletLog> walletLog(@RequestBody int id) {
        return adminService.getTransactionsFromWalletLogByUserId(id);
    }

}
