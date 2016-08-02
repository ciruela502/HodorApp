package pl.droidsonroids.hodor.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.hodor.HodorApplication;
import pl.droidsonroids.hodor.R;
import pl.droidsonroids.hodor.model.User;
import pl.droidsonroids.hodor.retrofit.RestAdapter;
import pl.droidsonroids.hodor.util.DatabaseHelper;

public class UsersListViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "UserListHolder";
    @BindView(R.id.text_view_username)
    TextView mTextViewUsername;

    private DatabaseHelper mDatabaseHelper;
    private RestAdapter mRestAdapter;

    public UsersListViewHolder(final View itemView, final DatabaseHelper databaseHelper) {
        super(itemView);
        mDatabaseHelper = databaseHelper;
        mRestAdapter = HodorApplication.getInstance().getRestAdapter();
        ButterKnife.bind(this, itemView);
    }

    public void bindData(final String username, final int backgroundColorRes) {
        mTextViewUsername.setText(username);
        mTextViewUsername.setBackgroundColor(ContextCompat.getColor(mTextViewUsername.getContext(),
                backgroundColorRes));

        mDatabaseHelper.getUserFromDatabase(username, new DatabaseHelper.OnUserReceivedListener() {
            @Override
            public void onUserReceived(User friend) {
                mTextViewUsername.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRestAdapter.sendPush(friend.getToken());
                    }
                });
            }
        });

    }
}

