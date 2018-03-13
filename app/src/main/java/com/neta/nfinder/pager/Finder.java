package com.neta.nfinder.pager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neta.nfinder.R;
import com.neta.nfinder.settings.Settings;
import com.neta.nfinder.database.AppDB;
import com.neta.nfinder.database.ContactDB;
import com.neta.nfinder.database.DocDB;
import com.neta.nfinder.database.FileDB;
import com.neta.nfinder.database.MusicDB;
import com.neta.nfinder.database.PhotoDB;
import com.neta.nfinder.database.VideoDB;
import com.neta.nfinder.view.VideoView;
import com.neta.nfinder.view.adapter.AppAdapter;
import com.neta.nfinder.view.adapter.ContactAdapter;
import com.neta.nfinder.view.adapter.DocAdapter;
import com.neta.nfinder.view.adapter.FileAdapter;
import com.neta.nfinder.view.adapter.MusicAdapter;
import com.neta.nfinder.view.adapter.PhotoAdapter;
import com.neta.nfinder.view.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Berfu on 4.12.2016.
 */

public class Finder extends Fragment
{
    private View fragmentFinder;
    private LinearLayout parentLayout;
    private ScrollView scrollView;
    private String value;

    private EditText findTextView;
    private Settings settings;

    private ImageView voiceInput;
    private ImageView optionButton;
    private CheckBox isFindApp;
    private CheckBox isFindContact;
    private CheckBox isFindMusic;
    private CheckBox isFindPhoto;
    private CheckBox isFindVideo;
    private CheckBox isFindFile;
    private CheckBox isFindDoc;

    private com.neta.nfinder.view.View docView;
    private com.neta.nfinder.view.View musicView;
    private com.neta.nfinder.view.View photoView;
    private com.neta.nfinder.view.View videoView;
    private com.neta.nfinder.view.View appView;
    private com.neta.nfinder.view.View contactView;
    private com.neta.nfinder.view.View favContactView;
    private com.neta.nfinder.view.View fileView;


    public Finder()
    {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentFinder = inflater.inflate(R.layout.fragment_finder, null);
        parentLayout = (LinearLayout) fragmentFinder.findViewById(R.id.parent_layout);
        parentLayout.setVisibility(LinearLayout.GONE);
        settings = new Settings(getContext());

        for(int i=0; i<7; i++)
        {
            parentLayout.addView(new View(getContext()));
        }

        createToolbar();
        optionButton.callOnClick();
        createScrollView();
        createFavouriteContactView();
        createContactView();
        createAppView();
        createDocView();
        createMusicView();
        createPhotoView();
        createVideoView();
        createFileView();

        return fragmentFinder;
    }




    /* TOOLBAR ************************************************************/
    private void createToolbar()
    {
        createFindTextView();
        createCancelButton();
        createInputVoice();
        createIsFindGroup();
        createOptionButton();
    }

    private void createFindTextView()
    {
        TextWatcher tw = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(final Editable editable)
            {
                String val = findTextView.getText().toString();
                LinearLayout fav_contact = (LinearLayout) fragmentFinder.findViewById(R.id.favourite_contact);
                if (!TextUtils.isEmpty(val))
                {
                    parentLayout.setVisibility(LinearLayout.VISIBLE);
                    fav_contact.setVisibility(LinearLayout.GONE);
                    value = val;

                    appView.execute(settings.isFindApp());
                    contactView.execute(settings.isFindContact());
                    musicView.execute(settings.isFindMusic());
                    docView.execute(settings.isFindDoc());
                    photoView.execute(settings.isFindPhoto());
                    videoView.execute(settings.isFindVideo());
                    fileView.execute(settings.isFindFile());

                } else if (TextUtils.isEmpty(val))
                {
                    //findText te girilen veri yoksa arama sonucu kısmı gizleniyor.
                    parentLayout.setVisibility(LinearLayout.GONE);
                    fav_contact.setVisibility(LinearLayout.VISIBLE);
                }
            }
        };

        findTextView = (EditText) fragmentFinder.findViewById(R.id.find_text);
        findTextView.setSingleLine(true);
        findTextView.addTextChangedListener(tw);
    }

    private void createInputVoice()
    {
        voiceInput = (ImageView) fragmentFinder.findViewById(R.id.button_voice);
        voiceInput.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(internetKontrol())
                {
                    findTextView.setText("");
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                            "Say Something...");
                    try {
                        startActivityForResult(intent, 100);
                    } catch (ActivityNotFoundException a) {
                        Toast.makeText(getContext(),"Device Not Support...",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean internetKontrol() { //interneti kontrol eden method
        // TODO Auto-generated method stub
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    findTextView.setText(result.get(0).toLowerCase());
                }
                break;
            }

        }
    }

    private void createCancelButton()
    {
        ImageView cancelButton = (ImageView) fragmentFinder.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                findTextView.setText("");
            }
        });
    }

    private void createIsFindGroup()
    {
        isFindApp = (CheckBox) fragmentFinder.findViewById(R.id.checkbox_app);
        isFindApp.setChecked(settings.isFindApp());
        isFindApp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                settings.setFindApp(b);
            }
        });

        isFindContact = (CheckBox) fragmentFinder.findViewById(R.id.checkbox_contact);
        isFindContact.setChecked(settings.isFindContact());
        isFindContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                settings.setFindContact(b);
            }
        });

        isFindMusic = (CheckBox) fragmentFinder.findViewById(R.id.checkbox_music);
        isFindMusic.setChecked(settings.isFindMusic());
        isFindMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                settings.setFindMusic(b);
            }
        });

        isFindPhoto = (CheckBox) fragmentFinder.findViewById(R.id.checkbox_photo);
        isFindPhoto.setChecked(settings.isFindPhoto());
        isFindPhoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                settings.setFindPhoto(b);
            }
        });

        isFindVideo = (CheckBox) fragmentFinder.findViewById(R.id.checkbox_video);
        isFindVideo.setChecked(settings.isFindVideo());
        isFindVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                settings.setFindVideo(b);
            }
        });

        isFindFile = (CheckBox) fragmentFinder.findViewById(R.id.checkbox_file);
        isFindFile.setChecked(settings.isFindFile());
        isFindFile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                settings.setFindFile(b);
            }
        });

        isFindDoc = (CheckBox) fragmentFinder.findViewById(R.id.checkbox_doc);
        isFindDoc.setChecked(settings.isFindDoc());
        isFindDoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                settings.setFindDoc(b);
            }
        });

    }

    private void createFavouriteContactView()
    {
        favContactView = new com.neta.nfinder.view.View(getContext())
        {
            Cursor cursor;
            @Override
            public void doIn()
            {
                ContactDB db = new ContactDB(getContext());
                cursor = db.getStarred();
            }

            @Override
            public void onPost()
            {
                ContactAdapter adapter = new ContactAdapter(getContext(), cursor);
                setAdapter(adapter);
                favContactView.setViewName(getString(R.string.favourite_contacts) + " ( "+String.valueOf(adapter.getCount())+" )");
            }

            @Override
            public void onClickAction(View v)
            {

            }

            @Override
            public void onLongClickAction(View v)
            {

            }
        };
        favContactView.setViewName("Favori Kişilerim");
        favContactView.setViewType(com.neta.nfinder.view.View.GRID);
        favContactView.execute(true);
        LinearLayout row = (LinearLayout) fragmentFinder.findViewById(R.id.favourite_contact);
        row.addView(favContactView);
    }

    private void createOptionButton()
    {
        optionButton = (ImageView) fragmentFinder.findViewById(R.id.button_options);
        optionButton.setClickable(true);
        optionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TableLayout tl = (TableLayout) fragmentFinder.findViewById(R.id.view_option);
                if(tl.getVisibility() == TableLayout.GONE)
                {
                    optionButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
                    tl.setVisibility(TableLayout.VISIBLE);
                }
                else
                {
                    optionButton.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right));
                    tl.setVisibility(TableLayout.GONE);
                }
            }
        });
    }
    /* END TOOLBAR *******************************************************/





    private void createScrollView()
    {
        scrollView = (ScrollView) fragmentFinder.findViewById(R.id.scroll);
    }

    private void createDocView()
    {
        docView = new com.neta.nfinder.view.DocView(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                DocDB db = new DocDB(getContext());
                cursor = db.getFound(value);
            }

            @Override
            public void onPost()
            {
                DocAdapter adapter = new DocAdapter(getContext(), cursor);
                setAdapter(adapter);
                try {
                    docView.setViewName(getString(R.string.doc) + " ( "+String.valueOf(adapter.getCount())+" )");
                }catch (Exception e)
                {
                    docView.setViewName(getString(R.string.doc));
                }

            }
        };
        int index = settings.getViewIndex(Settings.Key.FINDER_DOC);
        Log.e("DOC INDEX", String.valueOf(index));
        docView.setViewName(getString(R.string.doc));
        docView.setViewType(com.neta.nfinder.view.View.LIST);
        parentLayout.removeViewAt(index);
        parentLayout.addView(docView, index);
    }

    private void createMusicView()
    {
        musicView = new com.neta.nfinder.view.MusicVİew(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                MusicDB db = new MusicDB(getContext());
                cursor = db.getFound(value);
            }

            @Override
            public void onPost()
            {
                MusicAdapter adapter = new MusicAdapter(getContext(), cursor);
                setAdapter(adapter);
                try {
                    musicView.setViewName(getString(R.string.music) + " ( "+String.valueOf(adapter.getCount())+" )");
                }catch (Exception e)
                {
                    musicView.setViewName(getString(R.string.music));
                }
            }
        };
        int index = settings.getViewIndex(Settings.Key.FINDER_MUSIC);
        Log.e("DOC MUSIC", String.valueOf(index));
        musicView.setViewType(com.neta.nfinder.view.View.LIST);
        musicView.setViewName(getString(R.string.music));
        parentLayout.removeViewAt(index);
        parentLayout.addView(musicView, index);
    }

    private void createPhotoView()
    {
        photoView = new com.neta.nfinder.view.PhotoView(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                PhotoDB db = new PhotoDB(getContext());
                cursor = db.getFound(value);
            }

            @Override
            public void onPost()
            {
                final PhotoAdapter adapter = new PhotoAdapter(getContext(), cursor);
                setAdapter(adapter);
                try {
                    photoView.setViewName(getString(R.string.photo) + " ( "+String.valueOf(adapter.getCount())+" )");
                }catch (Exception e)
                {
                    photoView.setViewName(getString(R.string.photo));
                }
            }
        };
        int index = settings.getViewIndex(Settings.Key.FINDER_PHOTO);
        Log.e("DOC PHOTO", String.valueOf(index));
        photoView.setViewType(com.neta.nfinder.view.View.LIST);
        parentLayout.removeViewAt(index);
        parentLayout.addView(photoView, index);
    }

    private void createVideoView()
    {
        videoView = new VideoView(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                VideoDB db = new VideoDB(getContext());
                cursor = db.getFound(value);
            }

            @Override
            public void onPost()
            {
                VideoAdapter adapter = new VideoAdapter(getContext(), cursor);
                setAdapter(adapter);
                try {
                    videoView.setViewName(getString(R.string.video) + " ( "+String.valueOf(adapter.getCount())+" )");
                }catch (Exception e)
                {
                    videoView.setViewName(getString(R.string.video));
                }
            }
        };

        int index = settings.getViewIndex(Settings.Key.FINDER_VIDEO);
        Log.e("VIDEO INDEX", String.valueOf(index));
        videoView.setViewType(com.neta.nfinder.view.View.LIST);
        videoView.setViewName(getString(R.string.video));
        parentLayout.removeViewAt(index);
        parentLayout.addView(videoView, index);
    }

    private void createAppView()
    {
        appView = new com.neta.nfinder.view.View(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                AppDB db = new AppDB(getContext());
                cursor = db.getFound(value);
            }

            @Override
            public void onPost()
            {
                AppAdapter appAdapter = new AppAdapter(getContext(), cursor);
                setAdapter(appAdapter);
                try
                {
                    appView.setViewName(getString(R.string.applications) + " ( "+String.valueOf(appAdapter.getCount())+" )");
                }catch (Exception e)
                {
                    appView.setViewName(getString(R.string.applications));
                }

            }

            @Override
            public void onClickAction(View v)
            {

            }

            @Override
            public void onLongClickAction(View v)
            {

            }
        };
        int index = settings.getViewIndex(Settings.Key.FINDER_APP);
        Log.e("APP INDEX", String.valueOf(index));
        appView.setViewName(getString(R.string.applications));
        appView.setViewType(com.neta.nfinder.view.View.GRID);
        parentLayout.removeViewAt(index);
        parentLayout.addView(appView, index);
    }

    private void createContactView()
    {
        contactView = new com.neta.nfinder.view.View(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                ContactDB db = new ContactDB(getContext());
                cursor = db.getFound(value);
            }

            @Override
            public void onPost()
            {
                ContactAdapter adapter = new ContactAdapter(getContext(), cursor);
                setAdapter(adapter);
                try {
                    contactView.setViewName(getString(R.string.contacts) + " ( "+String.valueOf(adapter.getCount())+" )");
                }catch (Exception e)
                {
                    contactView.setViewName(getString(R.string.contacts));
                }

            }

            @Override
            public void onClickAction(View v)
            {

            }

            @Override
            public void onLongClickAction(View v)
            {

            }
        };
        int index = settings.getViewIndex(Settings.Key.FINDER_CONTACT);
        contactView.setViewType(com.neta.nfinder.view.View.GRID);
        contactView.setViewName(getString(R.string.contacts));
        parentLayout.removeViewAt(index);
        parentLayout.addView(contactView, index);
    }

    private void createFileView()
    {
        fileView = new com.neta.nfinder.view.FileView(getContext())
        {
            Cursor cursor;

            @Override
            public void doIn()
            {
                FileDB db = new FileDB(getContext());
                cursor = db.getFound(value);
            }

            @Override
            public void onPost()
            {
                FileAdapter adapter = new FileAdapter(getContext(), cursor);
                setAdapter(adapter);
                try {
                    fileView.setViewName(getString(R.string.files));
                }catch (Exception e)
                {
                    fileView.setViewName(getString(R.string.files) + " ( " + String.valueOf(adapter.getCount()) + " )");
                }
            }
        };

        int index = settings.getViewIndex(Settings.Key.FINDER_FILE);
        Log.e("FILE INDEX", String.valueOf(index));
        fileView.setViewName(getString(R.string.files));
        fileView.setViewType(com.neta.nfinder.view.View.LIST);
        parentLayout.removeViewAt(index);
        parentLayout.addView(fileView, index);
    }
}
