package ru.server.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.server.spring.models.api.WalletLog;
import ru.server.spring.schedulers.AdminScheduler;
import ru.server.spring.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminScheduler adminScheduler;

    @Autowired
    public AdminController(AdminService adminService, AdminScheduler adminScheduler) {
        this.adminService = adminService;
        this.adminScheduler = adminScheduler;
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
    public String hdGroupCount(@RequestParam String group) {
        switch (group) {
            case "moder":
                return adminScheduler.getModerGroupHelpDeskCount();
            case "domofond":
                return adminScheduler.getDomofondGroupHelpDeskCount();
            default:
                return "this group does not exist";
        }
    }

}
