public class Adventurer {

    private String id, name, rank, classType, password;
    private int age, hp, mp, attack, defense, xp = 0, gold = 0;
    private Party party = null;

    public Adventurer(String id, String name, int age, String rank, String classType, String password){

        if(age <= 0) throw new IllegalArgumentException("Invalid age.");
        if(!(rank.matches("[EDCBAS]"))) throw new IllegalArgumentException("Invalid rank.");

        if(!(classType.equals("Warrior") ||
             classType.equals("Mage") ||
             classType.equals("Archer") ||
             classType.equals("Healer") ||
             classType.equals("Rogue")))
            throw new IllegalArgumentException("Invalid class type.");

        this.id=id;
        this.name=name;
        this.age=age;
        this.rank=rank;
        this.classType=classType;
        this.password=password;

        assignStats();
    }

    private void assignStats(){
        switch(classType){
            case "Warrior": hp=150; mp=40; attack=20; defense=18; break;
            case "Mage": hp=80; mp=150; attack=25; defense=8; break;
            case "Archer": hp=100; mp=60; attack=18; defense=12; break;
            case "Healer": hp=90; mp=140; attack=10; defense=10; break;
            case "Rogue": hp=95; mp=70; attack=22; defense=11; break;
        }
    }
    
    public String getRank(){ return rank; }
    public String getClassType(){ return classType; }
    public String getId(){ return id; }
    public String getName(){ return name; }
    public String getPassword(){ return password; }
    public Party getParty(){ return party; }
    public void setParty(Party p){ party = p; }
    public boolean isInParty(){ return party != null; }

    public void addXP(int xp){ this.xp += xp; updateRank(); }
    public void addGold(int gold){ this.gold += gold; }

    private void updateRank(){
        if(xp>=100000) rank="S";
        else if(xp>=50000) rank="A";
        else if(xp>=15000) rank="B";
        else if(xp>=5000) rank="C";
        else if(xp>=1000) rank="D";
        else rank="E";
    }

    public void showStatus(){
        System.out.println("\n========= ADVENTURER STATUS =========");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Rank: " + rank);
        System.out.println("Class: " + classType);
        System.out.println("-------------------------------------");
        System.out.println("HP: " + hp + "   MP: " + mp);
        System.out.println("ATK: " + attack + "  DEF: " + defense);
        System.out.println("-------------------------------------");
        System.out.println("XP: " + xp + "   Gold: " + gold);
        System.out.println("=====================================\n");
    }
}
