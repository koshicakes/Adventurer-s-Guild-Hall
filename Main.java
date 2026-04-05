import java.util.*;
import java.io.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Guild guild = new Guild();
    static ArrayList<Party> parties = new ArrayList<>();

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "admin123";

    // ================= PARTY DB =================

    static String generatePartyId(){
        return "P" + String.format("%03d", parties.size() + 1);
    }

    static void saveParties(){
        try{
            PrintWriter pw = new PrintWriter("parties.txt");

            for(Party p: parties){
                StringBuilder line = new StringBuilder();
                line.append(p.getId()).append(",").append(p.getLeader().getId());

                for(Adventurer a: p.getMembers()){
                    line.append(",").append(a.getId());
                }

                pw.println(line.toString());
            }

            pw.close();
        }catch(Exception e){
            System.out.println("Error saving parties.");
        }
    }


    //--DITO MO LAGAY MARTIN

    

   // ================= PARTY =================

    static void partyMenu(Adventurer a){

    int c;

    do{
        System.out.println("\n===== PARTY MENU =====");
        System.out.println("[1] Create Party");
        System.out.println("[2] Join Party");
        System.out.println("[3] Show Party");
        System.out.println("[4] Back");

        System.out.print("Choice: ");
        c = sc.nextInt(); sc.nextLine();

        switch(c){

            case 1:
                if(a.isInParty()){
                    System.out.println("Already in party.");
                    break;
                }

                Party p = new Party(generatePartyId(), a);
                parties.add(p);
                saveParties();

                System.out.println("Party created.");
                break;

            case 2:
                for(Party pt: parties){
                    System.out.println(pt.getId() + " Leader: " + pt.getLeader().getName());
                }

                System.out.print("Enter Party ID: ");
                String id = sc.nextLine();

                for(Party pt: parties){
                    if(pt.getId().equals(id)){
                        pt.addMember(a);
                        saveParties();
                        return;
                    }
                }

                System.out.println("Not found.");
                break;

            case 3:
                if(a.isInParty()) a.getParty().showStatus();
                else System.out.println("Not in party.");
                break;
        }

        }while(c != 4);
    }

        static void showParties(){

    System.out.println("\n========== PARTY LIST ==========");

    if(parties.isEmpty()){
        System.out.println("No parties available.");
        System.out.println("================================");
        return;
    }

    for(Party p: parties){
        System.out.printf("ID: %-5s | Leader: %-10s | Members: %d/%d\n",
                p.getId(),
                p.getLeader().getName(),
                p.getSize(),
                p.getMaxSize());
    }

    System.out.println("================================\n");
    }
}