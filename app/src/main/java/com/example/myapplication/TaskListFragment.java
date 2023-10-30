package com.example.myapplication;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.UUID;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.TextView;
import android.content.Intent;
import com.example.myapplication.TaskStorage;




public class TaskListFragment extends Fragment {
//wyswietlanie listy
    public RecyclerView recyclerView;
    //laczy dane z listy z widokiem adaptera
    public TaskAdapter adapter;
    public static final String KEY_EXTRA_TASK_ID = "task_id";

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView dateTextView;
        private Task task;
///konstruktor inicjalizujazy widoki dla pojedynczego elelmentu listy
        public TaskHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);
            nameTextView = itemView.findViewById(R.id.task_item_name);
            dateTextView = itemView.findViewById(R.id.task_item_date);
        }
        public void bind(Task task){
            this.task = task;
            nameTextView.setText(task.getName());
            //formatowanie daty
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd.MM.yyyy");
            String formattedDate = dateFormat.format(task.getDate());
            dateTextView.setText(formattedDate);
            //dateTextView.setText(task.getDate().toString());
        }

        ///klikniecie w element listy
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);
        }
    }
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> tasks;
        public TaskAdapter(List<Task> tasks)
        {
            this.tasks = tasks;
        }
        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position){
            Task task = tasks.get(position);
            holder.bind(task);

        }
        public int getItemCount(){
            return tasks.size();
        }
    }

    private void updateView() {
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();
        if(adapter == null) {
            adapter = new TaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }
}
