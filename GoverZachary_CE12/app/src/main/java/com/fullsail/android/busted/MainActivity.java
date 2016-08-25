// Zachary Gover
// JAV1 - 1608
// MainActivity

package com.fullsail.android.busted;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;
import com.fullsail.android.busted.net.GetDetailsTask;
import com.fullsail.android.busted.net.GetMembersTask;
import com.fullsail.android.busted.net.NetworkUtils;
import com.fullsail.android.busted.object.Member;

import static android.R.id.progress;

public class MainActivity extends Activity {

	private View mMembersListScreen;
	private View mMemberDetailsScreen;
	private ListView lv;
	protected ProgressDialog progress;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.activity_main);

		progress = new ProgressDialog(this);
		progress.setTitle("Please Wait...");
		progress.setMessage("Fetching Results...");

		mMembersListScreen = findViewById(R.id.members_list_screen);
		mMemberDetailsScreen = findViewById(R.id.member_details_screen);
		lv = (ListView) mMembersListScreen.findViewById(R.id.members_list);
		lv.setOnItemClickListener(mItemClickListener);

		if (NetworkUtils.isConnected(this)) {
			GetMembersTask task = new GetMembersTask(this);
			task.execute();
		} else {
			showToast("Not connected to a network");
		}
	}

	public void showMembersListScreen(ArrayList<Member> _members) {
		mMemberDetailsScreen.setVisibility(View.GONE);
		mMembersListScreen.setVisibility(View.VISIBLE);

		lv.setAdapter(new MembersAdapter(this, _members));
	}

	public void showMemberDetailsScreen(int _id) {
		if (NetworkUtils.isConnected(this)) {
			mMembersListScreen.setVisibility(View.GONE);
			mMemberDetailsScreen.setVisibility(View.VISIBLE);

			GetDetailsTask task = new GetDetailsTask(this);
			task.execute(_id);
		} else {
			showToast("Not connected to a network");
		}
	}

	/**
	 * Populate the member details screen with data.
	 *
	 * @param _name
	 * @param _birthday
	 * @param _gender
	 * @param _twitterId
	 * @param _numCommittees
	 * @param _numRoles
	 */
	public void populateMemberDetailsScreen(String _name, String _birthday, String _gender,
			String _twitterId, String _numCommittees, String _numRoles) {

		TextView tv = (TextView) mMemberDetailsScreen.findViewById(R.id.text_name);
		tv.setText(_name);

		tv = (TextView) mMemberDetailsScreen.findViewById(R.id.text_birthday);
		tv.setText(_birthday);

		tv = (TextView) mMemberDetailsScreen.findViewById(R.id.text_gender);
		tv.setText(_gender);

		tv = (TextView) mMemberDetailsScreen.findViewById(R.id.text_twitter_id);
		tv.setText(_twitterId);

		tv = (TextView) mMemberDetailsScreen.findViewById(R.id.text_num_committees);
		tv.setText(_numCommittees);

		tv = (TextView) mMemberDetailsScreen.findViewById(R.id.text_num_roles);
		tv.setText(_numRoles);
	}

	OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
			int itemId = ((Member) lv.getAdapter().getItem(_position)).getId();
			showMemberDetailsScreen(itemId);
		}

	};

	public void onBackPressed() {
		if(mMemberDetailsScreen.getVisibility() == View.VISIBLE) {
			mMemberDetailsScreen.setVisibility(View.GONE);
			mMembersListScreen.setVisibility(View.VISIBLE);
		} else {
			super.onBackPressed();
		}
	}

	public void showToast(String message) {
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		toast.show();
	}

	public void showProgress() {
		this.progress.show();
	}

	public void hideProgress() {
		this.progress.hide();
	}
}
