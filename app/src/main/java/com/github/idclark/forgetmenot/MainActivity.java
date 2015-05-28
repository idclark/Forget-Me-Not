package com.github.idclark.forgetmenot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        Activity mActivity;
        RecyclerView mRecyclerView;
        TaskAdapter taskAdapter;
        AddFloatingActionButton mFabView;

        public PlaceholderFragment() {
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            this.mActivity = (Activity) activity;
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
            mFabView = (AddFloatingActionButton) rootView.findViewById(R.id.normal_plus);

            mFabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent createTaskIntent = new Intent(v.getContext(), EditActivity.class);
                    startActivity(createTaskIntent);
                }
            });
            taskAdapter = new TaskAdapter(createList(5));
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mRecyclerView.setAdapter(taskAdapter);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            taskAdapter.SetOnItemClickListener(new TaskAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View v, int position) {
                    Intent detailIntent = new Intent(v.getContext(), DetailActivity.class);
                    startActivity(detailIntent);

                }
            });

        }

        private List<Task> createList(int size) {
            List<Task> result = new ArrayList<>();
            for (int i = 1; i<=size; i++) {
                Task task = new Task();
                task.title = Task.TITLE_PREFIX + i;
                task.updated = Task.UPDATED_PREFIX +i;
                task.notes = task.NOTES_PREFIX + i;

                result.add(task);

            }
            return result;
        }
    }
}
