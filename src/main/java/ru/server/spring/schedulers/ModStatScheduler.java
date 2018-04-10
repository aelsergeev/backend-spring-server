package ru.server.spring.schedulers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.server.spring.configs.SchedulersConfiguration;
import ru.server.spring.services.AdminService;
import ru.server.spring.services.ModStatCategoryService;
import ru.server.spring.services.ModStatService;


@Component
@EnableScheduling
public class ModStatScheduler {

    private final AdminService adminService;
    private final ModStatCategoryService modStatCategoryService;
    private final ModStatService modStatService;
    private final SchedulersConfiguration schedulersConfiguration;

    public ModStatScheduler(AdminService adminService, ModStatCategoryService modStatCategoryService, ModStatService modStatService, SchedulersConfiguration schedulersConfiguration) {
        this.adminService = adminService;
        this.modStatCategoryService = modStatCategoryService;
        this.modStatService = modStatService;
        this.schedulersConfiguration = schedulersConfiguration;
    }

    @Scheduled(cron = "${schedulers.stat-category.cron}")
    private void getStatCategory() {
        if (schedulersConfiguration.getSchedulersProperties().getClearModStat().getEnable()) {
            Document doc = adminService.getModerationStatistics();

            Element table = doc.select("table tbody").first();
            Elements rows = table.select("tr");

            for (Element row : rows) {
                String colName = row.select("td").get(1).text();
                int colItemCount = Integer.parseInt(row.select("td").get(2).text());

                modStatCategoryService.update(colName, colItemCount);
            }
        }
    }

    @Scheduled(cron = "${schedulers.clear-mod-stat.cron}")
    private void clearModStat() {
        if (schedulersConfiguration.getSchedulersProperties().getStatCategory().getEnable())
            modStatService.clear();
    }

}