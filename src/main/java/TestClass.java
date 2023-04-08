import java.math.BigDecimal;

public class TestClass {

    public static void main(String[] args) {
        AccountDataList accountDataList = new AccountDataList();
        try {
            AccountData accountData = new AccountData(1L, "Name", new BigDecimal(123));
            accountDataList.changeData(accountData);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        try {
            accountDataList.deleteData(1L);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        try {
            AccountData accountData = new AccountData(1L, "Name", new BigDecimal(123));
            accountDataList.addData(accountData);
            accountData.setName("NewName");
            accountDataList.changeData(accountData);

            System.out.println(accountDataList.searchByAccount(1L).getAccount());
            System.out.println(accountDataList.searchByName("NewName").get(0).getName());
            System.out.println(accountDataList.searchByValue(new BigDecimal(123)).get(0).getValue());

            accountDataList.deleteData(1L);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        try {
            AccountData accountData = new AccountData(1L, "Name", new BigDecimal(123));
            accountDataList.addData(accountData);
            accountDataList.addData(accountData);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
