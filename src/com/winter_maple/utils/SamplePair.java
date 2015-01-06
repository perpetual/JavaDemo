package com.winter_maple.utils;

public class SamplePair<FST, SND> {

	public FST mFirst;
	public SND mSecond;
	
	public SamplePair(FST first, SND second) {
		mFirst = first;
		mSecond = second;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof SamplePair)) {
			return false;
		}
		SamplePair<FST, SND> sp = (SamplePair<FST, SND>)obj;
		return objectEqual(mFirst, sp.mFirst) && objectEqual(mSecond, sp.mSecond);
	}
	
	private boolean objectEqual(Object obj1, Object obj2) {
		if (obj1 == obj2 || (null == obj1 && null == obj2)) {
			return true;
		}
		if ((null == obj1 && null != obj2) || (null != obj1 && null == obj2)) {
			return false;
		}
		return obj1.equals(obj2);
	}
}
