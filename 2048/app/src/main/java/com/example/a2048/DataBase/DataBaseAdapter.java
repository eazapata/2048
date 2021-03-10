package com.example.a2048.DataBase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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

            this.playerCardview = (TextView) itemView.findViewById(R.id.player_cardview);
            this.scoreCardview = (TextView) itemView.findViewById(R.id.score_cardview);
            this.durationCardview = (TextView) itemView.findViewById(R.id.duration_cardview);
            this.btnEdit = (ImageView) itemView.findViewById(R.id.imageEdit);
            this.btnDelete = (ImageView) itemView.findViewById(R.id.imageDelete);
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
               confirmDelete(holder);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("KEY ID", score.getId());
                intent.putExtra("PLAYER SCORE", score.getPlayerScore());
                intent.putExtra("PLAYER NAME", score.getPlayer());
                intent.putExtra("PLAYER TIME", score.getTime());
                context.startActivity(intent);

            }
        });
    }

    private void confirmDelete(RecyclerView.ViewHolder viewHolder) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_score);
        dialog.setCanceledOnTouchOutside(false);
        Button yes = dialog.findViewById(R.id.yes_remove);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreList.get(viewHolder.getAdapterPosition());
                dataBaseHelper.delete(scoreList.get(viewHolder.getAdapterPosition()));
                scoreList.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        Button no = dialog.findViewById(R.id.no_remove);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

}
