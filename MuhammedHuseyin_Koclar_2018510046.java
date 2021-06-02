import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class MuhammedHuseyin_Koclar_2018510046{
    public static Lion[] lions = new Lion[65];
    public static Lion[] lionsGoHunting = new Lion[65];
    String map = new String();
    public static void main(String args[]) throws FileNotFoundException{
        readhuntingAbilities();
        readlionsHierarchy();
        System.out.println("possible total hunting abilities = " + calculateTotalHuntingAbilities(findRoot()));
    }

    public static int calculateTotalHuntingAbilities(Lion lion){
        int firstSitutation;
        int secondSitutaion;
        if(lion == null) return 0;
        if(lion.getleftChild()!=null){
            firstSitutation = lion.gethuntingAbility() + calculateTotalHuntingAbilities(lion.getleftChild().getleftChild()) + calculateTotalHuntingAbilities(lion.getrightSibling());
            secondSitutaion = calculateTotalHuntingAbilities(lion.getleftChild()) + calculateTotalHuntingAbilities(lion.getrightSibling());
        }
        else
            return 0;
        return Math.max(firstSitutation,secondSitutaion);

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

    Lion(String name, int huntingAbility){
        this.name = name;
        this.huntingAbility = huntingAbility;
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