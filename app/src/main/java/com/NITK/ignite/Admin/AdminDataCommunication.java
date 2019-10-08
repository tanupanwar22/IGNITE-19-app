package com.NITK.ignite.Admin;

import java.util.ArrayList;
import java.util.HashMap;

public interface AdminDataCommunication {
    ArrayList<String > getCollegeNames();
    ArrayList<String> getEventNames();
    HashMap<String,String> getEventWithDateAndTime();
    HashMap<String,String> getAllUUIDs();
    Boolean  getFlag1Status();
    Boolean getFlag2Status();
 }
