package eu.se_bastiaan.popcorntimeremote.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.koushikdutta.async.future.FutureCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import eu.se_bastiaan.popcorntimeremote.R;
import eu.se_bastiaan.popcorntimeremote.activities.ControllerActivity;
import eu.se_bastiaan.popcorntimeremote.rpc.PopcornTimeRpcClient;
import eu.se_bastiaan.popcorntimeremote.utils.LogUtils;
import eu.se_bastiaan.popcorntimeremote.widget.JoystickView;

public class SeriesControllerFragment extends BaseControlFragment {

    @InjectView(R.id.joystick)
    JoystickView joystickView;
    @InjectView(R.id.backButton)
    ImageButton backButton;
    @InjectView(R.id.favouriteButton)
    ImageButton favouriteButton;
    @InjectView(R.id.watchedButton)
    ImageButton watchedButton;

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.backButton:
                    getClient().back(mBlankResponseCallback);
                    break;
                case R.id.favouriteButton:
                    getClient().toggleFavourite(mBlankResponseCallback);
                    break;
                case R.id.watchedButton:
                    getClient().toggleWatched(mBlankResponseCallback);
                    break;
            }
        }
    };

    private JoystickView.OnJoystickMoveListener mOnJoystickMoveListener = new JoystickView.OnJoystickMoveListener() {
        @Override
        public void onValueChanged(int angle, int power, JoystickView.Direction direction) {
            LogUtils.d("mOnJoystickMoveListener", power);

            switch (direction) {
                case CENTER:
                    getClient().enter(mBlankResponseCallback);
                    break;
                case UP:
                    getClient().up(mBlankResponseCallback);
                    break;
                case DOWN:
                    getClient().down(mBlankResponseCallback);
                    break;
                case RIGHT:
                    getClient().nextSeason(mBlankResponseCallback);
                    break;
                case LEFT:
                    getClient().prevSeason(mBlankResponseCallback);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d("JoyStickMainControllerFragment", "onCreateView");

        View v = inflater.inflate(R.layout.fragment_seriescontroller, container, false);
        ButterKnife.inject(this, v);

        backButton.setOnClickListener(mButtonClickListener);
        favouriteButton.setOnClickListener(mButtonClickListener);
        watchedButton.setOnClickListener(mButtonClickListener);

        joystickView.setOnJoystickMoveListener(mOnJoystickMoveListener);
        joystickView.setJoystickImage(JoystickView.Direction.CENTER, R.drawable.ic_action_ok);
        joystickView.setJoystickImage(JoystickView.Direction.LEFT, R.drawable.ic_action_prevseason);
        joystickView.setJoystickImage(JoystickView.Direction.RIGHT, R.drawable.ic_action_nextseason);


        return v;
    }

}