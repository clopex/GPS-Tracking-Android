package tiimiss.globalgps.ba.tiimiss.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tiimiss.globalgps.ba.tiimiss.Listeners.TasksItemClickListener;
import tiimiss.globalgps.ba.tiimiss.R;

/**
 * Created by adismulabdic on 6/29/16.
 */
public class TasksReclyerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView taskName;
    private TasksItemClickListener clickListener;

    public TasksReclyerHolder(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        taskName = (TextView)itemView.findViewById(R.id.tasksname);
    }

    public void setClickListener(TasksItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v, getAdapterPosition());
    }
}
