package com.yene.bustiming;

import com.yene.fragment.DummySectionFragment;
import com.yene.fragment.LaunchpadSectionFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;



public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

    public AppSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    LaunchpadSectionFragment lp = new LaunchpadSectionFragment();
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return  lp;
                
            

            default:
               
                Fragment fragment = new DummySectionFragment();
                Bundle args = new Bundle();
                args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                fragment.setArguments(args);
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Map view " + (position);
    }

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
		

//        FragmentManager manager = ((Fragment) object).getFragmentManager();
//        android.support.v4.app.FragmentTransaction trans = manager.beginTransaction();
//        trans.remove((Fragment) object);
//        trans.commit();
	}
}