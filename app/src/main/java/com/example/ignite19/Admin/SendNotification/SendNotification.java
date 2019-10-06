package com.example.ignite19.Admin.SendNotification;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ignite19.Admin.AdminDataCommunication;
import com.example.ignite19.Admin.NotificationSender;
import com.example.ignite19.Admin.NotificationSenderAdmin;
import com.example.ignite19.Participation;
import com.example.ignite19.R;
import com.example.ignite19.ui.Notifications.NotificationPOJO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.core.Tag;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendNotification extends Fragment implements View.OnClickListener {
    ArrayList<String > collegeNames = new ArrayList<>();
    Button sendButton;
    EditText titleEditText,bodyEditText;
    AdminDataCommunication dataCommunication;
    HashMap<String ,String> uuidList = new HashMap<>();
    String titleInput,bodyInput;
    View v;
    View alertView;
    AlertDialog alertDialog;
    String topicCollegeName,uuid;

    public SendNotification() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataCommunication = (AdminDataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        uuidList = dataCommunication.getAllUUIDs();
        Bundle bundle = getArguments();
        if(bundle!=null){
            collegeNames = bundle.getStringArrayList("college_names");
        }
        v = inflater.inflate(R.layout.fragment_send_notification, container, false);
        titleEditText = v.findViewById(R.id.not_title);
        bodyEditText = v.findViewById(R.id.not_body);
        sendButton = v.findViewById(R.id.not_button);
        sendButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.not_button:
                if(checkEditTextEmptyOrNot()){
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    //show alertdialog here
                    TextView headerTextView,bodyTextView;
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    alertView = inflater.inflate(R.layout.alert_dialog_confirmation_admin, null);
                     headerTextView = alertView.findViewById(R.id.titlemm);
                     bodyTextView = alertView.findViewById(R.id.bodymm);
                     headerTextView.setText(titleInput);
                     bodyTextView.setText(bodyInput);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setView(alertView);
                    builder.setPositiveButton("I CONFIRM", null);
                    builder.setNegativeButton("CANCEL", null);
                    builder.setTitle("Please Acknowledge");
                    builder.setIcon(R.drawable.ic_notification);
                    alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.setCancelable(false);
                    Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button c = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    c.setTextColor(getResources().getColor(R.color.materialGreen));
                    b.setTextColor(getResources().getColor(R.color.materialGreen));
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            for(int i = 0 ; i <collegeNames.size();++i){
                                //changing college name to proper format
                                topicCollegeName = collegeNames.get(i).replaceAll(" ","_").toLowerCase();
                                uuid = uuidList.get(topicCollegeName);
                                NotificationSender.uploadToFirebase(getContext(),titleInput,bodyInput,topicCollegeName,uuid);
                            }
                            NotificationSenderAdmin.uploadToFirebase(getContext(),titleInput,bodyInput,"admin_xx","00");
                            alertDialog.dismiss();


                            Navigation.findNavController(view).navigate(R.id.action_sendNotification_to_adminHomeFragment);
                        }
                    });
                    c.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });
                    //now lets put this input in the form of message and send it as post request and message

                }
                else{
                    Toasty.error(getContext(),"Please enter values in both the fields",Toasty.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }
    }
    private boolean checkEditTextEmptyOrNot() {
        titleInput = titleEditText.getText().toString();
        bodyInput = bodyEditText.getText().toString();
        if(TextUtils.isEmpty(titleInput) || TextUtils.isEmpty(bodyInput)){
            return false;
        }
        else {
            return true;
        }
    }
}
