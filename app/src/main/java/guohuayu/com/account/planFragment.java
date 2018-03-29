package guohuayu.com.account;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//used to hold the plan detail fragment

public class planFragment extends Fragment  {

    private ListView listView;

    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        listView = view.findViewById(R.id.listview);
        //test listview data
        String[] moringplan = new String[]{"moring1", "moring2","moring3"};
        String[] afternoonplan = new String[]{"afternoon1", "afternoon2","afternoon3"};
        String[] nightplan = new String[]{"night1", "night2","night3"};

        list = new ArrayList<>();

        //input test listview data
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                Map<String, Object> map = new HashMap<>();
                map.put("moringplan",moringplan[j]);
                map.put("afternoonplan",afternoonplan[j]);
                map.put("nightplan",nightplan[j]);
                list.add(map);
            }
        }

        simpleAdapter = new SimpleAdapter(getContext(),
                list,
                R.layout.listview_item,
                new String[]{"moringplan","afternoonplan","nightplan"},
                new int[]{R.id.tv_morningplan, R.id.tv_afternoonplan,R.id.tv_nightplan});
        listView.setAdapter(simpleAdapter);

        return view;
    }

}
