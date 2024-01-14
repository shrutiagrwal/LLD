package practice;

import java.util.ArrayList;
import java.util.EnumSet;

enum Rank{
    Two,Three,four,five,six,seven,eight,nine,ten,King,queen,Joker,Ace
//    ACE(1,"ACE")

}
enum Suit{
    DIAMOND,HEARTS,CLUBS,SPADES
}

class Card{
    Suit suit;
    Rank rank;
    Card( Suit suit, Rank rank)
    {
        this.suit=suit;
        this.rank=rank;
    }
    public void getSuit()
    {
        System.out.println(this.suit);
    }
    public void getRank()
    {
        System.out.println(this.rank);
    }

}
 class CardDeck {
    ArrayList<Card> Cards=new ArrayList<>();


    public void buildDeck()
    {
        for(Suit suit: EnumSet.allOf(Suit.class))
        {
            for(Rank rank: EnumSet.allOf(Rank.class))
            {
                if(rank== Rank.Joker)
                       continue;
                else Cards.add(new Card(suit,rank));
            }
        }
        Cards.add(new Card(null,Rank.Joker));
        Cards.add(new Card(null,Rank.Joker));

    }
    public void DisplayCards()
    {
        for(Card card: Cards)
        {
            System.out.println("practice.Card"+ card);
            card.getRank();
            card.getSuit();
            System.out.println("----");
        }
    }
    public void DeckSize()
    {
        System.out.println(Cards.size());
    }
}

public class Cards{
public static void main(String[]args)
{
    CardDeck deck=new CardDeck();
    deck.buildDeck();
//    deck.DisplayCards();
    deck.DeckSize();
}

}



