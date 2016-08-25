// Zachary Gover
// JAV1 - 1608
// MainActivity

package com.fullsail.android.busted;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fullsail.android.busted.net.GetDetailsTask;
import com.fullsail.android.busted.net.GetMembersTask;
import com.fullsail.android.busted.object.Member;

public class MainActivity extends Activity {

	private View mMembersListScreen;
	private View mMemberDetailsScreen;
	private ListView lv;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.activity_main);

		mMembersListScreen = findViewById(R.id.members_list_screen);
		mMemberDetailsScreen = findViewById(R.id.member_details_screen);
		lv = (ListView) mMembersListScreen.findViewById(R.id.members_list);
		lv.setOnItemClickListener(mItemClickListener);

		GetMembersTask task = new GetMembersTask(this);
		task.execute();
	}

	public void showMembersListScreen(ArrayList<Member> _members) {
		mMemberDetailsScreen.setVisibility(View.GONE);
		mMembersListScreen.setVisibility(View.VISIBLE);

		lv.setAdapter(new MembersAdapter(this, _members));
	}

	public void showMemberDetailsScreen(int _id) {
		mMembersListScreen.setVisibility(View.GONE);
		mMemberDetailsScreen.setVisibility(View.VISIBLE);

		GetDetailsTask task = new GetDetailsTask(this);
		task.execute(_id);
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
}
