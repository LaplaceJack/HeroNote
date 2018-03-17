package com.example.heronote.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heronote.activity.DetailActivity;
import com.example.heronote.R;
import com.example.heronote.base.BaseApplication;
import com.example.heronote.bean.Note;
import com.example.heronote.db.NoteDbOperate;
import com.example.heronote.util.Utils;

import java.util.List;

/**
 * Created by Jack on 2018/2/14.
 */

public class NoteBriefAdapter extends RecyclerView.Adapter<NoteBriefAdapter.ViewHolder> {

    private List<Note> noteList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView day_of_month;
        TextView month_year;
        TextView day_of_week;
        TextView time;
        ImageView cover;
        TextView quote;
        Button more;
        View box_top;
        View box_bottom;

        private ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.card_view);
            day_of_month = view.findViewById(R.id.day_of_month);
            month_year = view.findViewById(R.id.month_year);
            day_of_week = view.findViewById(R.id.day_of_week);
            time = view.findViewById(R.id.time);
            cover = view.findViewById(R.id.cover);
            quote = view.findViewById(R.id.quote);
            more = view.findViewById(R.id.button_more);
            box_top = view.findViewById(R.id.box_top);
            box_bottom = view.findViewById(R.id.box_bottom);
        }
    }

    public NoteBriefAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.note_brief_card, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(BaseApplication.getContext(), DetailActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("note_data", noteList.get(holder.getAdapterPosition()-1));
//                BaseApplication.getContext().startActivity(intent);
                Utils.goToActivity(DetailActivity.class, "note_data", noteList.get(holder.getAdapterPosition()));
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(parent.getContext(), view);
                popupMenu.inflate(R.menu.card_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.collect:
                                Utils.snackbar(view, "已收藏", "撤销", Utils.listenerToToast("已撤销"));
                                return true;
                            case R.id.delete:
                                final NoteDbOperate operator = new NoteDbOperate();
                                final int i = holder.getAdapterPosition();
                                final Note note = noteList.get(i);
                                Utils.snackbar(view, "已删除", "撤销", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Utils.toast("已撤销");
                                        noteList.add(i, note);
                                        notifyDataSetChanged();
                                        operator.insertNote(note);
                                    }
                                });
                                noteList.remove(note);
                                notifyDataSetChanged();
                                operator.deleteNote(note.getTimeMillis());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Note note = noteList.get(position);
        holder.day_of_month.setText(note.formatDate("dd"));
        holder.month_year.setText(note.formatDate("MM-yyyy"));
        holder.day_of_week.setText(note.formatDate("EEE"));
        holder.time.setText(note.formatDate("HH:mm"));
        holder.quote.setText(note.getQuote());
        if (position == 0) {
            holder.box_top.setVisibility(View.VISIBLE);
        } else if (position == noteList.size() - 1) {
            holder.box_bottom.setVisibility(View.VISIBLE);
        }
        Utils.glide(note.getCoverPicPath(), holder.cover);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
