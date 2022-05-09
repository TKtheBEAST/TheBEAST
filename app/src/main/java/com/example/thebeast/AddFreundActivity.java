package com.example.thebeast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.recyclerViewAdapter.AddFreundRecyclerviewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFreundActivity extends AppCompatActivity implements AddFreundSelectionListener{

    private ImageView xImageView;
    private SearchView searchView;

    private RecyclerView addFreundRecyclerView;
    private AddFreundRecyclerviewAdapter addFreundAdapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private ProgressBar progressBar;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_freund);

        xImageView = findViewById(R.id.xImageView);
        searchView = findViewById(R.id.addFreundSearchView);
        addFreundRecyclerView = findViewById(R.id.addFreundRecyclerView);
        progressBar = findViewById(R.id.addFreundProgressBar);

        addFreundAdapter = new AddFreundRecyclerviewAdapter(this);
        addFreundRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addFreundRecyclerView.setHasFixedSize(true);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        xImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("thebeast@mail.de");
        setFocused(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String email) {
                progressBar.setVisibility(View.VISIBLE);
                searchForBeastEmail(email);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String email) {
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setFocused(SearchView view){
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }


    private void searchForBeastEmail(String email) {


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference userRef = firebaseFirestore.collection("User");

        userRef.whereEqualTo("beastEmail", email).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        addFreundRecyclerView.setAdapter(addFreundAdapter);

                        List<UserModel> user = new ArrayList();
                        user = task.getResult().toObjects(UserModel.class);
                        progressBar.setVisibility(View.INVISIBLE);
                        addFreundAdapter.setFreunde(user);

                    }
                });
    }


    @Override
    public void onFreundSelected(UserModel freund) {
        freundHinzufuegenDialog(freund);
    }

    public void freundHinzufuegenDialog(UserModel user){

        dialogBuilder = new AlertDialog.Builder(this);
        final View freundBestaetigenView = getLayoutInflater().inflate(R.layout.add_freund_popup,null);


        Button freundBestaetigenButton = freundBestaetigenView.findViewById(R.id.freundBestaetigenButton);
        Button abbrechenButton = freundBestaetigenView.findViewById(R.id.freundAbbrechenButton);
        TextView beastNameFreundBestaetigenTextView = freundBestaetigenView.findViewById(R.id.beastNameFreundBestaetigen);

        beastNameFreundBestaetigenTextView.setText(user.getBeastName());

        freundBestaetigenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                List<UserModel> freundeVonUser = new ArrayList<UserModel>();
                freundeVonUser = CurrentUser.getCurrentUser().getFreundeCurrentUser();

                if(CurrentUser.getCurrentUser().getBeastEmail().equals(user.getBeastEmail())){
                    Toast.makeText(AddFreundActivity.this,"Das bist du doch selbst...",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    return;
                }

                for(UserModel freund : freundeVonUser){
                    if(freund.getBeastEmail().equals(user.getBeastEmail())){
                        Toast.makeText(AddFreundActivity.this,"Ihr seid bereits befreundet",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        return;
                    }
                }
                progressBar.setVisibility(View.VISIBLE);
                freundHinzufuegen(user);
                dialog.dismiss();
            }
        });

        abbrechenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(freundBestaetigenView);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    private void freundHinzufuegen(UserModel user){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> data = new HashMap<>();
        data.put("beastID",user.getBeastId());
        data.put("beastName", user.getBeastName());
        data.put("beastSpruch", user.getBeastSpruch());
        data.put("beastEmail", user.getBeastEmail());
        data.put("workoutlaenge", user.getWorkoutlaenge());
        data.put("avatar", user.getAvatar());
        data.put("token", user.getToken());
        data.put("referenz", CurrentUser.getCurrentUser().getBeastName());


        FirebaseMessaging.getInstance().subscribeToTopic(user.getBeastId())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(AddFreundActivity.this,user.getBeastName() +
                                    "Hier ist was schief gelaufen... Versuche es noch einmal",Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            db.collection("User").document(CurrentUser.getCurrentUser().getBeastId())
                                    .collection("Freunde von User").document(user.getBeastId()).set(data)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                List<UserModel> freundeVonUser = new ArrayList<UserModel>();
                                                freundeVonUser = CurrentUser.getCurrentUser().getFreundeCurrentUser();
                                                freundeVonUser.add(user);
                                                CurrentUser.getCurrentUser().setFreundeCurrentUser(freundeVonUser);
                                                Toast.makeText(AddFreundActivity.this,"Du bist jetzt mit " +user.getBeastName() +
                                                        " befreundet.",Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(AddFreundActivity.this,user.getBeastName() +
                                                        "Hier ist was schief gelaufen... Versuche es noch einmal",Toast.LENGTH_LONG).show();
                                            }
                                            finish();
                                        }
                                    });
                        }
                    }
                });


    }
}