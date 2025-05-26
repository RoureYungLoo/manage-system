package com.randolflu.atm;

import java.util.ArrayList;

public class CrudUtil {

    private static ArrayList<Account> accounts;

    public CrudUtil() {
    }

    public static void setAccounts(ArrayList<Account> accounts) {
        CrudUtil.accounts = accounts;
    }

    /*增*/
    public static Account save(Account account) {
        Account byId = selectById(account.getId());
        if (null != byId) {
            return null;
        }
        accounts.add(account);
        return account;
    }

    /*删*/
    public static int deleteById(String id) {
        Account byId = selectById(id);
        int deleted = 0;
        if (null != byId) {
            accounts.remove(byId);
            deleted = 1;
        }
        return deleted;
    }

    /*改*/
    public static int updateById(Account account) {
        int updated = 0;
        Account byId = selectById(account.getId());
        if (null != byId) {
            int index = accounts.indexOf(byId);
            accounts.set(index, account);
            updated++;
        }
        return updated;
    }

    /*单查*/
    public static Account selectById(String id) {
        Account tmp = null;
        for (int i = 0; i < accounts.size(); i++) {
            tmp = accounts.get(i);
            if (tmp.getId().equals(id)) {
                break;
            }
        }
        return tmp;
    }

    /*全查*/
    public static ArrayList<Account> selectAll() {
        return accounts;
    }
}
