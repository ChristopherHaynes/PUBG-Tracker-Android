package wxc.pubgtracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class UserDiaglogFragment extends DialogFragment {

    PUBGManager manager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        manager = MyProperties.getInstance().pubgManager;

        String gamertagArray[];
        gamertagArray = new String[4];
        gamertagArray[0] = manager.wxcGamertags.get(0);
        gamertagArray[1] = manager.wxcGamertags.get(1);
        gamertagArray[2] = manager.wxcGamertags.get(2);
        gamertagArray[3] = manager.wxcGamertags.get(3);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Default Gamertag")
                .setItems(gamertagArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                            default:
                                SaveVariable("defaultGamertag", "Applepie2");
                                break;
                            case 1:
                                SaveVariable("defaultGamertag", "HeyChunk");
                                break;
                            case 2:
                                SaveVariable("defaultGamertag", "JellyFilledFun");
                                break;
                            case 3:
                                SaveVariable("defaultGamertag", "JP Argyle2");
                                break;
                        }
                    }
                });
        return builder.create();
    }

    protected void SaveVariable(String tag, String value) {
        SharedPreferences preferences;
        preferences = getActivity().getSharedPreferences("userprefs", 0);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(tag, value).commit();
    }
}
