import java.util.Scanner;

enum TransactionType {
    WITHDRAWL, CHECK_BALANCE
}

interface ATMState {
    void insertCard(ATM atm);

    void insertPin(ATM atm);

    boolean validatePin(ATM atm, int pin, Card card);

    void checkUserBalance(ATM atm, Account account);

    void withdrawMoneyProcessor(ATM atm, int amount, Account account);

    void exit(ATM atm);

    void stateSelection(ATM atm, TransactionType transactionType);
}

class Card {
    private final int pin;
    private final Account account;

    Card(int pin, Account account) {
        this.pin = pin;
        this.account = account;
    }

    public int getPin() {
        return this.pin;
    }

    public Account getAccount() {
        return this.account;
    }

}

class Account {
    private int balance;
    private String ACno;

    Account(String acNo, int balance) {
        this.balance = balance;
        this.ACno = acNo;
    }

    public void setACno(String number) {
        this.ACno = number;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAcno() {
        return this.ACno;
    }
}

class User {
    Card card;
    Account account;
    String name;

    User(String name, Card card, Account account) {
        this.name = name;
        this.card = card;
        this.account = account;
    }

    public Card getCard() {
        return this.card;
    }

    public Account getAccount() {
        return this.account;
    }
}

class ATM {
    private static final ATM atmObject = new ATM();
    ATMState state;
    private int AtmBalance;
    private int twoThousandNotes;
    private int fiveHundredNotes;
    private int oneHundredNotes;

    public static ATM getATMObject() {
        atmObject.setState(new IdleState());
        atmObject.setOneHundredNotes(5);
        atmObject.setFiveHundredNotes(2);
        atmObject.setTwoThousandNotes(2);
        atmObject.setAtmBalance(5500);
        return atmObject;
    }

    public int getAtmBalance() {
        return this.AtmBalance;
    }

    public void setAtmBalance(int atmBalance) {
        AtmBalance = atmBalance;
    }

    public int getTwoThousandNotes() {
        return this.twoThousandNotes;
    }

    public void setTwoThousandNotes(int twoThousandNotes) {
        this.twoThousandNotes = twoThousandNotes;
    }

    public int getFiveHundredNotes() {
        return this.fiveHundredNotes;
    }

    public void setFiveHundredNotes(int fiveHundredNotes) {
        this.fiveHundredNotes = fiveHundredNotes;
    }

    public int getOneHundredNotes() {
        return this.oneHundredNotes;
    }

    public void setOneHundredNotes(int oneHundredNotes) {
        this.oneHundredNotes = oneHundredNotes;
    }

    public ATMState getState() {
        return this.state;
    }

    public void setState(ATMState atmState) {
        this.state = atmState;
    }
}

class IdleState implements ATMState {
    @Override
    public void insertCard(ATM atm) {
        atm.setState(new HasCardState());
    }

    @Override
    public void insertPin(ATM atm) {
    }

    @Override
    public boolean validatePin(ATM atm, int pin, Card card) {
        return true;
    }

    @Override
    public void checkUserBalance(ATM atm, Account account) {
    }

    @Override
    public void withdrawMoneyProcessor(ATM atm, int amount, Account account) {
    }

    @Override
    public void exit(ATM atm) {
    }

    @Override
    public void stateSelection(ATM atm, TransactionType transactionType) {
    }

}

class HasCardState implements ATMState {
    @Override
    public void insertCard(ATM atm) {
    }

    @Override
    public void insertPin(ATM atm) {
        System.out.println("insert pin");
    }

    @Override
    public boolean validatePin(ATM atm, int pin, Card card) {
        int userPin = card.getPin();
        if (userPin == pin) {
            System.out.println("entered pin is valid");
            atm.setState(new ProcessSelectionState());
            return true;
        } else {
            System.out.println("invalid pin");
            exit(atm);
            atm.setState(new IdleState());
            return false;
        }
    }

    @Override
    public void checkUserBalance(ATM atm, Account account) {
    }

    @Override
    public void withdrawMoneyProcessor(ATM atm, int amount, Account account) {
    }

    @Override
    public void exit(ATM atm) {
        atm.setState(new IdleState());
    }

    @Override
    public void stateSelection(ATM atm, TransactionType transactionType) {
    }

}

class ProcessSelectionState implements ATMState {
    @Override
    public void insertCard(ATM atm) {
    }

    @Override
    public void insertPin(ATM atm) {
    }

    @Override
    public boolean validatePin(ATM atm, int pin, Card card) {
        return true;
    }

    @Override
    public void checkUserBalance(ATM atm, Account account) {
    }

    @Override
    public void withdrawMoneyProcessor(ATM atm, int amount, Account account) {
    }

    @Override
    public void exit(ATM atm) {
        atm.setState(new IdleState());
    }

    @Override
    public void stateSelection(ATM atm, TransactionType transactionType) {
        switch (transactionType) {
            case CHECK_BALANCE:
                atm.setState(new CheckBalanceState());
                break;
            case WITHDRAWL:
                atm.setState(new WithdrawlMoneyState());
                break;

        }
    }

}

class CheckBalanceState implements ATMState {
    @Override
    public void insertCard(ATM atm) {
    }

    @Override
    public void insertPin(ATM atm) {
    }

    @Override
    public boolean validatePin(ATM atm, int pin, Card card) {
        return true;
    }

    @Override
    public void checkUserBalance(ATM atm, Account account) {
        System.out.println("user account balance --" + account.getBalance());
        exit(atm);
    }

    @Override
    public void withdrawMoneyProcessor(ATM atm, int amount, Account account) {
    }

    @Override
    public void exit(ATM atm) {
        atm.setState(new IdleState());
    }

    @Override
    public void stateSelection(ATM atm, TransactionType transactionType) {
    }

}

class WithdrawlMoneyState implements ATMState {
    @Override
    public void insertCard(ATM atm) {
    }

    @Override
    public void insertPin(ATM atm) {
    }

    @Override
    public boolean validatePin(ATM atm, int pin, Card card) {
        return true;
    }

    @Override
    public void checkUserBalance(ATM atm, Account account) {
    }

    @Override
    public void withdrawMoneyProcessor(ATM atm, int amount, Account account) {
        if (atm.getAtmBalance() < amount) {
            System.out.println("atm has insufficient funds");
            exit(atm);
        } else if (account.getBalance() < amount) {
            System.out.println("your account has insufficient funds");
            exit(atm);
        } else {
            account.setBalance(account.getBalance() - amount);
            atm.setAtmBalance(atm.getAtmBalance() - amount);
            CashWithdrawlProcessor cashWithdrawlProcessor = new twoThousandProcessor(new fiveHundredProcessor(new OneHundredProcessor(null)));
            cashWithdrawlProcessor.withdraw(atm, amount);
            exit(atm);
        }
    }

    @Override
    public void exit(ATM atm) {
        atm.setState(new IdleState());
    }

    @Override
    public void stateSelection(ATM atm, TransactionType transactionType) {
    }

}

abstract class CashWithdrawlProcessor {
    CashWithdrawlProcessor nextProcessor;

    CashWithdrawlProcessor(CashWithdrawlProcessor processor) {
        this.nextProcessor = processor;
    }

    public void withdraw(ATM atm, int remainingAmount) {
        if (nextProcessor != null)
            nextProcessor.withdraw(atm, remainingAmount);
    }
}

class twoThousandProcessor extends CashWithdrawlProcessor {

    twoThousandProcessor(CashWithdrawlProcessor processor) {
        super(processor);
    }

    public void withdraw(ATM atm, int remainingAmount) {
        if (remainingAmount == 0)
            return;
        int notes = remainingAmount / 2000;
        if (notes > 0) {
            atm.setTwoThousandNotes(atm.getTwoThousandNotes() - notes);
            System.out.println("dispense " + notes + " note of 2000 rs");
            remainingAmount -= (notes * 2000);
        }
        if (nextProcessor != null)
            super.withdraw(atm, remainingAmount);
    }
}

class fiveHundredProcessor extends CashWithdrawlProcessor {

    fiveHundredProcessor(CashWithdrawlProcessor processor) {
        super(processor);
    }

    public void withdraw(ATM atm, int remainingAmount) {
        if (remainingAmount == 0)
            return;
        int notes = remainingAmount / 500;
        if (notes > 0) {
            atm.setFiveHundredNotes(atm.getFiveHundredNotes() - notes);
            System.out.println("dispense " + notes + " note of 500 rs");
            remainingAmount -= (notes * 500);
        }
        if (nextProcessor != null)
            super.withdraw(atm, remainingAmount);

    }
}

class OneHundredProcessor extends CashWithdrawlProcessor {

    OneHundredProcessor(CashWithdrawlProcessor processor) {
        super(processor);
    }

    public void withdraw(ATM atm, int remainingAmount) {
        if (remainingAmount == 0)
            return;
        int notes = remainingAmount / 100;
        if (notes > 0) {
            atm.setOneHundredNotes(atm.getOneHundredNotes() - notes);
            System.out.println("dispense " + notes + " note of 100 rs");
            remainingAmount -= (notes * 100);
        }
        if (nextProcessor != null)
            super.withdraw(atm, remainingAmount);

    }
}


public class ATMRoom {
    ATM atm;
    User user;

    public static void main(String[] args) {
        ATMRoom room = new ATMRoom();
        room.initialiseATMRoom();
        ATMState state = room.atm.getState();
        state.insertCard(room.atm);

        state = room.atm.getState();
        state.insertPin(room.atm);
        Scanner sc = new Scanner(System.in);
        int pin = sc.nextInt();
        state.validatePin(room.atm, pin, room.user.getCard());

        state = room.atm.getState();
//        state.stateSelection(room.atm,TransactionType.CHECK_BALANCE);
//        room.atm.getState().checkUserBalance(room.atm,room.user.getAccount());

        state.stateSelection(room.atm, TransactionType.WITHDRAWL);
        room.atm.getState().withdrawMoneyProcessor(room.atm, 4600, room.user.getAccount());
    }

    private void initialiseATMRoom() {
        atm = ATM.getATMObject();
        user = this.initialiseUser(40000);
    }

    private User initialiseUser(int balance) {
        Account account = new Account("ac1", balance);
        Card card = new Card(1234, account);
        return new User("Shruti", card, account);

    }
}