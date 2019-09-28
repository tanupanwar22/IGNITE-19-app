package com.example.ignite19.Admin.AdminAddTeam;

import com.example.ignite19.Participation;

import java.util.ArrayList;

public class StaticListOfEventParticipation {

    static ArrayList<Participation>  mList = new ArrayList<>();
    public static ArrayList<Participation> getEventListWithParticipation(){
       ArrayList<String > eventNames = new ArrayList<>();
       eventNames.add("Agomotto's Amet");
       eventNames.add("Bidding");
        eventNames.add("Brain Storm");
        eventNames.add("C for Codetta");
        eventNames.add("Captain Algorika");
        eventNames.add("Cerebro");
        eventNames.add("Code Infinity");
        eventNames.add("Dormammu's Loop");
        eventNames.add("Death's Head");
       eventNames.add("Debugging");
        eventNames.add("Krypthon");
        eventNames.add("Merc with the Mouth");
        eventNames.add("Nexus");
        eventNames.add("ORM Master");
        eventNames.add("Relay Coding");
        eventNames.add("Rage of Ultron");
        eventNames.add("Scattegories");
        eventNames.add("Strange Bugs");
        eventNames.add("Tesseract Endeavour");
        eventNames.add("The Search of the Gauntlet");
        eventNames.add("Workshop");
       for(int i = 0;i<eventNames.size();++i){
           Participation p = new Participation.Builder(0,eventNames.get(i)).build();
           mList.add(p);
       }
        return mList;
    }
}
