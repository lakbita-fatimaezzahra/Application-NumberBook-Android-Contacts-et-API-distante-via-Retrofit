package com.example.numberbook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnImport;
    private Button btnUpload;
    private Button btnFind;

    private EditText inputSearch;

    private RecyclerView recyclerNumbers;

    private PhoneListAdapter phoneAdapter;

    private List<PersonData> numberCollection = new ArrayList<>();

    private PhoneService phoneService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnImport = findViewById(R.id.btnLoadContacts);
        btnUpload = findViewById(R.id.btnSyncContacts);
        btnFind = findViewById(R.id.btnSearch);

        inputSearch = findViewById(R.id.etKeyword);

        recyclerNumbers = findViewById(R.id.recyclerViewContacts);

        recyclerNumbers.setLayoutManager(
                new LinearLayoutManager(this)
        );

        phoneAdapter = new PhoneListAdapter(numberCollection);

        recyclerNumbers.setAdapter(phoneAdapter);

        phoneService = ApiManager
                .buildConnection()
                .create(PhoneService.class);

        retrieveServerContacts();

        btnImport.setOnClickListener(v -> verifyPermission());

        btnUpload.setOnClickListener(v -> uploadContacts());

        btnFind.setOnClickListener(v -> executeSearch());
    }

    private void retrieveServerContacts() {

        phoneService.fetchStoredNumbers()
                .enqueue(new Callback<List<PersonData>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<PersonData>> call,
                            @NonNull Response<List<PersonData>> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            numberCollection.clear();

                            numberCollection.addAll(response.body());

                            phoneAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<PersonData>> call,
                            @NonNull Throwable t
                    ) {

                        Log.e("SERVER_FAIL", t.getMessage());

                        Toast.makeText(
                                MainActivity.this,
                                "Connexion impossible",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void verifyPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED) {

            importDeviceContacts();

        } else {

            permissionLauncher.launch(
                    Manifest.permission.READ_CONTACTS
            );
        }
    }

    private final ActivityResultLauncher<String>
            permissionLauncher =

            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),

                    granted -> {

                        if (granted) {

                            importDeviceContacts();
                        }
                    });

    private void importDeviceContacts() {

        numberCollection.clear();

        Cursor dataCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (dataCursor != null) {

            while (dataCursor.moveToNext()) {

                String personName =
                        dataCursor.getString(
                                dataCursor.getColumnIndexOrThrow(
                                        ContactsContract
                                                .CommonDataKinds
                                                .Phone
                                                .DISPLAY_NAME
                                )
                        );

                String personPhone =
                        dataCursor.getString(
                                dataCursor.getColumnIndexOrThrow(
                                        ContactsContract
                                                .CommonDataKinds
                                                .Phone
                                                .NUMBER
                                )
                        );

                numberCollection.add(
                        new PersonData(personName, personPhone)
                );
            }

            dataCursor.close();
        }

        phoneAdapter.notifyDataSetChanged();

        Toast.makeText(
                this,
                "Contacts importés",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void uploadContacts() {

        for (PersonData item : numberCollection) {

            phoneService.saveNumber(item)
                    .enqueue(new Callback<ServerReply>() {

                        @Override
                        public void onResponse(
                                Call<ServerReply> call,
                                Response<ServerReply> response
                        ) {
                        }

                        @Override
                        public void onFailure(
                                Call<ServerReply> call,
                                Throwable t
                        ) {
                        }
                    });
        }

        Toast.makeText(
                this,
                "Envoi terminé",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void executeSearch() {

        String enteredKeyword =
                inputSearch.getText().toString().trim();

        phoneService.findNumber(enteredKeyword)
                .enqueue(new Callback<List<PersonData>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<PersonData>> call,
                            @NonNull Response<List<PersonData>> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            numberCollection.clear();

                            numberCollection.addAll(response.body());

                            phoneAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<PersonData>> call,
                            @NonNull Throwable t
                    ) {

                        Toast.makeText(
                                MainActivity.this,
                                "Recherche impossible",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}