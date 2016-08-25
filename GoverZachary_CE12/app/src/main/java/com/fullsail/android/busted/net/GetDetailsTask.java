// Zachary Gover
// JAV1 - 1608
// GetDetailsTask

package com.fullsail.android.busted.net;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.fullsail.android.busted.MainActivity;

public class GetDetailsTask extends AsyncTask<Integer, Void, HashMap<String, String>> {

	private static final String API_URL = "https://www.govtrack.us/api/v2/person/";

	private static final String NAME = "name";
	private static final String BIRTHDAY = "birthday";
	private static final String GENDER = "gender";
	private static final String TWITTER_ID = "twitter_id";
	private static final String NUM_COMMITTEES = "num_committees";
	private static final String NUM_ROLES = "num_roles";

	private MainActivity mActivity;

	public GetDetailsTask(MainActivity _activity) {
		mActivity = _activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mActivity.showProgress();
	}

	@Override
	protected HashMap<String, String> doInBackground(Integer... _params) {

		// Add member ID to the end of the URL
		String data = NetworkUtils.getNetworkData(API_URL + _params[0]);
		HashMap<String, String> retValues = new HashMap<>();

		try {

			JSONObject response = new JSONObject(data);

			String birthday = response.getString("birthday");
			retValues.put(BIRTHDAY, birthday);

			JSONArray committeeArray = response.getJSONArray("committeeassignments");
			int numCommittees = committeeArray.length();
			retValues.put(NUM_COMMITTEES, "" + numCommittees);

			String name = response.getString("name");
			retValues.put(NAME, name);

			String gender = response.getString("gender_label");
			retValues.put(GENDER, gender);

			JSONArray rolesArray = response.getJSONArray("roles");
			int numRoles = rolesArray.length();
			retValues.put(NUM_ROLES, "" + numRoles);

			String twitterId = response.getString("twitterid");
			retValues.put(TWITTER_ID, twitterId);


		} catch(JSONException e) {
			e.printStackTrace();
		}

		return retValues;
	}

	@Override
	protected void onPostExecute(HashMap<String, String> _result) {
		super.onPostExecute(_result);

		// Update the UI
		String name = _result.get(NAME);
		String birthday = _result.get(BIRTHDAY);
		String gender = _result.get(GENDER);
		String twitterId = _result.get(TWITTER_ID);
		String numCommittees = _result.get(NUM_COMMITTEES);
		String numRoles = _result.get(NUM_ROLES);
		mActivity.populateMemberDetailsScreen(
			name, birthday, gender, twitterId, numCommittees, numRoles
		);

		mActivity.hideProgress();
	}
}