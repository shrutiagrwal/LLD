package practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    private String userId;
    private String name;
    private BalanceSheet userBalanceSheet;

    User(String id, String name) {
        this.userId = id;
        this.name = name;
        this.userBalanceSheet = new BalanceSheet();
    }

    public String getId() {
        return this.userId;
    }

    public BalanceSheet getUserBalanceSheet() {
        return userBalanceSheet;
    }

}

class UserController {
    List<User> userList = new ArrayList<>();

    public void createUser(String name, String id) {
        userList.add(new User(id, name));
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getUserById(String id) {
        for (User user : userList) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }
}

class Group {
    int gid;
    String gName;
    List<Expense> expenseList = new ArrayList<>();
    List<User> userList;
    ExpenseController expenseController;

    Group(int id, String name) {
        this.gid = id;
        this.gName = name;
        this.userList = new ArrayList<>();
        this.expenseList = new ArrayList<>();
        expenseController = new ExpenseController();
    }

    public void addMemberInGroup(User user) {
        userList.add(user);
    }

    public int getGid() {
        return this.gid;
    }

    public void createExpense(int eid, String name, double amount, List<Split> splitList, SplitType splitType, User paidBy) {
        Expense expense = expenseController.createExpense(eid, name, splitType, splitList, amount, paidBy);
        this.expenseList.add(expense);
    }
}

class GroupController {
    List<Group> groupList = new ArrayList<>();

    public void createGroup(String name, int id) {
        groupList.add(new Group(id, name));
    }

    public Group getGroup(int id) {
        for (Group group : groupList) {
            if (group.getGid() == id)
                return group;
        }
        return null;
    }
}

enum SplitType {
    EQUAL, UNEQUAL, PERCENTAGE;
}

//share of each user
class Split {
    User user;
    double amount;

    public User getUser() {
        return this.user;
    }

    Split(User user, double amount) {
        this.user = user;
        this.amount = amount;
    }
}

interface ExpenseValidator {
    boolean validate(double amount, List<Split> splitDetails);
}

class EqualSplit implements ExpenseValidator {
    @Override
    public boolean validate(double amount, List<Split> splitDetails) {
        double individualAmount = amount / splitDetails.size();
        for (Split split : splitDetails) {
            if (split.amount != individualAmount)
                return false;

        }
        return true;
    }
}

class UnequalSplit implements ExpenseValidator {
    @Override
    public boolean validate(double amount, List<Split> splitDetails) {
        double totalAmount = 0;
        for (Split split : splitDetails) {
            totalAmount += split.amount;
        }
        return totalAmount == amount;
    }
}

class PercentageSplit implements ExpenseValidator {
    @Override
    public boolean validate(double amount, List<Split> splitDetails) {
        double totalAmount = 0;
        for (Split split : splitDetails) {
            totalAmount += split.amount;
        }
        return totalAmount == amount;
    }
}

class SplitFactory {
    ExpenseValidator getSplitObject(SplitType splitType) {
        switch (splitType) {
            case EQUAL:
                return new EqualSplit();
            case UNEQUAL:
                return new UnequalSplit();
            case PERCENTAGE:
                return new PercentageSplit();
            default:
                return null;
        }

    }
}

class Expense {
    int eId;
    String name;
    SplitType splitType;
    List<Split> splitDetails;
    User paidBy;
    double amount;

    Expense(int id, String name, SplitType splitType, List<Split> splitDetails, double amount, User paidBy) {
        this.eId = id;
        this.name = name;
        this.splitType = splitType;
        this.splitDetails = splitDetails;
        this.amount = amount;
        this.paidBy = paidBy;
    }
}

class ExpenseController {
    SplitFactory splitFactory;
    BalanceSheetController balanceSheetController;

    ExpenseController() {
        splitFactory = new SplitFactory();
        balanceSheetController = new BalanceSheetController();

    }

    public Expense createExpense(int id, String name, SplitType splitType, List<Split> splitDetails, double amount, User paidBy) {
        ExpenseValidator validator = splitFactory.getSplitObject(splitType);
        if (validator.validate(amount, splitDetails)) {
            Expense expense = new Expense(id, name, splitType, splitDetails, amount, paidBy);

            balanceSheetController.updateBalanceSheet(paidBy, splitDetails, amount);
            return expense;
        } else
            return null;
    }
}

class Balance {
    double getBack;
    double owes;

    public void setOwes(double owes) {
        this.owes = owes;
    }

    public double getOwes() {
        return owes;
    }

    public double getGetBack() {
        return getBack;
    }

    public void setGetBack(double getBack) {
        this.getBack = getBack;
    }
}

class BalanceSheet {
    private Map<String, Balance> friendsBalance;
    private double totalGetBack;
    private double totalOwes;

    BalanceSheet() {
        friendsBalance = new HashMap<>();
        totalGetBack = 0;
        totalOwes = 0;
    }

    public double getTotalGetBack() {
        return totalGetBack;
    }

    public Map<String, Balance> getFriendsBalance() {
        return this.friendsBalance;
    }

    public void setTotalGetBack(double amount) {
        this.totalGetBack = amount;
    }

    public void setTotalOwes(double totalOwes) {
        this.totalOwes = totalOwes;
    }

    public double getTotalOwes() {
        return totalOwes;
    }
}

class BalanceSheetController {
    public void updateBalanceSheet(User paidByUser, List<Split> splitList, double totalAmount) {
        for (Split split : splitList) {
            User oweUser = split.getUser();
            BalanceSheet oweUserBalanceSheet = oweUser.getUserBalanceSheet();
            if (!oweUser.getId().equals(paidByUser.getId())) {
                paidByUser.getUserBalanceSheet().setTotalGetBack(paidByUser.getUserBalanceSheet().getTotalGetBack() + split.amount);
                // updating for paid by user in its friend balance sheet
                Balance userOweBalance;
                if (paidByUser.getUserBalanceSheet().getFriendsBalance().containsKey(oweUser.getId())) {
                    userOweBalance = paidByUser.getUserBalanceSheet().getFriendsBalance().get(oweUser.getId());
                } else {
                    userOweBalance = new Balance();
                    userOweBalance.setGetBack(0);
                    userOweBalance.setOwes(0);
                }
                userOweBalance.setGetBack(userOweBalance.getGetBack() + split.amount);
                paidByUser.getUserBalanceSheet().getFriendsBalance().put(oweUser.getId(), userOweBalance);

                // updating for owe user
                oweUserBalanceSheet.setTotalOwes(oweUserBalanceSheet.getTotalOwes() + split.amount);
                Balance paidBalance = new Balance();
                if (oweUserBalanceSheet.getFriendsBalance().containsKey(paidByUser.getId())) {
                    paidBalance = oweUserBalanceSheet.getFriendsBalance().get(paidByUser.getId());
                } else {
                    paidBalance.setOwes(0);
                }
                paidBalance.setOwes(userOweBalance.getOwes() + split.amount);
                oweUserBalanceSheet.getFriendsBalance().put(paidByUser.getId(), paidBalance);
            }
        }
    }

    public void displayBalanceSheet(User user) {
        System.out.println("user id----  " + user.getId());
        System.out.println("user total owes --  " + user.getUserBalanceSheet().getTotalOwes());
        System.out.println("user will get back --  " + user.getUserBalanceSheet().getTotalGetBack());
        System.out.println("practice.Balance sheet of user----");

        for (Map.Entry<String, Balance> entry : user.getUserBalanceSheet().getFriendsBalance().entrySet()) {
            Balance balance = entry.getValue();
            System.out.println("from user --" + entry.getKey() + " you get back-" + balance.getGetBack() + " you owe -" + balance.getOwes());
        }
    }
}

class Splitwise {

    UserController userController;
    GroupController groupController;
    BalanceSheetController balanceSheetController;

    Splitwise() {
        this.userController = new UserController();
        this.groupController = new GroupController();
        this.balanceSheetController = new BalanceSheetController();
    }

    public void demo() {
        this.groupController.createGroup("trip", 1);
        this.userController.createUser("Shruti", "Shruti");
        this.userController.createUser("abc", "abc");
        Group group = this.groupController.getGroup(1);


        group.addMemberInGroup(userController.getUserList().get(0));
        group.addMemberInGroup(userController.getUserList().get(1));


        List<Split> splitList = new ArrayList<>();
        Split split1 = new Split(userController.getUserById("Shruti"), 400);
        Split split2 = new Split(userController.getUserById("abc"), 400);

        splitList.add(split1);
        splitList.add(split2);
        List<Split> splitList2 = new ArrayList<>();
        group.createExpense(123, "food", 800, splitList, SplitType.EQUAL, userController.getUserById("Shruti"));

        Split split11 = new Split(userController.getUserById("Shruti"), 300);
        Split split12 = new Split(userController.getUserById("abc"), 400);

        splitList2.add(split11);
        splitList2.add(split12);
        group.createExpense(122123, "food", 700, splitList2, SplitType.UNEQUAL, userController.getUserById("abc"));
        balanceSheetController.displayBalanceSheet(userController.getUserById("Shruti"));
        balanceSheetController.displayBalanceSheet(userController.getUserById("abc"));
    }
}

public class SplitwiseDriver {
    public static void main(String[] args) {
        Splitwise splitwise = new Splitwise();
        splitwise.demo();

    }
}
