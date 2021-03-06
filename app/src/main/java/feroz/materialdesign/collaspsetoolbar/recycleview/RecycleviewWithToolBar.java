package feroz.materialdesign.collaspsetoolbar.recycleview;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.feroz.materialdesign.R;

import java.util.ArrayList;
import java.util.List;


public class RecycleviewWithToolBar extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    RoleDetailAdapter roleVerticalRecyclerViewAdapter;
    RecyclerView verticalRecycler;
    private List<Role> roles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_with_tool_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorAccent));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.android_light_red));
        toolbarTextAppernce();
        verticalRecycler = (RecyclerView) findViewById(R.id.rv_role_vertical);
        roles = new ArrayList<>();

        //role constructor => (int imageResID, String title, String subtitle, int totalItems, int completedItems)
        for (int i = 0; i < 30; i++) {

            Role role = new Role(R.drawable.ic_action_new, "Game Designer" + i, "User Interface Developer", 247, 31);
            roles.add(role);

        }
        roleVerticalRecyclerViewAdapter = new RoleDetailAdapter(roles);

        verticalRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        verticalRecycler.setLayoutManager(linearLayoutManager);

        verticalRecycler.setItemAnimator(new DefaultItemAnimator());
        verticalRecycler.setAdapter(roleVerticalRecyclerViewAdapter);
        verticalRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getBaseContext(), verticalRecycler, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        System.out.println("Vposition: " + position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }


}
