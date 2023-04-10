import exception.AccountAlreadyExistsException;
import exception.DataNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class AccountDataList {

    private final ArrayList<AccountData> sortByAccount;
    private final ArrayList<AccountData> sortByName;
    private final ArrayList<AccountData> sortByValue;

    public AccountDataList() {
        sortByAccount = new ArrayList<>();
        sortByName = new ArrayList<>();
        sortByValue = new ArrayList<>();
    }

    public void addData(AccountData newData) throws AccountAlreadyExistsException {
        if (sortByAccount.size() > 0)
            if (hasAccount(newData.getAccount())) {
            throw new AccountAlreadyExistsException("Аккаунт с ID " + newData.getAccount() +
                    " уже зарегистрирован.");
        }
        sortByName.add(newData);
        sortByAccount.add(newData);
        sortByValue.add(newData);
        sort();
    }

    public void deleteData(Long account) throws DataNotFoundException {
        AccountData deleteData = searchByAccount(account);
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
        if (sortByAccount.size() == 0) throw new DataNotFoundException("Аккаунт с ID " + account +
                " не зарегистрирован.");
        Long left = (long) -1, right = (long) sortByAccount.size();
        Long m = 0L;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (sortByAccount.get(Math.toIntExact(m)).getAccount() > account) {
                right = m;
            }
            else left = m;
        }
        if (Objects.equals(account, sortByAccount.get(Math.toIntExact(m)).getAccount())) {
            return sortByAccount.get(Math.toIntExact(m));
        }
        else {
            throw new DataNotFoundException("Аккаунт с ID " + account +
                    " не зарегистрирован.");
        }
    }

    public ArrayList<AccountData> searchByName(String name) throws DataNotFoundException {
        if (sortByAccount.size() == 0) throw new DataNotFoundException("Аккаунт с именем " + name +
                " не зарегистрирован.");
        Long left = (long) -1, right = (long) sortByAccount.size();
        Long m = 0L;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (sortByAccount.get(Math.toIntExact(m)).getName().compareTo(name) >= 0) {
                right = m;
            }
            else left = m;
        }
        ArrayList<AccountData> result = new ArrayList<>();
        while (m < sortByAccount.size()) {
            if (sortByAccount.get(Math.toIntExact(m)).getName().compareTo(name) == 0) {
                result.add(sortByAccount.get(Math.toIntExact(m)));
                m++;
            }
            else break;
        }
        if (result.size() < 1) throw new DataNotFoundException("Аккаунт с именем " + name +
                " не зарегистрирован.");
        return result;
    }

    public ArrayList<AccountData> searchByValue(double value) throws DataNotFoundException {
        if (sortByAccount.size() == 0) throw new DataNotFoundException("Аккаунт со значением " + value +
                " не зарегистрирован.");
        Long left = (long) -1, right = (long) sortByAccount.size();
        Long m = 0L;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (sortByAccount.get(Math.toIntExact(m)).getValue() >= value) {
                right = m;
            }
            else left = m;
        }
        ArrayList<AccountData> result = new ArrayList<>();
        while (m < sortByAccount.size()) {
            if (sortByAccount.get(Math.toIntExact(m)).getValue() == value) {
                result.add(sortByAccount.get(Math.toIntExact(m)));
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
        Long left = (long) -1, right = (long) sortByAccount.size();
        Long m = 0L;
        while (right - left > 1) {
            m = (left + right) / 2;
            if (sortByAccount.get(Math.toIntExact(m)).getAccount() > account) {
                right = m;
            }
            else left = m;
        }
        return (Objects.equals(account, sortByAccount.get(Math.toIntExact(m)).getAccount()));
    }
}
