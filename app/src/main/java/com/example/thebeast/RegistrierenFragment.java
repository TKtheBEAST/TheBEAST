package com.example.thebeast;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.viewmodel.RegistrierenFragmentViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.view.View.GONE;


public class RegistrierenFragment extends Fragment {


    private RegistrierenFragmentViewModel registrierenFragmentViewModel;
    private final int standardWorkoutlaenge = 2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uriImage;

    private NavController nav;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("User");
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;

    private Button registrierenButton;
    private EditText beastNameEditText, beastSpruchEditText, emailEditText, passwort1EditText, passwort2EditText;
    private TextView loginTV;
    private ProgressBar registrierenProgressBar;
    private ImageView avatar;


    public RegistrierenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrieren, container, false);


        registrierenButton = view.findViewById(R.id.registrierenButton);
        beastNameEditText = view.findViewById(R.id.beastnameRegET);
        beastSpruchEditText = view.findViewById(R.id.beastSpruchRegET);
        emailEditText = view.findViewById(R.id.emailRegET);
        passwort1EditText = view.findViewById(R.id.password1RegET);
        passwort2EditText = view.findViewById(R.id.password2RegET);
        registrierenProgressBar = view.findViewById(R.id.registrierenProgressBar);
        loginTV = view.findViewById(R.id.loginRegTV);
        avatar = view.findViewById(R.id.avatarRegIV);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("avatare/");

        avatar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        registrierenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                registrieren();
            }
        });
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registrierenFragment_to_loginFragment);
            }
        });


        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            avatar.setImageURI(uriImage);
        }
    }

    public void registrieren() {
        String beastName = beastNameEditText.getText().toString().trim();
        String beastSpruch = beastSpruchEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String passwort1 = passwort1EditText.getText().toString().trim();
        String passwort2 = passwort2EditText.getText().toString().trim();

        if (beastName.isEmpty()) {
            beastNameEditText.setError("Bitte gebe einen Spitznamen ein");
            beastNameEditText.requestFocus();
            return;
        }
        if (beastSpruch.isEmpty()) {
            beastSpruchEditText.setError("Sag den anderen was du für ein Beast bist");
            beastSpruchEditText.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailEditText.setError("Bitte gebe deine Email an");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Bitte gebe eine richtige Email an");
            emailEditText.requestFocus();
            return;
        }
        if (passwort1.isEmpty()) {
            passwort1EditText.setError("Bitte gebe ein Passwort ein");
            passwort2EditText.requestFocus();
            return;
        }
        if (passwort1.length() < 6) {
            passwort1EditText.setError("Das Passwort muss mindestens 6 zeichen enthalten");
            passwort1EditText.requestFocus();
            return;
        }
        if (!passwort2.equals(passwort1)) {
            passwort2EditText.setError("Die Passwörter stimmen nicht überein");
            passwort2EditText.requestFocus();
            return;
        }

        registrierenProgressBar.setVisibility(View.VISIBLE);

        //über Viemodel und Repository??
        //registrierenFragmentViewModel.createUserWithEmailAndPassword(beastName, beastSpruch, email, passwort2);


        mAuth.createUserWithEmailAndPassword(email, passwort2)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            StorageReference fileReference = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "." + uriImage);
                            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            UserModel user = new UserModel(beastName, beastSpruch, email, standardWorkoutlaenge);
                                            Uri avatar = uri;
                                            Map<String, Object> data = new HashMap<>();
                                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            data.put("beastID", uid);
                                            data.put("beastName", user.getBeastName());
                                            data.put("beastSpruch", user.getBeastSpruch());
                                            data.put("beastEmail", user.getBeastEmail());
                                            data.put("workoutlaenge", user.getWorkoutlaenge());
                                            data.put("avatar", uri.toString());

                                            userRef.document(uid).set(data)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                            if (task.isSuccessful()) {
                                                                user.sendEmailVerification();
                                                                Toast.makeText(getView().getContext(), beastName + " wurde erfolgreich hinzugefügt!" +
                                                                        " Checke deine Mails um deine Mail zu verifizieren", Toast.LENGTH_LONG).show();
                                                                Log.d(TAG, "User wurde hinzugefügt ");
                                                                registrierenProgressBar.setVisibility(GONE);
                                                                Navigation.findNavController(getView()).navigate(R.id.action_registrierenFragment_to_loginFragment);

                                                            } else {
                                                                Toast.makeText(getView().getContext(), "Du konntest nicht Registriert werden :( Versuche es noch einmal!", Toast.LENGTH_LONG).show();
                                                                Log.d(TAG, "User konnte nicht hinzugefuegt werden Firestore");
                                                                registrierenProgressBar.setVisibility(GONE);
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                                }
                            });
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                registrierenProgressBar.setVisibility(GONE);
                                emailEditText.setError("Deine Email ist nicht valide oder ist bereits vergeben.");
                                emailEditText.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                registrierenProgressBar.setVisibility(GONE);
                                emailEditText.setError("Es gibt breits einen User mit dieser E-Mail. Wähle eine andere.");
                                emailEditText.requestFocus();
                            } catch (Exception e) {
                                Toast.makeText(getView().getContext(), "Du konntest nicht Registriert werden :( Versuche es noch einmal!", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "User konnte nicht hinzugefuegt werden Firestore");
                                registrierenProgressBar.setVisibility(GONE);
                            }
                        }


                    }
                });

    }

}