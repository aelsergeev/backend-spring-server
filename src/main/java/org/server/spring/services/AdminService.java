package org.server.spring.services;

import com.google.gson.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.server.spring.configs.AdminConfiguration;
import org.server.spring.exceptions.AdminException;
import org.server.spring.models.api.WalletLog;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

public interface AdminService {
    Document getModerationStatistics();
    String activateItem(int id);
    List<WalletLog> getTransactionsFromWalletLogByUserId(int userId);
    Set<Integer> getUsersFromWalletLogByTransaction(String transactionId);
    JsonObject getProblemTickets(int ticketLimit, String startReactionTime, String endReactionTime);
    JsonObject getTicket(int id);
    JsonArray getUserAdminList();
    JsonObject getGroupFilterHelpdeskCount(int id);
}

class AdminAuthorizationServiceImpl {

    private static final Logger logger = Logger.getLogger(AdminService.class.getName());

    private final String username;
    private final String password;
    private final String userAgent;
    private final String domain;

    private volatile Map<String, String> cookiesAdmin = new HashMap<>();

    AdminAuthorizationServiceImpl(AdminConfiguration adminConfiguration) {
        username = adminConfiguration.getAdminProperties().getUsername();
        password = adminConfiguration.getAdminProperties().getPassword();
        userAgent = adminConfiguration.getAdminProperties().getUserAgent();
        domain = adminConfiguration.getAdminProperties().getDomain();
    }

    private void setCookiesAdmin(Map<String, String> cookiesAdmin) {
        this.cookiesAdmin.putAll(cookiesAdmin);
    }

    private boolean isAuthorised() {
        return cookiesAdmin.containsKey("adm_authkey") && !cookiesAdmin.get("adm_authkey").equals("");
    }

    @PostConstruct
    private void tryAuthorise() {
        try {
            Connection.Response authorization = Jsoup.connect(domain + "/login")
                    .method(Connection.Method.POST)
                    .userAgent(userAgent)
                    .header("Referer", domain)
                    .data("username", username, "password", password)
                    .followRedirects(false)
                    .execute();

            setCookiesAdmin(authorization.cookies());

            if (isAuthorised()) logger.info("Login to " + domain);
            else throw new AdminException("The password for " + domain + " is outdated");
        } catch (IOException io) {
            throw new AdminException(io.toString());
        }
    }

    private Connection.Response sendRequest(Connection connection) {
        try {
            Connection.Response response = connection
                    .userAgent(userAgent)
                    .cookies(cookiesAdmin)
                    .header("Referer", domain)
                    .followRedirects(false)
                    .execute();

            setCookiesAdmin(response.cookies());

            if (isAuthorised()) return response;
            else {
                tryAuthorise();
                throw new AdminException("Session for " + domain + " is outdated");
            }
        } catch (IOException io) {
            throw new AdminException(io.toString());
        }
    }

    void getResponse(String url) {
        Connection connection = Jsoup.connect(domain + url);
        sendRequest(connection);
    }

    Document getDocument(String url) {
        try {
            Connection connection = Jsoup.connect(domain + url);
            return sendRequest(connection).parse();
        } catch (IOException e) {
            throw new AdminException(e.toString());
        }
    }

    JsonElement getJsonGet(String url) {
        Connection connection = Jsoup.connect(domain + url).ignoreContentType(true);
        return new JsonParser().parse(sendRequest(connection).body());
    }

    JsonElement getJsonPost(String url, String body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");

        Connection connection = Jsoup.connect(domain + url).ignoreContentType(true).method(Connection.Method.POST).headers(headers).requestBody(body);
        return new JsonParser().parse(sendRequest(connection).body());
    }

}


@Service
class AdminServiceImpl extends AdminAuthorizationServiceImpl implements AdminService {

    private AdminServiceImpl(AdminConfiguration adminConfiguration) {
        super(adminConfiguration);
    }

    private Map<String, String> getRequestDate() {
        int transactionsSearchDays = 90;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy+HH:mm").withLocale(Locale.UK).withZone(ZoneId.systemDefault());
        Instant instant = Instant.now();

        Map<String, String> dates = new HashMap<>();
        dates.put("firstDay", formatter.format(instant.minus(Duration.ofDays(transactionsSearchDays))));
        dates.put("secondDay", formatter.format(instant));

        return dates;
    }

    public Document getModerationStatistics() {
        return getDocument("/moderation/statistics/category");
    }

    public String activateItem(int id) {
        Document document = getDocument("/items/item/info/" + id);

        Element element = document.select(".form-group label:contains(Статус)").first().parent();
        String status = element.select(".form-control-static span").first().text();

        if (status.contains("Reject")) {
            getResponse("/items/item/activate/" + id);
            return "Item activated";
        } else {
            return "Invalid item status: " + status;
        }
    }

    public List<WalletLog> getTransactionsFromWalletLogByUserId(int userId){
        Map<String, String> dates = getRequestDate();

        String firstDay = dates.get("firstDay");
        String secondDay = dates.get("secondDay");

        String url = "/billing/walletlog?date=" + firstDay + "+-+" + secondDay + "&userIds=" + userId;

        Document documentByUser = getDocument(url);

        Element table = documentByUser.select(".table tbody").first();
        Elements rows = table.select("tr");


        Set<String> transactions = new HashSet<>();
        for (Element row : rows) {
            Element tmp = row.select("td").get(6);
            if (tmp.hasText()) transactions.add(tmp.text());
        }


        List<WalletLog> walletLogList = new ArrayList<>();
        for (String transaction : transactions) {
            Set<Integer> userIds = getUsersFromWalletLogByTransaction(transaction);

            WalletLog walletLog = new WalletLog(transaction, userIds);

            walletLogList.add(walletLog);
        }

        return walletLogList;
    }

    public Set<Integer> getUsersFromWalletLogByTransaction(String transactionId) {
        Map<String, String> dates = getRequestDate();

        String firstDay = dates.get("firstDay");
        String secondDay = dates.get("secondDay");

        String url = "/billing/walletlog?date=" + firstDay + "+-+" + secondDay + "&payerCode=" + transactionId;


        Set<Integer> userIds = new HashSet<>();
        Document documentByUser = getDocument(url);

        Element table = documentByUser.select(".table tbody").first();
        Elements rows = table.select("tr");

        for (Element row : rows) {
            userIds.add(Integer.parseInt(row.select("td").get(4).text()));
        }

        return userIds;
    }

    public JsonObject getProblemTickets(int ticketLimit, String startReactionTime, String endReactionTime) {
        String url = "/helpdesk/api/1/proxy?method=ticket/search";

        int[] statusId = { 2, 3, 4, 5 };
        int[] typeId = { 3 };

        Map<String, String> reactionTxtime = new HashMap<>();
        reactionTxtime.put("start", startReactionTime);
        reactionTxtime.put("end", endReactionTime);

        Map<String, Object> data = new HashMap<>();
        data.put("method", "ticket/search");
        data.put("sortType", "desc");
        data.put("sortField", "reactionTxtime");
        data.put("statusId", statusId);
        data.put("typeId", typeId);
        data.put("limit", ticketLimit);
        data.put("reactionTxtime", reactionTxtime);

        String body = new Gson().toJson(data);

        return getJsonPost(url, body).getAsJsonObject();
    }

    public JsonObject getTicket(int id) {
        String url = "/helpdesk/api/1/proxy?method=ticket/admin_get";

        Map<String, Object> data = new HashMap<>();
        data.put("method", "ticket/admin_get");
        data.put("ticketId", id);

        String body = new Gson().toJson(data);

        return getJsonPost(url, body).getAsJsonObject().getAsJsonObject("result");
    }

    public JsonArray getUserAdminList() {
        String url = "/helpdesk/api/1/proxy?method=user/admin/list";

        Map<String, String> data = new HashMap<>();
        data.put("method", "user/admin/list");

        String body = new Gson().toJson(data);

        return getJsonPost(url, body).getAsJsonObject().getAsJsonArray("result");
    }

    public JsonObject getGroupFilterHelpdeskCount(int id) {
        String url = "/helpdesk/api/1/proxy?method=filter/group/counts";

        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(id));
        data.put("method", "filter/group/counts");

        String body = new Gson().toJson(data);

        return getJsonPost(url, body).getAsJsonObject();
    }

}
