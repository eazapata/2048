package com.example.a2048.DataBase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2048.R;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.scoreView> {

    private List<Score> scoreList = new ArrayList<>();
    private Context context;

    public DataBaseAdapter(List<Score> scoreList, Context context) {
        this.scoreList = scoreList;
        this.context = context;
    }

    public class scoreView extends RecyclerView.ViewHolder {
        private TextView playerCardview, scoreCardview, countryCardview;
        private Button btnEditar, btnElminar;

        public scoreView(@NonNull View itemView) {
            super(itemView);
            playerCardview = (TextView) itemView.findViewById(R.id.player_cardview);
            scoreCardview = (TextView) itemView.findViewById(R.id.score_cardview);
            //countryCardview = (TextView) itemView.findViewById(R.id.country_cardview);
            //btnEditar = (Button) itemView.findViewById(R.id.btnEditar);
            //btnElminar = (Button) itemView.findViewById(R.id.btnEliminar);
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
        Score score = scoreList.get(position);
        holder.playerCardview.setText(score.getPlayer());
        holder.scoreCardview.setText(String.valueOf(score.getPlayerScore()));
        //holder.countryCardview.setText(score.getCountry());

    }

    @Override
    public int getItemCount() {

        return scoreList.size();
    }


}
