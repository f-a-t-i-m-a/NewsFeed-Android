package ir.appson.sportfeed.views.navigationDrawer;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.proxy.FeedsListProxy;
import ir.appson.sportfeed.proxy.dto.ChannelObject;

import java.util.ArrayList;

public class NavigationDrawerAsync extends AsyncTask<Object, Object, ArrayList<ChannelObject>> {

    private Context context;
    private NavigationDrawerFragment navigationDrawerFragment;

    private FeedsListProxy proxy;

    public NavigationDrawerAsync(Context context, NavigationDrawerFragment navigationDrawerFragment) {
        this.context = context;
        this.navigationDrawerFragment = navigationDrawerFragment;

        this.proxy = new FeedsListProxy(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ArrayList<ChannelObject> channels = proxy.getFromCache();
        populateDrawer(channels);
    }

    @Override
    protected ArrayList<ChannelObject> doInBackground(Object[] params) {
        try {
            return proxy.get();
        }
        catch (Exception e){
            Toast.makeText(navigationDrawerFragment.getActivity(),"No network connection", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<ChannelObject> channelsObjectList) {
        super.onPostExecute(channelsObjectList);

        if (channelsObjectList != null)
            populateDrawer(channelsObjectList);
    }

    private void populateDrawer(ArrayList<ChannelObject> channelsObjectList) {
        ArrayList<String> channelsNamesList = new ArrayList<>();

        if (channelsObjectList == null)
            channelsObjectList = new ArrayList<>();

        for(ChannelObject channelObject : channelsObjectList)
            channelsNamesList.add(channelObject.getTitle());

        channelsNamesList.add(0,navigationDrawerFragment.getResources().getString(R.string.home_persian));
        channelsNamesList.add(1,navigationDrawerFragment.getResources().getString(R.string.all_channels_persian));
        channelsNamesList.add(channelsNamesList.size(),navigationDrawerFragment.getResources().getString(R.string.support_national_team_persian));
        channelsNamesList.add(channelsNamesList.size(),navigationDrawerFragment.getResources().getString(R.string.suggest_app_to_a_friend_persian));
        channelsNamesList.add(channelsNamesList.size(), navigationDrawerFragment.getResources().getString(R.string.customer_support_persian));
        channelsNamesList.add(channelsNamesList.size(), navigationDrawerFragment.getResources().getString(R.string.about_us_persian));

        // mNewsChannelsObjects will be used when user clicks on an item in drawer.
        navigationDrawerFragment.mNewsChannelsObjects = channelsObjectList;
        navigationDrawerFragment.mDrawerListView.setAdapter(new ArrayAdapter<>(
                navigationDrawerFragment.getActivity().getApplicationContext(),
                R.layout.text_view_navigation_drawer,
                channelsNamesList
        ));
    }
}
