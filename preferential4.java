import java.util.*;
import java.io.*;
import java.util.Map.Entry;

public class preferential4{
  public static void main(String[] args) throws FileNotFoundException{
    //File myFile = new File(args[0]);
    //Scanner sc = new Scanner(myFile);
    Scanner sc = new Scanner(System.in);
    ArrayList<String[]> votes = new ArrayList<String[]>();
    ArrayList<String> candidates = new ArrayList<String>();
    ArrayList<Integer> length = new ArrayList<Integer>();
    ArrayList<ArrayList<Integer>> allvotes = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<String>> allRounds = new ArrayList<ArrayList<String>>();
    ArrayList<String> eliminated = new ArrayList<String>();
    Boolean lastRound = false;
    int voteNum = 0;
    int longest = 0;
    
    //add votes to an array list
      while(sc.hasNextLine()){
        String[] s = (sc.nextLine().split("\\s+")); 
        length.add(s.length);
        votes.add(s);
        voteNum++;
      }
    
//      for (String i[] : votes) {
//        System.out.println(Arrays.toString(i));
//      }
    
    //get our candidates list
    for(int a = 0; a < votes.size(); a++){
      for(int b = 0; b < votes.get(a).length; b++){
        if(!candidates.contains(votes.get(a)[b])){
          candidates.add((votes.get(a))[b]);
        }
      }
    }
    
    //get a copy of our candidate arraylist
    ArrayList<String> candidatecopy = new ArrayList<String>();
    for(int co = 0; co < candidates.size(); co++){
      candidatecopy.add(candidates.get(co));
    }
    
    //System.out.print(candidates);
    
    loop:
      for(int r = 1; r <= candidatecopy.size(); r++){ //for each round 
      
      if(lastRound != true && r == candidatecopy.size()){
        break loop;
      }
      if(lastRound == true && r == candidatecopy.size()){
        lastRound = false;
      }
      System.out.println("Round " + (r));
      ArrayList<Integer> vote = new ArrayList<Integer>();
      ArrayList<Integer> indx = new ArrayList<Integer>();
      ArrayList<String> round = new ArrayList<String>();
      HashMap<String, Integer> map = new HashMap<String, Integer>();
      LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
      for(int v = 0; v < votes.size(); v++){
        int k = 0; //check the first column
        int index = (search(candidates, votes.get(v)[k]));
        if(index == -1){
          //System.out.println("look at next column");
          for(int next = k+1; next < votes.get(v).length; next++){ //check the next columns
            index = (search(candidates, votes.get(v)[next]));
            if(index != -1){
              indx.add(index);
              break;
            }
          }
        }else{
          indx.add(index);
        }
      }
      for(int o = 0; o < candidates.size(); o++){
        int occurrences = Collections.frequency(indx,(o));
        vote.add(occurrences);
        map.put(candidates.get(o), occurrences);
        round.add(candidates.get(o));
      }
      
      //sorting and printing our data
      String Strmax = Collections.max(candidates, Comparator.comparing(String::length));
      List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
      Collections.sort(list, new ValueThenKeyComparator<String, Integer>());
      Map<String, Integer> sortedmap = new HashMap<String,Integer>();
      for (Map.Entry<String, Integer> i : list){
        String key = i.getKey();
        Integer value = i.getValue();
        System.out.printf("%-" + (Strmax.length()+2) + "s %-2s %n",key,value);
      }
      
      //insta win 
      if(Collections.max(vote) > (voteNum/2)){
        int index = vote.indexOf(Collections.max(vote));
        System.out.println("Winner: " + candidates.get(index));
        break loop;
      }
      
      //System.out.println(vote);
      allRounds.add(round);
      allvotes.add(vote);
      //System.out.println("round" + round);
      //System.out.println("can " + candidates);
      //System.out.println("ar1 " + allRounds);
      
      int min = Collections.min(vote);
      
      ArrayList<Integer> mins = minima(vote, min); //find indexes of all minimum values in our vote arraylst
      ArrayList<String> pool = new ArrayList<String>();
      for(int m = 0; m < mins.size(); m++){
        pool.add(round.get(mins.get(m)));
      }
//        System.out.println("pool" +pool);
//        System.out.println("min" + mins);
      w:
        while(pool.size() > 1){
        if(r == candidatecopy.size()-1){
          //System.out.println("Final round");
          lastRound = true;
        }
        if(r == 1){
          System.out.println("Unbreakable Tie");
          break loop;
        }
        else{ 
          for(int back = r-2; back >=0; back--){ //for each previous round
            //System.out.println(allRounds.get(back));
            //System.out.println(allvotes.get(back));
            ArrayList<Integer> current = new ArrayList<Integer>();
            for(int p = 0; p < pool.size(); p++){
              
              int f = search(allRounds.get(back), pool.get(p));
              current.add(allvotes.get(back).get(f));
            }
            int min2 = Collections.min(current);
            if(minima(current, min2).size() == 1){
              String keep = (pool.get(minima(current, min2).get(0)));
              pool.clear();
              pool.add(keep);
              //System.out.println("new pool" + pool);
              break w;
            }
            if(back ==0){
              System.out.println("Unbreakable tie");
            }
          }
          break loop;
        }
      }
        
        
        
        //after our while loop we should only have one person to eliminate
        if(pool.size() == 1){
          if(r == candidatecopy.size()){
            System.out.println("Winner: " + candidates.get(0));
          }else{
            //System.out.println(lastRound);
            String candidate = pool.get(0);
            eliminated.add(pool.get(0));
            candidates.remove(pool.get(0));
            if(lastRound == true){
              System.out.println("Eliminated: " + candidate);
              System.out.println();
            }else{
              //System.out.println(candidates.size());
              if(candidates.size() == 1){ //if we only have one candidate left
                System.out.println("Winner: " + candidates.get(0)); //we have our winner 
              }
              else{
                System.out.println("Eliminated: " + candidate); //otherwise thou has been eliminated
                System.out.println();
              }
            }
          }
        }
    }
  }
  
  public static ArrayList<Integer> minima(ArrayList<Integer> vote, int min){
    ArrayList<Integer> Index = new ArrayList<Integer>();
    for(int index = 0; index < vote.size(); index++){
      if(vote.get(index) == min){
        Index.add(index);
      }
    }
    return Index;
  }
  
  public static int maxima(ArrayList<Integer> vote){
    int max = Collections.max(vote);
    return max;
  }
  
  public static int search(ArrayList<String> round, String can){
    int Index = round.indexOf(can);
    return Index;
  }
}