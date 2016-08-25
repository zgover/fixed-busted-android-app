// Zachary Gover
// JAV1 - 1608
// Member

package com.fullsail.android.busted.object;

public class Member {

	private int mId;
	private String mName;
	private String mParty;

	public Member() {
		mId = 0;
		mName = mParty = "";
	}

	public Member(int _id) {
		this();
		mId = _id;
	}

	public Member(int _id, String _name) {
		this(_id);
		mName = _name;
	}

	public Member(int _id, String _name, String _party) {
		this(_id, _name);
		mParty = _party;
	}

	public void setId(int _id) {
		mId = _id;
	}

	public int getId() {
		return mId;
	}

	public void setName(String _name) {
		mName = _name;
	}

	public String getName() {
		return mName;
	}

	public void setParty(String _party) {
		mParty = _party;
	}

	public String getParty() {
		return mParty;
	}
}