package com.josh.gomez.projectmessi.module.credits;

import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.josh.gomez.projectmessi.R;
import com.josh.gomez.projectmessi.generic.utils.Animation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class CreditActivity extends AppCompatActivity {

    @BindView(R.id.profile_image)
    ImageView profileImage;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        ButterKnife.bind(this);
    }

    @OnLongClick(R.id.profile_image)
    public boolean OnProfileImageLongClick() {
        toggleProfilePic();
        return true;
    }

    private void toggleProfilePic() {
        profileImage.setImageDrawable(getProfilepic());
        Animation.FadeInDown(profileImage);
    }

    private Drawable getProfilepic() {
        j++;
        switch (j) {
            case 1:
                return getResources().getDrawable(R.drawable.josh);
            default: {
                j = 0;
                return getResources().getDrawable(R.drawable.simpson);
            }
        }
    }

    @OnClick(R.id.imageView6)
    public void closeWindow() {
        this.finish();
    }
}
