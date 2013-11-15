package com.yene.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;



public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
	
	
    public AppSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    LaunchpadSectionFragment lp = new LaunchpadSectionFragment();
    public  Fragment ListView(int i){
    	 Fragment fragment = new ListView();
    	    Bundle args = new Bundle();
    	    args.putInt(ListView.ARG_SECTION_NUMBER, i + 1);
    	    fragment.setArguments(args);
			return fragment;
    }
   
    @Override
    public Fragment getItem(int i) {
    	Log.e("Selected ","selected view:"+ i);
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return  lp;
                
            case 1:
                return ListView(i);

            default:
              
                return ListView(i);
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
    	
    	String Map[] = {"Map","Options"};
    	
        return ""+Map[position];
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