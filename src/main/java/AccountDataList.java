import exception.AccountAlreadyExistsException;
import exception.DataNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class AccountDataList {

    private final ArrayList<AccountData> data;
    private final ArrayList<AccountData> sortByAccount;
    private final ArrayList<AccountData> sortByName;
    private final ArrayList<AccountData> sortByValue;

    public AccountDataList() {
        this.data = new ArrayList<>();
        sortByAccount = new ArrayList<>();
        sortByName = new ArrayList<>();
        sortByValue = new ArrayList<>();
    }

    public void addData(AccountData newData) throws AccountAlreadyExistsException {
        if (data.size() > 0)
            if (hasAccount(newData.getAccount())) {
            throw new AccountAlreadyExistsException("Аккаунт с ID " + newData.getAccount() +
                    " уже зарегистрирован.");
        }

        data.add(newData);
        sortByName.add(newData);
        sortByAccount.add(newData);
        sortByValue.add(newData);
        sort();
    }

    public void deleteData(Long account) throws DataNotFoundException {
        AccountData deleteData = searchByAccount(account);
        data.remove(deleteData);
        sortByName.remove(deleteData);
        sortByAccount.remove(deleteData);
        sortByValue.remove(deleteData);
    }

    public void changeData(AccountData newData)
            throws AccountAlreadyExistsException, DataNotFoundException {
        deleteData(newData.getAccount());
        addData(newData);
    }

    public AccountData searchByAccount(Long account) throws DataNotFoundException {
        if (data.size() == 0) throw new DataNotFoundException("Аккаунт с ID " + account +
                " не зарегистрирован.");
        Long left = (long) -1, right = (long) data.size();
        Long m = 0L;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (data.get(Math.toIntExact(m)).getAccount() > account) {
                right = m;
            }
            else left = m;
        }
        if (Objects.equals(account, data.get(Math.toIntExact(m)).getAccount())) {
            return data.get(Math.toIntExact(m));
        }
        else {
            throw new DataNotFoundException("Аккаунт с ID " + account +
                    " не зарегистрирован.");
        }
    }

    public ArrayList<AccountData> searchByName(String name) throws DataNotFoundException {
        if (data.size() == 0) throw new DataNotFoundException("Аккаунт с именем " + name +
                " не зарегистрирован.");
        Long left = (long) -1, right = (long) data.size();
        Long m = 0L;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (data.get(Math.toIntExact(m)).getName().compareTo(name) >= 0) {
                right = m;
            }
            else left = m;
        }
        ArrayList<AccountData> result = new ArrayList<>();
        while (m < data.size()) {
            if (data.get(Math.toIntExact(m)).getName().compareTo(name) == 0) {
                result.add(data.get(Math.toIntExact(m)));
                m++;
            }
            else break;
        }
        if (result.size() < 1) throw new DataNotFoundException("Аккаунт с именем " + name +
                " не зарегистрирован.");
        return result;
    }

    public ArrayList<AccountData> searchByValue(BigDecimal value) throws DataNotFoundException {
        if (data.size() == 0) throw new DataNotFoundException("Аккаунт со значением " + value +
                " не зарегистрирован.");
        Long left = (long) -1, right = (long) data.size();
        Long m = 0L;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (data.get(Math.toIntExact(m)).getValue().compareTo(value) >= 0) {
                right = m;
            }
            else left = m;
        }
        ArrayList<AccountData> result = new ArrayList<>();
        while (m < data.size()) {
            if (data.get(Math.toIntExact(m)).getValue().compareTo(value) == 0) {
                result.add(data.get(Math.toIntExact(m)));
                m++;
            }
            else break;
        }
        if (result.size() < 1) throw new DataNotFoundException("Аккаунт со значением " + value +
                " не зарегистрирован.");
        return result;
    }

    private void sort() {
        sortByAccount.sort(Comparator.comparing(AccountData::getAccount));
        sortByName.sort(Comparator.comparing(AccountData::getName));
        sortByValue.sort(Comparator.comparing(AccountData::getValue));
    }

    private boolean hasAccount(Long account) {
        Long left = (long) -1, right = (long) data.size();
        Long m = 0L;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (data.get(Math.toIntExact(m)).getAccount() > account) {
                right = m;
            }
            else left = m;
        }
        return (Objects.equals(account, data.get(Math.toIntExact(m)).getAccount()));
    }
}
