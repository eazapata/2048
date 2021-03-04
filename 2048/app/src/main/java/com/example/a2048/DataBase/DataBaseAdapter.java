package com.example.a2048.DataBase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2048.MainActivity;
import com.example.a2048.R;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.scoreView> {

    List<Score> scoreList = new ArrayList<>();
    private Context context;
    private DataBaseHelper dataBaseHelper;

    public DataBaseAdapter(List<Score> scoreList, Context context, DataBaseHelper dataBaseHelper) {
        this.scoreList = scoreList;
        this.context = context;
        this.dataBaseHelper = dataBaseHelper;
    }

    public class scoreView extends RecyclerView.ViewHolder {
        private TextView playerCardview, scoreCardview, countryCardview;
        private ImageView btnEdit, btnDelete;

        public scoreView(@NonNull View itemView) {
            super(itemView);

            playerCardview = (TextView) itemView.findViewById(R.id.player_cardview);
            scoreCardview = (TextView) itemView.findViewById(R.id.score_cardview);
            countryCardview = (TextView) itemView.findViewById(R.id.country_cardview);

            btnEdit = (ImageView) itemView.findViewById(R.id.imageEdit);
            btnDelete = (ImageView) itemView.findViewById(R.id.imageDelete);
        }
    }

    @NonNull
    @Override
    public scoreView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,null,false);
        return new scoreView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull scoreView holder, int position) {
        final Score score = scoreList.get(position);
        holder.playerCardview.setText(score.getPlayer());
        holder.countryCardview.setText(score.getCountry());
        holder.scoreCardview.setText(String.valueOf(score.getPlayerScore()));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.delete(score.getId());
                scoreList.remove(score);
                notifyDataSetChanged();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("PLAYER SCORE",score.getPlayerScore());
                intent.putExtra("PLAYER NAME",score.getPlayer());
                intent.putExtra("PLAYER Country",score.getCountry());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }


}
