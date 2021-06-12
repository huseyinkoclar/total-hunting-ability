import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class MuhammedHuseyin_Koclar_2018510046{
    public static Lion[] lions = new Lion[65];
    public static Lion[] sortedLionsForDp = new Lion[65];
    public static Lion[] sortedLions = new Lion[65];
    public static Lion[] selectedGreedyLions = new Lion[65];
    public static Lion[] selectedDPLions = new Lion[65];
    String map = new String();
    public static void main(String args[]) throws FileNotFoundException{
        readhuntingAbilities();
        readlionsHierarchy();
        traverseTree(findRoot());
        printArray(sortedLionsForDp);
        System.out.println();
        System.out.println("DP Results:  " + calculateTotalHuntingAbilities());
        sortingLions();
        System.out.println("Greedy Results:  " + greedyApproach());
        printArray(selectedGreedyLions);
        
    }

    public static int calculateTotalHuntingAbilities(){
        int total = 0;
        int total1 = 0;
        int total2 = 0;
        for(int i=1; i<sortedLionsForDp.length; i++){
            if(i%2==0){
                total1 += sortedLionsForDp[i].gethuntingAbility();
            }
            else{
                total2 += sortedLionsForDp[i].gethuntingAbility();
            }
            if(sortedLionsForDp[i].getleftChild()==null){
                total += Math.max(total1, total2);
                total1 = 0;
                total2 = 0;
            }    
        }
        return total;
    }
    

    static void traverseTree(Lion root){
        int counter = 0;
        sortedLionsForDp[0] = root;
        while(counter!=lions.length-1){
        if(root.getleftChild()!=null && root.getleftChild().getchoosen() != true){
            root.getleftChild().setchoose(true);
            counter++;
            sortedLionsForDp[counter] = root.getleftChild();
            root = root.getleftChild();
            continue;
        }
        else if(root.getrightSibling()!=null && root.getrightSibling().getchoosen() != true){
            root.getrightSibling().setchoose(true);
            counter++;
            sortedLionsForDp[counter]=root.getrightSibling();
            root = root.getrightSibling();
            continue;
        }
        else if(root.getParent()!=null){
            root = root.getParent();
            continue;
        }
        else break;
    }
    for(int i = 0; i<sortedLionsForDp.length; i++){
        sortedLionsForDp[i].setchoose(false);
    }
 }

  
    public static void printArray(Lion[] lions){
        for(Lion lion : lions){
            if(lion!=null)
            System.out.print(lion.getName() + " - ");
        }
    }


    public static int greedyApproach(){
        int counter = 0;
        for(Lion lion : sortedLions){
            boolean flag = false;
            for(Lion l : selectedGreedyLions){
                if(l == null) break;
                if(lion.getParent() == l || l.getParent() == lion){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                selectedGreedyLions[counter] = lion;
                counter++;
            }
        }

        int total = 0;
        for( Lion lion : selectedGreedyLions){
            if(lion != null)
            total += lion.gethuntingAbility();
        }
        return total;
    }


    public static void sortingLions(){
        Lion temp;
        sortedLions = lions;
        for(int i = 1; i<sortedLions.length; i++){
            for(int j = i; j>0; j--){
                if(sortedLions[j].gethuntingAbility() > sortedLions[j-1].gethuntingAbility()){
                    temp = sortedLions[j];
                    sortedLions[j] = sortedLions[j-1];
                    sortedLions[j-1] = temp;
                }
            }
        }
    }


    public static Lion findRoot(){
        for(int i = 0; i < lions.length; i++){
            if(lions[i].getParent()==null){
                return lions[i];
            }
        }
        return null;
    }


    public static void readhuntingAbilities() throws FileNotFoundException{
        File file = new File("hunting_abilities.txt");
        Scanner sc = new Scanner(file);
        sc.nextLine();
        int counter = 0;
        while(sc.hasNextLine()){
            String[] line = sc.nextLine().split("	");
            Lion lion = new Lion(line[0], Integer.parseInt(line[1]));
            lions[counter] = lion;
            counter++;
        }
        sc.close();

    }


    public static void readlionsHierarchy() throws FileNotFoundException{
        File file = new File("lions_hierarchy.txt");
        Scanner sc = new Scanner(file);
        sc.nextLine();
        
        while(sc.hasNextLine()){
            String[] line = sc.nextLine().split("	");
            Lion firstLion = null;
            Lion secondLion = null;
            for(int i = 0; i<lions.length; i++){
                if(lions[i].getName().equals(line[1])){
                    secondLion = lions[i];
                }
                else if(lions[i].getName().equals(line[0])){
                    firstLion = lions[i];
                }
            }
            if(line[2].equals("Left-Child")){
                firstLion.setleftChild(secondLion);
                secondLion.setParent(firstLion);

            }
            else if(line[2].equals("Right-Sibling")){
                firstLion.setrightSibling(secondLion);
                secondLion.setParent(firstLion.getParent());
            }
        }
        sc.close();
    }
    

    
}

class Lion{
    private String name;
    private Lion leftChild;
    private Lion rightSibling;
    private Lion parent;
    private int huntingAbility;
    private boolean choosen = false;

    Lion(String name, int huntingAbility){
        this.name = name;
        this.huntingAbility = huntingAbility;
    }

    public boolean getchoosen(){
        return choosen;
    }

    public void setchoose(boolean choosen){
        this.choosen = choosen;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Lion getParent(){
        return parent;
    }

    public void setParent(Lion parent){
        this.parent = parent;
    }

    public Lion getrightSibling(){
        return rightSibling;
    
    }

    public void setrightSibling(Lion rightSibling){
        this.rightSibling = rightSibling;
    }

    public Lion getleftChild(){
        return leftChild;
    }

    public void setleftChild(Lion leftChild){
        this.leftChild = leftChild;
    }

    public int gethuntingAbility(){
        return huntingAbility;
    }

    public void sethuntingAbility(int huntingAbility){
        this.huntingAbility = huntingAbility;
    }


}