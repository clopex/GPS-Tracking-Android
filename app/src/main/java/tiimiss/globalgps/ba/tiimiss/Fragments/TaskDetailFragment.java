package tiimiss.globalgps.ba.tiimiss.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tiimiss.globalgps.ba.tiimiss.R;

/**
 * Created by adismulabdic on 7/4/16.
 */
public class TaskDetailFragment extends Fragment{

    public TaskDetailFragment(){}

    public static TaskDetailFragment newInstance(){
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        return taskDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_detail_layout, container, false);

        return view;
    }
}
