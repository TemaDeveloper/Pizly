package com_pizly.java_pizly.pizly;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import com_pizly.java_pizly.pizly.adapters.SearchAdapter;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.models.BannedUser;
import com_pizly.java_pizly.pizly.models.ItemSearcher;
import com_pizly.java_pizly.pizly.models.Party;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import com_pizly.java_pizly.pizly.ui.eventAddition.AddtitionActivity;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.ui.home.BottomSettingsFragment;
import com_pizly.java_pizly.pizly.ui.home.HomeFragment;
import com_pizly.java_pizly.pizly.ui.home.QRCodeBottomSheetFragment;
import com_pizly.java_pizly.pizly.ui.map.MapFragment;
import com_pizly.java_pizly.pizly.ui.activeTckets.ActiveTicketsFragment;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TextAnimator, View.OnClickListener, SearchView.OnQueryTextListener {

    //----widgets----//
    private SmoothBottomBar bottomBar;
    private TypeTextWriterTextView titleText;
    private SearchView searchView;
    private ImageView imageViewSettings, imageViewQRCode;
    private RecyclerView searchRecyclerView;
    //----USER ID----//
    public static int id;
    //----Searcher----//
    private List<ItemSearcher> list;
    private SearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        checkingNetworkConnection();
        animText();

        imageViewQRCode.setOnClickListener(this);
        imageViewSettings.setOnClickListener(this);

        //----when the searcher is focused----//
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    titleText.setVisibility(View.GONE);
                    searchRecyclerView.setVisibility(View.VISIBLE);
                    bottomBar.setVisibility(View.GONE);
                } else {
                    titleText.setVisibility(View.VISIBLE);
                    searchRecyclerView.setVisibility(View.GONE);
                    bottomBar.setVisibility(View.VISIBLE);
                }
            }
        });

        //----when the searcher text changed----//
        searchView.setOnQueryTextListener(this);


        replace(new MapFragment());
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        replace(new MapFragment());
                        titleText.displayTextWithAnimation("Map");
                        imageViewQRCode.setVisibility(View.GONE);
                        break;
                    case 1:
                        replace(new ActiveTicketsFragment());
                        titleText.displayTextWithAnimation("Tickets");
                        imageViewQRCode.setVisibility(View.GONE);
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, AddtitionActivity.class));
                    case 3:
                        replace(new HomeFragment());
                        ApiInterface apiInterface = ApiClient.getApiService();
                        Call<Person> callUserGetter = apiInterface.getUser(id);
                        callUserGetter.enqueue(new Callback<Person>() {
                            @Override
                            public void onResponse(Call<Person> call, Response<Person> response) {
                                titleText.displayTextWithAnimation(response.body().getName());
                            }

                            @Override
                            public void onFailure(Call<Person> call, Throwable t) {

                            }
                        });

                        imageViewQRCode.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });

        changeTheme();

    }

    private void changeTheme() {
        if (SharedPrefManager.getInstance(getApplicationContext()).getMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    private void buildRecyclerView() {

        list = new ArrayList<>();

        list.add(new ItemSearcher(2, "Restaurant", false, R.drawable.img_ticket));
        list.add(new ItemSearcher(2, "Night With Sava", false, R.drawable.img_ticket));
        list.add(new ItemSearcher(2, "Night Club", false, R.drawable.img_ticket));

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(manager);

        adapter = new SearchAdapter(list, getApplicationContext(), getSupportFragmentManager(), MainActivity.this);
        searchRecyclerView.setAdapter(adapter);

    }

    private void filter(String text) {
        //----create new List----//
        List<ItemSearcher> filteredList = new ArrayList<ItemSearcher>();
        for (ItemSearcher item : list) {
            //----if the text that user set contain the info of the name of user----//
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                //----add the item to list----//
                filteredList.add(item);
            }
        }
        if (!filteredList.isEmpty()) {
            adapter.filterList(filteredList);
        }
    }

    private void init() {
        //----initialize widgets----//
        bottomBar = findViewById(R.id.nav_view);
        titleText = findViewById(R.id.text_view_title);
        searchView = findViewById(R.id.search_view);
        imageViewSettings = findViewById(R.id.image_view_settings);
        searchRecyclerView = findViewById(R.id.recycler_view_search);
        imageViewQRCode = findViewById(R.id.image_view_qr_code);
        //----get id from SharedPreferences----//
        id = SharedPrefManager.getInstance(MainActivity.this).getKeyID();
    }

    private void replace(Fragment fragment) {
        //----replacing fragments in MainActivity----//
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void animText() {
        titleText.setText("");
        titleText.setCharacterDelay(120);
        titleText.displayTextWithAnimation("Map");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_settings:
                //----open settings bottom fragment----//
                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSettingsFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "SettingsFragment");
                break;
            case R.id.image_view_qr_code:
                //----open QRcode bottom fragment----//
                QRCodeBottomSheetFragment qrCodeBottomSheetFragment = new QRCodeBottomSheetFragment();
                qrCodeBottomSheetFragment.show(getSupportFragmentManager(), qrCodeBottomSheetFragment.getTag());
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        buildRecyclerView();

        ApiInterface apiInterface = ApiClient.getApiService();
        Call<List<Person>> peopleCall = apiInterface.getAllUsers(id);
        peopleCall.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                for (Person person : response.body()) {
                    //----get users and load it to list----//
                    list.add(new ItemSearcher(0, person.getImage(), person.getName(), person.getId()));
                    //----filter users getting the text from searcher----//
                    filter(newText);
                    searchRecyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {

            }
        });

        Call<List<Party>> partiesCall = apiInterface.getParties(id);
        partiesCall.enqueue(new Callback<List<Party>>() {
            @Override
            public void onResponse(Call<List<Party>> call, Response<List<Party>> response) {
                for (Party party : response.body()) {
                    //----get parties images and load it to list----//
                    list.add(new ItemSearcher(1, party.getImageParty(), party.getTitle()));
                    //----add loaded list to searcher----//
                    filter(newText);
                    searchRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Party>> call, Throwable t) {

            }
        });


        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void checkingNetworkConnection(){
        if(!isNetworkAvailable()){
            final Dialog dialog = new Dialog(MainActivity.this, R.style.ThemeDialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_internet_connection);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            MaterialButton btnExit = dialog.findViewById(R.id.btn_exit), btnSettings = dialog.findViewById(R.id.btn_settings);

            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveTaskToBack(true);
                    finish();
                    System.exit(0);
                    dialog.cancel();
                }
            });

            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });

            dialog.show();
        }
    }

}