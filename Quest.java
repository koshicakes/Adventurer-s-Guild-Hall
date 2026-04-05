public class Quest {

    private String id, name, status;
    private int xp, gold;
    private Adventurer assigned;

    private String requiredRank; // NEW: rank requirement

    // ================= CONSTRUCTOR =================

    public Quest(String id, String name, int xp, int gold, String requiredRank){
        this.id = id;
        this.name = name;
        this.xp = xp;
        this.gold = gold;
        this.requiredRank = requiredRank;
        this.status = "Available";
        this.assigned = null;
    }

    // ================= RANK LOGIC =================

    private int rankValue(String r){
        switch(r){
            case "S": return 6;
            case "A": return 5;
            case "B": return 4;
            case "C": return 3;
            case "D": return 2;
            default: return 1; // E
        }
    }

    // ================= ASSIGN QUEST =================

    public void assign(Adventurer adv){

        if(status.equals("Completed")){
            System.out.println("Quest already assigned or completed.");
            return;
        }

        if(status.equals("Ongoing")){
            System.out.println("Quest already assigned or completed.");
            return;
        }

        //Rank gating
        if(rankValue(adv.getRank()) < rankValue(requiredRank)){
            System.out.println("Your rank is too low for this quest.");
            return;
        }

        assigned = adv;
        status = "Ongoing";

        System.out.println("Quest assigned successfully.");
    }

    // ================= COMPLETE QUEST =================

    public void complete(){

        if(!status.equals("Ongoing")){
            System.out.println("Quest is not active.");
            return;
        }

        if(assigned == null){
            System.out.println("No adventurer assigned.");
            return;
        }

        assigned.addXP(xp);
        assigned.addGold(gold);

        status = "Completed";

        System.out.println("Quest completed!");
        System.out.println("Rewards: +" + xp + " XP, +" + gold + " Gold");
    }

    // ================= GETTERS =================

    public String getId(){ return id; }
    public String getName(){ return name; }
    public String getStatus(){ return status; }
    public int getXp(){ return xp; }
    public int getGold(){ return gold; }
    public String getRequiredRank(){ return requiredRank; }
}