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

import com.example.a2048.R;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.scoreView> {

    private List<Score> scoreList = new ArrayList<>();
    private Context context;
    private DataBaseHelper dataBaseHelper;

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public DataBaseAdapter(Context context, DataBaseHelper dataBaseHelper) {
        this.context = context;
        this.dataBaseHelper = dataBaseHelper;
    }

    public class scoreView extends RecyclerView.ViewHolder {
        private TextView playerCardview, scoreCardview, durationCardview;
        private ImageView btnEdit, btnDelete;

        public scoreView(@NonNull View itemView) {
            super(itemView);

            playerCardview = (TextView) itemView.findViewById(R.id.player_cardview);
            scoreCardview = (TextView) itemView.findViewById(R.id.score_cardview);
            durationCardview = (TextView) itemView.findViewById(R.id.duration_cardview);
            btnEdit = (ImageView) itemView.findViewById(R.id.imageEdit);
            btnDelete = (ImageView) itemView.findViewById(R.id.imageDelete);
        }
    }

    @NonNull
    @Override
    public scoreView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, null, false);
        return new scoreView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull scoreView holder, int position) {
            final Score score = scoreList.get(position);
            holder.playerCardview.setText(score.getPlayer());
            holder.durationCardview.setText(score.getTime());
            holder.scoreCardview.setText(String.valueOf(score.getPlayerScore()));
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBaseHelper.delete(score);
                    scoreList.remove(score);
                    notifyDataSetChanged();
                }
            });
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("KEY ID", score.getId());
                    intent.putExtra("PLAYER SCORE", score.getPlayerScore());
                    intent.putExtra("PLAYER NAME", score.getPlayer());
                    intent.putExtra("PLAYER COUNTRY", score.getTime());
                    context.startActivity(intent);

                }
            });
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }
}
