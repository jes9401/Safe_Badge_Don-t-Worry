package com.dowellcomputer.naviexam.Announce;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dowellcomputer.naviexam.R;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice, null);
        TextView noticeText =(TextView) v.findViewById(R.id.noticeText);
        TextView postText =(TextView) v.findViewById(R.id.postText);
        noticeText.setText(noticeList.get(i).getTitle());
        postText.setText(noticeList.get(i).getText());

        v.setTag(noticeList.get(i).getTitle());
        return v;
    }


}
