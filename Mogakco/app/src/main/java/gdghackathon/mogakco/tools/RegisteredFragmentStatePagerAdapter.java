package gdghackathon.mogakco.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 *
 *  - Reference : http://stackoverflow.com/questions/8785221/retrieve-a-fragment-from-a-viewpager (Streets Of Boston's solution)
 */
public abstract class RegisteredFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public RegisteredFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
