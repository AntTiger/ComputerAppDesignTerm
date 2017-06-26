package com.example.kjw.mylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class BookManagementListAdapter extends ArrayAdapter implements View.OnClickListener {

    public interface ListBtnClickListener{
        void onListBtnClick(BookManagementVO position);
    }
    int resourceId ;
    private ListBtnClickListener listBtnClickListener;


    public BookManagementListAdapter(Context context, int resource, ArrayList<BookManagementListItem> list, ListBtnClickListener clickListener) {
        super(context, resource, list) ;

        this.resourceId = resource ;

        this.listBtnClickListener = clickListener ;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.book_management_list_item_title) ;
        TextView authorTextView = (TextView) convertView.findViewById(R.id.book_management_list_item_author) ;

        BookManagementListItem listViewItem = (BookManagementListItem) getItem(position);

        titleTextView.setText(listViewItem.getTitleStr());
        authorTextView.setText(listViewItem.getAuthorStr());

        Button button_modification=(Button)convertView.findViewById(R.id.book_management_list_item_modification);
        button_modification.setTag(new BookManagementVO(position,1));
        button_modification.setOnClickListener(this);

        Button button_deletion=(Button)convertView.findViewById(R.id.book_management_list_item_deletion);
        button_deletion.setTag(new BookManagementVO(position,2));
        button_deletion.setOnClickListener(this);

        return convertView;
    }

    public void onClick(View v){
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((BookManagementVO) v.getTag()) ;
        }
    }


}

