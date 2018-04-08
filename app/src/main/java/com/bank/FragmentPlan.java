package com.bank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Adminstrator on 2017/4/2.
 */
public class FragmentPlan extends Fragment {

    private List<PlanData> mplanData;

    private HomeAdapter mAdapter;


    //数据库
    private MySQLiteHelper mMysql;
    private SQLiteDatabase mDataBase;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showplan, container, false);

        initData();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*
         *    在此利用HomeAdapter 将mRecyclerView绑定一个内容适配器
         * */
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return view;
    }

    public void initData() {
        mplanData = new ArrayList<PlanData>();
        mMysql = new MySQLiteHelper(getActivity(), "finance.db", null, 1);
        mDataBase = mMysql.getReadableDatabase();

        Cursor cursor = mDataBase.rawQuery("select * from plan", null);
        cursor.moveToFirst();
        int columnsSize = cursor.getColumnCount();
        int number = 0;
        while (number < cursor.getCount()) {

            PlanData a = new PlanData();
            a.setMoringPlan(cursor.getString(cursor.getColumnIndex("Morningplan")));
            a.setAfterPlan(cursor.getString(cursor.getColumnIndex("Afternoonplan")));
            a.setNightPlan(cursor.getString(cursor.getColumnIndex("Nightplan")));
            a.setConclusion(cursor.getString(cursor.getColumnIndex("Conclusion")));
            a.setRank(cursor.getString(cursor.getColumnIndex("Rank")));

            cursor.moveToNext();
            number++;

            mplanData.add(a);
        }

        cursor.close();
        mDataBase.close();
        mMysql.close();
    }


    class PlanData {
        String MoringPlan;
        String AfterPlan;
        String NightPlan;
        String Conclusion;
        String Rank;

        void setMoringPlan(String plan) {
            MoringPlan = plan;
        }

        void setAfterPlan(String plan) {
            AfterPlan = plan;
        }

        void setNightPlan(String plan) {
            NightPlan = plan;
        }

        void setRank(String plan) {
            Rank = plan;
        }

        void setConclusion(String plan) {
            Conclusion = plan;
        }

        String getMoringPlan() {
            return this.MoringPlan;
        }

        String getAfterPlan() {
            return this.AfterPlan;
        }

        String getNightPlan() {
            return this.NightPlan;
        }

        String getConclusion() {
            return this.Conclusion;
        }

        String getRank() {
            return this.Rank;
        }
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;

        private int mOrientation;

        public DividerItemDecoration(Context context, int orientation) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }

        public void setOrientation(int orientation) {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent) {
//                        Log.d("recyclerview - itemdecoration", "onDraw()");

            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }

        }


        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnLongClickListener {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).
                    inflate(R.layout.planitem, parent, false));
            return holder;
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            PlanData data = mplanData.get(position);
            holder.showMorningplan.setText(data.getMoringPlan());
            holder.showAfternoonplan.setText(data.getAfterPlan());
            holder.showNightplan.setText(data.getNightPlan());

            holder.showNightplan.setOnLongClickListener(this);
        }
        @Override
        public int getItemCount() {
            return mplanData.size();
        }

        @Override
        public boolean onLongClick(final View v) {
            new AlertDialog.Builder(getContext())
                        /* 弹出窗口的最上头文字 */
                    .setTitle("delete this plan?")
                        /* 设置弹出窗口的图式 */
                    .setIcon(android.R.drawable.ic_dialog_info)
                        /* 设置弹出窗口的信息 */
                    .setMessage("Determine to delete the current plan")
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {

                                    //获取当前数据库
                                    mMysql = new MySQLiteHelper(getActivity(), "finance.db", null, 1);
                                    mDataBase = mMysql.getReadableDatabase();
                                    try {
                                        String nightplan = ((TextView)v).getText().toString();
                                        mDataBase.delete("plan","Nightplan=?",new String[]{nightplan});

                                    } catch (Exception e) {
                                        Log.e("delete erro!", "error");
                                    } finally {
                                        mDataBase.close();
                                        mMysql.close();
                                    }
                                }
                            }
                    ).setNegativeButton(
                    "NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }
            ).show();

            return true;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView showMorningplan, showAfternoonplan, showNightplan,showID;
            public MyViewHolder(View view) {
                super(view);
                showMorningplan = (TextView) view.findViewById(R.id.showmorningplan);
                showAfternoonplan = (TextView) view.findViewById(R.id.showafternoonplan);
                showNightplan = (TextView) view.findViewById(R.id.shownightplan);
                showID = view.findViewById(R.id.Conclusion);
            }
        }
    }

}
