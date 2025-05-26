package com.randolflu.atm;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

import static com.randolflu.atm.constants.Constants.MENU;
import static com.randolflu.atm.constants.Constants.WELCOME;


public class ATMApplication {
    private static int startId = 2025100001;
    private static ArrayList<Account> accountList = new ArrayList<>();
    private static Account account;
    private static Scanner scanner = new Scanner(System.in);
    private static Console console = System.console();

    static {
        accountList.add(new Account("1001", "123456", 5000, "张三", "男", "23", "18812341234", "郑州", "412323199802161234", 8000));
        accountList.add(new Account("1002", "123456", 6000, "李四", "男", "25", "18812341234", "郑州", "312323200002161233", 6000));
        accountList.add(new Account("1003", "123456", 7000, "王五", "女", "27", "18812341234", "郑州", "345323200002161126", 5000));
        accountList.add(new Account("1004", "123456", 7000, "王五", "女", "27", "18812341234", "郑州", "345323200002161126", 5000));
        accountList.add(new Account("1005", "123456", 7000, "王五", "女", "27", "18812341234", "郑州", "345323200002161126", 5000));
    }

    public static void main(String[] args) {
        while (true) {
            System.out.print(WELCOME);
            String option = scanner.next();
            switch (option) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    System.out.println("---- 欢迎下次使用 ----");
                    return;
                default:
                    System.out.println("请重新输入");
                    break;
            }
        }
    }

    private static void login() {
        System.out.print("请输入银行卡号: ");
        String id = scanner.next();
        Console console = System.console();
        char[] pwd = console.readPassword("请输入密码: ");
        String pwdStr = new String(pwd);

        Account login = selectAccountById(id);
        if (login != null && login.getPwd().equals(pwdStr)) {
            System.out.println("登录成功");
            account = login;
            /* 显示二级菜单 */
            showMenu();
        } else {
            System.out.println("用户名密码不匹配");
        }
    }

    /* 登录后显示菜单 */
    private static void showMenu() {
        while (true) {
            System.out.printf(MENU,
                    account.getName(),
                    account.getGender().equals("男") ? "先生" : "女士");
            String option = scanner.next();
            switch (option) {
                case "1":
                    showAccountInfo();
                    break;
                case "2":
                    deposit();
                    break;
                case "3":
                    withdraw();
                    break;
                case "4":
                    transfer();
                    break;
                case "5":
                    updatePassword();
                    break;
                case "6":
                    if (unregister()) {
                        System.out.println("注销成功, 正在退出系统...");
                        return;
                    } else {
                        break;
                    }
                case "7":
                    account = null;
                    System.out.println("欢迎下次使用");
                    return;
                default:
                    System.out.println("请重新输入");
                    break;

            }
        }
    }

    /* 用户转账 */
    private static void transfer() {
        /* 转给谁 */
        Account payee;
        while (true) {
            System.out.print("请输入对方账户: ");
            String payeeId = scanner.next();
            payee = selectAccountById(payeeId);
            if (payee == null) {
                System.out.println("对方账户不存在, 转账失败");
                return;
            } else {
                break;
            }
        }

        /* 转多少 */
        System.out.print("请输入汇款金额: ");
        double amount = scanner.nextDouble();
        if (account.getBalance() < amount) {
            System.out.printf("转账失败, 余额不足: %s", account.getBalance());
        } else {
            account.setBalance(account.getBalance() - amount);
            payee.setBalance(payee.getBalance() + amount);
            System.out.printf("转账成功, 当前余额: %.2f", account.getBalance());
        }
    }

    /* 根据 ID 查找账户 */
    private static Account selectAccountById(String accountId) {
        Account byId;
        for (int i = 0; i < accountList.size(); i++) {
            byId = accountList.get(i);
            if (byId.getId().equals(accountId)) {
                return byId;
            }
        }
        return null;
    }

    /* 更新密码 */
    private static void updatePassword() {
        /* 输入旧密码 */
        Console console = System.console();

        while (true) {
            char[] oldPwd = console.readPassword("请输入旧密码: ");
            char[] newPwd = console.readPassword("请输入新密码: ");
            char[] reNewPwd = console.readPassword("请再次输入新密码: ");
            String old = new String(oldPwd);
            String new_ = new String(newPwd);
            String reNew_ = new String(reNewPwd);

            if (!old.equals(account.getPwd())) {
                System.out.println("旧密码输入错误, 请重试");
            } else if (old.equals(new_)) {
                System.out.println("新密码不能和旧密码相同");
            } else if (!new_.equals(reNew_)) {
                System.out.println("新密码两次输入不匹配");
            } else {
                account.setPwd(reNew_);
                System.out.println("密码修改成功");
                console.flush();
                break;
            }
        }
    }

    /* 取款 */
    private static void withdraw() {
        System.out.print("请输入您的取款金额(面值为100): ");
        double qv = scanner.nextDouble();
        while (qv % 100 != 0) {
            System.out.print("请输入100的倍数");
            qv = scanner.nextDouble();
        }

        while (account.getBalance() < qv) {
            System.out.print("余额不足, 请重新输入取款金额");
            qv = scanner.nextDouble();
        }
        account.setBalance(account.getBalance() - qv);
        System.out.print("取款成功, 当前余额: " + account.getBalance());
    }

    /* 存款 */
    private static void deposit() {
        System.out.print("请将整理好的钞票放入放钞口: ");
        double money = scanner.nextDouble();
        System.out.println("正在验钞...");
        while (money % 100 != 0) {
            if (money % 100 != 0) {
                System.out.println("请放入面值为100元的钞票");
                money = scanner.nextDouble();
            }
        }
        account.setBalance(account.getBalance() + money);
        System.out.printf("存款成功, 当前余额: %s", account.getBalance());
    }

    /* 账户注销, 删除账户 */
    private static boolean unregister() {
        boolean status = false; // false: 注销失败
        /* 账户余额不为空不能注销账户 */
        System.out.print("即将注销您的银行账户, 是否继续( Y: 继续, N: 取消 )? ");
        String confirm = scanner.next();
        if (confirm.equalsIgnoreCase("y")) {
            if (account.getBalance() != 0) {
                System.out.printf("注销失败, 当前账户余额: %s", account.getBalance());
            } else {
                accountList.remove(account);
                status = true; // 注销成功
            }
        } else {
            System.out.println("用户取消操作");
        }

        return status;
    }

    private static void showAccountInfo() {
        System.out.println(formatAccountAsStr(account));
    }

    /* 账户注册 */
    private static void register() {
        Account register = new Account();

        register.setId(String.valueOf(startId++));

        System.out.print("请输入姓名: ");
        String name = scanner.next();
        register.setName(name);

        System.out.print("请输入性别: ");
        String gender = scanner.next();
        register.setGender(gender);

        System.out.print("请输入年龄: ");
        String age = scanner.next();
        register.setAge(age);

        System.out.print("请输入电话: ");
        String phone = scanner.next();
        register.setPhone(phone);

        System.out.print("请输入地址: ");
        String address = scanner.next();
        register.setAddr(address);

        System.out.print("请输入身份证号码: ");
        String identity = scanner.next();
        register.setIdentity(identity);

        char[] pwd = console.readPassword("请输入密码: ");
        String pwdStr = new String(pwd);
        char[] repwd = console.readPassword("请再次输入密码: ");
        String repwdStr = new String(repwd);
        while (!pwdStr.equals(repwdStr)) {
            repwd = console.readPassword("密码输入不一致,请重新输入密码: ");
        }
        register.setPwd(new String(repwd));
        console.flush();

        accountList.add(register);
        System.out.println("注册成功, 您的账户信息: " + formatAccountAsStr(register));

    }

    /* 格式化账户信息 */
    private static String formatAccountAsStr(Account account) {
        return String.format(
                """
                        银行卡号: %s
                        姓名: %s
                        性别: %s
                        年龄: %s
                        电话: %s
                        地址: %s
                        身份证号码: %s
                        限额: %.2f 元
                        余额: %.2f 元
                        密码: %s
                        """,
                account.getId(),
                account.getName(),
                account.getGender(),
                account.getAge(),
                account.getPhone(),
                account.getAddr(),
                account.getIdentity(),
                account.getLimit(),
                account.getBalance(),
                account.getPwd()
        );
    }
}
