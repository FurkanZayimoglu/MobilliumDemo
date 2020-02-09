package com.furkanzayimoglu.mobilliumdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.furkanzayimoglu.mobilliumdemo.R;
import com.furkanzayimoglu.mobilliumdemo.activity.FreeActivity;
import com.furkanzayimoglu.mobilliumdemo.activity.PremiumActivity;
import com.furkanzayimoglu.mobilliumdemo.adapter.DoctorAdapter;
import com.furkanzayimoglu.mobilliumdemo.model.DoctorModel;
import com.furkanzayimoglu.mobilliumdemo.model.ResponseModel;
import com.furkanzayimoglu.mobilliumdemo.network.DoctorApi;
import com.furkanzayimoglu.mobilliumdemo.network.DoctorApiService;
import com.furkanzayimoglu.mobilliumdemo.utils.ItemDivider;
import com.furkanzayimoglu.mobilliumdemo.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private DoctorApiService doctorApiService;
    private ArrayList<DoctorModel> doctorlist;
    public RecyclerView recyclerView;
    public DoctorAdapter adapter;
    public SearchView searchView;
    public CheckBox checkWoman;
    public CheckBox checkMan;
    View noUserFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noUserFound = findViewById(R.id.noFoundUser);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        doctorApiService = DoctorApi.getRetrofitInstance().create(DoctorApiService.class);
        doctorlist = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        checkWoman = findViewById(R.id.checkWoman);
        checkMan = findViewById(R.id.checkMan);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new ItemDivider(this));
        searchView = findViewById(R.id.search);
        getResponseDoctors();
        search();
        onCheckboxClicked();


    }

    private void onCheckboxClicked() {

        checkMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkWoman.setChecked(false);
                    ArrayList<DoctorModel> manList = new ArrayList<>();
                    for (DoctorModel genderMan : doctorlist) {
                        if (genderMan.getGender().equals("male")) {
                            manList.add(genderMan);
                            setAdapter(manList);
                        }

                    }
                }else{
                    setAdapter(doctorlist);
                }
            }
        });
        checkWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkMan.setChecked(false);
                    ArrayList<DoctorModel> womanList = new ArrayList<>();
                    for (DoctorModel genderMan : doctorlist) {
                        if (genderMan.getGender().equals("female")) {
                            womanList.add(genderMan);
                            setAdapter(womanList);
                        }

                    }
                }else{
                    setAdapter(doctorlist);
                }
            }
        });
    }

    public void search() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                adapter.getFilter().filter(query);
                if (doctorlist.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    noUserFound.setVisibility(View.VISIBLE);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }

        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                noUserFound.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    private void setAdapter(ArrayList<DoctorModel> arrayList){
        adapter = new DoctorAdapter(this,arrayList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setClickListener(this);

    }

    private void getResponseDoctors() {

        Call<ResponseModel> call = doctorApiService.getDoctors();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        ResponseModel responseModel = response.body();
                        Log.i(" verileeeerrrr", responseModel.getDoctors().get(0).getFull_name()); // verilerin geldiği onaylandı .
                        doctorlist = responseModel.getDoctors();
                        Log.i(" listem : ", doctorlist.toString());
                        setAdapter(doctorlist);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(DoctorModel model) {
        if (model.getUser_status().equals("premium")){
            Intent intent = new Intent(this, PremiumActivity.class);
            intent.putExtra("name",model.getFull_name());
            intent.putExtra("image",model.getImage().getUrl());
            intent.putExtra("status",model.getUser_status());
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, FreeActivity.class);
            intent.putExtra("name",model.getFull_name());
            intent.putExtra("image",model.getImage().getUrl());
            intent.putExtra("status",model.getUser_status());
            startActivity(intent);
        }
    }
}
