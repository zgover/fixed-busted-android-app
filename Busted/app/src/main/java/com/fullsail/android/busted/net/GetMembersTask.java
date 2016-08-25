// Zachary Gover
// JAV1 - 1608
// GetMembersTask

package com.fullsail.android.busted.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.fullsail.android.busted.MainActivity;
import com.fullsail.android.busted.object.Member;

public class GetMembersTask extends AsyncTask<Void, Void, ArrayList<Member>> {

	private static final String API_URL = "https://www.govtrack.us/api/v2/role?current=true";

	private MainActivity mActivity;

	public GetMembersTask(MainActivity _activity) {
		mActivity = _activity;
	}

	@Override
	protected ArrayList<Member> doInBackground(Void... _params) {

		String data = NetworkUtils.getNetworkData(API_URL);

		try {

			JSONObject response = new JSONObject(data);

			JSONArray membersJson = response.getJSONArray("objects");

			ArrayList<Member> members = new ArrayList<Member>();

			for(int i = 0; i < membersJson.length(); i++) {
				JSONObject obj = membersJson.getJSONObject(i);
				JSONObject person = obj.getJSONObject("person");

				int id = person.getInt("id");
				String name = person.getString("name");
				String party = obj.getString("party");

				members.add(new Member(id, name, party));
			}

			// Update the UI
			mActivity.showMembersListScreen(members);

			return members;

		} catch(JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<Member> _result) {
		super.onPostExecute(_result);
	}
}