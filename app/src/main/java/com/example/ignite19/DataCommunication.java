package com.example.ignite19;

import com.example.ignite19.ui.schedule.eventSchedule;
import java.util.ArrayList;

public interface DataCommunication {
    ArrayList<eventSchedule> getDay0CompleteSchedule();
    ArrayList<eventSchedule> getDay1CompleteSchedule();
    ArrayList<eventSchedule> getDay2CompleteSchedule();
    ArrayList<UserDataAndEventList> getAllEventList();
    ArrayList<Participation> getUserParticipationDetails();
    ArrayList<String> getEventNames();
    String  getUUID();
    UserDetail getUserDetail();
    String getUserName();
    Boolean getFirstListenerFlagStatus();
    Boolean getSecondListenerFlagStatus();

}
