package com.example.contexualmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String>list;
    ArrayAdapter<String>adapter;
    List<String>selected=new ArrayList<>();
    int countOfSel=0;
    public  static final String MY_TAG="com.example.contexualmenu.MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.listView);
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        list=new ArrayList<>();
        for(int i=0;i<15;i++){
            list.add("Item "+i);
        }
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);

        lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                
                if(checked){
                    selected.add(list.get(position));
                    countOfSel++;
                    mode.setTitle(countOfSel+" selected");
                    lv.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.purple_200));
                }else{selected.remove(list.get(position));
                    countOfSel--;
                    mode.setTitle(countOfSel+" selected");
                    lv.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.white));

                }

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.menu_cont_mode,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @SuppressLint("LongLogTag")
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch(item.getItemId()){
                    case R.id.delete:
                        Iterator<String> iterator=selected.iterator();
                        String n;
                        int index;
                        int c=0;
                        while (iterator.hasNext()){
                            n=iterator.next();
                            index=(list.indexOf(n)+c);
                           lv.getChildAt(index).setBackgroundColor(getResources().getColor(R.color.white));
                           list.remove(n);
                            c++;
                            }
                        adapter.notifyDataSetChanged();
                        selected.clear();
                            mode.finish();
                            break;

                    case R.id.share:
                        Toast.makeText(getApplicationContext(),"Sharing..",Toast.LENGTH_LONG).show();
                        mode.finish();
                        break;
                    }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                Iterator<String> iteratorSh=selected.iterator();
                String b;
                while (iteratorSh.hasNext()) {
                    b = iteratorSh.next();
                    lv.getChildAt(list.indexOf(b)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                countOfSel=0;
                selected.clear();

            }
        });
    }
}