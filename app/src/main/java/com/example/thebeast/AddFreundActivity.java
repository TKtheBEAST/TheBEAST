package com.example.thebeast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.recyclerViewAdapter.AddFreundRecyclerviewAdapter;
import com.example.thebeast.recyclerViewAdapter.FreundeRecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddFreundActivity extends AppCompatActivity implements AddFreundSelectionListener{

    private ImageView xImageView;
    private SearchView searchView;

    private RecyclerView addFreundRecyclerView;
    private AddFreundRecyclerviewAdapter addFreundAdapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_freund);

        xImageView = findViewById(R.id.xImageView);
        searchView = findViewById(R.id.addFreundSearchView);
        addFreundRecyclerView = findViewById(R.id.addFreundRecyclerView);

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String email) {
                searchForBeastEmail(email);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String email) {
                return false;
            }
        });
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
                        addFreundAdapter.setFreunde(user);

                    }
                });
    }


    @Override
    public void onFreundSelected(UserModel freund) {
        Toast.makeText(this,freund.getBeastEmail(),Toast.LENGTH_LONG).show();
    }

    public void beastNameAnpassenDialog(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View beastNameAendernView = getLayoutInflater().inflate(R.layout.beastname_aendern_popup,null);


        Button beastNameuebernehmenButton = beastNameAendernView.findViewById(R.id.uebernehmenBeastNaendernPopup);
        Button abbrechenButton = beastNameAendernView.findViewById(R.id.abbrechenBeastNaendernPopup);
        EditText beastNameTextView = beastNameAendernView.findViewById(R.id.beastNameaendernPopupET);

        beastNameTextView.setHint(CurrentUser.getCurrentUser().getBeastName());

        beastNameuebernehmenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String neuerBeastName = beastNameTextView.getText().toString();
                einstellungenFragmentViewModel.updateBeastName(neuerBeastName);
                startActivity(new Intent(getActivity(),MainActivity.class));
                dialog.dismiss();
            }
        });

        abbrechenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(beastNameAendernView);
        dialog = dialogBuilder.create();
        dialog.show();


    }
}