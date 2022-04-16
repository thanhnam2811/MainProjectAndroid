package com.example.mainproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mainproject.R;
import com.example.mainproject.model.ProgramingLanguage;

import java.util.List;

public class ProgramingLanguageApdapter extends BaseAdapter {
    private final List<ProgramingLanguage> mProgramingLanguages;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final int type;
    public static final int GRIDVIEW_TYPE = 2;
    public static final int LISTVIEW_TYPE = 1;

    public ProgramingLanguageApdapter(Context context, List<ProgramingLanguage> programingLanguages, int type) {
        mContext = context;
        mProgramingLanguages = programingLanguages;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.type = type;
    }

    @Override
    public int getCount() {
        return mProgramingLanguages.size();
    }

    @Override
    public Object getItem(int position) {
        return mProgramingLanguages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            if (type == GRIDVIEW_TYPE)
                view = mLayoutInflater.inflate(R.layout.layout_gridview, null);
            else {
                view = mLayoutInflater.inflate(R.layout.layout_listview, null);
                holder.txtDescription = view.findViewById(R.id.txtDescription);
            }
            holder.imgIcon = view.findViewById(R.id.imgIcon);
            holder.txtTitle = view.findViewById(R.id.txtTitle);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ProgramingLanguage programingLanguage = mProgramingLanguages.get(i);
        int imgId = this.getMipmapResIdByName(programingLanguage.getImage());
        holder.imgIcon.setImageResource(imgId);
        holder.txtTitle.setText(programingLanguage.getName());
        if (type == LISTVIEW_TYPE)
            holder.txtDescription.setText(programingLanguage.getDescription());

        return view;
    }

    private int getMipmapResIdByName(String resName) {
        String pkgName = mContext.getPackageName();
        // Return 0 if not found.
        int resID = mContext.getResources().getIdentifier(resName, "mipmap", pkgName);
        Log.i("Programing Language List View", "Res Name: " + resName + " ==> Res ID = " + resID);
        return resID;
    }

    static class ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDescription;
    }
}
