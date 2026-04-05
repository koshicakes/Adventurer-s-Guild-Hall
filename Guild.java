import java.util.*;
import java.io.*;

public class Guild {

    private ArrayList<Adventurer> adventurers = new ArrayList<>();
    private ArrayList<Quest> quests = new ArrayList<>();

    // ================= AUTO ID =================

    public String generateAdventurerId(){
        return "A" + String.format("%03d", adventurers.size() + 1);
    }

    public String generateQuestId(){
        return "Q" + String.format("%03d", quests.size() + 1);
    }

    // ================= LOAD / SAVE =================

    public void loadAdventurers(){
        try{
            File file = new File("adventurers.txt");
            if(!file.exists()) file.createNewFile();

            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()){
                String[] d = sc.nextLine().split(",");
                if(d.length < 6) continue;

                adventurers.add(new Adventurer(
                    d[0], d[1],
                    Integer.parseInt(d[2]),
                    d[3], d[4], d[5]
                ));
            }

            sc.close();
        }catch(Exception e){
            System.out.println("Error loading adventurers.");
        }
    }

    public void saveAdventurers(){
        try{
            PrintWriter pw = new PrintWriter("adventurers.txt");

            for(Adventurer a: adventurers){
                pw.println(a.getId()+","+a.getName()+","+a.getAge()+","+a.getRank()+","+a.getClassType()+","+a.getPassword());
            }

            pw.close();
        }catch(Exception e){
            System.out.println("Error saving adventurers.");
        }
    }

    public void loadQuests(){
        try{
            File file = new File("quests.txt");
            if(!file.exists()) file.createNewFile();

            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()){
                String[] d = sc.nextLine().split(",");
                if(d.length < 5) continue;

                quests.add(new Quest(d[0], d[1], Integer.parseInt(d[2]), Integer.parseInt(d[3]), d[4]));
            }

            sc.close();
        }catch(Exception e){
            System.out.println("Error loading quests.");
        }
    }

    public void saveQuests(){
        try{
            PrintWriter pw = new PrintWriter("quests.txt");

            for(Quest q: quests){
                pw.println(
                        q.getId()+","+
                        q.getName()+","+
                        q.getXp()+","+
                        q.getGold()+","+
                        q.getRequiredRank()
                );
            }

            pw.close();
        }catch(Exception e){
            System.out.println("Error saving quests.");
        }
    }

    // ================= CORE =================

    public void register(String name,int age,String classType,String password){

        String id = generateAdventurerId();

        adventurers.add(new Adventurer(id,name,age,"E",classType,password));
        saveAdventurers();

        System.out.println("Adventurer successfully registered.");
        System.out.println("Your ID: " + id);
    }

    public Adventurer findAdv(String id){
        for(Adventurer a: adventurers){
            if(a.getId().equals(id)) return a;
        }
        return null;
    }

    public void removeAdventurer(String id){

        Iterator<Adventurer> it = adventurers.iterator();

        while(it.hasNext()){
            Adventurer a = it.next();

            if(a.getId().equals(id)){

                if(a.isInParty()){
                    Party p = a.getParty();

                    if(a.equals(p.getLeader())){
                        for(Adventurer m : p.getMembers()){
                            m.setParty(null);
                        }
                        System.out.println("Party disbanded.");
                    } else {
                        p.removeMember(p.getLeader(), a);
                    }
                }

                it.remove();
                saveAdventurers();

                System.out.println("Operation completed successfully.");
                return;
            }
        }

        System.out.println("Adventurer not found.");
    }

    public void searchAdventurer(String id){

        Adventurer a = findAdv(id);

        if(a == null){
            System.out.println("Not found.");
            return;
        }

        a.showStatus();
        System.out.println("Party: " + (a.isInParty() ? a.getParty().getId() : "None"));
    }

    // ================= QUEST =================

    public void addQuest(String name,int xp,int gold,String rank){

    String id = generateQuestId();

    quests.add(new Quest(id,name,xp,gold,rank));
    saveQuests();

    System.out.println("Quest added. ID: " + id);
}

    public Quest findQuest(String id){
        for(Quest q: quests){
            if(q.getId().equals(id)) return q;
        }
        return null;
    }

    public void showQuests(){

        System.out.println("\n========== QUEST BOARD ==========");

        for(Quest q: quests){
            System.out.printf("%-5s | %-15s | %-10s\n",
                q.getId(), q.getName(), q.getStatus());
        }

        System.out.println("=================================\n");
    }

    // ================= SORT =================

    private int getRankValue(String r){
        switch(r){
            case "S": return 6;
            case "A": return 5;
            case "B": return 4;
            case "C": return 3;
            case "D": return 2;
            default: return 1;
        }
    }

    public void showAdventurers(){

        ArrayList<Adventurer> sorted = new ArrayList<>(adventurers);

        sorted.sort((a,b) -> getRankValue(b.getRank()) - getRankValue(a.getRank()));

        System.out.println("\n========== ADVENTURERS ==========");

        for(Adventurer a: sorted){
            System.out.printf("%s | %s | %s | %s\n",
                a.getId(), a.getName(), a.getRank(), a.getClassType());
        }

        System.out.println("=================================\n");
    }
}