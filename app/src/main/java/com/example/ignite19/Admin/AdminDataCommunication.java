package com.example.ignite19.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface AdminDataCommunication {
    ArrayList<String > getCollegeNames();
    ArrayList<String> getEventNames();
    HashMap<String,String> getEventWithDateAndTime();
    HashMap<String,String> getAllUUIDs();
    Boolean  getFlag1Status();
    Boolean getFlag2Status();
 }
