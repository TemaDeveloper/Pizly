package com_pizly.java_pizly.pizly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.models.Person;
import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    private List<Person> people;
    private Context context;

    public PeopleAdapter(List<Person> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        holder.nameText.setText(people.get(position).getName());
        holder.personImage.setImageResource(people.get(position).getUserImage());
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private ImageView personImage;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.text_view_name);
            personImage = itemView.findViewById(R.id.image_view_person);

        }
    }
}
