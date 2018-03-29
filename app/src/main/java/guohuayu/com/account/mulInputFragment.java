package guohuayu.com.account;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class mulInputFragment extends Fragment implements View.OnClickListener {
    private LinearLayout add_bill, add_plan, check_bill;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mul_input, container, false);

        add_bill = view.findViewById(R.id.add_bill);
        add_plan = view.findViewById(R.id.add_plan);
        check_bill = view.findViewById(R.id.check_bill);

        add_bill.setOnClickListener(this);
        add_plan.setOnClickListener(this);
        check_bill.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_bill:
                Intent intent = new Intent(getActivity(),addbillActivity.class);
                startActivity(intent);
                break;
            case R.id.add_plan:
                startActivity(new Intent(getActivity(), addplanActivity.class));
                break;
            case R.id.check_bill:
                startActivity(new Intent(getActivity(), checkbillActivity.class));

        }
    }
}
