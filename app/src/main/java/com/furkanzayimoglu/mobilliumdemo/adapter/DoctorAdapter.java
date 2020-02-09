package com.furkanzayimoglu.mobilliumdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.furkanzayimoglu.mobilliumdemo.model.DoctorModel;
import com.furkanzayimoglu.mobilliumdemo.utils.OnItemClickListener;
import com.furkanzayimoglu.mobilliumdemo.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> implements Filterable {

    private List<DoctorModel> doctorList;
    private List<DoctorModel> filterDoctorList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener listener;
    private Context context;


    public DoctorAdapter (Context context, ArrayList<DoctorModel> arrayList, OnItemClickListener listener){
       this.doctorList = arrayList;
       this.filterDoctorList = new ArrayList<>(arrayList);
       this.layoutInflater = LayoutInflater.from(context);
       this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycleritemdesign,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameText.setText(doctorList.get(position).getFull_name());
        Glide.with(context).load(doctorList.get(position).getImage().getUrl())
                .apply(RequestOptions.circleCropTransform()).into(holder.imagePhoto);
        }
        // ilk başta picasso kullanmıştım lakin resimler yuvarlak olacağından dolayı ve resimleri circle yapma işleminde
        // kolaylık olması açısından glide kütüphanesini kullandım.

    @Override
    public int getItemCount() {
        return doctorList.size();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DoctorModel> list = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                list.addAll(filterDoctorList);
            } else {
                String filterPatern = constraint.toString().toLowerCase().trim();

                for (DoctorModel doctorModel : filterDoctorList) {
                    // search for user name
                    if (doctorModel.getFull_name().toLowerCase().contains(filterPatern)) {
                        list.add(doctorModel);
                    }

                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = list;
            filterResults.count = list.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

                doctorList.clear();
                doctorList.addAll((List) results.values);
                notifyDataSetChanged();
        }
    };


    public void setClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameText;
        ImageView imagePhoto;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            imagePhoto = itemView.findViewById(R.id.photos);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(doctorList.get(getAdapterPosition()));
        }
    }

}

