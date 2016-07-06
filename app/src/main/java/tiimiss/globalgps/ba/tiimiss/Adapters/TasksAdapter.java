package tiimiss.globalgps.ba.tiimiss.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import tiimiss.globalgps.ba.tiimiss.Activity.DashboardActivity;
import tiimiss.globalgps.ba.tiimiss.Fragments.TaskDetailFragment;
import tiimiss.globalgps.ba.tiimiss.Holders.TasksReclyerHolder;
import tiimiss.globalgps.ba.tiimiss.Listeners.TasksItemClickListener;
import tiimiss.globalgps.ba.tiimiss.R;
import tiimiss.globalgps.ba.tiimiss.Utillities.ItemTasks;

/**
 * Created by adismulabdic on 7/3/16.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksReclyerHolder> {

    private List<ItemTasks> itemsList;
    private Context context;
    String fromLatitude, fromLongitude, toLatitude, toLongituted, taskDescription;

    public TasksAdapter(Context context, List<ItemTasks> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public TasksReclyerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, null);
        TasksReclyerHolder holder = new TasksReclyerHolder(layoutView);

        return holder;
    }

    @Override
    public void onBindViewHolder(TasksReclyerHolder holder, int position) {

        holder.taskName.setText("Task Name: "+itemsList.get(position).getTaskName());
        fromLatitude = itemsList.get(position).getDestFromLat();
        fromLongitude = itemsList.get(position).getDestFromLng();
        toLatitude = itemsList.get(position).getDestToLat();
        toLongituted = itemsList.get(position).getDestToLng();
        taskDescription = itemsList.get(position).getTaskdesc();

        holder.setClickListener(new TasksItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, DashboardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("taskName", itemsList.get(position).getTaskName());
                bundle.putString("descTask", itemsList.get(position).getTaskdesc());
                bundle.putString("fromLat", itemsList.get(position).getDestFromLat());
                bundle.putString("fromLng", itemsList.get(position).getDestFromLng());
                bundle.putString("toLat", itemsList.get(position).getDestToLat());
                bundle.putString("toLng", itemsList.get(position).getDestToLng());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.itemsList.size();
    }
}
