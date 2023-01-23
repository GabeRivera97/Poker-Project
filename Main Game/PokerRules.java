class Poker 
{
  private PlayingCards deck;
  private ArrayList<String> hand1 = new ArrayList<String>();
  private ArrayList<String> hand2 = new ArrayList<String>();

  void dealHands()
  {
    for (int i = 0; i < 10; i++){
      if (i % 2 == 0){
        hand1.add(deck.getNextCard());
      }
      else if (i % 2 != 0){
        hand2.add(deck.getNextCard());
      }
    }
  }

  //constructors
  Poker()
  {
    deck = new PlayingCards();
    deck.Shuffle();
    dealHands();
  }
  
  Poker(ArrayList<String> arr1, ArrayList<String> arr2)
  {
    deck = new PlayingCards();
    hand1 = arr1;
    hand2 = arr2;
  }

  void showHand(int player)
  {
    if (player == 1)
    {
      System.out.println("Player 1's hand:");
      for (int i = 0; i < hand1.size(); i++)
      {
        System.out.print(hand1.get(i) + ", ");
      }
      System.out.println();
    }
    else
    {
      System.out.println("Player 2's hand:");
      for (int i = 0; i < hand2.size(); i++)
      {
        System.out.print(hand2.get(i) + ", ");
      }
      System.out.println();
    }
  }

  int[] countSuite (ArrayList<String> playerHand)
  {
    int[] suiteNums = new int[4];
    
    for (int i = 0; i < playerHand.size(); i ++)
    {
      String[] splitString = playerHand.get(i).split(" ");
      
      if (splitString[2].equals("Clubs"))
      {
        suiteNums[0] += 1;
      }
      else if (splitString[2].equals("Diamonds"))
      {
        suiteNums[1] += 1;
      }
      else if (splitString[2].equals("Hearts"))
      {
        suiteNums[2] += 1;
      }
      else if (splitString[2].equals("Spades"))
      {
        suiteNums[3] += 1;
      }
    }

    return suiteNums;
  }

  int[] countValues (ArrayList<String> playerHand)
  {
    int[] suiteNums = new int[13];

    for (int i = 0; i < playerHand.size(); i ++)
    {
      String[] splitString = playerHand.get(i).split(" ");
      
      switch (splitString[0])
      {
        case "2":
          suiteNums[0] += 1;
          break;
        case "3":
          suiteNums[1] += 1;
          break;
        case "4":
          suiteNums[2] += 1;
          break;
        case "5":
          suiteNums[3] += 1;
          break;
        case "6":
          suiteNums[4] += 1;
          break;
        case "7":
          suiteNums[5] += 1;
          break;
        case "8":
          suiteNums[6] += 1;
          break;
        case "9":
          suiteNums[7] += 1;
          break;
        case "10":
          suiteNums[8] += 1;
          break;
        case "J":
          suiteNums[9] += 1;
          break;
        case "Q":
          suiteNums[10] += 1;
          break;
        case "K":
          suiteNums[11] += 1;
          break;
        case "A":
          suiteNums[12] += 1;
          break;
      }
    }

    return suiteNums;
  }

  int numPairs (int[] handValues)
  {
    int pairs = 0;
    for (int i = 0; i < handValues.length; i++)
    {
      if (handValues[i] == 2)
      {
        pairs += 1;
      }
    }
    return pairs;
  }

  int threeOfAKind (int[] handValues)
  {
    int pairs = 0;
    for (int i = 0; i < handValues.length; i++)
    {
      if (handValues[i] == 3)
      {
        pairs += 1;
      }
    }
    return pairs;
  }

  int fourOfAKind (int[] handValues)
  {
    int pairs = 0;
    for (int i = 0; i < handValues.length; i++)
    {
      if (handValues[i] == 4)
      {
        pairs += 1;
      }
    }
    return pairs;
  }

  boolean fullHouse (int[] handValues){
    if (this.numPairs(handValues) > 0 && this.threeOfAKind(handValues) > 0)
    {
      return true;
    }
    else return false;
  }

  boolean straight (int[] handValues)
  {
    //check for the odd straight (Ace is in index 12)
    if (handValues[0] > 0 && handValues[1] > 0 && handValues[2] > 0 && handValues[3] > 0 && handValues[12] > 0)
    {
      return true;
    }

    //check for sequential values up to the value of 10, as after this an Ace cannot occur (except the odd straight).
    for (int i = 0; i < handValues.length - 4; i++)
    {
      if (handValues[i] > 0)
      {
        if (handValues[i + 1] > 0 && handValues[i + 2] > 0 && handValues[i + 3] > 0 && handValues[i + 4] > 0)
        {
          return true;
        }
      }
    }

    //if none of the above conditions are met, return false
    return false;
  }

  boolean flush (int[] handSuites)
  {
    for (int i = 0; i < handSuites.length; i++){
      if (handSuites[i] == 5) return true;
    }
    return false;
  }

  boolean straightFlush(ArrayList<String> playerHand){
    if (this.straight(this.countValues(playerHand)) && this.flush(this.countSuite(playerHand)))
    {
      return true;
    }
    else return false;
  }

  boolean royalFlush (ArrayList<String> playerHand)
  {
    int[] handValues = this.countValues(playerHand);
    int[] handSuites = this.countSuite(playerHand);

    if (handValues[8] == 1 && handValues[9] == 1 && handValues[10] == 1 && handValues[11] == 1 && handValues[12] == 1)
    {
      if (this.flush(handSuites))
      {
        return true;
      }
    }
    return false;
  }

  //works with all methods above to score the game.
  String scoreHand (int player)
  {
    ArrayList<String> playerHand;
    if (player == 1)
    {
      playerHand = hand1;
    }
    else
    {
      playerHand = hand2;
    }

    int[] playerValues = this.countValues(playerHand);
    int[] playerSuites = this.countSuite(playerHand);

    if (this.royalFlush(playerHand))
    {
      return "Royal Flush";
    }
    else if (this.straightFlush(playerHand))
    {
      return "Straight Flush";
    }
    else if (this.flush(playerSuites))
    {
      return "Flush";
    }
    else if (this.straight(playerValues))
    {
      return "Straight";
    }
    else if (this.fullHouse(playerValues))
    {
      return "Full House";
    }
    else if (this.fourOfAKind(playerValues) > 0)
    {
      return "Four of a Kind";
    }
    else if (this.threeOfAKind(playerValues) > 0)
    {
      return "Three of a Kind";
    }
    else if (this.numPairs(playerValues) > 0)
    {
      return this.numPairs(playerValues) + " pairs";
    }
    else return "High Card";
  }
  
  ArrayList<String> getHand1(){
    return hand1;
  }
}
