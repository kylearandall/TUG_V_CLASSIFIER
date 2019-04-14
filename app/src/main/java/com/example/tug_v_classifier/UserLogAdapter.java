package com.example.tug_v_classifier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserLogAdapter extends ArrayAdapter<UserLogItem> {

    private Context mContext;
    private int mResource;

    public UserLogAdapter(@NonNull Context context, int resource, @NonNull List<UserLogItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String userName = getItem(position).getUserName();
        String date = getItem(position).getDate();
        String location = getItem(position).getLocation();
        String recClass = getItem(position).getRecClass();
        String setClass = getItem(position).getSetClass();
        String resultStatus = getItem(position).getResultStatus();
        String pictureStrings = getItem(position).getPictureStrings();
        String adminApprovalName = getItem(position).getAdminApprovedName();
        String otherText = getItem(position).getOtherUnknownText();
        String factors = getItem(position).getFactors();
        int uploaded = getItem(position).isUploaded();



        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, parent, false);

        TextView tvUserName = (TextView)convertView.findViewById(R.id.username);
        TextView tvResultStatus = (TextView)convertView.findViewById(R.id.resultstatus);
        TextView tvDate = (TextView)convertView.findViewById(R.id.date);
        TextView tvLocation = (TextView)convertView.findViewById(R.id.location);

        tvUserName.setText(userName);
        tvResultStatus.setText(resultStatus);
        tvDate.setText(date);
        tvLocation.setText(location);

        return convertView;
    }
}
