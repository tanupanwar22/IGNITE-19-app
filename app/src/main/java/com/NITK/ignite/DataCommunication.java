package com.NITK.ignite;

import com.NITK.ignite.ui.schedule.eventSchedule;
import java.util.ArrayList;

public interface DataCommunication {
    ArrayList<eventSchedule> getDay0CompleteSchedule();
    ArrayList<eventSchedule> getDay1CompleteSchedule();
    ArrayList<eventSchedule> getDay2CompleteSchedule();
    ArrayList<UserDataAndEventList> getAllEventList();
    ArrayList<Participation> getUserParticipationDetails();
    ArrayList<String> getEventNames();
    String  getUUID();
    ArrayList<String> getRegisteredEventNames();
    ArrayList<String> getUnRegisteredEventNames();
    UserDetail getUserDetail();
    String getUserName();
    Boolean getFirstListenerFlagStatus();
    Boolean getSecondListenerFlagStatus();

}
