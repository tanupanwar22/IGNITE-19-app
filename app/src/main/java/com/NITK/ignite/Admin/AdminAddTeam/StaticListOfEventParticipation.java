package com.NITK.ignite.Admin.AdminAddTeam;

import com.NITK.ignite.Participation;

import java.util.ArrayList;

public class StaticListOfEventParticipation {

    static ArrayList<Participation>  mList = new ArrayList<>();
    public static ArrayList<Participation> getEventListWithParticipation(){
       ArrayList<String > eventNames = new ArrayList<>();
       eventNames.add("Agomotto's Amet");
        eventNames.add("C for Codetta");
        eventNames.add("Captain Algorika");
        eventNames.add("Cerebro");
        eventNames.add("Code Infinity");
        eventNames.add("Dormammu's Loop");
        eventNames.add("Death's Head");
        eventNames.add("Krypthon");
        eventNames.add("Merc with the Mouth");
        eventNames.add("ORM Master");
        eventNames.add("Rage of Ultron");
        eventNames.add("Scattegories");
        eventNames.add("The Search of Gauntlet");
        eventNames.add("Nexus");
        eventNames.add("End Game");
        eventNames.add("The Last Crusade");
       for(int i = 0;i<eventNames.size();++i){
           Participation p = new Participation.Builder(0,eventNames.get(i)).build();
           mList.add(p);
       }
        return mList;
    }
}
