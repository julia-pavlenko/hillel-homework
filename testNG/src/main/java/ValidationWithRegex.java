import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationWithRegex {

    public static void main(String[] args) {
        System.out.println(parseActivity("Jun  8 14:15:18 skyfence sf_gateway[12923] [12948]: MgmtInterface INFO[MgmtIntfNetworkEvent.cpp:161] Activity: [Event Info: [EventType: Full] [Login Username: user1@skyromi.onmicrosoft.com (repository: user1@skyromi.onmicrosoft.com)] [Data Object: ] [User Action: login] [User Action Status: success] [User Action Status Reason: ] [Time: Thu, 08 Jun 2017 14:15:18 GMT] [Service type: Box] [File Size: 0] [Authentication Type: Form] [IsManagedEndpoint: false] [Asset Id: 1] [Event Id: 43681314331638] [Session Id: 3450197739] [Gateway Action Status: monitor] [Client IP: 192.168.3.216] [Server IP: 107.152.27.215] [Client Location: 03 0/0 unknown] [Server Location: US 37.3622/-122.14] [Browser Id: 2R5X2kHSBC57ccb978] [Device Id: c48eec5fe0ddb37a8584cff2fe34acbbc2651fdf0906ed1029c8e4e696193f7c] [OS: Windows] [OS version: 10] [device type: PC] [device version: ] [Client Type: desktop] [Customer Id: 1] [DataTypes: ] [icapConnectors: ] [TOR Networks: ] [Anonymous Proxies: ] [Malicious IPs: ] [IP Chain: 192.168.3.216] [Mapping Ids: Box-1,Box-2,Box-5] [Is DLP excceeds: False] [Useragent: Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko] [source: proxy] [URI: https://skyromi1.account.box.com/login?redirect_url=/folder/0] ]\n" +
                "Jun  8 14:15:47 skyfence sf_gateway[12923] [12948]: MgmtInterface INFO[MgmtIntfNetworkEvent.cpp:161] Activity: [Event Info: [EventType: Full] [Login Username: user1@skyromi.onmicrosoft.com (repository: user1@skyromi.onmicrosoft.com)] [Data Object: ] [User Action: logout] [User Action Status: unknown] [User Action Status Reason: ] [Time: Thu, 08 Jun 2017 14:15:47 GMT] [Service type: Box] [File Size: 0] [IsManagedEndpoint: false] [Asset Id: 1] [Event Id: 43947602304019] [Session Id: 3450197739] [Gateway Action Status: monitor] [Client IP: 192.168.3.216] [Server IP: 107.152.26.199] [Client Location: 03 0/0 unknown] [Server Location: US 37.3622/-122.14] [Browser Id: 2R5X2kHSBC57ccb978] [Device Id: c48eec5fe0ddb37a8584cff2fe34acbbc2651fdf0906ed1029c8e4e696193f7c] [OS: Windows] [OS version: 10] [device type: PC] [device version: ] [Client Type: desktop] [Customer Id: 1] [DataTypes: ] [icapConnectors: ] [TOR Networks: ] [Anonymous Proxies: ] [Malicious IPs: ] [IP Chain: 192.168.3.216] [Mapping Ids: Box-1,Box-10] [Is DLP excceeds: False] [Useragent: Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko] [source: proxy] [URI: https://skyromi1.app.box.com/logout] ]"));
    }

    public boolean validateEmails(String data) {
        return data.matches("^(?:(?:[a-zA-Z-0-9]+(?:@gmail.com|@yandex.ru)),?)+$");
    }

    public boolean isLessThan100(String data) {
        return data.matches("^(?:Amount: )?([0-9]{1,2}|100)(\\.[0-9]{1,2})?$");
    }

    public boolean isBiggerThan1450(String data) {
        return data.matches("^(?:Amount: )?(?:(?:14[5-9]\\d)|(?:1[5-9]\\d\\d)|(?:[2-9]\\d\\d\\d)|(?:\\d\\d\\d\\d\\d+))(?:\\.[0-9]{1,2})?$");
    }

    public static String parseActivity(String input) {
        String[] fieldNames = {"Login Username", "Data Object", "Records", "User Action", "User Action Status", "Labels", "Service type", "Mapping Ids", "URI"};

        StringBuilder cleanLog = new StringBuilder();
        cleanLog.append(input.substring(0,input.indexOf("skyfence")));
        cleanLog.append(" Activity: ");

        for (String field:fieldNames) {
            Pattern pattern = Pattern.compile("\\["+field+".+?\\]");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                cleanLog.append(matcher.group());
            }
        }

        return cleanLog.toString();
    }
}
