package practice;

import java.util.ArrayList;
import java.util.List;

enum ItemType {
    COKE, PEPSI, CHOCOLATE, JUICE
}

class Item {
    int price;
    ItemType itemType;

    Item(int price, ItemType itemType) {

        this.price = price;
        this.itemType = itemType;
    }

    public int getPrice() {
        return price;
    }

    public ItemType getItemType() {
        return itemType;
    }
}

class ItemShelf {
    int code;
    Item item;
    boolean soldOut;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

}

class Inventory {
    ItemShelf[] inventory = null;

    public void setInventory(ItemShelf[] inventory) {
        this.inventory = inventory;
    }

    public void initialEmptyInventory() {
        int startCode = 101;
        for (int i = 0; i < inventory.length; i++) {
            ItemShelf space = new ItemShelf();
            space.setCode(startCode);
            space.setSoldOut(true);
            inventory[i] = space;
            startCode++;
        }
    }

    public void addItem(Item item, int codeNumber) throws Exception {

        for (ItemShelf itemShelf : inventory) {
            if (itemShelf.code == codeNumber) {
                if (itemShelf.isSoldOut()) {
                    itemShelf.item = item;
                    itemShelf.setSoldOut(false);
                } else {
                    throw new Exception("already item is present, you can not add item here");
                }
            }
        }
    }

    public Item getItem(int codeNumber) throws Exception {
        for (ItemShelf itemShelf : inventory) {
            if (itemShelf.code == codeNumber) {
                if (itemShelf.isSoldOut())
                    throw new Exception("item already sold out");
                else
                    return itemShelf.item;
            }
        }
        throw new Exception("Invalid Code");
    }


}

interface State {
    public void clickOnInsertCoinButton(VendingMachine vendingMachine);

    public void insertCoin(VendingMachine vendingMachine, Coin coin);

    public void clickOnSelectProductButton(VendingMachine vendingMachine);

    void selectProduct(VendingMachine vendingMachine, int codeNumber) throws Exception;

    public Item dispenseProduct(VendingMachine vendingMachine, int codeNumber) throws Exception;

    public List<Coin> refundFullMoney(VendingMachine vendingMachine);

    public int refundRemainingMoney(int extraMoney);

}

class IdleState implements State {

    public IdleState() {
        System.out.println("Currently Vending machine is in IdleState");
    }

    public IdleState(VendingMachine vendingMachine) {
        System.out.println("Machine is in idle state");
        vendingMachine.setCoinList(new ArrayList<>());
    }

    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) {
        vendingMachine.setState(new HasMoneyState());
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) {

    }

    @Override
    public void clickOnSelectProductButton(VendingMachine vendingMachine) {

    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, int code) {

    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine, int codeNumber) {

        return null;
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) {
        return null;
    }

    @Override
    public int refundRemainingMoney(int extraMoney) {

        return extraMoney;
    }
}

class HasMoneyState implements State {

    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) {
        return;
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) {
        System.out.println("Accepted the coin");
        vendingMachine.getCoinList().add(coin);
    }

    @Override
    public void clickOnSelectProductButton(VendingMachine vendingMachine) {
        vendingMachine.setState(new ProductSelectionState());
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, int code) {
        return;
    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine, int codeNumber) {
        return null;
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) {
        System.out.println("returning full amount");
        vendingMachine.setState(new IdleState(vendingMachine));
        return vendingMachine.getCoinList();
    }

    @Override
    public int refundRemainingMoney(int extraMoney) {

        return extraMoney;
    }
}

class ProductSelectionState implements State {
    ProductSelectionState() {
        System.out.println("currently in product selection state");
    }

    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) {
        return;
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) {
        return;
    }

    @Override
    public void clickOnSelectProductButton(VendingMachine vendingMachine) {
        return;
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, int codeNumber) throws Exception {
        Item item = vendingMachine.getInventory().getItem(codeNumber);
        int paidByUser = 0;
        for (Coin coin : vendingMachine.getCoinList()) {
            paidByUser += coin.value;
        }
        if (paidByUser < item.getPrice()) {
            System.out.println("insufficient balance returning all money");
            refundFullMoney(vendingMachine);
            vendingMachine.setState(new IdleState(vendingMachine));
        } else if (paidByUser == item.getPrice()) {
            vendingMachine.setState(new DispenseProductState(vendingMachine, codeNumber));
        } else {
            refundRemainingMoney(paidByUser - item.getPrice());
            vendingMachine.setState(new DispenseProductState(vendingMachine, codeNumber));
        }
    }

    @Override
    public Item dispenseProduct(VendingMachine vendingMachine, int codeNumber) {

        return null;
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) {
        System.out.println("returning full amount");
        vendingMachine.setState(new IdleState(vendingMachine));
        return vendingMachine.getCoinList();
    }

    @Override
    public int refundRemainingMoney(int extraMoney) {
        System.out.println("refunding money -" + extraMoney);
        return extraMoney;
    }
}

class DispenseProductState implements State {
    DispenseProductState(VendingMachine vendingMachine, int code) throws Exception {
        System.out.println("currently in dispense state");
        dispenseProduct(vendingMachine, code);
    }

    @Override
    public void clickOnInsertCoinButton(VendingMachine vendingMachine) {
    }

    @Override
    public void insertCoin(VendingMachine vendingMachine, Coin coin) {
    }

    @Override
    public void clickOnSelectProductButton(VendingMachine vendingMachine) {
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, int codeNumber) throws Exception {
    }

    public Item dispenseProduct(VendingMachine vendingMachine, int codeNumber) throws Exception {
        System.out.println("Product has been dispensed");
        Item item = vendingMachine.getInventory().getItem(codeNumber);
        vendingMachine.setState(new IdleState(vendingMachine));
        return item;

    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine vendingMachine) {
        return null;
    }

    @Override
    public int refundRemainingMoney(int extraMoney) {
        return 0;
    }
}

class VendingMachine {
    VendingMachine() {
        this.state = new IdleState();
    }

    private Inventory inventory;
    private State state;
    private List<Coin> coinList;

    public List<Coin> getCoinList() {
        return coinList;
    }

    public void setCoinList(List<Coin> coinList) {
        this.coinList = coinList;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public State getState() {
        return this.state;
    }
}

enum Coin {
    TEN(10), FIVE(5);

    public int value;

    Coin(int value) {
        this.value = value;
    }

}

public class vendingMachineMain {
    public static void main(String args[]) throws Exception {
        VendingMachine machine = new VendingMachine();
        fillUpInventory(machine);
        displayInventory(machine);

        State currState = machine.getState();
        currState.clickOnInsertCoinButton(machine);
        currState=machine.getState();
        currState.insertCoin(machine,Coin.FIVE);
        currState.insertCoin(machine,Coin.FIVE);
        currState.insertCoin(machine,Coin.TEN);

        currState.clickOnSelectProductButton(machine);
        currState=machine.getState();
        currState.selectProduct(machine,102);

        currState=machine.getState();


    }

    private static void displayInventory(VendingMachine vendingMachine) {

        ItemShelf[] slots = vendingMachine.getInventory().inventory;
        for (int i = 0; i < slots.length; i++) {

            System.out.println("CodeNumber: " + slots[i].getCode() +
                    " Item: " + slots[i].getItem().getItemType().name() +
                    " Price: " + slots[i].getItem().getPrice() +
                    " isAvailable: " + !slots[i].isSoldOut());
        }
    }

    private static void fillUpInventory(VendingMachine vendingMachine) {
        ItemShelf[] slots = vendingMachine.getInventory().inventory;
        for (int i = 0; i < slots.length; i++) {
            Item newItem;
            if (i >= 0 && i < 3) {
                slots[i].setItem(new Item(12, ItemType.PEPSI));
            } else if (i >= 3 && i < 5) {
                slots[i].setItem(new Item(12, ItemType.CHOCOLATE));
            } else if (i >= 5 && i < 7) {
                slots[i].setItem(new Item(32, ItemType.JUICE));
            } else if (i >= 7 && i < 10) {
                slots[i].setItem(new Item(23, ItemType.COKE));
            }
            slots[i].setSoldOut(false);
        }
    }


}
