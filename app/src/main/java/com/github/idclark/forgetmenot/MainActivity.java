package com.github.idclark.forgetmenot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.github.idclark.forgetmenot.data.TaskTableController;


public class MainActivity extends AppCompatActivity {

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

        Toolbar toolbar;
        Activity mActivity;
        RecyclerView mRecyclerView;
        TaskAdapter taskAdapter;
        AddFloatingActionButton mFabView;
        TextView m_ID;

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
            toolbar = (Toolbar) rootView.findViewById(R.id.tool_bar);
            ((MainActivity) getActivity()).setSupportActionBar(toolbar);

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
            mFabView = (AddFloatingActionButton) rootView.findViewById(R.id.normal_plus);

            mFabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent createTaskIntent = new Intent(v.getContext(), EditActivity.class);
                    startActivity(createTaskIntent);
                }
            });

            taskAdapter = new TaskAdapter(new TaskTableController(getActivity()).getAllTasksForUser(), getActivity());
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
                    m_ID = (TextView) v.findViewById(R.id._task_id);
                    Intent detailIntent = new Intent(v.getContext(), DetailActivity.class);
                    detailIntent.putExtra("TASK_ID", m_ID.getText().toString());
                    startActivity(detailIntent);
                }
            });

            ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(taskAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);
        }

    }
}
