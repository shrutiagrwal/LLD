package VendingMachine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum ItemType{
    COKE, PEPSI, JUICE
};
@AllArgsConstructor
@Setter
class ItemShelf{
    int code;
    ItemType itemType;
    int count;
    int price;
}
class Inventory{
    Map<Integer,ItemShelf> itemShelves= new HashMap<>();
     public void initialiseInventory(int size){
             itemShelves.put(101,new ItemShelf(101, ItemType.COKE, size, 50));
             itemShelves.put(102,new ItemShelf(102, ItemType.PEPSI, size,40));
             itemShelves.put(103,new ItemShelf(103, ItemType.JUICE,size, 100));
     }

     public ItemShelf getItem(int code){
         return itemShelves.get(code);
     }
}
@Getter
enum Coin {
    TEN(10), FIVE(5);
    public final int value;
    Coin(int value) {
        this.value = value;
    }
}

abstract class State{
    abstract void clickOnInsertCoinButton(VendingMachine machine);
    abstract void insertCoins(VendingMachine machine, Coin coin);
    abstract void ClickOnSelectProductButton(VendingMachine machine);
    abstract void selectProduct(VendingMachine machine, int pCode);
    abstract ItemShelf dispenseProduct(VendingMachine machine, int pCode);
    List<Coin> returnCompleteMoney(VendingMachine machine){
        return machine.getCoinList();
    };
    int returnRemainingMoney(VendingMachine machine,int extraMoney){
        return extraMoney;
    };
    void cancel(VendingMachine machine, int money){
        returnCompleteMoney(machine);
        machine.setState(new IdleState());
    };
}

class IdleState extends State{
    @Override
    public void clickOnInsertCoinButton(VendingMachine machine) {
        machine.setState(new HasMoneyState());
    }
    @Override
    public void insertCoins(VendingMachine machine, Coin coin) {}
    @Override
    public void ClickOnSelectProductButton(VendingMachine machine) {}
    @Override
    public void selectProduct(VendingMachine machine, int pCode) {}
    @Override
    public ItemShelf dispenseProduct(VendingMachine machine, int pCode) {
        return null;
    }
}

class HasMoneyState extends State{
    @Override
    public void clickOnInsertCoinButton(VendingMachine machine) {}
    @Override
    public void insertCoins(VendingMachine machine, Coin coin) {
        machine.getCoinList().add(coin);
    }
    @Override
    public void ClickOnSelectProductButton(VendingMachine machine) {
            machine.setState(new SelectionState());
    }
    @Override
    public void selectProduct(VendingMachine machine, int pCode) {}
    @Override
    public ItemShelf dispenseProduct(VendingMachine machine, int pCode) {
        return null;
    }
}
class SelectionState extends State{

    @Override
    public void clickOnInsertCoinButton(VendingMachine machine) {}

    @Override
    public void insertCoins(VendingMachine machine, Coin coin) {}

    @Override
    public void ClickOnSelectProductButton(VendingMachine machine) {}

    @Override
    public void selectProduct(VendingMachine machine, int pCode) {
        int moneyAddedByUser = machine.getCoinList().stream()
                .mapToInt(coin -> coin.value)
                .sum();

        ItemShelf itemShelf =machine.getInventory().getItem(pCode);
       if(itemShelf.count ==0){
           System.out.println("item is out of stock");
           this.returnCompleteMoney(machine);
           machine.setState(new IdleState());
           return;
       }
       if(moneyAddedByUser < itemShelf.price){
           System.out.println("insufficient balance ");
           returnCompleteMoney(machine);
           machine.setState(new IdleState());
           return;
       }
       if(moneyAddedByUser > itemShelf.price){
           returnRemainingMoney(machine, moneyAddedByUser- itemShelf.price );
       }

        machine.setState(new DispatchState());
        machine.dispenseProduct(pCode); // explicit, not in constructor
    }

    @Override
    public ItemShelf dispenseProduct(VendingMachine machine, int pCode) {
        return null;
    }
}
class DispatchState extends State{

    @Override
    public void clickOnInsertCoinButton(VendingMachine machine) {}

    @Override
    public void insertCoins(VendingMachine machine, Coin coin) {}

    @Override
    public void ClickOnSelectProductButton(VendingMachine machine) {}

    @Override
    public void selectProduct(VendingMachine machine, int pCode) {}

    @Override
    public ItemShelf dispenseProduct(VendingMachine machine, int pCode) {
        System.out.println("Product has been dispensed");
        ItemShelf item= machine.getInventory().getItem(pCode);
        machine.getInventory().getItem(pCode).setCount(item.count-1);
        machine.setState(new IdleState());
        return item;
    }

    @Override
    public List<Coin> returnCompleteMoney(VendingMachine machine) {
        return List.of();
    }

    @Override
    public int returnRemainingMoney(VendingMachine machine, int extraMoney) {
        return 0;
    }

    @Override
    public void cancel(VendingMachine machine, int money) {

    }
}

@Setter
@Getter
class VendingMachine{
    State state;
    private List<Coin> coinList;
    private Inventory inventory;
    VendingMachine (){
        this.state=new IdleState();
        this.inventory=new Inventory();
        this.coinList=new ArrayList<>();
    }

    public void initializeInventory(int size){
        this.inventory.initialiseInventory(size);
    }

    public void clickOnInsertCoinButton(){
        state.clickOnInsertCoinButton(this);
    }
    public void insertCoins(Coin coin){
        state.insertCoins(this, coin);
    }
    public void ClickOnSelectProductButton(){
        state.ClickOnSelectProductButton(this);
    }
    public void selectProduct(int code){
        state.selectProduct(this, code);
    }
    public void dispenseProduct(int code){
        state.dispenseProduct(this, code);
        coinList= new ArrayList<>();
    }
    public void cancel(){
        int moneyAddedByUser =coinList.stream()
                .mapToInt(coin -> coin.value)
                .sum();
        state.cancel(this,moneyAddedByUser);
        coinList= new ArrayList<>();
    }


}
public class demo {

    static void main(String[] args) {
        VendingMachine machine= new VendingMachine();
        machine.initializeInventory(10);

        machine.clickOnInsertCoinButton();
        for(int i=0;i<4;i++){
            machine.insertCoins( Coin.FIVE);
            machine.insertCoins( Coin.TEN);
        }

        machine.ClickOnSelectProductButton();
        machine.selectProduct( 102);
    }
}
